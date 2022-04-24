package com.veselovvv.notes.ui.note

import android.text.Editable
import android.text.TextWatcher

interface BaseTextWatcher : TextWatcher {
    override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun afterTextChanged(sequence: Editable?) = Unit
}