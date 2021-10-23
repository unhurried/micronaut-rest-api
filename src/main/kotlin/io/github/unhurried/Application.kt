package io.github.unhurried

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("io.github.unhurried")
		.start()
}

