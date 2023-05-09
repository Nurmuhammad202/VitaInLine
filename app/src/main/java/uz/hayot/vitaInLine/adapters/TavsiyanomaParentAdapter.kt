package uz.hayot.vitaInLine.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.databinding.TavsiyanomaParentRowBinding
import uz.hayot.vitaInLine.models.ParentRcModel

class TavsiyanomaParentAdapter(private val parentList: List<ParentRcModel>) :
    RecyclerView.Adapter<TavsiyanomaParentAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: TavsiyanomaParentRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(parentRcModel: ParentRcModel) {
            binding.tavParentRvName.text = parentRcModel.title
            binding.tavChildRv.adapter = TavsiyanomaChildAdapter(parentRcModel.list)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            TavsiyanomaParentRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(parentList[position])


    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    override fun getItemCount(): Int {
        return parentList.size
    }


}