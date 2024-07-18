package example.com.domain.user.dao

import example.com.domain.user.entity.User
import example.com.domain.user.entity.Users
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

//     //complicated query by queryDsl
//     fun findUsersWithCompletedOrdersInLastMonth(): List<User> = transaction {
//        Users.select {
//            Users.id inSubQuery (Orders
//                .slice(Orders.userId)
//                .select {
//                    (Orders.status eq OrderStatus.COMPLETED) and
//                    (Orders.completedAt greaterEq LocalDate.now().minusMonths(1))
//                }
//                .distinct())
//        }.map { User.wrapRow(it) }
//    }
//
//    fun getUser(): User {
//        transaction {
//            (Users
//                .join(Pigs, JoinType.LEFT, Users.id, Pigs.ownerId)
//                .join(Farms, JoinType.INNER, Users.id, Farms.ownerId)
//                .select(...)
//            )
//            .orderBy(Users.id to SortOrder.DESC)
//            .map{
//                DTO(
//                    mapping...
//                )
//            }
//        }
//    }
}