package uz.hayot.vitaInLine.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinner()

        binding.davolanishBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_davolanishFragment)
        }

        binding.tavsiyanomaBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tavsiyanomaFragment)

        }
        binding.homePillBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pillsFragment)
        }


    }

    private fun initSpinner() {
        val languageSpinnerAdapter = ArrayAdapter(
            binding.root.context, R.layout.simple_spinner_item,
            resources.getStringArray(R.array.spinner_items)
        )
        binding.homeLanguageSpinner.setPopupBackgroundResource(R.drawable.edit_text_no_active);
        binding.homeLanguageSpinner.adapter = languageSpinnerAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}