package uz.hayot.vitaInLine.ui.main.doctorsDustup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.adapters.DoctorAdapter
import uz.hayot.vitaInLine.databinding.FragmentDoctorsBinding


@AndroidEntryPoint
class DoctorsFragment : Fragment(), DoctorAdapter.OnItemClickListener {
    private var _binding: FragmentDoctorsBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val doctorViewModel: DoctorViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDoctorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doctorViewModel.getConfirmation()


        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        doctorViewModel.customDoctorByIdModel.observe(requireActivity()) {
            binding.RecyclerView.adapter = DoctorAdapter(it, this)
        }

        Toast.makeText(requireContext(), doctorViewModel.getErrorText(), Toast.LENGTH_SHORT).show()

        doctorViewModel.successListener.observe(requireActivity()) {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClickListener(id: String) {
        doctorViewModel.confirmations(id)
        Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
    }
}