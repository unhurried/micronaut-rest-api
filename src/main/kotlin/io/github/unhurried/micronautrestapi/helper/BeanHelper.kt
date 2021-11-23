package io.github.unhurried.micronautrestapi.helper

import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.beans.BeanWrapper
import io.micronaut.core.convert.exceptions.ConversionErrorException
import jakarta.inject.Named

@Named
class BeanHelper {
    fun <T: Any> createAndCopy(src: Any, clazz: Class<T>): T {
        return createAndCopy(src, clazz, null)
    }

    fun <S: Any, T: Any> createAndCopy(src: S, clazz: Class<T>, function: ((S, T) -> Unit)?): T {
        val target = BeanIntrospection.getIntrospection(clazz).instantiate()
        copy(src, target)
        function?.invoke(src, target)
        return target
    }

    fun <T, R> copy(src: T, dst: R) {
        val srcWrapper = BeanWrapper.getWrapper(src)
        val dstWrapper = BeanWrapper.getWrapper(dst)
        srcWrapper.beanProperties.forEach { b ->
            srcWrapper.getProperty(b.name, b.type).ifPresent { value ->
                try {
                    dstWrapper.setProperty(b.name, value)
                // Ignore properties that cannot be copied.
                } catch (e: ConversionErrorException) {}
            }
        }
    }
}