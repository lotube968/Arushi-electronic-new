package com.example.data

data class RechargePlan(
    val id: String = "",
    val price: Double = 0.0,
    val validity: String = "",
    val data: String = "",
    val description: String = "",
    val category: String = "Popular", // "Popular", "True 5G Unlimited", "Top Up", "Yearly", "Jio Bharat", "Jio Phone"
    val isLoan: Boolean = false,
    val operator: String = "Jio" // "Jio", "Airtel", "Vi", "BSNL"
)

data class Handset(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val operator: String = "Jio"
)

data class ProtectionPlan(
    val id: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val durationMonths: Int = 12
)

data class WarrantyPlanOption(
    val id: String = "",
    val title: String = "",
    val durationMonths: Int = 12,
    val price: Double = 0.0
)

data class LoanSettings(
    val interestRatePercent: Double = 0.0,
    val processingFeeUnder100: Double = 5.0,
    val processingFeeOver100: Double = 15.0,
    val lateFeePerDay: Double = 2.0
)

data class Transaction(
    val id: String = "",
    val type: String = "", // "RECHARGE", "PROTECTION", "WARRANTY", "ADD_MONEY", "TRANSFER", "ACTIVATE_PAY_LATER", "PAY_LATER_REPAY"
    val customerEmail: String = "",
    val customerName: String = "",
    val customerPhone: String = "",
    val details: String = "",
    val amount: Double = 0.0,
    val status: String = "PENDING", // PENDING, SUCCESSFUL, REJECTED
    val timestamp: Long = 0L,
    val upiNote: String = "",
    val qrCodeUri: String = ""
)
