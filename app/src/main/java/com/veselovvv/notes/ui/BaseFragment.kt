package com.veselovvv.notes.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.veselovvv.notes.R

abstract class BaseFragment : Fragment() {
    val notesViewModel: NotesViewModel by lazy {
        ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }

    fun showSnackBar(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).apply {
            anchorView = requireActivity().findViewById(R.id.add_fab)
            show()
        }
    }
}