package com.example.crudexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WriteMessageBoardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_message_board)

        //Firebase
        auth = Firebase.auth

        val btnWrite : Button = findViewById(R.id.btnWrite)
        val etWriteMessageBoard : EditText = findViewById(R.id.etWriteMessageBoard)

        btnWrite.setOnClickListener {
            // Write a message to the database
            val database = Firebase.database
            val refMessageBoard = database.getReference("Message Board")

            val currentUserUID = auth.currentUser?.uid.toString()
            val message = etWriteMessageBoard.text.toString()

            refMessageBoard.child(currentUserUID).push().setValue(ModelMessage(message))
            Toast.makeText(this, "Message : <${message}> created", Toast.LENGTH_SHORT).show()

            val intentToMessageBoardActivity = Intent(this, MessageBoardActivity::class.java)
            startActivity(intentToMessageBoardActivity)

        }

    }
}