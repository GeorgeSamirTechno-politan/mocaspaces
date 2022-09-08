//package com.technopolitan.mocaspaces.modules
//
//import android.graphics.Bitmap
//import android.graphics.PointF
//import com.google.android.gms.vision.face.Landmark
//import com.google.mlkit.vision.face.Face
//import dagger.Module
//import kotlin.math.sqrt
//
//@Module
//class CropFaceModule {
//
//    private lateinit var face: Face
//    private lateinit var bitmap: Bitmap
////    private lateinit var cropAlgorithm: CropAlgorithm
//    fun init(face: Face, bitmap: Bitmap, cropAlgorithm: CropAlgorithm) : Bitmap{
//        this.face = face
//        this.bitmap = bitmap
//        this.cropAlgorithm = cropAlgorithm
//        return cropFace(face, bitmap, cropAlgorithm)!!.first
//    }
//
//
//    private fun cropFace(
//        face: Face,
//        bitmap: Bitmap,
//        cropAlgorithm: CropAlgorithm
//    ): Pair<Bitmap, Double>? {
//        if (cropAlgorithm == CropAlgorithm.LEAST) {
//            return cropFaceByLeastAlgorithm(face, bitmap)
//        } else {
//            val eyeDistance = getPixelBetweenEyes(face)
//            val width: Float
//            val height: Float
//            if (cropAlgorithm == CropAlgorithm.SQUARE) {
//                val tempWidth = (eyeDistance / 0.30).toFloat()
//                height = (tempWidth / 0.75).toFloat()
//                width = height
//            } else {
//                width = (eyeDistance / 0.25).toFloat()
//                height = (width / 0.75).toFloat()
//            }
//
//            val leftEyePosition: PointF = getLandmarkPosition(face, Landmark.LEFT_EYE)!!
//            val rightEyePosition: PointF = getLandmarkPosition(face, Landmark.RIGHT_EYE)!!
//            val eyeMidPoint: PointF =
//                midpoint(
//                    leftEyePosition.x,
//                    rightEyePosition.x,
//                    leftEyePosition.y,
//                    rightEyePosition.y
//                )
//
//            var faceStartX = (eyeMidPoint.x - width / 2).toInt()
//            faceStartX = faceStartX.coerceAtLeast(0)
//            val upperHeightRatio = if (cropAlgorithm == CropAlgorithm.THREE_BY_FOUR) 0.6 else 0.5
//            val faceUpperHeight = (upperHeightRatio * width).toInt() + 1
//            var faceStartY = (eyeMidPoint.y - faceUpperHeight).toInt()
//            faceStartY = faceStartY.coerceAtLeast(0)
//
//
//            //finding image coordinates for final bitmap
//            var finalStartX = faceStartX
//            var finalWidth = width.toInt()
//            var finalStartY = faceStartY
//            var finalHeight = height.toInt()
//
//            if (finalStartY + finalHeight > bitmap.height) {
//                val excessHeight: Int = finalStartY + finalHeight - bitmap.height
//                finalHeight -= excessHeight
//
//                //if input image doesn't have require height to make cropped bitmap square, reducing result image width
//                if (cropAlgorithm == CropAlgorithm.SQUARE) {
//                    finalStartX += (excessHeight / 2)
//                    finalWidth -= excessHeight
//                } else {
//                    val newWidth = finalHeight * 0.75
//                    val weightDiff = finalHeight - newWidth
//                    finalStartX += (weightDiff / 2).toInt()
//                    finalWidth = newWidth.toInt()
//                }
//            }
//
//            if (finalStartX + finalWidth > bitmap.width) {
//                val excessWidth: Int = finalStartX + finalWidth - bitmap.width
//                finalWidth -= excessWidth
//
//                //if input image doesn't have require width to make cropped bitmap square, reducing result image height
//                if (cropAlgorithm == CropAlgorithm.SQUARE) {
//                    finalStartY += (excessWidth / 2)
//                    finalHeight -= excessWidth
//                } else {
//                    val newHeight = finalWidth / 0.75
//                    val heightDiff = finalHeight - newHeight
//                    finalStartY += (heightDiff / 2).toInt()
//                    finalHeight = newHeight.toInt()
//                }
//            }
//
//            //converting width,height to multiple of 8
//            val widthRemainder = finalWidth % 8
//            val heightRemainder = finalHeight % 8
//            if (widthRemainder != 0) {
//                finalWidth -= widthRemainder
//            }
//            if (heightRemainder != 0) {
//                finalHeight -= heightRemainder
//            }
//
//            try {
//                val croppedBitmap =
//                    Bitmap.createBitmap(bitmap, finalStartX, finalStartY, finalWidth, finalHeight)
//                return Pair(croppedBitmap, eyeDistance)
//            } catch (e: IllegalArgumentException) {
//             e.printStackTrace()
//            }
//            return null
//        }
//    }
//
//    private fun cropFaceByLeastAlgorithm(
//        face: Face,
//        bitmap: Bitmap
//    ): Pair<Bitmap, Double>? {
//        val eyeDistance = getPixelBetweenEyes(face)
//
//        val heightTopOffset = (eyeDistance / 2).toInt()
//        val heightBottomOffset = heightTopOffset / 2
//        var startX = face.boundingBox.left
//        var startY = face.boundingBox.top - heightTopOffset
//        var width = face.boundingBox.width()
//        var height = face.boundingBox.height() + heightTopOffset + heightBottomOffset
//        if (startX < 0) {
//            startX = 0
//        }
//        if (startY < 0) {
//            startY = 0
//        }
//
//        if ((startY + height) > bitmap.height) {
//            height = bitmap.height - startY
//        }
//
//        if ((startX + width) > bitmap.width) {
//            width = bitmap.width - startX
//        }
//
//        //converting width,height to multiple of 8
//        val widthRemainder = width % 8
//        val heightRemainder = height % 8
//        if (widthRemainder != 0) {
//            width -= widthRemainder
//        }
//        if (heightRemainder != 0) {
//            height -= heightRemainder
//        }
//
//
//
//        try {
//            val croppedBitmap = Bitmap.createBitmap(
//                bitmap,
//                startX,
//                startY,
//                width,
//                height
//            )
//            return Pair(croppedBitmap, eyeDistance)
//        } catch (e: IllegalArgumentException) {
//           e.printStackTrace()
//        }
//        return null
//    }
//
//    private fun getPixelBetweenEyes(face: Face): Double {
//        val leftEyePosition: PointF = getLandmarkPosition(face, Landmark.LEFT_EYE)!!
//        val rightEyePosition: PointF = getLandmarkPosition(face, Landmark.RIGHT_EYE)!!
//        return calculateDistanceBetweenPoints(
//            leftEyePosition.x.toDouble(),
//            leftEyePosition.y.toDouble(),
//            rightEyePosition.x.toDouble(),
//            rightEyePosition.y.toDouble()
//        )
//    }
//
//    private fun getLandmarkPosition(
//        face: Face,
//        landmarkId: Int
//    ): PointF? {
//        return face.getLandmark(landmarkId)?.position
//    }
//
//    private fun calculateDistanceBetweenPoints(
//        x1: Double,
//        y1: Double,
//        x2: Double,
//        y2: Double
//    ): Double {
//        return sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))
//    }
//
//    private fun midpoint(
//        x1: Float, x2: Float,
//        y1: Float, y2: Float
//    ): PointF {
//        return PointF((x1 + x2) / 2, (y1 + y2) / 2)
//    }
//
//    private fun eulerValueToAngle(value: Float): Float = if (value < 0) {
//        value + 360
//    } else value
//}