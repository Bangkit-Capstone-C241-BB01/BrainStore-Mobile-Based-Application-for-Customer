package com.xc.brainstore.utils

object Validation {
    val emailRegex = Regex("^[a-zA-Z0-9.+_-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    val nameRegex = Regex("^[a-zA-Z ]+\$")
    val passRegex =
        Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()\\-_=+{}\\[\\]:;<>,.?]).{8,}\$")
}