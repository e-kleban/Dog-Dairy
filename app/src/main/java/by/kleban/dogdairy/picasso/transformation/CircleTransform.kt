package by.kleban.dogdairy.picasso.transformation

import android.graphics.*
import com.squareup.picasso.Transformation


class CircleTransform : Transformation {
    override fun transform(source: Bitmap?): Bitmap {
        source as Bitmap

        val size: Int = Math.min(source.width, source.height)

        val x: Int = (source.width - size) / 2
        val y: Int = (source.height - size) / 2

        val squareBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squareBitmap != source) {
            source.recycle()
        }
        val bitmap = Bitmap.createBitmap(size, size, source.config)
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