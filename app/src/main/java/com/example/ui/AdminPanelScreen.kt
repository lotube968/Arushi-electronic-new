package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(
    viewModel: RepairViewModel,
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("युझर्स (Users)", "रिचार्ज (Recharges)", "सुरक्षा (Plans)", "लोन दर (Loan)", "व्यवहार (Txns)")
    
    val allUsers by viewModel.allUsers.collectAsState()
    val rechargePlans by viewModel.rechargePlans.collectAsState()
    val protectionPlans by viewModel.protectionPlans.collectAsState()
    val warrantyPlans by viewModel.warrantyPlans.collectAsState()
    val loanSettings by viewModel.loanSettings.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    // Real-time floating notification for pending transactions
    val pendingTxn = remember(transactions) {
        transactions.firstOrNull { it.status == "PENDING" || it.status == "PENDING_ADMIN_APPROVAL" }
    }

    val context = androidx.compose.ui.platform.LocalContext.current
    var prevPendingCount by remember { mutableStateOf(-1) }
    val currentPendingCount = remember(transactions) {
        transactions.count { it.status == "PENDING" || it.status == "PENDING_ADMIN_APPROVAL" }
    }

    LaunchedEffect(currentPendingCount) {
        if (prevPendingCount != -1 && currentPendingCount > prevPendingCount) {
            try {
                val toneGen = android.media.ToneGenerator(android.media.AudioManager.STREAM_NOTIFICATION, 100)
                toneGen.startTone(android.media.ToneGenerator.TONE_PROP_BEEP, 200)
            } catch (e: Exception) {
                // Sound fail fallback
            }
        }
        prevPendingCount = currentPendingCount
    }

    var showChangePasswordDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "ऍडमिन पॅनल / Admin Panel",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "आरुषी इलेक्ट्रॉनिक्स व्यवस्थापन",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { showChangePasswordDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Change Password",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = onLogout) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Logout",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                         containerColor = Color(0xFF1E1E2E)
                    )
                )
            },
            containerColor = Color(0xFF12121A)
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Tab Selector
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color(0xFF1E1E2E),
                    contentColor = Color(0xFFF59E0B)
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    maxLines = 1
                                )
                            },
                            selectedContentColor = Color(0xFFF59E0B),
                            unselectedContentColor = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                when (selectedTab) {
                    0 -> UserManagementTab(users = allUsers, onUpdateUser = { email, score, active, wallet, due, link ->
                        viewModel.updateUserFromAdmin(email, score, active, wallet, due, link)
                    })
                    1 -> RechargePlansTab(
                        plans = rechargePlans, 
                        onUpdatePlan = { plan -> viewModel.updateRechargePlan(plan) },
                        onAddPlan = { plan -> viewModel.addNewRechargePlan(plan) },
                        onAddHandset = { handset -> viewModel.addNewHandset(handset) }
                    )
                    2 -> WarrantyPlansTab(
                        protectionPlans = protectionPlans,
                        warrantyPlans = warrantyPlans,
                        onUpdateProtection = { plan -> viewModel.updateProtectionPlan(plan) },
                        onUpdateWarranty = { plan -> viewModel.updateWarrantyPlan(plan) },
                        onAddProtection = { plan -> viewModel.addNewProtectionPlan(plan) },
                        onAddWarranty = { plan -> viewModel.addNewWarrantyPlan(plan) }
                    )
                    3 -> LoanRatesTab(
                        settings = loanSettings,
                        onUpdateSettings = { settings -> viewModel.updateLoanSettings(settings) }
                    )
                    4 -> TransactionsManagementTab(
                        transactions = transactions,
                        onApprove = { txnId -> viewModel.approveTransaction(txnId) },
                        onReject = { txnId -> viewModel.rejectTransaction(txnId) }
                    )
                }
            }
        }

        // Floating Banner Notification for Admin
        if (pendingTxn != null) {
            val isSoundMuted by viewModel.isSoundMuted.collectAsState()
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .clickable { viewModel.muteAlertSound() }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (isSoundMuted) {
                                    viewModel.isSoundMuted.value = false
                                    viewModel.startAlertSound()
                                } else {
                                    viewModel.muteAlertSound()
                                }
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = if (isSoundMuted) Icons.Default.NotificationsOff else Icons.Default.NotificationsActive,
                                contentDescription = if (isSoundMuted) "Unmute alert" else "Mute alert",
                                tint = if (isSoundMuted) Color.Gray else Color(0xFFEA580C),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = "🔔 नवीन पेमेंट विनंती! (₹${pendingTxn.amount.toInt()})",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E),
                                fontSize = 12.sp
                            )
                            Text(
                                text = "${pendingTxn.customerName} - ${pendingTxn.type}",
                                fontSize = 11.sp,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "🔇 आवाज बंद करण्यासाठी येथे क्लिक करा / Click here to mute",
                                fontSize = 9.sp,
                                color = Color(0xFFEA580C),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        TextButton(
                            onClick = { viewModel.rejectTransaction(pendingTxn.id) },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                        ) {
                            Text("रद्द", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { viewModel.approveTransaction(pendingTxn.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text("मंजूर", fontSize = 11.sp, color = Color.White)
                        }
                    }
                }
            }
        }

        if (showChangePasswordDialog) {
            var newPassInput by remember { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = { showChangePasswordDialog = false },
                title = { Text("ऍडमिन पासवर्ड बदला / Change Admin Password", fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("नवीन पासवर्ड टाका (किमान ६ अंक):", fontSize = 12.sp, color = Color.Gray)
                        OutlinedTextField(
                            value = newPassInput,
                            onValueChange = { newPassInput = it },
                            placeholder = { Text("उदा. 123456") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newPassInput.isNotBlank()) {
                                viewModel.changeAdminPassword(
                                    newPassInput,
                                    onSuccess = {
                                        android.widget.Toast.makeText(context, "ऍडमिन पासवर्ड यशस्वीरीत्या बदलला!", android.widget.Toast.LENGTH_SHORT).show()
                                        showChangePasswordDialog = false
                                    },
                                    onFailure = { err ->
                                        android.widget.Toast.makeText(context, "त्रुटी: $err", android.widget.Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) {
                        Text("जतन करा / Save", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showChangePasswordDialog = false }) {
                        Text("रद्द करा / Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun UserManagementTab(
    users: List<UserProfile>,
    onUpdateUser: (String, Int, Boolean, Double, Double, String) -> Unit
) {
    var editingUser by remember { mutableStateOf<UserProfile?>(null) }

    if (editingUser != null) {
        val user = editingUser!!
        var score by remember { mutableStateOf(user.creditScore.toString()) }
        var isPayLaterActive by remember { mutableStateOf(user.payLaterActive) }
        var walletBalance by remember { mutableStateOf(user.walletBalance.toString()) }
        var payLaterDue by remember { mutableStateOf(user.payLaterDue.toString()) }
        var autoPaymentLink by remember { mutableStateOf(user.autoPaymentLink) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { editingUser = null }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "संपादन: ${user.name}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("ईमेल: ${user.email}", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                    Text("मोबाईल: ${user.phone}", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Credit Score input
                    OutlinedTextField(
                        value = score,
                        onValueChange = { score = it },
                        label = { Text("क्रेडिट स्कोर (Credit Score)", color = Color.White.copy(alpha = 0.7f)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFF59E0B)
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Wallet Balance input
                    OutlinedTextField(
                        value = walletBalance,
                        onValueChange = { walletBalance = it },
                        label = { Text("वॉलेट शिल्लक (Wallet Balance ₹)", color = Color.White.copy(alpha = 0.7f)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFF59E0B)
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Pay Later Due input
                    OutlinedTextField(
                        value = payLaterDue,
                        onValueChange = { payLaterDue = it },
                        label = { Text("पेलेटर थकबाकी (Pay Later Due ₹)", color = Color.White.copy(alpha = 0.7f)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFF59E0B)
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Auto Payment Activation Link
                    OutlinedTextField(
                        value = autoPaymentLink,
                        onValueChange = { autoPaymentLink = it },
                        label = { Text("ऑटो पेमेंट लिंक (Auto Payment Enrollment Link)", color = Color.White.copy(alpha = 0.7f)) },
                        placeholder = { Text("उदा. https://pay.phonepe.com/...", color = Color.White.copy(alpha = 0.4f)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFF59E0B)
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    // Pay Later Status Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("पेलेटर लिमिट सक्रिय करा (Activate Pay Later)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("याद्वारे युझरचे ५,००० लिमिट ॲक्टिव्हेट होईल", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                        }
                        Switch(
                            checked = isPayLaterActive,
                            onCheckedChange = { isPayLaterActive = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFFF59E0B),
                                checkedTrackColor = Color(0xFFF59E0B).copy(alpha = 0.4f)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val parsedScore = score.toIntOrNull() ?: user.creditScore
                            val parsedWallet = walletBalance.toDoubleOrNull() ?: user.walletBalance
                            val parsedDue = payLaterDue.toDoubleOrNull() ?: user.payLaterDue
                            onUpdateUser(user.email, parsedScore, isPayLaterActive, parsedWallet, parsedDue, autoPaymentLink)
                            editingUser = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("बदल जतन करा / Save Profile", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (users.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("कोणताही युझर नोंदणीकृत नाही / No Users Found", color = Color.White.copy(alpha = 0.6f))
                    }
                }
            } else {
                items(users) { user ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = user.name,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "मोबाईल / Mobile: ${user.phone}",
                                    color = Color(0xFFF59E0B),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "ईमेल / Email: ${user.email}",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 11.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text(
                                        text = "क्रेडिट: ${user.creditScore}",
                                        color = if (user.creditScore >= 700) Color(0xFF10B981) else Color(0xFFEF4444),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "वॉलेट: ₹${user.walletBalance.toInt()}",
                                        color = Color(0xFFF59E0B),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "पेलेटर: " + (if (user.payLaterActive) "सक्रिय" else "बंद"),
                                        color = if (user.payLaterActive) Color(0xFF10B981) else Color.White.copy(alpha = 0.5f),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val ctx = androidx.compose.ui.platform.LocalContext.current
                                
                                // WhatsApp Chat
                                IconButton(
                                    onClick = {
                                        val whatsappText = "नमस्कार ${user.name}, आरुषी इलेक्ट्रॉनिक्स व्यवस्थापनाकडून संपर्क साधण्यात आला आहे."
                                        try {
                                            val intent = android.content.Intent(
                                                android.content.Intent.ACTION_VIEW,
                                                android.net.Uri.parse("https://api.whatsapp.com/send?phone=91${user.phone}&text=${java.net.URLEncoder.encode(whatsappText, "UTF-8")}")
                                            )
                                            ctx.startActivity(intent)
                                        } catch (e: Exception) {
                                            android.widget.Toast.makeText(ctx, "WhatsApp उघडण्यास अपयश", android.widget.Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Chat,
                                        contentDescription = "WhatsApp",
                                        tint = Color(0xFF25D366),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                // Phone Call
                                IconButton(
                                    onClick = {
                                        try {
                                            val intent = android.content.Intent(
                                                android.content.Intent.ACTION_DIAL,
                                                android.net.Uri.parse("tel:${user.phone}")
                                            )
                                            ctx.startActivity(intent)
                                        } catch (e: Exception) {
                                            android.widget.Toast.makeText(ctx, "कॉल डायल करण्यास अपयश", android.widget.Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Phone,
                                        contentDescription = "Call",
                                        tint = Color(0xFF3B82F6),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                // Reset Password
                                IconButton(
                                    onClick = {
                                        try {
                                            com.google.firebase.auth.FirebaseAuth.getInstance().sendPasswordResetEmail(user.email)
                                                .addOnSuccessListener {
                                                    android.widget.Toast.makeText(ctx, "पासवर्ड रिसेट ईमेल पाठवला!", android.widget.Toast.LENGTH_SHORT).show()
                                                    
                                                    // Also prefill WhatsApp message to user
                                                    val whatsappResetText = "नमस्कार ${user.name}, आरुषी मल्टिसर्व्हिसेस कडून आपले खाते पासवर्ड बदलण्यासाठीचा ईमेल (${user.email}) पाठवला आहे. कृपया तपासा."
                                                    try {
                                                        val intent = android.content.Intent(
                                                            android.content.Intent.ACTION_VIEW,
                                                            android.net.Uri.parse("https://api.whatsapp.com/send?phone=91${user.phone}&text=${java.net.URLEncoder.encode(whatsappResetText, "UTF-8")}")
                                                        )
                                                        ctx.startActivity(intent)
                                                    } catch (e: Exception) {}
                                                }
                                                .addOnFailureListener { e ->
                                                    android.widget.Toast.makeText(ctx, "त्रुटी: ${e.localizedMessage}", android.widget.Toast.LENGTH_LONG).show()
                                                }
                                        } catch (e: Exception) {
                                            android.widget.Toast.makeText(ctx, "त्रुटी: ${e.localizedMessage}", android.widget.Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VpnKey,
                                        contentDescription = "Reset User Password",
                                        tint = Color(0xFFEF4444),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { editingUser = user },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit User", tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RechargePlansTab(
    plans: List<RechargePlan>,
    onUpdatePlan: (RechargePlan) -> Unit,
    onAddPlan: (RechargePlan) -> Unit,
    onAddHandset: (Handset) -> Unit
) {
    var editingPlan by remember { mutableStateOf<RechargePlan?>(null) }
    var showAddPlanDialog by remember { mutableStateOf(false) }
    var showAddHandsetDialog by remember { mutableStateOf(false) }

    // Add Plan State
    var addPrice by remember { mutableStateOf("") }
    var addValidity by remember { mutableStateOf("") }
    var addData by remember { mutableStateOf("") }
    var addDesc by remember { mutableStateOf("") }
    var addCategory by remember { mutableStateOf("Popular") }
    var addOperator by remember { mutableStateOf("Jio") }

    // Add Handset State
    var hsName by remember { mutableStateOf("") }
    var hsPrice by remember { mutableStateOf("") }
    var hsDesc by remember { mutableStateOf("") }
    var hsOperator by remember { mutableStateOf("Jio") }

    if (editingPlan != null) {
        val plan = editingPlan!!
        var price by remember { mutableStateOf(plan.price.toString()) }
        var desc by remember { mutableStateOf(plan.description) }

        AlertDialog(
            onDismissRequest = { editingPlan = null },
            title = { Text("प्लॅन संपादित करा / Edit Plan", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("श्रेणी (Category): ${plan.category}", fontSize = 12.sp)
                    Text("वैधता (Validity): ${plan.validity} | डेटा (Data): ${plan.data}", fontSize = 12.sp)
                    
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("किंमत (Price ₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("वर्णन (Description)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val parsedPrice = price.toDoubleOrNull() ?: plan.price
                        onUpdatePlan(plan.copy(price = parsedPrice, description = desc))
                        editingPlan = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))
                ) {
                    Text("जतन करा / Save", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { editingPlan = null }) {
                    Text("रद्द करा / Cancel")
                }
            }
        )
    }

    if (showAddPlanDialog) {
        AlertDialog(
            onDismissRequest = { showAddPlanDialog = false },
            title = { Text("नवीन रिचार्ज प्लॅन जोडा", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    OutlinedTextField(
                        value = addPrice,
                        onValueChange = { addPrice = it },
                        label = { Text("किंमत / Price (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = addValidity,
                        onValueChange = { addValidity = it },
                        label = { Text("वैधता / Validity (e.g. 28 Days)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = addData,
                        onValueChange = { addData = it },
                        label = { Text("डेटा / Data (e.g. 1.5 GB/Day)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = addDesc,
                        onValueChange = { addDesc = it },
                        label = { Text("प्लॅन माहिती / Description") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("ऑपरेटर निवडा / Operator:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("Jio", "Airtel", "Vi", "BSNL").forEach { op ->
                            val isSel = addOperator == op
                            Button(
                                onClick = { addOperator = op },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSel) Color(0xFFF59E0B) else Color.Gray,
                                    contentColor = if (isSel) Color.Black else Color.White
                                ),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(op, fontSize = 11.sp, maxLines = 1)
                            }
                        }
                    }

                    Text("कॅटेगरी निवडा / Category:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    val categories = listOf("Popular", "True 5G Unlimited", "Top Up", "Yearly", "Jio Phone", "Jio Bharat")
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        categories.chunked(3).forEach { rowItems ->
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                rowItems.forEach { cat ->
                                    val isSel = addCategory == cat
                                    Button(
                                        onClick = { addCategory = cat },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSel) Color(0xFFF59E0B) else Color.Gray,
                                            contentColor = if (isSel) Color.Black else Color.White
                                        ),
                                        modifier = Modifier.weight(1f),
                                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp)
                                    ) {
                                        Text(cat, fontSize = 10.sp, maxLines = 1)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val priceNum = addPrice.toDoubleOrNull() ?: 0.0
                        if (priceNum > 0 && addValidity.isNotEmpty()) {
                            onAddPlan(
                                RechargePlan(
                                    id = "plan_${System.currentTimeMillis()}",
                                    price = priceNum,
                                    validity = addValidity,
                                    data = addData,
                                    description = addDesc,
                                    category = addCategory,
                                    operator = addOperator,
                                    isLoan = false
                                )
                            )
                            showAddPlanDialog = false
                            addPrice = ""; addValidity = ""; addData = ""; addDesc = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))
                ) {
                    Text("जोडा / Add", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddPlanDialog = false }) { Text("रद्द करा") }
            }
        )
    }

    if (showAddHandsetDialog) {
        AlertDialog(
            onDismissRequest = { showAddHandsetDialog = false },
            title = { Text("नवीन मोबाईल हँडसेट जोडा", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = hsName,
                        onValueChange = { hsName = it },
                        label = { Text("नाव / Handset Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = hsPrice,
                        onValueChange = { hsPrice = it },
                        label = { Text("किंमत / Price (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = hsDesc,
                        onValueChange = { hsDesc = it },
                        label = { Text("माहिती / Description") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("ऑपरेटर निवडा / Operator:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("Jio", "Airtel", "Vi", "BSNL").forEach { op ->
                            val isSel = hsOperator == op
                            Button(
                                onClick = { hsOperator = op },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSel) Color(0xFFF59E0B) else Color.Gray,
                                    contentColor = if (isSel) Color.Black else Color.White
                                ),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(op, fontSize = 11.sp, maxLines = 1)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val priceNum = hsPrice.toDoubleOrNull() ?: 0.0
                        if (hsName.isNotEmpty() && priceNum > 0) {
                            onAddHandset(
                                Handset(
                                    id = "hs_${System.currentTimeMillis()}",
                                    name = hsName,
                                    price = priceNum,
                                    description = hsDesc,
                                    operator = hsOperator
                                )
                            )
                            showAddHandsetDialog = false
                            hsName = ""; hsPrice = ""; hsDesc = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))
                ) {
                    Text("जोडा / Add", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddHandsetDialog = false }) { Text("रद्द करा") }
            }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { showAddPlanDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("➕ प्लॅन जोडा (Add Plan)", color = Color.White, fontSize = 11.sp)
                }
                Button(
                    onClick = { showAddHandsetDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("📱 हँडसेट जोडा (Handset)", color = Color.White, fontSize = 11.sp)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        items(plans) { plan ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "₹${plan.price}",
                                color = Color(0xFFF59E0B),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.White.copy(alpha = 0.15f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("${plan.operator} - ${plan.category}", fontSize = 10.sp, color = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("वैधता: ${plan.validity} | डेटा: ${plan.data}", color = Color.White, fontSize = 13.sp)
                        Text(plan.description, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                    }
                    IconButton(onClick = { editingPlan = plan }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Plan", tint = Color(0xFFF59E0B))
                    }
                }
            }
        }
    }
}

@Composable
fun WarrantyPlansTab(
    protectionPlans: List<ProtectionPlan>,
    warrantyPlans: List<WarrantyPlanOption>,
    onUpdateProtection: (ProtectionPlan) -> Unit,
    onUpdateWarranty: (WarrantyPlanOption) -> Unit,
    onAddProtection: (ProtectionPlan) -> Unit,
    onAddWarranty: (WarrantyPlanOption) -> Unit
) {
    var editingProtection by remember { mutableStateOf<ProtectionPlan?>(null) }
    var editingWarranty by remember { mutableStateOf<WarrantyPlanOption?>(null) }

    var showAddProtection by remember { mutableStateOf(false) }
    var showAddWarranty by remember { mutableStateOf(false) }

    // State for Add Protection
    var addProtTitle by remember { mutableStateOf("") }
    var addProtPrice by remember { mutableStateOf("") }
    var addProtDesc by remember { mutableStateOf("") }
    var addProtDuration by remember { mutableStateOf("12") }

    // State for Add Warranty
    var addWarrTitle by remember { mutableStateOf("") }
    var addWarrPrice by remember { mutableStateOf("") }
    var addWarrDuration by remember { mutableStateOf("12") }

    if (editingProtection != null) {
        val plan = editingProtection!!
        var title by remember { mutableStateOf(plan.title) }
        var price by remember { mutableStateOf(plan.price.toString()) }
        var desc by remember { mutableStateOf(plan.description) }

        AlertDialog(
            onDismissRequest = { editingProtection = null },
            title = { Text("सुरक्षा प्लॅन संपादित करा / Edit Combo", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("नाव / Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("किंमत (Price ₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("वर्णन / Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val parsedPrice = price.toDoubleOrNull() ?: plan.price
                        onUpdateProtection(plan.copy(title = title, price = parsedPrice, description = desc))
                        editingProtection = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))
                ) {
                    Text("जतन करा / Save", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { editingProtection = null }) {
                    Text("रद्द करा / Cancel")
                }
            }
        )
    }

    if (editingWarranty != null) {
        val plan = editingWarranty!!
        var title by remember { mutableStateOf(plan.title) }
        var price by remember { mutableStateOf(plan.price.toString()) }

        AlertDialog(
            onDismissRequest = { editingWarranty = null },
            title = { Text("वॉरंटी प्लॅन संपादित करा / Edit Warranty", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("नाव / Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("किंमत (Price ₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val parsedPrice = price.toDoubleOrNull() ?: plan.price
                        onUpdateWarranty(plan.copy(title = title, price = parsedPrice))
                        editingWarranty = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))
                ) {
                    Text("जतन करा / Save", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { editingWarranty = null }) {
                    Text("रद्द करा / Cancel")
                }
            }
        )
    }

    if (showAddProtection) {
        AlertDialog(
            onDismissRequest = { showAddProtection = false },
            title = { Text("नवीन सुरक्षा प्लॅन जोडा", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = addProtTitle,
                        onValueChange = { addProtTitle = it },
                        label = { Text("नाव / Title (e.g. फोल्डर सुरक्षा)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = addProtPrice,
                        onValueChange = { addProtPrice = it },
                        label = { Text("किंमत / Price (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = addProtDesc,
                        onValueChange = { addProtDesc = it },
                        label = { Text("माहिती / Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = addProtDuration,
                        onValueChange = { addProtDuration = it },
                        label = { Text("कालावधी / Duration (Months)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val priceNum = addProtPrice.toDoubleOrNull() ?: 0.0
                        val dur = addProtDuration.toIntOrNull() ?: 12
                        if (addProtTitle.isNotEmpty() && priceNum > 0) {
                            onAddProtection(
                                ProtectionPlan(
                                    id = "p_${System.currentTimeMillis()}",
                                    title = addProtTitle,
                                    price = priceNum,
                                    description = addProtDesc,
                                    durationMonths = dur
                                )
                            )
                            showAddProtection = false
                            addProtTitle = ""; addProtPrice = ""; addProtDesc = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))
                ) {
                    Text("जोडा / Add", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddProtection = false }) { Text("रद्द करा") }
            }
        )
    }

    if (showAddWarranty) {
        AlertDialog(
            onDismissRequest = { showAddWarranty = false },
            title = { Text("नवीन वॉरंटी योजना जोडा", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = addWarrTitle,
                        onValueChange = { addWarrTitle = it },
                        label = { Text("नाव / Title (e.g. १८ महिने वॉरंटी)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = addWarrPrice,
                        onValueChange = { addWarrPrice = it },
                        label = { Text("किंमत / Price (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = addWarrDuration,
                        onValueChange = { addWarrDuration = it },
                        label = { Text("कालावधी / Duration (Months)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val priceNum = addWarrPrice.toDoubleOrNull() ?: 0.0
                        val dur = addWarrDuration.toIntOrNull() ?: 12
                        if (addWarrTitle.isNotEmpty() && priceNum > 0) {
                            onAddWarranty(
                                WarrantyPlanOption(
                                    id = "w_${System.currentTimeMillis()}",
                                    title = addWarrTitle,
                                    durationMonths = dur,
                                    price = priceNum
                                )
                            )
                            showAddWarranty = false
                            addWarrTitle = ""; addWarrPrice = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))
                ) {
                    Text("जोडा / Add", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddWarranty = false }) { Text("रद्द करा") }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { showAddProtection = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("➕ सुरक्षा जोडा", color = Color.White, fontSize = 11.sp)
            }
            Button(
                onClick = { showAddWarranty = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("➕ वॉरंटी जोडा", color = Color.White, fontSize = 11.sp)
            }
        }

        // Section 1: Protection Plans (Combo/All-in-one protection)
        Text(
            text = "🛡️ कॉम्बो आणि स्क्रीन सुरक्षा विमा योजना:",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color(0xFFF59E0B)
        )
        protectionPlans.forEach { plan ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(plan.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("किंमत: ₹${plan.price}", color = Color(0xFFF59E0B), fontWeight = FontWeight.Medium, fontSize = 13.sp)
                        Text(plan.description, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                    }
                    IconButton(onClick = { editingProtection = plan }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Protection", tint = Color(0xFFF59E0B))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Section 2: Standard Warranty Options
        Text(
            text = "📅 स्टॅंडर्ड वॉरंटी प्लॅन्स (Extended Warranty):",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color(0xFFF59E0B)
        )
        warrantyPlans.forEach { plan ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(plan.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("कालावधी: ${plan.durationMonths} महिने", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                        Text("किंमत: ₹${plan.price}", color = Color(0xFFF59E0B), fontWeight = FontWeight.Medium, fontSize = 13.sp)
                    }
                    IconButton(onClick = { editingWarranty = plan }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Warranty", tint = Color(0xFFF59E0B))
                    }
                }
            }
        }
    }
}

@Composable
fun LoanRatesTab(
    settings: LoanSettings,
    onUpdateSettings: (LoanSettings) -> Unit
) {
    var interest by remember { mutableStateOf(settings.interestRatePercent.toString()) }
    var processingFeeUnder100 by remember { mutableStateOf(settings.processingFeeUnder100.toString()) }
    var processingFeeOver100 by remember { mutableStateOf(settings.processingFeeOver100.toString()) }
    var lateFee by remember { mutableStateOf(settings.lateFeePerDay.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("📈 व्याज आणि प्रोसेसिंग फी दर व्यवस्थापन", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFF59E0B))
        
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                
                OutlinedTextField(
                    value = interest,
                    onValueChange = { interest = it },
                    label = { Text("व्याजदर (Interest Rate %)", color = Color.White.copy(alpha = 0.7f)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = processingFeeUnder100,
                    onValueChange = { processingFeeUnder100 = it },
                    label = { Text("शंभर रुपयांच्या आतील प्रोसेसिंग फी (Processing Fee < ₹100)", color = Color.White.copy(alpha = 0.7f)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = processingFeeOver100,
                    onValueChange = { processingFeeOver100 = it },
                    label = { Text("शंभर रुपयांच्या वरील प्रोसेसिंग फी (Processing Fee >= ₹100)", color = Color.White.copy(alpha = 0.7f)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = lateFee,
                    onValueChange = { lateFee = it },
                    label = { Text("प्रति दिवस विलंब शुल्क (Late Fee Per Day ₹)", color = Color.White.copy(alpha = 0.7f)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val settingsObj = LoanSettings(
                            interestRatePercent = interest.toDoubleOrNull() ?: settings.interestRatePercent,
                            processingFeeUnder100 = processingFeeUnder100.toDoubleOrNull() ?: settings.processingFeeUnder100,
                            processingFeeOver100 = processingFeeOver100.toDoubleOrNull() ?: settings.processingFeeOver100,
                            lateFeePerDay = lateFee.toDoubleOrNull() ?: settings.lateFeePerDay
                        )
                        onUpdateSettings(settingsObj)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("बदल जतन करा / Save Settings", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun TransactionsManagementTab(
    transactions: List<com.example.data.Transaction>,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit
) {
    val pendingList = transactions.filter { it.status == "PENDING" || it.status == "PENDING_ADMIN_APPROVAL" }.sortedByDescending { it.timestamp }
    val historyList = transactions.filter { it.status != "PENDING" && it.status != "PENDING_ADMIN_APPROVAL" }.sortedByDescending { it.timestamp }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Pending Actions Section
        item {
            Text(
                text = "⏳ प्रलंबित व्यवहार / Pending Actions (${pendingList.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFFF59E0B)
            )
        }

        if (pendingList.isEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E).copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "कोणतीही नवीन व्यवहार विनंती नाही",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        } else {
            items(pendingList) { txn ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2E241E)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = txn.customerName,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "मोबाईल: ${txn.customerPhone}",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 11.sp
                                )
                            }
                            Text(
                                text = "₹${txn.amount.toInt()}",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF59E0B),
                                fontSize = 18.sp
                            )
                        }
                        
                        Divider(color = Color.White.copy(alpha = 0.1f), modifier = Modifier.padding(vertical = 8.dp))
                        
                        Text(
                            text = "प्रकार: ${txn.type} | ${txn.details}",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )
                        
                        Spacer(modifier = Modifier.height(10.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = { onReject(txn.id) },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                            ) {
                                Text("Reject (रद्द करा)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            Button(
                                onClick = { onApprove(txn.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Approve (मंजूर करा)", fontSize = 12.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }

        // History Section
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "📜 व्यवहार इतिहास / History (${historyList.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }

        if (historyList.isEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E).copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "इतिहास कोरा आहे",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        } else {
            items(historyList) { txn ->
                val isSuccess = txn.status == "SUCCESSFUL" || txn.status == "APPROVED"
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = txn.customerName,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = txn.type,
                                    color = Color.White.copy(alpha = 0.5f),
                                    fontSize = 10.sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "₹${txn.amount.toInt()}",
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSuccess) Color(0xFF10B981) else Color.Red,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = txn.status,
                                    fontSize = 9.sp,
                                    color = if (isSuccess) Color(0xFF10B981).copy(alpha = 0.8f) else Color.Red.copy(alpha = 0.8f),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = txn.details,
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}
