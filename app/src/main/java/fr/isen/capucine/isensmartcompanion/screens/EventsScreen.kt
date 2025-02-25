package fr.isen.capucine.isensmartcompanion.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.capucine.isensmartcompanion.EventDetailActivity
import fr.isen.capucine.isensmartcompanion.models.EventModel

@Composable
fun EventsScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val events = EventModel.fakeEvents()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            items(events) { event ->
                EventRow(event, context)
            }
        }
    }
}

@Composable
fun EventRow(event: EventModel, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                Log.d("event", "Clicked on: ${event.title}")
                val intent = Intent(context, EventDetailActivity::class.java).apply {
                    putExtra("event_id", event.id)
                    putExtra("event_title", event.title)
                    putExtra("event_description", event.description)
                    putExtra("event_date", event.date)
                    putExtra("event_location", event.location)
                    putExtra("event_category", event.category)
                }
                context.startActivity(intent)
            },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "📅 ${event.date}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
