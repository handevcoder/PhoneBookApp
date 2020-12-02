package hi.iwansyy.phonebookapp.model

import android.os.Parcelable
import hi.iwansyy.phonebookapp.repository.local.entities.TodoEntity
import hi.iwansyy.phonebookapp.repository.remote.request.TodoRequest
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoModel(
    val id: Long = 0,
    var task: String = "",
    var status: Boolean = false,
    var date: String = ""
) : Parcelable

fun TodoModel.toEntity() = TodoEntity(id, task, status, date)
fun TodoModel.toRequest() = TodoRequest(status, task, date)