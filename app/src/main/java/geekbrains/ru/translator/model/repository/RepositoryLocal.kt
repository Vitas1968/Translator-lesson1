package geekbrains.ru.translator.model.repository

import com.google.vitaly.model.data.DataModel

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(dataModel: DataModel)
}
