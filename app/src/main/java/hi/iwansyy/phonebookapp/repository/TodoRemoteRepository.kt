package hi.iwansyy.phonebookapp.repository

import hi.iwansyy.phonebookapp.repository.remote.request.TodoRequest
import hi.iwansyy.phonebookapp.repository.remote.response.BaseResponse
import hi.iwansyy.phonebookapp.repository.remote.response.TodoResponse

interface TodoRemoteRepository{
    suspend fun getAllTodo(): BaseResponse<List<TodoResponse>>
    suspend fun insertTodo(todoRequest: TodoRequest): BaseResponse<TodoResponse>
    suspend fun updateTodo(id: Long, todoRequest: TodoRequest): BaseResponse<TodoResponse>
    suspend fun deleteTodo(id: Long): BaseResponse<TodoResponse>
}