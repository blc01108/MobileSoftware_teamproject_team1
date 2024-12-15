package com.example.mobilesw_project.scene.meal_input

// 필요한 라이브러리 및 패키지 import
import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mobilesw_project.MainViewModel
import com.example.mobilesw_project.R
import com.example.mobilesw_project.data.Meal
import java.util.Calendar
import kotlin.random.Random

@Composable
fun MealInputScreen(
    mainViewModel: MainViewModel = viewModel(), // ViewModel 주입
    goBack: () -> Unit, // 뒤로 가기 콜백
) {
    val context = LocalContext.current
    val purple = Color(128, 0, 128) // RGB 값으로 보라색 설정
    // 각 입력 필드의 상태 관리
    var mealDate by remember { mutableStateOf("") }
    var mealName by remember { mutableStateOf("") }
    var mealCost by remember { mutableStateOf("") }
    var mealEvaluation by remember { mutableStateOf("") }
    var mealImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedLocation by remember { mutableStateOf("") }
    var selectedMealType by remember { mutableStateOf("") }
    var sideDishes by remember { mutableStateOf(listOf("")) } // 반찬 리스트 상태 관리

    // 드롭다운 메뉴의 옵션들
    val locations = listOf(
        "상록원 1층", "상록원 2층", "상록원 1층", "가든쿡",
        "기숙사 식당", "두리터 카페", "노브랜드", "ING 카페", "그루터기 카페", "블루포트"
    )
    val mealTypes = listOf("조식", "중식", "석식", "간식 및 음료")

    // 이미지 선택을 위한 런처
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> mealImageUri = uri } // 선택된 이미지 URI 저장
    )

    Box( // Box로 이미지 배경 설정
        modifier = Modifier.fillMaxSize()
    ) {
        // 배경 이미지 설정
        Image(
            painter = rememberAsyncImagePainter(R.drawable.frame_2), // 배경 이미지 설정
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // 이미지가 화면을 꽉 채우도록 설정
        )

        // LazyColumn 내용
        LazyColumn( // 화면을 스크롤 가능하게 설정
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // 패딩 설정
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), // 패딩 설정
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 제목 및 뒤로가기 버튼
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(R.drawable.backicon),
                            contentDescription = "Icon",
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.TopStart)
                                .clickable { goBack() } //아이콘 클릭 시 홈 화면 이동
                        )
                        Text(
                            text = "식사 입력",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }

                    // 음식 이름 입력 필드
                    OutlinedTextField(
                        value = mealName,
                        onValueChange = { mealName = it },
                        label = { Text("음식(음료) 이름 입력") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.edit_4), // 아이콘 리소스 ID
                                contentDescription = "아이콘",
                                modifier = Modifier.size(24.dp) // 아이콘 크기 설정
                            )
                        }
                    )

                    // 사진 선택 버튼
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .background(purple, RoundedCornerShape(8.dp))  // 배경을 흰색으로 설정
                            .clickable {
                                // 클릭 시 실행할 코드
                                imagePickerLauncher.launch("image/*")  // 이미지 선택
                            }
                            .padding(16.dp)  // Box 안에 여백 추가
                    ) {
                        Text(
                            text = "사진 선택",
                            fontSize = 16.sp,
                            color = Color.White,  // 텍스트 색상 설정
                            modifier = Modifier.align(Alignment.Center)  // 텍스트 중앙 정렬
                        )
                    }


                    // 선택된 이미지 표시
                    mealImageUri?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .background(Color.Gray, RoundedCornerShape(8.dp))
                        )
                    }

                    // 반찬 입력 필드와 반찬 추가 버튼
                    Column(
                        modifier = Modifier.padding(top = 16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        sideDishes.forEachIndexed { index, sideDish ->
                            OutlinedTextField(
                                value = sideDish,
                                onValueChange = { updatedValue ->
                                    sideDishes = sideDishes.toMutableList().apply {
                                        this[index] = updatedValue
                                    }
                                },
                                label = { Text("반찬 ${index + 1} 입력") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.edit_4), // 아이콘 리소스 ID
                                        contentDescription = "아이콘",
                                        modifier = Modifier.size(24.dp) // 아이콘 크기 설정
                                    )
                                }

                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .background(purple, RoundedCornerShape(8.dp))  // 배경을 흰색으로 설정
                                .clickable {
                                    // 클릭 시 실행할 코드
                                    sideDishes = sideDishes + ""  // 새로운 빈 반찬 입력 필드 추가
                                }
                                .padding(16.dp)  // Box 안에 여백 추가
                        ) {
                            Text(
                                text = "반찬 추가",
                                fontSize = 16.sp,
                                color = Color.White,  // 텍스트 색상 설정
                                modifier = Modifier.align(Alignment.Center)  // 텍스트 중앙 정렬
                            )
                        }

                    }

                    // 장소 선택 및 식사 종류 선택
                    Column(modifier = Modifier.padding(top = 16.dp)) {
                        CustomDropdown(
                            label = "장소 선택",
                            selectedValue = selectedLocation,
                            options = locations,
                            onValueSelected = { selectedLocation = it }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomDropdown(
                            label = "식사 종류",
                            selectedValue = selectedMealType,
                            options = mealTypes,
                            onValueSelected = { selectedMealType = it }
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp)) // 식사 종류와 날짜 선택 사이에 간격 추가

                    // 날짜 선택 컴포넌트
                    DatePickerComponent(
                        selectedDate = mealDate,
                        onDateSelected = { date -> mealDate = date }
                    )

                    // 식사 비용 입력 필드
                    OutlinedTextField(
                        value = mealCost,
                        onValueChange = { mealCost = it },
                        label = { Text("식사 비용") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                    )

                    // 음식 평가 입력 필드
                    OutlinedTextField(
                        value = mealEvaluation,
                        onValueChange = { mealEvaluation = it },
                        label = { Text("음식에 대한 자신의 평가") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                    )

                    // 저장 버튼
                    Button(
                        onClick = {
                            mainViewModel.addMeal(
                                Meal(
                                    name = mealName,
                                    mealType = selectedMealType,
                                    date = mealDate,
                                    location = selectedLocation,
                                    imageUri = mealImageUri,
                                    price = mealCost,
                                    calorie = Random.nextInt(300, 1001).toString(), // 칼로리를 랜덤으로 설정
                                    evaluation = mealEvaluation ,
                                    sideDishes = sideDishes // 입력된 반찬 리스트를 추가
                                )
                            )
                            goBack() // 뒤로 가기 호출
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "저장")
                    }
                }
            }
        }
    }
}

@Composable
fun CustomDropdown(
    label: String,
    selectedValue: String,
    options: List<String>,
    onValueSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable { expanded = true }
            .padding(16.dp)
    ) {
        Column {
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = selectedValue.ifEmpty { "선택하세요" },
                fontSize = 16.sp,
                color = if (selectedValue.isNotEmpty()) Color.Black else Color.Gray
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onValueSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DatePickerComponent(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        onDateSelected("$selectedYear-${selectedMonth + 1}-$selectedDay")
                    },
                    year,
                    month,
                    day
                ).show()
            }
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "식사 날짜",
                fontSize = 12.sp, color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = selectedDate.ifEmpty { "날짜 선택" },
                fontSize = 16.sp,
                color = if (selectedDate.isNotEmpty()) Color.Black else Color.Gray
            )
        }
    }
}



