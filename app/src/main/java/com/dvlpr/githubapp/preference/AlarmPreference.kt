package com.dvlpr.githubapp.preference

import android.content.Context
import com.dvlpr.githubapp.model.AlarmModel

class AlarmPreference(context: Context) {
    companion object {
        const val PREF_NAME = "alarm_pref"
        private const val ALARM = "isRemind"
    }

    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setAlarm(value: AlarmModel) {
        val editor = pref.edit()
        editor.putBoolean(ALARM, value.reminder)
        editor.apply()
    }

    fun getAlarm(): AlarmModel {
        val model = AlarmModel()
        model.reminder = pref.getBoolean(ALARM, false)
        return model
    }
}