package geekbrains.ru.translator.model.datasource

import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.SearchResult
import geekbrains.ru.translator.room.HistoryDao
import geekbrains.ru.translator.utils.convertDataModelSuccessToEntity
import geekbrains.ru.translator.utils.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        val listHistoryEntity=historyDao.all()
        return mapHistoryEntityToSearchResult(listHistoryEntity)
    }

    override suspend fun saveToDB(dataModel: DataModel) {
        convertDataModelSuccessToEntity(dataModel)?.let {
            historyDao.insert(it)
        }
    }
}
