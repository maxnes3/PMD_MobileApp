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

            val extendedBitmap = scaleRatioBitmap(bitmap)

            // Сжимаем изображение до указанного максимального размера
            val quality = calculateQuality(extendedBitmap)
            extendedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

            val byteArray = outputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.NO_WRAP)
        }

        fun toBitmap(base64String: String): Bitmap {
            val byteArray = Base64.decode(base64String, Base64.NO_WRAP)
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }

        private fun calculateQuality(bitmap: Bitmap): Int {
            val outputStream = ByteArrayOutputStream()

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

        private fun scaleRatioBitmap(bitmap: Bitmap): Bitmap {
            val maxWidth = 990
            val maxHeight = 990
            if (bitmap.width > maxWidth || bitmap.height > maxHeight) {
                // Если размер превышает максимальный, масштабируем изображение
                val ratio = Math.min(maxWidth.toFloat() / bitmap.width,
                    maxHeight.toFloat() / bitmap.height)

                val width = (ratio * bitmap.width).toInt()
                val height = (ratio * bitmap.height).toInt()

                return Bitmap.createScaledBitmap(bitmap, width, height, true)
            }
            return bitmap
        }
    }
}