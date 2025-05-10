package com.newton.partners.utils

import androidx.compose.runtime.Composable

@Composable
 fun getPartnershipPeriod(startDate: String, endDate: String?): String {
    return if (endDate.isNullOrBlank()) {
        "Since $startDate"
    } else {
        "$startDate - $endDate"
    }
}