package uz.hayot.vitaInLine.ui.main.doctorsDustup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.adapters.DoctorAdapter
import uz.hayot.vitaInLine.databinding.FragmentDoctorsBinding
import uz.hayot.vitaInLine.ui.dialog.DostupDialog


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
        binding.doctorAnimationView.visibility=View.VISIBLE

        doctorViewModel.getConfirmation()

        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        doctorViewModel.customDoctorByIdModel.observe(requireActivity()) {
            binding.doctorAnimationView.visibility=View.GONE
            if(it.isNotEmpty()){
                binding.RecyclerView.adapter = DoctorAdapter(it, this)
            }else{
                binding.doctorNotFoundContainer.visibility=View.VISIBLE
            }


        }

//        Toast.makeText(requireContext(), doctorViewModel.getErrorText(), Toast.LENGTH_SHORT).show()

        doctorViewModel.successListener.observe(requireActivity()) {
            if(it){
                Toast.makeText(binding.root.context,"Success",Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }else{
                Toast.makeText(binding.root.context,doctorViewModel.getErrorText(),Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClickListener(id: String) {
        openDostupDialog(id)

    }

    private fun openDostupDialog(id:String) {
        val dialog=DostupDialog(binding.root.context){
            if(it){
                doctorViewModel.confirmations(id)
            }else{
               findNavController().popBackStack()
            }
        }

        dialog.show()
    }
}