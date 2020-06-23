package com.example.thecats.utils

enum class Error(val message: String) {
    UNEXPECTED("Unexpected error")
}

fun convertError(t: Throwable): Error {
    return when (t) {
        is Exception -> Error.UNEXPECTED
        else -> Error.UNEXPECTED
    }
}

