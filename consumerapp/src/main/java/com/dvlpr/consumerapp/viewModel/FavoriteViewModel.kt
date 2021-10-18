package com.dvlpr.consumerapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dvlpr.consumerapp.database.FavoriteDatabaseContract
import com.dvlpr.consumerapp.helper.MappingHelper
import com.dvlpr.consumerapp.model.UserModel

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userList = MutableLiveData<ArrayList<UserModel>>()

    fun setFavUser(context: Context) {
        val cursor = context.contentResolver.query(
            FavoriteDatabaseContract.FavoriteUserColumns.CONTENT_URI, null, null, null,null
        )
        val convertedList = MappingHelper.mappingCursor(cursor)
        userList.postValue(convertedList)
    }

    fun getFavUser(): LiveData<ArrayList<UserModel>> {
        return userList
    }
}