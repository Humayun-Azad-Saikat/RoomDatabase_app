package com.example.roomdatabase_app.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Upsert
    suspend fun upsert(note:Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM Note")
    fun getAllNotes():Flow<List<Note>>
}