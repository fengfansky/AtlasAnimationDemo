package rokid.opengl.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

import static android.opengl.GLES10.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLUtils.texImage2D;

/**
 * Created by fanfeng on 2017/3/29.
 */

public class TextureHelper {


    public static TextureHelper getSingleInstance(){

        return Holder.textureHelper;
    }

    private static class Holder{
        private static  final TextureHelper textureHelper = new TextureHelper();
    }

    public static int loadTexture(Context context, int resourcesId){

        final int [] textureObjIds = new int[1];

        GLES20.glGenTextures(1, textureObjIds, 0);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resourcesId, options);

        if (bitmap == null) {
            if (LoggerConfig.ON) {
                Log.d("TextureHelper","bitmap is null");
            }

            glDeleteTextures(1, textureObjIds, 0);
            return 0;
        }

        glBindTexture(GL_TEXTURE_2D, textureObjIds[0]);

        // Set filtering: a default must be set, or the texture will be
        // black.
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        // Load the bitmap into the bound texture.
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        if (LoggerConfig.ON)
            Log.d("TextureHelper", "loadTexture bitmap: " + bitmap.getHeight() + " texture is " + textureObjIds[0]);

        // Note: Following code may cause an error to be reported in the
        // ADB log as follows: E/IMGSRV(20095): :0: HardwareMipGen:
        // Failed to generate texture mipmap levels (error=3)
        // No OpenGL error will be encountered (glGetError() will return
        // 0). If this happens, just squash the source image to be
        // square. It will look the same because of texture coordinates,
        // and mipmap generation will work.

        glGenerateMipmap(GL_TEXTURE_2D);

        // Recycle the bitmap, since its data has been loaded into
        // OpenGL.
        bitmap.recycle();

        // Unbind from the texture.
        glBindTexture(GL_TEXTURE_2D, 0);

        return textureObjIds[0];

    }

}
