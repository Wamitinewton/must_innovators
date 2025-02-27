package com.newton.events.data.remote

object EventEndPoint {
    const val GET_ALL_EVENTS = "events/list/"
    const val GET_EVENT_BY_ID = "events/{id}/view/"
    const val RSVP_EVENT = "events/{id}/registrations/"
    const val SEARCH_EVENT = "events/by-name/"
}