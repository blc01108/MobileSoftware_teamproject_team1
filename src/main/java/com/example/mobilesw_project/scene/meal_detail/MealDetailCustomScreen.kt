package com.example.mobilesw_project.scene.meal_detail

// 필요한 라이브러리 및 패키지 import
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mobilesw_project.MainViewModel
import com.example.mobilesw_project.R

// 식사 세부 정보 화면 컴포저블
@Composable
fun MealDetailScreen(
    goBack: () -> Unit, // 뒤로 가기 콜백
    mainViewModel: MainViewModel = viewModel(),
    mealName: String
) {
    // ViewModel에서 식사 데이터를 검색
    val meal = mainViewModel.meals.find { it.name == mealName }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(R.drawable.frame_2), // 배경 이미지 설정
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // 이미지가 화면을 꽉 채우도록 설정
        )
    }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 아이콘 이미지 (drawable에서 가져오기)
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.backicon),
                        contentDescription = "Back Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                            .clickable { goBack() }
                    )
                    // "식사 세부 정보" 텍스트
                    Text(
                        text = "식사 세부 정보",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (meal != null) {
                item {
                    // 이미지 표시
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Gray, RoundedCornerShape(8.dp)) // 배경 설정
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(meal.imageUri),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // 음식 이름
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Text("음식 이름: ${meal.name}", fontSize = 16.sp)
                    }
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }

                // 반찬 목록
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Text("반찬 이름:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))  // 반찬 항목들과의 간격을 둡니다.
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                if (meal.sideDishes.isNotEmpty()) {
                                    // 반찬이 여러 개일 때, 각 항목이 겹치지 않도록 텍스트와 텍스트 사이에 여백을 추가
                                    meal.sideDishes.forEachIndexed { index, sideDish ->
                                        Text("${index + 1}. $sideDish", fontSize = 16.sp)
                                        Spacer(modifier = Modifier.height(4.dp))  // 각 반찬 항목 간에 여백 추가
                                    }
                                } else {
                                    Text("등록된 반찬이 없습니다.", fontSize = 16.sp, color = Color.Gray)
                                }
                            }
                        }
                        }
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }

                // 다른 항목들 (식사 종류, 날짜 등)
                val details = listOf(
                    "식사 종류: ${meal.mealType}",
                    "식사 날짜: ${meal.date}",
                    "식사 장소: ${meal.location}",
                    "가격: ${meal.price}원",
                    "칼로리: ${meal.calorie}kcal",
                    "평가: ${meal.evaluation}"
                )

                details.forEach { detail ->
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(16.dp)
                        ) {
                            Text(detail, fontSize = 16.sp)
                        }
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }

                item {
                    // 삭제 버튼
                    Button(onClick = {
                        mainViewModel.meals.remove(meal) // 선택한 식사 삭제
                    }) {
                        Text("삭제")
                    }
                }
            } else {
                item {
                    Text(
                        "해당 식사를 찾을 수 없습니다.",
                        fontSize = 16.sp,
                        color = Color.Red
                    )
                }
            }
        }
}

