package geekbrains.ru.model.data.userdata

data class Result(
    val text: String = "",
    val meanings: List<Meaning> = listOf()
)
