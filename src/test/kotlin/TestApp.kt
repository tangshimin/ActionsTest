import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class TestApp {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun TestApp(){
        // 设置测试环境
        composeTestRule.setContent {
            App()
        }
        composeTestRule.onNode(hasText("Hello, Github"))
            .assertExists()
            .performClick()

        composeTestRule.onNode(hasText("Hello, Actions!"))
            .assertExists()
    }
}