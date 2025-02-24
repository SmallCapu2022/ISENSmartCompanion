package fr.isen.capucine.isensmartcompanion

import android.graphics.Paint.Align
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.isen.capucine.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        MainScreen(innerPadding)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(innerPaddingValues: PaddingValues) {
    val context = LocalContext.current
    var userInput = remember { mutableStateOf("")}
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painterResource(id = R.drawable.isen), context.getString(R.string.isen_logo))
        Text(context.getString(R.string.app_name))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            TextField(
                value = userInput.value,
                onValueChange = {newValue ->
                    userInput.value = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                modifier = Modifier.weight(1F)
            )
            OutlinedButton(onClick = {
                Toast.makeText(context, "user input : ${userInput.value}", Toast.LENGTH_LONG).show()
            },
                modifier = Modifier.background(Color.Red, shape = CircleShape),
                content = {
                    Image(painterResource(R.drawable.send), "")
                })
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ISENSmartCompanionTheme {
        MainScreen(PaddingValues(24.dp))
    }
}