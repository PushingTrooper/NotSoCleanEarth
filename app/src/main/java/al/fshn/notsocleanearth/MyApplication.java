package al.fshn.notsocleanearth;

import android.app.Application;

import al.fshn.notsocleanearth.data.AppContainer;

// Custom Application class that needs to be specified
// in the AndroidManifest.xml file
public class MyApplication extends Application {

    // Instance of AppContainer that will be used by all the Activities of the app
    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer(getApplicationContext());
    }
}

