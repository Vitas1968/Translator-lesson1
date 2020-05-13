package geekbrains.ru.translator.view.base

import androidx.appcompat.app.AppCompatActivity
import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.viewmodel.BaseViewModel

abstract class BaseActivity<T : DataModel, I : Interactor<T>> : AppCompatActivity() {
    abstract val model: BaseViewModel<T>
    abstract  fun renderData(dataModel: T)
    }

