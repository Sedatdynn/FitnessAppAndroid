package com.example.cache


import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object CacheManager {
    const val TAG = "CacheManager"
    private const val preferenceName = "MySharedPreferences"
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun init(context: Context) {
        Log.i(TAG, "cache manager has been initialized!")
        preferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    fun saveString(key: CacheKeys, value: String) {
        editor.putString(key.name, value).apply()
    }

    fun saveInt(key: CacheKeys, value: Int) {
        editor.putInt(key.name, value).apply()
    }

    fun saveBoolean(key: CacheKeys, value: Boolean) {
        editor.putBoolean(key.name, value).apply()
    }

    fun saveFloat(key: CacheKeys, value: Float) {
        editor.putFloat(key.name, value).apply()
    }

    fun saveLong(key: CacheKeys, value: Long) {
        editor.putLong(key.name, value).apply()
    }


    fun getInt(key: CacheKeys, default: Int): Int {
        return preferences.getInt(key.name, default)
    }

    fun getString(key: CacheKeys): String? {
        return preferences.getString(key.name, null)
    }

    fun getBoolean(key: CacheKeys, default: Boolean?): Boolean {
        return preferences.getBoolean(key.name, default ?: false)
    }

    fun getFloat(key: CacheKeys, default: Float): Float {
        return preferences.getFloat(key.name, default)
    }

    fun getLong(key: CacheKeys, default: Long): Long {
        return preferences.getLong(key.name, default)
    }


}