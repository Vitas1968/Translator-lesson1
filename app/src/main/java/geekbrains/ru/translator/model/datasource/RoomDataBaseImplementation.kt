package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.SearchResult
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
