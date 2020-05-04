package geekbrains.ru.translator.view.base

import geekbrains.ru.translator.model.data.DataModel

interface View {

    fun renderData(dataModel: DataModel)

}
