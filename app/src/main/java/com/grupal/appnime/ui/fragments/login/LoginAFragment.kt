package com.grupal.appnime.ui.fragments.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.grupal.appnime.R
import com.grupal.appnime.databinding.FragmentLoginaBinding
import com.grupal.appnime.ui.activities.MainActivity
import com.grupal.appnime.ui.core.ManageUIStates
import com.grupal.appnime.ui.core.UIStates
import com.grupal.appnime.ui.viewmodels.login.LoginFragmentVM
import com.grupal.appnime.ui.fragments.main.MenuFragment

class LoginAFragment : Fragment() {

    private lateinit var binding: FragmentLoginaBinding
    private lateinit var managerUIStates: ManageUIStates
    private val loginFragmentVM: LoginFragmentVM by viewModels()
    private lateinit var auth: FirebaseAuth

    private lateinit var sharedPreferences: SharedPreferences

    private val sharedPreferencesEditor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVariables()
        initListeners()
        initiObservers()
    }

    private fun initVariables() {
        auth = FirebaseAuth.getInstance()
        managerUIStates = ManageUIStates(requireActivity(), binding.lytLoading.mainLayout)
        sharedPreferences = requireContext().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    }


    private fun initiObservers() {
        loginFragmentVM.uiState.observe(viewLifecycleOwner) { state ->
            if (state is UIStates.Success) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            } else {
                managerUIStates.invoke(state)
            }
        }

        loginFragmentVM.idUser.observe(viewLifecycleOwner) { id ->
            startActivity(
                Intent(
                    requireActivity(),
                    MenuFragment::class.java
                )
            )
            requireActivity().finish()
        }
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etxtUser.text.toString()
            val password = binding.etxtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginFragmentVM.authWhitFireBase(email, password, auth, requireActivity())
            } else {
                Toast.makeText(requireContext(), "Ingresa tus credenciales", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
