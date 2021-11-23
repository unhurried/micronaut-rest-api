package io.github.unhurried.micronautrestapi.resource

import io.github.unhurried.micronautrestapi.aspect.LoggerAdvice
import io.github.unhurried.micronautrestapi.helper.BeanHelper
import io.github.unhurried.micronautrestapi.repository.ToDoRepository
import io.github.unhurried.micronautrestapi.repository.entity.ToDo
import io.github.unhurried.micronautrestapi.resource.bean.ToDoBean
import io.github.unhurried.micronautrestapi.resource.bean.ToDoListBean
import io.micronaut.data.model.Pageable
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/** A JAX-RS resource class for To Do items  */
@LoggerAdvice
@Transactional(rollbackOn = [Exception::class])
@Path("/todos") // Base path for all methods in the class
@Consumes(MediaType.APPLICATION_JSON) // Content-Type of request body
@Produces(MediaType.APPLICATION_JSON) // Content-Type of response body
class ToDoResource (private val repository: ToDoRepository, private val beanHelper: BeanHelper) {
    private val beanToEntity = { src: ToDoBean, target: ToDo ->
        target.category = ToDo.Category.valueOf(src.category.toString())
    }
    private val entityToBean = { src: ToDo, target: ToDoBean ->
        target.category = ToDoBean.Category.valueOf(src.category.toString()) }

    /** GET /api/todos: Get a list of items. */
    @GET
    fun getList(@QueryParam("size") @DefaultValue("10") size: Int, @QueryParam("page") @DefaultValue("0") page: Int): Response {
        val result = repository.findAll(Pageable.from(page, size))
        val beanList = result.toList().map { entity: ToDo -> beanHelper.createAndCopy(entity, ToDoBean::class.java, entityToBean) }
        val list = ToDoListBean(items = beanList, total = result.totalSize)
        return Response.ok().entity(list).build()
    }

    /** POST /api/todos: Create a new item.  */
    @POST
    fun create(reqBean: ToDoBean): Response {
        var entity = beanHelper.createAndCopy(reqBean, ToDo::class.java, beanToEntity)
        entity = repository.save(entity)
        val resBean = beanHelper.createAndCopy(entity, ToDoBean::class.java, entityToBean)
        return Response.status(201).entity(resBean).build()
    }

    /** GET /api/todos/{id}: Get a todo item.  */
    @GET
    @Path("{id}")
    operator fun get(@PathParam("id") id: Long): Response {
        val result = repository.findById(id)
        return if (result.isPresent) {
            val bean = beanHelper.createAndCopy(result.get(), ToDoBean::class.java, entityToBean)
            Response.ok().entity(bean).build()
        } else {
            throw NotFoundException();
        }
    }

    /** PUT /api/todos/{id}: Update a todo item.  */
    @PUT
    @Path("{id}")
    fun update(@PathParam("id") id: Long?, reqBean: ToDoBean): Response {
        reqBean.id = id
        val entity = beanHelper.createAndCopy(reqBean, ToDo::class.java, beanToEntity)
        repository.save(entity)
        return Response.ok().entity(reqBean).build()
    }

    /** DELETE /api/todos/{id}: Delete a todo item.  */
    @DELETE
    @Path("{id}")
    fun remove(@PathParam("id") id: Long): Response {
        val result = repository.findById(id)
        return if (result.isPresent) {
            repository.deleteById(id)
            Response.ok().build()
        } else {
            throw NotFoundException()
        }
    }
}
