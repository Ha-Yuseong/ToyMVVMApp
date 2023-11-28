package com.mytest.mvvmTest

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData

class ViewModel {

    var toastMessage = MutableLiveData<Int>()
    var checkPasswordMessage = ObservableField<Boolean>(false)
    var passCheck = true

    var model = Model()

    // 클릭된 숫자를 가져와서 Model의 MutableList에 추가해준다.
    fun clickNumber(i : Int){
        toastMessage.value = i
        model.inputPassword(i)

        checkPasswordMessage.set(false)

        // model의 리스트 크기가 4가 되었다면 비밀번호가 맞는지 확인한다.
        if(model.password.size == 4){
            // 맞다면 passCheck 값에 비밀번호 비교 결과를 넣는다.
            passCheck = model.checkPassword()
            // checkPasswordMessage에 true값을 줘서 Observer가 변화를 감지하도록 만들어준다.
            checkPasswordMessage.set(true)
            model.password.clear()
        }
    }
}