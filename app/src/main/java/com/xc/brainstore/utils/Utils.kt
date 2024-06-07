package com.xc.brainstore.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Feature Search Product By Taking Product Image Using Camera

private const val MAXIMAL_SIZE = 1000000
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
//
//fun getImageUri(context: Context): Uri {
//    var uri: Uri? = null
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//        val contentValues = ContentValues().apply {
//            put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
//            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
//            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
//        }
//        uri = context.contentResolver.insert(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            contentValues
//        )
//    }
//    return uri ?: getImageUriForPreQ(context)
//}
//
//private fun getImageUriForPreQ(context: Context): Uri {
//    val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//    val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
//    if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
//    return FileProvider.getUriForFile(
//        context,
//        "com.xc.storyapp.provider",
//        imageFile
//    )
//}
//
fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}
//
fun uriToFile(imageUri: Uri, context: Context): File {
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun formatRupiah(number: String): String {
    val localeID = Locale("in", "ID")
    val numberString = number.replace("[.,]".toRegex(), "")
    Log.d("format price", numberString)
    val parsedNumber = numberString.toDoubleOrNull()
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    formatRupiah.maximumFractionDigits = 0
    return formatRupiah.format(parsedNumber)
}

//fun formatRupiah(number: Double): String {
//    val formatRupiah = NumberFormat.getCurrencyInstance()
//    formatRupiah.maximumFractionDigits = 0
//    return formatRupiah.format(number)
//}

//fun File.reduceFileImage(): File {
//    val file = this
//    val bitmap = BitmapFactory.decodeFile(file.path).getRotatedBitmap(file)
//    var compressQuality = 100
//    var streamLength: Int
//    do {
//        val bmpStream = ByteArrayOutputStream()
//        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
//        val bmpPicByteArray = bmpStream.toByteArray()
//        streamLength = bmpPicByteArray.size
//        compressQuality -= 5
//    } while (streamLength > MAXIMAL_SIZE)
//    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
//    return file
//}
//
//fun Bitmap.getRotatedBitmap(file: File): Bitmap? {
//    val orientation = ExifInterface(file).getAttributeInt(
//        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
//    )
//    return when (orientation) {
//        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90F)
//        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180F)
//        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(this, 270F)
//        ExifInterface.ORIENTATION_NORMAL -> this
//        else -> this
//    }
//}
//
//fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
//    val matrix = Matrix()
//    matrix.postRotate(angle)
//    return Bitmap.createBitmap(
//        source, 0, 0, source.width, source.height, matrix, true
//    )
//}