package com.whacamole.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
}