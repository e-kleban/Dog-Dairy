package by.kleban.dogdairy.utils.picasso.transformation

import android.graphics.*
import com.squareup.picasso.Transformation


class CircleTransform : Transformation {

    override fun transform(source: Bitmap?): Bitmap {
        val newSource = source as Bitmap

        val size: Int = Math.min(newSource.width, newSource.height)

        val x: Int = (newSource.width - size) / 2
        val y: Int = (newSource.height - size) / 2

        val squareBitmap = Bitmap.createBitmap(newSource, x, y, size, size)
        if (squareBitmap != newSource) {
            newSource.recycle()
        }
        val bitmap = Bitmap.createBitmap(size, size, newSource.config)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squareBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val r: Float = size / 2f
        canvas.drawCircle(r, r, r, paint)

        squareBitmap.recycle()
        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}