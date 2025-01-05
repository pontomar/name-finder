package com.example.namefinder


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.namefinder.ui.theme.NameFinderTheme
import com.example.namefinder.ui.theme.Pink40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NameFinderTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Pink40)
                ) { innerPadding ->
                    val navController = rememberNavController()
                    val model: NameFinderViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "NameFinder") {
                        composable("FavoriteNamesScreen") {
                            FavoriteNamesScreen(
                                modifier = Modifier,
                                //.padding(innerPadding),
                                navController = navController, model = model
                            )
                        }
                        composable("NameFinder") {
                            NameFinder(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                model = model,
                            )
                        }
                    }
                }
            }
        }
    }
}


/*
@Composable
fun UserInputRow(model: NameFinderViewModel) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text("Enter your idea") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .background(color = Color.Transparent),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent// Background color for TextField
            ),           shape = RoundedCornerShape(16.dp),
        )

        Button(
            onClick = { model.addSelectedName(text) },
            modifier = Modifier.padding(start = 8.dp),
            colors = ButtonDefaults.buttonColors(ocher),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Add", color = Color.Black)
        }
    }
}
*/

