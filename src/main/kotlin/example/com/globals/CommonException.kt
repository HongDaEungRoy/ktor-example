package example.com.globals

open class CommonException(
    val code: String,
    override val message: String
): RuntimeException() {
}