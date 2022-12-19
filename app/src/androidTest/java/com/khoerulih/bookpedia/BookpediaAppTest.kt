package com.khoerulih.bookpedia

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.khoerulih.bookpedia.model.DummyBook
import com.khoerulih.bookpedia.ui.navigation.Screen
import com.khoerulih.bookpedia.ui.theme.BookpediaTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BookpediaAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            BookpediaTheme() {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                BookpediaApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_read_list).performClick()
        navController.assertCurrentRouteName(Screen.ReadList.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithText(DummyBook.dummyBook[1].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)
        composeTestRule.onNodeWithText(DummyBook.dummyBook[1].title)
            .assertIsDisplayed()
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithText(DummyBook.dummyBook[2].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back))
            .performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_addReadListBook_working() {
        composeTestRule.onNodeWithText(DummyBook.dummyBook[0].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)
        composeTestRule.onNodeWithStringId(R.string.add_to_read_list).performClick()
        navController.assertCurrentRouteName(Screen.ReadList.route)
        composeTestRule.onNodeWithText(DummyBook.dummyBook[0].title)
            .assertIsDisplayed()
    }

    @Test
    fun navHost_removeReadListBook_working() {
        composeTestRule.onNodeWithText(DummyBook.dummyBook[3].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)

        composeTestRule.onNodeWithStringId(R.string.add_to_read_list).performClick()
        navController.assertCurrentRouteName(Screen.ReadList.route)

        composeTestRule.onNodeWithText(DummyBook.dummyBook[3].title).assertIsDisplayed()
        composeTestRule.onNodeWithText(DummyBook.dummyBook[3].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)

        composeTestRule.onNodeWithStringId(R.string.remove_from_read_list).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.remove_from_read_list).performClick()

        composeTestRule.onNodeWithText(DummyBook.dummyBook[3].title).assertDoesNotExist()

    }

}