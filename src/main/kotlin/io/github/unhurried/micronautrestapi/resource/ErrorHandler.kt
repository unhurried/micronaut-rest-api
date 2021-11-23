package io.github.unhurried.micronautrestapi.resource

import io.github.unhurried.micronautrestapi.resource.bean.ErrorBean
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import org.slf4j.LoggerFactory
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

@Controller
class ErrorHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Error(global = true)
    fun handleWebApplicationException(e: WebApplicationException): Response {
        return e.response
    }

    @Error(global = true)
    fun handleThrowable(e: Throwable): HttpResponse<Any> {
        logger.error("Unexpected error happened.", e)
        return HttpResponse.serverError<Any>(ErrorBean("internal_server_error"))
    }
}
