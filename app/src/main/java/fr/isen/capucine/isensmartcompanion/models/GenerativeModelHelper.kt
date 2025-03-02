package fr.isen.capucine.isensmartcompanion.models

import com.google.ai.client.generativeai.GenerativeModel

object GenerativeModelHelper {
    // Configuration du modèle "gemini-1.5-flash"
    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash", // Modèle optimisé pour texte à texte rapide
        apiKey = "AIzaSyDHl2KX1gI8oFuReFSDO5QeufaR4PNUWzE" // Ta clé API
    )
}
