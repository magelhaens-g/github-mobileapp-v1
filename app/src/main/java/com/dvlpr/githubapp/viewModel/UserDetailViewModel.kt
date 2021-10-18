package com.dvlpr.githubapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dvlpr.githubapp.data.FavoriteUser
import com.dvlpr.githubapp.data.FavoriteUserDao
import com.dvlpr.githubapp.data.FavoriteUserDatabase
import com.dvlpr.githubapp.retrofit.Retrofit
import com.dvlpr.githubapp.model.UserDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {
    val userDetail = MutableLiveData<UserDetail>()
    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteUserDatabase? = FavoriteUserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favUserDao()
    }

    fun setUserDetail(username: String) {
        Retrofit.apiRequest.getUserDetail(username).enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                if (response.isSuccessful) {
                    userDetail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun getUserDetail(): LiveData<UserDetail> = userDetail

    fun addToFav(username: String?, id: Int, avatarUrl: String?, htmlUrl: String?){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id,
                avatarUrl,
                htmlUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFav(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}