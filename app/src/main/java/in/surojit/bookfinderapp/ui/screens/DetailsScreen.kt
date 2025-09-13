package `in`.surojit.bookfinderapp.ui.screens

import kotlin.collections.get
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import `in`.surojit.bookfinderapp.ui.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    // Rotation state
    var rotation by remember { mutableStateOf(0f) }

    LaunchedEffect(bookId) {
        viewModel.loadDetails(bookId)
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(Modifier.width(8.dp))
            Text(state.workDetails?.title ?: "Book Details", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(Modifier.height(16.dp))

        state.workDetails?.covers?.firstOrNull()?.let { coverId ->
            val painter = rememberAsyncImagePainter("https://covers.openlibrary.org/b/id/$coverId-L.jpg")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            val (dx, _) = dragAmount
                            rotation += dx // horizontal drag changes rotation
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .graphicsLayer {
                            rotationZ = rotation
                        }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Button(onClick = { viewModel.toggleFavorite(bookId) }) {
            Text(if (state.isFavorite) "Unfavorite" else "Save")
        }

        Spacer(Modifier.height(12.dp))

        state.workDetails?.let { details ->
            val desc = when (details.description) {
                is String -> details.description
                is Map<*, *> -> details.description["value"] as? String
                else -> null
            }
            desc?.let { Text(it) }
        }

        if (state.isLoading) {
            Text("Loading details...")
        }
        state.error?.let { Text("Error: $it") }
    }
}