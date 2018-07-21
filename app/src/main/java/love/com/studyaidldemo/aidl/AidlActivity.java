package love.com.studyaidldemo.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import love.com.studyaidldemo.R;

import static love.com.studyaidldemo.ConstantBean.TAG;

public class AidlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        bindMessengerService();
    }


    private void bindMessengerService() {
        ComponentName componentName = new ComponentName(getPackageName(), RemoteAidlService.class.getName());
        Intent intent = new Intent();
        intent.setComponent(componentName);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "messenger service onServiceConnected");

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "messenger service onServiceDisconnected");


            }
        }, BIND_AUTO_CREATE);
    }
}
