package com.example.lesson_3_safarov.presentation.utils

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.lesson_3_safarov.R
import com.google.android.material.progressindicator.CircularProgressIndicator

class ProgressButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var text = ""
    private var textAppearanceResId: Int = 0
    private var indicatorColor = ContextCompat.getColor(context, R.color.white)

    private var textView = TextView(context)
    private var circularProgressIndicator = CircularProgressIndicator(context)

    var isLoading: Boolean = false
        set(value) {
            if (value) {
                circularProgressIndicator.visibility = VISIBLE
                textView.visibility = INVISIBLE
            } else {
                circularProgressIndicator.visibility = INVISIBLE
                textView.visibility = VISIBLE
            }
            field = value
        }

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ProgressButtonView, defStyleAttr, 0)

        text = typedArray.getString(R.styleable.ProgressButtonView_text) ?: text
        indicatorColor =
            typedArray.getColor(R.styleable.ProgressButtonView_indicatorColor, indicatorColor)
        textAppearanceResId = typedArray.getResourceId(
            R.styleable.ProgressButtonView_textAppearance,
            textAppearanceResId
        )

        typedArray.recycle()

        addTextView()
        addProgressIndicator()
    }

    private fun addTextView() {
        textView.text = text.uppercase()
        textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        textView.gravity = Gravity.CENTER
        if (textAppearanceResId != 0) setTextAppearanceCompat(textView, textAppearanceResId)
        addView(textView)
    }

    private fun addProgressIndicator() {
        circularProgressIndicator.setIndicatorColor(indicatorColor)
        circularProgressIndicator.isIndeterminate = true
        circularProgressIndicator.visibility = INVISIBLE
        circularProgressIndicator.indicatorSize =
            context.resources.getDimension(R.dimen.button_progress_size).toInt()
        addView(circularProgressIndicator)
        (circularProgressIndicator.layoutParams as LayoutParams).gravity = Gravity.CENTER
    }

    private fun setTextAppearanceCompat(textView: TextView, resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(resId)
        } else {
            @Suppress("DEPRECATION")
            textView.setTextAppearance(context, resId)
        }
    }
}