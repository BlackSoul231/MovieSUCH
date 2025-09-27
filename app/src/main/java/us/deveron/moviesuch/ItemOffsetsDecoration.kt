package us.deveron.moviesuch

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import androidx.compose.ui.graphics.Paint
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetsDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    @Deprecated("Deprecated in Java")
    val image: Bitmap? = context.getDrawable(R.drawable.rocketa)?.toBitmap()
    override fun getItemOffsets(
        outRect: Rect,
        itemPosition: Int,
        parent: RecyclerView,
    ) {
        super.getItemOffsets(outRect, itemPosition, parent)
        val hSide = 120
        val vSide = 120
        outRect.set(hSide, vSide, hSide, vSide)
    }
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val paint: android.graphics.Paint = Paint() as android.graphics.Paint // Создание кисти
        paint.color = context.getColor(R.color.purple_royal) // Установка цвета
        paint.strokeWidth = 5F; // Размер кисти 5px
        // Рисуем линию из точки 0 0 в точку 100 100
        c.drawLine(0f, 0f, 100f, 100f, paint)

        image?.let {
            btm ->
                c.drawBitmap(
                    btm,
                    Rect(0, 0, btm.width, btm.height),
                    Rect(0, 0, btm.width, btm.height),
                    null)
        }
    }
}