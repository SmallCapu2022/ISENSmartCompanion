package fr.isen.capucine.isensmartcompanion.screens

import android.widget.Toast
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.isen.capucine.isensmartcompanion.MainActivity
import fr.isen.capucine.isensmartcompanion.R
import fr.isen.capucine.isensmartcompanion.models.GenerativeModelHelper
import kotlinx.coroutines.launch

@Composable
fun MainScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    var userInput by remember { mutableStateOf("") }
    val chatList = remember { mutableStateListOf<String>() }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 📌 Logo ISEN
        Image(
            painter = painterResource(R.drawable.isen),
            contentDescription = context.getString(R.string.isen_logo),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Text(
            text = context.getString(R.string.isen_logo),
            style = MaterialTheme.typography.headlineSmall
        )

        // 📌 Espacement avant la zone de saisie
        Spacer(modifier = Modifier.weight(1f))

        // 📌 Affichage des messages sous forme de liste
        LazyColumn {
            items(chatList) { eachChat ->
                Text("$eachChat")
            }
        }

        // 📌 Row contenant le champ de saisie et le bouton d'envoi
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = userInput,
                onValueChange = { newValue -> userInput = newValue },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    if (userInput.isNotEmpty()) {
                        scope.launch {
                            try {
                                val response = GenerativeModelHelper.generativeModel.generateContent(userInput)

                                val aiResponse = response.text ?: "Aucune réponse générée"

                                // Ajout à l'affichage du chat
                                chatList.add("Utilisateur: $userInput")
                                chatList.add("IA: $aiResponse")

                                // 🔥 Sauvegarde dans la base de données
                                val activity = context as? MainActivity
                                activity?.saveInteraction(userInput, aiResponse)

                                // Réinitialisation du champ de saisie
                                userInput = ""
                            } catch (e: Exception) {
                                Log.e("gemini", "Error: ${e.message}")
                                chatList.add("Erreur lors de l'analyse de l'IA: ${e.message}")
                            }
                        }
                    } else {
                        Toast.makeText(context, "Veuillez entrer un texte avant d'envoyer", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            ) {
                Image(
                    painter = painterResource(R.drawable.send),
                    contentDescription = "Envoyer",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // 📌 Espacement avant la barre de navigation
        Spacer(modifier = Modifier.height(32.dp))
    }
}
