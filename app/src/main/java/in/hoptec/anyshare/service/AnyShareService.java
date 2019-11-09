package in.hoptec.anyshare.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AnyShareService extends Service {
    public AnyShareService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
