package uz.hayot.vitaInLine.ui.main.pill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.databinding.FragmentAboutPillBinding


class AboutPillFragment : Fragment() {
    private var _binding: FragmentAboutPillBinding? = null
    private val binding get() = _binding!!
    private val args: AboutPillFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAboutPillBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFakeAboutPill()

        binding.aboutPillBackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_aboutPillFragment_to_pillsFragment)
        }
    }

    private fun initFakeAboutPill() {
        binding.aboutPillImage.setImageResource(args.aboutPill.pillIcon)
        binding.aboutPillName.text = args.aboutPill.pillName
        binding.aboutPillDesc.text = args.aboutPill.pillDescription
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}