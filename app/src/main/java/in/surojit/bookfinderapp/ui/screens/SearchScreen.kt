package `in`.surojit.bookfinderapp.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import `in`.surojit.bookfinderapp.domain.model.Book
import `in`.surojit.bookfinderapp.ui.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onBookClick: (Book) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    var text by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                if (it.text.length > 2) {
                    viewModel.search(it.text, refresh = true)
                }
            },
            label = { Text("Search books by title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        when {
            state.isLoading && state.items.isEmpty() -> {
                // Show shimmer list while loading first page
                LazyColumn {
                    items(6) {
                        ShimmerBookItem()
                    }
                }
            }
            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                }
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.items) { book ->
                        BookItem(book = book, onClick = { onBookClick(book) })
                    }

                    // Show shimmer row while loading next page
                    if (state.isLoading && state.items.isNotEmpty()) {
                        items(3) {
                            ShimmerBookItem()
                        }
                    }

                    // Auto-load next page
                    if (!state.isEndReached && !state.isLoading) {
                        item {
                            LaunchedEffect(state.query, state.items.size) {
                                viewModel.search(state.query, refresh = false)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        if (book.coverUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(book.coverUrl),
                contentDescription = book.title,
                modifier = Modifier.size(60.dp)
            )
        } else {
            Spacer(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.LightGray.copy(alpha = 0.5f))
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Text(book.authors, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun ShimmerBookItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ShimmerItem(
            modifier = Modifier
                .size(60.dp)
                .clip(MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            ShimmerItem(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(0.6f)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(modifier = Modifier.height(6.dp))
            ShimmerItem(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.4f)
                    .clip(MaterialTheme.shapes.small)
            )
        }
    }
}

@Composable
fun ShimmerItem(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing)),
        label = "shimmerAnim"
    )
    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.3f),
            Color.LightGray.copy(alpha = 0.6f)
        ),
        start = Offset.Zero,
        end = Offset(x = translate, y = translate)
    )
    Spacer(modifier = modifier.background(brush))
}