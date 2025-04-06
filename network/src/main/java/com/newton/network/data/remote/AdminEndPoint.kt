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
package com.newton.network.data.remote

object AdminEndPoint {
    const val CREATE_EVENT = "events/add/"
    const val UPDATE_EVENT = "events/update/{id}"
    const val DELETE_EVENT = "events/delete/{id}"
    const val ADD_COMMUNITY = "add-community/"
    const val SEND_NEWSLETTER = "newsletter/"
    const val GET_RSVPS_DATA = "events/{id}/registrations/"
    const val GET_ALL_USERS_DATA = "api/account/get-all-users/"
    const val UPDATE_COMMUNITY = "community/update/{id}/"
    const val GET_EVENTS_FEEDBACK = "events/{id}/feedback/"
    const val GET_ALL_EVENTS_DATA = "events/list/"
    const val ADD_PARTNER = "partners/"
    const val GET_USERS_FEEDBACK = "feedbacks/"
    const val ADD_EXECUTIVE = "api/executives/"
    const val UPDATE_CLUB = "api/club/"
}
