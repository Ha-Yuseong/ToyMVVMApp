package com.jetpackCompose.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
            // 안드로이드는 화면이 회전되면 액티비티가 초기화된다. 그러면 상태를 저장하는 remember도 같이 초기화되므로
            // remember보다 rememberSaveable을 사용하면 초기화되지 않는다.
            var isFavorite by rememberSaveable{
                mutableStateOf(false)
            }
            ImageCard(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(16.dp),
                isFavorite = true){
                favorite ->
                isFavorite = favorite
            }
        }

    }

    @Composable
    fun ImageCard(
        modifier: Modifier = Modifier,
        isFavorite : Boolean,
        onTabFavorite : (Boolean) -> Unit
    ){

        Card(shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = modifier ) {
            
            Box(modifier = Modifier.height(200.dp),
                ){
                Image(painter = painterResource(id = R.drawable.poster),
                    contentDescription = "poster",
                    contentScale = ContentScale.Crop)

                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopEnd){

                    IconButton(onClick = {
                        onTabFavorite(!isFavorite)
                    }) {

                        Icon(imageVector = if(!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                            contentDescription = "favorite",
                            tint = Color.White, )
                    }

                }
            }
        }
    }


//    @Composable
//    fun lazyColumn(){
//        LazyColumn(
//            modifier = Modifier
//            .background(color = Color.Green)
//            .fillMaxWidth(),
//            contentPadding = PaddingValues(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        )
//        {
//            item{
//                Text("Header", fontSize = 30.sp)
//            }
//            items(50){
//                    index ->
//                Text(text = "글씨 $index", fontSize = 30.sp)
//            }
//            item{
//                Text("Footer", fontSize = 30.sp)
//            }
//        }
//    }
    

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
