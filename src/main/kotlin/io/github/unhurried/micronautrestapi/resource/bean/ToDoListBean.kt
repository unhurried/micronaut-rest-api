package io.github.unhurried.micronautrestapi.resource.bean

/** Data Transfer Object for request and response bodies of ToDoResource  */
data class ToDoListBean (
    var total: Long? = null,
    var items: Iterable<ToDoBean>? = null
)