package com.veselovvv.notes

class FavoriteNoteListPage : AbstractPage(R.id.note_recycler_view) {
    private val recyclerViewUi = RecyclerViewUi(R.id.note_recycler_view)
    private val bottomNavigationUi = BottomNavigationUi()

    fun checkFavoriteNoteListInitialState() = recyclerViewUi.checkNoteListInitialState()

    fun checkFavoriteNoteListState(notes: List<Pair<String, String>>) =
        recyclerViewUi.checkNoteListState(notes = notes)

    fun clickOnMakeFavoriteNoteButton(index: Int) =
        recyclerViewUi.clickOnMakeFavoriteNoteButton(index = index)

    fun clickOnHomeTab() = bottomNavigationUi.clickOnHomeTab()
}
