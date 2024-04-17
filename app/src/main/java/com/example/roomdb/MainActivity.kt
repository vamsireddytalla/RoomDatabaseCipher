package com.example.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.roomdb.dao.UserDao
import com.example.roomdb.database.SQLCipherUtils
import com.example.roomdb.database.UserDatabase
import com.example.roomdb.databinding.ActivityMainBinding
import com.example.roomdb.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.sqlcipher.database.SQLiteDatabase
private const val TAG = "MainActivity"
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
        dao = database.userDao()


        refreshData()
        binding.b2.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val name = binding.name.text.toString()
                val email = binding.email.text.toString()
                val no = binding.phone.text.toString()
                val user = User(name=name, email = email, phnNumber = no)
                dao.insert(user)

                binding.name.text.clear()
                binding.email.text.clear()
                binding.phone.text.clear()
                refreshData()
            }
        }

    }

    fun refreshData(){
        lifecycleScope.launch(Dispatchers.IO) {
            val list = dao.getUsersList()
            withContext(Dispatchers.Main){
                val adapter = ArrayAdapter(this@MainActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    list.map { it.name.toString()+"\n"+it.phnNumber.toString()+"\n"+it.email.toString() })
                binding.list.adapter = adapter
            }
        }
    }

}