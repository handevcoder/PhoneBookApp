package hi.iwansyy.phonebookapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import hi.iwansyy.phonebookapp.R
import hi.iwansyy.phonebookapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentRegisterBinding.inflate(inflater, container, false).apply {
            this.btnSignIn.setOnClickListener { findNavController().navigate(R.id.action_registerFragment_to_loginFragment) }
        }
        return this.binding.root

    }
}