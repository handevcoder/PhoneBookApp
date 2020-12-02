package hi.iwansyy.phonebookapp.model


data class BaseModel<DATA>(
    val status: Boolean,
    val message: String,
    val data: DATA
)

