package com.example.lesson_5_safavor

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat


class ChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val MAX_COLUMNS = 9
        const val COLUMN_LINE_WIDTH = 14f
        const val START_COLUMN_HEIGHT = 8f
        const val COLUMN_TEXT_OFFSET = 32f
        const val TEXT_OFFSET = 16f
        const val ASPECT_RATIO = 360 / 150
    }

    private var textColor = ContextCompat.getColor(context, R.color.black)
    private var columnColor = ContextCompat.getColor(context, R.color.black)
    private var maxColumnValue = Int.MAX_VALUE

    private val paintColumn = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val paintColumnText = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = 12 * resources.displayMetrics.density
    }

    private val paintText = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = 10 * resources.displayMetrics.density
    }

    // Расчёт высоты строк для отрисовки соответствующих отступов
    private val columnTextHeight =
        paintColumnText.fontMetrics.bottom - paintColumnText.fontMetrics.top
    private val textHeight = paintText.fontMetrics.bottom - paintText.fontMetrics.top


    private var _chartDataList: ArrayList<Pair<String, Int>>? = arrayListOf()
    private val chartDataList get() = _chartDataList!!

    fun setData(data: ArrayList<Pair<String, Int>>) {
        _chartDataList = data
        invalidate()
    }

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ChartView, defStyleAttr, 0)

        textColor = typedArray.getColor(R.styleable.ChartView_textColor, textColor)
        columnColor = typedArray.getColor(R.styleable.ChartView_columnColor, columnColor)
        maxColumnValue = typedArray.getInteger(R.styleable.ChartView_maxColumnValue, maxColumnValue)

        paintText.color = textColor
        paintColumn.color = columnColor
        paintColumnText.color = columnColor

        typedArray.recycle()
    }

    private var percentMultiplier = 1f

    private fun animateColumns() {
        animation.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _chartDataList = null
        animation.cancel()
    }

    private val animation = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1000
        addUpdateListener {
            percentMultiplier = (it.animatedValue as Float)
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val newWidth = MeasureSpec.getSize(widthMeasureSpec)
        val newHeight = newWidth / ASPECT_RATIO

        setMeasuredDimension(newWidth, newHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val currentColumnsCount =
            if (chartDataList.size <= MAX_COLUMNS) chartDataList.size
            else MAX_COLUMNS

        val currentColumnWidth = measuredWidth / currentColumnsCount

        for ((index, dataColumn) in chartDataList.withIndex()) {
            if (index <= currentColumnsCount - 1) {

                // Проверка на то, находится ли указанное значение в диапазоне допустимого
                val currentColumnValue = if (dataColumn.second > maxColumnValue) maxColumnValue
                else if (dataColumn.second <= 0) 0
                else dataColumn.second

                // Процент занимаемого столбцом места по высоте, относительно всего места
                val columnPercentOfHeight = ((currentColumnValue * 100) / maxColumnValue) * 0.01f

                // Высота столбца, текста и отступа между ними
                val columnAndTextHeight = measuredHeight - textHeight - TEXT_OFFSET

                // Высота только столбца, без высоты текста над ним и отступа
                val columnHeight = columnAndTextHeight - columnTextHeight - COLUMN_TEXT_OFFSET

                // Высота столбца для текущего значения
                val currentColumnHeight =
                    (columnHeight * columnPercentOfHeight * percentMultiplier) + START_COLUMN_HEIGHT

                canvas.drawRoundRect(
                    currentColumnWidth * index + currentColumnWidth / 2 - COLUMN_LINE_WIDTH / 2,
                    columnAndTextHeight - currentColumnHeight,
                    currentColumnWidth * index + currentColumnWidth / 2 + COLUMN_LINE_WIDTH / 2,
                    columnAndTextHeight,
                    10f,
                    10f,
                    paintColumn
                )
                canvas.drawText(
                    dataColumn.second.toString(),
                    (currentColumnWidth * index)
                            + (currentColumnWidth / 2)
                            - (paintColumnText.measureText(dataColumn.second.toString()) / 2),
                    columnAndTextHeight - currentColumnHeight - COLUMN_TEXT_OFFSET,
                    paintColumnText
                )
                canvas.drawText(
                    dataColumn.first,
                    (currentColumnWidth * index)
                            + (currentColumnWidth / 2)
                            - (paintText.measureText(dataColumn.first) / 2),
                    measuredHeight.toFloat(),
                    paintText
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when {
            gestureDetector.onTouchEvent(event) -> true
            else -> false
        }
    }

    private val gestureDetector = GestureDetector(context,
        object : GestureDetector.OnGestureListener {
            override fun onDown(p0: MotionEvent): Boolean {
                return false
            }

            override fun onShowPress(p0: MotionEvent) {

            }

            override fun onSingleTapUp(p0: MotionEvent): Boolean {
                animateColumns()
                return true
            }

            override fun onScroll(
                p0: MotionEvent?,
                p1: MotionEvent,
                p2: Float,
                p3: Float
            ): Boolean {
                return false
            }

            override fun onLongPress(p0: MotionEvent) {
                animateColumns()
            }

            override fun onFling(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
                return false
            }
        }
    )
}