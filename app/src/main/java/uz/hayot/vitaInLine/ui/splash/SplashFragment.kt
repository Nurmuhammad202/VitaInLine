package uz.hayot.vitaInLine.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.databinding.FragmentSplashBinding
import uz.hayot.vitaInLine.ui.auth.AuthViewModel
import uz.hayot.vitaInLine.util.functions.ExtraFunctions


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init spannable text
        initSpannableText()
        // fake transition
        val loginState = viewModel.getToken()
        object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {

                if (loginState) {
                    findNavController().navigate(R.id.action_splashFragment_to_mainActivity)
                    requireActivity().finish()
                }
                else findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
            }
        }.start()
    }

    private fun initSpannableText() {
        val item = resources.getText(R.string.splash_text)
        val startColor: Int = resources.getColor(R.color.black)
        val endColor: Int = resources.getColor(R.color.main_color)
        binding.splashSpannableText.text =
            ExtraFunctions.twoColoredText(item.toString(), 0, 7, startColor, endColor)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}