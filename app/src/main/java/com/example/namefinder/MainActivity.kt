package com.example.namefinder

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.namefinder.ui.theme.NameFinderTheme
import com.example.namefinder.ui.theme.ocher

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NameFinderTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = ocher)
                ) { innerPadding ->
                    val navController = rememberNavController()
                    val model: NameFinderViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "StartPage") {
                        composable("StartPage") {
                            StartPage(
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

@Composable
fun StartPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    model: NameFinderViewModel,
) {
    val activity = LocalContext.current as? Activity
    DisposableEffect(Unit) {
        val originalOrientation = activity?.requestedOrientation

        // Set the screen orientation to portrait
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // When the composable leaves the composition, restore the original orientation
        onDispose {
            activity?.requestedOrientation =
                originalOrientation ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp, 20.dp)
            .background(color = Color(0xFFD7B29D)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top


    ) {
        Spacer(modifier = Modifier.size(50.dp))
        Text(
            "FAVORITE NAMES", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black
        )
        Spacer(modifier = Modifier.size(50.dp))

        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFAEDCD))
        , modifier = Modifier.padding(30.dp, 10.dp)) {
            FavoriteList(model.getSelectNames(), model = model)
           // UserInputRow()
        }
        Spacer(modifier = Modifier.size(30.dp))
        Button(
            onClick = { navController.navigate("NameFinder") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFAEDCD)
            ),
            shape = RoundedCornerShape(16.dp),

            ) {
            Text("Discover Baby Names", color = Color.Black)

        }

    }
}

@Composable
fun FavoriteList(favorites: List<BabyName>, model: NameFinderViewModel) {
    Column {
        favorites.forEach { favorite ->
            FavoriteRow(favorite, model)
        }
    }
}

@Composable
fun FavoriteRow(favorite: BabyName, model: NameFinderViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = favorite.name, fontSize = 20.sp, modifier = Modifier.weight(1f).padding(20.dp, 6.dp)
        )
        IconButton(onClick = { model.removeSelectedName(favorite) }) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Remove Name from List",
                tint = Color.Black
            )
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

