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

object ApiEndpoints {
    const val GET_COMMUNITIES = "list-communities/"
    const val GET_EXECUTIVES = "api/executives/"
    const val GET_CLUB_BIO = "api/club/"

    const val REGISTER = "api/account/register/"
    const val LOGIN = "api/account/login/"
    const val CHANGE_PWD = "api/account/change-password/"
    const val REFRESH_TOKEN = "api/account/token/refresh/"
    const val GET_USER_DATA = "api/account/get-user-data/"
    const val UPDATE_USER_DATA = "api/account/update-user-profile/"
    const val REQUEST_OTP = "api/account/password-reset/request/"
    const val VERIFY_OTP = "api/account/verify-otp/"
    const val RESET_PASSWORD = "api/account/password-reset/reset/"
    const val DELETE_ACCOUNT = "api/account/delete-account/"

    const val GET_ALL_EVENTS = "events/list/"
    const val GET_EVENT_BY_ID = "events/{id}/view/"
    const val RSVP_EVENT = "events/{id}/registrations/"
    const val SEARCH_EVENT = "events/by-name/"
    const val GET_USER_TICKETS = "event-registrations/user-registrations/"

    const val GET_PARTNERS = "partners/"
    const val GET_TESTIMONIALS = "testimonies/testimonials/"
    const val CREATE_TESTIMONIAL = "testimonies/testimonials/"
}
