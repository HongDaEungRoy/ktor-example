package example.com.domain.pig.route.request

data class PigCreateRequest(
    val ownerId: Long,
    val pigNm: String,
    val weight: Int
)