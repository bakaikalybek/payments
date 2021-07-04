package kg.bakai.payments.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kg.bakai.payments.R
import kg.bakai.payments.data.model.Status
import kg.bakai.payments.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)
        binding.apply {
            btnEnter.setOnClickListener {
                val isFormValid = validate()
                if (isFormValid) {
                    loginViewModel.login(etLogin.text.toString(), etPassword.text.toString())
                }
            }
        }


        loginViewModel.apply {
            if(checkSession()) navController.navigate(R.id.action_navigation_login_to_navigation_home)
            loginResponse.observe(viewLifecycleOwner) { resource ->
                when (resource?.status) {
                    Status.SUCCESS -> {
                        if(checkSession()) navController.navigate(R.id.action_navigation_login_to_navigation_home)
                    }
                    else -> Toast.makeText(requireContext(), resource?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun validate(): Boolean {
        binding.apply {
            if (etLogin.text.isNullOrEmpty()) {
                etLogin.error = "Поле не должно быть пустым"
                return false
            } else if (etPassword.text.isNullOrEmpty()) {
                etPassword.error = "Поле не должно быть пустым"
                return false
            }
            return true
        }
    }
}