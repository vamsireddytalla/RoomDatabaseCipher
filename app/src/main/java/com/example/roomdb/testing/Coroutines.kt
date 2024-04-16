package com.example.roomdb.testing

import android.security.keystore.KeyProperties
import android.view.animation.Transformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.security.KeyStore

suspend fun main() {
//    val sum = {a:Int,b:Int->a+b}// lambda expression
//    val result = sum(3,4) // invoking the lambda expression
//    println(result.toString())
//    val ress = calculate(2,3) { a, b -> a + b }
//    println(Constants.myField)


    runBlocking {
        val scope = CoroutineScope(Dispatchers.Default)
        val jobs = mutableListOf<Job>()
        for (i in 0 until 10) {
            val taskId = i
            val job = scope.launch {
                val random:Double = (Math.random() * 5000)
                println("Task $taskId started Time is ${random.toLong()}")
                delay(random.toLong()) // Simulating some work
                println("Task $taskId completed")
            }
            jobs.add(job)
        }
        jobs.forEach { it.join() }
        scope.coroutineContext.cancelChildren()
    }
    println("task finished")


}

class  Vamsi{
    companion object{
        private val keystore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
        val myField = 10
        fun callMe(){

        }
    }
}



fun calculate(x:Int,y:Int,op:(Int,Int)->Int) : Int
{
  return op(x,y);
}



