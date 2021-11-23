package io.github.unhurried.micronautrestapi.repository

import io.github.unhurried.micronautrestapi.repository.entity.ToDo
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository

/** Spring Data JPA repository for ToDoEntity  */
@Repository
interface ToDoRepository : PageableRepository<ToDo, Long>
