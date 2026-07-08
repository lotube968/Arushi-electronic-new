package com.example

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Speed
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.TextButton
import androidx.compose.material3.CircularProgressIndicator
import kotlinx.coroutines.delay
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.Roofing
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.BookingEntity
import com.example.data.RepairViewModel
import com.example.data.WarrantyItemEntity
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import com.example.ui.AuthScreen
import com.example.ui.AdminPanelScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: RepairViewModel = viewModel()
                val currentUserProfile by viewModel.currentUserProfile.collectAsState()
                val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsState()
                val isLoading by viewModel.isLoading.collectAsState()
                val loadingText by viewModel.loadingText.collectAsState()
                
                var showSplash by remember { mutableStateOf(true) }

                Box(modifier = Modifier.fillMaxSize()) {
                    if (showSplash) {
                        AnimatedAarushiLogoSplash(
                            onFinished = { showSplash = false }
                        )
                    } else {
                        when {
                            isAdminLoggedIn -> {
                                AdminPanelScreen(
                                    viewModel = viewModel,
                                    onLogout = { viewModel.signOut() }
                                )
                            }
                            currentUserProfile == null -> {
                                AuthScreen(
                                    viewModel = viewModel,
                                    onAuthSuccess = {}
                                )
                            }
                            else -> {
                                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                    MainRepairDashboard(
                                        modifier = Modifier.padding(innerPadding),
                                        viewModel = viewModel
                                    )
                                }
                            }
                        }
                    }
                    
                    ServiceLoadingOverlay(isLoading = isLoading, text = loadingText)
                }
            }
        }
    }
}

@Composable
fun AnimatedAarushiLogoSplash(
    onFinished: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "splash")
    
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val haloScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "halo"
    )

    var startAnimations by remember { mutableStateOf(false) }
    val cardScale by animateFloatAsState(
        targetValue = if (startAnimations) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_scale"
    )
    
    val textAlpha by animateFloatAsState(
        targetValue = if (startAnimations) 1f else 0f,
        animationSpec = tween(1000, delayMillis = 400),
        label = "text_alpha"
    )

    var loadingProgress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        startAnimations = true
        for (i in 1..100) {
            delay(20)
            loadingProgress = i / 100f
        }
        delay(300)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFE5F1FF),
                        Color(0xFFCBE2FD)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFF2F80ED).copy(alpha = 0.05f),
                radius = 120.dp.toPx(),
                center = Offset(size.width * 0.15f, size.height * 0.2f)
            )
            drawCircle(
                color = Color(0xFF1B498F).copy(alpha = 0.04f),
                radius = 160.dp.toPx(),
                center = Offset(size.width * 0.85f, size.height * 0.8f)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .scale(cardScale)
                    .size(310.dp)
                    .shadow(
                        elevation = 16.dp,
                        shape = CircleShape,
                        ambientColor = Color(0xFF2F80ED).copy(alpha = 0.3f),
                        spotColor = Color(0xFF2F80ED).copy(alpha = 0.5f)
                    )
                    .background(Color.White, shape = CircleShape)
                    .border(
                        width = 4.dp,
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color(0xFF1B498F),
                                Color(0xFFF26522),
                                Color(0xFF2F80ED),
                                Color(0xFF1B498F)
                            )
                        ),
                        shape = CircleShape
                    )
                    .border(
                        width = 8.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFF005691).copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(rotationAngle)
                        .scale(haloScale)
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Handyman,
                            contentDescription = null,
                            tint = Color(0xFF005691).copy(alpha = 0.15f),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        Icon(
                            imageVector = Icons.Default.Smartphone,
                            contentDescription = null,
                            tint = Color(0xFF005691).copy(alpha = 0.15f),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .graphicsLayer { alpha = textAlpha }
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Text(
                        text = "आरुषी",
                        fontSize = 46.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFD32F2F),
                        textAlign = TextAlign.Center,
                        lineHeight = 48.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "इलेक्ट्रॉनिक ॲण्ड\nमोबाईल रिपेरिंग",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.width(40.dp).height(2.dp).background(Color(0xFF64748B)))
                        Icon(
                            imageVector = Icons.Default.ElectricBolt,
                            contentDescription = null,
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(16.dp).padding(horizontal = 2.dp)
                        )
                        Box(modifier = Modifier.width(40.dp).height(2.dp).background(Color(0xFF64748B)))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFE5F1FF))
                                .padding(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Handyman,
                                contentDescription = null,
                                tint = Color(0xFF2F80ED),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "मोबाईल",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2F80ED)
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2F80ED))
                                .border(2.dp, Color.White, CircleShape)
                                .padding(4.dp)
                        ) {
                            Text(
                                text = "सर्व प्रकारच्या\nइलेक्ट्रॉनिक वस्तू\nरिपेअर करून\nमिळेल.",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                lineHeight = 9.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .clip(RoundedCornerShape(bottomStart = 110.dp, bottomEnd = 110.dp, topStart = 12.dp, topEnd = 12.dp))
                            .background(Color(0xFF002D62)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF25D366)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "W",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "9860856702",
                                color = Color(0xFFFFEB3B),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "विश्वासार्ह सेवा... योग्य दरात...",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer { alpha = textAlpha }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.width(180.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    progress = { loadingProgress },
                    color = Color(0xFF2F80ED),
                    trackColor = Color(0xFFE2E8F0),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(100))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "दुकान उघडत आहे... / Loading...",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }
        }
    }
}

data class ServiceCategory(
    val id: String,
    val titleMr: String,
    val titleEn: String,
    val icon: ImageVector,
    val descriptionMr: String,
    val descriptionEn: String,
    val color: Color
)

data class SliderImage(
    val url: String,
    val captionEn: String,
    val captionMr: String
)

enum class HistoryType {
    RECHARGE, BOOKING, WARRANTY
}

