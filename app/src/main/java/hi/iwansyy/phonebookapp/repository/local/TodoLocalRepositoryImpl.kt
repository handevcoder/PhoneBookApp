package hi.iwansyy.phonebookapp.repository.local

import hi.iwansyy.phonebookapp.repository.TodoLocalRepository
import hi.iwansyy.phonebookapp.repository.local.daos.TodoDao
import hi.iwansyy.phonebookapp.repository.local.entities.TodoEntity

class TodoLocalRepositoryImpl(private val dao: TodoDao) : TodoLocalRepository{
    override suspend fun getALlTodo(): List<TodoEntity> = dao.getAllTodo()
    override suspend fun insertTodo(todoEntity: TodoEntity) = dao.insertTodo(todoEntity)
    override suspend fun deleteTodo(todoEntity: TodoEntity) = dao.deleteTodo(todoEntity)
    override suspend fun isFavorite(id: Long): Boolean = dao.isFavorites(id)
}