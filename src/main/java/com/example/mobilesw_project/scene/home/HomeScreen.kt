package com.example.mobilesw_project.scene.home

// 필요한 라이브러리 및 패키지 import
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilesw_project.R

// 홈 화면 컴포저블
@Composable
fun HomeScreen(
    goToMealInput: () -> Unit, // 식사 입력 화면으로 이동하는 콜백
    goToMealRecord: () -> Unit, // 식사 기록 화면으로 이동하는 콜백
    goToMealList: () -> Unit // 식사 목록 화면으로 이동하는 콜백
) {
    Box(
        modifier = Modifier
            .fillMaxSize() // 화면 크기를 꽉 채움
    ) {
        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.homescreen_background),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 앱 제목
            Box(
                contentAlignment = Alignment.Center, // Box의 중앙에 텍스트 배치
                modifier = Modifier
                    .padding(bottom = 32.dp)
            ) {
                // 텍스트 배경 이미지
                Image(
                    painter = painterResource(id = R.drawable.rectangle_1), // 배경 이미지
                    contentDescription = "Text Background",
                    modifier = Modifier.size(width = 200.dp, height = 60.dp) // 크기 조정
                )
                // 텍스트
                Text(
                    text = "학식 기록 APP",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray// 텍스트 색상
                )
            }

            // "식사 입력" 버튼
            Button(
                onClick = { goToMealInput() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.edit_4), // 식사 입력 아이콘
                        contentDescription = "식사 입력 아이콘",
                        modifier = Modifier.size(24.dp).padding(end = 8.dp) // 이미지 크기 및 간격
                    )
                    Text(text = "식사 입력", color = Color.Black)
                }
            }

            Button(
                onClick = { goToMealList() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.list), // 식사 목록 아이콘
                        contentDescription = "식사 목록 아이콘",
                        modifier = Modifier.size(24.dp).padding(end = 8.dp)
                    )
                    Text(text = "식사 목록", color = Color.Black)
                }
            }

            // "식사 기록" 버튼
            Button(
                onClick = { goToMealRecord() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.settings), // 식사 기록 아이콘
                        contentDescription = "식사 기록 아이콘",
                        modifier = Modifier.size(24.dp).padding(end = 8.dp)
                    )
                    Text(text = "식사 기록", color = Color.Black)
                }
            }
        }
    }
}

