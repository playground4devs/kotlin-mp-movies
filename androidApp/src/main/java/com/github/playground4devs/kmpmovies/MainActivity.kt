package com.github.playground4devs.kmpmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.playground4devs.kmpmovies.ui.SampleTheme
import com.github.playground4devs.movies.Platform

import androidx.compose.Composable
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.setContent
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take

import androidx.compose.remember
import androidx.ui.foundation.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Menu
import com.github.playground4devs.movies.awaitTest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            delay(1000)
            val response = Platform.awaitResponse()
            val test = awaitTest()
            println("Response from lib: $response - $test")
            Platform.flowOfResponse().take(10).collect {
                println("Response from lib: $it")
            }
        }

        setContent {
            SampleTheme {

                val scaffoldState = remember { ScaffoldState() }
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = { Text("Simple Scaffold Screen") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scaffoldState.drawerState = DrawerState.Opened
                                }) {
                                    Icon(Icons.Filled.Menu)
                                }
                            }
                        )
                    },
                    floatingActionButtonPosition = Scaffold.FabPosition.End,
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            text = { Text("Inc") },
                            onClick = { /* fab click handler */ }
                        )
                    },
                    bodyContent = { innerPadding ->
                        //ScrollableColumn(contentPadding = innerPadding) {  // from dev15
                        VerticalScroller {

                            // A surface container using the 'background' color from the theme
                            Surface(color = MaterialTheme.colors.background) {
                                Greeting(Platform.name)
                            }
//                            repeat(100) {
//                                Box(
//                                    Modifier.fillMaxWidth().preferredHeight(50.dp),
//                                    backgroundColor = colors[it % colors.size]
//                                )
//                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SampleTheme {
        Greeting(Platform.name)
    }
}
