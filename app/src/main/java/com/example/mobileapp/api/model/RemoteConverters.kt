package com.example.mobileapp.api.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class RemoteConverters {
    companion object {
        private const val CHARSET_UTF8 = "UTF-8"

        fun fromBitmap(bitmap: Bitmap): String {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val byteArray = outputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.NO_WRAP)
        }

        fun toBitmap(base64String: String): Bitmap {
            val byteArray = Base64.decode(base64String, Base64.NO_WRAP)
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
    }
}