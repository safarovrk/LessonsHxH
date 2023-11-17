package com.example.lesson_3_safarov.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lesson_3_safarov.data.storage.entities.ProductEntity

@Dao
interface ProductDao {

    companion object {
        const val PRODUCT_ENTITY_NAME = "product_entity"
    }

    @Insert(entity = ProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM $PRODUCT_ENTITY_NAME")
    suspend fun getProducts(): List<ProductEntity>
}