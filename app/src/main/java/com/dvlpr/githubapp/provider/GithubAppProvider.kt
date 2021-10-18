package com.dvlpr.githubapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dvlpr.githubapp.data.FavoriteUserDao
import com.dvlpr.githubapp.data.FavoriteUserDatabase

class GithubAppProvider : ContentProvider() {
    private lateinit var favUserDao: FavoriteUserDao

    companion object {
        const val AUTHORITY = "com.dvlpr.githubapp"
        const val TABLE_NAME = "favorite_user"
        const val FAVORITE_USER_ID = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE_USER_ID)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        favUserDao = context?.let { context ->
            FavoriteUserDatabase.getDatabase(context)?.favUserDao()
        }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cursor: Cursor?
        when (uriMatcher.match(uri)) {
            FAVORITE_USER_ID -> {
                cursor = favUserDao.findFavoriteUser()
                if (context != null) {
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            } else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}