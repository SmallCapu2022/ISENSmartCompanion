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

        Image(
            painter = painterResource(R.drawable.isen),
            contentDescription = context.getString(R.string.isen_logo),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Text(
            text = context.getString(R.string.app_name),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.weight(1f))

        LazyColumn {
            items(chatList) { eachChat ->
                Text(eachChat)
            }
        }

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
                placeholder = { Text(context.getString(R.string.ask_question_placeholder)) },
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

                                val aiResponse = response.text ?: context.getString(R.string.no_response_generated)

                                chatList.add(context.getString(R.string.user_prefix, userInput))
                                chatList.add(context.getString(R.string.ia_prefix, aiResponse))

                                val activity = context as? MainActivity
                                activity?.saveInteraction(userInput, aiResponse)

                                userInput = ""
                            } catch (e: Exception) {
                                Log.e("gemini", "Error: ${e.message}")
                                chatList.add(context.getString(R.string.error_parsing_ai, e.message))

                            }
                        }
                    } else {
                        Toast.makeText(context, context.getString(R.string.empty_message_warning), Toast.LENGTH_SHORT).show()
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

        Spacer(modifier = Modifier.height(32.dp))
    }
}
