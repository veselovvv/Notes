package com.veselovvv.notes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.veselovvv.notes.R
import com.veselovvv.notes.fragments.NoteFragment
import com.veselovvv.notes.fragments.NoteListFragment
import java.util.*

class MainActivity : AppCompatActivity(), NoteListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = NoteListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }

    override fun onNoteSelected(noteId: UUID) {
        val fragment = NoteFragment.newInstance(noteId)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}