data class UnifiedHistoryItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val detail: String,
    val timestamp: Long,
    val type: HistoryType,
    val status: String,
    val rawBooking: BookingEntity? = null,
    val rawWarranty: WarrantyItemEntity? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRepairDashboard(
    modifier: Modifier = Modifier,
    viewModel: RepairViewModel = viewModel()
) {
    val bookings by viewModel.bookings.collectAsState()
    val warrantyItems by viewModel.warrantyItems.collectAsState()
    val isFirebaseConnected by viewModel.isFirebaseConnected.collectAsState()

    var searchQuery by mutableStateOf("")
    var selectedService by mutableStateOf<ServiceCategory?>(null)
    
    // Bottom Navigation Tab: 0: Home, 1: History, 2: My Investments
    var currentBottomTab by remember { mutableStateOf(0) }
    
    val userProfileState by viewModel.currentUserProfile.collectAsState()
    val userProfile = userProfileState ?: com.example.data.UserProfile()

    var walletBalance by remember { mutableStateOf(500.0) }
    var creditScore by remember { mutableStateOf(720) }
    var payLaterDue by remember { mutableStateOf(0.0) }
    var userName by remember { mutableStateOf("") }
    var payLaterActive by remember { mutableStateOf(false) }
    var autoPaymentLink by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(userProfileState) {
        userProfileState?.let {
            walletBalance = it.walletBalance
            creditScore = it.creditScore
            payLaterDue = it.payLaterDue
            userName = it.name
            payLaterActive = it.payLaterActive
            autoPaymentLink = it.autoPaymentLink
        }
    }

    LaunchedEffect(walletBalance, creditScore, payLaterDue) {
        userProfileState?.let { profile ->
            if (walletBalance != profile.walletBalance) {
                viewModel.updateUserWalletBalance(profile.email, walletBalance)
            }
            if (payLaterDue != profile.payLaterDue) {
                viewModel.updateUserPayLaterDue(profile.email, payLaterDue)
            }
        }
    }
    var showAddMoneyDialog by remember { mutableStateOf(false) }
    var showSendMoneyDialog by remember { mutableStateOf(false) }
    var showRepaymentReminderDialog by remember { mutableStateOf(true) }
    
    var showRealtimePaymentQRDialog by remember { mutableStateOf(false) }
    var realtimePaymentQRAmount by remember { mutableStateOf(0.0) }
    var realtimePaymentQRPurpose by remember { mutableStateOf("") }
    var realtimePaymentQRDetails by remember { mutableStateOf("") }
    var onRealtimePaymentQRSuccess by remember { mutableStateOf<() -> Unit>({}) }

    // Sliding Banner States
    var sliderImages by remember {
        mutableStateOf(
            listOf(
                SliderImage(
                    "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?auto=format&fit=crop&w=800&q=80",
                    "Mobile Repair & Glass Replacement",
                    "मोबाईल रिपेअरिंग आणि ग्लास बदलणे"
                ),
                SliderImage(
                    "https://images.unsplash.com/photo-1546054454-aa26e2b734c7?auto=format&fit=crop&w=800&q=80",
                    "Quick Mobile Recharges & Loans",
                    "मोबाईल रिचार्ज आणि रिचार्ज लोन"
                ),
                SliderImage(
                    "https://images.unsplash.com/photo-1581092160607-ee22621dd758?auto=format&fit=crop&w=800&q=80",
                    "Professional Appliance Repairs",
                    "सर्व प्रकारची घरगुती इलेक्ट्रॉनिक दुरुस्ती"
                )
            )
        )
    }
    var currentSlideIndex by remember { mutableStateOf(0) }
    var showUploadBannerDialog by remember { mutableStateOf(false) }

    // Auto-scroll banner
    LaunchedEffect(currentSlideIndex, sliderImages.size) {
        if (sliderImages.isNotEmpty()) {
            delay(4000)
            currentSlideIndex = (currentSlideIndex + 1) % sliderImages.size
        }
    }

    // Modal dialog trigger states
    var showRechargeDialog by remember { mutableStateOf(false) }
    var showLoanDialog by remember { mutableStateOf(false) }
    var showComboDialog by remember { mutableStateOf(false) }
    var showInvoiceItem by remember { mutableStateOf<WarrantyItemEntity?>(null) }
    var showAddWarrantyDialog by remember { mutableStateOf(false) }

    // Filtered lists for history tabs
    var historyFilterTab by remember { mutableStateOf(0) } // 0: All, 1: Recharges, 2: Bookings, 3: Warranties

    // Predefined service categories in Marathi & English
    val services = remember {
        listOf(
            ServiceCategory(
                id = "RECHARGE",
                titleMr = "रिचार्ज व रिचार्ज लोन",
                titleEn = "Recharge & Mobile Loan",
                icon = Icons.Default.PhoneAndroid,
                descriptionMr = "जलद रिचार्ज व तात्काळ मोबाईल रिचार्ज लोन सेवा",
                descriptionEn = "Quick mobile recharges and instant emergency recharge loans",
                color = Color(0xFF2F80ED)
            ),
            ServiceCategory(
                id = "ELECTRONIC",
                titleMr = "इलेक्ट्रॉनिक सर्व्हिसेस",
                titleEn = "Electronic Services",
                icon = Icons.Default.Build,
                descriptionMr = "टीव्ही, मिक्सर, इस्त्री आणि घरगुती वस्तू दुरुस्ती",
                descriptionEn = "Repair TV, mixer, iron, and home appliances",
                color = Color(0xFF3B82F6)
            ),
            ServiceCategory(
                id = "COMBO",
                titleMr = "मोबाईल कॉम्बो प्रोटेक्शन",
                titleEn = "Mobile Combo Protection",
                icon = Icons.Default.Security,
                descriptionMr = "मोबाईल स्क्रीन आणि ग्लास प्रोटेक्शन वॉरंटी",
                descriptionEn = "Premium mobile screen and glass replacement protection",
                color = Color(0xFF1E3A8A)
            ),
            ServiceCategory(
                id = "WARRANTY",
                titleMr = "वॉरंटीसह वस्तू",
                titleEn = "Standard Warranty Goods",
                icon = Icons.Default.WorkspacePremium,
                descriptionMr = "स्टॅंडर्ड वॉरंटीसह इलेक्ट्रॉनिक वस्तू खरेदी व नोंदणी",
                descriptionEn = "Purchase and register goods with reliable warranty",
                color = Color(0xFFD97706)
            ),
            ServiceCategory(
                id = "LINE_FITTING",
                titleMr = "होम लाईन फिटिंग",
                titleEn = "Home Line Fitting",
                icon = Icons.Default.FlashOn,
                descriptionMr = "घरगुती इलेक्ट्रical वायरिंग आणि फिटिंग सेवा",
                descriptionEn = "Professional household electrical fitting and wiring",
                color = Color(0xFF2563EB)
            ),
            ServiceCategory(
                id = "PLUMBER",
                titleMr = "प्लंबर सेवा",
                titleEn = "Plumbing Services",
                icon = Icons.Default.Plumbing,
                descriptionMr = "नळ दुरुस्ती, गळती आणि पाईपलाईन फिटिंग",
                descriptionEn = "Tap repair, leakages, and pipeline setup",
                color = Color(0xFF0284C7)
            ),
            ServiceCategory(
                id = "TIN_SHED",
                titleMr = "तीनाचे शेड",
                titleEn = "Tin Shed Fitting",
                icon = Icons.Default.Roofing,
                descriptionMr = "मजबूत पत्र्याचे आणि तीनाचे शेड फिटिंग कामे",
                descriptionEn = "Durable sheet metal and tin roof construction",
                color = Color(0xFF4F46E5)
            )
        )
    }

    val filteredServices = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            services
        } else {
            services.filter {
                it.titleEn.contains(searchQuery, ignoreCase = true) ||
                        it.titleMr.contains(searchQuery, ignoreCase = true) ||
                        it.descriptionEn.contains(searchQuery, ignoreCase = true) ||
                        it.descriptionMr.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "आरुषी इलेक्ट्रॉनिक्स & सर्व्हिसेस",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "Aarushi Electronics & Services",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.signOut() }) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Logout",
                            tint = Color(0xFF2F80ED)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            Column {
                HorizontalDivider(
                    color = Color(0xFFCBD5E1),
                    thickness = 1.dp
                )
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("main_bottom_navigation")
                ) {
                    NavigationBarItem(
                        selected = currentBottomTab == 0,
                        onClick = { currentBottomTab = 0 },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("मुख्यपृष्ठ / Home", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2F80ED),
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = Color(0xFF2F80ED),
                            indicatorColor = Color(0xFFE5F1FF)
                        )
                    )
                    NavigationBarItem(
                        selected = currentBottomTab == 1,
                        onClick = { currentBottomTab = 1 },
                        icon = { Icon(Icons.Default.History, contentDescription = "History") },
                        label = { Text("इतिहास / History", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2F80ED),
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = Color(0xFF2F80ED),
                            indicatorColor = Color(0xFFE5F1FF)
                        )
                    )
                    NavigationBarItem(
                        selected = currentBottomTab == 2,
                        onClick = { currentBottomTab = 2 },
                        icon = { Icon(Icons.Default.WorkspacePremium, contentDescription = "Investments") },
                        label = { Text("गुंतवणूक / Investments", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2F80ED),
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = Color(0xFF2F80ED),
                            indicatorColor = Color(0xFFE5F1FF)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8FAFC))
        ) {
            when (currentBottomTab) {
                0 -> {
                    // HOME SCREEN
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // 1. TOP UNIFIED USER FINANCIAL DASHBOARD CARD (Fixed/Sticky!)
                        val context = androidx.compose.ui.platform.LocalContext.current
                        UserDashboardCard(
                            userName = userName,
                            walletBalance = walletBalance,
                            payLaterDue = payLaterDue,
                            creditScore = creditScore,
                            payLaterActive = payLaterActive,
                            autoPaymentLink = autoPaymentLink,
                            onAddMoneyClick = { showAddMoneyDialog = true },
                            onSendMoneyClick = { showSendMoneyDialog = true },
                            onRepaySuccess = { paidAmount ->
                                walletBalance -= paidAmount
                                payLaterDue -= paidAmount
                            },
                            isFirebaseConnected = isFirebaseConnected,
                            onActivatePayLaterClick = {
                                val profile = userProfileState
                                if (profile != null) {
                                    val whatsappText = "नमस्कार आरुषी मल्टिसर्व्हिसेस, माझे नाव ${profile.name} असून मला माझे पे लेटर लिमिट सक्रिय करायचे आहे. माझा मोबाईल नंबर ${profile.phone} आहे."
                                    val txnId = "TXN_ACTIVATE_${System.currentTimeMillis()}"
                                    val txn = com.example.data.Transaction(
                                        id = txnId,
                                        type = "ACTIVATE_PAY_LATER",
                                        customerEmail = profile.email,
                                        customerName = profile.name,
                                        customerPhone = profile.phone,
                                        details = "Request to activate Pay Later limit of ₹600",
                                        amount = 600.0,
                                        status = "PENDING_ADMIN_APPROVAL",
                                        timestamp = System.currentTimeMillis(),
                                        qrCodeUri = ""
                                    )
                                    viewModel.submitTransaction(txn)
                                    try {
                                        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://api.whatsapp.com/send?phone=919860856702&text=${java.net.URLEncoder.encode(whatsappText, "UTF-8")}"))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        android.widget.Toast.makeText(context, "WhatsApp open failed: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
                                    }
                                    android.widget.Toast.makeText(context, "विनंती पाठवली! ऍडमिन मंजुरी प्रलंबित आहे.", android.widget.Toast.LENGTH_LONG).show()
                                }
                            },
                            onRepayWithQRClick = { amountToPay ->
                                realtimePaymentQRAmount = amountToPay
                                realtimePaymentQRPurpose = "PAY_LATER_REPAY"
                                realtimePaymentQRDetails = "Repay Pay Later loan of ₹$amountToPay"
                                onRealtimePaymentQRSuccess = {
                                    // Managed inside admin approval flow
                                }
                                showRealtimePaymentQRDialog = true
                            },
                            userProfile = userProfileState
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Scrollable bottom area with separate scroll container
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                        ) {
                            // 2. PROMO SLIDING BANNER (REPLACES THE LOCATION BAR)
                        if (sliderImages.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.Black)
                            ) {
                                // Slider Image Loader
                                val currentSlide = sliderImages[currentSlideIndex]
                                AsyncImage(
                                    model = currentSlide.url,
                                    contentDescription = "Promo Banner",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                )

                                // Overlay with Gradient & Text Captions
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    Color.Black.copy(alpha = 0.8f)
                                                )
                                            )
                                        )
                                )

                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = currentSlide.captionMr,
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = currentSlide.captionEn,
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                // Interactive indicator dots
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(12.dp)
                                        .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    sliderImages.forEachIndexed { idx, _ ->
                                        Box(
                                            modifier = Modifier
                                                .size(if (idx == currentSlideIndex) 8.dp else 5.dp)
                                                .clip(CircleShape)
                                                .background(if (idx == currentSlideIndex) Color(0xFF2F80ED) else Color.White.copy(alpha = 0.6f))
                                        )
                                    }
                                }

                                // Simulated upload button overlay
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(12.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.Black.copy(alpha = 0.6f))
                                        .clickable { showUploadBannerDialog = true }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CloudQueue,
                                            contentDescription = "Upload",
                                            tint = Color.White,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Text(
                                            text = "फोटो अपलोड / Upload",
                                            color = Color.White,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        // 3. SEARCH BAR
                        Spacer(modifier = Modifier.height(4.dp))
                        SearchBarCard(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // CATEGORIZED SECTIONS
                        // SECTION 1: मोबाईल रिचार्ज आणि लोन (Mobile Recharge & Loan)
                        CategoryHeader(titleMr = "१. रिचार्ज आणि रिचार्ज लोन सेवा", titleEn = "1. Mobile Recharge & Loans")
                        RechargeSectionBlock(
                            onRechargeClick = { showRechargeDialog = true },
                            onLoanClick = { showLoanDialog = true },
                            bookings = bookings
                        )

                        // SECTION 2: वॉरंटी आणि प्रोटेक्शन (Warranty & Protection)
                        CategoryHeader(titleMr = "२. वॉरंटी आणि प्रोटेक्शन सेवा", titleEn = "2. Warranty & Protection")
                        WarrantySectionBlock(
                            onComboClick = { showComboDialog = true },
                            onStandardClick = { showAddWarrantyDialog = true }
                        )

                        // SECTION 3: इतर इलेक्ट्रॉनिक्स सेवा (Other Electronics Services)
                        CategoryHeader(titleMr = "३. इतर इलेक्ट्रॉनिक्स सेवा", titleEn = "3. Other Services")
                        OtherServicesBlock(
                            services = filteredServices,
                            onServiceClick = { selectedService = it }
                        )

                        // SECTION 4: सक्रिय वॉरंटी आणि कॉम्बो इन्व्हॉइसेस (Active Protection Plans & Invoices)
                        CategoryHeader(titleMr = "४. तुमचे सक्रिय वॉरंटी आणि कॉम्बो इन्व्हॉइसेस", titleEn = "4. Your Active Warranties & Invoices")
                        if (warrantyItems.isEmpty()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Security,
                                        contentDescription = "No active plans",
                                        tint = Color.LightGray,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "सध्या कोणताही सक्रिय वॉरंटी किंवा प्रोटेक्शन प्लॅन नाही.",
                                        fontSize = 11.sp,
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "No active protection or warranty plans. Register one above!",
                                        fontSize = 10.sp,
                                        color = Color.Gray.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                warrantyItems.forEach { item ->
                                    val purchaseTime = item.purchaseDate
                                    val durationMs = item.warrantyDurationMonths.toLong() * 30 * 24 * 60 * 60 * 1000L
                                    val timeElapsed = System.currentTimeMillis() - purchaseTime
                                    val remainingMs = (durationMs - timeElapsed).coerceAtLeast(0L)
                                    val daysLeft = remainingMs / (1000 * 60 * 60 * 24)
                                    
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.White),
                                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                                    Icon(
                                                        imageVector = Icons.Default.Verified,
                                                        contentDescription = "Verified Plan",
                                                        tint = if (daysLeft > 0) Color(0xFF00A859) else Color.Red,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Text(
                                                        text = item.itemName,
                                                        fontSize = 13.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(0xFF1E293B),
                                                        maxLines = 1
                                                    )
                                                }
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(6.dp))
                                                        .background(if (daysLeft > 0) Color(0xFFE6F4EA) else Color(0xFFFCE8E6))
                                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                                ) {
                                                    Text(
                                                        text = if (daysLeft > 0) "${daysLeft} दिवस शिल्लक / Days Left" else "Expired",
                                                        fontSize = 8.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (daysLeft > 0) Color(0xFF137333) else Color(0xFFC5221F)
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "ब्रँड: ${item.brand} | S/N: ${item.serialNumber}",
                                                fontSize = 10.sp,
                                                color = Color.Gray
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Button(
                                                onClick = { showInvoiceItem = item },
                                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9)),
                                                modifier = Modifier.align(Alignment.End),
                                                shape = RoundedCornerShape(8.dp),
                                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                    Icon(
                                                        imageVector = Icons.Default.ReceiptLong,
                                                        contentDescription = "Tax Invoice",
                                                        tint = Color(0xFF475569),
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                    Text(
                                                        text = "टॅक्स इन्व्हॉइस / View Invoice 📄",
                                                        fontSize = 10.sp,
                                                        color = Color(0xFF475569),
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // SECTION 5: क्रेडिट स्कोर रिपोर्ट आणि सिम्युलेटर (Active Credit Score Report at bottom)
                        CategoryHeader(titleMr = "५. क्रेडिट स्कोर रिपोर्ट आणि सिम्युलेटर", titleEn = "5. Credit Score Report & Emulator")
                        CreditScoreReportCard(
                            creditScore = creditScore,
                            onScoreChanged = { newScore -> creditScore = newScore }
                        )

                        Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
                1 -> {
                    // ALL HISTORY TAB
                    UnifiedHistoryScreen(
                        bookings = bookings,
                        warrantyItems = warrantyItems,
                        activeFilter = historyFilterTab,
                        onFilterChange = { historyFilterTab = it },
                        onCancelBooking = { viewModel.cancelBooking(it) },
                        onRemoveWarranty = { viewModel.removeWarranty(it) },
                        onInvoiceClick = { showInvoiceItem = it }
                    )
                }
                2 -> {
                    // MY INVESTMENTS TAB (EXTENDED WARRANTIES & COMBO PLANS)
                    MyInvestmentsScreen(
                        warrantyItems = warrantyItems,
                        onInvoiceClick = { showInvoiceItem = it },
                        onRemoveWarranty = { viewModel.removeWarranty(it) }
                    )
                }
            }
        }

        // --- DIALOGS AND POPUPS ---

        // A. Standard booking popup
        AnimatedVisibility(
            visible = selectedService != null,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            selectedService?.let { service ->
                BookingFormSheet(
                    service = service,
                    onDismiss = { selectedService = null },
                    onSubmit = { name, phone, details ->
                        scope.launch {
                            viewModel.showLoading("सर्व्हिस बुकिंग केली जात आहे... / Registering your service booking...")
                            delay(1200)
                            viewModel.bookService(service.id, name, phone, details)
                            viewModel.hideLoading()
                            selectedService = null
                        }
                    },
                    defaultName = userName
                )
            }
        }

        // Repayment Reminder Dialog (due 4 days before)
        if (showRepaymentReminderDialog && payLaterDue > 0.0) {
            AlertDialog(
                onDismissRequest = { showRepaymentReminderDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = "Reminder Icon", tint = Color.Red)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("रिपेमेंट रिमाइंडर / Due Reminder", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                },
                text = {
                    Text(
                        text = "तुमची पे लेटर थकबाकी ₹${payLaterDue.toInt()} भरण्याची वेळ आली आहे (४ दिवस शिल्लक).\n\nकृपया वेळेवर रीपेमेंट करून तुमचा सिबिल स्कोअर ७५०+ राखण्यास मदत करा आणि अधिक क्रेडिट लिमिट मिळवा.",
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val toPay = payLaterDue.coerceAtMost(walletBalance)
                            if (toPay > 0) {
                                walletBalance -= toPay
                                payLaterDue -= toPay
                            }
                            showRepaymentReminderDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                        enabled = walletBalance >= 1.0
                    ) {
                        Text("बिल भरा / Pay Now (₹${payLaterDue.toInt()})", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showRepaymentReminderDialog = false }) {
                        Text("नंतर भरा / Remind Later")
                    }
                }
            )
        }

        // B. Add Money Dialog
        if (showAddMoneyDialog) {
            AddMoneyDialog(
                currentBalance = walletBalance,
                onDismiss = { showAddMoneyDialog = false },
                onAddSuccess = { added ->
                    showAddMoneyDialog = false
                    realtimePaymentQRAmount = added
                    realtimePaymentQRPurpose = "ADD_MONEY"
                    realtimePaymentQRDetails = "Add ₹$added to Wallet"
                    onRealtimePaymentQRSuccess = {
                        scope.launch {
                            walletBalance += added
                        }
                    }
                    showRealtimePaymentQRDialog = true
                }
            )
        }

        // Reusable Realtime QR Payment Dialog
        if (showRealtimePaymentQRDialog) {
            RealtimePaymentQRDialog(
                amount = realtimePaymentQRAmount,
                paymentPurpose = realtimePaymentQRPurpose,
                details = realtimePaymentQRDetails,
                viewModel = viewModel,
                onDismiss = { showRealtimePaymentQRDialog = false },
                onSuccess = {
                    onRealtimePaymentQRSuccess()
                    showRealtimePaymentQRDialog = false
                }
            )
        }

        // B2. Wallet to Wallet Transfer Dialog
        if (showSendMoneyDialog) {
            var recipientEmailOrPhone by remember { mutableStateOf("") }
            var amountStr by remember { mutableStateOf("") }
            var transferStatus by remember { mutableStateOf("") }
            var isSending by remember { mutableStateOf(false) }

            AlertDialog(
                onDismissRequest = { showSendMoneyDialog = false },
                title = { Text("पैसे पाठवा / Wallet Transfer", fontWeight = FontWeight.Bold, color = Color.White) },
                containerColor = Color(0xFF0F172A),
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            "दुसऱ्या युझरच्या खात्यात थेट पैसे पाठवा.",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        
                        OutlinedTextField(
                            value = recipientEmailOrPhone,
                            onValueChange = { recipientEmailOrPhone = it },
                            label = { Text("युझर मेल आयडी / ईमेल (User Email)", color = Color.White.copy(alpha = 0.7f)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF10B981)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        OutlinedTextField(
                            value = amountStr,
                            onValueChange = { amountStr = it },
                            label = { Text("रक्कम / Amount (₹)", color = Color.White.copy(alpha = 0.7f)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF10B981)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (transferStatus.isNotEmpty()) {
                            Text(
                                text = transferStatus,
                                color = if (transferStatus.contains("यशस्वी") || transferStatus.contains("Successful")) Color(0xFF10B981) else Color(0xFFEF4444),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val amountVal = amountStr.toDoubleOrNull() ?: 0.0
                            if (recipientEmailOrPhone.trim().isEmpty()) {
                                transferStatus = "कृपया ईमेल टाका / Please enter Email."
                                return@Button
                            }
                            if (amountVal <= 0.0) {
                                transferStatus = "कृपया वैध रक्कम टाका / Enter valid amount."
                                return@Button
                            }
                            if (amountVal > walletBalance) {
                                transferStatus = "तुमच्या वॉलेटमध्ये पुरेसा बॅलन्स नाही."
                                return@Button
                            }
                            if (recipientEmailOrPhone.trim().lowercase() == userProfile.email.lowercase()) {
                                transferStatus = "स्वतःच्याच खात्यात ट्रान्सफर करता येत नाही!"
                                return@Button
                            }

                            viewModel.showLoading("वॉलेटमधून रक्कम ट्रान्सफर केली जात आहे... / Transferring wallet balance...")
                            isSending = true
                            viewModel.transferWalletBalance(
                                recipientEmailOrPhone = recipientEmailOrPhone.trim(),
                                amount = amountVal,
                                onSuccess = {
                                    scope.launch {
                                        delay(1000)
                                        walletBalance -= amountVal
                                        isSending = false
                                        viewModel.hideLoading()
                                        showSendMoneyDialog = false
                                    }
                                },
                                onError = { errorMsg ->
                                    scope.launch {
                                        delay(1000)
                                        transferStatus = "त्रुटी: $errorMsg"
                                        isSending = false
                                        viewModel.hideLoading()
                                    }
                                }
                            )
                        },
                        enabled = !isSending,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) {
                        Text(if (isSending) "प्रोसेसिंग..." else "पाठवा / Send", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSendMoneyDialog = false }, enabled = !isSending) {
                        Text("रद्द करा / Cancel", color = Color.White.copy(alpha = 0.7f))
                    }
                }
            )
        }

        // C. Upload Custom Banner Dialog (Firebase Upload Simulation)
        if (showUploadBannerDialog) {
            UploadBannerDialog(
                onDismiss = { showUploadBannerDialog = false },
                onUploadSuccess = { newSlide ->
                    sliderImages = sliderImages + newSlide
                    currentSlideIndex = sliderImages.size - 1
                    showUploadBannerDialog = false
                }
            )
        }

        // D. Mobile Recharge Dialog
        if (showRechargeDialog) {
            val maxLimit = when {
                creditScore >= 800 -> 2000.0
                creditScore >= 700 -> 1000.0
                creditScore >= 600 -> 500.0
                creditScore >= 500 -> 200.0
                else -> 0.0
            }
            val availableCreditLimit = maxLimit - payLaterDue
            val rechargePlans by viewModel.rechargePlans.collectAsState()
            
            val loanSettings by viewModel.loanSettings.collectAsState()
            
            MobileRechargeDialog(
                walletBalance = walletBalance,
                availableCreditLimit = availableCreditLimit,
                rechargePlans = rechargePlans,
                loanSettings = loanSettings,
                viewModel = viewModel,
                onDismiss = { showRechargeDialog = false },
                onSuccess = { number, operatorAndPlan, planPrice, method ->
                    var finalChargedAmount = planPrice
                    var calculatedFeeText = ""
                    if (method == "PayLater") {
                        val isUnder100 = planPrice < 100.0
                        val processingFee = if (isUnder100) loanSettings.processingFeeUnder100 else loanSettings.processingFeeOver100
                        val interest = planPrice * (loanSettings.interestRatePercent / 100.0)
                        finalChargedAmount = planPrice + processingFee + interest
                        
                        val validityDays = if (isUnder100) "8 दिवस मुदत" else "प्लॅन वैधता मुदत"
                        calculatedFeeText = " (फी: ₹${processingFee}, व्याज ${loanSettings.interestRatePercent}%, $validityDays)"
                        
                        payLaterDue += finalChargedAmount
                    } else if (method == "Wallet") {
                        walletBalance -= planPrice
                    }
                    
                    viewModel.bookService(
                        serviceType = "RECHARGE",
                        name = "Mobile: $number",
                        phone = number,
                        description = "$operatorAndPlan Recharge (${when(method) { "PayLater" -> "पे लेटर / Pay Later" "Wallet" -> "वॉलेट / Wallet" else -> "QR कोड / QR Code" }})$calculatedFeeText"
                    )
                    showRechargeDialog = false
                },
                onPayWithQR = { amount, purpose, details, onSuccessCallback ->
                    realtimePaymentQRAmount = amount
                    realtimePaymentQRPurpose = purpose
                    realtimePaymentQRDetails = details
                    onRealtimePaymentQRSuccess = onSuccessCallback
                    showRealtimePaymentQRDialog = true
                }
            )
        }

        // E. Mobile Recharge Loan Dialog
        if (showLoanDialog) {
            MobileLoanDialog(
                onDismiss = { showLoanDialog = false },
                onLoanSuccess = { amount ->
                    scope.launch {
                        viewModel.showLoading("क्रेडिट पात्रता पडताळणी आणि कर्ज मंजुरी प्रक्रिया सुरू आहे... / Verifying Credit & Approving Loan...")
                        delay(1500)
                        walletBalance += amount
                        viewModel.bookService(
                            serviceType = "RECHARGE",
                            name = "Emergency Recharge Loan",
                            phone = "AARUSHI_LOAN",
                            description = "Approved Instant Mobile Loan: ₹$amount"
                        )
                        viewModel.hideLoading()
                        showLoanDialog = false
                    }
                }
            )
        }

        // F. Mobile Combo Protection Dialog
        if (showComboDialog) {
            val protectionPlans by viewModel.protectionPlans.collectAsState()
            MobileComboProtectionDialog(
                walletBalance = walletBalance,
                protectionPlans = protectionPlans,
                onDismiss = { showComboDialog = false },
                onSuccess = { brand, serial, price, durationMonths ->
                    walletBalance -= price
                    viewModel.registerWarranty(
                        itemName = "Mobile Combo Protection Plan",
                        brand = brand,
                        serialNumber = "$serial (Screen/Glass Warranty)",
                        durationMonths = durationMonths
                    )
                    showComboDialog = false
                },
                onPayWithQR = { brand, serial, price ->
                    showComboDialog = false
                    realtimePaymentQRAmount = price
                    realtimePaymentQRPurpose = "COMBO_PROTECTION"
                    realtimePaymentQRDetails = "Combo Plan for $brand ($serial)"
                    onRealtimePaymentQRSuccess = {
                        viewModel.registerWarranty(
                            itemName = "Mobile Combo Protection Plan",
                            brand = brand,
                            serialNumber = "$serial (Screen/Glass/Combo Warranty)",
                            durationMonths = 12
                        )
                    }
                    showRealtimePaymentQRDialog = true
                }
            )
        }

        // G. Standard Warranty Addition dialog
        if (showAddWarrantyDialog) {
            Dialog(onDismissRequest = { showAddWarrantyDialog = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "नवीन वॉरंटी नोंदवा / Register Warranty",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        AddWarrantyForm(
                            onSubmit = { name, brand, serial, duration ->
                                viewModel.registerWarranty(name, brand, serial, duration)
                                showAddWarrantyDialog = false
                            },
                            onDismiss = { showAddWarrantyDialog = false }
                        )
                    }
                }
            }
        }

        // H. High-Fidelity Tax Invoice View Dialog
        showInvoiceItem?.let { item ->
            TaxInvoiceDialog(
                item = item,
                customerName = userName.split("/")[0].trim(),
                onDismiss = { showInvoiceItem = null }
            )
        }
    }
}

@Composable
fun UserDashboardCard(
    userName: String,
    walletBalance: Double,
    payLaterDue: Double,
    creditScore: Int,
    payLaterActive: Boolean,
    autoPaymentLink: String,
    onAddMoneyClick: () -> Unit,
    onSendMoneyClick: () -> Unit,
    onRepaySuccess: (Double) -> Unit,
    isFirebaseConnected: Boolean,
    onActivatePayLaterClick: () -> Unit,
    onRepayWithQRClick: (Double) -> Unit,
    userProfile: com.example.data.UserProfile?
) {
    val maxLimit = when {
        creditScore < 500 -> 0.0
        creditScore in 500..599 -> 100.0
        creditScore in 600..699 -> 350.0
        else -> 700.0
    }
    val availableLimit = (maxLimit - payLaterDue).coerceAtLeast(0.0)
    val ctx = androidx.compose.ui.platform.LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, Color(0xFF2F80ED).copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1E3A8A), // Royal Blue
                            Color(0xFF0F172A)  // Deep Midnight Slate
                        )
                    )
                )
                .padding(12.dp)
        ) {
            // 1. Top Row: User Profile & Sync Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "User Profile",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "नमस्कार / Hello 👋",
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.75f),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = userName,
                            fontSize = 13.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Sync status badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isFirebaseConnected) Color(0xFF10B981).copy(alpha = 0.2f) else Color(0xFFF59E0B).copy(alpha = 0.2f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(if (isFirebaseConnected) Color(0xFF10B981) else Color(0xFFF59E0B))
                        )
                        Text(
                            text = if (isFirebaseConnected) "Firebase Sync" else "Local Sync",
                            color = if (isFirebaseConnected) Color(0xFFA7F3D0) else Color(0xFFFDE68A),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.12f))
            Spacer(modifier = Modifier.height(8.dp))

            // 2. Financial Info side-by-side Layout (using Row and Weights)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Wallet Balance Section
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = "Wallet Icon",
                                tint = Color(0xFF2F80ED),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "वॉलेट / Wallet",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "₹${String.format(Locale.US, "%.2f", walletBalance)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1E293B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Button(
                                onClick = onAddMoneyClick,
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F80ED)),
                                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 0.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Money",
                                    tint = Color.White,
                                    modifier = Modifier.size(10.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "Add",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Button(
                                onClick = onSendMoneyClick,
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 0.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Send Money",
                                    tint = Color.White,
                                    modifier = Modifier.size(10.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "Send",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // Pay Later Section
                if (payLaterActive) {
                    Card(
                        modifier = Modifier.weight(1.2f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                        border = BorderStroke(1.dp, Color(0xFFDCFCE7))
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CreditCard,
                                    contentDescription = "Pay Later Icon",
                                    tint = Color(0xFF16A34A),
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "पे लेटर / Pay Later",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "शिल्लक / Available:",
                                        fontSize = 8.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "₹${availableLimit.toInt()}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF16A34A)
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "थकीत / Due:",
                                        fontSize = 8.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "₹${payLaterDue.toInt()}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (payLaterDue > 0) Color.Red else Color.Gray
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            if (payLaterDue > 0.0) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Button(
                                        onClick = {
                                            val toPay = payLaterDue.coerceAtMost(walletBalance)
                                            if (toPay > 0) {
                                                onRepaySuccess(toPay)
                                            }
                                        },
                                        shape = RoundedCornerShape(6.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                                        enabled = walletBalance >= 1.0,
                                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(26.dp)
                                    ) {
                                        Text(
                                            text = "वॉलेटने भरा (₹${payLaterDue.toInt()})",
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            onRepayWithQRClick(payLaterDue)
                                        },
                                        shape = RoundedCornerShape(6.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F259F)),
                                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(26.dp)
                                    ) {
                                        Text(
                                            text = "UPI QR ने भरा (₹${payLaterDue.toInt()})",
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }
                            } else {
                                // Display Total limit
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .background(Color(0xFFDCFCE7).copy(alpha = 0.5f), RoundedCornerShape(6.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "एकूण लिमिट / Limit: ₹${maxLimit.toInt()}",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF15803D)
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // Pay Later Section (When Deactivated / Needs Enrollment)
                    Card(
                        modifier = Modifier.weight(1.2f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                        border = BorderStroke(1.dp, Color(0xFFFEF3C7))
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CreditCard,
                                    contentDescription = "Pay Later Icon",
                                    tint = Color(0xFFD97706),
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "पे लेटर / Pay Later",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB45309)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "₹६०० लिमिट मिळवा",
                                fontSize = 8.sp,
                                color = Color.DarkGray,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Button(
                                onClick = {
                                    onActivatePayLaterClick()
                                },
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                            ) {
                                Text(
                                    text = "सक्रिय करा / Activate",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
            
            // If wallet balance is insufficient to pay dues, show warning inside this card
            if (payLaterActive && payLaterDue > 0.0 && walletBalance < payLaterDue) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFEF2F2), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Warning",
                        tint = Color.Red,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "पे लेटर बिल भरण्यासाठी वॉलेटमध्ये पुरेसे पैसे नाहीत. कृपया आधी पैसे जोडा.",
                        fontSize = 9.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.15f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.08f))
                    .clickable {
                        val whatsappText = "नमस्कार आरुषी इलेक्ट्रॉनिक्स, मला मदत हवी आहे."
                        try {
                            val intent = android.content.Intent(
                                android.content.Intent.ACTION_VIEW,
                                android.net.Uri.parse("https://api.whatsapp.com/send?phone=919860856702&text=${java.net.URLEncoder.encode(whatsappText, "UTF-8")}")
                            )
                            ctx.startActivity(intent)
                        } catch (e: Exception) {}
                    }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Help Icon",
                        tint = Color(0xFF25D366),
                        modifier = Modifier.size(16.dp)
                    )
                    Column {
                        Text(
                            text = "मदत हवी आहे? / Need Help? 💬",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "+91 9860856702 वर थेट व्हॉट्सॲप करा",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 9.sp
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Open Chat",
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun SearchBarCard(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFF1F5F9))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFF2F80ED),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (searchQuery.isEmpty()) {
                    Text(
                        text = "इथे सेवा शोधा... / Search services...",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("service_search_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                )
            }
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryHeader(titleMr: String, titleEn: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Text(
            text = titleMr,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B)
        )
        Text(
            text = titleEn,
            fontSize = 11.sp,
            color = Color(0xFF2F80ED),
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun RechargeSectionBlock(
    onRechargeClick: () -> Unit,
    onLoanClick: () -> Unit,
    bookings: List<BookingEntity>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Card 1: Mobile Recharge
            Card(
                onClick = onRechargeClick,
                modifier = Modifier
                    .weight(1f)
                    .height(130.dp)
                    .testTag("recharge_button"),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF3B82F6), // Bright Blue
                                    Color(0xFF1D4ED8)  // Royal Blue
                                )
                            )
                        )
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.22f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhoneAndroid,
                            contentDescription = "Mobile Recharge",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "मोबाईल रिचार्ज",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Mobile Recharge",
                        fontSize = 9.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Card 2: Recharge Loan
            Card(
                onClick = onLoanClick,
                modifier = Modifier
                    .weight(1f)
                    .height(130.dp)
                    .testTag("recharge_loan_button"),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF6366F1), // Indigo
                                    Color(0xFF4338CA)  // Dark Indigo
                                )
                            )
                        )
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.22f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = "Recharge Loan",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "मोबाईल रिचार्ज लोन",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Emergency Loan",
                        fontSize = 9.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Mini Recharge History Card
        val recharges = remember(bookings) {
            bookings.filter { it.serviceType == "RECHARGE" || it.serviceType == "RECHARGE_NORMAL" || it.serviceType == "RECHARGE_LOAN" }
        }

        // Active Plan Expiry Notice Card (Requirement 4)
        val activeRecharge = if (recharges.isNotEmpty()) {
            val r = recharges.first()
            val cleanDesc = r.description.substringBefore(" (")
            Triple(cleanDesc, 2L, r.customerPhone)
        } else {
            Triple("Jio ₹२९९ Unlimited Plan (1.5 GB/Day)", 3L, "9860856702")
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)), // Soft warm orange background
            border = BorderStroke(1.dp, Color(0xFFFFEDD5))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Warning",
                            tint = Color(0xFFEA580C),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "रिचार्ज प्लॅन संपत आला आहे! / Plan Expiring!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color(0xFF9A3412)
                        )
                    }
                    
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFEA580C), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "${activeRecharge.second} दिवस शिल्लक / ${activeRecharge.second} Days Left",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "चालू प्लॅन: ${activeRecharge.first} (${activeRecharge.third})",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF431407)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "⚠️ तुमचा हाफ प्लॅन एक्स्पायरी होण्यास येत आहे. सेवा खंडित होण्यापूर्वी कृपया पुन्हा रिचार्ज करा. / Your plan is nearing expiry. Please recharge again to continue uninterrupted services.",
                    fontSize = 10.sp,
                    color = Color(0xFFC2410C),
                    lineHeight = 14.sp
                )
                
                Spacer(modifier = Modifier.height(10.dp))
                
                Button(
                    onClick = onRechargeClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA580C)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhoneAndroid,
                        contentDescription = "Recharge Again",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "पुन्हा रिचार्ज करा / Recharge Again",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (recharges.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFF1F5F9))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "ताजी रिचार्ज हिस्ट्री / Recent Recharges",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2F80ED),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    recharges.take(2).forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(if (item.serviceType == "RECHARGE_LOAN") Color(0xFFEEF2FF) else Color(0xFFE5F1FF)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (item.serviceType == "RECHARGE_LOAN") Icons.Default.AccountBalanceWallet else Icons.Default.PhoneAndroid,
                                        contentDescription = "Icon",
                                        tint = if (item.serviceType == "RECHARGE_LOAN") Color(0xFF1E3A8A) else Color(0xFF2F80ED),
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = item.description,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF1E293B),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.widthIn(max = 200.dp)
                                )
                            }
                            Text(
                                text = "यशस्वी / Success",
                                fontSize = 9.sp,
                                color = Color(0xFF25D366),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WarrantySectionBlock(
    onComboClick: () -> Unit,
    onStandardClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Card 1: Combo Protection
        Card(
            onClick = onComboClick,
            modifier = Modifier
                .weight(1.2f)
                .height(135.dp)
                .testTag("combo_protection_button"),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFF59E0B), // Amber
                                Color(0xFFD97706)  // Orange
                            )
                        )
                    )
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.22f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Combo Protection",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "मोबाईल कॉम्बो प्रोटेक्शन",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 13.sp
                )
                Text(
                    text = "Combo Screen Protection",
                    fontSize = 9.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Card 2: Standard extended Warranty
        Card(
            onClick = onStandardClick,
            modifier = Modifier
                .weight(1f)
                .height(135.dp)
                .testTag("extended_warranty_button"),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF10B981), // Emerald
                                Color(0xFF047857)  // Green
                            )
                        )
                    )
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.22f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.WorkspacePremium,
                        contentDescription = "Extended Warranty",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "एक्सटेंडेड वॉरंटी",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Register Product",
                    fontSize = 9.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OtherServicesBlock(
    services: List<ServiceCategory>,
    onServiceClick: (ServiceCategory) -> Unit
) {
    val otherServices = remember(services) {
        services.filter { it.id != "RECHARGE" && it.id != "COMBO" && it.id != "WARRANTY" }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        items(otherServices) { service ->
            ServiceGridItem(
                service = service,
                onClick = { onServiceClick(service) }
            )
        }
    }
}

