package io.github.unhurried.micronautrestapi.repository.entity

import javax.persistence.*

/** A JPA entity corresponding to todo table  */
// @Entity: Indicate that the class is a JPA entity.
@Entity
// @Table: Specifies the table name if it is not same as the class name.
@Table(name = "todo")
data class ToDo (
    // @Id: Indicates that the field uniquely identifies the entity.
    @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY):
    //   Generates the primary key by using the identity columns of RDBMS.
    //   (ex. MySQL: auto_increment)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String? = null,
    var category: Category? = null,
    var content: String? = null
) {
    enum class Category {
        one, two, three
    }
}