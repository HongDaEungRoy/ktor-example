package example.com.domain.user.entity

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable


object Users : IdTable<Long>("USERS") {
    override val id = long("id").autoIncrement("SEQ_USER_ID").entityId()
    val name = varchar("name", 100)
    val age = integer("age")
}


class User(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<User>(Users)

    var name by Users.name
    var age by Users.age
}