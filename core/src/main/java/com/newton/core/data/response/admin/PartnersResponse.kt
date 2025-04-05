/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.core.data.response.admin

data class PartnersResponse(
    val achievements: String,
    val benefits: String,
    val contact_email: String,
    val contact_person: String,
    val description: String,
    val end_date: String,
    val events_supported: String,
    val id: Int,
    val linked_in: String,
    val logo: String,
    val name: String,
    val ongoing: Boolean,
    val resources: String,
    val scope: String,
    val start_date: String,
    val status: String,
    val target_audience: String,
    val twitter: String,
    val type: String,
    val web_url: String
)
