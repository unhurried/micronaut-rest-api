package io.github.unhurried.micronautrestapi

import io.github.unhurried.micronautrestapi.resource.bean.ToDoBean
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions

@MicronautTest
class MicronautRestApiTest {
    @Inject
    lateinit var application: EmbeddedServer

    @Inject
    @field:Client("/") // @Client needs to be specified to the property of the Java class.
    lateinit var client: HttpClient

    @Test
    fun testPost() {
        val reqBody = ToDoBean(title = "test title", category = ToDoBean.Category.one, content = "test content")
        var response = client.toBlocking().exchange(
            HttpRequest.POST<ToDoBean>("/api/todos", reqBody),
            ToDoBean::class.java)
        Assertions.assertEquals(201, response.status().code)
        val resBody = response.body()
        Assertions.assertNotNull(resBody)
        Assertions.assertEquals(1, resBody?.id)
        Assertions.assertEquals(reqBody.category, resBody?.category)
        Assertions.assertEquals(reqBody.content, resBody?.content)
    }
}
