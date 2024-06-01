package com.example.roomdatabase_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.roomdatabase_app.roomDB.Note
import com.example.roomdatabase_app.roomDB.NoteDatabase
import com.example.roomdatabase_app.ui.theme.RoomDatabase_AppTheme
import com.example.roomdatabase_app.viewModel.NoteViewmodel
import com.example.roomdatabase_app.viewModel.Repository

class MainActivity : ComponentActivity() {


    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            name = "note.db"
        ).build()
    }

    val viewModel by viewModels<NoteViewmodel>(factoryProducer = {
            object :ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewmodel(Repository(db)) as T
                }
            }
        }
    )


    @OptIn(ExperimentalFoundationApi::class) //for combined clickable in LazyColumn's Column
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDatabase_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                    var noteName by remember { mutableStateOf("") }
                    var noteBody by remember { mutableStateOf("") }

                    val note = Note(
                        noteName,
                        noteBody
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(40.dp)
                    ) {
                        TextField(value = noteName, onValueChange = {noteName = it}, placeholder = {Text(text = "Enter note title",fontFamily = FontFamily.Serif)})
                        TextField(value = noteBody, onValueChange = {noteBody = it}, placeholder = {Text(text = "Enter note body",fontFamily = FontFamily.Serif)})

                        Button(onClick = { viewModel.upsertNote(note)  }) {
                            Text(text = "Insert Data",fontFamily = FontFamily.Serif)
                        }

                        var notelist by remember { mutableStateOf(listOf<Note>()) }
                        viewModel.getAllNotes().observe(this@MainActivity){
                            notelist = it
                        }

                        Spacer(modifier = Modifier.padding(10.dp))

                        LazyColumn() {
                            items(notelist){note->
                                Column() {

                                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                                        Text(text = "${note.noteName}", fontSize = 20.sp, fontFamily = FontFamily.Serif, modifier = Modifier.weight(10f), textAlign = TextAlign.Center)
                                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "deleteNote", modifier = Modifier
                                            .weight(1f)
                                            .clickable { viewModel.deleteNote(note) })
                                    }

                                    Spacer(modifier = Modifier.padding(10.dp))
                                    Text(text = "${note.noteBody}", fontSize = 20.sp, fontFamily = FontFamily.Serif)
                                    Divider(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(6.dp))
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoomDatabase_AppTheme {

    }
}