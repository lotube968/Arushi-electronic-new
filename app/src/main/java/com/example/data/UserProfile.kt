package com.example.data

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val creditScore: Int = 720,
    val walletBalance: Double = 500.0,
    val payLaterDue: Double = 0.0,
    val payLaterLimit: Double = 700.0,
    val payLaterActive: Boolean = false,
    val autoPaymentLink: String = ""
) {
    fun calculatedLimit(): Double {
        return when {
            creditScore < 500 -> 0.0
            creditScore in 500..599 -> 100.0
            creditScore in 600..699 -> 350.0
            else -> 700.0
        }
    }
}
