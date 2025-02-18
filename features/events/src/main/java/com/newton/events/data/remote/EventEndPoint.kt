package com.newton.events.data.remote

object EventEndPoint {
    const val CREATE_EVENT = "events/add/"
    const val UPDATE_EVENT = "/events/update/{id}"
    const val DELETE_EVENT = " events/delete/{id}"
    const val GET_ALL_EVENTS = "events/list/"
    const val GET_EVENT_BY_ID = "events/{id}/view/"
    const val RSVP_EVENT = "events/{id}/registrations/"
}