package com.example.namefinder

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.namefinder.ui.theme.SwitchWithIcon
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
        var backgroundColor by remember { mutableStateOf(Color(0xFF8F4082)) }

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
            Row(modifier = Modifier.
            padding(80.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center)
            {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_female_24),
                    contentDescription = "Show only girl names",
                    tint = Color.White) // Set to null or use a color if needed)
                Spacer(modifier = Modifier.size(20.dp))
                SwitchWithIcon()
                Spacer(modifier = Modifier.size(20.dp))
                Icon(
                    painter = painterResource(id = R.drawable.baseline_male_24),
                    contentDescription = "Show only boy names",
                    tint = Color.White // Set to null or use a color if needed
                )
            }

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
                    var showDialog by remember { mutableStateOf(false) }
                    IconButton(onClick = { currentBabyName?.let { model.addSelectedName(it) } }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Adds Name to Favorites",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "Information about the Name",
                            tint = Color.White
                        )
                        if (showDialog) {
                            Dialog(onDismissRequest = { showDialog = false }) {
                                Box(
                                    modifier = Modifier
                                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                                        .padding(16.dp)
                                ) {
                                    Column {

                                    }
                                    if (!currentBabyName?.altSpelling.isNullOrEmpty()) {
                                        Text("Alternative Spellings for this name:\n" + currentBabyName?.altSpelling)
                                    } else {
                                        Text("There are no alternative Spellings available for this name")
                                    }
                                    }
                                    Spacer(Modifier.height(8.dp))
                                    Button(
                                        onClick = { showDialog = false },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFC79FC0)
                                        )
                                    ) {
                                        Text("Close")
                                    }
                                }
                            }
                        }

                        IconButton(onClick = { navController.navigate("FavoriteNamesScreen") }) {
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