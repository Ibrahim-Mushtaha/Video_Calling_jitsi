package com.ix.ibrahim7.videocall.ui.viewmodel.auth

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {


    val liveData: LiveData<SplashState>
        get() = mutableLiveData
    private val mutableLiveData = MutableLiveData<SplashState>()

    init {
        GlobalScope.launch {
            delay(2000)
            mutableLiveData.postValue(SplashState.MainActivity)
        }
    }


}

sealed class SplashState {
    object MainActivity : SplashState()
}
