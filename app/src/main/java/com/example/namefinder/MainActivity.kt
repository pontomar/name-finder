package com.example.namefinder

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.namefinder.ui.theme.NameFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NameFinderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val model: NameFinderViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "StartPage") {
                        composable("StartPage") {
                            StartPage(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                model = model
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
            activity?.requestedOrientation = originalOrientation
                ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.DarkGray)
    ) {
        FavoriteList(model.getSelectNames(), model = model)
        Button(onClick = { navController.navigate("NameFinder") }) {
            Text("Discover Baby Names", color = Color.White)

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
            text = favorite.name,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { model.removeSelectedName(favorite) }) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Remove Name from List",
                tint = Color.White
            )
        }
    }
}
