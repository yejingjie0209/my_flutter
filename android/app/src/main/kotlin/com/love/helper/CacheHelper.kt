package com.love.helper

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

/**
 * @author Jason
 * @description:
 * @date :2019-07-30 10:33
 */
object CacheHelper {
    var sp: SharedPreferences? = null

    fun init(context: Context) {
        sp = context.getSharedPreferences(context.packageName, MODE_PRIVATE)
    }

    fun save(action: SharedPreferences.Editor.() -> Unit) {
        val edit = sp?.edit()
        if (edit != null) {
            action(edit)
        }
        edit?.apply()
    }

    fun get(action: SharedPreferences.() -> Unit) {
        if (sp != null) {
            action(sp!!)
        }
    }

}
