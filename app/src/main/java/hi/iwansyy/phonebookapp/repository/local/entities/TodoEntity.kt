package hi.iwansyy.phonebookapp.repository.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hi.iwansyy.phonebookapp.model.TodoModel

@Entity(tableName = "tabel_todo")
data class TodoEntity(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(name = "task") var task: String = "",
    @ColumnInfo(name = "status") var status: Boolean = false,
    @ColumnInfo(name = "date") var date: String = ""
)
fun TodoEntity.toModel() = TodoModel(id, task, status, date)