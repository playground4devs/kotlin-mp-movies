package com.github.omarmiatello.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.omarmiatello.androidApp.ui.SampleTheme
import sample.Platform

import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import sample.awaitTest

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
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(Platform.name)
                }
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
