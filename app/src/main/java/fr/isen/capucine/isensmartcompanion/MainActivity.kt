package fr.isen.capucine.isensmartcompanion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.capucine.isensmartcompanion.database.HistoryDatabase
import fr.isen.capucine.isensmartcompanion.database.HistoryItem
import fr.isen.capucine.isensmartcompanion.screens.EventsScreen
import fr.isen.capucine.isensmartcompanion.screens.HistoryScreen
import fr.isen.capucine.isensmartcompanion.screens.MainScreen
import fr.isen.capucine.isensmartcompanion.screens.TabView
import fr.isen.capucine.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import kotlinx.coroutines.launch

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current

            val homeTab = TabBarItem(
                title = context.getString(R.string.home),
                selectedIcon = ImageVector.vectorResource(id = R.drawable.home),
                unselectedIcon = ImageVector.vectorResource(id = R.drawable.home)
            )
            val eventsTab = TabBarItem(
                title = context.getString(R.string.event),
                selectedIcon = ImageVector.vectorResource(id = R.drawable.event),
                unselectedIcon = ImageVector.vectorResource(id = R.drawable.event)
            )
            val historyTab = TabBarItem(
                title = context.getString(R.string.history),
                selectedIcon = ImageVector.vectorResource(id = R.drawable.history),
                unselectedIcon = ImageVector.vectorResource(id = R.drawable.history)
            )

            // Creating a list of all the tabs
            val tabBarItems = listOf(homeTab, eventsTab, historyTab)

            // Creating our navController
            val navController = rememberNavController()
            ISENSmartCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { TabView(tabBarItems, navController) }) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = homeTab.title) {
                            composable(homeTab.title) {
                                MainScreen(innerPadding)
                            }
                            composable(eventsTab.title) {
                                EventsScreen(innerPadding = innerPadding)
                            }
                            composable(historyTab.title) {
                                HistoryScreen(innerPadding, context) // Pass context to HistoryScreen
                            }
                        }
                    }
                }
            }
        }
    }

    fun saveInteraction(question: String, answer: String) {
        val database = HistoryDatabase.getDatabase(this)
        val historyDao = database.historyDao()

        lifecycleScope.launch {
            historyDao.insertHistoryItem(HistoryItem(question = question, answer = answer))
            Log.d("Database", "Save successful")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ISENSmartCompanionTheme {
        MainScreen(PaddingValues(8.dp))
    }
}
