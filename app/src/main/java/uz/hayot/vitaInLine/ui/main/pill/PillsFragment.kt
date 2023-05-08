package uz.hayot.vitaInLine.ui.main.pill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.hayot.vitaInLine.adapters.PillAdapter
import uz.hayot.vitaInLine.databinding.FragmentPillsBinding
import uz.hayot.vitaInLine.fake_data.FakeData
import uz.hayot.vitaInLine.models.Pill

class PillsFragment : Fragment(), PillAdapter.OnPillsClickedListener {
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

    }

    private fun fakePillsAdapter() {
        val list: MutableList<Pill> = FakeData.getPillData()
        val adapter = PillAdapter(list)
        adapter.setOnPillsClicked(this)
        binding.rcViewPills.adapter = adapter

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPillsClicked(position: Int) {

    }
}