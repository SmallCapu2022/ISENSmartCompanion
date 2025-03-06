package fr.isen.capucine.isensmartcompanion.models

import com.google.ai.client.generativeai.GenerativeModel

object GenerativeModelHelper {
    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyDHl2KX1gI8oFuReFSDO5QeufaR4PNUWzE"
    )
}
