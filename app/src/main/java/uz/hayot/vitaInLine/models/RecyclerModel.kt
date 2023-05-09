package uz.hayot.vitaInLine.models

data class ParentRcModel(val icon: Int, val title: String, val list: MutableList<ChildRcModel>)
data class ChildRcModel(
    val checkBox: Boolean,
    val time: String,
    val pillCount: String,
    val pillDesc: String
)

data class DateModel(val date: String)