package com.example.crudexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.crudexample.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Firebase
        auth = Firebase.auth
        //binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()

        //init
        val etEmail = binding.etEmail
        val etPassword = binding.etPassword
        val btnRegister = binding.btnRegister
        val btnLogout = binding.btnLogout
        val btnLogin = binding.btnLogin

        btnRegister.setOnClickListener {
            auth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Register Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        btnLogout.setOnClickListener{
            auth.signOut()
            Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()
        }

        btnLogin.setOnClickListener{
            auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show()

                        val intentToMessageBoardActivity = Intent(this, MessageBoardActivity::class.java)
                        startActivity(intentToMessageBoardActivity)
                    } else {
                        Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}