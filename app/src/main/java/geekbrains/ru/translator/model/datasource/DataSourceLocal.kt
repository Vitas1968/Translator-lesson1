package geekbrains.ru.translator.model.datasource

import com.google.vitaly.model.data.DataModel

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(dataModel: DataModel)
}
