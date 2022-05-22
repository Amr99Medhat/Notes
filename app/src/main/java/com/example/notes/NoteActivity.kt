package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.extras?.getString("title")
        val note = intent.extras?.getString("note")
        val date = intent.extras?.getString("date")


        binding.activityNoteTvTitle.text = title
        binding.activityNoteTvNote.text = note
        binding.activityNoteTvDate.text = date

    }
}