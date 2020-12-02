package hi.iwansyy.phonebookapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import hi.iwansyy.phonebookapp.R
import hi.iwansyy.phonebookapp.databinding.FragmentLoginBinding
import hi.iwansyy.phonebookapp.repository.remote.constan.SessionUtil
import hi.iwansyy.phonebookapp.viewmodel.AuthState
import hi.iwansyy.phonebookapp.viewmodel.AuthViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val session by lazy { SessionUtil(requireContext()) }
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            if (session.token.isNotEmpty()) {
                findNavController().navigate(R.id.action_loginFragment_to_todosFragment)
            }

            btnLogin.setOnClickListener {
                if (tilEmail.text.toString().isNotEmpty() &&
                    tilPassword.text.toString().isNotEmpty()
                ) {
                    authViewModel.login(tilEmail.text.toString(), tilPassword.text.toString())
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Email dan password tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            authViewModel.state.observe(viewLifecycleOwner) {
                when (it) {
                    is AuthState.Login -> {
                        btnLogin.visibility = View.VISIBLE
                        session.token = it.data.token
                        findNavController().navigate(R.id.action_loginFragment_to_todosFragment)
                    }
                    is AuthState.Loading -> btnLogin.visibility = View.GONE
                    is AuthState.Error -> {
                        btnLogin.visibility = View.VISIBLE

                        Toast.makeText(
                            requireContext(),
                            it.exception.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

            }

            btnSignUp.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }

        }
        return binding.root
    }
}
