package com.veselovvv.notes

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import java.util.*

class NoteListFragment : Fragment() {

    // Интерфейс обратного вызова:
    interface Callbacks {
        fun onNoteSelected(noteId: UUID)
    }

    private var callbacks: Callbacks? = null
    private lateinit var noteRecyclerView: RecyclerView
    private var adapter: NoteAdapter? = NoteAdapter(emptyList())

    private val noteListViewModel: NoteListViewModel by lazy {
        ViewModelProviders.of(this).get(NoteListViewModel::class.java)
    }

    // Прикрепление фрагмента к активности:
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Сообщение FragmentManager, что экземпляр NoteListFragment должен получать обратные вызовы меню:
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        noteRecyclerView = view.findViewById(R.id.note_recycler_view) as RecyclerView
        noteRecyclerView.layoutManager = LinearLayoutManager(context)
        noteRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Регистрация наблюдателя за экземпляром LiveData и связывание наблюдателя с фрагментом:
        noteListViewModel.noteListLiveData.observe(
            viewLifecycleOwner,
            Observer { notes -> notes?.let { updateUI(notes) } }
        )
    }

    // Открепление фрагмента от активности:
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    // Заполняет меню, определенное в файле fragment_note_list.xml:
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_note_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_note -> {
                val note = Note()
                noteListViewModel.addNote(note)
                callbacks?.onNoteSelected(note.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // Настраивает интерфейс NoteListFragment:
    private fun updateUI(notes: List<Note>) {
        adapter = NoteAdapter(notes)
        noteRecyclerView.adapter = adapter
    }

    // Хранит ссылки на виджеты внутри представления элемента:
    private inner class NoteHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var note: Note

        private val titleTextView: MaterialTextView = itemView.findViewById(R.id.note_title)
        private val textTextView: MaterialTextView = itemView.findViewById(R.id.note_text)
        private val dateTextView: MaterialTextView = itemView.findViewById(R.id.note_date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(note: Note) {
            this.note = note
            titleTextView.text = this.note.title
            textTextView.text = this.note.text
            dateTextView.text = this.note.date.toString()
        }

        override fun onClick(view: View?) {
            callbacks?.onNoteSelected(note.id)
        }
    }

    // Адаптер:
    private inner class NoteAdapter(var notes: List<Note>)
        : RecyclerView.Adapter<NoteHolder>() {

        // Заполняет представление элемента, создает новый контейнер, передает его обратно:
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val view = layoutInflater.inflate(R.layout.list_item_note, parent, false)
            return NoteHolder(view)
        }

        // Берет заметку с определенным индексом и задает соответствующий текст:
        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val note = notes[position]
            holder.bind(note)
        }

        // Количество элементов в списке заметок:
        override fun getItemCount() = notes.size
    }

    // Создает объект NoteListFragment:
    companion object {
        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }
}