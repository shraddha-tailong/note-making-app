package com.example.notes_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), INotesRVAdapter {
    private  val RQ_SPEECH_REC =102

    lateinit var viewmodel: NoteViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView.layoutManager= LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)
        recyclerView.adapter = adapter

        viewmodel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewmodel::class.java)
        viewmodel.allNotes.observe(this, Observer {List ->
            List?.let {
                adapter.updateList(it)
            }

        })

    }

    override fun onItemClicked(note: Note) {
       viewmodel.deleteNote(note)
        Toast.makeText(this,"${note.text} deleted",Toast.LENGTH_LONG).show()
    }

    fun submitData(view: android.view.View) {
        val noteText = input.text.toString()
        if (noteText.isNotEmpty()){
            viewmodel.insertNote(Note(noteText))
            Toast.makeText(this,"$noteText Inserted",Toast.LENGTH_LONG).show()
        }
    }
}

