package uz.hayot.vitaInLine.ui.main.pill

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.hayot.vitaInLine.data.Repository
import uz.hayot.vitaInLine.data.model.advertising.AdvertisingModel
import javax.inject.Inject

@HiltViewModel
class PillViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val advertising = MutableLiveData<AdvertisingModel>()

    fun pillViewModel() = viewModelScope.launch {
        try {
            repository.advertising().let { response ->
                if (response.isSuccessful) {
                    advertising.value = response.body()
                }
            }
        } catch (Ex: Exception) {
            Ex.message
        }
    }
}