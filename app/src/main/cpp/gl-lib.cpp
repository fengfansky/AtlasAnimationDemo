//
// Created by Fan Feng on 2017/3/29.
//

#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <GLES2/gl2.h>
#include <EGL/egl.h>
#include <stdio.h>
#define  LOG_TAG    "libgl2jni"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
namespace gllib {
    static void printGLString(const char *name, GLenum s) {
        const char *v = (const char *) glGetString(s);
        LOGI("GL %s = %s\n", name, v);
    }

    static void checkGLError(const char *op) {
        for (GLint error = glGetError(); error; error
                                                        = glGetError()) {
            LOGI("after %s() glError (0x%x)\n", op, error);
        }
    }


    /**
     * this code test gl();
     */


    const int POSITION_COMPONENT_COUNT = 2;
    const int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    const int BYTES_PER_FLOAT = 4;

    const int STRIDE = (POSITION_COMPONENT_COUNT
                        + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;


    const GLfloat vertices[] = {
            // Order of coordinates: X, Y, S, T
            // Triangle Fan
            0.0f, 0.0f,     0.5f, 0.5f,
            -0.5f, -0.8f,   0.0f, 0.9f,
            0.5f, -0.8f,    1.0f, 0.9f,
            0.5f, 0.8f,     1.0f, 0.1f,
            -0.5f, 0.8f,    0.0f, 0.1f,
            -0.5f, -0.8f,   0.0f, 0.9f
    };

    GLuint VBO, VAO, EBO;


    void init() {

        glClearColor(0.5f, 0.5f, 0.5f, 0.5f);



        /* glGenVertexArrays(1, &VAO);
         glGenBuffers(1, &VBO);
         glGenBuffers(1, &EBO);

         glBindVertexArray(VAO);

         glBindBuffer(GL_ARRAY_BUFFER, VBO);
         glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

         glBindBuffer(GL_ARRAY_BUFFER, EBO);
         glBufferData(GL_ARRAY_BUFFER, )*/
    }


    void resize(int width, int height) {

        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);


    }

    void frame() {

        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

    }

    void bindBuffer() {


    }


    int genTexture(jobject bitmap) {

    }

    bool uploadTexture(JNIEnv *env, jobject obj, jobject graphicBuffer, jobject bitmapHandle) {



    }


    extern "C" {


    JNIEXPORT void JNICALL Java_rokid_opengl_jnilib_GLJNILib_init(JNIEnv *env, jobject obj) {
        init();
    }

    JNIEXPORT void JNICALL
    Java_rokid_opengl_jnilib_GLJNILib_resize(JNIEnv *env, jobject obj, jint width, jint height) {
        resize(width, height);
    }

    JNIEXPORT void JNICALL Java_rokid_opengl_jnilib_GLJNILib_frame(JNIEnv *env, jobject obj) {
        frame();
    }

    JNIEXPORT jint JNICALL Java_rokid_opengl_jnilib_GLJNILib_genTexture(JNIEnv *env, jobject obj,
                                                                        jobject bitmap) {
        genTexture(bitmap);
    }

    JNIEXPORT jboolean JNICALL
    Java_rokid_opengl_jnilib_GLJNILib_uploadTexture(JNIEnv *env, jobject obj, jobject graphicBuffer,
                                                    jobject bitmapHandle) {
        uploadTexture(env, obj, graphicBuffer, bitmapHandle);
    }

    JNIEXPORT jint JNICALL Java_rokid_opengl_jnilib_GLJNILib_initBuffer(JNIEnv *env, jobject obj) {
        bindBuffer();
    }
    }
}