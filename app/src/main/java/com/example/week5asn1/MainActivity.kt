package com.example.week5asn1

import android.Manifest
import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.week5asn1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.MANAGE_EXTERNAL_STORAGE), 24)

        binding.add.setOnClickListener{
            val db = DBHelper(this, null)
            if(binding.editFirstName.text != null && binding.editLastName.text != null && binding.editRewards.text != null &&
                    binding.editUID.text != null)
            {
                try{
                    val uid = binding.editUID.text.toString().toInt()
                    val fn = binding.editFirstName.text.toString()
                    val ln = binding.editLastName.text.toString()
                    val rewards = binding.editRewards.text.toString().toInt()
                    db.addUser(uid, fn, ln, rewards)

                    Toast.makeText(this, "User $uid $fn added to the database.", Toast.LENGTH_LONG).show()
                } catch (e : Exception)
                {
                    Toast.makeText(this, "Unknown Error.", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.displayInfo.setOnClickListener{
            val db = DBHelper(this, null)
            if(binding.displayInfoEntry.text != null)
            {
                try {
                    val uid = binding.displayInfoEntry.text.toString().toInt()
                    val cursor = db.getUser(uid)
                    if (cursor != null) {
                        binding.displayInfo.setText(cursor.getString(cursor.getColumnIndex(DBHelper.UID_COL.toString())) + "\n"
                        + cursor.getString(cursor.getColumnIndex(DBHelper.FIRST_NAME_COL)) + "\n"
                        + cursor.getString(cursor.getColumnIndex(DBHelper.LAST_NAME_COL)) + "\n"
                        + cursor.getString(cursor.getColumnIndex(DBHelper.REWARDS_COL.toString())))
                    }

                } catch( e: Exception) {
                    Toast.makeText(this, "UID Does not exist or unknown error.",
                        Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this, "Please enter the UID of the user whose info you wish to display.",
                    Toast.LENGTH_LONG).show()
            }
        }

        binding.delete.setOnClickListener{
            val db = DBHelper(this, null)
            if(binding.displayInfoEntry.text != null) {
                try {
                    val uid = binding.displayInfoEntry.text.toString().toInt()
                    db.delete(uid)
                    Toast.makeText(this,"User successfully deleted from the database.",
                        Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "User does not exist or invalid UID.",
                        Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this, "Please enter the UID of the user you wish to delete.",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}