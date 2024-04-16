package com.example.roomdb.testing

fun main()
{

    fun setOnFlickListner(listner: OnClickListner){
        //Implementation
        println("talla")
        listner.onFlick()
    }
    val network = Network.Success("Successfull")
    setOnFlickListner(object :OnClickListner{
        override fun onFlick() {
            println("vamsi reddy")
        }
    })
}

interface OnClickListner{
    fun onFlick()
}


sealed class Network {
    data class Success(val data:String) : Network()
    data class Error(val message:String) :Network()
    object Loading:Network()
}

