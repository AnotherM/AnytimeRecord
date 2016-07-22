package m.another.anytimerecord;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;

public class PgySDK extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);

    }
}
