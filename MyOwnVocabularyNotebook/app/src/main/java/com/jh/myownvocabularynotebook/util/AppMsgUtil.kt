package com.jh.myownvocabularynotebook.util

import android.app.Activity
import com.devspark.appmsg.AppMsg
import com.jh.myownvocabularynotebook.R

class AppMsgUtil {

    companion object {
        fun showMsg(text: String, activity: Activity) {
            val appMsg =
                AppMsg.makeText(activity, text, AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.app_main_color))
            appMsg.duration = Const.DELAY_SHOW_MSG
            appMsg.show()
        }

        fun showErrMsg(text: String, activity: Activity) {
            val appMsg =
                AppMsg.makeText(
                    activity,
                    text,
                    AppMsg.Style(AppMsg.LENGTH_SHORT, com.devspark.appmsg.R.color.alert)
                )
            appMsg.duration = Const.DELAY_SHOW_MSG
            appMsg.show()
        }
    }
}