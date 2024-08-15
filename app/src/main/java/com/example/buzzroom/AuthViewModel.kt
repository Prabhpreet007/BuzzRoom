package com.example.buzzroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel:ViewModel() {
    private val auth:FirebaseAuth=FirebaseAuth.getInstance()

    private val _authState=MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        if(auth.currentUser==null){
            _authState.value=AuthState.UnAuthenticated
        }
        else{
            _authState.value=AuthState.Authenticated
        }
    }

    fun login(email:String,password:String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value=AuthState.Error("Email or password can't be empty")
            return

        }

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                task->
                if(task.isSuccessful){
                    _authState.value=AuthState.Authenticated
                }
                else{
                    _authState.value=AuthState.Error(task.exception?.message?:"Something went wrong")
                }

            }
    }


    fun signUp(email:String,password:String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value=AuthState.Error("Email or password can't be empty")
            return

        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                    task->
                if(task.isSuccessful){
                    _authState.value=AuthState.Authenticated
                }
                else{
                    _authState.value=AuthState.Error(task.exception?.message?:"Something went wrong")
                }

            }
    }

    fun signout(){
        auth.signOut()
        _authState.value=AuthState.UnAuthenticated
    }
}

sealed class AuthState{
    object Authenticated:AuthState()
    object UnAuthenticated:AuthState()
    object Loading:AuthState()
    data class Error(val message:String):AuthState()
}