package uz.hayot.vitaInLine.models

data class ParentRcModel(val title: String, val list: MutableList<ChildRcModel>)
data class ChildRcModel(
    val checkBox: Boolean,
    val time: String,
    val pillCount: String,
    val pillDesc: String
)