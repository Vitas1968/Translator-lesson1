package geekbrains.ru.translator.model.repository

import com.google.vitaly.model.data.SearchResult
import geekbrains.ru.translator.model.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResult>>) :
    Repository<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        return dataSource.getData(word)
    }
}
