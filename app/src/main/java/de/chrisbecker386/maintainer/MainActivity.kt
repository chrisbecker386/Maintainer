package de.chrisbecker386.maintainer

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.chrisbecker386.maintainer.ui.composable.MaintainerAppBar
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaintainerApp()
        }
    }

    @Composable
    fun MaintainerApp() {
        MaintainerTheme {
            Scaffold(topBar = {
                MaintainerAppBar(
                    title = "title",
                    false,
                    false,
                    modifier = Modifier.height(40.dp)

                )
            }) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }
        }
    }

    @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    fun DefaultPreview() {
        MaintainerApp()
    }
}
