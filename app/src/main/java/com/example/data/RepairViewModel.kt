package com.example.data

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale

class RepairViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RepairRepository
    
    val bookings: StateFlow<List<BookingEntity>>
    val warrantyItems: StateFlow<List<WarrantyItemEntity>>
    
    private val _isFirebaseConnected = MutableStateFlow(false)
    val isFirebaseConnected: StateFlow<Boolean> = _isFirebaseConnected.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _loadingText = MutableStateFlow("लोड होत आहे... / Loading...")
    val loadingText: StateFlow<String> = _loadingText.asStateFlow()

    fun showLoading(text: String = "लोड होत आहे... / Loading...") {
        _loadingText.value = text
        _isLoading.value = true
    }

    fun hideLoading() {
        _isLoading.value = false
    }

    // Authentication States
    val currentUserProfile = MutableStateFlow<UserProfile?>(null)
    val isAdminLoggedIn = MutableStateFlow(false)
    val allUsers = MutableStateFlow<List<UserProfile>>(emptyList())
    
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()
    
    private val _adminPassword = MutableStateFlow("123456")
    val adminPassword: StateFlow<String> = _adminPassword.asStateFlow()

    private var mediaPlayer: android.media.MediaPlayer? = null
    val isSoundMuted = MutableStateFlow(false)
    private var prevPendingTxnIds = emptySet<String>()

    // Default static fallback lists
    val defaultRechargePlans = listOf(
        RechargePlan("plan_1", 19.0, "1 Day (24 Hours)", "1 GB", "Jio Prepaid Data Topup", "Top Up", false),
        RechargePlan("plan_2", 349.0, "28 Days", "Unlimited 5G + 2GB/day", "Jio Prepaid Unlimited plan", "True 5G Unlimited", false),
        RechargePlan("plan_3", 200.0, "28 Days", "Unlimited 5G + 30 GB", "Mega OTT Pass- 15 Premium OTT apps (No Calls & No SMS) + 1000 TV channels...", "Popular", false),
        RechargePlan("plan_4", 299.0, "28 Days", "1.5GB/Day Data", "Jio Prepaid 1.5GB/Day Voice plan", "Popular", false),
        RechargePlan("plan_5", 719.0, "84 Days", "2GB/Day Data", "Jio Prepaid 2GB/Day long-term Voice plan", "Yearly", false),
        RechargePlan("plan_6", 15.0, "Co-terminus", "1 GB Data", "Emergency Loan Pack", "Top Up", true),
        RechargePlan("plan_7", 25.0, "Co-terminus", "2 GB Data", "Emergency Loan Pack", "Top Up", true),
        RechargePlan("plan_8", 61.0, "Co-terminus", "6 GB Data", "Emergency Loan Pack", "Top Up", true)
    )

    val defaultProtectionPlans = listOf(
        ProtectionPlan("p1", "स्क्रीन ग्लास सुरक्षा (₹199)", 199.0, "स्क्रीन ग्लास फुटल्यास विनामूल्य रिप्लेसमेंट", 12),
        ProtectionPlan("p2", "फुल स्क्रीन + एलसीडी कॉम्बो (₹499)", 499.0, "एलसीडी किंवा फोल्डर डॅमेजवर ५०% सूट", 12),
        ProtectionPlan("p3", "ऑल-इन-वन पाणी + स्क्रीन कॉम्बो (₹799)", 799.0, "पाणी आणि स्क्रीन दोन्ही नुकसानांवर पूर्ण विमा", 12)
    )

    val defaultWarrantyPlans = listOf(
        WarrantyPlanOption("w1", "६ महिने स्टॅंडर्ड वॉरंटी", 6, 299.0),
        WarrantyPlanOption("w2", "१२ महिने स्टॅंडर्ड वॉरंटी", 12, 499.0),
        WarrantyPlanOption("w3", "२४ महिने प्रीमियम वॉरंटी", 24, 899.0)
    )

    private val gson = Gson()

    val defaultHandsets = listOf(
        Handset("hs_1", "JioBharat V2", 999.0, "JioBharat V2 - 4G Phone with UPI Payments & JioCinema", "Jio"),
        Handset("hs_2", "JioPhone Prima 2", 2599.0, "JioPhone Prima 2 - Premium 4G Keypad Phone with Video Calling", "Jio")
    )

    private val _handsets = MutableStateFlow<List<Handset>>(emptyList())
    val handsets: StateFlow<List<Handset>> = _handsets.asStateFlow()

    private val _loanSettings = MutableStateFlow(LoanSettings())
    val loanSettings: StateFlow<LoanSettings> = _loanSettings.asStateFlow()

    private val _rechargePlans = MutableStateFlow<List<RechargePlan>>(defaultRechargePlans)
    val rechargePlans: StateFlow<List<RechargePlan>> = _rechargePlans.asStateFlow()

    private val _protectionPlans = MutableStateFlow<List<ProtectionPlan>>(defaultProtectionPlans)
    val protectionPlans: StateFlow<List<ProtectionPlan>> = _protectionPlans.asStateFlow()

    private val _warrantyPlans = MutableStateFlow<List<WarrantyPlanOption>>(defaultWarrantyPlans)
    val warrantyPlans: StateFlow<List<WarrantyPlanOption>> = _warrantyPlans.asStateFlow()
    
    private val sharedPrefs = application.getSharedPreferences("aarushi_user_prefs", android.content.Context.MODE_PRIVATE)

    init {
        val database = AppDatabase.getDatabase(application)
        repository = RepairRepository(database.repairDao())
        
        bookings = repository.allBookings.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
        
        warrantyItems = repository.allWarrantyItems.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // Load custom plans from local preferences override
        val plansPrefs = application.getSharedPreferences("custom_plans_prefs", android.content.Context.MODE_PRIVATE)
        
        val interest = plansPrefs.getFloat("loan_interest_rate", 0f).toDouble()
        val p1 = plansPrefs.getFloat("loan_processing_fee_under_100", 5f).toDouble()
        val p2 = plansPrefs.getFloat("loan_processing_fee_over_100", 15f).toDouble()
        val lateFee = plansPrefs.getFloat("loan_late_fee_per_day", 2f).toDouble()
        _loanSettings.value = LoanSettings(interest, p1, p2, lateFee)

        val handsetJson = plansPrefs.getString("custom_handsets", null)
        val loadedHandsets = if (handsetJson != null) {
            try {
                val type = object : TypeToken<List<Handset>>() {}.type
                gson.fromJson<List<Handset>>(handsetJson, type)
            } catch (e: Exception) {
                defaultHandsets
            }
        } else {
            defaultHandsets
        }
        _handsets.value = loadedHandsets

        val customRechargesJson = plansPrefs.getString("custom_recharge_plans_list", null)
        val customRecharges = if (customRechargesJson != null) {
            try {
                val type = object : TypeToken<List<RechargePlan>>() {}.type
                gson.fromJson<List<RechargePlan>>(customRechargesJson, type)
            } catch (e: Exception) {
                emptyList<RechargePlan>()
            }
        } else {
            emptyList()
        }

        val loadedRecharge = defaultRechargePlans.map { plan ->
            val customPrice = plansPrefs.getFloat("recharge_price_${plan.id}", -1f)
            val customDesc = plansPrefs.getString("recharge_desc_${plan.id}", null)
            if (customPrice != -1f || customDesc != null) {
                plan.copy(
                    price = if (customPrice != -1f) customPrice.toDouble() else plan.price,
                    description = customDesc ?: plan.description
                )
            } else plan
        } + customRecharges
        _rechargePlans.value = loadedRecharge

        val customProtectionJson = plansPrefs.getString("custom_protection_plans_list", null)
        val customProtection = if (customProtectionJson != null) {
            try {
                val type = object : TypeToken<List<ProtectionPlan>>() {}.type
                gson.fromJson<List<ProtectionPlan>>(customProtectionJson, type)
            } catch (e: Exception) {
                emptyList<ProtectionPlan>()
            }
        } else {
            emptyList()
        }

        val loadedProtection = defaultProtectionPlans.map { plan ->
            val customPrice = plansPrefs.getFloat("protection_price_${plan.id}", -1f)
            val customTitle = plansPrefs.getString("protection_title_${plan.id}", null)
            val customDesc = plansPrefs.getString("protection_desc_${plan.id}", null)
            if (customPrice != -1f || customTitle != null || customDesc != null) {
                plan.copy(
                    price = if (customPrice != -1f) customPrice.toDouble() else plan.price,
                    title = customTitle ?: plan.title,
                    description = customDesc ?: plan.description
                )
            } else plan
        } + customProtection
        _protectionPlans.value = loadedProtection

        val customWarrantyJson = plansPrefs.getString("custom_warranty_plans_list", null)
        val customWarranty = if (customWarrantyJson != null) {
            try {
                val type = object : TypeToken<List<WarrantyPlanOption>>() {}.type
                gson.fromJson<List<WarrantyPlanOption>>(customWarrantyJson, type)
            } catch (e: Exception) {
                emptyList<WarrantyPlanOption>()
            }
        } else {
            emptyList()
        }

        val loadedWarranty = defaultWarrantyPlans.map { plan ->
            val customPrice = plansPrefs.getFloat("warranty_price_${plan.id}", -1f)
            val customTitle = plansPrefs.getString("warranty_title_${plan.id}", null)
            if (customPrice != -1f || customTitle != null) {
                plan.copy(
                    price = if (customPrice != -1f) customPrice.toDouble() else plan.price,
                    title = customTitle ?: plan.title
                )
            } else plan
        } + customWarranty
        _warrantyPlans.value = loadedWarranty
        
        // Seed database with pre-loaded high-fidelity standard warranty and combo invoice
        viewModelScope.launch {
            try {
                val existing = repository.allWarrantyItems.first()
                if (existing.isEmpty()) {
                    repository.insertWarrantyItem(
                        WarrantyItemEntity(
                            itemName = "६ महिने स्टॅंडर्ड वॉरंटी (Standard Warranty)",
                            brand = "Samsung Galaxy A54",
                            serialNumber = "SN-928374820",
                            purchaseDate = System.currentTimeMillis() - 10 * 24 * 3600 * 1000L, // 10 days ago
                            warrantyDurationMonths = 6
                        )
                    )
                    repository.insertWarrantyItem(
                        WarrantyItemEntity(
                            itemName = "फुल स्क्रीन + एलसीडी कॉम्बो (LCD Screen Combo Plan)",
                            brand = "Realme 11 Pro+",
                            serialNumber = "IMEI-9827361827",
                            purchaseDate = System.currentTimeMillis() - 2 * 24 * 3600 * 1000L, // 2 days ago
                            warrantyDurationMonths = 12
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("RepairViewModel", "Failed to seed warranty database: ${e.message}")
            }
        }
        
        checkFirebaseStatus(application)
        fetchAndSyncPlans()
        loadAllUsers()

        // Restore persistent login (One-time login)
        val lastEmail = sharedPrefs.getString("last_logged_in_email", null)
        val lastIsAdmin = sharedPrefs.getBoolean("last_logged_in_is_admin", false)
        if (lastIsAdmin) {
            isAdminLoggedIn.value = true
        } else if (lastEmail != null) {
            val localProfile = loadUserLocally(lastEmail)
            if (localProfile != null) {
                currentUserProfile.value = localProfile
            }
        }

        // Continuous alert sound for Admin when pending transactions exist
        viewModelScope.launch {
            combine(isAdminLoggedIn, transactions) { isAdmin, txns ->
                Pair(isAdmin, txns)
            }.collect { (isAdmin, txns) ->
                val pendingTxns = txns.filter { it.status == "PENDING" || it.status == "PENDING_ADMIN_APPROVAL" }
                val hasPending = pendingTxns.isNotEmpty()
                
                if (isAdmin && hasPending) {
                    val currentPendingIds = pendingTxns.map { it.id }.toSet()
                    // Check if there is a NEW pending transaction ID that we haven't seen
                    val hasNewTxn = currentPendingIds.isNotEmpty() && !currentPendingIds.all { prevPendingTxnIds.contains(it) }
                    if (hasNewTxn) {
                        // New request arrived! Unmute and play sound
                        isSoundMuted.value = false
                    }
                    prevPendingTxnIds = currentPendingIds
                    
                    if (!isSoundMuted.value) {
                        startAlertSound()
                    } else {
                        stopAlertSound()
                    }
                } else {
                    stopAlertSound()
                    prevPendingTxnIds = emptySet()
                    isSoundMuted.value = false
                }
            }
        }
    }

    private fun checkFirebaseStatus(context: Application) {
        viewModelScope.launch {
            val isInitialized = try {
                if (FirebaseApp.getApps(context).isEmpty()) {
                    val options = com.google.firebase.FirebaseOptions.Builder()
                        .setProjectId("gen-lang-client-0056523893")
                        .setApplicationId("1:9461184433:android:39f47727dcc01c4b33af90")
                        .setDatabaseUrl("https://gen-lang-client-0056523893-default-rtdb.firebaseio.com/")
                        .setGcmSenderId("9461184433")
                        .setStorageBucket("gen-lang-client-0056523893.firebasestorage.app")
                        .setApiKey("AIzaSyD4pRVg3yy6glwAs2mRhDHF5ttoPRCAP4Q")
                        .build()
                    FirebaseApp.initializeApp(context, options)
                }
                FirebaseApp.getApps(context).isNotEmpty()
            } catch (e: Exception) {
                Log.e("RepairViewModel", "Firebase initialization error: ${e.message}")
                false
            }
            _isFirebaseConnected.value = isInitialized
            if (isInitialized) {
                startRealtimeSync()
            }
        }
    }

    private fun fetchAndSyncPlans() {
        viewModelScope.launch {
            try {
                val firestore = FirebaseFirestore.getInstance()
                
                // 1. Fetch Recharge Plans
                firestore.collection("recharge_plans").get().addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        for (plan in defaultRechargePlans) {
                            firestore.collection("recharge_plans").document(plan.id).set(plan)
                        }
                        _rechargePlans.value = defaultRechargePlans
                    } else {
                        val list = result.mapNotNull { doc ->
                            try {
                                RechargePlan(
                                    id = doc.id,
                                    price = doc.getDouble("price") ?: 0.0,
                                    validity = doc.getString("validity") ?: "",
                                    data = doc.getString("data") ?: "",
                                    description = doc.getString("description") ?: "",
                                    category = doc.getString("category") ?: "Popular",
                                    isLoan = doc.getBoolean("loan") ?: doc.getBoolean("isLoan") ?: false
                                )
                            } catch (e: Exception) {
                                null
                            }
                        }
                        if (list.isNotEmpty()) {
                            _rechargePlans.value = list
                        }
                    }
                }
                
                // 2. Fetch Protection Plans
                firestore.collection("protection_plans").get().addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        for (plan in defaultProtectionPlans) {
                            firestore.collection("protection_plans").document(plan.id).set(plan)
                        }
                        _protectionPlans.value = defaultProtectionPlans
                    } else {
                        val list = result.mapNotNull { doc ->
                            try {
                                ProtectionPlan(
                                    id = doc.id,
                                    title = doc.getString("title") ?: "",
                                    price = doc.getDouble("price") ?: 0.0,
                                    description = doc.getString("description") ?: "",
                                    durationMonths = doc.getLong("durationMonths")?.toInt() ?: 12
                                )
                            } catch (e: Exception) {
                                null
                            }
                        }
                        if (list.isNotEmpty()) {
                            _protectionPlans.value = list
                        }
                    }
                }

                // 3. Fetch Warranty Plans
                firestore.collection("warranty_plans").get().addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        for (plan in defaultWarrantyPlans) {
                            firestore.collection("warranty_plans").document(plan.id).set(plan)
                        }
                        _warrantyPlans.value = defaultWarrantyPlans
                    } else {
                        val list = result.mapNotNull { doc ->
                            try {
                                WarrantyPlanOption(
                                    id = doc.id,
                                    title = doc.getString("title") ?: "",
                                    durationMonths = doc.getLong("durationMonths")?.toInt() ?: 12,
                                    price = doc.getDouble("price") ?: 0.0
                                )
                            } catch (e: Exception) {
                                null
                            }
                        }
                        if (list.isNotEmpty()) {
                            _warrantyPlans.value = list
                        }
                    }
                }
            } catch (e: Exception) {
                Log.w("RepairViewModel", "Firebase plans sync offline: ${e.message}")
            }
        }
    }

    fun bookService(serviceType: String, name: String, phone: String, description: String) {
        viewModelScope.launch {
            val newBooking = BookingEntity(
                serviceType = serviceType,
                customerName = name,
                customerPhone = phone,
                description = description,
                status = "PENDING"
            )
            repository.insertBooking(newBooking)
        }
    }

    fun registerWarranty(itemName: String, brand: String, serialNumber: String, durationMonths: Int) {
        viewModelScope.launch {
            val newItem = WarrantyItemEntity(
                itemName = itemName,
                brand = brand,
                serialNumber = serialNumber,
                purchaseDate = System.currentTimeMillis(),
                warrantyDurationMonths = durationMonths
            )
            repository.insertWarrantyItem(newItem)
        }
    }

    fun cancelBooking(id: Int) {
        viewModelScope.launch {
            repository.deleteBooking(id)
        }
    }

    fun removeWarranty(id: Int) {
        viewModelScope.launch {
            repository.deleteWarrantyItem(id)
        }
    }

    // Local and Sync helpers for User profiles and Authentication
    fun saveUserLocally(profile: UserProfile) {
        val normalizedEmail = profile.email.lowercase().trim()
        val emails = sharedPrefs.getStringSet("all_emails", mutableSetOf()) ?: mutableSetOf()
        val updatedEmails = emails.toMutableSet()
        updatedEmails.add(normalizedEmail)
        
        sharedPrefs.edit()
            .putStringSet("all_emails", updatedEmails)
            .putString("name_$normalizedEmail", profile.name)
            .putString("uid_$normalizedEmail", profile.uid)
            .putString("phone_$normalizedEmail", profile.phone)
            .putInt("credit_score_$normalizedEmail", profile.creditScore)
            .putFloat("wallet_balance_$normalizedEmail", profile.walletBalance.toFloat())
            .putFloat("pay_later_due_$normalizedEmail", profile.payLaterDue.toFloat())
            .putFloat("pay_later_limit_$normalizedEmail", profile.payLaterLimit.toFloat())
            .putBoolean("pay_later_active_$normalizedEmail", profile.payLaterActive)
            .putString("auto_payment_link_$normalizedEmail", profile.autoPaymentLink)
            .apply()
    }

    fun loadUserLocally(email: String): UserProfile? {
        val normalizedEmail = email.lowercase().trim()
        val name = sharedPrefs.getString("name_$normalizedEmail", null) ?: return null
        val uid = sharedPrefs.getString("uid_$normalizedEmail", "uid_${System.currentTimeMillis()}") ?: ""
        val phone = sharedPrefs.getString("phone_$normalizedEmail", "") ?: ""
        val creditScore = sharedPrefs.getInt("credit_score_$normalizedEmail", 720)
        val walletBalance = sharedPrefs.getFloat("wallet_balance_$normalizedEmail", 500f).toDouble()
        val payLaterDue = sharedPrefs.getFloat("pay_later_due_$normalizedEmail", 0f).toDouble()
        val payLaterLimit = sharedPrefs.getFloat("pay_later_limit_$normalizedEmail", 5000f).toDouble()
        val payLaterActive = sharedPrefs.getBoolean("pay_later_active_$normalizedEmail", false)
        val autoPaymentLink = sharedPrefs.getString("auto_payment_link_$normalizedEmail", "") ?: ""
        
        return UserProfile(
            uid = uid,
            name = name,
            email = normalizedEmail,
            phone = phone,
            creditScore = creditScore,
            walletBalance = walletBalance,
            payLaterDue = payLaterDue,
            payLaterLimit = payLaterLimit,
            payLaterActive = payLaterActive,
            autoPaymentLink = autoPaymentLink
        )
    }

    fun loadAllUsers() {
        viewModelScope.launch {
            val list = mutableListOf<UserProfile>()
            val emails = sharedPrefs.getStringSet("all_emails", emptySet()) ?: emptySet()
            
            for (email in emails) {
                val profile = loadUserLocally(email)
                if (profile != null) {
                    list.add(profile)
                }
            }
            
            // If empty, seed with mock users
            if (list.isEmpty()) {
                val defaultMockUsers = listOf(
                    UserProfile("u1", "योगेश पाटील / Yogesh Patil", "yogesh@aarushi.com", "9860856702", 720, 500.0, 0.0, 5000.0, false, ""),
                    UserProfile("u2", "अमित शर्मा / Amit Sharma", "amit@aarushi.com", "9876543210", 680, 250.0, 0.0, 3000.0, false, ""),
                    UserProfile("u3", "प्रिया नाईक / Priya Naik", "priya@aarushi.com", "9123456789", 780, 1200.0, 0.0, 8000.0, true, "https://pay.phonepe.com/g/active-autopay-aarushi")
                )
                for (user in defaultMockUsers) {
                    saveUserLocally(user)
                    list.add(user)
                }
            }
            
            allUsers.value = list
            
            // Try Firebase
            try {
                val firestore = FirebaseFirestore.getInstance()
                firestore.collection("users").get().addOnSuccessListener { result ->
                    if (!result.isEmpty) {
                        val firebaseList = result.mapNotNull { doc ->
                            try {
                                UserProfile(
                                    uid = doc.getString("uid") ?: doc.id,
                                    name = doc.getString("name") ?: "",
                                    email = doc.getString("email") ?: doc.id,
                                    phone = doc.getString("phone") ?: "",
                                    creditScore = doc.getLong("creditScore")?.toInt() ?: 720,
                                    walletBalance = doc.getDouble("walletBalance") ?: 500.0,
                                    payLaterDue = doc.getDouble("payLaterDue") ?: 0.0,
                                    payLaterLimit = doc.getDouble("payLaterLimit") ?: 5000.0,
                                    payLaterActive = doc.getBoolean("payLaterActive") ?: false,
                                    autoPaymentLink = doc.getString("autoPaymentLink") ?: ""
                                )
                            } catch (e: Exception) {
                                null
                            }
                        }
                        if (firebaseList.isNotEmpty()) {
                            // Merge/update local cache with firebase
                            for (fbUser in firebaseList) {
                                saveUserLocally(fbUser)
                            }
                            allUsers.value = firebaseList
                        }
                    }
                }
            } catch (e: Exception) {
                Log.w("RepairViewModel", "Firebase loadAllUsers offline: ${e.message}")
            }
        }
    }

    fun signUp(email: String, password: String, name: String, phone: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            val normalizedEmail = email.lowercase().trim()
            val normalizedPhone = phone.trim()

            // Check if mobile number is already registered
            val isPhoneRegistered = allUsers.value.any { it.phone.trim() == normalizedPhone }
            if (isPhoneRegistered) {
                onFailure("या मोबाईल नंबरने आधीच नोंदणी केलेली आहे / This mobile number is already registered")
                return@launch
            }

            // Check if email is already registered
            val isEmailRegistered = allUsers.value.any { it.email.lowercase().trim() == normalizedEmail }
            if (isEmailRegistered) {
                onFailure("या ईमेल आयडीने आधीच नोंदणी केलेली आहे / This email ID is already registered")
                return@launch
            }
            
            if (_isFirebaseConnected.value) {
                try {
                    val auth = FirebaseAuth.getInstance()
                    auth.createUserWithEmailAndPassword(normalizedEmail, password)
                        .addOnSuccessListener { authResult ->
                            val uid = authResult.user?.uid ?: "user_${System.currentTimeMillis()}"
                            val newProfile = UserProfile(
                                uid = uid,
                                name = name,
                                email = normalizedEmail,
                                phone = phone,
                                creditScore = 720,
                                walletBalance = 0.0,
                                payLaterDue = 0.0,
                                payLaterLimit = 600.0,
                                payLaterActive = false,
                                autoPaymentLink = ""
                            )
                            
                            viewModelScope.launch {
                                saveUserLocally(newProfile)
                                try {
                                    val firestore = FirebaseFirestore.getInstance()
                                    firestore.collection("users").document(normalizedEmail).set(newProfile)
                                    val rtdb = FirebaseDatabase.getInstance()
                                    rtdb.getReference("users").child(sanitiseEmail(normalizedEmail)).setValue(newProfile)
                                } catch (e: Exception) {
                                    Log.w("RepairViewModel", "Firebase store error: ${e.message}")
                                }
                                currentUserProfile.value = newProfile
                                sharedPrefs.edit().putString("last_logged_in_email", normalizedEmail).putBoolean("last_logged_in_is_admin", false).apply()
                                loadAllUsers()
                                onSuccess()
                            }
                        }
                        .addOnFailureListener { exception ->
                            onFailure(exception.localizedMessage ?: "रजिस्ट्रेशन अयशस्वी / Registration failed")
                        }
                } catch (e: Exception) {
                    registerOffline(normalizedEmail, name, phone, onSuccess, onFailure)
                }
            } else {
                registerOffline(normalizedEmail, name, phone, onSuccess, onFailure)
            }
        }
    }

    private fun registerOffline(normalizedEmail: String, name: String, phone: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            val uid = "user_${System.currentTimeMillis()}"
            val newProfile = UserProfile(
                uid = uid,
                name = name,
                email = normalizedEmail,
                phone = phone,
                creditScore = 720,
                walletBalance = 0.0,
                payLaterDue = 0.0,
                payLaterLimit = 600.0,
                payLaterActive = false,
                autoPaymentLink = ""
            )
            
            saveUserLocally(newProfile)
            
            try {
                val firestore = FirebaseFirestore.getInstance()
                firestore.collection("users").document(normalizedEmail).set(newProfile)
                if (_isFirebaseConnected.value) {
                    val rtdb = FirebaseDatabase.getInstance()
                    rtdb.getReference("users").child(sanitiseEmail(normalizedEmail)).setValue(newProfile)
                }
            } catch (e: Exception) {
                Log.w("RepairViewModel", "Firebase signup offline: ${e.message}")
            }
            
            currentUserProfile.value = newProfile
            sharedPrefs.edit().putString("last_logged_in_email", normalizedEmail).putBoolean("last_logged_in_is_admin", false).apply()
            loadAllUsers()
            onSuccess()
        }
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            val normalizedEmail = email.lowercase().trim()
            
            // Check Admin login
            if (normalizedEmail == "lotube968@gmail.com" && password == adminPassword.value) {
                isAdminLoggedIn.value = true
                currentUserProfile.value = null
                sharedPrefs.edit().putString("last_logged_in_email", normalizedEmail).putBoolean("last_logged_in_is_admin", true).apply()
                onSuccess()
                return@launch
            }
            
            if (_isFirebaseConnected.value) {
                try {
                    val auth = FirebaseAuth.getInstance()
                    auth.signInWithEmailAndPassword(normalizedEmail, password)
                        .addOnSuccessListener { authResult ->
                            viewModelScope.launch {
                                sharedPrefs.edit().putString("last_logged_in_email", normalizedEmail).putBoolean("last_logged_in_is_admin", false).apply()
                                val localProfile = loadUserLocally(normalizedEmail)
                                if (localProfile != null) {
                                    currentUserProfile.value = localProfile
                                    onSuccess()
                                    
                                    try {
                                        val firestore = FirebaseFirestore.getInstance()
                                        firestore.collection("users").document(normalizedEmail).get()
                                            .addOnSuccessListener { doc ->
                                                if (doc.exists()) {
                                                    val firebaseProfile = doc.toObject(UserProfile::class.java)
                                                    if (firebaseProfile != null) {
                                                        currentUserProfile.value = firebaseProfile
                                                        saveUserLocally(firebaseProfile)
                                                        loadAllUsers()
                                                    }
                                                }
                                            }
                                    } catch (e: Exception) {
                                        Log.w("RepairViewModel", "Firebase offline, using local profile")
                                    }
                                } else {
                                    try {
                                        val firestore = FirebaseFirestore.getInstance()
                                        firestore.collection("users").document(normalizedEmail).get()
                                            .addOnSuccessListener { doc ->
                                                if (doc.exists()) {
                                                    val firebaseProfile = doc.toObject(UserProfile::class.java)
                                                    if (firebaseProfile != null) {
                                                        currentUserProfile.value = firebaseProfile
                                                        saveUserLocally(firebaseProfile)
                                                        loadAllUsers()
                                                        onSuccess()
                                                    } else {
                                                        createFallbackUser(normalizedEmail, onSuccess)
                                                    }
                                                } else {
                                                    createFallbackUser(normalizedEmail, onSuccess)
                                                }
                                            }
                                            .addOnFailureListener {
                                                createFallbackUser(normalizedEmail, onSuccess)
                                            }
                                    } catch (e: Exception) {
                                        createFallbackUser(normalizedEmail, onSuccess)
                                    }
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            viewModelScope.launch {
                                val localProfile = loadUserLocally(normalizedEmail)
                                if (localProfile != null) {
                                    currentUserProfile.value = localProfile
                                    sharedPrefs.edit().putString("last_logged_in_email", normalizedEmail).putBoolean("last_logged_in_is_admin", false).apply()
                                    onSuccess()
                                } else {
                                    onFailure(exception.localizedMessage ?: "लॉगिन अयशस्वी / Sign In failed")
                                }
                            }
                        }
                } catch (e: Exception) {
                    signInOffline(normalizedEmail, password, onSuccess, onFailure)
                }
            } else {
                signInOffline(normalizedEmail, password, onSuccess, onFailure)
            }
        }
    }

    private fun signInOffline(normalizedEmail: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            val localProfile = loadUserLocally(normalizedEmail)
            if (localProfile != null) {
                currentUserProfile.value = localProfile
                sharedPrefs.edit().putString("last_logged_in_email", normalizedEmail).putBoolean("last_logged_in_is_admin", false).apply()
                onSuccess()
            } else {
                createFallbackUser(normalizedEmail, onSuccess)
            }
        }
    }

    private fun createFallbackUser(email: String, onSuccess: () -> Unit) {
        val fallbackUser = UserProfile(
            uid = "user_${System.currentTimeMillis()}",
            name = email.split("@")[0].replaceFirstChar { it.uppercase() } + " Patil",
            email = email,
            phone = "9860856702",
            creditScore = 720,
            walletBalance = 0.0,
            payLaterDue = 0.0,
            payLaterLimit = 600.0,
            payLaterActive = false
        )
        saveUserLocally(fallbackUser)
        currentUserProfile.value = fallbackUser
        sharedPrefs.edit().putString("last_logged_in_email", email).putBoolean("last_logged_in_is_admin", false).apply()
        loadAllUsers()
        onSuccess()
    }

    fun signOut() {
        currentUserProfile.value = null
        isAdminLoggedIn.value = false
        sharedPrefs.edit().remove("last_logged_in_email").remove("last_logged_in_is_admin").apply()
    }

    fun updateUserFromAdmin(email: String, creditScore: Int, payLaterActive: Boolean, walletBalance: Double, payLaterDue: Double, autoPaymentLink: String) {
        viewModelScope.launch {
            val localProfile = loadUserLocally(email)
            if (localProfile != null) {
                // Compare and log transactions for history
                if (walletBalance != localProfile.walletBalance) {
                    val diff = walletBalance - localProfile.walletBalance
                    if (diff > 0) {
                        logTransaction("WALLET_DEPOSIT", localProfile.name, localProfile.phone, "ऍडमिनने वॉलेटमध्ये ₹$diff जमा केले")
                    } else {
                        logTransaction("WALLET_DEPOSIT", localProfile.name, localProfile.phone, "ऍडमिनने वॉलेटमधून ₹${-diff} काढले")
                    }
                }
                if (payLaterDue != localProfile.payLaterDue) {
                    val diff = payLaterDue - localProfile.payLaterDue
                    if (diff > 0) {
                        logTransaction("LATE_FEES", localProfile.name, localProfile.phone, "थकबाकी वाढली: ₹$diff (ऍडमिन)")
                    } else {
                        logTransaction("LATE_FEES", localProfile.name, localProfile.phone, "थकबाकी जमा केली: ₹${-diff} (ऍडमिन)")
                    }
                }
                if (creditScore != localProfile.creditScore) {
                    logTransaction("CREDIT_SCORE", localProfile.name, localProfile.phone, "क्रेडिट स्कोर बदलला: ${localProfile.creditScore} -> $creditScore (ऍडमिन)")
                }

                val updated = localProfile.copy(
                    creditScore = creditScore,
                    payLaterActive = payLaterActive,
                    walletBalance = walletBalance,
                    payLaterDue = payLaterDue,
                    autoPaymentLink = autoPaymentLink
                )
                saveUserLocally(updated)
                
                if (currentUserProfile.value?.email?.lowercase() == email.lowercase()) {
                    currentUserProfile.value = updated
                }
                
                try {
                    val firestore = FirebaseFirestore.getInstance()
                    firestore.collection("users").document(email.lowercase().trim()).set(updated)
                    if (_isFirebaseConnected.value) {
                        val rtdb = FirebaseDatabase.getInstance()
                        rtdb.getReference("users").child(sanitiseEmail(email)).setValue(updated)
                    }
                } catch (e: Exception) {
                    Log.w("RepairViewModel", "Firebase offline, update saved locally")
                }
                
                loadAllUsers()
            }
        }
    }

    fun updateUserWalletBalance(email: String, newBalance: Double) {
        viewModelScope.launch {
            val localProfile = loadUserLocally(email)
            if (localProfile != null) {
                val updated = localProfile.copy(walletBalance = newBalance)
                saveUserLocally(updated)
                if (currentUserProfile.value?.email?.lowercase() == email.lowercase()) {
                    currentUserProfile.value = updated
                }
                try {
                    val firestore = FirebaseFirestore.getInstance()
                    firestore.collection("users").document(email.lowercase().trim()).set(updated)
                    if (_isFirebaseConnected.value) {
                        val rtdb = FirebaseDatabase.getInstance()
                        rtdb.getReference("users").child(sanitiseEmail(email)).setValue(updated)
                    }
                } catch (e: Exception) {}
                loadAllUsers()
            }
        }
    }

    fun updateUserPayLaterDue(email: String, newDue: Double) {
        viewModelScope.launch {
            val localProfile = loadUserLocally(email)
            if (localProfile != null) {
                val updated = localProfile.copy(payLaterDue = newDue)
                saveUserLocally(updated)
                if (currentUserProfile.value?.email?.lowercase() == email.lowercase()) {
                    currentUserProfile.value = updated
                }
                try {
                    val firestore = FirebaseFirestore.getInstance()
                    firestore.collection("users").document(email.lowercase().trim()).set(updated)
                    if (_isFirebaseConnected.value) {
                        val rtdb = FirebaseDatabase.getInstance()
                        rtdb.getReference("users").child(sanitiseEmail(email)).setValue(updated)
                    }
                } catch (e: Exception) {}
                loadAllUsers()
            }
        }
    }

    fun updateRechargePlan(plan: RechargePlan) {
        val updatedList = _rechargePlans.value.map { if (it.id == plan.id) plan else it }
        _rechargePlans.value = updatedList
        
        val plansPrefs = getApplication<Application>().getSharedPreferences("custom_plans_prefs", android.content.Context.MODE_PRIVATE)
        plansPrefs.edit()
            .putFloat("recharge_price_${plan.id}", plan.price.toFloat())
            .putString("recharge_desc_${plan.id}", plan.description)
            .apply()

        try {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("recharge_plans").document(plan.id).set(plan)
            if (_isFirebaseConnected.value) {
                FirebaseDatabase.getInstance().getReference("recharge_plans").child(plan.id).setValue(plan)
            }
        } catch (e: Exception) {
            Log.w("RepairViewModel", "Firebase offline, plan saved locally")
        }
    }

    fun updateProtectionPlan(plan: ProtectionPlan) {
        val updatedList = _protectionPlans.value.map { if (it.id == plan.id) plan else it }
        _protectionPlans.value = updatedList

        val plansPrefs = getApplication<Application>().getSharedPreferences("custom_plans_prefs", android.content.Context.MODE_PRIVATE)
        plansPrefs.edit()
            .putFloat("protection_price_${plan.id}", plan.price.toFloat())
            .putString("protection_title_${plan.id}", plan.title)
            .putString("protection_desc_${plan.id}", plan.description)
            .apply()

        try {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("protection_plans").document(plan.id).set(plan)
            if (_isFirebaseConnected.value) {
                FirebaseDatabase.getInstance().getReference("protection_plans").child(plan.id).setValue(plan)
            }
        } catch (e: Exception) {
            Log.w("RepairViewModel", "Firebase offline, plan saved locally")
        }
    }

    fun updateWarrantyPlan(plan: WarrantyPlanOption) {
        val updatedList = _warrantyPlans.value.map { if (it.id == plan.id) plan else it }
        _warrantyPlans.value = updatedList

        val plansPrefs = getApplication<Application>().getSharedPreferences("custom_plans_prefs", android.content.Context.MODE_PRIVATE)
        plansPrefs.edit()
            .putFloat("warranty_price_${plan.id}", plan.price.toFloat())
            .putString("warranty_title_${plan.id}", plan.title)
            .apply()

        try {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("warranty_plans").document(plan.id).set(plan)
            if (_isFirebaseConnected.value) {
                FirebaseDatabase.getInstance().getReference("warranty_plans").child(plan.id).setValue(plan)
            }
        } catch (e: Exception) {
            Log.w("RepairViewModel", "Firebase offline, plan saved locally")
        }
    }

    fun addNewRechargePlan(plan: RechargePlan) {
        val plansPrefs = getApplication<Application>().getSharedPreferences("custom_plans_prefs", android.content.Context.MODE_PRIVATE)
        val customRechargesJson = plansPrefs.getString("custom_recharge_plans_list", null)
        val customRecharges = if (customRechargesJson != null) {
            try {
                val type = object : TypeToken<List<RechargePlan>>() {}.type
                gson.fromJson<List<RechargePlan>>(customRechargesJson, type)
            } catch (e: Exception) {
                emptyList<RechargePlan>()
            }
        } else {
            emptyList()
        }.toMutableList()
        
        customRecharges.add(plan)
        plansPrefs.edit().putString("custom_recharge_plans_list", gson.toJson(customRecharges)).apply()
        
        _rechargePlans.value = _rechargePlans.value + plan
        try {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("recharge_plans").document(plan.id).set(plan)
            if (_isFirebaseConnected.value) {
                FirebaseDatabase.getInstance().getReference("recharge_plans").child(plan.id).setValue(plan)
            }
        } catch (e: Exception) {}
    }

    fun addNewProtectionPlan(plan: ProtectionPlan) {
        val plansPrefs = getApplication<Application>().getSharedPreferences("custom_plans_prefs", android.content.Context.MODE_PRIVATE)
        val customProtectionJson = plansPrefs.getString("custom_protection_plans_list", null)
        val customProtection = if (customProtectionJson != null) {
            try {
                val type = object : TypeToken<List<ProtectionPlan>>() {}.type
                gson.fromJson<List<ProtectionPlan>>(customProtectionJson, type)
            } catch (e: Exception) {
                emptyList<ProtectionPlan>()
            }
        } else {
            emptyList()
        }.toMutableList()
        
        customProtection.add(plan)
        plansPrefs.edit().putString("custom_protection_plans_list", gson.toJson(customProtection)).apply()
        
        _protectionPlans.value = _protectionPlans.value + plan
        try {
            if (_isFirebaseConnected.value) {
                FirebaseDatabase.getInstance().getReference("protection_plans").child(plan.id).setValue(plan)
            }
        } catch (e: Exception) {}
    }

    fun addNewWarrantyPlan(plan: WarrantyPlanOption) {
        val plansPrefs = getApplication<Application>().getSharedPreferences("custom_plans_prefs", android.content.Context.MODE_PRIVATE)
        val customWarrantyJson = plansPrefs.getString("custom_warranty_plans_list", null)
        val customWarranty = if (customWarrantyJson != null) {
            try {
                val type = object : TypeToken<List<WarrantyPlanOption>>() {}.type
                gson.fromJson<List<WarrantyPlanOption>>(customWarrantyJson, type)
            } catch (e: Exception) {
                emptyList<WarrantyPlanOption>()
            }
        } else {
            emptyList()
        }.toMutableList()
        
        customWarranty.add(plan)
        plansPrefs.edit().putString("custom_warranty_plans_list", gson.toJson(customWarranty)).apply()
        
        _warrantyPlans.value = _warrantyPlans.value + plan
        try {
            if (_isFirebaseConnected.value) {
                FirebaseDatabase.getInstance().getReference("warranty_plans").child(plan.id).setValue(plan)
            }
        } catch (e: Exception) {}
    }

    fun addNewHandset(handset: Handset) {
        val plansPrefs = getApplication<Application>().getSharedPreferences("custom_plans_prefs", android.content.Context.MODE_PRIVATE)
        val list = _handsets.value.toMutableList()
        list.add(handset)
        _handsets.value = list
        plansPrefs.edit().putString("custom_handsets", gson.toJson(list)).apply()
        try {
            if (_isFirebaseConnected.value) {
                FirebaseDatabase.getInstance().getReference("handsets").child(handset.id).setValue(handset)
            }
        } catch (e: Exception) {}
    }

    fun updateLoanSettings(settings: LoanSettings) {
        _loanSettings.value = settings
        val plansPrefs = getApplication<Application>().getSharedPreferences("custom_plans_prefs", android.content.Context.MODE_PRIVATE)
        plansPrefs.edit()
            .putFloat("loan_interest_rate", settings.interestRatePercent.toFloat())
            .putFloat("loan_processing_fee_under_100", settings.processingFeeUnder100.toFloat())
            .putFloat("loan_processing_fee_over_100", settings.processingFeeOver100.toFloat())
            .putFloat("loan_late_fee_per_day", settings.lateFeePerDay.toFloat())
            .apply()
        try {
            if (_isFirebaseConnected.value) {
                FirebaseDatabase.getInstance().getReference("loan_settings").setValue(settings)
            }
        } catch (e: Exception) {}
    }

    fun logTransaction(serviceType: String, name: String, phone: String, description: String, status: String = "SUCCESS") {
        viewModelScope.launch {
            val newBooking = BookingEntity(
                serviceType = serviceType,
                customerName = name,
                customerPhone = phone,
                description = description,
                status = status
            )
            repository.insertBooking(newBooking)
        }
    }

    fun transferWalletBalance(recipientEmailOrPhone: String, amount: Double, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val currentUser = currentUserProfile.value ?: return onError("User not logged in")
        if (amount <= 0) return onError("Amount must be greater than 0")
        if (currentUser.walletBalance < amount) return onError("Insufficient wallet balance")
        
        val targetUser = allUsers.value.find { 
            it.email.equals(recipientEmailOrPhone, ignoreCase = true) || it.phone == recipientEmailOrPhone 
        } ?: return onError("Recipient user not found in database")
        
        if (targetUser.email.equals(currentUser.email, ignoreCase = true)) {
            return onError("You cannot transfer money to yourself")
        }

        val updatedSender = currentUser.copy(walletBalance = currentUser.walletBalance - amount)
        val updatedRecipient = targetUser.copy(walletBalance = targetUser.walletBalance + amount)
        
        saveUserLocally(updatedSender)
        saveUserLocally(updatedRecipient)
        currentUserProfile.value = updatedSender
        
        logTransaction(
            serviceType = "WALLET_TRANSFER",
            name = currentUser.name,
            phone = currentUser.phone,
            description = "Sent ₹$amount to ${targetUser.name}"
        )
        
        logTransaction(
            serviceType = "WALLET_TRANSFER",
            name = targetUser.name,
            phone = targetUser.phone,
            description = "Received ₹$amount from ${currentUser.name}"
        )

        loadAllUsers()
        onSuccess()
    }

    fun sanitiseEmail(email: String): String {
        return email.lowercase().trim().replace(".", "_")
    }

    private fun startRealtimeSync() {
        if (!_isFirebaseConnected.value) return
        val rtdb = FirebaseDatabase.getInstance()
        
        // 1. Sync Recharge Plans
        rtdb.getReference("recharge_plans").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<RechargePlan>()
                for (child in snapshot.children) {
                    val plan = child.getValue(RechargePlan::class.java)
                    if (plan != null) list.add(plan)
                }
                if (list.isNotEmpty()) {
                    _rechargePlans.value = list
                } else {
                    for (plan in defaultRechargePlans) {
                        rtdb.getReference("recharge_plans").child(plan.id).setValue(plan)
                    }
                    _rechargePlans.value = defaultRechargePlans
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("RepairViewModel", "recharge_plans sync cancelled")
            }
        })

        // 2. Sync Protection Plans
        rtdb.getReference("protection_plans").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ProtectionPlan>()
                for (child in snapshot.children) {
                    val plan = child.getValue(ProtectionPlan::class.java)
                    if (plan != null) list.add(plan)
                }
                if (list.isNotEmpty()) {
                    _protectionPlans.value = list
                } else {
                    for (plan in defaultProtectionPlans) {
                        rtdb.getReference("protection_plans").child(plan.id).setValue(plan)
                    }
                    _protectionPlans.value = defaultProtectionPlans
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("RepairViewModel", "protection_plans sync cancelled")
            }
        })

        // 3. Sync Warranty Plans
        rtdb.getReference("warranty_plans").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<WarrantyPlanOption>()
                for (child in snapshot.children) {
                    val plan = child.getValue(WarrantyPlanOption::class.java)
                    if (plan != null) list.add(plan)
                }
                if (list.isNotEmpty()) {
                    _warrantyPlans.value = list
                } else {
                    for (plan in defaultWarrantyPlans) {
                        rtdb.getReference("warranty_plans").child(plan.id).setValue(plan)
                    }
                    _warrantyPlans.value = defaultWarrantyPlans
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("RepairViewModel", "warranty_plans sync cancelled")
            }
        })

        // 4. Sync Handsets
        rtdb.getReference("handsets").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Handset>()
                for (child in snapshot.children) {
                    val hs = child.getValue(Handset::class.java)
                    if (hs != null) list.add(hs)
                }
                if (list.isNotEmpty()) {
                    _handsets.value = list
                } else {
                    for (hs in defaultHandsets) {
                        rtdb.getReference("handsets").child(hs.id).setValue(hs)
                    }
                    _handsets.value = defaultHandsets
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("RepairViewModel", "handsets sync cancelled")
            }
        })

        // 5. Sync Loan Settings
        rtdb.getReference("loan_settings").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val settings = snapshot.getValue(LoanSettings::class.java)
                if (settings != null) {
                    _loanSettings.value = settings
                } else {
                    rtdb.getReference("loan_settings").setValue(LoanSettings())
                    _loanSettings.value = LoanSettings()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("RepairViewModel", "loan_settings sync cancelled")
            }
        })

        // 6. Sync Admin Password
        rtdb.getReference("admin_settings").child("password").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pass = snapshot.getValue(String::class.java)
                if (pass != null) {
                    _adminPassword.value = pass
                } else {
                    rtdb.getReference("admin_settings").child("password").setValue("123456")
                    _adminPassword.value = "123456"
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("RepairViewModel", "admin password sync cancelled")
            }
        })

        // 7. Sync All Users
        rtdb.getReference("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<UserProfile>()
                for (child in snapshot.children) {
                    val user = child.getValue(UserProfile::class.java)
                    if (user != null) {
                        list.add(user)
                        val activeEmail = currentUserProfile.value?.email
                        if (activeEmail != null && sanitiseEmail(activeEmail) == child.key) {
                            currentUserProfile.value = user
                            saveUserLocally(user)
                        }
                    }
                }
                allUsers.value = list
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("RepairViewModel", "users sync cancelled")
            }
        })

        // 8. Sync Transactions
        rtdb.getReference("transactions").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Transaction>()
                for (child in snapshot.children) {
                    val txn = child.getValue(Transaction::class.java)
                    if (txn != null) list.add(txn)
                }
                _transactions.value = list.sortedByDescending { it.timestamp }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("RepairViewModel", "transactions sync cancelled")
            }
        })
    }

    fun submitTransaction(txn: Transaction, onSuccess: () -> Unit = {}, onFailure: (String) -> Unit = {}) {
        viewModelScope.launch {
            if (_isFirebaseConnected.value) {
                val rtdb = FirebaseDatabase.getInstance()
                rtdb.getReference("transactions").child(txn.id).setValue(txn)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onFailure(e.localizedMessage ?: "विनंती सबमिट करण्यास अडचण आली.") }
            } else {
                onFailure("Firebase जोडलेले नाही.")
            }
        }
    }

    fun changeAdminPassword(newPassword: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            if (_isFirebaseConnected.value) {
                val rtdb = FirebaseDatabase.getInstance()
                rtdb.getReference("admin_settings").child("password").setValue(newPassword)
                    .addOnSuccessListener {
                        _adminPassword.value = newPassword
                        sharedPrefs.edit().putString("admin_password_local", newPassword).apply()
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onFailure(e.localizedMessage ?: "पासवर्ड बदलण्यास अपयश आले.")
                    }
            } else {
                _adminPassword.value = newPassword
                sharedPrefs.edit().putString("admin_password_local", newPassword).apply()
                onSuccess()
            }
        }
    }

    fun approveTransaction(txnId: String, onSuccess: () -> Unit = {}, onFailure: (String) -> Unit = {}) {
        viewModelScope.launch {
            if (_isFirebaseConnected.value) {
                val rtdb = FirebaseDatabase.getInstance()
                rtdb.getReference("transactions").child(txnId).get().addOnSuccessListener { snapshot ->
                    val txn = snapshot.getValue(Transaction::class.java)
                    if (txn != null && (txn.status == "PENDING" || txn.status == "PENDING_ADMIN_APPROVAL")) {
                        val updatedTxn = txn.copy(status = "SUCCESSFUL")
                        
                        rtdb.getReference("transactions").child(txnId).setValue(updatedTxn)
                            .addOnSuccessListener {
                                val userEmail = txn.customerEmail
                                rtdb.getReference("users").child(sanitiseEmail(userEmail)).get().addOnSuccessListener { userSnapshot ->
                                    val user = userSnapshot.getValue(UserProfile::class.java)
                                    if (user != null) {
                                        val updatedUser = when (txn.type) {
                                            "ADD_MONEY" -> user.copy(walletBalance = user.walletBalance + txn.amount)
                                            "PAY_LATER_REPAY", "LOAN_REPAYMENT" -> user.copy(payLaterDue = (user.payLaterDue - txn.amount).coerceAtLeast(0.0))
                                            "ACTIVATE_PAY_LATER" -> user.copy(payLaterActive = true)
                                            else -> user
                                        }
                                        rtdb.getReference("users").child(sanitiseEmail(userEmail)).setValue(updatedUser)
                                        logTransaction(
                                            serviceType = "TRANSACTION_APPROVED",
                                            name = txn.customerName,
                                            phone = txn.customerPhone,
                                            description = "Approved txn ₹${txn.amount} for ${txn.type}"
                                        )
                                    }
                                }
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                onFailure(e.localizedMessage ?: "Failed to update transaction status")
                            }
                    } else {
                        onFailure("Transaction already approved or not found")
                    }
                }.addOnFailureListener { e ->
                    onFailure(e.localizedMessage ?: "Failed to fetch transaction")
                }
            } else {
                onFailure("Firebase not connected")
            }
        }
    }

    fun rejectTransaction(txnId: String, onSuccess: () -> Unit = {}, onFailure: (String) -> Unit = {}) {
        viewModelScope.launch {
            if (_isFirebaseConnected.value) {
                val rtdb = FirebaseDatabase.getInstance()
                rtdb.getReference("transactions").child(txnId).child("status").setValue("REJECTED")
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onFailure(e.localizedMessage ?: "Failed to reject transaction")
                    }
            } else {
                onFailure("Firebase not connected")
            }
        }
    }

    fun startAlertSound() {
        if (isSoundMuted.value) return
        
        // 1. Try starting the background Service to support background playback when minimized
        try {
            val intent = Intent(getApplication(), AlertSoundService::class.java)
            getApplication<Application>().startService(intent)
            Log.d("RepairViewModel", "AlertSoundService started for background playback")
        } catch (e: Exception) {
            Log.e("RepairViewModel", "Failed to start background AlertSoundService: ${e.message}")
        }

        // 2. Also start local MediaPlayer as a robust fallback/supplement
        if (mediaPlayer == null) {
            try {
                val alertUri = android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_RINGTONE)
                    ?: android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION)
                
                mediaPlayer = android.media.MediaPlayer().apply {
                    setDataSource(getApplication(), alertUri)
                    setAudioAttributes(
                        android.media.AudioAttributes.Builder()
                            .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(android.media.AudioAttributes.USAGE_ALARM)
                            .build()
                    )
                    isLooping = true
                    prepare()
                    start()
                }
                Log.d("RepairViewModel", "Local alert sound fallback started")
            } catch (ex: Exception) {
                Log.e("RepairViewModel", "Failed to start local alert sound fallback: ${ex.message}")
            }
        }
    }

    fun stopAlertSound() {
        // 1. Stop background service
        try {
            val intent = Intent(getApplication(), AlertSoundService::class.java)
            getApplication<Application>().stopService(intent)
            Log.d("RepairViewModel", "AlertSoundService stopped")
        } catch (e: Exception) {
            Log.e("RepairViewModel", "Failed to stop AlertSoundService: ${e.message}")
        }

        // 2. Stop local MediaPlayer fallback
        mediaPlayer?.let {
            try {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            } catch (e: Exception) {
                Log.e("RepairViewModel", "Error stopping local alert sound", e)
            }
        }
        mediaPlayer = null
        Log.d("RepairViewModel", "Local alert sound fallback stopped")
    }

    fun muteAlertSound() {
        isSoundMuted.value = true
        stopAlertSound()
    }

    override fun onCleared() {
        stopAlertSound()
        super.onCleared()
    }
}
