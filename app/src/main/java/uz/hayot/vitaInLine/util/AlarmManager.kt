package uz.hayot.vitaInLine.util

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import uz.hayot.vitaInLine.MainActivity
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.data.model.AlarmData
import uz.hayot.vitaInLine.util.functions.ExtraFunctions
import java.util.*


const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

@RequiresApi(Build.VERSION_CODES.M)
fun setAlarm(context: Context, alarmData: AlarmData) {
    val title = context.resources.getString(R.string.notification_title)
    val titleExtraText = context.resources.getString(R.string.notification_extra_title)

    val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val pendingIntent: PendingIntent = Intent(context, MyAlarm::class.java).let { intent ->
        intent.putExtra("time", alarmData.time)
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, titleExtraText)
        PendingIntent.getBroadcast(
            context,
            alarmData.requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }



    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        ExtraFunctions.convertTimeStringToMillis(alarmData.time),
        pendingIntent

    )

//    alarmManager.setRepeating(
//        AlarmManager.RTC_WAKEUP,
//        timeInMillis,
//        24 * 3600 * 1000,
//        pendingIntent
//
//    )

//    Toast.makeText(context, "Alarm is set", Toast.LENGTH_SHORT).show()
}

@SuppressLint("UnspecifiedImmutableFlag")
fun cancelAlarm(context: Context, alarmData: AlarmData) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MyAlarm::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarmData.requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    alarmManager.cancel(pendingIntent)
}


@RequiresApi(Build.VERSION_CODES.N)
fun createNotificationChannel(context: Context) {


    // Set the custom sound URI
    val soundUri = Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + R.raw.alarm_sound
    )

    val name = "Notif Channel"
    val desc = "A Description of the Channel"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(channelID, name, importance)
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    channel.description = desc

    val notificationManager =
        context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)

}


class MyAlarm : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        Log.d("MyLog", "Message:${intent.getStringExtra("key")}")

        try {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val mp = MediaPlayer.create(context.applicationContext, alarmSound)
            mp.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val bundle = Bundle()
        bundle.putBoolean("notification", true)
        bundle.putString("time", intent.getStringExtra("time"))
        val pendingFragmentIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.main_nav_graph)
            .setDestination(R.id.davolanishFragment)
            .setArguments(bundle)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setContentIntent(pendingFragmentIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)

    }
}