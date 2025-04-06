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
package com.newton.network.data.response.admin

/**
{
"uid": "df7a49c0-e7a2-434b-a6c4-a9686f60cecf",
"full_name": "newton wamiti",
"email": "newtondev461@gmail.com",
"course": "bcs",
"educational_level": "2",
"phone_number": "0792036343",
"expectations": "I am expecting a new job for the inconvenience",
"registration_timestamp": "2025-02-22T07:32:34.637172Z",
"ticket_number": "9efe4af6-e9ac-4da0-96f1-318bd97db2e9",
"event": 17
}
 */
data class AttendeeResponse(
    val course: String,
    val educational_level: String,
    val email: String,
    val event: Int,
    val expectations: String,
    val full_name: String,
    val phone_number: String,
    val registration_timestamp: String,
    val ticket_number: String,
    val uid: String
)
