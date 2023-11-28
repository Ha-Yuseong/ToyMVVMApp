package com.mytest.mvvmTest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import com.mytest.mvvmTest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var viewModel = ViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        viewModel.toastMessage.observe(this, Observer {
            Toast.makeText(this, "$it 번을 클릭했습니다.", Toast.LENGTH_SHORT).show()
        })

        // Observable은 생명주기를 따라가지 않기 때문에 MainActivity가 destroy되도 메모리에 남아있다.
        // side Effect가 발생할 수 있으므로 주의해서 사용하기
        viewModel.checkPasswordMessage.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(viewModel.checkPasswordMessage.get()==true){
                    binding.messageSuccess.text = "비밀번호가 틀립니다!"
                    if(viewModel.passCheck){
                        binding.messageSuccess.text = "비밀번호가 맞습니다!"
                    }
                    binding.messageSuccess.visibility = View.VISIBLE
                }else{
                    binding.messageSuccess.visibility = View.INVISIBLE
                }

            }

        })

    }

}