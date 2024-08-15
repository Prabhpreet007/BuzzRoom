package com.example.buzzroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.buzzroom.ui.theme.BuzzRoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel:AuthViewModel by viewModels()
        setContent {
            BuzzRoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()){ innerPadding->
                    MyAppNavigation(modifier = Modifier.padding(innerPadding),authViewModel = authViewModel)
                }
            }
        }
    }
}
