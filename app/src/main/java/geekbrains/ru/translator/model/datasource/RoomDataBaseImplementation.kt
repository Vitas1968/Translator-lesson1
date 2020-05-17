package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.SearchResult
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
