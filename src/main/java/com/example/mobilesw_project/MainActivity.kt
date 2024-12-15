package com.example.mobilesw_project

// 필요한 라이브러리 및 패키지 import
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobilesw_project.scene.home.HomeScreen
import com.example.mobilesw_project.scene.meal_detail.MealDetailScreen
import com.example.mobilesw_project.scene.meal_input.MealInputScreen
import com.example.mobilesw_project.scene.meal_list.MealListScreen
import com.example.mobilesw_project.scene.meal_record.MealRecordScreen

// MainActivity: 앱의 메인 액티비티
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 네비게이션 컨트롤러 생성
            val navController = rememberNavController()
            // ViewModel 생성
            val mainViewModel: MainViewModel = viewModel()

            // 특정 화면으로 이동하는 함수
            fun goTo(id: String) {
                navController.navigate(id)
            }

            // 네비게이션 스택에서 현재 화면을 제거하고 이전 화면으로 돌아가는 함수
            fun popBackStack() {
                navController.popBackStack()
            }

            // NavHost 설정: 네비게이션의 시작점과 각 화면 정의
            NavHost(
                navController = navController, // 네비게이션 컨트롤러 연결
                startDestination = MainNavigationId.Home // 시작 화면 설정
            ) {
                // 홈 화면
                composable(MainNavigationId.Home) {
                    HomeScreen(
                        goToMealRecord = { goTo(MainNavigationId.MealRecord) }, // 식사 기록 화면으로 이동
                        goToMealList = { goTo(MainNavigationId.MealList) }, // 식사 목록 화면으로 이동
                        goToMealInput = { goTo(MainNavigationId.MealInput) } // 식사 입력 화면으로 이동
                    )
                }

                // 식사 입력 화면
                composable(MainNavigationId.MealInput) {
                    MealInputScreen(
                        mainViewModel = mainViewModel, // ViewModel 전달
                        goBack = { popBackStack() } // 뒤로 가기 호출
                    )
                }

                // 식사 기록 화면
                composable(MainNavigationId.MealRecord) {
                    MealRecordScreen(
                        goBack = { popBackStack() }, // 뒤로 가기 호출
                        mainViewModel = mainViewModel // ViewModel 전달
                    )
                }

                // 식사 목록 화면
                composable(MainNavigationId.MealList) {
                    MealListScreen(
                        mainViewModel = mainViewModel, // ViewModel 전달
                        goBack = { popBackStack() }, // 뒤로 가기 호출
                        goToMealDetail = { mealName ->
                            goTo("${MainNavigationId.MealDetail}/$mealName") // 식사 세부 화면으로 이동
                        }
                    )
                }

                // 식사 세부 화면
                composable(
                    route = "${MainNavigationId.MealDetail}/{mealName}", // 네비게이션 경로 설정
                    arguments = listOf(
                        navArgument("mealName") { type = NavType.StringType } // 경로 매개변수 정의
                    )
                ) { backStackEntry ->
                    val mealName = backStackEntry.arguments?.getString("mealName") ?: "" // 매개변수에서 식사 이름 가져오기

                    MealDetailScreen(
                        mainViewModel = mainViewModel, // ViewModel 전달
                        mealName = mealName, // 매개변수 전달
                        goBack = { popBackStack() } // 뒤로 가기 호출
                    )
                }
            }
        }
    }
}

