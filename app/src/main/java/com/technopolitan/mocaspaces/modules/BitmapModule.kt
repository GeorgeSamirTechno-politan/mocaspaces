//package com.technopolitan.mocaspaces.modules
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.view.View
//import dagger.Module
//import java.io.ByteArrayOutputStream
//import javax.inject.Inject
//
//@Module
//class BitmapModule
////@Inject constructor(private var context: Context)
//{
//
//    private lateinit var callBack: (bitmap: Bitmap) -> Unit
//    private lateinit var path: String
//    private lateinit var bitmap: Bitmap
//
//    fun init(path: String): BitmapModule {
//        this.path = path
//        return this
//    }
//
////    fun cropImage(width: Int, height: Int, pivotX: Flo): BitmapModule{
////        val bitmap = BitmapFactory.decodeFile(path)
//////        val xOffset = (width - side) /2
//////        val yOffset = (height - side)/2
////        this.bitmap = Bitmap.createBitmap(
////            bitmap, // source bitmap
////            xOffset, // x coordinate of the first pixel in source
////            yOffset, // y coordinate of the first pixel in source
////            side, // width
////            side // height
////        )
////        return this
////    }
//
//    private fun cropImage(bitmap: Bitmap, frame: View, reference: View) {
//        val heightOriginal = frame.height
//        val widthOriginal = frame.width
//        val heightFrame = reference.height
//        val widthFrame = reference.width
//        val leftFrame = reference.left
//        val topFrame = reference.top
//        val heightReal = bitmap.height
//        val widthReal = bitmap.width
//        val widthFinal = widthFrame * widthReal / widthOriginal
//        val heightFinal = heightFrame * heightReal / heightOriginal
//        val leftFinal = leftFrame * widthReal / widthOriginal
//        val topFinal = topFrame * heightReal / heightOriginal
//        val bitmapFinal = Bitmap.createBitmap(
//            bitmap,
//            leftFinal, topFinal, widthFinal, heightFinal
//        )
//        val stream = ByteArrayOutputStream()
//        bitmapFinal.compress(
//            Bitmap.CompressFormat.JPEG,
//            100,
//            stream
//        ) //100 is the best quality possibe
//        return stream.toByteArray()
//    }
//
//
//    fun build(callBack: (bitmap: Bitmap) -> Unit): BitmapModule {
//        this.callBack
//        return this
//    }
//}