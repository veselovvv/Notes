package com.veselovvv.notes.ui

import android.os.Bundle
import android.view.View

class FavoriteNoteListFragment : NoteListFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel.favoriteNoteListLiveData.observe(viewLifecycleOwner) { notes ->
            notes?.let { updateUI(notes) }
        }
    }

    companion object {
        fun newInstance() = FavoriteNoteListFragment()
    }
}