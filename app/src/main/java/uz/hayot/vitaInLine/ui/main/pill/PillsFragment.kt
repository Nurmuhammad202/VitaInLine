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
import uz.hayot.vitaInLine.data.model.advertising.Data
import uz.hayot.vitaInLine.databinding.FragmentPillsBinding

@AndroidEntryPoint
class PillsFragment : Fragment() {
    private var _binding: FragmentPillsBinding? = null
    private val binding get() = _binding!!
    private val pillViewModel: PillViewModel by viewModels()

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
        pillViewModel.pillViewModel()

        pillViewModel.success.observe(requireActivity()) { success ->
            if (success) {
                binding.animationPillsView.visibility = View.GONE
                val dataPill = pillViewModel.getPillData()
                if (dataPill.data.isNotEmpty()) {
                    binding.pillNotFoundContainer.visibility = View.GONE
                    fakePillsAdapter(dataPill.data as ArrayList<Data>)
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


        binding.pillsBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun fakePillsAdapter(data: ArrayList<Data>) {
        val adapter = PillAdapter(data)
        binding.animationPillsView.visibility = View.GONE
        adapter.setOnPillsClicked(object : PillAdapter.OnPillsClickedListener {
            override fun onPillsClicked(position: Int) {
                val title = data[position].title
                val desc = data[position].description
                val link = data[position].video
                val bundle = Bundle()
                bundle.putString("title", title)
                bundle.putString("desc", desc)
                bundle.putString("link", link)
                findNavController().navigate(R.id.action_pillsFragment_to_aboutPillFragment, bundle)
            }

        })
        binding.rVPills.adapter = adapter

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}