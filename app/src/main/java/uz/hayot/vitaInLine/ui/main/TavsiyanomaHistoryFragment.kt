package uz.hayot.vitaInLine.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.adapters.DavolanishParentAdapter
import uz.hayot.vitaInLine.data.model.DataItem
import uz.hayot.vitaInLine.databinding.FragmentTavsiyanomaHistoryBinding

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class TavsiyanomaHistoryFragment : Fragment() {
    private var _binding: FragmentTavsiyanomaHistoryBinding? = null
    private val binding get() = _binding!!

    private val davolanishViewModel: DavolanishViewModel by viewModels()
    private lateinit var dataList: List<DataItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTavsiyanomaHistoryBinding.inflate(inflater, container, false)
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animationTavsiyaHisView.visibility = View.VISIBLE
        davolanishViewModel.recommendationsHistory()


        davolanishViewModel.success.observe(requireActivity()) { success ->
            if (success) {
                binding.animationTavsiyaHisView.visibility = View.GONE
                dataList = davolanishViewModel.getRecommendationHisData().data as List<DataItem>
                if (dataList.isEmpty()) binding.tavHisNotFoundContainer.visibility = View.VISIBLE
                else binding.tavHisNotFoundContainer.visibility = View.GONE
                initDataAdapter(dataList)
            } else {
                if (binding.animationTavsiyaHisView.isVisible) {
                    binding.animationTavsiyaHisView.visibility = View.GONE
                    Toast.makeText(
                        binding.root.context,
                        davolanishViewModel.getErrorText(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        binding.tavHisBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun initDataAdapter(list: List<DataItem>) {
        val adapter = DavolanishParentAdapter(list, "recommendations")
        binding.davRecyclerView.adapter = adapter
        adapter.setOnInfoClicked(object : DavolanishParentAdapter.OnParentInfoClickedListener {})

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}