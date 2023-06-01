package uz.hayot.vitaInLine.ui.main.pill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.adapters.PillAdapter
import uz.hayot.vitaInLine.databinding.FragmentPillsBinding
import uz.hayot.vitaInLine.fake_data.FakeData
import uz.hayot.vitaInLine.models.Pill

class PillsFragment : Fragment() {
    private var _binding: FragmentPillsBinding? = null
    private val binding get() = _binding!!


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

        fakePillsAdapter()

        binding.pillsBackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_pillsFragment_to_homeFragment)
        }


    }

    private fun fakePillsAdapter() {
        val list: MutableList<Pill> = FakeData.getPillData()
        val adapter = PillAdapter(list)
        adapter.setOnPillsClicked(object : PillAdapter.OnPillsClickedListener {
            override fun onPillsClicked(position: Int) {
                val objPill = list[position]
                val action = PillsFragmentDirections.actionPillsFragmentToAboutPillFragment(objPill)
                findNavController().navigate(action)
            }

        })
        binding.rVPills.adapter = adapter

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}