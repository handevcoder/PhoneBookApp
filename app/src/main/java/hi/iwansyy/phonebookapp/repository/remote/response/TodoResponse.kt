package hi.iwansyy.phonebookapp.repository.remote.response

import com.google.gson.annotations.SerializedName
import hi.iwansyy.phonebookapp.model.TodoModel

data class TodoResponse(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("task") val task: String = "",
    @SerializedName("status") val status: Boolean = false,
    @SerializedName("date") val date: String
)

fun TodoResponse.toModel() = TodoModel(id, task, status, date)