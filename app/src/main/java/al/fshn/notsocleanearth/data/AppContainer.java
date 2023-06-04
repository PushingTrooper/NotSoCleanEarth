package al.fshn.notsocleanearth.data;

import android.content.Context;

import androidx.room.Room;

// Container of objects shared across the whole app
public class AppContainer {
    private Context applicationContext;
    private AppDatabase appDatabase;
    public AppRepository userRepository;

    public AppContainer(Context applicationContext) {
        this.applicationContext = applicationContext;
        appDatabase = Room.databaseBuilder(applicationContext,
                AppDatabase.class, "clean-earth-database").build();
        userRepository = new AppRepository(appDatabase);
    }

    // Since you want to expose userRepository out of the container, you need to satisfy
    // its dependencies as you did before
    /*private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://example.com")
            .build()
            .create(LoginService.class);*/

//    private UserRemoteDataSource remoteDataSource = new UserRemoteDataSource(retrofit);



    // userRepository is not private; it'll be exposed

}

