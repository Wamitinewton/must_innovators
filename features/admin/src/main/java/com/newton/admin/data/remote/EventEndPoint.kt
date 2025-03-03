package com.newton.admin.data.remote

object EventEndPoint {
    const val CREATE_EVENT = "/events/add/"
    const val UPDATE_EVENT = "/events/update/{id}"
    const val DELETE_EVENT = "/events/delete/{id}"
    const val ADD_COMMUNITY = "/communities/"
    const val SEND_NEWSLETTER = "/newsletter/"
    const val GET_RSVPS_DATA = "/events/{id}/registrations/"
}