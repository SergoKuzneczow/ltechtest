package com.sergokuzneczow.database.impl.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sergokuzneczow.database.impl.room.entity.AuthenticateRequestLocalModel

@Dao
internal interface AuthenticateRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(column: AuthenticateRequestLocalModel)

    @Query("select * from authenticate_requests where phone_mask=:phoneMask order by authenticate_requests_key desc LIMIT 1")
    fun queryLastAuthenticateRequestColumn(phoneMask: String): AuthenticateRequestLocalModel?
}