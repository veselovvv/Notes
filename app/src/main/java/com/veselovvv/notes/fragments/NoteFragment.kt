package com.veselovvv.notes.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.veselovvv.notes.models.Note
import com.veselovvv.notes.viewmodels.NoteDetailViewModel
import com.veselovvv.notes.R
import java.util.*

private const val ARG_NOTE_ID = "note_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0

class NoteFragment : Fragment(), DatePickerFragment.Callbacks {
    private lateinit var note: Note
    private lateinit var titleField: TextInputEditText
    private lateinit var textField: TextInputEditText
    private lateinit var dateButton: MaterialButton

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
        dateButton = view.findViewById(R.id.note_date) as MaterialButton

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
        setupDateButton()
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

    private fun setupDateButton() {
        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(note.date).apply {
                setTargetFragment(this@NoteFragment, REQUEST_DATE)
                show(this@NoteFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        noteDetailViewModel.saveNote(note)
    }

    override fun onDateSelected(date: Date) {
        note.date = date
        updateUI()
    }

    private fun updateUI() {
        titleField.setText(note.title)
        textField.setText(note.text)
        dateButton.text = note.date.toString()
    }

    companion object {
        // Создает пакет аргументов и экземпляр фрагмента, присоединяет аргументы к фрагменту:
        fun newInstance(noteId: UUID) = NoteFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_NOTE_ID, noteId)
            }
        }
    }

    interface BaseTextWatcher : TextWatcher {
        override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(sequence: Editable?) {}
    }
}