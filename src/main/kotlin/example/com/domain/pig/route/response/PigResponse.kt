package example.com.domain.pig.route.response

import org.bson.types.ObjectId

data class PigResponse(
    val id: ObjectId,
    val ownerId: Long,
    val pigNm: String,
    val weight: Int
) {
}