@Composable
fun TabButton(
    title: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isActive) Color(0xFF2F80ED) else Color.White,
        animationSpec = tween(durationMillis = 200)
    )
    val contentColor by animateColorAsState(
        targetValue = if (isActive) Color.White else Color(0xFF475569),
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = if (isActive) Color.Transparent else Color(0xFFE2E8F0),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = contentColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Custom simulated abstract map drawn on Canvas to perfectly emulate the background design
@Composable
fun InteractiveServiceMap(
    modifier: Modifier = Modifier,
    shopColor: Color = Color(0xFF2F80ED)
) {
    val gridColor = Color(0xFFE2E8F0).copy(alpha = 0.5f)
    val roadColor = Color(0xFFE5F1FF)
    val pointPulseRadius by animateFloatAsState(
        targetValue = 24f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(1500),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        )
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        // Draw Map Background
        drawRect(color = Color(0xFFF1F5F9))

        // Draw grid lines
        val step = 40.dp.toPx()
        for (x in 0.. (width / step).toInt()) {
            drawLine(
                color = gridColor,
                start = Offset(x * step, 0f),
                end = Offset(x * step, height),
                strokeWidth = 1.dp.toPx()
            )
        }
        for (y in 0.. (height / step).toInt()) {
            drawLine(
                color = gridColor,
                start = Offset(0f, y * step),
                end = Offset(width, y * step),
                strokeWidth = 1.dp.toPx()
            )
        }

        // Draw Streets & Pathways
        val street1 = Path().apply {
            moveTo(0f, height * 0.4f)
            lineTo(width * 0.5f, height * 0.4f)
            lineTo(width, height * 0.8f)
        }
        drawPath(
            path = street1,
            color = roadColor,
            style = Stroke(width = 24.dp.toPx())
        )

        val street2 = Path().apply {
            moveTo(width * 0.3f, 0f)
            lineTo(width * 0.3f, height)
        }
        drawPath(
            path = street2,
            color = roadColor,
            style = Stroke(width = 16.dp.toPx())
        )

        // Active Repair Shop Main Hub (Central point)
        val centerPoint = Offset(width * 0.5f, height * 0.4f)
        
        // Draw pulsating halo
        drawCircle(
            color = shopColor.copy(alpha = 0.2f),
            radius = pointPulseRadius * 2,
            center = centerPoint
        )

        // Draw main hub icon circle
        drawCircle(
            color = shopColor,
            radius = 12.dp.toPx(),
            center = centerPoint
        )

        drawCircle(
            color = Color.White,
            radius = 5.dp.toPx(),
            center = centerPoint
        )

        // Draw other local repairing demand points (pulsing spots)
        drawCircle(
            color = Color(0xFFF59E0B).copy(alpha = 0.6f),
            radius = 6.dp.toPx(),
            center = Offset(width * 0.25f, height * 0.7f)
        )

        drawCircle(
            color = Color(0xFFEF4444).copy(alpha = 0.6f),
            radius = 5.dp.toPx(),
            center = Offset(width * 0.8f, height * 0.25f)
        )
    }
}

