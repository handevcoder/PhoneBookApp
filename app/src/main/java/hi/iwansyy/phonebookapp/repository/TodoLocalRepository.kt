package hi.iwansyy.phonebookapp.repository

import hi.iwansyy.phonebookapp.repository.local.entities.TodoEntity

interface TodoLocalRepository {
    suspend fun getALlTodo(): List<TodoEntity>
    suspend fun insertTodo(todoEntity: TodoEntity)
    suspend fun deleteTodo(todoEntity: TodoEntity)
    suspend fun isFavorite(id: Long): Boolean
}