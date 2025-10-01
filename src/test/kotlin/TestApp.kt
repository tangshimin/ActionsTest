import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import java.io.PrintStream
import java.nio.charset.StandardCharsets

class TestApp {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testApp(){
        // 设置测试环境
        composeTestRule.setContent {
            App()
        }
        
        // 强制使用 UTF-8 编码输出中文
        val originalOut = System.out
        try {
            System.setOut(PrintStream(System.out, true, StandardCharsets.UTF_8.name()))
            println("测试中文字符显示")
        } finally {
            System.setOut(originalOut)
        }

        composeTestRule.onNode(hasText("Hello, Github"))
            .assertExists()
            .performClick()

        composeTestRule.onNode(hasText("Hello, Actions!"))
            .assertExists()
    }
}