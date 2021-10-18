package com.dvlpr.githubapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvlpr.githubapp.retrofit.Retrofit
import com.dvlpr.githubapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val following = MutableLiveData<ArrayList<UserModel>>()

    fun setListFollowing(username: String) {
        Retrofit.apiRequest.getUserFollowing(username).enqueue(object : Callback<ArrayList<UserModel>> {
            override fun onResponse(call: Call<ArrayList<UserModel>>, response: Response<ArrayList<UserModel>>) {
                if (response.isSuccessful) {
                    following.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun getListFollowing(): LiveData<ArrayList<UserModel>> = following
}