package com.dvlpr.githubapp.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Message
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.dvlpr.githubapp.R
import java.nio.file.attribute.AclEntry
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        private const val CHANNEL_ID = "01"
        private const val CHANNEL_NAME = "Github App Reminder"
        private const val NOTIFICATION_ID = 1
        private const val FORMAT_TIME = "HH:mm"
        private const val EXTRA_NOTIFICATION = "extra_notification"
        private const val EXTRA_TYPE = "extra_type"
        private const val REPEATER_ID = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        sendNotification(context)
    }

    private fun sendNotification(context: Context) {
        val intent = context?.packageManager.getLaunchIntentForPackage("com.dvlpr.githubapp")
        val pIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val notifManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pIntent)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(context.resources.getString(R.string.text_notif))
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(CHANNEL_ID)
            notifManager.createNotificationChannel(channel)
        }

        val notif = builder.build()
        notifManager.notify(NOTIFICATION_ID, notif)
    }

    fun alarmRepeater(context: Context, type: String, time: String, notif: String) {
        if (isDateInvalid(time, FORMAT_TIME)) return
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_NOTIFICATION, notif)
        intent.putExtra(EXTRA_TYPE, type)
        val time = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]))
        calendar.set(Calendar.SECOND, 0)
        val pIntent = PendingIntent.getBroadcast(context, REPEATER_ID, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pIntent)
        Toast.makeText(context, context.resources.getString(R.string.set_up_alarm), Toast.LENGTH_SHORT).show()
    }

    private fun isDateInvalid(time: String, formatTime: String): Boolean {
        return try {
            val formatDate = SimpleDateFormat(formatTime, Locale.getDefault())
            formatDate.isLenient = false
            formatDate.parse(time)
            false
        } catch (exception: ParseException) {
            true
        }
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val reqCode = REPEATER_ID
        val pIntent = PendingIntent.getBroadcast(context, reqCode, intent, 0)
        pIntent.cancel()
        alarmManager.cancel(pIntent)
        Toast.makeText(context, context.resources.getString(R.string.cancel_alarm), Toast.LENGTH_SHORT).show()
    }
}