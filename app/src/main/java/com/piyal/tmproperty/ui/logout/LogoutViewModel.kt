package com.piyal.tmproperty.ui.logout

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(private val context: Context) : ViewModel() {

    private val _logoutStatus = MutableLiveData<UiState<Unit>>()
    val logoutStatus: LiveData<UiState<Unit>> get() = _logoutStatus

    fun logout() {
        viewModelScope.launch {
            _logoutStatus.value = UiState.Loading
            try {
                // Perform logout action here
                FirebaseAuth.getInstance().signOut()

                // Clear shared preferences
                val sharedPreferences = context.getSharedPreferences("login_status", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", false)
                editor.apply()

                _logoutStatus.value = UiState.Success(Unit)
            } catch (e: Exception) {
                _logoutStatus.value = UiState.Failure(e.message)
            }
        }
    }
}

