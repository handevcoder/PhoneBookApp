package hi.iwansyy.phonebookapp.model

data class ContactModel(
    val id: Long,
    val name: String,
    val phone: String,
    val job: String,
    val company: String,
    val email: String,
    val image: String
)