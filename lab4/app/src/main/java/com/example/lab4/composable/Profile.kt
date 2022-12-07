package com.example.lab4.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab4.R

@Composable
fun Profile() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text= stringResource(id = R.string.profile),
                color = MaterialTheme.colors.onPrimary) },
                backgroundColor = MaterialTheme.colors.primary) },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bird),
                    contentDescription = stringResource(id = R.string.profile),
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    )
}