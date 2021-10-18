package com.dvlpr.githubapp.model

data class UserDetail(
    val login: String?,
    val id: Int,
    val avatar_url: String?,
    val name: String?,
    val public_repos: Int,
    val followers: Int,
    val following: Int,
    val company: String?,
    val location: String?,
    val html_url: String?
)