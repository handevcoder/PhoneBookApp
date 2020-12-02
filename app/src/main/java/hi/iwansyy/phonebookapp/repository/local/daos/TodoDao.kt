package hi.iwansyy.phonebookapp.repository.local.daos

import androidx.room.*
import hi.iwansyy.phonebookapp.repository.local.entities.TodoEntity

@Dao
interface TodoDao {
    @Query("SELECT * FROM tabel_todo ORDER BY ID DESC")
    suspend fun getAllTodo(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Update
    suspend fun updateTodo(todoEntity: TodoEntity)

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)

    @Query("SELECT EXISTS(SELECT * FROM tabel_todo WHERE id = :id)")
    suspend fun isFavorites(id: Long): Boolean
}