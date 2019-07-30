package com.love.model

import java.io.Serializable

/**
 * @author Jason
 * @description:
 * @date :2019-07-29 16:15
 */
open class BaseResponse : Serializable {
    var reason: String? = null
    var error_code: Int = 0
}