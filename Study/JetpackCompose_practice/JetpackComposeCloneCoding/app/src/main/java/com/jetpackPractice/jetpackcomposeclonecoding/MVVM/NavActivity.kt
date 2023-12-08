package com.jetpackPractice.jetpackcomposeclonecoding.MVVM

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpackPractice.jetpackcomposeclonecoding.MVVM.ui.theme.JetpackComposeCloneCodingTheme

class NavActivity : ComponentActivity() {

    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeCloneCodingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Greeting(mainViewModel)
                        ButtonToNotHello(mainViewModel)
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(mainViewModel: MainViewModel?) {

    mainViewModel?.loginData?.observeAsState()?.value

    Text(
        text = mainViewModel?.loginData?.value!!,
        modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp),
        fontSize = 22.sp
    )
}

@Composable
fun ButtonToNotHello(mainViewModel: MainViewModel){

    Button(onClick = { mainViewModel.buttonClick("만나서 반가워용! 환영해요!") }, colors = ButtonDefaults.buttonColors(Color.Blue)) {
        Text(text = "여길 누르면 진심이 나옵니다.", fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeCloneCodingTheme {
        Greeting(null)
    }
}