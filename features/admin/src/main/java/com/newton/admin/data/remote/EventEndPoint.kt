package com.newton.admin.data.remote

object EventEndPoint {
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
}