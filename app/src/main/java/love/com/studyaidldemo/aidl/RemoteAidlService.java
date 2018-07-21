package love.com.studyaidldemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import static love.com.studyaidldemo.ConstantBean.TAG;

/**
 * Created by love on 2018/7/21.
 */

public class RemoteAidlService extends Service {

    private static final int CLIENT_CONNECT_REQUEST = 100;


    Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CLIENT_CONNECT_REQUEST:
                    Log.i(TAG, "messenger service onServiceConnected");
                    break;


            }
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
