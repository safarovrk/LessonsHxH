package com.example.lesson_3_safarov.presentation.ui.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PreOrderProduct(
    val id: String,
    val title: String,
    val department: String,
    val price: String,
    val preview: String,
    val size: String
) : Parcelable {

    companion object {
        private const val CURRENCY_POSTFIX_LENGTH = 1
    }

    fun getIntPrice(): Int {
        var stringPrice = this.price
        stringPrice = stringPrice.replace(" ", "")
        stringPrice = stringPrice.dropLast(CURRENCY_POSTFIX_LENGTH)
        return try { stringPrice.toInt() }
        catch (e: NumberFormatException) { 0 }
    }
}
