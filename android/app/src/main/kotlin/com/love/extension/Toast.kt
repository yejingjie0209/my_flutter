package com.love.extension

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG

/**
 * @author Jason
 * @description:
 * @date :2019-07-25 18:31
 */
fun Context.toast(msg: String, duration: Int = LENGTH_LONG) {
    Toast.makeText(this, msg, duration).show()
}