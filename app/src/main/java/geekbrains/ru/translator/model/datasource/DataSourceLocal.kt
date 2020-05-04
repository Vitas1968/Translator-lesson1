package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.SearchResult
import io.reactivex.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> = remoteProvider.getData(word)
}
