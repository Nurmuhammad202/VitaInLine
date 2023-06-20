package uz.hayot.vitaInLine.ui.auth

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.data.model.SendSigInModel
import uz.hayot.vitaInLine.databinding.FragmentSignInBinding
import uz.hayot.vitaInLine.util.functions.ExtraFunctions

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpannableText()
        initSpinner()
        visibilityPassword()

        binding.sigInUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        binding.sigInBtn.setOnClickListener {
            val birtDay = binding.signInUsername.text.toString()
            val password = binding.signInPassword.text.toString()
            if (birtDay.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.signIn(sendSigInModel = SendSigInModel(birtDay, password))
                binding.animationSignInView.visibility = View.VISIBLE

            } else {
                Toast.makeText(requireContext(), "Ma'lumotlarni to'ldiring", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        authViewModel.success.observe(requireActivity()) {
            if (it) {
                binding.animationSignInView.visibility = View.GONE
                findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
                requireActivity().finish()
            }
        }


    }

    private fun initSpinner() {
        val languageSpinnerAdapter = ArrayAdapter(
            binding.root.context, R.layout.simple_spinner_item,
            resources.getStringArray(R.array.spinner_items)
        )
        binding.signInLanguageSpinner.setPopupBackgroundResource(R.drawable.edit_text_no_active);
        binding.signInLanguageSpinner.adapter = languageSpinnerAdapter
    }

    private fun initSpannableText() {
        val item = resources.getText(R.string.sign_in_title)
        val startColor: Int = resources.getColor(R.color.secondary_text_color)
        val endColor: Int = resources.getColor(R.color.main_color)
        binding.signInTitle.text =
            ExtraFunctions.twoColoredText(item.toString(), 0, 7, startColor, endColor)

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun visibilityPassword() {
        var isPasswordVisible = false
        val showPasswordDrawable: Drawable =
            ContextCompat.getDrawable(binding.root.context, R.drawable.outline_visibility)!!
        val hidePasswordDrawable: Drawable =
            ContextCompat.getDrawable(binding.root.context, R.drawable.outline_visibility_off)!!
        binding.signInPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = 2
                if (event.rawX >= binding.signInPassword.right - binding.signInPassword.compoundDrawables[drawableRight].bounds.width()) {
                    isPasswordVisible = !isPasswordVisible
                    val passwordTransformationMethod =
                        if (isPasswordVisible) null else PasswordTransformationMethod()
                    binding.signInPassword.transformationMethod = passwordTransformationMethod
                    binding.signInPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        if (isPasswordVisible) hidePasswordDrawable else showPasswordDrawable,
                        null
                    )
                    return@setOnTouchListener true
                }
            }
            false
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}