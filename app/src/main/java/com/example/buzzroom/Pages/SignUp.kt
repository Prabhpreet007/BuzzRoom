package com.example.buzzroom.Pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.buzzroom.AuthState
import com.example.buzzroom.AuthViewModel

@Composable
fun SignUp(modifier: Modifier=Modifier, navController: NavHostController, authViewModel: AuthViewModel){
    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    val authState=authViewModel.authState.observeAsState()
    val context= LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated->navController.navigate("home")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message,Toast.LENGTH_SHORT).show()
            else ->Unit
        }
    }

    Column(modifier=Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "SignUp", fontSize = 32.sp,color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email=it},
            label = { Text(text = "Email",color = Color.Black) }
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password=it},
            label = { Text(text = "password",color = Color.Black) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            authViewModel.signUp(email,password)
        },colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black // or any background color you prefer
        )) {
            Text(text = "Create Account",color = Color.White)
        }

        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

            Text(text = "Already have an account?",color = Color.Black
            )

            TextButton(onClick = {
                navController.navigate("Login")
            },)
            {
                Text(text = "Login",color = Color.Black)
            }
        }

    }
}