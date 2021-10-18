package com.dvlpr.githubapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dvlpr.githubapp.data.FavoriteUser
import com.dvlpr.githubapp.data.FavoriteUserDao
import com.dvlpr.githubapp.data.FavoriteUserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteUserDatabase? = FavoriteUserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favUserDao()
    }

    fun getFavUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}