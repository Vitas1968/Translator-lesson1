package geekbrains.ru.translator.model.datasource

import com.google.vitaly.model.data.DataModel
import com.google.vitaly.repository.DataSource

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(dataModel: DataModel)
}
