package hi.iwansyy.phonebookapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import hi.iwansyy.phonebookapp.databinding.FragmentTodosBinding
import hi.iwansyy.phonebookapp.model.TodoModel
import hi.iwansyy.phonebookapp.repository.TodoLocalRepository
import hi.iwansyy.phonebookapp.repository.TodoRemoteRepository
import hi.iwansyy.phonebookapp.repository.local.TodoLocalRepositoryImpl
import hi.iwansyy.phonebookapp.repository.local.daos.TodoDao
import hi.iwansyy.phonebookapp.repository.local.database.LocalDatabase
import hi.iwansyy.phonebookapp.repository.remote.TodoRemoteRepositoryImpl
import hi.iwansyy.phonebookapp.repository.remote.client.TodoClient
import hi.iwansyy.phonebookapp.repository.remote.service.TodoService
import hi.iwansyy.phonebookapp.view.adapter.TodoAdapter
import hi.iwansyy.phonebookapp.view.sate.TodoState
import hi.iwansyy.phonebookapp.viewmodel.TodoViewModel
import hi.iwansyy.phonebookapp.viewmodel.TodoViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite.*

class TodosFragment : Fragment(), TodoAdapter.TodoListener {
    private lateinit var binding: FragmentTodosBinding

    private val adapter by lazy { TodoAdapter(requireActivity(), this) }
    private val service: TodoService by lazy { TodoClient.service }
    private val dao: TodoDao by lazy { LocalDatabase.getDatabase(requireContext()).dao() }
    private val localRepository: TodoLocalRepository by lazy { TodoLocalRepositoryImpl(dao) }
    private val remoteRepository: TodoRemoteRepository by lazy { TodoRemoteRepositoryImpl(service) }
    private val viewModelFactory by lazy { TodoViewModelFactory(remoteRepository, localRepository) }
    private val viewModel by viewModels<TodoViewModel> { viewModelFactory }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodosBinding.inflate(inflater, container, false).apply {
            rvTodo.adapter = adapter
            viewModel.state.observe(viewLifecycleOwner){
                when(it){
                    is TodoState.Loading -> showLoading(true)
                    is TodoState.Error -> {
                        showLoading(false)
                        showMessage(it.exception.message ?: "Oops something went wrong")
                    }
                    is TodoState.SuccessGetAllTodo -> {
                        showLoading(false)
                        adapter.list = it.list.toMutableList()
                    }
                    is TodoState.SuccessUpdateTodo -> {
                        showLoading(false)
                        adapter.updateTodo(it.todo)
                    }
                    is TodoState.SuccessDeleteTodo -> {
                        showLoading(false)
                        adapter.deleteTodo(it.todo)
                    }
                    is TodoState.SuccessInsertFavorite -> {
                        showMessage("Todo dengan id ${it.todo.id} berhasil ditambahkan di favorite")
                    }
                    is TodoState.SuccessDeleteFavorite -> {
                        showMessage("Todo dengan id ${it.todo.id} berhasil dihapus dari favorite")
                    }
                    else -> throw Exception("Unsupported state type")
                }
            }

            btnAdd.setOnClickListener {  }

        }

        return binding.root
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvTodo.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.btnAdd.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllTodo()
    }

    override fun onChange(todoModel: TodoModel) {
        todoModel.status = !todoModel.status
        viewModel.updateTodo(todoModel)
    }

    override fun onDelete(todoModel: TodoModel) {
        viewModel.deleteTodo(todoModel)
    }

    override fun onFavorite(todoModel: TodoModel) {
        viewModel.submitFavorite(todoModel)
    }
}

