package com.example.lesson_3_safarov.presentation.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.lesson_3_safarov.R
import com.google.android.material.snackbar.Snackbar

/**
 * Набор событий, при которых может вызываться [Snackbar].
 */
sealed class SnackbarEvents {
    /**
     * Событие ошибки.
     */
    object ErrorEvent : SnackbarEvents()
    /**
     * Информационное событие.
     */
    object InfoEvent : SnackbarEvents()
}

/**
 * Показывает Snackbar стилизованный под определённое событие [SnackbarEvents].
 *
 * @param snackbar Snackbar, который необходимо показать.
 * @param event Определяет текущее событие. Может быть [SnackbarEvents.InfoEvent] или [SnackbarEvents.ErrorEvent].
 */
fun Context.showStylizedSnackbar(snackbar: Snackbar, event: SnackbarEvents) {
    when (event) {
        is SnackbarEvents.ErrorEvent -> snackbar.view.background =
            ContextCompat.getDrawable(this, R.drawable.snackbar_background_error)

        is SnackbarEvents.InfoEvent -> snackbar.view.background =
            ContextCompat.getDrawable(this, R.drawable.snackbar_background_info)
    }
    snackbar.show()
}