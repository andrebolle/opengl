package com.example.opengl

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log

class MyGLRenderer : GLSurfaceView.Renderer {
    private val _tag = "com.example.opengl.MyGLRenderer"
    private lateinit var shape: Shape2D

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private val vPMatrix = FloatArray(16) //FIXME Why am I here?
    private val projection = FloatArray(16) //FIXME The aspect ratio.
    private val viewMatrix = FloatArray(16)

    private fun glInfo() {

//        OpenGL Version	GLSL Version
//        2.0               1.10
//        2.1               1.20
//        3.0               1.30
//        3.1               1.40
//        3.2               1.50
        Log.i(_tag, GLES20.glGetString(GLES20.GL_VENDOR))
        Log.i(_tag, GLES20.glGetString(GLES20.GL_RENDERER))
        Log.i(_tag, GLES20.glGetString(GLES20.GL_VERSION))
        Log.i(_tag, GLES20.glGetString(GLES20.GL_SHADING_LANGUAGE_VERSION))


        val count = IntArray(1)
        GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_ATTRIBS, count, 0)
        Log.i(_tag, "${count[0]}")
    }

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {

        glInfo()

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f)

        // initialize a triangle
        shape = Shape2D(Meshes.polygon(400))
    }

    // Draw every frame
    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projection, 0, viewMatrix, 0)

        // Draw shape
        shape.draw(projection)
    }

    // Reset the viewport if the screen size changes, e.g. if it rotates.
    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        Matrix.orthoM(projection, 0, -width / 2f, width / 2f, -height / 2f, height / 2f, -1f, 1f)


    }
}