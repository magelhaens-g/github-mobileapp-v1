package com.dvlpr.githubapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dvlpr.githubapp.databinding.ActivityAlarmBinding
import com.dvlpr.githubapp.model.AlarmModel
import com.dvlpr.githubapp.preference.AlarmPreference
import com.dvlpr.githubapp.receiver.AlarmReceiver

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmModel: AlarmModel
    private lateinit var alarmReceiver: AlarmReceiver
    private val alarmType = "RepeatingAlarm"
    private val alarmTime = "09:00"
    private val alarmMessage = "Github App Reminder"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alarmPreference = AlarmPreference(this)
        if (alarmPreference.getAlarm().reminder) {
            binding.switchAlarm.isChecked = true
        } else {
            binding.switchAlarm.isChecked = false
        }

        alarmReceiver = AlarmReceiver()

        binding.apply {
            switchAlarm.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    saveReminder(true)
                    alarmReceiver.alarmRepeater(this@AlarmActivity, alarmType, alarmTime, alarmMessage)
                } else {
                    saveReminder(false)
                    alarmReceiver.cancelAlarm(this@AlarmActivity)
                }
            }
            btnBack.setOnClickListener {
                this@AlarmActivity.finish()
            }
        }
    }

    private fun saveReminder(b: Boolean) {
        val alarmPreference = AlarmPreference(this)
        alarmModel = AlarmModel()
        alarmModel.reminder = b
        alarmPreference.setAlarm(alarmModel)
    }
}