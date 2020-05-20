package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.DataModel

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(dataModel: DataModel)
}
