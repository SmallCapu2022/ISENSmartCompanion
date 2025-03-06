package fr.isen.capucine.isensmartcompanion.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fr.isen.capucine.isensmartcompanion.EventDetailActivity
import androidx.compose.ui.unit.dp
import fr.isen.capucine.isensmartcompanion.R
import fr.isen.capucine.isensmartcompanion.api.NetworkManager
import fr.isen.capucine.isensmartcompanion.models.EventModel
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    var events by remember { mutableStateOf<List<EventModel>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = NetworkManager.api.getEvents()
                if (response.isSuccessful) {
                    response.body()?.let { eventList ->
                        events = eventList
                    }
                } else {
                    Log.e("EventsScreen", context.getString(R.string.api_error, response.errorBody()))
                }
            } catch (e: Exception) {
                Log.e("EventsScreen", context.getString(R.string.exception_error, e.message))

            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
                EventDetailActivity.start(context, event)
            },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = context.getString(R.string.event_date_display, event.date), style = MaterialTheme.typography.bodySmall)

        }
    }
}
