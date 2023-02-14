package com.example.crudexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MessageBoardActivity : AppCompatActivity() {

    lateinit var lvMessageAdapter : LVMessageAdapter
    val messages = mutableListOf<ModelMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_board)

        val btnWriteMessageBoard = findViewById<Button>(R.id.btnWriteMessageBoard)

        btnWriteMessageBoard.setOnClickListener {
            val intentToWriteMessageBoardActivity = Intent(this, WriteMessageBoardActivity::class.java)
            startActivity(intentToWriteMessageBoardActivity)
        }

        lvMessageAdapter = LVMessageAdapter(messages)

        val lvMessages : ListView = findViewById(R.id.lvMessages)
        lvMessages.adapter = lvMessageAdapter

        getData()

    }

    fun getData() {
        val database = Firebase.database
        val refMessageBoard = database.getReference("Message Board")

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messages.clear()
                for(dataModel in dataSnapshot.children) {
                    for(data in dataModel.children) {
                        val message = data.getValue(ModelMessage::class.java)
                        messages.add(message!!)
                    }
                }
                lvMessageAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MessageBoardActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        refMessageBoard.addValueEventListener(postListener)
    }
}