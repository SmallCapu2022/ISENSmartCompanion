package fr.isen.capucine.isensmartcompanion.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.isen.capucine.isensmartcompanion.R

@Composable
fun MainScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    var userInput = remember { mutableStateOf("") }

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            text = context.getString(R.string.app_name),
            style = MaterialTheme.typography.headlineSmall
        )

        // 📌 Espacement avant la zone de saisie
        Spacer(modifier = Modifier.weight(1f))

        // 📌 Row contenant le champ de saisie et le bouton d’envoi
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = userInput.value,
                onValueChange = { newValue -> userInput.value = newValue },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            // 📌 Nouveau design du bouton d'envoi
            IconButton(
                onClick = {
                    Toast.makeText(context, "User input: ${userInput.value}", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            ) {
                Image(
                    painter = painterResource(R.drawable.send),
                    contentDescription = "Send",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // 📌 Espacement avant la barre de navigation
        Spacer(modifier = Modifier.height(32.dp))
    }
}
