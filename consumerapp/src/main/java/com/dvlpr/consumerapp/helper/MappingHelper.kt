package com.dvlpr.consumerapp.helper

import android.database.Cursor
import com.dvlpr.consumerapp.database.FavoriteDatabaseContract
import com.dvlpr.consumerapp.model.UserModel

object MappingHelper {
    fun mappingCursor(cursor: Cursor?): ArrayList<UserModel> {
        val user = ArrayList<UserModel>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(FavoriteDatabaseContract.FavoriteUserColumns.ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDatabaseContract.FavoriteUserColumns.USERNAME))
                val photo = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDatabaseContract.FavoriteUserColumns.PHOTO))
                val url = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDatabaseContract.FavoriteUserColumns.URL))
                user.add(
                    UserModel(
                        name,
                        id,
                        photo,
                        url
                    )
                )
            }
        }
        return user
    }
}