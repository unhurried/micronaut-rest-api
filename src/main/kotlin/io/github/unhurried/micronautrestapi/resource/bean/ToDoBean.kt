package io.github.unhurried.micronautrestapi.resource.bean

import io.micronaut.core.annotation.Introspected

/** Data Transfer Object for request and response bodies of ToDoResource  */
@Introspected
data class ToDoBean (
    var id: Long? = null,
    var title: String? = null,
    var category: Category? = null,
    var content: String? = null
) {
    enum class Category {
        one, two, three
    }
}