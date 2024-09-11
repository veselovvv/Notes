package com.veselovvv.notes

class NotePage : AbstractPage(R.id.note_root_layout) {
    private val textFieldUi = TextFieldUi()
    private val buttonUi = ButtonUi()

    fun checkNoteInitialState() {
        with(textFieldUi) {
            checkIsDisplayed(textFieldId = R.id.note_title)
            checkIsDisplayed(textFieldId = R.id.note_text)
            checkTextMatches(textFieldId = R.id.note_title, text = "")
            checkTextMatches(textFieldId = R.id.note_text, text = "")
        }
    }

    fun clickSaveNoteButton() = buttonUi.click(buttonId = R.id.save_note)

    fun typeInTitleTextField(text: String) =
        textFieldUi.typeIn(textFieldId = R.id.note_title, text = text)

    fun typeInNoteTextField(text: String) =
        textFieldUi.typeIn(textFieldId = R.id.note_text, text = text)

    fun checkTitleState(text: String) =
        textFieldUi.checkTextMatches(textFieldId = R.id.note_title, text = text)

    fun clearTitleTextField() =
        textFieldUi.typeIn(textFieldId = R.id.note_title, text = "")
}
