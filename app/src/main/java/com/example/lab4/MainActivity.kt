package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab4.ui.theme.Lab4Theme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab4.ui.Category
import com.example.lab4.ui.MyViewModel
import com.example.lab4.ui.Recommendation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4Theme {
                MyApp()
            }
        }
    }
}

//region Greeting

//@Composable
//fun Greeting(myViewModel: MyViewModel = viewModel(), modifier: Modifier = Modifier) {
//    val myUiState by myViewModel.uiState.collectAsState()
//
//    Column {
//        Text(
//            text = "Hello " + myUiState.guess + "!",
//            modifier = modifier
//        )
//        OutlinedTextField(
//            value = myUiState.guess,
//            onValueChange = { myViewModel.SetValue(it) },
//            modifier = modifier,
//            label = { Text("Введите ваше имя")}
//        )
//    }
//}

//endregion

enum class MyScreen() {
    MyCity,
    Category,
    Recommendation
}

@Composable
fun MyApp(
    myViewModel: MyViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    myViewModel.GetUiStateData()

    val uiState by myViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = MyScreen.MyCity.name
    ) {
        composable(route = MyScreen.MyCity.name) {
            MyCityScreen(
                categoryList = uiState.categoryList,
                onButtonCategoryClicked = {
                    myViewModel.SetCurrentCategory(it)
                    navController.navigate(MyScreen.Category.name)
                },
                modifier = modifier
            )
        }
        composable(route = MyScreen.Category.name) {
            uiState.currentCategory?.scrollableListScreen(
                onButtonRecommendationClicked = {
                    myViewModel.SetCurrentRecommendation(it)
                    navController.navigate(MyScreen.Recommendation.name)
                },
                onGoBackButtonClicked = { navController.navigate(MyScreen.MyCity.name)},
                modifier = modifier
            )
        }
        composable(route = MyScreen.Recommendation.name) {
            uiState.currentRecommendation?.fullDescription(
                onGoBackButtonClicked = { navController.navigate(MyScreen.Category.name)},
                modifier = modifier
            )
        }
    }
}

fun MyViewModel.GetUiStateData() {
    val list: List<Category> = listOf(
        Category(name = "Рестораны", R.drawable.kfc, listOf(
            Recommendation(name = "KFC", imageId =  R.drawable.kfc, description = "Раньше это было KFC"),
            Recommendation(name = "Burger King", imageId = R.drawable.king, description = "Burger King - г*вно"),
            Recommendation(name = "McDonald's", imageId = R.drawable.point, description = "Вкусно и точка")
        )),
        Category(name = "Торговые центры", R.drawable.red, listOf(
            Recommendation(name = "Алые паруса", imageId =  R.drawable.red, description = "С корабликом"),
            Recommendation(name = "Талисман", imageId = R.drawable.talisman, description = "У меня фантазия закончилась"),
            Recommendation(name = "Флагман", imageId = R.drawable.flagman, description = "А может её и не было вообще")
        )),
        Category(name = "Парки", R.drawable.kirova, listOf(
            Recommendation(name = "Парк Кирова", imageId =  R.drawable.kirova, description = "Большой, потому что в лес переходит"),
            Recommendation(name = "Парк Горького", imageId = R.drawable.gorkogo, description = "Видел колесо, может был внутри, не помню"),
            Recommendation(name = "Козий парк", imageId = R.drawable.kozi, description = "Совсем маленький, зато близко к моему дому")
        )),
        Category(name = "Какое-то очень очень очень очень очень длинное название")
    )

    SetCategoryList(list)
}

@Composable
fun MyCityScreen(
    categoryList: List<Category>,
    onButtonCategoryClicked: (Category) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AppBar("My City")
        LazyColumn {
            items(categoryList) { item ->
                TextButton(
                    onClick = { onButtonCategoryClicked(item) }
                ) {
                    item.briefDescription()
                }
            }
        }
    }
}

@Composable
fun AppBar(text: String,
           isHaveButton: Boolean = false,
           buttonFunction: () -> Unit = {},
           modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(3.dp, Color.Black)
            .height(64.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            if (isHaveButton) {
                IconButton(
                    onClick = buttonFunction,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back_32dp_1f1f1f_fill0_wght400_grad0_opsz40),
                        contentDescription = "Arrow Back"
                    )
                }
            }

            Spacer(modifier.weight(1f))

            val fontSizeRange: Pair<Int, Int> = Pair<Int, Int>(4, 32)
            var fontSizeValue by remember { mutableStateOf(fontSizeRange.second) }
            var fontSizeValueStep by remember { mutableStateOf(7) }
            var readyToDraw by remember { mutableStateOf(false) }

            Text(
                text = text,
                fontSize = fontSizeValue.sp,
                textAlign = TextAlign.Right,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = {
                    if (it.didOverflowHeight && !readyToDraw) {
                        val nextFontSizeValue = fontSizeValue - fontSizeValueStep
                        if (nextFontSizeValue <= fontSizeRange.first) {
                            fontSizeValue = fontSizeRange.first
                            readyToDraw = true
                        } else {
                            fontSizeValue = nextFontSizeValue
                        }
                    } else {
                        readyToDraw = true
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp).drawWithContent { if (readyToDraw) drawContent() }
            )
        }
    }
}

//region Previews

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Lab4Theme {
//        Greeting()
//    }
//}

@Preview(showBackground = true)
@Composable
fun MyCityScreenPreview() {
    Lab4Theme {
        val categoryList : List<Category> = listOf(
            Category("Category 1"),
            Category("Category 2"),
            Category("Category 3"),
            Category("Category 4"),
            Category("Category 5"),
        )

        MyCityScreen(categoryList = categoryList)
    }
}

@Preview(showBackground = true)
@Composable
fun RecomendationListPreview() {
    Lab4Theme {
        val category = Category("test category")
        category.list = listOf(
            Recommendation("Recomendation 1"),
            Recommendation("Recomendation 2"),
            Recommendation("Recomendation 3"),
            Recommendation("Recomendation 4"),
            Recommendation("Recomendation 5")
        )
        category.scrollableListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendationDescriptionPreview() {
    Lab4Theme {
        val recommendation = Recommendation(
            name = "test recommendation",
            description = stringResource(R.string.test_description)
        )

        recommendation.fullDescription()
    }
}

//endregion
