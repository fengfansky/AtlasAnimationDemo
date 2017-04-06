package rokid.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rokid.opengl.graphics.Table;
import rokid.opengl.jnilib.GLJNILib;
import rokid.opengl.shader.TextureShaderProgram;
import rokid.opengl.util.MatrixHelper;
import rokid.opengl.util.TextResourceReader;
import rokid.opengl.util.TextureHelper;

/**
 * Created by fanfeng on 2017/3/29.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    public MyGLSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private  void init(Context context){
        setEGLConfigChooser(8, 8, 8, 0, 16, 0);
        setEGLContextClientVersion(2);
        setRenderer(new MyRender(context));
    }

    private static class MyRender implements Renderer{

        private Table table;
        private int texture;
        private TextureShaderProgram textureShaderProgram;
        private Context context;

        private String vsSource;
        private String fragSource;

        private final float[] projectionMatrix = new float[16];
        private final float[] modelMatrix = new float[16];

        public MyRender(Context context) {
            this.context = context;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

            Log.d(this.getClass().getName(), "onSurfaceCreated()");

            GLJNILib.init();

            table = new Table();

            vsSource = TextResourceReader.readShaderFromRaw(context,"shader/shader.vs");

            fragSource = TextResourceReader.readShaderFromRaw(context,"shader/shader.frag");

            textureShaderProgram = new TextureShaderProgram(vsSource,fragSource);

//            textureShaderProgram = new TextureShaderProgram(Constants.TEXTURE_VERTEX_SHADER_SOURCE,Constants.TEXTURE_FRAGMENT_SHADER_SOURCE);

            texture = TextureHelper.getSingleInstance().loadTexture(context, R.drawable.battery);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLJNILib.resize(width,height);
            final float aspectRadio = width > height ? (float)(width/height) : (float)(height/width);
            if (width>height){
                Matrix.orthoM(projectionMatrix, 0, -aspectRadio, aspectRadio, -1f, 1f, -1f, 1f);
            }else {
                Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -aspectRadio, aspectRadio, -1f, 1f);
            }
            Log.d(this.getClass().getName(), "onSurfaceChanged()");


            Matrix.perspectiveM(projectionMatrix, 0, 45, (float)width/(float)height, 1f, 10f);
            MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
                    / (float) height, 1f, 10f);
            Matrix.setIdentityM(modelMatrix, 0);

            Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f);
//            Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

            final float[] temp = new float[16];
            Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
            System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLJNILib.frame();

            // Draw the table.
            textureShaderProgram.useProgram();

            textureShaderProgram.setUniforms(projectionMatrix,texture);
            table.bindData(textureShaderProgram);
            table.draw();

        }
    }


}
