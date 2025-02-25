package fr.isen.capucine.isensmartcompanion.screens

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
import androidx.compose.ui.unit.dp
import fr.isen.capucine.isensmartcompanion.models.EventModel

@Composable
fun EventsScreen(innerPadding: PaddingValues) {
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
                EventRow(event)
            }
        }
    }
}

@Composable
fun EventRow(event: EventModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                Log.d("event", "event")
    },
        shape = MaterialTheme.shapes.medium // Design arrondi
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "📅 ${event.date}", style = MaterialTheme.typography.bodySmall)
            Text(text = "📍 ${event.location}", style = MaterialTheme.typography.bodySmall)
            Text(text = "🗂️ Catégorie : ${event.category}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
