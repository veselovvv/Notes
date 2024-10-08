package com.veselovvv.notes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import com.veselovvv.notes.R
import com.veselovvv.notes.data.Note
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

@AndroidEntryPoint
class NoteFragment : BaseFragment() {
    private lateinit var note: Note
    private lateinit var titleField: TextInputEditText
    private lateinit var textField: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        note = Note()
        loadNoteById()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)
        titleField = view.findViewById(R.id.note_title)
        textField = view.findViewById(R.id.note_text)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadNoteById()
        notesViewModel.noteLiveData.observe(viewLifecycleOwner) { note ->
            note?.let {
                this.note = note
                titleField.setText(note.title)
                textField.setText(note.text)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        titleField.addTextChangedListener(object : BaseTextWatcher {
            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                note.title = sequence.toString()
            }
        })
        textField.addTextChangedListener(object : BaseTextWatcher {
            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                note.text = sequence.toString()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.save_note -> {
            if (note.text.isNotEmpty()) {
                saveNote()
                showSnackBar(getString(R.string.saved))
                requireActivity().onBackPressed()
            } else
                showSnackBar(getString(R.string.text_is_empty))

            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun loadNoteById() {
        val noteId = arguments?.getSerializable(ARG_NOTE_ID) as UUID
        notesViewModel.loadNote(noteId)
    }

    fun saveNote() {
        if (note.title.isEmpty()) note.title = getString(R.string.no_title)
        note.date = SimpleDateFormat.getDateInstance().format(Date())
        if (notesViewModel.noteLiveData.value?.id != note.id) notesViewModel.addNote(note)
        notesViewModel.saveNote(note)
    }

    companion object {
        private const val ARG_NOTE_ID = "note_id"

        // Создает пакет аргументов и экземпляр фрагмента, присоединяет аргументы к фрагменту:
        fun newInstance(noteId: UUID) = NoteFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_NOTE_ID, noteId)
            }
        }
    }
}