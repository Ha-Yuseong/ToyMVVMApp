package com.jetpackCompose.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpackCompose.practice.PreviewParameter.Test
import com.jetpackCompose.practice.PreviewParameter.TestPreviewProvider
import com.jetpackCompose.practice.ui.theme.JetpackPracticeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Message()
                }
            }
        }
    }

    @Preview
    @Composable
    fun Message(){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
            .padding(16.dp)
            , horizontalAlignment = Alignment.CenterHorizontally) {

            Text("Hello")
            Text("World22")
        }
    }

    @Preview
    @Composable
    fun previewParamTest(@PreviewParameter(TestPreviewProvider::class, 4) test : Test){
        showMessage(test = test)
    }

    @Composable
    fun showMessage(test : Test){
        Column {
            Text(text = test.testInput, fontSize = 100.sp)
        }
    }

}
