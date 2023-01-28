package com.example.opengl

class Meshes {
    // Dictionary cache of polygons
    companion object {
//        private val cache = mutableMapOf<Int, FloatArray>()
//        fun polygon(n: Int): FloatArray {
//            if (n in cache) return cache[n]!!
//            val vertexCount = n + 2
//            val vertexSize = 3
//            val result = FloatArray(vertexCount * vertexSize)
//            val centerIndex = 0
//            val xIndex = centerIndex + 1
//            val yIndex = xIndex + 1
//            val zIndex = yIndex + 1
//            result[centerIndex] = 0f
//            result[xIndex] = 0f
//            result[yIndex] = 0f
//            result[zIndex] = 0f
//            for (i in 0 until n) {
//                val angle = (2 * Math.PI * i) / n
//                val currentIndex = (i + 1) * vertexSize
//                result[currentIndex + xIndex] = cos(angle).toFloat()
//                result[currentIndex + yIndex] = sin(angle).toFloat()
//                result[currentIndex + zIndex] = 0f
//            }
//            val lastIndex = vertexCount * vertexSize
//            result[lastIndex + xIndex] = cos(0.0).toFloat()
//            result[lastIndex + yIndex] = sin(0.0).toFloat()
//            result[lastIndex + zIndex] = 0f
//            cache[n] = result
//            return result
//        }


        private val cache = mutableMapOf<Int, FloatArray>()
        fun polygon(n: Int): FloatArray {
            if (n in cache) return cache[n]!!
            val result = FloatArray(3 * (n + 2))
            result[0] = 0f
            result[1] = 0f
            result[2] = 0f
            for (i in 0 until n) {
                val angle = (2 * Math.PI * i) / n
                result[3 * i + 3] = Math.cos(angle).toFloat()
                result[3 * i + 4] = Math.sin(angle).toFloat()
                result[3 * i + 5] = 0f
            }
            result[3 * n + 3] = Math.cos(0.0).toFloat()
            result[3 * n + 4] = Math.sin(0.0).toFloat()
            result[3 * n + 5] = 0f
            cache[n] = result
            return result
        }





    }
}