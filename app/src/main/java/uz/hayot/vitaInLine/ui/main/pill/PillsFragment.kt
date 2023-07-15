package uz.hayot.vitaInLine.ui.main.pill

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
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.adapters.PillAdapter
import uz.hayot.vitaInLine.data.model.DataItem
import uz.hayot.vitaInLine.data.model.DataPill
import uz.hayot.vitaInLine.databinding.FragmentPillsBinding

@AndroidEntryPoint
class PillsFragment : Fragment() {
    private var _binding: FragmentPillsBinding? = null
    private val binding get() = _binding!!
    private val pillViewModel: PillViewModel by viewModels()
    private var pillDataList: List<DataPill> = ArrayList()
    private lateinit var adapter: PillAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPillsBinding.inflate(inflater, container, false)
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animationPillsView.visibility = View.VISIBLE
        pillViewModel.getHealing()
        fakePillsAdapter(pillDataList)


        pillViewModel.successHealing.observe(requireActivity()) { successHealing ->
            if (successHealing) {
                val dataHealing = pillViewModel.getHealingData().data as List<DataItem>
                if (dataHealing.isNotEmpty()) {
                    binding.pillNotFoundContainer.visibility = View.GONE
                    val pillIdList = separateDataById(dataHealing)
                    if(pillIdList.isNotEmpty()){
                        pillViewModel.fetchPillData(pillIdList)
                        pillViewModel.successPill.observe(requireActivity()) {
                            if (it) {
                                pillDataList=pillViewModel.getPillData() as List<DataPill>
                                if(pillDataList.isNotEmpty()){
                                    adapter.updateData(pillDataList)
                                    binding.animationPillsView.visibility = View.GONE
                                }else{
                                    binding.pillNotFoundContainer.visibility = View.VISIBLE
                                }
                            }
                        }
                    }else{
                        binding.pillNotFoundContainer.visibility = View.VISIBLE
                    }

                } else {
                    binding.pillNotFoundContainer.visibility = View.VISIBLE
                }

            } else {
                if (binding.animationPillsView.isVisible) {
                    binding.animationPillsView.visibility = View.GONE
                    Toast.makeText(
                        binding.root.context,
                        pillViewModel.getErrorText(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        adapter.setOnPillsClicked(object : PillAdapter.OnPillsClickedListener {
            override fun onPillsClicked(position: Int) {
                val title =pillDataList[position].title
                val desc = pillDataList[position].description
                val link = pillDataList[position].video
                val bundle = Bundle()
                bundle.putString("title", title)
                bundle.putString("desc", desc)
                bundle.putString("link", link)
                findNavController().navigate(R.id.action_pillsFragment_to_aboutPillFragment, bundle)
            }

        })

        binding.pillsBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun separateDataById(dataHealing: List<DataItem>): MutableList<String> {
        val list: MutableList<String> = ArrayList()
        for (i in dataHealing.indices) {
            if (dataHealing[i].pillId != null) {
                list.add(dataHealing[i].pillId.toString())
            }
        }
        return list
    }

    private fun fakePillsAdapter(data: List<DataPill>) {
        adapter = PillAdapter(data)
        binding.rVPills.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}