package com.newton.auth.data.remote.mappers

import com.newton.auth.data.remote.authResponse.sign_up.SignupDto
import com.newton.auth.domain.models.sign_up.UserDataResponse

fun UserDataResponse.toResponseData(): SignupDto {
    return SignupDto(
        firstName = firstName,
        lastname = lastName,
        email = email,
        password = password,
    )
}

fun SignupDto.toResponseData(): UserDataResponse {
    return UserDataResponse(
        firstName = firstName,
        lastName = lastname,
        email = email,
        password = password,
        nonFieldErrors = nonFieldErrors
    )
}

