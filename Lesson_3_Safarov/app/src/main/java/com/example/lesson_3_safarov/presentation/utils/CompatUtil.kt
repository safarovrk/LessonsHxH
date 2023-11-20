package com.example.lesson_3_safarov.presentation.utils

import android.content.Context
import android.os.Build
import android.widget.TextView

fun Context.setTextAppearanceCompat(textView: TextView, resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        textView.setTextAppearance(resId)
    } else {
        @Suppress("DEPRECATION")
        textView.setTextAppearance(this, resId)
    }
}
