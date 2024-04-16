package com.example.roomdb.testing

sealed class SealedConcepts {
    data class Success(val data:String) : SealedConcepts()
    data class Error(val meesage:String) : SealedConcepts()
}