package com.example.roomdatabase_app.viewModel

import com.example.roomdatabase_app.roomDB.Note
import com.example.roomdatabase_app.roomDB.NoteDatabase

class Repository(private val db : NoteDatabase) {

    suspend fun upsertNote(note: Note){
        db.dao.upsert(note)
    }

    suspend fun deleteNote(note:Note){
        db.dao.delete(note)
    }

    fun getAllNotes() = db.dao.getAllNotes()
}