package com.veselovvv.notes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import java.util.*

private const val ARG_NOTE_ID = "note_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0
private const val DATE_FORMAT = "EEE, MMM, dd"

class NoteFragment : Fragment(), DatePickerFragment.Callbacks {

    private lateinit var note: Note
    private lateinit var titleField: EditText
    private lateinit var textField: EditText
    private lateinit var dateButton: Button

    private val noteDetailViewModel: NoteDetailViewModel by lazy {
        ViewModelProviders.of(this).get(NoteDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        note = Note()
        val noteId: UUID = arguments?.getSerializable(ARG_NOTE_ID) as UUID
        noteDetailViewModel.loadNote(noteId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)

        titleField = view.findViewById(R.id.note_title) as EditText
        textField = view.findViewById(R.id.note_text) as EditText
        dateButton = view.findViewById(R.id.note_date) as Button

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteId = arguments?.getSerializable(ARG_NOTE_ID) as UUID
        noteDetailViewModel.loadNote(noteId)

        noteDetailViewModel.noteLiveData.observe(
            viewLifecycleOwner,
            Observer { note ->
                note?.let {
                    this.note = note

                    updateUI()
                }
            })
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                note.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {

            }
        }

        val textWatcher = object : TextWatcher {

            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                note.text = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {

            }
        }

        titleField.addTextChangedListener(titleWatcher)
        textField.addTextChangedListener(textWatcher)

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
        fun newInstance(noteId: UUID): NoteFragment {

            val args = Bundle().apply {
                putSerializable(ARG_NOTE_ID, noteId)
            }

            return NoteFragment().apply {
                arguments = args
            }
        }
    }
}