package com.example.buzzroom

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.buzzroom.Pages.Home
import com.example.buzzroom.Pages.Login
import com.example.buzzroom.Pages.SignUp

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier,authViewModel: AuthViewModel){
    var navController= rememberNavController()
    NavHost(navController = navController, startDestination = "Login") {
        composable("Login"){
            Login(modifier,navController,authViewModel)
        }
        composable("home"){
            Home(modifier,navController,authViewModel)
        }
        composable("SignUp"){
            SignUp(modifier,navController,authViewModel)
        }
    }
}