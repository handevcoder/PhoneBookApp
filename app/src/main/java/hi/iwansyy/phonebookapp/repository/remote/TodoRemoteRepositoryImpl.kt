package hi.iwansyy.phonebookapp.repository.remote

import hi.iwansyy.phonebookapp.repository.TodoRemoteRepository
import hi.iwansyy.phonebookapp.repository.remote.request.TodoRequest
import hi.iwansyy.phonebookapp.repository.remote.response.BaseResponse
import hi.iwansyy.phonebookapp.repository.remote.response.TodoResponse
import hi.iwansyy.phonebookapp.repository.remote.service.TodoService

class TodoRemoteRepositoryImpl(private val service: TodoService): TodoRemoteRepository {
    override suspend fun getAllTodo(): BaseResponse<List<TodoResponse>> = service.getAllTodo()
    override suspend fun insertTodo(todoRequest: TodoRequest): BaseResponse<TodoResponse> = service.insertTodo(todoRequest)
    override suspend fun updateTodo(id: Long, todoRequest: TodoRequest): BaseResponse<TodoResponse> = service.updateTodoById(id, todoRequest)
    override suspend fun deleteTodo(id: Long): BaseResponse<TodoResponse> = service.deleteTodoById(id)
}