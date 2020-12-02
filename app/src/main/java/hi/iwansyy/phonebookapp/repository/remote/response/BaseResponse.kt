package hi.iwansyy.phonebookapp.repository.remote.response

import com.google.gson.annotations.SerializedName
import hi.iwansyy.phonebookapp.model.TodoModel

data class BaseResponse<T>(val status: Boolean, val message: String, val data:T)