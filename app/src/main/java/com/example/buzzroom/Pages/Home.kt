package com.example.buzzroom.Pages

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.buzzroom.AuthState
import com.example.buzzroom.AuthViewModel
import com.example.buzzroom.LiveAudioRoomActivity
import kotlin.random.Random

@Composable
fun Home(modifier: Modifier = Modifier, navController: NavHostController, authViewModel: AuthViewModel){
    val authState=authViewModel.authState.observeAsState()

    var roomId by rememberSaveable {
        mutableStateOf("")
    }

    var username by rememberSaveable {
        mutableStateOf("")
    }

    var context= LocalContext.current
    var expanded by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.UnAuthenticated->navController.navigate("Login")
            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Centered "BuzzRoom" text
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "BuzzRoom", fontSize = 32.sp, color = Color.Black)
        }

        // Left-aligned dropdown menu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopStart // Align content to the top left
        ) {
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "More options")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Sign out") },
                    onClick = {
                        authViewModel.signout()
                        expanded = false
                    }
                )
            }
        }
    }


    Column(modifier=modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =Arrangement.Center) {

        OutlinedTextField(
            value = roomId,
            onValueChange = {roomId=it},
            label = {Text(text = "Room Id",color= Color.Black)
            })
        Spacer(modifier = modifier.height(8.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {username=it},
            label = {Text(text = "Username",color= Color.Black)})
        Spacer(modifier = modifier.height(16.dp))

        Button(onClick = {
            roomId= generateRoomID()
            val intent= Intent(context,LiveAudioRoomActivity::class.java)

            intent.putExtra("userId",username)
            intent.putExtra("roomId",roomId)
            intent.putExtra("isHost",true)
            context.startActivity(intent)

        },colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black // or any background color you prefer
        ),modifier = Modifier
            .width(145.dp)) {
            Text(text = "Create Room")
        }

        Button(onClick = {
            val intent= Intent(context,LiveAudioRoomActivity::class.java)

            intent.putExtra("userId",username)
            intent.putExtra("roomId",roomId)
            intent.putExtra("isHost",false)
            context.startActivity(intent)
                         },colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black // or any background color you prefer
        ),modifier = Modifier
            .width(145.dp)) {
            Text(text = "Join Room")
        }


    }
}

fun generateRoomID():String{
    var id=StringBuilder();

    while(id.length<5){
        var random = Random.nextInt(10)
        id.append(random)
    }
    return id.toString()
}

