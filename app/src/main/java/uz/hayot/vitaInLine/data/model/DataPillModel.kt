package uz.hayot.vitaInLine.data.model

data class DataPillModel(
	val data: DataPill? = null,
	val message: String? = null
)

data class DataPill(
	val createdAt: String? = null,
	val description: String? = null,
	val id: String? = null,
	val video: String? = null,
	val title: String? = null,
	val slug: String? = null,
	val updatedAt: String? = null
)

