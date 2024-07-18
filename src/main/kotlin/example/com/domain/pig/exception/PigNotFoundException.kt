package example.com.domain.pig.exception

import example.com.globals.CommonException
import org.bson.types.ObjectId

class PigNotFoundException(
    pigId: ObjectId
): CommonException(code = 404.toString(), "Pig with id: $pigId not found")