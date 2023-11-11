package com.example.lesson_3_safarov.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreferenceStorage @Inject constructor(
    context: Context
) {

    companion object {
        const val PREF_FILE_NAME = "encrypted_shared_pref"
        const val PREF_TOKEN_KEY = "pref_token_key"
    }

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPref: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREF_FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    suspend fun setValue(key: String, value: String) = withContext(Dispatchers.IO) {
        sharedPref.edit().putString(key, value).apply()
    }
    suspend fun getValue(key: String): String = withContext(Dispatchers.IO) {
        return@withContext sharedPref.getString(key, null) ?: ""
    }
    suspend fun deleteValue(key: String) = withContext(Dispatchers.IO) {
        sharedPref.edit().putString(key, null).apply()
    }
}