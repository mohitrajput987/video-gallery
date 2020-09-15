@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.youtility.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import java.util.*

/**
 * Created by Mohit Rajput on 15/9/20.
 * This class creates and manages preferences for this application.
 * It can be used in singleton pattern
 */

object SharedPreferenceHelper {
    private const val PREFERENCE_NAME = "video-gallery"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun getString(key: String, defVal: String = ""): String {
        return sharedPreferences.getString(key, defVal)!!
    }

    fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun getInt(key: String, defVal: Int): Int {
        return sharedPreferences.getInt(key, defVal)
    }

    fun saveInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    fun getLong(key: String, defVal: Long): Long {
        return sharedPreferences.getLong(key, defVal)
    }

    fun saveLong(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getBoolean(key: String, defVal: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defVal)
    }

    fun saveBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getFloat(key: String): Float {
        return sharedPreferences.getFloat(key, 0f)
    }

    fun getFloat(key: String, defVal: Float): Float {
        return sharedPreferences.getFloat(key, defVal)
    }

    fun saveFloat(key: String, value: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    /**
     * Put ArrayList of String into SharedPreferences with 'key' and save
     *
     * @param key        SharedPreferences key
     * @param stringList ArrayList of String to be added
     */
    fun saveListString(key: String, stringList: ArrayList<String>) {
        val stringArray = stringList.toTypedArray()
        sharedPreferences.edit().putString(key, TextUtils.join("‚‗‚", stringArray)).apply()
    }

    /**
     * Get parsed ArrayList of String from SharedPreferences at 'key'
     *
     * @param key SharedPreferences key
     * @return ArrayList of String
     */
    fun getListString(key: String): ArrayList<String> {
        return ArrayList(
            mutableListOf(
                *TextUtils.split(
                    sharedPreferences.getString(key, ""),
                    "‚‗‚"
                )
            )
        )
    }

    /**
     * Used to check whether preference contains value of given 'key'
     *
     * @param key SharedPreferences key
     * @return true is value exist for the key. false otherwise
     */
    fun contains(key: String) = sharedPreferences.contains(key)

    /**
     * Clear all values from this preference
     */
    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    /**
     * Clear value of given key from this preference
     *
     * @param key name of the key whose value to be removed
     */
    fun clear(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
}
