package com.example.mobilesw_project.scene.meal_list

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

// 식사 목록 화면 컴포저블
@Composable
fun MealListScreen(
    mainViewModel: MainViewModel = viewModel(), // ViewModel 주입
    goToMealDetail: (mealName: String) -> Unit, // 상세 정보 화면으로 이동하는 콜백
    goBack: () -> Unit // 홈화면으로 뒤로가기
) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // 패딩 설정
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 상단에 아이콘과 제목을 수평 배치
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.backicon), // 아이콘 설정
                    contentDescription = "Back Icon",
                    modifier = Modifier
                        .size(32.dp) // 아이콘 크기 설정
                        .clickable { goBack() }
                )
                Spacer(modifier = Modifier.width(16.dp)) // 아이콘과 텍스트 간격 설정
                Text(
                    text = "식사 목록",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically) // 텍스트 세로 정렬
                )
            }
            // LazyColumn을 사용하여 동적으로 목록 생성
            LazyColumn {
                items(mainViewModel.meals) { meal -> // ViewModel에서 식사 목록을 가져옴
                    MealListItem(
                        mealName = meal.name,
                        date = meal.date,
                        location = meal.location,
                        imageUri = meal.imageUri ?: Uri.EMPTY, // 이미지 URI 설정
                        onClick = { mealName, imageUri, date, location ->
                            goToMealDetail(mealName) // 아이템 클릭 시 상세 화면으로 이동
                        }
                    )
                }
            }
        }
    }
}

// 식사 목록의 개별 아이템 컴포저블
@Composable
fun MealListItem(
    mealName: String, // 식사 이름
    imageUri: Uri, // 이미지 URI
    date: String, // 식사 날짜
    location: String, // 식사 장소
    onClick: (mealName: String, imageUri: Uri, date: String, location: String) -> Unit // 클릭 이벤트 콜백
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // 아이템 간 간격 설정
            .background(Color.White, RoundedCornerShape(8.dp)) // 배경과 모서리 둥글게 설정
            .padding(16.dp)
            .clickable {
                onClick(mealName, imageUri, date, location) // 클릭 이벤트 처리
            }
    ) {
        // 이미지 표시
        Box(
            modifier = Modifier
                .size(50.dp) // 이미지 크기 설정
                .background(Color.Gray, RoundedCornerShape(8.dp)) // 이미지 배경 설정
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUri), // 이미지 로드
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center) // 이미지 정렬
            )
        }
        Spacer(modifier = Modifier.width(16.dp)) // 이미지와 텍스트 간 간격
        Column {
            // 식사 이름
            Text(text = mealName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            // 날짜 정보
            Text(text = date, fontSize = 12.sp, color = Color.Gray)
            // 장소 정보
            Text(text = location, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