@Composable
fun ServiceGridItem(
    service: ServiceCategory,
    onClick: () -> Unit
) {
    val darkColor = Color(
        red = (service.color.red * 0.7f).coerceIn(0f, 1f),
        green = (service.color.green * 0.7f).coerceIn(0f, 1f),
        blue = (service.color.blue * 0.7f).coerceIn(0f, 1f),
        alpha = service.color.alpha
    )
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(service.color, darkColor)
    )

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            .testTag("service_grid_${service.id}"),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.22f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = service.icon,
                    contentDescription = service.titleEn,
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = service.titleMr,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = service.titleEn,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun BookingsHistorySection(
    bookings: List<BookingEntity>,
    onCancelBooking: (Int) -> Unit
) {
    if (bookings.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = "No bookings",
                tint = Color.LightGray,
                modifier = Modifier.size(72.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "अजून कोणतीही बुकिंग नाही",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = "नवीन बुकिंग करण्यासाठी 'सेवा' टॅबवर जा.",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = "सर्व बुकिंगचा इतिहास / Booking History",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            items(bookings) { booking ->
                BookingItemCard(booking = booking, onCancel = { onCancelBooking(booking.id) })
            }
        }
    }
}

@Composable
fun BookingItemCard(
    booking: BookingEntity,
    onCancel: () -> Unit
) {
    val formattedDate = remember(booking.timestamp) {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        sdf.format(Date(booking.timestamp))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("booking_card_${booking.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE5F1FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Construction,
                            contentDescription = "Service",
                            tint = Color(0xFF2F80ED),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = booking.serviceType,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                }

                // Cloud Sync Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (booking.isSynced) Color(0xFFE5F1FF) else Color(0xFFFEF3C7)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (booking.isSynced) Icons.Default.CloudDone else Icons.Default.CloudQueue,
                            contentDescription = "Cloud state",
                            tint = if (booking.isSynced) Color(0xFF2F80ED) else Color(0xFFD97706),
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = if (booking.isSynced) "Cloud Synced" else "Local Saved",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (booking.isSynced) Color(0xFF2F80ED) else Color(0xFFD97706)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "ग्राहक / Customer: ${booking.customerName} (${booking.customerPhone})",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF475569)
            )

            Text(
                text = "तपशील / Description: ${booking.description}",
                fontSize = 11.sp,
                color = Color(0xFF64748B),
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(color = Color(0xFFF1F5F9))

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formattedDate,
                    fontSize = 10.sp,
                    color = Color.Gray
                )

                IconButton(
                    onClick = onCancel,
                    modifier = Modifier.size(24.dp).testTag("delete_booking_btn_${booking.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WarrantyTrackerSection(
    items: List<WarrantyItemEntity>,
    onAddWarranty: (String, String, String, Int) -> Unit,
    onRemoveWarranty: (Int) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "स्टॅंडर्ड वॉरंटी ट्रॅकर / Warranty Certificates",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = "वस्तूंची खरेदी व डिजिटल वॉरंटी कार्ड तपासा",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F80ED)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("add_warranty_button")
            ) {
                Text("नवीन जोडा +", fontSize = 11.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (showAddDialog) {
            AddWarrantyForm(
                onDismiss = { showAddDialog = false },
                onSubmit = { name, brand, sr, duration ->
                    onAddWarranty(name, brand, sr, duration)
                    showAddDialog = false
                }
            )
        }

        if (items.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.WorkspacePremium,
                    contentDescription = "No certificates",
                    tint = Color.LightGray,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "डिजिटल वॉरंटी सर्टिफिकेट्स रिकामे आहेत",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items) { item ->
                    WarrantyItemCard(item = item, onRemove = { onRemoveWarranty(item.id) })
                }
            }
        }
    }
}

@Composable
fun WarrantyItemCard(
    item: WarrantyItemEntity,
    onRemove: () -> Unit
) {
    // Math logic for computing warranty expiration and days left
    val timePassed = remember(item.purchaseDate) {
        System.currentTimeMillis() - item.purchaseDate
    }
    val totalDurationMs = remember(item.warrantyDurationMonths) {
        item.warrantyDurationMonths.toLong() * 30 * 24 * 60 * 60 * 1000L
    }
    val remainingTimeMs = remember(timePassed, totalDurationMs) {
        (totalDurationMs - timePassed).coerceAtLeast(0L)
    }
    val daysLeft = remember(remainingTimeMs) {
        remainingTimeMs / (1000 * 60 * 60 * 24)
    }
    val progress = remember(remainingTimeMs, totalDurationMs) {
        (remainingTimeMs.toFloat() / totalDurationMs.toFloat()).coerceIn(0f, 1f)
    }

    val purchaseDateStr = remember(item.purchaseDate) {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        sdf.format(Date(item.purchaseDate))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("warranty_card_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEDFAF1)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verified Warranty",
                        tint = if (daysLeft > 0) Color(0xFF00A859) else Color.Red,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = item.itemName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                }

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(24.dp).testTag("delete_warranty_btn_${item.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete certificate",
                        tint = Color.LightGray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "ब्रांड / Brand: ${item.brand} | S/N: ${item.serialNumber}",
                fontSize = 11.sp,
                color = Color.Gray
            )

            Text(
                text = "खरेदी दिनांक / Purchased: $purchaseDateStr",
                fontSize = 11.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (daysLeft > 0) "वॉरंटी उर्वरित: $daysLeft दिवस" else "वॉरंटी संपली / Expired",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (daysLeft > 0) Color(0xFF00A859) else Color.Red
                )
                Text(
                    text = "${item.warrantyDurationMonths} महिने",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = if (daysLeft > 10) Color(0xFF00A859) else Color(0xFFEF4444),
                trackColor = Color(0xFFEDFAF1)
            )
        }
    }
}

