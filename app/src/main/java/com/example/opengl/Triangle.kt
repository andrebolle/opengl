package com.example.opengl

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

// number of coordinates per vertex in this array
const val COORDINATES_PER_VERTEX = 3
var triangleCoordinates = floatArrayOf(     // in counterclockwise order:
    0.0f, 0.622008459f, 0.0f,      // top
    -0.5f, -0.311004243f, 0.0f,    // bottom left
    0.5f, -0.311004243f, 0.0f      // bottom right
)


class Triangle {

    // Set color with red, green, blue and alpha (opacity) values
    private val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    private var vertexBuffer: FloatBuffer =
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer.allocateDirect(triangleCoordinates.size * 4).run {
            // use the device hardware's native byte order
            order(ByteOrder.nativeOrder())

            // create a floating point buffer from the ByteBuffer
            asFloatBuffer().apply {
                // add the coordinates to the FloatBuffer
                put(triangleCoordinates)
                // set the buffer to read the first coordinate
                position(0)
            }
        }

    // attribute and varying are deprecated since glsl 1.3 and were removed in glsl 1.4:
    // Use of the keywords attribute and varying. Use in and out instead.
    private val vertexShaderCode =
//        "in vec4 vPosition;" +
        "attribute vec4 vPosition;" +
//        "layout (location = 0) in vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = vPosition;" +
                "}"

    private val fragmentShaderCode =
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"

    private var mProgram: Int

    private fun loadShader(type: Int, shaderCode: String): Int {

        // create a vertex shader type (GL_VERTEX_SHADER)
        // or a fragment shader type (GL_FRAGMENT_SHADER)
        return GLES20.glCreateShader(type).also { shader ->

            // add the source code to the shader and compile it
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
        }
    }

    init {

        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram().also {

            // add the vertex shader to program
            GLES20.glAttachShader(it, vertexShader)

            // add the fragment shader to program
            GLES20.glAttachShader(it, fragmentShader)

            // creates OpenGL ES program executables
            GLES20.glLinkProgram(it)
        }
    }

    private var positionHandle: Int = 0
    private var colHandle: Int = 0
    private var mColorHandle: Int = 0

    private val vertexCount: Int = triangleCoordinates.size / COORDINATES_PER_VERTEX
    private val vertexStride: Int = COORDINATES_PER_VERTEX * 4 // 4 bytes per vertex

    fun draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)

//        // get handle to vertex shader's vPosition member
//        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {
//
//            // Enable a handle to the triangle vertices
//            GLES20.glEnableVertexAttribArray(it)
//
//            // Prepare the triangle coordinate data
//            GLES20.glVertexAttribPointer(
//                it,
//                COORDINATES_PER_VERTEX,
//                GLES20.GL_FLOAT,
//                false,
//                vertexStride,
//                vertexBuffer
//            )
//
//
//            // get handle to fragment shader's vColor member
//            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
//
//                // Set color for drawing the triangle
//                GLES20.glUniform4fv(colorHandle, 1, color, 0)
//            }
//
//            // Draw the triangle
//            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
//
//            // Disable vertex array
//            GLES20.glDisableVertexAttribArray(it)
//        }

        // ------------ Position Attribute ------------------
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")

        println("Position Handle $positionHandle")

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle)

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
            positionHandle,
            COORDINATES_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer
        )

        // ------------ Colour Attribute ------------------
        colHandle = GLES20.glGetAttribLocation(mProgram, "vCol")

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(colHandle)

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
            colHandle,
            COORDINATES_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer
        )


        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->

            // Set color for drawing the triangle
            GLES20.glUniform4fv(colorHandle, 1, color, 0)
        }

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)

    }
}