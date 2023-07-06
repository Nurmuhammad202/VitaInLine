package uz.hayot.vitaInLine.util

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import uz.hayot.vitaInLine.MainActivity
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.data.model.AlarmData
import uz.hayot.vitaInLine.util.functions.ExtraFunctions.Companion.convertTimeStringToMillis
import java.util.*


const val notificationID = 2
const val channelID = "channel2"
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





    try {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            convertTimeStringToMillis(alarmData.time),
            pendingIntent

        )

        Toast.makeText(context, "Alarm is set", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
    }


//    alarmManager.setRepeating(
//        AlarmManager.RTC_WAKEUP,
//        ExtraFunctions.convertTimeStringToMillis(alarmData.time),
//        24 * 3600 * 1000,
//        pendingIntent
//
//    )


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

        try {
            val mediaPlayer = MediaPlayer.create(
                context,
                Uri.parse("android.resource://${context.packageName}/${R.raw.alarm_sound_two}")
            )
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


}