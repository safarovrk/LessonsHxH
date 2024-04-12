package com.example.lesson_3_safarov.data.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lesson_3_safarov.data.storage.dao.ProductDao
import com.example.lesson_3_safarov.domain.catalog.Product

@Entity(tableName = ProductDao.PRODUCT_ENTITY_NAME)
data class ProductEntity(
    @ColumnInfo("id") @PrimaryKey val id: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("department") val department: String,
    @ColumnInfo("price") val price: String,
    @ColumnInfo("preview") val preview: String,
) {
    companion object {
        fun fromProduct(product: Product) = ProductEntity(
            id = product.id,
            title = product.title,
            department = product.department,
            price = product.price,
            preview = product.preview
        )
    }

    fun toProduct() = Product(
        id = id,
        title = title,
        department = department,
        price = price,
        preview = preview
    )
}