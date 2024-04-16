package com.example.roomdb.testing

fun main()
{
    val sum = {a:Int,b:Int -> a+b}
    println(sum(2,2))
    println(calculate1(1,1,{a,b->a+b}))
    println("Vamsi".addLastName())
    println(Honey.sir)
    Honey.sir = "reddy"
    println(Honey.sir)
}

fun calculate1(x:Int,y:Int,operation:(Int,Int)->Int) : Int
{
  return operation(x,y);
}

fun String.addLastName() : String {
    return this+" Reddy"
}

class Honey{
    companion object{
        var sir:String = "JP"
    }
}
