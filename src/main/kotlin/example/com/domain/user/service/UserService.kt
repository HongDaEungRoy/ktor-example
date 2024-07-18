package example.com.domain.user.service

import example.com.domain.user.dao.UserRepository
import example.com.domain.user.entity.User
import example.com.domain.user.exception.UserNotFoundException
import example.com.domain.user.route.response.UserResponse
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction


class UserService(private val userRepository: UserRepository) {
    fun create(name: String, age: Int) {
        transaction {
            User.new {
                this.name = name
                this.age = age
            }

        }
    }

    fun getById(id: Long): UserResponse {
        return transaction {
            val user = User.findById(id) ?: throw UserNotFoundException(id)
            UserResponse(user.id.value, user.name, user.age)
        }
    }

    fun someComplicatedQuery() {
        transaction {
            userRepository
        }
    }

}