package rokid.opengl;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    MyGLSurfaceView myGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGLSurfaceView = new MyGLSurfaceView(getApplicationContext());
        setContentView(myGLSurfaceView);

    }


    @Override
    protected void onResume() {
        super.onResume();
        myGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myGLSurfaceView.onPause();
    }
}
