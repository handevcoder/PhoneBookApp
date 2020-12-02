package hi.iwansyy.phonebookapp.repository.remote.constan

import android.content.Context
import androidx.core.content.edit

class SessionUtil(private val context: Context) {
    private val sharedName = "Local_Session"
    private val tokenKey = "Local_Session"

    private val sharedPreferences by lazy {
        context.getSharedPreferences(sharedName, Context.MODE_PRIVATE)
    }

    var token
        get() = sharedPreferences.getString(tokenKey, "") ?: ""
        set(value) {
            sharedPreferences.edit { putString(tokenKey, "Bearer $value") }
        }
}
