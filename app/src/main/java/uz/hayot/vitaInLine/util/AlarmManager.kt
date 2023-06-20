package uz.hayot.vitaInLine.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService

@RequiresApi(Build.VERSION_CODES.M)
fun setAlarm(timeInMillis: Long, context:Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MyAlarm::class.java)
     val pendingIntent = PendingIntent.getActivity(
        context,
        33,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.setRepeating(
        AlarmManager.RTC,
        timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
    Toast.makeText(context, "Alarm is set", Toast.LENGTH_SHORT).show()
}

class MyAlarm : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        Log.d("Alarm Bell", "Alarm just fired")
    }
}