@Composable
fun AddWarrantyForm(
    onDismiss: () -> Unit,
    onSubmit: (String, String, String, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var serial by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("12") }
    var isOldMobile by remember { mutableStateOf(false) }
    
    val context = androidx.compose.ui.platform.LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .testTag("add_warranty_form"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "डिजिटल वॉरंटी जोडा / Add Digital Warranty",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Photo Requirement Info Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                border = BorderStroke(1.dp, Color(0xFF3B82F6)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "महत्त्वाची सूचना / Photo Requirement:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E40AF)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "वॉरंटी देण्यासाठी खालील ४-५ फोटो आवश्यक आहेत:\n" +
                                "१. बिलाचा फोटो (Bill Photo)\n" +
                                "२. प्रॉडक्टचे फोटो (Product Photos)\n" +
                                "३. चालू कंडिशनचा फोटो (Working Condition Photo)",
                        fontSize = 9.sp,
                        color = Color(0xFF1E40AF),
                        lineHeight = 11.sp
                    )
                }
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("वस्तूचे नाव / Item Name (e.g. Mixer)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("warranty_item_name_input"),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = Color(0xFF2F80ED),
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    focusedLabelColor = Color(0xFF2F80ED),
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = brand,
                    onValueChange = { brand = it },
                    label = { Text("ब्रांड / Brand") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("warranty_brand_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = Color(0xFF2F80ED),
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        focusedLabelColor = Color(0xFF2F80ED),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                OutlinedTextField(
                    value = serial,
                    onValueChange = { serial = it },
                    label = { Text("सिरीयल नं / S/N") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("warranty_serial_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = Color(0xFF2F80ED),
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        focusedLabelColor = Color(0xFF2F80ED),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("वॉरंटी कालावधी (महिने) / Duration (Months)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("warranty_duration_input"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = Color(0xFF2F80ED),
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    focusedLabelColor = Color(0xFF2F80ED),
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Is Old Mobile Row Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { isOldMobile = !isOldMobile }
                    .background(if (isOldMobile) Color(0xFFFEF2F2) else Color(0xFFF1F5F9))
                    .border(
                        width = 1.dp,
                        color = if (isOldMobile) Color(0xFFEF4444) else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (isOldMobile) Color(0xFFEF4444) else Color.White)
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (isOldMobile) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Checked",
                            tint = Color.White,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "हा जुना मोबाईल आहे का? / Is this an old mobile?",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isOldMobile) Color(0xFF991B1B) else Color.DarkGray
                    )
                    Text(
                        text = "जुन्या मोबाईलसाठी अतिरिक्त शुल्क (₹१५०) लागू होईल आणि वॉरंटी १५ दिवसांनंतर सक्रिय होईल.",
                        fontSize = 9.sp,
                        color = if (isOldMobile) Color(0xFFEF4444) else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "रद्द करा / Cancel",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .clickable(onClick = onDismiss)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("cancel_warranty_btn")
                )

                Button(
                    onClick = {
                        if (name.isNotBlank() && brand.isNotBlank()) {
                            val finalDuration = duration.toIntOrNull() ?: 12
                            val typeLabel = if (isOldMobile) "जुना मोबाईल (Old Phone - १५ दिवस वेटिंग)" else "नवीन वस्तू (New Item)"
                            val whatsappText = "नमस्कार आरुषी मल्टिसर्व्हिसेस, मी नवीन एक्सटेंडेड वॉरंटी नोंदणी करू इच्छितो.\n" +
                                    "वस्तू: $name\n" +
                                    "ब्रांड: $brand\n" +
                                    "सिरीयल/IMEI: $serial\n" +
                                    "कालावधी: $finalDuration महिने\n" +
                                    "प्रकार: $typeLabel\n" +
                                    "शुल्क: ${if (isOldMobile) "अतिरिक्त ₹१५० लागू" else "सामान्य"}\n" +
                                    "मी खालील आवश्यक ४-५ फोटो पाठवत आहे:\n" +
                                    "१. बिलाचा फोटो (Bill Photo)\n" +
                                    "२. प्रॉडक्टचे फोटो (Product Photos)\n" +
                                    "३. चालू कंडिशनचा फोटो (Working Condition Photo)"
                            
                            try {
                                val intent = android.content.Intent(
                                    android.content.Intent.ACTION_VIEW,
                                    android.net.Uri.parse("https://api.whatsapp.com/send?phone=919860856702&text=${java.net.URLEncoder.encode(whatsappText, "UTF-8")}")
                                )
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                android.widget.Toast.makeText(context, "WhatsApp open failed", android.widget.Toast.LENGTH_SHORT).show()
                            }

                            onSubmit(name, "$brand ${if (isOldMobile) "(Old Phone)" else ""}", serial.ifBlank { "SN_NONE" }, finalDuration)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F80ED)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("submit_warranty_btn")
                ) {
                    Text("सर्टिफिकेट जनरेट करा", fontSize = 11.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun BookingFormSheet(
    service: ServiceCategory,
    onDismiss: () -> Unit,
    onSubmit: (String, String, String) -> Unit,
    defaultName: String = ""
) {
    var name by remember { mutableStateOf(defaultName) }
    var phone by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var workReason by remember { mutableStateOf("") }
    var workLocation by remember { mutableStateOf("") }

    // Specialized variables based on the active booking service type
    var isLoanSelected by remember { mutableStateOf(false) }
    var selectedPlanPrice by remember { mutableStateOf("₹299") }
    var selectedOperator by remember { mutableStateOf("Jio") }
    var selectedItemType by remember { mutableStateOf("TV / एलसीडी") }
    var selectedProtectionType by remember { mutableStateOf("फुल ग्लास + स्क्रीन कॉम्बो संरक्षण") }
    var tinShedWidth by remember { mutableStateOf("12") }
    var tinShedLength by remember { mutableStateOf("15") }

    // Automatically generate pre-filled description context depending on service
    LaunchedEffect(
        service.id,
        selectedPlanPrice,
        selectedOperator,
        selectedItemType,
        selectedProtectionType,
        tinShedWidth,
        tinShedLength,
        isLoanSelected
    ) {
        description = when (service.id) {
            "RECHARGE" -> {
                val serviceTypeStr = if (isLoanSelected) "इमर्जन्सी रिचार्ज लोन / Emergency Recharge Loan" else "नॉर्मल रिचार्ज / Regular Recharge"
                "Operator: $selectedOperator | Plan: $selectedPlanPrice | Service Type: $serviceTypeStr | Status: Approved / Pay Later"
            }
            "ELECTRONIC" -> "Category: $selectedItemType | Repair Problem Details: "
            "COMBO" -> "Protection Selected: $selectedProtectionType | Warranty Included: Yes"
            "LINE_FITTING" -> "Fitting Type: Full House Wiring Setup | Site Address: "
            "PLUMBER" -> "Service Required: Tap & Pipeline leakage checkup | Urgency: High"
            "TIN_SHED" -> {
                val area = (tinShedWidth.toIntOrNull() ?: 0) * (tinShedLength.toIntOrNull() ?: 0)
                val estimatedCost = area * 120
                "Shed Size: $tinShedWidth x $tinShedLength feet ($area sq. ft.) | Estimated Construction Cost: ₹$estimatedCost"
            }
            else -> ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = false, onClick = {}) // prevent click-through
                .testTag("booking_form_sheet_${service.id}"),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(service.color.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = service.icon,
                                    contentDescription = "service",
                                    tint = service.color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = service.titleMr,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = service.titleEn,
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_sheet_btn")) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close sheet",
                                tint = Color.Gray
                            )
                        }
                    }
                    HorizontalDivider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(vertical = 8.dp))
                }

                // Dynamic service form elements
                item {
                    when (service.id) {
                        "RECHARGE" -> {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = "सेवा प्रकार निवडा / Choose Service Type",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (!isLoanSelected) Color(0xFFE5F1FF) else Color(0xFFF1F5F9))
                                            .border(
                                                width = 1.5.dp,
                                                color = if (!isLoanSelected) Color(0xFF2F80ED) else Color.Transparent,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clickable { 
                                                isLoanSelected = false
                                                selectedPlanPrice = "₹299"
                                            }
                                            .padding(vertical = 10.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "नॉर्मल रिचार्ज",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (!isLoanSelected) Color(0xFF2F80ED) else Color.DarkGray
                                            )
                                            Text(
                                                text = "Regular Recharge",
                                                fontSize = 9.sp,
                                                color = if (!isLoanSelected) Color(0xFF2F80ED) else Color.Gray
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (isLoanSelected) Color(0xFFFFF3CD) else Color(0xFFF1F5F9))
                                            .border(
                                                width = 1.5.dp,
                                                color = if (isLoanSelected) Color(0xFFD97706) else Color.Transparent,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clickable { 
                                                isLoanSelected = true
                                                selectedPlanPrice = "₹25 Loan Pack"
                                            }
                                            .padding(vertical = 10.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "इमर्जन्सी रिचार्ज लोन ⚡",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isLoanSelected) Color(0xFFD97706) else Color.DarkGray
                                            )
                                            Text(
                                                text = "Recharge Loan",
                                                fontSize = 9.sp,
                                                color = if (isLoanSelected) Color(0xFFD97706) else Color.Gray
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "ऑपरेटर निवडा / Choose Operator",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    listOf("Jio", "Airtel", "Vi", "BSNL").forEach { operator ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(
                                                    if (selectedOperator.startsWith(operator)) Color(
                                                        0xFFE5F1FF
                                                    ) else Color(0xFFF1F5F9)
                                                )
                                                .border(
                                                    width = 1.dp,
                                                    color = if (selectedOperator.startsWith(operator)) Color(
                                                        0xFF2F80ED
                                                    ) else Color.Transparent,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .clickable { selectedOperator = "$operator Network" }
                                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = operator,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (selectedOperator.startsWith(operator)) Color(
                                                    0xFF2F80ED
                                                ) else Color.DarkGray
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = if (isLoanSelected) "लोन पॅक्स निवडा / Choose Emergency Loan Packs" else "रिचार्ज प्लॅन्स / Quick Recharge Plans",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    val plans = if (isLoanSelected) {
                                        listOf("₹15 Loan Pack", "₹25 Loan Pack", "₹61 Loan Pack", "₹110 Loan Pack")
                                    } else {
                                        listOf("₹199", "₹299", "₹349", "₹719")
                                    }
                                    plans.forEach { plan ->
                                        Card(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clickable { selectedPlanPrice = plan },
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (selectedPlanPrice == plan) {
                                                    if (isLoanSelected) Color(0xFFFFFBEB) else Color(0xFFE5F1FF)
                                                } else Color(0xFFF8FAFC)
                                            ),
                                            border = BorderStroke(
                                                1.dp,
                                                if (selectedPlanPrice == plan) {
                                                    if (isLoanSelected) Color(0xFFD97706) else Color(0xFF2F80ED)
                                                } else Color(0xFFE2E8F0)
                                            )
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(8.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = plan,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp,
                                                    color = if (selectedPlanPrice == plan) {
                                                        if (isLoanSelected) Color(0xFFD97706) else Color(0xFF2F80ED)
                                                    } else Color.Black,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Text(
                                                    text = if (isLoanSelected) {
                                                        when (plan) {
                                                            "₹15 Loan Pack" -> "1GB Data"
                                                            "₹25 Loan Pack" -> "2GB Data"
                                                            "₹61 Loan Pack" -> "6GB Data"
                                                            else -> "Talktime"
                                                        }
                                                    } else {
                                                        if (plan == "₹199") "1.5GB/D" else "2GB/Day"
                                                    },
                                                    fontSize = 8.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        }
                                    }
                                }

                                if (isLoanSelected) {
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(8.dp),
                                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Info,
                                                contentDescription = "info",
                                                tint = Color(0xFFD97706),
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                text = "लोन नियम: तात्काळ रिचार्ज करा आणि सोयीनुसार नंतर आरुषी स्टोअरला भेट देऊन पेमेंट पूर्ण करा. शून्य अतिरिक्त व्याज दर!",
                                                fontSize = 9.sp,
                                                color = Color(0xFF92400E),
                                                lineHeight = 11.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        "ELECTRONIC" -> {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = "वस्तू निवडा / Item Category",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(3),
                                    modifier = Modifier.height(72.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    val appliances = listOf("TV / LCD", "Mixer", "Iron", "AC Unit", "Fan / Cooler")
                                    items(appliances) { item ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(
                                                    if (selectedItemType == item) Color(0xFFE5F1FF) else Color(
                                                        0xFFF1F5F9
                                                    )
                                                )
                                                .border(
                                                    1.dp,
                                                    if (selectedItemType == item) Color(0xFF2F80ED) else Color.Transparent,
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .clickable { selectedItemType = item }
                                                .padding(vertical = 6.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = item,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (selectedItemType == item) Color(
                                                    0xFF2F80ED
                                                ) else Color.DarkGray
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        "COMBO" -> {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = "कॉम्बो प्रोटेक्शन प्लॅन्स / Protection Option",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                val combos = listOf(
                                    "स्क्रीन ग्लास सुरक्षा (₹199)",
                                    "फुल स्क्रीन + एलसीडी कॉम्बो (₹499)",
                                    "ऑल-इन-वन पाणी + स्क्रीन कॉम्बो (₹799)"
                                )
                                combos.forEach { opt ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                if (selectedProtectionType == opt) Color(0xFFE5F1FF) else Color(
                                                    0xFFF8FAFC
                                                )
                                            )
                                            .border(
                                                1.dp,
                                                if (selectedProtectionType == opt) Color(0xFF2F80ED) else Color(
                                                    0xFFE2E8F0
                                                ),
                                                RoundedCornerShape(12.dp)
                                            )
                                            .clickable { selectedProtectionType = opt }
                                            .padding(horizontal = 14.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Security,
                                            contentDescription = "Shield",
                                            tint = if (selectedProtectionType == opt) Color(
                                                0xFF2F80ED
                                            ) else Color.LightGray,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = opt,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (selectedProtectionType == opt) Color(
                                                0xFF2F80ED
                                            ) else Color.DarkGray
                                        )
                                    }
                                }
                            }
                        }

                        "TIN_SHED" -> {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = "शेडचे मोजमाप (फूट) / Dimension Size (Feet)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = tinShedLength,
                                        onValueChange = { tinShedLength = it },
                                        label = { Text("लांबी / Length (Ft)") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.weight(1f),
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                            focusedBorderColor = Color(0xFF2F80ED),
                                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                            focusedLabelColor = Color(0xFF2F80ED),
                                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    )

                                    OutlinedTextField(
                                        value = tinShedWidth,
                                        onValueChange = { tinShedWidth = it },
                                        label = { Text("रुंदी / Width (Ft)") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.weight(1f),
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                            focusedBorderColor = Color(0xFF2F80ED),
                                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                            focusedLabelColor = Color(0xFF2F80ED),
                                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    )
                                }

                                val area = (tinShedWidth.toIntOrNull() ?: 0) * (tinShedLength.toIntOrNull() ?: 0)
                                Text(
                                    text = "एकूण क्षेत्रफळ: $area चौ. फूट (दर: ₹120 / चौ. फूट)",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE5F1FF))
                                ) {
                                    Text(
                                        text = "अंदाजे खर्च / Estimated Cost: ₹${area * 120}/-",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color(0xFF2F80ED),
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // General booking input fields
                item {
                    Text(
                        text = "ग्राहक तपशील / Customer Details",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("तुमचे नाव / Your Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("booking_name_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedBorderColor = Color(0xFF2F80ED),
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                            focusedLabelColor = Color(0xFF2F80ED),
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("मोबाईल नंबर / Mobile Number") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("booking_phone_input"),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedBorderColor = Color(0xFF2F80ED),
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                            focusedLabelColor = Color(0xFF2F80ED),
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (service.id == "RECHARGE") {
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("तपशील / Requirements / Address") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .testTag("booking_desc_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedBorderColor = Color(0xFF2F80ED),
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                focusedLabelColor = Color(0xFF2F80ED),
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        )
                    } else {
                        OutlinedTextField(
                            value = workReason,
                            onValueChange = { workReason = it },
                            label = { Text("कोणत्या कामाचे काय कारण / कोणते काम करायचे आहे?") },
                            placeholder = { Text("उदा. टीव्ही स्क्रीन चालू होत नाही / AC लीक आहे") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .testTag("booking_work_reason_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedBorderColor = Color(0xFF2F80ED),
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                focusedLabelColor = Color(0xFF2F80ED),
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        OutlinedTextField(
                            value = workLocation,
                            onValueChange = { workLocation = it },
                            label = { Text("काम कुठे करायचे आहे? (पत्ता)") },
                            placeholder = { Text("उदा. घर क्र. १२, दत्त चौक, वाशीम") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .testTag("booking_work_location_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedBorderColor = Color(0xFF2F80ED),
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                focusedLabelColor = Color(0xFF2F80ED),
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        )
                    }
                }

                item {
                    val context = LocalContext.current
                    Button(
                        onClick = {
                            if (name.isNotBlank() && phone.isNotBlank()) {
                                if (service.id == "RECHARGE") {
                                    onSubmit(name, phone, description)
                                } else {
                                    val compiledDetails = "काय काम/कारण: $workReason | कुठे करायचे (पत्ता): $workLocation"
                                    onSubmit(name, phone, compiledDetails)
                                    
                                    // WhatsApp redirect
                                    val whatsappMessage = """
*नवीन सेवा बुकिंग (New Service Booking)*
------------------------------------
👤 *प्रोफाईल युजर नाव / Profile User:* $defaultName
👤 *ग्राहकाचे नाव / Customer Name:* $name
📞 *मोबाईल नंबर / Mobile:* $phone
🛠️ *पाहिजे असलेली सेवा / Service:* ${service.titleMr} (${service.titleEn})
❓ *कामाचे कारण / Requirement:* $workReason
📍 *कामाचा पत्ता / Work Address:* $workLocation
------------------------------------
_आरुषी स्टोअर अप्लिकेशनवरून पाठवले_
                                    """.trimIndent()
                                    
                                    try {
                                        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://api.whatsapp.com/send?phone=919860856702&text=${java.net.URLEncoder.encode(whatsappMessage, "UTF-8")}"))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        android.widget.Toast.makeText(context, "WhatsApp error: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        },
                        enabled = name.isNotBlank() && phone.isNotBlank() && (service.id == "RECHARGE" || (workReason.isNotBlank() && workLocation.isNotBlank())),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_booking_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F80ED)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (service.id == "RECHARGE") "बुकिंगची नोंद करा / Confirm Service Booking" else "माहिती व्हाट्सअप वर पाठवा / Send on WhatsApp 💬",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// BorderStroke helper
@Composable
fun BorderStroke(width: androidx.compose.ui.unit.Dp, color: Color) = remember(width, color) {
    androidx.compose.foundation.BorderStroke(width, color)
}

// 1. UNIFIED HISTORY SCREEN
@Composable
fun UnifiedHistoryScreen(
    bookings: List<BookingEntity>,
    warrantyItems: List<WarrantyItemEntity>,
    activeFilter: Int,
    onFilterChange: (Int) -> Unit,
    onCancelBooking: (Int) -> Unit,
    onRemoveWarranty: (Int) -> Unit,
    onInvoiceClick: (WarrantyItemEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "व्यवहार इतिहास / Transaction & Booking History",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B)
        )
        Text(
            text = "सर्व रिचार्ज, बुकिंग्ज आणि वॉरंटी विधानांची एकाच ठिकाणी माहिती",
            fontSize = 11.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Filter tabs: 0: All, 1: Recharges, 2: Bookings, 3: Warranties
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val tabs = listOf(
                "सर्व / All",
                "रिचार्ज / Recharge",
                "बुकिंग्ज / Bookings",
                "वॉरंटी / Warranty"
            )
            tabs.forEachIndexed { index, title ->
                val isActive = activeFilter == index
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isActive) Color(0xFF2F80ED) else Color(0xFFF1F5F9))
                        .clickable { onFilterChange(index) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) Color.White else Color(0xFF475569)
                    )
                }
            }
        }

        // Gather all items and filter them
        val filteredBookings = remember(bookings, activeFilter) {
            when (activeFilter) {
                0 -> bookings
                1 -> bookings.filter { it.serviceType == "RECHARGE" }
                2 -> bookings.filter { it.serviceType != "RECHARGE" }
                else -> emptyList()
            }
        }

        val filteredWarranties = remember(warrantyItems, activeFilter) {
            if (activeFilter == 0 || activeFilter == 3) warrantyItems else emptyList()
        }

        if (filteredBookings.isEmpty() && filteredWarranties.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "No history",
                    tint = Color.LightGray,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "निवडलेल्या वर्गवारीत कोणताही इतिहास नाही",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Bookings (including Recharges)
                items(filteredBookings) { booking ->
                    val isRecharge = booking.serviceType == "RECHARGE"
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("history_booking_${booking.id}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    val icon = when (booking.serviceType) {
                                        "RECHARGE" -> Icons.Default.PhoneAndroid
                                        "WALLET_TRANSFER" -> Icons.Default.Send
                                        "WALLET_DEPOSIT" -> Icons.Default.AccountBalanceWallet
                                        "ADMIN_ADJUSTMENT" -> Icons.Default.Edit
                                        else -> Icons.Default.Construction
                                    }
                                    val tint = when (booking.serviceType) {
                                        "RECHARGE" -> Color(0xFF2F80ED)
                                        "WALLET_TRANSFER" -> Color(0xFF10B981)
                                        "WALLET_DEPOSIT" -> Color(0xFF3B82F6)
                                        "ADMIN_ADJUSTMENT" -> Color(0xFFF59E0B)
                                        else -> Color(0xFF64748B)
                                    }
                                    val title = when (booking.serviceType) {
                                        "RECHARGE" -> "मोबाईल रिचार्ज / Recharge"
                                        "WALLET_TRANSFER" -> "वॉलेट ट्रान्सफर / Wallet Transfer"
                                        "WALLET_DEPOSIT" -> "वॉलेट ठेव / Wallet Deposit"
                                        "ADMIN_ADJUSTMENT" -> "ऍडमीन बदल / Admin Adjustment"
                                        else -> booking.serviceType
                                    }
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = "Icon",
                                        tint = tint,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = title,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E293B)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (booking.status == "SUCCESS" || booking.status == "APPROVED") Color(0xFFD1FAE5) else Color(0xFFFEF3C7))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = booking.status,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (booking.status == "SUCCESS" || booking.status == "APPROVED") Color(0xFF065F46) else Color(0xFF92400E)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "ग्राहक / User: ${booking.customerName} (${booking.customerPhone})",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "तपशील / Description: ${booking.description}",
                                fontSize = 11.sp,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(booking.timestamp)),
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = {
                                            onInvoiceClick(
                                                WarrantyItemEntity(
                                                    id = booking.id,
                                                    itemName = if (isRecharge) "Mobile Recharge" else booking.serviceType,
                                                    brand = if (booking.description.contains("Jio")) "Jio Network" else "Operator Service",
                                                    serialNumber = booking.customerPhone,
                                                    purchaseDate = booking.timestamp,
                                                    warrantyDurationMonths = 0
                                                )
                                            )
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5F1FF)),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ReceiptLong,
                                            contentDescription = "Invoice",
                                            tint = Color(0xFF2F80ED),
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("पावती / Invoice", fontSize = 10.sp, color = Color(0xFF2F80ED))
                                    }

                                    IconButton(
                                        onClick = { onCancelBooking(booking.id) },
                                        modifier = Modifier.size(30.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.Red,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Warranties
                items(filteredWarranties) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("history_warranty_${item.id}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFEDFAF1)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.WorkspacePremium,
                                        contentDescription = "Icon",
                                        tint = Color(0xFFD97706),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = item.itemName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E293B)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFEDFAF1))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "${item.warrantyDurationMonths} Months",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF00A859)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "ब्रांड / Brand: ${item.brand} | S/N: ${item.serialNumber}",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(item.purchaseDate)),
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = { onInvoiceClick(item) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5F1FF)),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ReceiptLong,
                                            contentDescription = "Invoice",
                                            tint = Color(0xFF2F80ED),
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("पावती / Invoice", fontSize = 10.sp, color = Color(0xFF2F80ED))
                                    }

                                    IconButton(
                                        onClick = { onRemoveWarranty(item.id) },
                                        modifier = Modifier.size(30.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.Red,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 2. MY INVESTMENTS SCREEN
@Composable
fun MyInvestmentsScreen(
    warrantyItems: List<WarrantyItemEntity>,
    onInvoiceClick: (WarrantyItemEntity) -> Unit,
    onRemoveWarranty: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Summary Dashboard
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A))
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "माझी गुंतवणूक आणि सुरक्षा / Protection Dashboard",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "एकूण सुरक्षा प्लॅन्स: ${warrantyItems.size}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "तुमचे सर्व मोबाईल आणि इलेक्ट्रॉनिक उपकरणे पूर्णतः सुरक्षित आहेत.",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Text(
            text = "सक्रिय सुरक्षा प्लॅन्स / Active Warranties & Protection Plans",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B)
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (warrantyItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "No protection",
                    tint = Color.LightGray,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "सक्रिय सुरक्षा किंवा वॉरंटी सर्टिफिकेट्स नाहीत",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(warrantyItems) { item ->
                    val purchaseTime = item.purchaseDate
                    val durationMs = item.warrantyDurationMonths.toLong() * 30 * 24 * 60 * 60 * 1000L
                    val timeElapsed = System.currentTimeMillis() - purchaseTime
                    val remainingMs = (durationMs - timeElapsed).coerceAtLeast(0L)
                    val daysLeft = remainingMs / (1000 * 60 * 60 * 24)
                    val progress = (remainingMs.toFloat() / durationMs.toFloat()).coerceIn(0f, 1f)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("investment_card_${item.id}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verified Plan",
                                        tint = if (daysLeft > 0) Color(0xFF00A859) else Color.Red,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = item.itemName,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E293B)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (daysLeft > 0) Color(0xFFE6F4EA) else Color(0xFFFCE8E6))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = if (daysLeft > 0) "सक्रिय / Active" else "Expired",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (daysLeft > 0) Color(0xFF137333) else Color(0xFFC5221F)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Brand: ${item.brand} | S/N: ${item.serialNumber}",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (daysLeft > 0) "उर्वरित कालावधी: $daysLeft दिवस" else "वॉरंटी संपुष्टात आली",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (daysLeft > 0) Color(0xFF00A859) else Color.Red
                                )
                                Text(
                                    text = "${item.warrantyDurationMonths} Months",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(CircleShape),
                                color = if (daysLeft > 30) Color(0xFF00A859) else Color(0xFFEF4444),
                                trackColor = Color(0xFFEDFAF1)
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "खरेदी दिनांक: ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(item.purchaseDate))}",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = { onInvoiceClick(item) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F80ED)),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Text("कर पावती / Invoice", fontSize = 10.sp, color = Color.White)
                                    }

                                    IconButton(
                                        onClick = { onRemoveWarranty(item.id) },
                                        modifier = Modifier.size(30.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Unsubscribe",
                                            tint = Color.LightGray,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 3. ADD MONEY DIALOG
@Composable
fun AddMoneyDialog(
    currentBalance: Double,
    onDismiss: () -> Unit,
    onAddSuccess: (Double) -> Unit
) {
    var amountText by remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("add_money_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "वॉलेटमध्ये पैसे जोडा / Add Wallet Money 💰",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "सध्याचे बॅलन्स / Current Balance: ₹$currentBalance",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("रक्कम टाका / Enter Amount (₹)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_money_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = Color(0xFF2F80ED),
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        focusedLabelColor = Color(0xFF2F80ED),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(100.0, 500.0, 1000.0).forEach { quickAmount ->
                        Button(
                            onClick = { amountText = quickAmount.toInt().toString() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5F1FF)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+₹${quickAmount.toInt()}", fontSize = 11.sp, color = Color(0xFF2F80ED))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "रद्द करा / Cancel",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable(onClick = onDismiss)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .testTag("cancel_add_money")
                    )

                    Button(
                        onClick = {
                            val amt = amountText.toDoubleOrNull() ?: 0.0
                            if (amt > 0.0) {
                                onAddSuccess(amt)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F80ED)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("confirm_add_money")
                    ) {
                        Text("पैसे जोडा", fontSize = 11.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

// 4. UPLOAD BANNER DIALOG
@Composable
fun UploadBannerDialog(
    onDismiss: () -> Unit,
    onUploadSuccess: (SliderImage) -> Unit
) {
    var url by remember { mutableStateOf("") }
    var captionMr by remember { mutableStateOf("") }
    var captionEn by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("upload_banner_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "नवीन बॅनर जोडा / Add Promotional Banner 🖼️",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("बॅनर इमेज URL / Banner Image URL") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("banner_url_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = Color(0xFF2F80ED),
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        focusedLabelColor = Color(0xFF2F80ED),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = captionMr,
                    onValueChange = { captionMr = it },
                    label = { Text("मराठी कॅप्शन / Marathi Caption") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("banner_caption_mr_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = Color(0xFF2F80ED),
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        focusedLabelColor = Color(0xFF2F80ED),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = captionEn,
                    onValueChange = { captionEn = it },
                    label = { Text("इंग्रजी कॅप्शन / English Caption") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("banner_caption_en_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = Color(0xFF2F80ED),
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        focusedLabelColor = Color(0xFF2F80ED),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "रद्द करा / Cancel",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable(onClick = onDismiss)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .testTag("cancel_upload_banner")
                    )

                    Button(
                        onClick = {
                            if (url.isNotBlank() && captionMr.isNotBlank() && captionEn.isNotBlank()) {
                                onUploadSuccess(SliderImage(url, captionEn, captionMr))
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F80ED)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("confirm_upload_banner")
                    ) {
                        Text("अपलोड करा", fontSize = 11.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

// 5. MOBILE RECHARGE DIALOG (PhonePe Style UI/UX)
@Composable
fun MobileRechargeDialog(
    walletBalance: Double,
    availableCreditLimit: Double,
    rechargePlans: List<com.example.data.RechargePlan>,
    loanSettings: com.example.data.LoanSettings,
    viewModel: com.example.data.RepairViewModel,
    onDismiss: () -> Unit,
    onSuccess: (String, String, Double, String) -> Unit,
    onPayWithQR: (Double, String, String, () -> Unit) -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current

    var searchContactQuery by remember { mutableStateOf("") }
    var selectedContactName by remember { mutableStateOf("") }
    var selectedContactPhone by remember { mutableStateOf("") }

    val contactLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.PickContact()
    ) { uri ->
        if (uri != null) {
            try {
                val contentResolver = context.contentResolver
                val cursor = contentResolver.query(uri, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idIndex = cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID)
                    val contactId = if (idIndex >= 0) cursor.getString(idIndex) else ""
                    val hasPhoneIndex = cursor.getColumnIndex(android.provider.ContactsContract.Contacts.HAS_PHONE_NUMBER)
                    val hasPhone = if (hasPhoneIndex >= 0) cursor.getString(hasPhoneIndex) else "0"
                    
                    var phoneNo = ""
                    var contactName = ""
                    
                    val nameIndex = cursor.getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME)
                    if (nameIndex >= 0) {
                        contactName = cursor.getString(nameIndex)
                    }
                    
                    if (hasPhone == "1" && contactId.isNotEmpty()) {
                        val phonesCursor = contentResolver.query(
                            android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(contactId),
                            null
                        )
                        if (phonesCursor != null && phonesCursor.moveToFirst()) {
                            val numIndex = phonesCursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER)
                            if (numIndex >= 0) {
                                phoneNo = phonesCursor.getString(numIndex)
                            }
                            phonesCursor.close()
                        }
                    }
                    cursor.close()
                    
                    val cleanPhone = phoneNo.replace(Regex("[^0-9]"), "").takeLast(10)
                    if (cleanPhone.length == 10) {
                        searchContactQuery = cleanPhone
                        selectedContactName = contactName.ifBlank { "Selected Contact" }
                        selectedContactPhone = cleanPhone
                    } else {
                        val cleanRaw = phoneNo.filter { it.isDigit() }.takeLast(10)
                        if (cleanRaw.length == 10) {
                            searchContactQuery = cleanRaw
                            selectedContactName = contactName.ifBlank { "Selected Contact" }
                            selectedContactPhone = cleanRaw
                        } else {
                            android.widget.Toast.makeText(context, "निवडलेल्या संपर्कामध्ये वैध १० अंकी नंबर नाही / Selected contact does not have a valid 10-digit number", android.widget.Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: Exception) {
                android.widget.Toast.makeText(context, "संपर्क निवडण्यात अडचण आली / Error picking contact", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    var currentScreen by remember { mutableStateOf(0) } // 0 = Search/Contacts, 1 = Plan Selection, 2 = Summary & Payment, 3 = QR Code Screen
    
    // Auto-detect operator and circle details
    var operatorAndCircle by remember { mutableStateOf("Jio Prepaid • Maharashtra & Goa") }
    
    var selectedPlan by remember { mutableStateOf<com.example.data.RechargePlan?>(null) }
    var planSearchQuery by remember { mutableStateOf("") }
    var activeTab by remember { mutableStateOf("Popular") } // "Popular", "True 5G Unlimited", "Top Up", "Yearly"
    var paymentMethod by remember { mutableStateOf("Wallet") } // "Wallet", "PayLater", "QRCode"

    // Mock Recent Contacts
    val recents = listOf(
        Triple("Sandip Rathod", "9860856702", "Last Recharge: ₹19 on 29 Jun 2026"),
        Triple("Durgesh Thakare", "9511261904", "Last Recharge: ₹19 on 20 Jun 2026"),
        Triple("Kartik", "8010424571", "Last Recharge: ₹19 on 17 Apr 2026")
    )

    // Handle manual 10-digit input to transition automatically
    LaunchedEffect(searchContactQuery) {
        val cleanNum = searchContactQuery.filter { it.isDigit() }
        if (cleanNum.length == 10) {
            viewModel.showLoading("मोबाईल ऑपरेटर तपासत आहे... / Detecting operator details...")
            delay(1200)
            viewModel.hideLoading()
            selectedContactName = "Manual Number"
            selectedContactPhone = cleanNum
            // Auto-detect operator prefix mock
            val prefix = cleanNum.take(2)
            operatorAndCircle = when (prefix) {
                "98", "95" -> "Jio Prepaid • Maharashtra & Goa"
                "80", "70" -> "Airtel Prepaid • Maharashtra & Goa"
                "91", "90" -> "Vi Prepaid • Maharashtra & Goa"
                else -> "BSNL Prepaid • Maharashtra & Goa"
            }
            currentScreen = 1
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background), // Theme-adaptive background
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp) // Leave top space for status bar
                .testTag("phonepe_recharge_dialog")
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header Block
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF5F259F)) // PhonePe Signature Purple
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (currentScreen > 0) {
                                currentScreen--
                            } else {
                                onDismiss()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = when (currentScreen) {
                                0 -> "मोबाईल रिचार्ज / Mobile Recharge"
                                1 -> "रिचार्ज प्लॅन निवडा / Select Plan"
                                2 -> "प्लॅन सारांश / Plan Summary"
                                else -> "क्यूआर स्कॅनर / QR Payment"
                            },
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (currentScreen > 0 && selectedContactPhone.isNotEmpty()) {
                            Text(
                                text = "$selectedContactName • $selectedContactPhone",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Help",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Main screen view switcher
                Box(modifier = Modifier.weight(1f)) {
                    when (currentScreen) {
                        0 -> {
                            // SCREEN 0: Contacts, Search, and Recents
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = PaddingValues(vertical = 16.dp)
                            ) {
                                // Advertisement Banner
                                item {
                                    Card(
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE9FE)),
                                        border = BorderStroke(1.dp, Color(0xFFC7D2FE)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(CircleShape)
                                                    .background(Color(0xFF5F259F)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text("₹", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                            }
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Text(
                                                    text = "मिळवा फ्लॅट ₹३० कॅशबॅक! 🎁",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF4C1D95)
                                                )
                                                Text(
                                                    text = "तुमचा पहिला रिचार्ज करा आणि आकर्षक रिवॉर्ड्स मिळवा.",
                                                    fontSize = 10.sp,
                                                    color = Color.DarkGray
                                                )
                                            }
                                        }
                                    }
                                }

                                // Search Field
                                item {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        OutlinedTextField(
                                            value = searchContactQuery,
                                            onValueChange = { searchContactQuery = it },
                                            placeholder = { Text("नंबर किंवा नाव टाका / Enter Number", color = Color.Gray) },
                                            leadingIcon = {
                                                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
                                            },
                                            modifier = Modifier
                                                .weight(1f)
                                                .testTag("contact_search_input"),
                                            singleLine = true,
                                            shape = RoundedCornerShape(14.dp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedTextColor = Color.Black,
                                                unfocusedTextColor = Color.Black,
                                                focusedContainerColor = Color.White,
                                                unfocusedContainerColor = Color.White,
                                                focusedBorderColor = Color(0xFF5F259F),
                                                unfocusedBorderColor = Color(0xFFCCCCCC)
                                            )
                                        )

                                        IconButton(
                                            onClick = {
                                                try {
                                                    contactLauncher.launch(null)
                                                } catch (e: Exception) {
                                                    android.widget.Toast.makeText(context, "संपर्क निवडण्यात अडचण आली / Contact picker unavailable", android.widget.Toast.LENGTH_SHORT).show()
                                                }
                                            },
                                            modifier = Modifier
                                                .size(54.dp)
                                                .clip(RoundedCornerShape(14.dp))
                                                .background(Color(0xFF5F259F))
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AccountCircle,
                                                contentDescription = "Select from Contacts",
                                                tint = Color.White,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }

                                item {
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "By proceeding further, you allow PhonePe/Aarushi Store to fetch your operator plans and notify expiry details safely.",
                                        fontSize = 9.sp,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }

                        1 -> {
                            // SCREEN 1: Plan Selection Screen
                            val filteredPlans = rechargePlans.filter {
                                (it.category == activeTab || (activeTab == "Popular" && !it.isLoan)) &&
                                (planSearchQuery.isEmpty() || it.price.toString().contains(planSearchQuery) || it.description.contains(planSearchQuery, ignoreCase = true))
                            }

                            Column(modifier = Modifier.fillMaxSize()) {
                                // Contact Detail Header Card
                                Card(
                                    shape = RoundedCornerShape(0.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFF5F259F)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(selectedContactName.take(1), color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("$selectedContactName • $selectedContactPhone", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text(operatorAndCircle, fontSize = 10.sp, color = Color.Gray)
                                        }
                                        Text(
                                            text = "Change",
                                            color = Color(0xFF5F259F),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .clickable { currentScreen = 0 }
                                                .padding(4.dp)
                                            )
                                    }
                                }

                                // Special Offer Scroll View
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 16.dp)
                                ) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    
                                    // Search Plan Bar
                                    OutlinedTextField(
                                        value = planSearchQuery,
                                        onValueChange = { planSearchQuery = it },
                                        placeholder = { Text("उदा. 349, 5G, डेटा प्लॅन शोधा...") },
                                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        shape = RoundedCornerShape(12.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                            focusedBorderColor = Color(0xFF5F259F),
                                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                            focusedLabelColor = Color(0xFF5F259F),
                                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    // Category Tabs row
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState())
                                            .padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        listOf("Popular", "True 5G Unlimited", "Top Up", "Yearly").forEach { tab ->
                                            val isSelected = activeTab == tab
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(20.dp))
                                                    .background(if (isSelected) Color(0xFF5F259F) else Color.White)
                                                    .border(1.dp, if (isSelected) Color.Transparent else Color.LightGray, RoundedCornerShape(20.dp))
                                                    .clickable {
                                                        scope.launch {
                                                            viewModel.showLoading("$tab प्लॅन लोड करत आहे... / Loading $tab plans...")
                                                            delay(700)
                                                            viewModel.hideLoading()
                                                            activeTab = tab
                                                        }
                                                    }
                                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                                            ) {
                                                Text(
                                                    text = tab,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (isSelected) Color.White else Color.DarkGray
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "$activeTab प्लॅनची यादी / Plans",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    // Vertical Plans list
                                    LazyColumn(
                                        verticalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        if (filteredPlans.isEmpty()) {
                                            item {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(30.dp),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text("या कॅटेगरीत कोणतेही प्लॅन सापडले नाहीत.", fontSize = 11.sp, color = Color.Gray)
                                                }
                                            }
                                        }

                                        items(filteredPlans) { plan ->
                                            val isPremiumPlan = plan.price >= 200.0
                                            Card(
                                                shape = RoundedCornerShape(16.dp),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = if (isPremiumPlan) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
                                                ),
                                                border = BorderStroke(
                                                    width = 1.5.dp,
                                                    color = if (isPremiumPlan) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else MaterialTheme.colorScheme.outlineVariant
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .shadow(2.dp, RoundedCornerShape(16.dp))
                                                    .clickable {
                                                        scope.launch {
                                                            viewModel.showLoading("निवडलेल्या प्लॅनची पडताळणी करत आहे... / Validating plan...")
                                                            delay(1000)
                                                            viewModel.hideLoading()
                                                            selectedPlan = plan
                                                            currentScreen = 2
                                                        }
                                                    }
                                            ) {
                                                Column(modifier = Modifier.padding(16.dp)) {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        // Large Bold Price with vibrant Purple Color for maximum visibility
                                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                                            Text(
                                                                text = "₹${plan.price.toInt()}",
                                                                fontSize = 24.sp,
                                                                fontWeight = FontWeight.ExtraBold,
                                                                color = Color(0xFF4C1D95) // Deep rich violet
                                                            )
                                                            if (isPremiumPlan) {
                                                                Spacer(modifier = Modifier.width(8.dp))
                                                                Box(
                                                                    modifier = Modifier
                                                                        .clip(RoundedCornerShape(4.dp))
                                                                        .background(Color(0xFF10B981))
                                                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                                                ) {
                                                                    Text("लोकप्रिय / POPULAR", fontSize = 8.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                                                }
                                                            }
                                                        }
                                                        
                                                        // High readability Validity Badge
                                                        Box(
                                                            modifier = Modifier
                                                                .clip(RoundedCornerShape(8.dp))
                                                                .background(Color(0xFFEDE9FE))
                                                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                                        ) {
                                                            Text(
                                                                text = "वैधता / Validity: ${plan.validity}",
                                                                fontSize = 11.sp,
                                                                fontWeight = FontWeight.ExtraBold,
                                                                color = Color(0xFF5F259F)
                                                            )
                                                        }
                                                    }
                                                    
                                                    Spacer(modifier = Modifier.height(10.dp))
                                                    
                                                    // Data and benefits details
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                                            Icon(
                                                                imageVector = Icons.Default.Verified,
                                                                contentDescription = null,
                                                                tint = Color(0xFF10B981),
                                                                modifier = Modifier.size(14.dp)
                                                            )
                                                            Spacer(modifier = Modifier.width(6.dp))
                                                            Text(
                                                                text = "डेटा / Data: ${plan.data}",
                                                                fontSize = 12.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = MaterialTheme.colorScheme.onSurface
                                                            )
                                                        }
                                                    }

                                                    Spacer(modifier = Modifier.height(6.dp))

                                                    Text(
                                                        text = plan.description,
                                                        fontSize = 11.sp,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                        fontWeight = FontWeight.Medium,
                                                        maxLines = 2,
                                                        overflow = TextOverflow.Ellipsis,
                                                        lineHeight = 15.sp
                                                    )
                                                    
                                                    Spacer(modifier = Modifier.height(10.dp))
                                                    
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                                            val tags = if (plan.isLoan) listOf("पे-लेटर उपलब्ध", "प्रोसेसिंग फी ₹०", "क्रेडिट स्कोर") else listOf("5G", "Prime", "Gemini AI")
                                                            tags.forEach { tag ->
                                                                Box(
                                                                    modifier = Modifier
                                                                        .clip(RoundedCornerShape(4.dp))
                                                                        .background(MaterialTheme.colorScheme.secondaryContainer)
                                                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                                                ) {
                                                                    Text(tag, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.Bold)
                                                                }
                                                            }
                                                        }
                                                        Text(
                                                            text = "खरेदी करा / BUY NOW >",
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = Color(0xFF5F259F)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        2 -> {
                            // SCREEN 2: Plan Summary & Payment Options
                            val plan = selectedPlan ?: rechargePlans.first()
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp),
                                contentPadding = PaddingValues(vertical = 16.dp)
                            ) {
                                // Selected Plan details card
                                item {
                                    Card(
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.White),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column {
                                                    Text("निवडलेला प्लॅन / Selected Plan", fontSize = 10.sp, color = Color.Gray)
                                                    Text(
                                                        text = "₹${plan.price.toInt()}",
                                                        fontSize = 24.sp,
                                                        fontWeight = FontWeight.ExtraBold,
                                                        color = Color(0xFF5F259F)
                                                    )
                                                }
                                                Button(
                                                    onClick = { currentScreen = 1 },
                                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDE9FE)),
                                                    shape = RoundedCornerShape(10.dp)
                                                ) {
                                                    Text("बदला / Change", color = Color(0xFF5F259F), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                                }
                                            }
                                            
                                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF1F5F9))

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column {
                                                    Text("वैधता / Validity", fontSize = 10.sp, color = Color.Gray)
                                                    Text(plan.validity, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                                }
                                                Column(horizontalAlignment = Alignment.End) {
                                                    Text("डेटा / Total Data", fontSize = 10.sp, color = Color.Gray)
                                                    Text(plan.data, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                text = "तपशील / Description: ${plan.description}",
                                                fontSize = 11.sp,
                                                color = Color.DarkGray
                                            )
                                        }
                                    }
                                }

                                // Payment Options list
                                item {
                                    Text(
                                        text = "पेमेंट पद्धत निवडा / Select Payment Method",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.DarkGray
                                    )
                                }

                                // 1. Wallet Option
                                item {
                                    val isSelected = paymentMethod == "Wallet"
                                    val hasEnough = walletBalance >= plan.price
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (isSelected) Color(0xFFF3E8FF) else Color.White)
                                            .border(1.dp, if (isSelected) Color(0xFF5F259F) else Color.Transparent, RoundedCornerShape(12.dp))
                                            .clickable { paymentMethod = "Wallet" }
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccountBalanceWallet,
                                            contentDescription = null,
                                            tint = if (isSelected) Color(0xFF5F259F) else Color.Gray,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("आरुषी स्टोअर वॉलेट / Wallet", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text("शिल्लक बॅलन्स / Balance: ₹${String.format(Locale.US, "%.1f", walletBalance)}", fontSize = 10.sp, color = Color.Gray)
                                        }
                                        RadioButton(selected = isSelected, onClick = { paymentMethod = "Wallet" })
                                    }
                                }

                                // 2. Pay Later Option
                                item {
                                    val isSelected = paymentMethod == "PayLater"
                                    val hasEnough = availableCreditLimit >= plan.price
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (isSelected) Color(0xFFF3E8FF) else Color.White)
                                            .border(1.dp, if (isSelected) Color(0xFF5F259F) else Color.Transparent, RoundedCornerShape(12.dp))
                                            .clickable { paymentMethod = "PayLater" }
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CreditCard,
                                            contentDescription = null,
                                            tint = if (isSelected) Color(0xFF5F259F) else Color.Gray,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("पे लेटर (क्रेडिट लिमिट) / Pay Later", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF065F46))
                                            Text("उपलब्ध मर्यादा / Credit Limit: ₹${String.format(Locale.US, "%.1f", availableCreditLimit)}", fontSize = 10.sp, color = Color.Gray)
                                            
                                            // Dynamic fee details from Admin Panel Settings
                                            val isUnder100 = plan.price < 100.0
                                            val fee = if (isUnder100) loanSettings.processingFeeUnder100 else loanSettings.processingFeeOver100
                                            val interest = plan.price * (loanSettings.interestRatePercent / 100.0)
                                            val term = if (isUnder100) "8 दिवस मुदत" else "वैधता: ${plan.validity} मुदत"
                                            
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = "व्याज: ${loanSettings.interestRatePercent}% (+₹${String.format(Locale.US, "%.1f", interest)}) • प्रोसेसिंग फी: +₹${fee.toInt()} • $term",
                                                fontSize = 9.sp,
                                                color = Color(0xFF047857),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        RadioButton(selected = isSelected, onClick = { paymentMethod = "PayLater" })
                                    }
                                }

                                // 3. QR Code Option
                                item {
                                    val isSelected = paymentMethod == "QRCode"
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (isSelected) Color(0xFFF3E8FF) else Color.White)
                                            .border(1.dp, if (isSelected) Color(0xFF5F259F) else Color.Transparent, RoundedCornerShape(12.dp))
                                            .clickable { paymentMethod = "QRCode" }
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Build, // Placeholder for QR scanner
                                            contentDescription = null,
                                            tint = if (isSelected) Color(0xFF5F259F) else Color.Gray,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("UPI QR कोड स्कॅन करून भरा / Pay via QR Code 🏁", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text("PhonePe, GPay, Paytm कोणत्याही ॲपने भरा", fontSize = 10.sp, color = Color.Gray)
                                        }
                                        RadioButton(selected = isSelected, onClick = { paymentMethod = "QRCode" })
                                    }
                                }

                                // Validation text & Proceed Button
                                item {
                                    val limitToCheck = if (paymentMethod == "Wallet") walletBalance else if (paymentMethod == "PayLater") availableCreditLimit else 99999.0
                                    val canPay = limitToCheck >= plan.price

                                    if (!canPay) {
                                        Text(
                                            text = if (paymentMethod == "Wallet") "❌ अपुरे वॉलेट बॅलन्स! कृपया बॅलन्स जोडा." else "❌ अपुरी क्रेडिट लिमिट! तात्काळ मर्यादा वाढवण्यासाठी क्रेडिट स्कोर सुधारा.",
                                            fontSize = 11.sp,
                                            color = Color.Red,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Button(
                                        onClick = {
                                            if (canPay) {
                                                if (paymentMethod == "QRCode") {
                                                    onPayWithQR(
                                                        plan.price,
                                                        "RECHARGE",
                                                        "Recharge for $selectedContactPhone ($operatorAndCircle | ${plan.data} Plan)"
                                                    ) {
                                                        onSuccess(selectedContactPhone, "$operatorAndCircle | ${plan.data} Plan", plan.price, "QRCode")
                                                    }
                                                } else {
                                                    onSuccess(selectedContactPhone, "$operatorAndCircle | ${plan.data} Plan", plan.price, paymentMethod)
                                                }
                                            }
                                        },
                                        enabled = canPay,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(52.dp)
                                            .testTag("phonepe_pay_btn"),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F259F)),
                                        shape = RoundedCornerShape(14.dp)
                                    ) {
                                        Text(
                                            text = if (paymentMethod == "QRCode") "QR कोड जनरेट करा (₹${plan.price.toInt()})" else "पेमेंट पूर्ण करा आणि रिचार्ज करा (₹${plan.price.toInt()})",
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        else -> {
                            // SCREEN 3: QR Code Scanner Simulation
                            val plan = selectedPlan ?: rechargePlans.first()
                            var simSuccess by remember { mutableStateOf(false) }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Card(
                                    shape = RoundedCornerShape(24.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                    modifier = Modifier.width(300.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(24.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "आरुषी मल्टिसर्व्हिसेस UPI",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 14.sp,
                                            color = Color(0xFF5F259F)
                                        )
                                        Spacer(modifier = Modifier.height(14.dp))

                                        // Beautiful Custom QR Code using Canvas Vector Art
                                        Box(
                                            modifier = Modifier
                                                .size(200.dp)
                                                .border(2.dp, Color(0xFF5F259F), RoundedCornerShape(12.dp))
                                                .padding(12.dp)
                                        ) {
                                            Canvas(modifier = Modifier.fillMaxSize()) {
                                                // Outer Corners
                                                drawRect(color = Color.Black, topLeft = Offset(0f, 0f), size = androidx.compose.ui.geometry.Size(50f, 50f))
                                                drawRect(color = Color.White, topLeft = Offset(10f, 10f), size = androidx.compose.ui.geometry.Size(30f, 30f))
                                                drawRect(color = Color.Black, topLeft = Offset(15f, 15f), size = androidx.compose.ui.geometry.Size(20f, 20f))

                                                drawRect(color = Color.Black, topLeft = Offset(size.width - 50f, 0f), size = androidx.compose.ui.geometry.Size(50f, 50f))
                                                drawRect(color = Color.White, topLeft = Offset(size.width - 40f, 10f), size = androidx.compose.ui.geometry.Size(30f, 30f))
                                                drawRect(color = Color.Black, topLeft = Offset(size.width - 35f, 15f), size = androidx.compose.ui.geometry.Size(20f, 20f))

                                                drawRect(color = Color.Black, topLeft = Offset(0f, size.height - 50f), size = androidx.compose.ui.geometry.Size(50f, 50f))
                                                drawRect(color = Color.White, topLeft = Offset(10f, size.height - 40f), size = androidx.compose.ui.geometry.Size(30f, 30f))
                                                drawRect(color = Color.Black, topLeft = Offset(15f, size.height - 35f), size = androidx.compose.ui.geometry.Size(20f, 20f))

                                                // Draw center high-tech matrix lines and patterns
                                                drawCircle(color = Color(0xFF5F259F), center = Offset(size.width / 2, size.height / 2), radius = 25f)
                                                
                                                val strokeWidth = 8f
                                                // Random bits
                                                for (i in 0..10) {
                                                    val x = (50..150).random().toFloat()
                                                    val y = (50..150).random().toFloat()
                                                    drawRect(color = Color.Black, topLeft = Offset(x, y), size = androidx.compose.ui.geometry.Size(15f, 15f))
                                                }
                                                // Tech scanner grid lines
                                                drawLine(color = Color(0xFF10B981).copy(alpha = 0.6f), start = Offset(0f, size.height / 2), end = Offset(size.width, size.height / 2), strokeWidth = strokeWidth)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Text(
                                            text = "₹${plan.price.toInt()}",
                                            fontSize = 28.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = "स्कॅन करून कोणत्याही ॲपद्वारे पे करा",
                                            fontSize = 11.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Simulated payment complete button
                                    Button(
                                        onClick = {
                                            simSuccess = true
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text("स्कॅन यशस्वी (Simulate Pay) ✅", color = Color.White, fontSize = 12.sp)
                                    }
                                }

                                if (simSuccess) {
                                    LaunchedEffect(Unit) {
                                        delay(1200)
                                        onSuccess(selectedContactPhone, "$operatorAndCircle | ${plan.data} Plan", plan.price, "QRCode")
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFFD1FAE5))
                                    ) {
                                        Text(
                                            text = "🎉 पेमेंट यशस्वी! रिचार्ज प्रक्रिया पूर्ण होत आहे...",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF065F46),
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Simple helper Composable for selected check button state
@Composable
fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .border(2.dp, if (selected) Color(0xFF5F259F) else Color.Gray, CircleShape)
            .background(if (selected) Color(0xFF5F259F).copy(alpha = 0.2f) else Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF5F259F))
            )
        }
    }
}

data class RadioButtonColors(
    val selectedColor: Color
)

@Composable
fun rememberScrollState() = androidx.compose.foundation.rememberScrollState()


// REALTIME PAYMENT QR DIALOG WITH 10-MINUTE EXPIRY & REAL-TIME ADMIN APPROVAL
@Composable
fun RealtimePaymentQRDialog(
    amount: Double,
    paymentPurpose: String,
    details: String,
    viewModel: com.example.data.RepairViewModel,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    val currentUser = viewModel.currentUserProfile.collectAsState().value ?: return

    var txnId by remember { mutableStateOf("TXN_${System.currentTimeMillis()}") }
    var timeLeftSeconds by remember { mutableIntStateOf(600) } // 10 minutes
    var isExpired by remember { mutableStateOf(false) }
    var userConfirmedPayment by remember { mutableStateOf(false) }
    var approvalStatus by remember { mutableStateOf("PENDING") }

    // Countdown Timer logic
    LaunchedEffect(timeLeftSeconds) {
        if (timeLeftSeconds > 0) {
            delay(1000)
            timeLeftSeconds--
        } else {
            isExpired = true
        }
    }

    // Initialize transaction in Realtime Database
    LaunchedEffect(Unit) {
        val rawNote = "${currentUser.phone}_${paymentPurpose.replace(" ", "_")}".take(40)
        val encodedNote = java.net.URLEncoder.encode(rawNote, "UTF-8")
        val upiLink = "upi://pay?pa=paytmqr6v6iy7@ptys&pn=Aarushi%20Electronics&am=$amount&tn=$encodedNote"
        val encodedUpi = java.net.URLEncoder.encode(upiLink, "UTF-8")
        val qrUri = "https://api.qrserver.com/v1/create-qr-code/?size=500x500&data=$encodedUpi"
        
        val txn = com.example.data.Transaction(
            id = txnId,
            type = paymentPurpose,
            customerEmail = currentUser.email,
            customerName = currentUser.name,
            customerPhone = currentUser.phone,
            details = details,
            amount = amount,
            status = "PENDING",
            timestamp = System.currentTimeMillis(),
            qrCodeUri = qrUri
        )
        viewModel.submitTransaction(txn)
    }

    // Real-time listener on transaction ID
    LaunchedEffect(txnId) {
        if (viewModel.isFirebaseConnected.value) {
            val rtdb = com.google.firebase.database.FirebaseDatabase.getInstance()
            rtdb.getReference("transactions").child(txnId)
                .addValueEventListener(object : com.google.firebase.database.ValueEventListener {
                    override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                        val updatedTxn = snapshot.getValue(com.example.data.Transaction::class.java)
                        if (updatedTxn != null) {
                            approvalStatus = updatedTxn.status
                        }
                    }
                    override fun onCancelled(error: com.google.firebase.database.DatabaseError) {}
                })
        }
    }

    // Success state handling
    if (approvalStatus == "SUCCESSFUL") {
        Dialog(onDismissRequest = {}) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "🎉 पेमेंट यशस्वी! / Payment Successful!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF065F46)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "तुमचे पेमेंट यशस्वीरीत्या पूर्ण झाले आहे आणि ऍडमिनद्वारे मंजूर करण्यात आले आहे.",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            onSuccess()
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) {
                        Text("ओके / OK", color = Color.White)
                    }
                }
            }
        }
    } else {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "आरुषी मल्टिसर्व्हिसेस UPI पेमेंट 💳",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "पेमेंट उद्देश / Purpose: $paymentPurpose",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    if (isExpired) {
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .border(2.dp, Color.Red, RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "क्यूआर कोड कालबाह्य झाला!\nQR Code Expired!",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                fontSize = 14.sp
                            )
                        }
                    } else {
                        // QR Code display via Coil
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .border(2.dp, Color(0xFF5F259F), RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val rawNote = "${currentUser.phone}_${paymentPurpose.replace(" ", "_")}".take(40)
                            val encodedNote = java.net.URLEncoder.encode(rawNote, "UTF-8")
                            val upiLink = "upi://pay?pa=paytmqr6v6iy7@ptys&pn=Aarushi%20Electronics&am=$amount&tn=$encodedNote"
                            val encodedUpi = java.net.URLEncoder.encode(upiLink, "UTF-8")
                            val qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=500x500&data=$encodedUpi"
                            
                            coil.compose.AsyncImage(
                                model = qrUrl,
                                contentDescription = "UPI QR Code",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Timer display
                    val minutes = timeLeftSeconds / 60
                    val seconds = timeLeftSeconds % 60
                    Text(
                        text = String.format(Locale.getDefault(), "वेळ शिल्लक / Time Left: %02d:%02d", minutes, seconds),
                        fontWeight = FontWeight.Bold,
                        color = if (timeLeftSeconds < 60) Color.Red else Color(0xFF2F80ED),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "₹${amount}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Text(
                        text = "स्कॅन करून कोणत्याही UPI ॲपद्वारे पे करा",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (!userConfirmedPayment) {
                        Button(
                            onClick = {
                                if (!isExpired) {
                                    userConfirmedPayment = true
                                    // Update transaction status in RTDB to PENDING_ADMIN_APPROVAL
                                    val rtdb = com.google.firebase.database.FirebaseDatabase.getInstance()
                                    rtdb.getReference("transactions").child(txnId).child("status").setValue("PENDING_ADMIN_APPROVAL")
                                    
                                    // Also log a local booking/transaction
                                    viewModel.logTransaction(
                                        serviceType = "QR_PAYMENT_SUBMITTED",
                                        name = currentUser.name,
                                        phone = currentUser.phone,
                                        description = "Submitted ₹$amount payment for approval"
                                    )
                                }
                            },
                            enabled = !isExpired,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("मी पेमेंट केले आहे (Confirm Pay) ✅", color = Color.White, fontSize = 12.sp)
                        }
                    } else {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color(0xFFD97706),
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "पडताळणी प्रलंबित आहे... / Verification Pending...",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                                Text(
                                    text = "कृपया थांबा, ऍडमिन पेमेंट व्हेरीफाय करत आहे.",
                                    fontSize = 10.sp,
                                    color = Color(0xFFB45309),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "रद्द करा / Cancel",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable(onClick = onDismiss)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}


// 6. MOBILE LOAN DIALOG
@Composable
fun MobileLoanDialog(
    onDismiss: () -> Unit,
    onLoanSuccess: (Double) -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("Jio") }
    var selectedLoanPack by remember { mutableStateOf("₹25 Loan Pack") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("mobile_loan_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "तात्काळ रिचार्ज लोन / Emergency Loan ⚡",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD97706)
                )
                Text(
                    text = "शून्य% व्याज! रिचार्ज करा आणि आरुषी स्टोअरला सोयीनुसार नंतर पे करा.",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("मोबाईल नंबर / Mobile Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("loan_phone_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = Color(0xFFD97706),
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        focusedLabelColor = Color(0xFFD97706),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "ऑपरेटर / Operator",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Jio", "Airtel", "Vi", "BSNL").forEach { op ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (operator == op) Color(0xFFFEF3C7) else Color(0xFFF1F5F9))
                                .border(
                                    width = 1.dp,
                                    color = if (operator == op) Color(0xFFD97706) else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { operator = op }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = op,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (operator == op) Color(0xFFD97706) else Color.DarkGray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "लोन पॅक निवडा / Choose Loan Pack",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("₹15 Pack", "₹25 Pack", "₹61 Pack").forEach { pack ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selectedLoanPack.contains(pack)) Color(0xFFFEF3C7) else Color(0xFFF1F5F9))
                                .border(
                                    width = 1.dp,
                                    color = if (selectedLoanPack.contains(pack)) Color(0xFFD97706) else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedLoanPack = "$pack Loan" }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = pack,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedLoanPack.contains(pack)) Color(0xFFD97706) else Color.DarkGray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "रद्द करा / Cancel",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable(onClick = onDismiss)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .testTag("cancel_loan")
                    )

                    Button(
                        onClick = {
                            if (phone.length >= 10) {
                                val loanAmount = selectedLoanPack.replace(Regex("[^0-9]"), "").toDoubleOrNull() ?: 25.0
                                onLoanSuccess(loanAmount)
                            }
                        },
                        enabled = phone.length >= 10,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("confirm_loan")
                    ) {
                        Text("लोन मंजुर करा", fontSize = 11.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

// 7. MOBILE COMBO PROTECTION DIALOG
@Composable
fun MobileComboProtectionDialog(
    walletBalance: Double,
    protectionPlans: List<com.example.data.ProtectionPlan>,
    onDismiss: () -> Unit,
    onSuccess: (String, String, Double, Int) -> Unit,
    onPayWithQR: (String, String, Double) -> Unit
) {
    var brand by remember { mutableStateOf("") }
    var serial by remember { mutableStateOf("") }
    var isOldMobile by remember { mutableStateOf(false) }
    
    val context = androidx.compose.ui.platform.LocalContext.current
    
    val activePlans = protectionPlans.ifEmpty {
        listOf(
            com.example.data.ProtectionPlan("1", "स्क्रीन ग्लास सुरक्षा", 199.0, "12 महिने मोफत स्क्रीन ग्लास रिप्लेसमेंट", 12),
            com.example.data.ProtectionPlan("2", "फुल स्क्रीन + एलसीडी कॉम्बो", 499.0, "एलसीडी डॅमेज आणि संपूर्ण स्क्रीन प्रोटेक्शन", 12),
            com.example.data.ProtectionPlan("3", "ऑल-इन-वन पाणी + स्क्रीन कॉम्बो", 799.0, "वाॅटर डॅमेज + स्क्रीन संरक्षण", 12)
        )
    }
    
    var selectedPlanEntity by remember { mutableStateOf(activePlans.first()) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("mobile_combo_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "मोबाईल कॉम्बो सुरक्षा योजना / Combo Protection Plan 🛡️",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A8A)
                )
                Text(
                    text = "स्क्रीन, ग्लास आणि वॉटर डॅमेजवर परिपूर्ण विनामूल्य रिप्लेसमेंट संरक्षण",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Photo Requirement Warning Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                    border = BorderStroke(1.dp, Color(0xFF3B82F6)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Info",
                                tint = Color(0xFF2563EB),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "महत्त्वाची सूचना / Photo Requirement:",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E40AF)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "वॉरंटी सक्रिय करण्यासाठी व्हॉट्सॲपवर खालील ४-५ फोटो आवश्यक आहेत:\n" +
                                    "१. बिलाचा फोटो (Bill Photo)\n" +
                                    "२. प्रॉडक्टचे फोटो (Product Photos)\n" +
                                    "३. चालू कंडिशनचा फोटो (Working Condition Photo)",
                            fontSize = 9.sp,
                            color = Color(0xFF1E40AF),
                            lineHeight = 11.sp
                        )
                    }
                }

            OutlinedTextField(
                value = brand,
                onValueChange = { brand = it },
                label = { Text("मोबाईलचे नाव व मॉडेल / Phone Model (e.g. Redmi Note 12)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("combo_model_input"),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = Color(0xFF1E3A8A),
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    focusedLabelColor = Color(0xFF1E3A8A),
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = serial,
                onValueChange = { serial = it },
                label = { Text("IMEI क्रमांक / IMEI Number") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("combo_imei_input"),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = Color(0xFF1E3A8A),
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    focusedLabelColor = Color(0xFF1E3A8A),
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "संरक्षण पॅकेज / Protection Plan Option",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            
            activePlans.forEach { plan ->
                val isSelected = selectedPlanEntity.id == plan.id
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) Color(0xFFE5F1FF) else Color(0xFFF1F5F9))
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color(0xFF1E3A8A) else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedPlanEntity = plan }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Shield",
                        tint = if (isSelected) Color(0xFF1E3A8A) else Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "${plan.title} (₹${plan.price.toInt()})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color(0xFF1E3A8A) else Color.DarkGray
                        )
                        Text(
                            text = plan.description,
                            fontSize = 9.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Is Old Mobile Row Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { isOldMobile = !isOldMobile }
                    .background(if (isOldMobile) Color(0xFFFEF2F2) else Color(0xFFF1F5F9))
                    .border(
                        width = 1.dp,
                        color = if (isOldMobile) Color(0xFFEF4444) else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (isOldMobile) Color(0xFFEF4444) else Color.White)
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (isOldMobile) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Checked",
                            tint = Color.White,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "हा जुना मोबाईल आहे का? / Is this an old mobile?",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isOldMobile) Color(0xFF991B1B) else Color.DarkGray
                    )
                    Text(
                        text = "जुन्या मोबाईलसाठी अतिरिक्त शुल्क (₹१५०) लागू होईल आणि वॉरंटी १५ दिवसांनंतर सक्रिय होईल.",
                        fontSize = 9.sp,
                        color = if (isOldMobile) Color(0xFFEF4444) else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val basePrice = selectedPlanEntity.price
            val extraFee = if (isOldMobile) 150.0 else 0.0
            val priceVal = basePrice + extraFee
            val canPay = walletBalance >= priceVal

            if (!canPay) {
                Text(
                    text = "❌ अपुरे बॅलन्स! कॉम्बो प्लॅनसाठी पैसे जोडा.",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "रद्द करा / Cancel",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    modifier = Modifier
                        .clickable(onClick = onDismiss)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .testTag("cancel_combo")
                )

                Button(
                    onClick = {
                        if (brand.isNotBlank()) {
                            val typeText = if (isOldMobile) "जुना मोबाईल (Old Phone - १५ दिवस वेटिंग)" else "नवीन मोबाईल (New Phone)"
                            val whatsappMsg = "नमस्कार आरुषी मल्टिसर्व्हिसेस, मी मोबाईल कॉम्बो सुरक्षा योजना सक्रिय करू इच्छितो.\n" +
                                    "मोबाईल: $brand\n" +
                                    "IMEI: $serial\n" +
                                    "योजना: ${selectedPlanEntity.title}\n" +
                                    "प्रकार: $typeText\n" +
                                    "एकूण किंमत: ₹${priceVal.toInt()}\n" +
                                    "मी खालील आवश्यक ४-५ फोटो पाठवत आहे:\n" +
                                    "१. बिलाचा फोटो (Bill Photo)\n" +
                                    "२. प्रॉडक्टचे फोटो (Product Photos)\n" +
                                    "३. चालू कंडिशनचा फोटो (Working Condition Photo)"
                            try {
                                val intent = android.content.Intent(
                                    android.content.Intent.ACTION_VIEW,
                                    android.net.Uri.parse("https://api.whatsapp.com/send?phone=919860856702&text=${java.net.URLEncoder.encode(whatsappMsg, "UTF-8")}")
                                )
                                context.startActivity(intent)
                            } catch (e: Exception) {}
                            
                            onPayWithQR(brand, "${serial.ifBlank { "IMEI9827361827" }} ${if (isOldMobile) "(Old Phone)" else ""}", priceVal)
                        }
                    },
                    enabled = brand.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F259F)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("UPI QR ने पे (₹${priceVal.toInt()})", fontSize = 10.sp, color = Color.White)
                }

                Button(
                    onClick = {
                        if (brand.isNotBlank() && canPay) {
                            val typeText = if (isOldMobile) "जुना मोबाईल (Old Phone - १५ दिवस वेटिंग)" else "नवीन मोबाईल (New Phone)"
                            val whatsappMsg = "नमस्कार आरुषी मल्टिसर्व्हिसेस, मी मोबाईल कॉम्बो सुरक्षा योजना खरेदी केली आहे.\n" +
                                    "मोबाईल: $brand\n" +
                                    "IMEI: $serial\n" +
                                    "योजना: ${selectedPlanEntity.title}\n" +
                                    "प्रकार: $typeText\n" +
                                    "एकूण किंमत: ₹${priceVal.toInt()}\n" +
                                    "मी खालील आवश्यक ४-५ फोटो पाठवत आहे:\n" +
                                    "१. बिलाचा फोटो (Bill Photo)\n" +
                                    "२. प्रॉडक्टचे फोटो (Product Photos)\n" +
                                    "३. चालू कंडिशनचा फोटो (Working Condition Photo)"
                            try {
                                val intent = android.content.Intent(
                                    android.content.Intent.ACTION_VIEW,
                                    android.net.Uri.parse("https://api.whatsapp.com/send?phone=919860856702&text=${java.net.URLEncoder.encode(whatsappMsg, "UTF-8")}")
                                )
                                context.startActivity(intent)
                            } catch (e: Exception) {}

                            onSuccess(brand, "${serial.ifBlank { "IMEI9827361827" }} ${if (isOldMobile) "(Old Phone)" else ""}", priceVal, 12)
                        }
                    },
                    enabled = brand.isNotBlank() && canPay,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("confirm_combo")
                ) {
                    Text("खरेदी (₹${priceVal.toInt()})", fontSize = 10.sp, color = Color.White)
                }
            }
        }
    }
}
}

// 8. TAX INVOICE DIALOG
@Composable
fun TaxInvoiceDialog(
    item: WarrantyItemEntity,
    customerName: String,
    onDismiss: () -> Unit
) {
    val dateStr = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(item.purchaseDate))
    val priceVal = if (item.itemName.contains("Combo")) 499.0 else if (item.itemName.contains("Recharge")) 299.0 else 1200.0
    val cgst = priceVal * 0.09
    val sgst = priceVal * 0.09
    val totalVal = priceVal + cgst + sgst

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("tax_invoice_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header Block
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "आरुषी इलेक्ट्रॉनिक्स",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "Aarushi Electronics & Sales",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFE5F1FF))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "TAX INVOICE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2F80ED)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFFE2E8F0))
                Spacer(modifier = Modifier.height(8.dp))

                // Invoice metadata
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(text = "GSTIN: 27AARUS8923K1ZP", fontSize = 9.sp, color = Color.Gray)
                        Text(text = "बिल क्र. / Invoice: #INV-2026-${item.id}", fontSize = 9.sp, color = Color.Gray)
                        Text(text = "दिनांक / Date: $dateStr", fontSize = 9.sp, color = Color.Gray)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "ग्राहक / Client: $customerName", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                        Text(text = "S/N: ${item.serialNumber}", fontSize = 9.sp, color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFFE2E8F0))
                Spacer(modifier = Modifier.height(8.dp))

                // Item Table Header
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "विवरण / Product Description", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.weight(2f))
                    Text(text = "कालावधी", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(text = "रक्कम / Price", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                }
                Spacer(modifier = Modifier.height(6.dp))

                // Item Table Row
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = item.itemName, fontSize = 11.sp, color = Color.Black, modifier = Modifier.weight(2f))
                    Text(text = if (item.warrantyDurationMonths > 0) "${item.warrantyDurationMonths} Months" else "One-Time", fontSize = 11.sp, color = Color.Black, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(text = "₹${"%.2f".format(priceVal)}", fontSize = 11.sp, color = Color.Black, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9))
                Spacer(modifier = Modifier.height(8.dp))

                // Calculations
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "करपात्र मूल्य / Taxable Value:", fontSize = 10.sp, color = Color.Gray)
                        Text(text = "₹${"%.2f".format(priceVal)}", fontSize = 10.sp, color = Color.Gray)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "CGST (9%):", fontSize = 10.sp, color = Color.Gray)
                        Text(text = "₹${"%.2f".format(cgst)}", fontSize = 10.sp, color = Color.Gray)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "SGST (9%):", fontSize = 10.sp, color = Color.Gray)
                        Text(text = "₹${"%.2f".format(sgst)}", fontSize = 10.sp, color = Color.Gray)
                    }
                    HorizontalDivider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(vertical = 4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "एकूण देय रक्कम / Total Paid:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text(text = "₹${"%.2f".format(totalVal)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2F80ED))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Footer stamp/text
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F4EA))
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Success Paid",
                            tint = Color(0xFF137333),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "वॉलेट पेमेंट मंजूर - संगणकीकृत पावती",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF137333)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { /* simulated share action */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share invoice",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("शेअर करा", fontSize = 11.sp, color = Color.DarkGray)
                    }

                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F80ED)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).testTag("close_invoice_btn")
                    ) {
                        Text("बंद करा", fontSize = 11.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

// 12. CREDIT SCORE REPORT CARD & SIMULATOR
@Composable
fun CreditScoreReportCard(
    creditScore: Int,
    onScoreChanged: (Int) -> Unit
) {
    var showAdminPanel by remember { mutableStateOf(false) }
    var inputScoreString by remember { mutableStateOf(creditScore.toString()) }
    var isSyncing by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("credit_score_report_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF3E8FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = "Credit Score",
                            tint = Color(0xFF8B5CF6),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "तुमचा क्रेडिट स्कोर रिपोर्ट",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "Your Credit Score Report",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
                
                // Show/Hide Admin Simulator Button
                TextButton(
                    onClick = { showAdminPanel = !showAdminPanel },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF8B5CF6))
                ) {
                    Text(
                        text = if (showAdminPanel) "सिम्युलेटर बंद करा" else "डेटाबेस सिम्युलेटर",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(14.dp))
            
            // Score Display with colored visual indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8FAFC), RoundedCornerShape(16.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "क्रेडिट स्कोर / Credit Score",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "$creditScore",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = when {
                                creditScore >= 750 -> Color(0xFF10B981) // Excellent Green
                                creditScore >= 650 -> Color(0xFFF59E0B) // Good Amber
                                else -> Color(0xFFEF4444) // Poor Red
                            }
                        )
                        
                        val scoreText = when {
                            creditScore >= 750 -> "उत्कृष्ट / Excellent 🌟"
                            creditScore >= 650 -> "उत्तम / Good 👍"
                            creditScore >= 550 -> "मध्यम / Average"
                            else -> "कमी / Poor ⚠️"
                        }
                        Text(
                            text = scoreText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                }
                
                // Progress Bar representation of Credit Score
                Box(
                    modifier = Modifier
                        .size(100.dp, 12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFE2E8F0))
                ) {
                    // Normalize credit score from 300-900 range
                    val fraction = ((creditScore - 300).coerceAtLeast(0).toFloat() / 600f).coerceIn(0f, 1f)
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(fraction)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFFEF4444),
                                        Color(0xFFF59E0B),
                                        Color(0xFF10B981)
                                    )
                                )
                            )
                    )
                }
            }
            
            // Admin Panel / Firebase Database Score Configurator
            AnimatedVisibility(visible = showAdminPanel) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Text(
                        text = "🔥 Firebase Firestore Cloud Console Emulator",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEA4335) // Firebase Red
                    )
                    Text(
                        text = "इथे क्रेडिट स्कोर बदलून फायरबेसवरून लिमिट बदलण्याचे सिम्युलेशन करा:",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = inputScoreString,
                            onValueChange = { inputScoreString = it },
                            label = { Text("क्रेडिट स्कोर (300 - 900)", fontSize = 10.sp) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFEA4335))
                        )
                        
                        Button(
                            onClick = {
                                val score = inputScoreString.toIntOrNull()
                                if (score != null && score in 300..900) {
                                    isSyncing = true
                                }
                            },
                            enabled = !isSyncing,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA4335)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            if (isSyncing) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("Firestore वर टाका", fontSize = 10.sp)
                            }
                        }
                    }
                    
                    if (isSyncing) {
                        LaunchedEffect(Unit) {
                            delay(1200)
                            val score = inputScoreString.toIntOrNull() ?: 720
                            onScoreChanged(score)
                            isSyncing = false
                        }
                        
                        Text(
                            text = "Firestore database शी कनेक्ट होत आहे... डेटाबेसमध्ये सेव्ह करत आहे...",
                            fontSize = 9.sp,
                            color = Color(0xFFEA4335),
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "*स्कोर नियमावली:\n300-499: ₹0 | 500-599: ₹200 | 600-699: ₹500 | 700-799: ₹1000 | 800-900: ₹2000",
                        fontSize = 8.sp,
                        color = Color.Gray,
                        lineHeight = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceLoadingOverlay(
    isLoading: Boolean,
    text: String
) {
    if (isLoading) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    modifier = Modifier
                        .padding(32.dp)
                        .widthIn(max = 280.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Rotating gear settings animation
                        val infiniteTransition = rememberInfiniteTransition(label = "gearRotation")
                        val angle by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 360f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 2200, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart
                            ),
                            label = "angle"
                        )
                        
                        // Scale pulsation for visual feedback
                        val scale by infiniteTransition.animateFloat(
                            initialValue = 0.96f,
                            targetValue = 1.04f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 1100, easing = FastOutSlowInEasing),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "scale"
                        )

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(96.dp)
                                .scale(scale)
                        ) {
                            // Circular background glow
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFEDE9FE))
                            )
                            
                            // Outer progress ring
                            CircularProgressIndicator(
                                modifier = Modifier.size(84.dp),
                                color = Color(0xFF5F259F),
                                strokeWidth = 4.dp
                            )
                            
                            // Inside settings/service icon rotating representing settings/service
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Loading",
                                tint = Color(0xFF5F259F),
                                modifier = Modifier
                                    .size(46.dp)
                                    .rotate(angle)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = text,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E1B4B),
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(6.dp))
                        
                        Text(
                            text = "कृपया थोडा वेळ थांबा / Please wait",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

