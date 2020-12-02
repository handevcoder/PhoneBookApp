package hi.iwansyy.phonebookapp.viewmodel

import androidx.lifecycle.*
import hi.iwansyy.phonebookapp.model.TodoModel
import hi.iwansyy.phonebookapp.model.toEntity
import hi.iwansyy.phonebookapp.model.toRequest
import hi.iwansyy.phonebookapp.repository.TodoLocalRepository
import hi.iwansyy.phonebookapp.repository.TodoRemoteRepository
import hi.iwansyy.phonebookapp.repository.local.entities.toModel
import hi.iwansyy.phonebookapp.repository.remote.response.toModel
import hi.iwansyy.phonebookapp.view.sate.TodoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class TodoViewModelFactory(
    private val remoteRepository: TodoRemoteRepository,
    private val localRepository: TodoLocalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(remoteRepository, localRepository) as T
    }
}

class TodoViewModel(
    private val remoteRepository: TodoRemoteRepository,
    private val localRepository: TodoLocalRepository
) : ViewModel() {
    private val mutableState by lazy { MutableLiveData<TodoState>() }
    val state: LiveData<TodoState> get() = mutableState

    fun getAllTodo() {
        mutableState.value = TodoState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoResponse = remoteRepository.getAllTodo()
                val todoListModel = todoResponse.data.asSequence().map { it.toModel() }.toList()
                mutableState.postValue(TodoState.SuccessGetAllTodo(todoListModel))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    fun insertTodo(todoModel: TodoModel) {
        mutableState.value = TodoState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoResponse = remoteRepository.insertTodo(todoModel.toRequest())
                val todo = todoResponse.data.toModel()
                mutableState.postValue(TodoState.SuccessInsertTodo(todo))
            } catch (exc: java.lang.Exception) {
                onError(exc)
            }
        }
    }

    fun updateTodo(todoModel: TodoModel) {
        mutableState.value = TodoState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoResponse = remoteRepository.updateTodo(todoModel.id, todoModel.toRequest())
                val todo = todoResponse.data.toModel()
                mutableState.postValue(TodoState.SuccessUpdateTodo(todo))
            } catch (exc: java.lang.Exception) {
                onError(exc)
            }
        }
    }

    fun deleteTodo(todoModel: TodoModel) {
        mutableState.value = TodoState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoResponse = remoteRepository.deleteTodo(todoModel.id)
                val todo = todoResponse.data.toModel()
                mutableState.postValue(TodoState.SuccessDeleteTodo(todo))
            } catch (exc: java.lang.Exception) {
                onError(exc)
            }
        }
    }

    fun getAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoEntityList = localRepository.getALlTodo()
                val todoModelList = todoEntityList.asSequence().map { it.toModel() }.toList()
                mutableState.postValue(TodoState.SuccessGetAllFavorite(todoModelList))
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    fun submitFavorite(todoModel: TodoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (localRepository.isFavorite(todoModel.id)) {
                    deleteFavorite(todoModel)
                } else {
                    insertFavorite(todoModel)
                }
            } catch (exc: Exception) {
                onError(exc)
            }
        }
    }

    private fun insertFavorite(todoModel: TodoModel){
        viewModelScope.launch(Dispatchers.IO){
            try {
                localRepository.deleteTodo(todoModel.toEntity())
                mutableState.postValue(TodoState.SuccessInsertFavorite(todoModel))
            } catch (exc : Exception){
                onError(exc)
            }
        }
    }

    private fun deleteFavorite(todoModel: TodoModel){
        viewModelScope.launch(Dispatchers.IO){
            try {
                localRepository.deleteTodo(todoModel.toEntity())
                mutableState.postValue(TodoState.SuccessDeleteFavorite(todoModel))
            } catch (exc: Exception){
                onError(exc)
            }
        }
    }

    private fun onError(exc: Exception) {
        exc.printStackTrace()
        mutableState.postValue(TodoState.Error(exc))
    }

}
