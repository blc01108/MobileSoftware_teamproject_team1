package com.example.mobilesw_project.scene.meal_record

// 필요한 라이브러리 및 패키지 import
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
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

// 식사 기록 화면 컴포저블
@Composable
fun MealRecordScreen(
    goBack: () -> Unit, // 뒤로 가기 콜백
    mainViewModel: MainViewModel = viewModel() // ViewModel 주입
) {
    // ViewModel을 통해 한 달 동안의 칼로리와 비용 데이터 가져오기
    val totalCalories = mainViewModel.getTotalCalories()
    val totalBreakfastCost = mainViewModel.getTotalBreakfastCost()
    val totalLunchCost = mainViewModel.getTotalLunchCost()
    val totalDinnerCost = mainViewModel.getTotalDinnerCost()
    val totalSnackCost = mainViewModel.getTotalSnackCost()

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
                .padding(16.dp), // 화면 여백 설정
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.backicon), // 아이콘 이미지 설정
                    contentDescription = "Back Icon",
                    modifier = Modifier
                        .padding(end = 8.dp) // 텍스트와 간격 추가
                        .size(24.dp) // 아이콘 크기
                        .clickable { goBack() } // 클릭 시 뒤로 가기
                )
                Text(
                    text = "식사 기록(월)", // 제목 텍스트
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 각 항목마다 박스 배경을 흰색으로 설정
            ItemBox("한 달 동안의 칼로리 총량", totalCalories.toString())
            ItemBox("한 달 동안의 조식 비용", totalBreakfastCost.toString())
            ItemBox("한 달 동안의 중식 비용", totalLunchCost.toString())
            ItemBox("한 달 동안의 석식 비용", totalDinnerCost.toString())
            ItemBox("한 달 동안의 간식 및 음료 비용", totalSnackCost.toString())
        }
    }
}

// 개별 항목을 위한 흰색 배경의 박스 Composable (밑줄 포함)
@Composable
fun ItemBox(label: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // 항목 간 여백
            .background(Color.White) // 각 아이템의 흰색 배경
            .padding(16.dp) // 내부 여백
    ) {
        Column {
            // 라벨 출력
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Gray
            )
            // 값 출력
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp) // 라벨과 값 사이 간격
            )
            // 밑줄
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp) // 값과 밑줄 사이 간격
                    .height(2.dp) // 밑줄 두께
                    .background(Color.LightGray) // 밑줄 색상
            )
        }
    }
}