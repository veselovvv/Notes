package com.veselovvv.notes.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.veselovvv.notes.R
import com.veselovvv.notes.data.Note
import java.util.*

class MainActivity : AppCompatActivity(), NoteListFragment.Callbacks {
    lateinit var addFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFab = findViewById(R.id.add_fab)
        addFab.setOnClickListener {
            onNoteSelected(Note().id)
        }

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = NoteListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }

    override fun onNoteSelected(noteId: UUID) {
        val fragment = NoteFragment.newInstance(noteId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}

fun showSnackBar(view: View, text: String, fab: FloatingActionButton) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).apply {
        anchorView = fab
        show()
    }
}