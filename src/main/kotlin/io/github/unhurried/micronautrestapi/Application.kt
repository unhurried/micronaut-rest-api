package io.github.unhurried.micronautrestapi

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("io.github.unhurried.micronautrestapi")
		.start()
}

