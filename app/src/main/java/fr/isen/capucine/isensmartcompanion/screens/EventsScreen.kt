package fr.isen.capucine.isensmartcompanion.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.capucine.isensmartcompanion.EventDetailActivity

@Composable
fun EventsScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                val intent = Intent(context, EventDetailActivity::class.java)
                context.startActivity(intent)
            },
            content = {
                Text("Go to Event Details")
            }
        )
    }
}
