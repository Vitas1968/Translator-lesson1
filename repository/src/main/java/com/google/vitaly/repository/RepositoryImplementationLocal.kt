package geekbrains.ru.translator.model.repository

import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.SearchResult
import com.google.vitaly.repository.DataSourceLocal
import com.google.vitaly.repository.RepositoryLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<SearchResult>>) :
    RepositoryLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(dataModel: DataModel) {
        dataSource.saveToDB(dataModel)
    }
}
