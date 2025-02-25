package fr.isen.capucine.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.capucine.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 🔹 Récupération des données envoyées par l’intent
        val eventTitle = intent.getStringExtra("event_title") ?: "Événement inconnu"
        val eventDescription = intent.getStringExtra("event_description") ?: "Pas de description"
        val eventDate = intent.getStringExtra("event_date") ?: "Date non spécifiée"
        val eventLocation = intent.getStringExtra("event_location") ?: "Lieu inconnu"
        val eventCategory = intent.getStringExtra("event_category") ?: "Catégorie inconnue"

        setContent {
            ISENSmartCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        EventDetailCard(
                            title = eventTitle,
                            description = eventDescription,
                            date = eventDate,
                            location = eventLocation,
                            category = eventCategory
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventDetailCard(
    title: String,
    description: String,
    date: String,
    location: String,
    category: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "📅 Date : $date", style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
            Text(text = "📍 Lieu : $location", style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
            Text(text = "🗂️ Catégorie : $category", style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailPreview() {
    ISENSmartCompanionTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EventDetailCard(
                title = "Gala annuel de l'ISEN",
                description = "Soirée prestigieuse organisée par le BDE",
                date = "10 avril 2025",
                location = "Palais Neptune, Toulon",
                category = "BDE"
            )
        }
    }
}
