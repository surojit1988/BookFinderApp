package `in`.surojit.bookfinderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import `in`.surojit.bookfinderapp.ui.navigation.NavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            BookFinderAppUI()
        }
    }
}

@Composable
fun BookFinderAppUI() {
    MaterialTheme {
        Surface(modifier = Modifier) {
            NavGraph()
        }
    }
}