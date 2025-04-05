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
package com.newton.core.domain.models.adminModels

/**
{
"id": 3,
"username": "piexie3bett",
"email": "checktix92@gmail.com",
"first_name": "Manu",
"last_name": "Bett",
"course": "checktix92@gmail.com",
"photo":""
}
 */
data class UserData(
    val course: String = "",
    val email: String = "",
    val first_name: String = "",
    val id: Int = 0,
    val last_name: String = "",
    val photo: String? = null,
    val username: String = ""
)
