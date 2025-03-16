package com.newton.core.enums

/**
 * Enum representing different types of errors for easier handling
 */
enum class ErrorType {
    CONNECTIVITY,
    TIMEOUT,
    IO,

    CLIENT_ERROR,
    AUTHENTICATION,
    AUTHORIZATION,
    VALIDATION,
    NOT_FOUND,
    CONFLICT,
    RATE_LIMIT,
    SERVER_ERROR,
    HTTP_ERROR,

    UNKNOWN,
    FATAL
}