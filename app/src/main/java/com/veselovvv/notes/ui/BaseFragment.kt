package com.veselovvv.notes.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.veselovvv.notes.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    val notesViewModel: NotesViewModel by viewModels()

    fun showSnackBar(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).apply {
            anchorView = requireActivity().findViewById(R.id.add_fab)
            show()
        }
    }
}