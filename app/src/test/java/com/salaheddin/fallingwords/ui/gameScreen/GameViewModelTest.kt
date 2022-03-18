package com.salaheddin.fallingwords.ui.gameScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.salaheddin.fallingwords.repositories.WordsRepository
import com.salaheddin.fallingwords.repositories.WordsRepositoryMock
import com.salaheddin.fallingwords.repositories.WordsRepositorySameMock
import com.salaheddin.fallingwords.utils.MainCoroutineRule
import com.salaheddin.fallingwords.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class GameViewModelTest{
    private lateinit var gameViewModel: GameViewModel

    private lateinit var repository: WordsRepository

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private fun setUp() {
        repository = WordsRepositorySameMock()
        gameViewModel = GameViewModel(repository)
    }

    @Test
    fun `current word is not null`() {
        setUp()
        assert(gameViewModel.currentWord.value != null)
    }

    @Test
    fun `falling word is not null`() {
        setUp()
        assert(gameViewModel.fallingWord.value != null)
    }

    @Test
    fun `score starts 0`() {
        setUp()
        assert(gameViewModel.score.value == 0)
    }

    @Test
    fun `isGameOver starts false`() {
        setUp()
        assert(gameViewModel.isGameOver.value == false)
    }

    @Test
    fun `hasWon starts false`() {
        setUp()
        assert(gameViewModel.hasWon.value == false)
    }

    @Test
    fun `restart resets everything`() {
        setUp()
        gameViewModel.restart()
        assert(gameViewModel.score.value == 0)
        assert(gameViewModel.hasWon.value == false)
        assert(gameViewModel.isGameOver.value == false)
        assert(gameViewModel.currentWord.value != null)
        assert(gameViewModel.fallingWord.value != null)
    }

    @Test
    fun `Game over ends the game`() {
        setUp()
        gameViewModel.setGameOver()
        assert(gameViewModel.hasWon.value == false)
        assert(gameViewModel.isGameOver.value == true)
    }

    @Test
    fun `submitWrongAction with wrong answers leads to game over`() = runBlocking {
        repository = WordsRepositorySameMock()
        gameViewModel = GameViewModel(repository)

        gameViewModel.currentWord.getOrAwaitValue()
        gameViewModel.fallingWord.getOrAwaitValue()
        gameViewModel.submitWrongAction()

        assert(gameViewModel.isGameOver.value == true)
    }

    @Test
    fun `submitWrongAction with correct answer resumes the game`() = runBlocking {
        repository = WordsRepositoryMock()
        gameViewModel = GameViewModel(repository)

        gameViewModel.currentWord.getOrAwaitValue()
        gameViewModel.fallingWord.getOrAwaitValue()
        gameViewModel.submitWrongAction()

        assert(gameViewModel.currentWord.value != gameViewModel.fallingWord.value)
    }

    @Test
    fun `submitCorrectAction with wrong answers leads to game over`() = runBlocking {
        repository = WordsRepositoryMock()
        gameViewModel = GameViewModel(repository)

        gameViewModel.currentWord.getOrAwaitValue()
        gameViewModel.fallingWord.getOrAwaitValue()
        gameViewModel.submitCorrectAction()

        assert(gameViewModel.isGameOver.value == true)
    }

    @Test
    fun `submitCorrectAction with correct answers give point`() = runBlocking {
        repository = WordsRepositorySameMock()
        gameViewModel = GameViewModel(repository)

        gameViewModel.currentWord.getOrAwaitValue()
        gameViewModel.fallingWord.getOrAwaitValue()
        gameViewModel.submitCorrectAction()

        assert(gameViewModel.score.value == 1)
    }
}