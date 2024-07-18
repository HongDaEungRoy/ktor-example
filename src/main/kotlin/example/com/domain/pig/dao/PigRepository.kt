package example.com.domain.pig.dao

import com.mongodb.client.MongoDatabase
import example.com.domain.pig.entity.Pig
import org.bson.types.ObjectId
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollection

class PigRepository(val database: MongoDatabase) {
    private val collection = database.getCollection<Pig>()

    //by KMongo
    fun save(entity: Pig) {
        collection.insertOne(entity)
    }

    fun findById(id: ObjectId) = collection.findOneById(id)


//    suspend fun save(entity: Pig) {
//        collection.insertOne(entity)
//    }
//
//    suspend fun findById(id: ObjectId) = collection.find(Filters.eq("_id", id)).firstOrNull()
}