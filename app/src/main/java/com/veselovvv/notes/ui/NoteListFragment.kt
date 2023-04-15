package com.veselovvv.notes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.veselovvv.notes.R
import com.veselovvv.notes.data.Note
import java.util.*

open class NoteListFragment : BaseFragment() {
    private lateinit var noteRecyclerView: RecyclerView
    private var callbacks: Callbacks? = null
    private var adapter: NoteAdapter? = NoteAdapter(emptyList())
    private val notesViewModel: NotesViewModel by lazy {
        ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }

    override fun onAttach(context: Context) { // прикрепление фрагмента к активности
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)
        noteRecyclerView = view.findViewById(R.id.note_recycler_view)
        noteRecyclerView.layoutManager = LinearLayoutManager(context)
        noteRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Регистрация наблюдателя за экземпляром LiveData и связывание наблюдателя с фрагментом:
        notesViewModel.noteListLiveData.observe(viewLifecycleOwner) { notes ->
            notes?.let { updateUI(notes) }
        }
    }

    override fun onDetach() { // открепление фрагмента от активности
        super.onDetach()
        callbacks = null
    }

    fun updateUI(notes: List<Note>) {
        adapter = NoteAdapter(notes)
        noteRecyclerView.adapter = adapter
        noteRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private inner class NoteHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var note: Note

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(note: Note) {
            this.note = note
            itemView.findViewById<MaterialTextView>(R.id.note_title).text = this.note.title
            itemView.findViewById<MaterialTextView>(R.id.note_text).text = this.note.text
            itemView.findViewById<MaterialTextView>(R.id.note_date).text = this.note.date
            itemView.findViewById<ShapeableImageView>(R.id.delete_note).setOnClickListener {
                notesViewModel.deleteNote(note)
                showSnackBar(getString(R.string.deleted))
            }
            itemView.findViewById<ShapeableImageView>(R.id.make_favorite_note).setOnClickListener {
                note.isFavorite = !note.isFavorite
                notesViewModel.saveNote(note)
                if (note.isFavorite) showSnackBar(getString(R.string.added_to_favorites))
                else showSnackBar(getString(R.string.removed_from_favorites))
            }
        }

        override fun onClick(view: View?) {
            callbacks?.onNoteSelected(note.id)
        }
    }

    private inner class NoteAdapter(private var notes: List<Note>) : RecyclerView.Adapter<NoteHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            NoteHolder(layoutInflater.inflate(R.layout.list_item_note, parent, false))

        override fun onBindViewHolder(holder: NoteHolder, position: Int) = holder.bind(notes[position])
        override fun getItemCount() = notes.size
    }

    companion object {
        fun newInstance() = NoteListFragment()
    }

    interface Callbacks {
        fun onNoteSelected(noteId: UUID)
    }
}