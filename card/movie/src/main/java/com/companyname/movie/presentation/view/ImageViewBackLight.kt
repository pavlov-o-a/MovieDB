package com.companyname.movie.presentation.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import kotlin.math.abs

private const val BITMAP_SIZE = 60
private const val FRAME_WIDTH = 2

class ImageViewBackLight @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        pickBackLight()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        pickBackLight()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        pickBackLight()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        pickBackLight()
    }

    private fun pickBackLight(){
        drawable?.let {
            val bitmap = it.toBitmap(BITMAP_SIZE, BITMAP_SIZE)
            pickImageBackLight2(bitmap)
        }
    }

    private fun pickImageBackLight2(bitmap: Bitmap) {
        //we take pixels only from top half of the frame with FRAME_WIDTH
        //because in movie card ony top half of picture is visible on opening
        val framePixels = mutableListOf<Int>()
        for (j in 0 until bitmap.height/2){
            if (j <= FRAME_WIDTH) {
                for (i in 0 until bitmap.width)
                    framePixels.add(bitmap.getPixel(i, j))
            } else {
                for (i in 0..FRAME_WIDTH)
                    framePixels.add(bitmap.getPixel(i, j))
                for (i in (bitmap.width - 1 - FRAME_WIDTH) until bitmap.width)
                    framePixels.add(bitmap.getPixel(i, j))
            }
        }
        //we choose most popular color
        val threshold = 10
        var maxColorWeight = 0
        var newMaxWeight: Int
        var popularColor = framePixels[0]
        for (pixel in framePixels){
            newMaxWeight = 0
            for (other in framePixels){
                if (abs(Color.red(pixel) - Color.red(other)) < threshold
                    && abs(Color.green(pixel) - Color.green(other)) < threshold
                    && abs(Color.blue(pixel) - Color.blue(other)) < threshold)
                    newMaxWeight++
            }
            if (newMaxWeight > maxColorWeight){
                popularColor = pixel
                maxColorWeight = newMaxWeight
            }
        }
        setBackgroundColor(popularColor)
    }
}