package com.dvlpr.githubapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvlpr.githubapp.retrofit.Retrofit
import com.dvlpr.githubapp.model.UserData
import com.dvlpr.githubapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<UserModel>>()

    fun setUserSearch(query: String) {
        Retrofit.apiRequest.getUserSearch(query).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun getSearchUser(): LiveData<ArrayList<UserModel>> = listUser
}