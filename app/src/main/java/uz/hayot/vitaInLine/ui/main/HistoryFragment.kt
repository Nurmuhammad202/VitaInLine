package uz.hayot.vitaInLine.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.hayot.vitaInLine.adapters.HistoryAdapter
import uz.hayot.vitaInLine.databinding.FragmentHistoryBinding
import uz.hayot.vitaInLine.fake_data.FakeData


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFakeHistory()

    }

    private fun initFakeHistory() {
        val data: MutableList<Any> = FakeData.getHistoryFakeDate()
        val adapter = HistoryAdapter(data)
        binding.historyRv.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}