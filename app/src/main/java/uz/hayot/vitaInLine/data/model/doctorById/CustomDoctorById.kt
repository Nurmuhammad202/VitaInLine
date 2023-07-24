package uz.hayot.vitaInLine.data.model.doctorById

data class CustomDoctorById(var userName: String, var specialty: String, var confirmationId: String)

data class ApproveConfirmationModel(var confirmationId: String)