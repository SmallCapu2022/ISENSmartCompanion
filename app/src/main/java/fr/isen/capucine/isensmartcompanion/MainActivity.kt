package fr.isen.capucine.isensmartcompanion

import android.os.Bundle
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.capucine.isensmartcompanion.screens.EventsScreen
import fr.isen.capucine.isensmartcompanion.screens.HistoryScreen
import fr.isen.capucine.isensmartcompanion.screens.MainScreen
import fr.isen.capucine.isensmartcompanion.screens.TabView
import fr.isen.capucine.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

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
            // creating a list of all the tabs
            val tabBarItems = listOf(homeTab, eventsTab, historyTab)

            // creating our navController
            val navController = rememberNavController()
            ISENSmartCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { TabView(tabBarItems, navController) }) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = homeTab.title) {
                            composable(homeTab.title) {
                                MainScreen(innerPadding)
                            }
                            composable(eventsTab.title) {
                                EventsScreen(
                                    innerPadding = innerPadding
                                )
                            }
                            composable(historyTab.title) {
                                HistoryScreen(innerPadding)
                            }
                        }
                    }
                }
            }
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