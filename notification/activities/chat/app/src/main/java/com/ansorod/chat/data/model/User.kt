package com.ansorod.chat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey @ColumnInfo(name = "user_id") val id: Int = 1,
    @ColumnInfo(name = "id") val name: String
    )