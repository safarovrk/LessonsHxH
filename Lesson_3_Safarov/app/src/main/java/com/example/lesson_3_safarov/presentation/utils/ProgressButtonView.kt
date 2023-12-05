package com.example.lesson_3_safarov.presentation.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.databinding.ViewLoadableButtonBinding

class ProgressButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ViewLoadableButtonBinding? = null

    private var buttonText = ""

    init {
        binding = ViewLoadableButtonBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.view_loadable_button, this, true)
        )
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ProgressButtonView, defStyleAttr, 0)

        buttonText = typedArray.getString(R.styleable.ProgressButtonView_text).toString()
        setText(buttonText)

        typedArray.recycle()
    }

    fun setStateLoading() = binding?.run {
        buttonLoadable.text = ""
        progressBar.visibility = VISIBLE
    }

    fun setStateData() = binding?.run {
        buttonLoadable.text = buttonText
        progressBar.visibility = GONE
    }

    fun setText(text: String) = binding?.run {
        buttonText = text
        buttonLoadable.text = buttonText
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }
}