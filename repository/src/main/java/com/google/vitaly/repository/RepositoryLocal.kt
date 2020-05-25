package geekbrains.ru.translator.model.repository

import com.google.vitaly.model.data.DataModel
import com.google.vitaly.repository.Repository

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(dataModel: DataModel)
}
