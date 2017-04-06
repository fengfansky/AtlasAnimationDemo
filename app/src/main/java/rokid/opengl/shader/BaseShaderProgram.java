package rokid.opengl.shader;

import android.util.Log;

import static android.opengl.GLES20.*;

import rokid.opengl.util.LoggerConfig;

/**
 * Created by fanfeng on 2017/3/29.
 */

public abstract class BaseShaderProgram {


    private static final String TAG = "BaseShaderProgram";

    int program;

    BaseShaderProgram(String vertexShaderSource, String fragmentShaderSource) {

        program = buildProgram(vertexShaderSource,fragmentShaderSource);

    }

    int buildProgram(String vertexShaderSource, String fragmentShaderSource){

        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        int program = linkShader(vertexShader, fragmentShader);

        if (LoggerConfig.ON){
            validateProgram(program);
        }

        return program;

    }

    abstract int compileShader(int shaderType,String shaderSource);

    abstract int linkShader(int vertexShader, int fragmentShader );

    abstract boolean validateProgram(int programObjId);



    int compileVertexShader(String shaderSource){
        return compileShader(GL_VERTEX_SHADER, shaderSource);
    }

    int compileFragmentShader(String shaderSource){
        return compileShader(GL_FRAGMENT_SHADER, shaderSource);
    }


    public static class ShaderProgram extends BaseShaderProgram {



        public ShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
            super(vertexShaderSource, fragmentShaderSource);
            genShaderLocation();
        }

        @Override
        int compileShader(int shaderType, String shaderSource) {

            int shaderObjectId = glCreateShader(shaderType);

            if (shaderObjectId == 0) {
                if (LoggerConfig.ON) {
                    Log.w(TAG, "Could not create new shader.");
                }

                return 0;
            }

            glShaderSource(shaderObjectId,shaderSource);

            glCompileShader(shaderObjectId);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS,
                    compileStatus, 0);

            if (LoggerConfig.ON) {
                // Print the shader info log to the Android log output.
                Log.v(TAG, "Results of compiling source:" + "\n" + shaderObjectId
                        + "\n:" + glGetShaderInfoLog(shaderObjectId));
            }

            // Verify the compile status.
            if (compileStatus[0] == 0) {
                // If it failed, delete the shader object.
                glDeleteShader(shaderObjectId);

                if (LoggerConfig.ON) {
                    Log.w(TAG, "Compilation of shader failed.");
                }

                return 0;
            }

            return shaderObjectId;
        }

        @Override
        int linkShader(int vertexShader, int fragmentShader) {

            int programObjId = glCreateProgram();

            if (programObjId == 0) {
                if (LoggerConfig.ON) {
                    Log.w(TAG, "Could not create new program");
                }

                return 0;
            }

            glAttachShader(programObjId, vertexShader);

            glAttachShader(programObjId, fragmentShader);

            glLinkProgram(programObjId);

            // Get the link status.
            final int[] linkStatus = new int[1];
            glGetProgramiv(programObjId, GL_LINK_STATUS,
                    linkStatus, 0);

            if (LoggerConfig.ON) {
                // Print the program info log to the Android log output.
                Log.v(
                        TAG,
                        "Results of linking program:\n"
                                + glGetProgramInfoLog(programObjId));
            }

            // Verify the link status.
            if (linkStatus[0] == 0) {
                // If it failed, delete the program object.
                glDeleteProgram(programObjId);

                if (LoggerConfig.ON) {
                    Log.w(TAG, "Linking of program failed.");
                }

                return 0;
            }

            return programObjId;
        }

        @Override
        boolean validateProgram(int programObjId){
            glValidateProgram(programObjId);
            final int[] validateStatus = new int[1];
            glGetProgramiv(programObjId, GL_VALIDATE_STATUS,
                    validateStatus, 0);
            Log.v(TAG, "Results of validating program: " + validateStatus[0]
                    + "\nLog:" + glGetProgramInfoLog(programObjId));

            return validateStatus[0] != 0;
        }

        public void genShaderLocation(){

        }

        public void useProgram(){
            glUseProgram(program);
        }
    }

}
