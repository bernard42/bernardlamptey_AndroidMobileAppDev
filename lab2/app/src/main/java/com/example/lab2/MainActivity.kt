package com.example.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainMethod()
        }
    }
}


@Composable
fun mainMethod() {
    var message by remember { mutableStateOf("") }
    var txtMessage by remember { mutableStateOf("") }
    var imageId by remember { mutableStateOf("") }
    var tempImageId by remember { mutableStateOf(R.drawable.bird.toString()) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        getInput(name = txtMessage, changed = { txtMessage = it })
        executeButton { imageId = if(tempImageId == R.drawable.bird.toString()) (R.drawable.butterfly.toString())
        else (R.drawable.bird.toString())
            ; message = txtMessage }

        if(imageId == ""){
            displayImage()
        }
        else{
            var img = imageId.toInt();
            displayImage(img)
            tempImageId = imageId
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Black)
        ) {
            outputMessage(inMessage = message)
        }
    }
}

@Composable
fun executeButton(clicked: () -> Unit){
    Button(onClick= clicked) {
        Text(stringResource(id = R.string.execute))
    }
}

@Composable
fun displayImage(@DrawableRes img: Int = R.drawable.bird){
    Image(
        painter = painterResource(id = img),
        contentDescription = stringResource(id = R.string.image),
        modifier = Modifier
            .padding(top = 40.dp, bottom = 40.dp)
            .size(190.dp)
            .clip(CircleShape)
    )
}

@Composable
fun getInput(name: String, changed: (String) -> Unit){
    TextField(
        value = name,
        label = { Text(stringResource(id = R.string.inMessage)) },
        onValueChange = changed,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
    )
}

@Composable
fun outputMessage(inMessage: String){
    if(inMessage.isNotEmpty()) {
        Text(
            inMessage,
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}

