package example.com.domain.pig.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Pig(
    @BsonId val id: ObjectId = ObjectId(),
    val ownerId: Long,
    val pigNo: String,
    val weight: Int
)
