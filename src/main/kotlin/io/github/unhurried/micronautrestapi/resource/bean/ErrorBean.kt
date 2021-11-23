package io.github.unhurried.micronautrestapi.resource.bean

/** Data Transfer Object for request and response bodies in error responses  */
data class ErrorBean (
    var errorCode: String? = null
)
