package hi.iwansyy.phonebookapp.view.sate

import hi.iwansyy.phonebookapp.model.TodoModel
import java.lang.Exception

sealed class TodoState {
    data class Loading(val message: String = "Loading ..."): TodoState()
    data class Error(val exception: Exception): TodoState()
    data class SuccessGetAllTodo(val list: List<TodoModel>): TodoState()
    data class SuccessInsertTodo(val todo: TodoModel): TodoState()
    data class SuccessUpdateTodo(val todo: TodoModel): TodoState()
    data class SuccessDeleteTodo(val todo: TodoModel): TodoState()
    data class SuccessGetAllFavorite(val list: List<TodoModel>): TodoState()
    data class SuccessInsertFavorite(val todo: TodoModel): TodoState()
    data class SuccessDeleteFavorite(val todo: TodoModel): TodoState()

}