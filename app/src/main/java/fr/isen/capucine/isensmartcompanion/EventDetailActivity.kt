package fr.isen.capucine.isensmartcompanion

import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import fr.isen.capucine.isensmartcompanion.models.EventModel

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
        }


        val event = EventModel(
            id = intent.getStringExtra("event_id") ?: "",
            title = intent.getStringExtra("event_title") ?: "Unknown Event",
            description = intent.getStringExtra("event_description") ?: "No description",
            date = intent.getStringExtra("event_date") ?: "No date",
            location = intent.getStringExtra("event_location") ?: "No location",
            category = intent.getStringExtra("event_category") ?: "No category"
        )

        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EventDetailCard(event, this@EventDetailActivity)
                }
            }
        }
    }

    companion object {
        fun start(context: Context, event: EventModel) {
            val intent = Intent(context, EventDetailActivity::class.java).apply {
                putExtra("event_id", event.id)
                putExtra("event_title", event.title)
                putExtra("event_description", event.description)
                putExtra("event_date", event.date)
                putExtra("event_location", event.location)
                putExtra("event_category", event.category)
            }
            context.startActivity(intent)
        }
    }
}

@Composable
fun EventDetailCard(event: EventModel, context: Context) {
    val sharedPreferences = context.getSharedPreferences("event_prefs", Context.MODE_PRIVATE)
    var isPinned by remember { mutableStateOf(sharedPreferences.getBoolean(event.id, false)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(text = event.title, style = MaterialTheme.typography.headlineLarge)
            Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(12.dp))

            IconButton(
                onClick = {
                    isPinned = !isPinned
                    sharedPreferences.edit().putBoolean(event.id, isPinned).apply()
                    if (isPinned) {
                        scheduleNotification(context, event.title, 10)
                    }
                }
            ) {
                Icon(
                    painter = painterResource(if (isPinned) R.drawable.bell_filled else R.drawable.bell_outline),
                    contentDescription = "Set Reminder",
                    tint = if (isPinned) Color.Green else Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@SuppressLint("ScheduleExactAlarm")
fun scheduleNotification(context: Context, title: String, delaySeconds: Int) {
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("title", title)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context, title.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val triggerTime = System.currentTimeMillis() + (delaySeconds * 1000)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
            return
        }
    }

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
}

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val title = intent?.getStringExtra("title") ?: context.getString(R.string.event_reminder)
            showNotification(it, title)
        }
    }

    private fun showNotification(context: Context, title: String) {
        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(context, "event_channel")
            .setSmallIcon(R.drawable.bell_filled)
            .setContentTitle(context.getString(R.string.reminder))
            .setContentText(context.getString(R.string.event_notification, title))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "event_channel", context.getString(R.string.event_reminders_channel), NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(title.hashCode(), notificationBuilder.build())
    }
}
