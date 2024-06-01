package com.example.roomdatabase_app.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val noteName:String,
    val noteBody:String,
    @PrimaryKey(autoGenerate = true)
    val noteID:Int = 0
)
