package com.example.roomdatabase_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase_app.roomDB.Note
import kotlinx.coroutines.launch

class NoteViewmodel(private val repository: Repository):ViewModel() {

    fun getAllNotes() = repository.getAllNotes().asLiveData()

    fun upsertNote(note: Note){
        viewModelScope.launch {
            repository.upsertNote(note)
        }
    }

    fun deleteNote(note:Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}