package hi.iwansyy.phonebookapp.repository.remote.request

data class TodoRequest(val status: Boolean, val task: String = "", val date: String = "")