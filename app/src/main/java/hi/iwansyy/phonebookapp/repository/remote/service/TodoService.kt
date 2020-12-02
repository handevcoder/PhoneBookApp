package hi.iwansyy.phonebookapp.repository.remote.service

import android.widget.BaseAdapter
import hi.iwansyy.phonebookapp.repository.remote.request.TodoRequest
import hi.iwansyy.phonebookapp.repository.remote.response.BaseResponse
import hi.iwansyy.phonebookapp.repository.remote.response.TodoResponse
import retrofit2.http.*

interface TodoService {
    @GET("v1/todos")
    suspend fun getAllTodo(): BaseResponse<List<TodoResponse>>

    @POST("v1/todos")
    suspend fun insertTodo(@Body todoRequest: TodoRequest): BaseResponse<TodoResponse>

    @PUT("v1/todos/{id}")
    suspend fun updateTodoById(
        @Path("id") id: Long,
        @Body todoRequest: TodoRequest
    ): BaseResponse<TodoResponse>

    @DELETE("v1/todos/{id}")
    suspend fun deleteTodoById(@Path("id") id: Long): BaseResponse<TodoResponse>

}