package uz.hayot.vitaInLine.data.model

data class HealingResponse(
    val data: List<DataItem?>? = null,
    val message: String? = null
)

data class DataItem(
    val period: Int? = null,
    val quantity: Int? = null,
    val endedDate: String? = null,
    val patientId: String? = null,
    val startedDate: String? = null,
    val type: String? = null,
    val pill: String? = null,
    val pillId: String? = null,
    val createdAt: String? = null,
    val times: List<String>? = null,
    val doctorId: String? = null,
    val information: String? = null,
    val extraInformation: String? = null,
    val id: String? = null,
    val updatedAt: String? = null,
    val title: String? = null
)

