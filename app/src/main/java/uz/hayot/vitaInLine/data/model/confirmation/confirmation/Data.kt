package uz.hayot.vitaInLine.data.model.confirmation.confirmation

data class Data(
    val approved: Boolean,
    val createdAt: String,
    val doctorId: String,
    val id: String,
    val isDeleted: Boolean,
    val patientId: String,
    val time: String,
    val updatedAt: String
)