package hi.iwansyy.phonebookapp.repository.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hi.iwansyy.phonebookapp.model.ContactModel

@Entity(tableName = "tabel_contact")
data class ContactEntity(
@PrimaryKey val id: Long = 0,
@ColumnInfo(name = "name") var name: String = "",
@ColumnInfo(name = "phone") var phone: String = "",
@ColumnInfo(name = "job") var job: String = "",
@ColumnInfo(name = "company") var company: String = "",
@ColumnInfo(name = "email") var email: String = "",
@ColumnInfo(name = "image") var image: String = ""
)
fun ContactEntity.toModel() = ContactModel(id, name, phone, job, company, email, image)
/*
@Entity(tableName = "tabel_todo")
data class TodoEntity(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(name = "task") var task: String = "",
    @ColumnInfo(name = "status") var status: Boolean = false,
    @ColumnInfo(name = "date") var date: String = ""

        val id: Long,
    val name: String,
    val phone: String,
    val job: String,
    val company: String,
    val email: String,
    val image: String
)
fun TodoEntity.toModel() = TodoModel(id, task, status, date)*/