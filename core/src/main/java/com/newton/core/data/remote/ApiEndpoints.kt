package com.newton.core.data.remote

object ApiEndpoints {
    const val GET_COMMUNITIES = "list-communities/"
    const val GET_EXECUTIVES = "executives/"


    const val REGISTER = "api/account/register/"
    const val LOGIN = "api/account/login/"
    const val CHANGE_PWD = "api/account/change-password/"
    const val REFRESH_TOKEN = "api/account/token/refresh/"
    const val GET_USER_DATA = "api/account/get-user-data/"

    const val GET_ALL_EVENTS = "events/list/"
    const val GET_EVENT_BY_ID = "events/{id}/view/"
    const val RSVP_EVENT = "events/{id}/registrations/"
    const val SEARCH_EVENT = "events/by-name/"
}