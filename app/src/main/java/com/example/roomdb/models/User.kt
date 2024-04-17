package com.example.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class User(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                val name: String,
                val email: String,
                val phnNumber: String)
