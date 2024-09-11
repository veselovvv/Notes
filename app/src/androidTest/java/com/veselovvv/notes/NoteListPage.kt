package com.veselovvv.notes

class NoteListPage : AbstractPage(R.id.note_recycler_view) {
    private val recyclerViewUi = RecyclerViewUi(R.id.note_recycler_view)
    private val buttonUi = ButtonUi()

    fun checkNoteListInitialState() = recyclerViewUi.checkNoteListInitialState()

    fun clickAddButton() = buttonUi.click(buttonId = R.id.add_fab)

    fun checkNoteListState(notes: List<Pair<String, String>>) =
        recyclerViewUi.checkNoteListState(notes = notes)
}
