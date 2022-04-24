package com.veselovvv.notes.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.veselovvv.notes.R
import com.veselovvv.notes.data.Note
import java.util.*

private const val ARG_NOTE_ID = "note_id"

class NoteFragment : Fragment() {
    private lateinit var note: Note
    private lateinit var titleField: TextInputEditText
    private lateinit var textField: TextInputEditText

    private val noteDetailViewModel: NoteDetailViewModel by lazy {
        ViewModelProviders.of(this).get(NoteDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note = Note()
        loadNoteById()
    }

    private fun loadNoteById() {
        val noteId: UUID = arguments?.getSerializable(ARG_NOTE_ID) as UUID
        noteDetailViewModel.loadNote(noteId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)
        titleField = view.findViewById(R.id.note_title) as TextInputEditText
        textField = view.findViewById(R.id.note_text) as TextInputEditText
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadNoteById()

        noteDetailViewModel.noteLiveData.observe(viewLifecycleOwner, Observer { note ->
            note?.let {
                this.note = note
                updateUI()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        addListenersForTextFields()
    }

    private fun addListenersForTextFields() {
        val titleWatcher = object : BaseTextWatcher {
            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                note.title = sequence.toString()
            }
        }

        val textWatcher = object : BaseTextWatcher {
            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                note.text = sequence.toString()
            }
        }

        titleField.addTextChangedListener(titleWatcher)
        textField.addTextChangedListener(textWatcher)
    }

    override fun onStop() {
        super.onStop()
        noteDetailViewModel.saveNote(note)
    }

    private fun updateUI() {
        titleField.setText(note.title)
        textField.setText(note.text)
    }

    companion object {
        // Создает пакет аргументов и экземпляр фрагмента, присоединяет аргументы к фрагменту:
        fun newInstance(noteId: UUID) = NoteFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_NOTE_ID, noteId)
            }
        }
    }
}