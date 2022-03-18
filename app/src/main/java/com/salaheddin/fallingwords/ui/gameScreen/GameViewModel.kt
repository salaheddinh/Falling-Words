package com.salaheddin.fallingwords.ui.gameScreen

import androidx.lifecycle.*
import com.salaheddin.fallingwords.models.Word
import com.salaheddin.fallingwords.repositories.WordsRepository
import kotlinx.coroutines.launch

class GameViewModel(private val repository: WordsRepository) : ViewModel() {

    //to reduce the possible answers to 5 only
    private val currentPossibleAnswers = ArrayList<Word>()
    private val allAnswers = ArrayList<Word>()

    private val _currentWord: MutableLiveData<Word> = MutableLiveData()
    val currentWord: LiveData<Word> = _currentWord

    private val _fallingWord: MutableLiveData<Word> = MutableLiveData()
    val fallingWord: LiveData<Word> = _fallingWord

    private val _score: MutableLiveData<Int> = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _isGameOver: MutableLiveData<Boolean> = MutableLiveData(false)
    val isGameOver: LiveData<Boolean> = _isGameOver

    private val _hasWon: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasWon: LiveData<Boolean> = _hasWon

    init {
        getCurrentWord()
        getFallingWord()
    }

    private fun getCurrentWord() = viewModelScope.launch {
        if (allAnswers.isEmpty()) {
            allAnswers.addAll(repository.getWords())
        }
        if (allAnswers.size > 5) {
            currentPossibleAnswers.clear()
            currentPossibleAnswers.addAll(allAnswers.shuffled().take(5))
            val randomWord = currentPossibleAnswers.random()
            allAnswers.remove(randomWord)
            _currentWord.postValue(randomWord)
        } else {
            _hasWon.postValue(true)
        }
    }

    private fun getFallingWord() = viewModelScope.launch {
        if (currentPossibleAnswers.isNotEmpty()) {
            val randomWord = currentPossibleAnswers.random()
            currentPossibleAnswers.remove(randomWord)
            _fallingWord.postValue(randomWord)
        } else {
            setGameOver()
        }
    }

    fun submitWrongAction() {
        if (currentWord.value?.spa == fallingWord.value?.spa) {
            setGameOver()
        }
        getFallingWord()
    }

    fun submitCorrectAction() {
        if (currentWord.value?.spa == fallingWord.value?.spa) {
            val currentScore = _score.value
            currentScore?.let {
                _score.postValue(it + 1)
                getCurrentWord()
                getFallingWord()
            }
        } else {
            setGameOver()
        }
    }

    fun restart() {
        allAnswers.clear()
        currentPossibleAnswers.clear()
        getCurrentWord()
        getFallingWord()
        _score.postValue(0)
        _isGameOver.postValue(false)
        _hasWon.postValue(false)
    }

    fun setGameOver() {
        _hasWon.value?.let {
            if (!it) {
                _isGameOver.postValue(true)
            }
        } ?: _isGameOver.postValue(true)
    }
}