package io.github.unhurried.micronautrestapi.aspect

import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import io.micronaut.http.context.ServerRequestContext
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

/** An aspect that logs HTTP method and path of API calls. */
@Singleton
@InterceptorBean(LoggerAdvice::class)
class LoggerAspect : MethodInterceptor<Any, Any> {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun intercept(context: MethodInvocationContext<Any, Any>): Any? {
        ServerRequestContext.currentRequest<Any>().ifPresent { request ->
            log.info("API call: " + request.method + " " + request.path)
        }
        return context.proceed()
    }
}
