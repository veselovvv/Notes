package com.veselovvv.notes.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders

class FavoriteNoteListFragment : NoteListFragment() {
    private val notesViewModel: NotesViewModel by lazy {
        ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel.favoriteNoteListLiveData.observe(
            viewLifecycleOwner, { notes -> notes?.let { updateUI(notes) } }
        )
    }

    companion object {
        fun newInstance() = FavoriteNoteListFragment()
    }
}