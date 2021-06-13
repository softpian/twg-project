package nz.co.warehouseandroidtest.utils

import android.content.Context

object PreferenceUtil {
    fun getUserId(context: Context): String? {
        val sharedPreferences =
            context.getSharedPreferences(Constants.PREF_USER_ID, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.PREF_USER_ID, null)
    }

    fun putUserId(context: Context, userId: String) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.PREF_USER_ID, Context.MODE_PRIVATE)
        sharedPreferences.edit().run {
            putString(Constants.PREF_USER_ID, userId)
            commit()
        }
    }
}