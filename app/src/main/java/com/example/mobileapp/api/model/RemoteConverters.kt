package com.example.mobileapp.api.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class RemoteConverters {
    companion object {
        private const val CHARSET_UTF8 = "UTF-8"
        private const val MAX_IMAGE_SIZE = 1024 // Размер в килобайтах, который вы хотите использовать

        fun fromBitmap(bitmap: Bitmap): String {
            val outputStream = ByteArrayOutputStream()

            // Сжимаем изображение до указанного максимального размера
            val quality = calculateQuality(bitmap)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

            val byteArray = outputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.NO_WRAP)
        }

        fun toBitmap(base64String: String): Bitmap {
            val byteArray = Base64.decode(base64String, Base64.NO_WRAP)
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }

        private fun calculateQuality(bitmap: Bitmap): Int {
            val outputStream = ByteArrayOutputStream()
            /*bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val initialSize = outputStream.size()*/

            // Уменьшаем качество изображения, пока его размер не станет меньше максимального
            var quality = 100
            while (outputStream.size() / 1024 > MAX_IMAGE_SIZE && quality > 0) {
                outputStream.reset()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                quality -= 10
            }

            // Возвращаем качество, при котором размер изображения удовлетворяет ограничению
            return if (quality < 0) 0 else quality
        }
    }
}
