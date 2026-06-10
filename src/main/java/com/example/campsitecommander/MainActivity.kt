package com.example.campsitecommander

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.campsitecommander.ui.theme.CampsiteCommanderTheme
import kotlinx.coroutines.delay

// --- Nature-Themed Colour Palette ---
val DarkForestGreen = Color(0xFF1B4332)
val SoftSageGreen = Color(0xFFD8F3DC)
val CampfireOrange = Color(0xFFD00000)
val EarthBrown = Color(0xFF744A34)
val OffWhite = Color(0xFFF8F9FA)

// --- Navigation Destinations ---
enum class AppScreen {
    SPLASH, MAIN, ADD_GEAR, DETAILED_VIEW
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampsiteCommanderTheme {
                CampsiteCommanderApp()
            }
        }
    }
}

@Composable
fun CampsiteCommanderApp() {
    // Screen navigation tracking state
    var currentScreen by remember { mutableStateOf(AppScreen.SPLASH) }

    // --- Data Storage: Parallel Arrays via State Lists ---
    val itemNames = remember { mutableStateListOf("tent", "marshmallows", "flashlight") }
    val itemCategories = remember { mutableStateListOf("shelter", "food", "safety") }
    val itemQuantities = remember { mutableStateListOf(2, 6, 8) }
    val itemComments = remember { mutableStateListOf("4-person waterproof", "(mega size)", "check batteries(AA)") }

    // --- Screen Navigation Logic Switcher ---
    when (currentScreen) {
        AppScreen.SPLASH -> SplashScreen {
            AppScreen.MAIN
        }
        AppScreen.MAIN -> MainScreen(
            quantities = itemQuantities,
            onNavigateToAdd = { currentScreen = AppScreen.ADD_GEAR },
            onNavigateToView = { currentScreen = AppScreen.DETAILED_VIEW }
        )
        AppScreen.ADD_GEAR -> AddGearScreen(
            names = itemNames,
            categories = itemCategories,
            quantities = itemQuantities,
            comments = itemComments,
            onBackToMain = { currentScreen = AppScreen.MAIN }
        )
        AppScreen.DETAILED_VIEW -> DetailedViewScreen(
            items = itemNames,
            categories = itemCategories,
            quantities = itemQuantities,
            comments = itemComments
        )
    }
}

@Composable
fun DetailedViewScreen(
    items: SnapshotStateList<String>,
    categories: SnapshotStateList<String>,
    quantities: SnapshotStateList<Int>,
    comments: SnapshotStateList<String>
) {
    TODO("Not yet implemented")
}

// 1. --- SPLASH SCREEN ---
@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkForestGreen),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🌲🔥🌲",
                fontSize = 64.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "CAMPSITE COMMANDER",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = OffWhite,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            CircularProgressIndicator(color = SoftSageGreen)
        }
    }
}

// 2. --- MAIN SCREEN ---
@Composable
fun MainScreen(
    quantities: List<Int>,
    onNavigateToAdd: () -> Unit,
    onNavigateToView: () -> Unit
) {
    var totalItems = 0
    for (i in 0 until quantities.size) {
        totalItems += quantities[i]
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = SoftSageGreen
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "⛺ Base Camp ⛺",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkForestGreen
                )
                Text(
                    text = "Command central for your gear",
                    fontSize = 16.sp,
                    color = EarthBrown
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = DarkForestGreen)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TOTAL ITEMS PACKED",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = SoftSageGreen,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$totalItems",
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Black,
                        color = OffWhite
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onNavigateToAdd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CampfireOrange),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "➕ ADD GEAR", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OffWhite)
                }

                Button(
                    onClick = onNavigateToView,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EarthBrown),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "📋 VIEW CHECKLIST", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OffWhite)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// 3. --- ADD GEAR SCREEN ---
@Composable
fun AddGearScreen(
    names: MutableList<String>,
    categories: MutableList<String>,
    quantities: MutableList<Int>,
    comments: MutableList<String>,
    onBackToMain: () -> Unit
) {
    var inputName by remember { mutableStateOf("") }
    var inputCategory by remember { mutableStateOf("") }
    var inputQuantity by remember { mutableStateOf("") }
    var inputComment by remember { mutableStateOf("") }
    var errorFeedback by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = OffWhite
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Log Camping Gear",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkForestGreen
            )

            HorizontalDivider(color = SoftSageGreen, thickness = 2.dp)

            OutlinedTextField(
                value = inputName,
                onValueChange = { inputName = it },
                label = { Text("Item Name (e.g., Stove)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = inputCategory,
                onValueChange = { inputCategory = it },
                label = { Text("Category (shelter, cooking, safety, food...)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = inputQuantity,
                onValueChange = { inputQuantity = it },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}



