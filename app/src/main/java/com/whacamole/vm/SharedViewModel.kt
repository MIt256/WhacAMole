package com.whacamole.vm

import android.app.Application
import android.content.SharedPreferences
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    application: Application,
    private val sharedPreferences: SharedPreferences
) : AndroidViewModel(application) {

    var cTimer: CountDownTimer? = null
    var moleTimer: CountDownTimer? = null

    private var _currentScore = MutableLiveData<Int>(0)
    var currentScore: LiveData<Int> = _currentScore

    private var _recordScore = MutableLiveData<Int>(0)
    var recordScore: LiveData<Int> = _recordScore

    val timeLeft: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val molePosition: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        _recordScore.value = sharedPreferences.getInt("record", 0)
    }

    fun startGameTimer() {
        cTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft.value = millisUntilFinished.toInt() / 1000
            }

            override fun onFinish() {
            }
        }.start()
    }

    fun startMoleTimer() {
        moleTimer = object : CountDownTimer(30000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                molePosition.value  = (0..8).filter { it != molePosition.value }.random()
            }
            override fun onFinish() {
            }
        }.start()
    }

    fun incScore() {
        _currentScore.value = _currentScore.value?.plus(1)
    }

    fun saveRecord() {
        if (currentScore.value!! > recordScore.value!!) {
            _currentScore.value?.let { sharedPreferences.edit().putInt("record", it).apply() }
            _recordScore.value = currentScore.value
        }
    }

    fun cancelTimers() {
        cTimer?.cancel()
        moleTimer?.cancel()
        timeLeft.value = 30
        _currentScore.value = 0
        molePosition.value = -1
    }
}