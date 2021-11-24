package com.axis.restcrud.exception

data class Warning (
    val msg: String?
    ): RuntimeException()