package com.veselovvv.notes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.veselovvv.notes.R
import com.veselovvv.notes.data.Note
import java.util.*

class MainActivity : AppCompatActivity(), NoteListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, NoteListFragment.newInstance())
                .commit()
        }

        findViewById<ShapeableImageView>(R.id.home).setOnClickListener {
            navigate(NoteListFragment.newInstance())
        }

        findViewById<BottomAppBar>(R.id.bottom_app_bar).setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorites -> {
                    navigate(FavoriteNoteListFragment.newInstance())
                    true
                }
                else -> false
            }
        }

        findViewById<FloatingActionButton>(R.id.add_fab).setOnClickListener {
            onNoteSelected(Note().id)
        }
    }

    override fun onNoteSelected(noteId: UUID) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NoteFragment.newInstance(noteId))
            .addToBackStack(null)
            .commit()
    }

    fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}