package com.example.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.roomdb.dao.UserDao
import com.example.roomdb.database.UserDatabase
import com.example.roomdb.databinding.ActivityMainBinding
import com.example.roomdb.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.sqlcipher.database.SQLiteDatabase

class MainActivity : AppCompatActivity()
{

    lateinit var binding: ActivityMainBinding
    lateinit var database: UserDatabase
    lateinit var dao: UserDao


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        SQLiteDatabase.loadLibs(this)
        database = UserDatabase.getInstance(this)
        dao = database.userDao();


        binding.b1.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val searchedText = binding.et1.text.toString().trim()
                val userList: List<User> = dao.makeQuery(searchedText)

                withContext(Dispatchers.Main) {
                    println(userList.toString())
                    if (userList.isNotEmpty()) {
                        binding.result.text = userList[0].toString()
                    }
                }
            }

        }

    }

}