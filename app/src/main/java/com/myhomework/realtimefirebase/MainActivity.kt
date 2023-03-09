package com.myhomework.realtimefirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.myhomework.realtimefirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.reference

        binding.btnSave.setOnClickListener {
            val name = binding.tvName.text.toString()
            val age = binding.tvAge.text.toString()
            val id = binding.tvId.text.toString()

            val person = hashMapOf(
                "name" to name,
                "age" to age,
                "id" to id
            )
            myRef.child("person").child("$count").setValue(person)
            count++
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }

        binding.btnGetData.setOnClickListener {
            myRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.value
                    binding.tvData.setText(value.toString())
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Failure", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}