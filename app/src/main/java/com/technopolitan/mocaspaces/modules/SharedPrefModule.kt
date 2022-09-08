package com.technopolitan.mocaspaces.modules

import android.content.Context
import android.content.SharedPreferences
import com.technopolitan.mocaspaces.SharedPrefKey
import dagger.Module
import javax.inject.Inject

@Module
class SharedPrefModule @Inject constructor(private var context: Context) {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SharedPrefKey.Moca.name, Context.MODE_PRIVATE)

    fun setIntToShared(key: String, value: Int) {
        try {
            sharedPreferences.edit()?.putInt(key, value)?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getIntFromShared(key: String): Int {
        return try {
            sharedPreferences.getInt(key, 0)
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun setBooleanToShared(key: String, value: Boolean) {
        try {
            sharedPreferences.edit()?.putBoolean(key, value)?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getBooleanFromShared(key: String): Boolean {
        return try {
            sharedPreferences.getBoolean(key, false)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun setStringToShared(key: String, value: String) {
        try {
            sharedPreferences.edit()?.putString(key, value)?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getStringFromShared(key: String): String {
        return try {
            sharedPreferences.getString(key, "")!!
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun setFloatToShared(key: String, value: Float) {
        try {
            sharedPreferences.edit()?.putFloat(key, value)?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getFloatFromShared(key: String): Float {
        return try {
            sharedPreferences.getFloat(key, 0f)
        } catch (e: Exception) {
            e.printStackTrace()
            0f
        }
    }

    fun setLongToShared(key: String, value: Long) {
        try {
            sharedPreferences.edit()?.putLong(key, value)?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getLongFromShared(key: String): Long {
        return try {
            sharedPreferences.getLong(key, 0)
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun setStringSetShared(key: String, value: Set<String>) {
        try {
            sharedPreferences.edit()?.putStringSet(key, value)?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getStringSetFromShared(key: String): Set<String> {
        val stringSet: MutableSet<String> = HashSet()
        return try {
            sharedPreferences.getStringSet(key, stringSet)!!
        } catch (e: Exception) {
            e.printStackTrace()
            stringSet
        }
    }

    fun remove(key: String) {
        try {
            sharedPreferences.edit()?.remove(key)?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun contain(key: String): Boolean {
        return try {
            sharedPreferences.contains(key)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun clearAll() {
        try {
            sharedPreferences.edit()?.clear()?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}