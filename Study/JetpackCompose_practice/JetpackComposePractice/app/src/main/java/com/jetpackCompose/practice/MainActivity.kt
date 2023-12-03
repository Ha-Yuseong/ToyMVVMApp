package com.jetpackCompose.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
            lazyColumn()
        }
    }


    @Composable
    fun lazyColumn(){
        LazyColumn(
            modifier = Modifier
            .background(color = Color.Green)
            .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            item{
                Text("Header", fontSize = 30.sp)
            }
            items(50){
                    index ->
                Text(text = "글씨 $index", fontSize = 30.sp)
            }
            item{
                Text("Footer", fontSize = 30.sp)
            }
        }
    }
    

//    @Preview
//    @Composable
//    fun BoxTest(){
//        ShowBox(show = "hello")
//    }
//
//    @Composable
//    fun ShowBox(show : String){
//        Box(modifier = Modifier.background(color = Color.Green).fillMaxSize()){
//            Text(text = show)
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd){
//                Text("안녕하십니까~~~~~?")
//            }
//        }
//    }

//    @Preview
//    @Composable
//    fun Message(){
//        Column(modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color.LightGray)
//            .padding(16.dp)
//            , horizontalAlignment = Alignment.CenterHorizontally) {
//
//            Text("Hello", color = Color.Blue)
//            Text("World22", color = Color.Blue)
//        }
//    }

//    @Preview
//    @Composable
//    fun PreviewParamTest(@PreviewParameter(TestPreviewProvider::class, 4) test : Test){
//        ShowMessage(test = test)
//    }
//
//    @Composable
//    fun ShowMessage(test : Test){
//        Column {
//            Text(text = test.testInput, fontSize = 100.sp)
//        }
//    }

}
