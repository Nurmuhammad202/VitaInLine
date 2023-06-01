package uz.hayot.vitaInLine.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.adapters.DavolanishHistoryAdapter
import uz.hayot.vitaInLine.databinding.FragmentDavolanishHistoryBinding
import uz.hayot.vitaInLine.fake_data.FakeData


class DavolanishHistoryFragment : Fragment() {
    private var _binding: FragmentDavolanishHistoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDavolanishHistoryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFakeDavHistory()

        binding.davHisBackBtn.setOnClickListener{
            findNavController().navigate(R.id.action_davolonishHistoryFragment_to_davolanishFragment)
        }

    }

    private fun initFakeDavHistory() {
        val data: MutableList<Any> = FakeData.getHistoryFakeDate()
        val adapter = DavolanishHistoryAdapter(data)
        binding.davHistoryRv.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}