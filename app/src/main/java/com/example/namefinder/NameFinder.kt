package com.example.namefinder

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Random


@Composable
fun NameFinder(
    modifier: Modifier,
    navController: NavController,
    model: NameFinderViewModel
) {
    Column(modifier = Modifier) {
        val nameListType = model.girlNames
        var currentBabyName by remember { mutableStateOf<BabyName?>(null) }
        var backgroundColor by remember { mutableStateOf(Color.Magenta) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .pointerInput(Unit) {
                    detectTapGestures { _ ->
                        // Randomly select a name on swipe
                        backgroundColor = getRandomColor()
                        currentBabyName = nameListType.random()
                        println(nameListType.size)
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ShowRandomName(
                    name = currentBabyName?.name ?: "Tap to Start",
                )
                Spacer(
                    modifier = Modifier.size(20.dp)
                )
                Row() {
                    IconButton(onClick = { currentBabyName?.let { model.addSelectedName(it) } }) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Adds Name to Favorites",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { navController.navigate("StartPage") }) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "See favorite Baby Names",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ShowRandomName(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier,
        color = Color.White,
        fontSize = 40.sp,
        fontWeight = FontWeight.Light
    )
}


fun getRandomColor(): Color {
    val random = Random()
    val red = random.nextFloat() * 0.7f + 0.15f // Range: 0.15 to 0.85
    val green = random.nextFloat() * 0.7f + 0.15f // Range: 0.15 to 0.85
    val blue = random.nextFloat() * 0.7f + 0.15f // Range: 0.15 to 0.85

    return Color(red, green, blue, alpha = 1f)
}