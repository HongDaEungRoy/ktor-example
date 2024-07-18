package example.com.domain.user.exception

import example.com.globals.CommonException

class UserNotFoundException(
    userId: Long
): CommonException(code = 404.toString(), "User with id: $userId not found")