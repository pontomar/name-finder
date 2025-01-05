package com.example.namefinder

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController


@Composable
fun FavoriteNamesScreen(
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

    val favoriteNames = model.selectedNames

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp, 20.dp)
            .background(color = Color(0xFF8F4082)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top


    ) {
        Spacer(modifier = Modifier.size(50.dp))
        Text(
            "Favorite Baby Names", fontSize = 25.sp, fontWeight = FontWeight.Light, color = Color.White
        )
        Spacer(modifier = Modifier.size(30.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFC79FC0)),
            modifier = Modifier.padding(30.dp, 10.dp)
        ) {
            FavoriteList(favoriteNames, model = model)
            // UserInputRow()
        }
        Spacer(modifier = Modifier.size(30.dp))
        Button(
            onClick = { navController.navigate("NameFinder") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),

            ) {
            Text("Discover Baby Names", fontWeight = FontWeight.SemiBold , color =  Color(0xFF8F4082) )

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
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = favorite.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .weight(1f)
                .padding(20.dp, 6.dp)
        )
        IconButton(onClick = { showDialog = true }) {
            Icon(
                Icons.Filled.Info,
                contentDescription = "Show Information about this name",
                tint = Color.DarkGray,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
            )
        }
        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Some more information about the name - coming probably not so soon.")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { showDialog = false },  colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFC79FC0))) {
                            Text("Close")
                        }
                    }
                }
            }
        }

        IconButton(onClick = { model.removeSelectedName(favorite)}) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Remove Name from List",
                tint = Color.DarkGray,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)

            )
        }
    }
}

