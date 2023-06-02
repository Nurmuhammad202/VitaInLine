package uz.hayot.vitaInLine.data.model

data class CreateDataPatient(
    val fullname: String,
    val birthday: String,
    val passport: String,
    val province: String,
    val phone: String
)
