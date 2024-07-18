package example.com.domain.pig.service

import example.com.domain.pig.dao.PigRepository
import example.com.domain.pig.entity.Pig
import example.com.domain.pig.exception.PigNotFoundException
import example.com.domain.pig.route.response.PigResponse
import org.bson.types.ObjectId

class PigService(private val pigRepository: PigRepository) {

    suspend fun create(ownerId: Long, pigNm: String, weight: Int) {
        val pig = Pig(ownerId = ownerId, pigNo = pigNm, weight = weight)
        pigRepository.save(pig)
    }

    suspend fun getById(id: ObjectId): PigResponse {
        return pigRepository.findById(id)?.let { PigResponse(it.id, it.ownerId, it.pigNo, it.weight) }
            ?: throw PigNotFoundException(id)

    }
}