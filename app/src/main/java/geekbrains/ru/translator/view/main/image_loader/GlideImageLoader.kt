package com.google.vitaly.appvk.ui.image

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.vitaly.appvk.mvp.model.cache.image.IImageCache
import com.google.vitaly.appvk.mvp.model.image.IImageLoader
import com.google.vitaly.appvk.mvp.network.NetworkStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import timber.log.Timber
import java.io.ByteArrayOutputStream

class GlideImageLoader(override val cache: IImageCache, val networkStatus: NetworkStatus) :
    IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Timber.d("Loading image $url ")
        networkStatus.isOnlineSingle()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isOnline ->
                if (isOnline) {
                    Timber.d("Online. Loading image from net")
                    Glide.with(container.context)
                        .asBitmap()
                        .load(url)
                        .listener(object : RequestListener<Bitmap> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean) =
                                Timber.d("Failed to load image $url").let { true }

                            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                val compressFormat = if (url.contains(".jpg")) Bitmap.CompressFormat.JPEG else Bitmap.CompressFormat.PNG
                                val stream = ByteArrayOutputStream()
                                resource?.compress(compressFormat, 100, stream)
                                val bytes = stream.use { it.toByteArray() }
                                cache.saveImage(url, bytes).blockingAwait()
                                return false
                            }
                        })
                        .into(container)
                } else {
                    Timber.d("Offline. Loading image from cache")
                    cache.getBytes(url).observeOn(AndroidSchedulers.mainThread()).subscribe({
                        Glide.with(container.context)
                            .load(it)
                            .into(container)
                    }, {
                        Timber.e(it)
                    })
                }
            }

    }
}