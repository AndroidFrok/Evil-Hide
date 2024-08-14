package me.sweetll.evilhide.adapter

import androidx.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.Switch
import com.github.ivbaranov.mfb.MaterialFavoriteButton

@BindingAdapter("drawable")
fun setImageSrc(imageView: ImageView, drawable: Drawable) {
    imageView.setImageDrawable(drawable)
}

@BindingAdapter("favorite")
fun setFavorite(materialFavoriteButton: MaterialFavoriteButton, favorite: Boolean) {
    materialFavoriteButton.setFavorite(favorite, false)
}
