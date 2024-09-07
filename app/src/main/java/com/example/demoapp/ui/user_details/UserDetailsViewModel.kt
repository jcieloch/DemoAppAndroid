package com.example.demoapp.ui.user_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.demoapp.data.repository.UserRepository
import com.example.demoapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {
    private val _user: LiveData<User?> = userRepository.getUser().asLiveData()
    val user: LiveData<User?> = _user
}
