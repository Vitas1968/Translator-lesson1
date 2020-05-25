package geekbrains.ru.translator.view.main.image_loader


import android.widget.ImageView
import com.bumptech.glide.Glide


class GlideImageLoader {
    fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }
}