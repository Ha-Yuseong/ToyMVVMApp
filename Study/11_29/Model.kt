package com.mytest.mvvmTest

class Model {
    var password : MutableList<Int> = mutableListOf()

    fun inputPassword(i : Int){
        if(password.size < 4){
            password.add(i)
        }
    }
    // 비밀번호가 savePassword의 값과 같은지 확인하는 코드
    fun checkPassword() : Boolean{

        var trueCount = 0
        var savePassword = mutableListOf(1,2,3,4)

        for(i in 0 until savePassword.size){
            if(savePassword.get(i)==password.get(i)){
                trueCount++
            }
        }

        return trueCount == 4
    }
}