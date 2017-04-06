package rokid.opengl.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by fanfeng on 2017/3/29.
 */

public class TextResourceReader {


    public static String readShaderFromRes(Context context, int resouseId){
        StringBuilder sb = new StringBuilder();

        try {

            InputStream inputStream = context.getResources().openRawResource(resouseId);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null){
                sb.append(nextLine);
                sb.append("\n");
            }


        }catch (IOException e){
            e.printStackTrace();
        }

        return sb.toString();
    }


    public static String readShaderFromRaw(Context context, String filePath){

        AssetManager assetManager = context.getAssets();

        StringBuilder sb = new StringBuilder();

        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;

        try {

            inputStream = assetManager.open(filePath);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine ;

            while ((nextLine = bufferedReader.readLine())!=null){
                sb.append(nextLine);
                sb.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }




}
