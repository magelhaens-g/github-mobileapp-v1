package com.dvlpr.consumerapp.database

import android.net.Uri
import android.provider.BaseColumns

object FavoriteDatabaseContract {
    const val AUTHORITY = "com.dvlpr.githubapp"
    const val CONTENT = "content"

    internal class FavoriteUserColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val ID = "id"
            const val USERNAME = "login"
            const val PHOTO = "avatar_url"
            const val URL = "html_url"

            val CONTENT_URI = Uri.Builder().scheme(CONTENT)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}