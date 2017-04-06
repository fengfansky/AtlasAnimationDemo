package rokid.opengl.jnilib;

import android.graphics.Bitmap;

import java.nio.FloatBuffer;

/**
 * Created by fanfeng on 2017/3/29.
 */

public class GLJNILib {

    static {
        System.loadLibrary("gl-lib");
    }

    public static native void init();
    public static native void resize(int widht,int height);
    public static native void frame();

    /**
     * Texture generate and upload
     */
    public static native int genTexture(Bitmap bitmap);
    public static native boolean uploadTexture();


    /**
     * VertexArray floatBuffer
     */
    public static native FloatBuffer[] initBuffer();

}
