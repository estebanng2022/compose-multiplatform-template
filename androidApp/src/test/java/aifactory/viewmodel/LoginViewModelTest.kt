package aifactory.viewmodel

import aifactory.data.AiRepository
import aifactory.ui.screens.login.LoginViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: AiRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        // Placeholder for ViewModel creation with mock repo
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `valid submission leads to success`() = runTest {
        // Given the repo will return true for a valid login
        // When login is called with valid credentials
        // Then the state should transition to Loading and then Content
    }

    @Test
    fun `invalid submission leads to error`() = runTest {
        // Given the repo will return false for an invalid login
        // When login is called with invalid credentials
        // Then the state should transition to Loading and then Error
    }
}
