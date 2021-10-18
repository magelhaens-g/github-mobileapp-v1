package com.dvlpr.githubapp.retrofit

import com.dvlpr.githubapp.model.UserData
import com.dvlpr.githubapp.model.UserDetail
import com.dvlpr.githubapp.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitEndPoint {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUserSearch(@Query("q") query: String): Call<UserData>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>
}