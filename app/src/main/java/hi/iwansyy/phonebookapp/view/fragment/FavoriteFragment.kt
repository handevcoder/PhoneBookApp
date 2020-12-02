package hi.iwansyy.phonebookapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import hi.iwansyy.phonebookapp.databinding.FragmentFavoriteBinding
import hi.iwansyy.phonebookapp.model.TodoModel
import hi.iwansyy.phonebookapp.repository.TodoLocalRepository
import hi.iwansyy.phonebookapp.repository.TodoRemoteRepository
import hi.iwansyy.phonebookapp.repository.local.TodoLocalRepositoryImpl
import hi.iwansyy.phonebookapp.repository.local.daos.TodoDao
import hi.iwansyy.phonebookapp.repository.local.database.LocalDatabase
import hi.iwansyy.phonebookapp.repository.remote.TodoRemoteRepositoryImpl
import hi.iwansyy.phonebookapp.repository.remote.client.TodoClient
import hi.iwansyy.phonebookapp.repository.remote.service.TodoService
import hi.iwansyy.phonebookapp.view.adapter.FavoriteAdapter
import hi.iwansyy.phonebookapp.view.sate.TodoState
import hi.iwansyy.phonebookapp.viewmodel.TodoViewModel
import hi.iwansyy.phonebookapp.viewmodel.TodoViewModelFactory
import java.lang.Exception

class FavoriteFragment : Fragment(), FavoriteAdapter.FavoriteListener {
    private lateinit var binding: FragmentFavoriteBinding
    private val adapter by lazy { FavoriteAdapter(requireActivity(), this) }
    private val service: TodoService by lazy { TodoClient.service }
    private val dao: TodoDao by lazy { LocalDatabase.getDatabase(requireContext()).dao() }
    private val localRepository : TodoLocalRepository by lazy { TodoLocalRepositoryImpl(dao) }
    private val remoteRepository : TodoRemoteRepository by lazy { TodoRemoteRepositoryImpl(service) }
    private val viewModelFactory by lazy { TodoViewModelFactory(remoteRepository, localRepository) }
    private val viewModel by viewModels<TodoViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false).apply {
            rvFavorite.adapter = adapter
            viewModel.state.observe(viewLifecycleOwner){
                when(it){
                    is TodoState.SuccessGetAllFavorite -> {
                        adapter.list = it.list.toMutableList()
                    }
                    is TodoState.SuccessInsertFavorite -> {
                        adapter.insertTodo(it.todo)
                    }
                    is TodoState.SuccessDeleteFavorite -> {
                        adapter.deleteTodo(it.todo)
                    }
                    else -> throw Exception("Unsupported state type")
                }
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllFavorite()
    }

    override fun onClick(todoModel: TodoModel) {
        viewModel.submitFavorite(todoModel)
    }
}