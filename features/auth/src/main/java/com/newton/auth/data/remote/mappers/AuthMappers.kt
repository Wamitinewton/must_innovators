package com.newton.auth.data.remote.mappers

import com.newton.auth.data.remote.authResponse.sign_up.SignupDto
import com.newton.auth.domain.models.sign_up.SignupResponse

fun SignupResponse.toResponseData(): SignupDto {
    return SignupDto(
        firstName = firstName,
        lastname = lastName,
        email = email,
        username = userName,
        password = password,
    )
}

fun SignupDto.toResponseData(): SignupResponse {
    return SignupResponse(
        firstName = firstName,
        lastName = lastname,
        email = email,
        userName = username,
        password = password,
        nonFieldErrors = nonFieldErrors
    )
}

