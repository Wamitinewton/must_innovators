package com.newton.core.data.remote

object ApiEndpoints {
    const val GET_COMMUNITIES = "communities/"
    const val GET_EXECUTIVES = "executives/"


    const val REGISTER = "api/account/register/"
    const val LOGIN = "api/account/login/"
    const val CHANGE_PWD = "api/account/change-password/"
    const val REFRESH_TOKEN = "api/account/token/refresh/"
    const val GET_USER_DATA = "api/account/get-user-data/"
    const val UPDATE_USER_DATA = "api/account/update-user-profile/"
    const val REQUEST_OTP = "api/account/password-reset/request/"
    const val VERIFY_OTP = "api/account/password-reset/verify-otp/"
    const val RESET_PASSWORD = "api/account/password-reset/reset/"
    const val DELETE_ACCOUNT = "api/account/delete-account/"

    const val GET_ALL_EVENTS = "events/list/"
    const val GET_EVENT_BY_ID = "events/{id}/view/"
    const val RSVP_EVENT = "events/{id}/registrations/"
    const val SEARCH_EVENT = "events/by-name/"

    const val GET_PARTNERS = "partners/"

}