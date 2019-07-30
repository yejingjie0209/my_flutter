package com.love.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.Intent
import android.content.Intent.CATEGORY_DEFAULT
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color
import android.net.Uri
import android.net.Uri.fromParts
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.*
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.support.v4.app.NotificationManagerCompat


/**
 * @author Jason
 * @description:
 * @date :2019-07-24 15:23
 */
object PermissionUtils {


    fun checkNotify(context: Context): Boolean {
        val notification = NotificationManagerCompat.from(context)
        val isEnabled = notification.areNotificationsEnabled()
        if (!isEnabled) {
            //未打开通知
            if(context is Activity){
                val alertDialog = AlertDialog.Builder(context)
                        .setTitle("提示")
                        .setMessage("请jason在“通知”中打开通知权限")
                        .setNegativeButton("我就不") { dialog, _ -> dialog.cancel() }
                        .setPositiveButton("好哒") { dialog, _ ->
                            dialog.cancel()
                            val intent = Intent()
                            when {
                                SDK_INT >= O -> {
                                    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                                    intent.putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
                                }
                                SDK_INT >= LOLLIPOP -> {  //5.0
                                    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                                    intent.putExtra("app_package", context.packageName)
                                    intent.putExtra("app_uid", context.applicationInfo.uid)
                                    context.startActivity(intent)
                                }
                                SDK_INT == KITKAT -> {  //4.4
                                    intent.action = ACTION_APPLICATION_DETAILS_SETTINGS
                                    intent.addCategory(CATEGORY_DEFAULT)
                                    intent.data = Uri.parse("package:" + context.packageName)
                                }
                                SDK_INT >= 16 -> {
                                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                                    intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                                    intent.data = fromParts("package", context.packageName, null)
                                }
                            }
                            context.startActivity(intent)
                        }.create()
                alertDialog.show()
                alertDialog.getButton(BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
            }
        }
        return isEnabled
    }
}