package <%= appPackage %>.application

import android.content.Context
import android.util.Log

import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideModule: AppGlideModule() {

    @Suppress("INTEGER_OVERFLOW")
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        val diskCacheSizeBytes = 1024 * 1024 * 100 * 50
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, "Hue-images", diskCacheSizeBytes.toLong()))
        builder.setLogLevel(Log.DEBUG)
    }
}