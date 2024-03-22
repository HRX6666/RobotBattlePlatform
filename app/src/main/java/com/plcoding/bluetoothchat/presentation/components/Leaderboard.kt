package com.plcoding.bluetoothchat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GameLeaderboard() {
    Column {
        // 前三名柱状图
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Gray)
                .padding(16.dp)
        ) {
            Text(text = "1st Place", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.Gray)
                .padding(16.dp)
        ) {
            Text(text = "2nd Place", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.Gray)
                .padding(16.dp)
        ) {
            Text(text = "3rd Place", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 其他排名横向排列
        Column {
            repeat(7) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.Gray)
                        .padding(16.dp)
                ) {
                    Text(text = "${index + 4}th Place", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
@Preview
@Composable
fun xxx(){
    GameLeaderboard()
}
