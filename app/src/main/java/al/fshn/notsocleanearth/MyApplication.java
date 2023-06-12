package al.fshn.notsocleanearth;

import android.app.Application;

import al.fshn.notsocleanearth.data.AppContainer;

// Custom Application class that needs to be specified
// in the AndroidManifest.xml file
public class MyApplication extends Application {

    // Instance of AppContainer that will be used by all the Activities of the app
    public AppContainer appContainer;
    public static final String OPEN_WEATHER_API_KEY = "6ab6daad78870fa415ccb37fbb33aa89";

    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer(getApplicationContext());
    }
}

