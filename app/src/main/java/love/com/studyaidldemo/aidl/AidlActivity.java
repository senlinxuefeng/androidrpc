package love.com.studyaidldemo.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import love.com.studyaidldemo.R;

import static love.com.studyaidldemo.ConstantBean.TAG;

public class AidlActivity extends AppCompatActivity {

    private RemotePersonAidl remotePersonAidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        bindMessengerService();

        findViewById(R.id.sendToServer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonBean personBean = new PersonBean();
                personBean.setAge(12);
                personBean.setName("zhangsan");
                try {
                    remotePersonAidl.addPerson(personBean);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                try {
                    Log.i(TAG, "aidl service receive PersonBean:" + remotePersonAidl.getPerson().size());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void bindMessengerService() {
        ComponentName componentName = new ComponentName(getPackageName(), RemoteAidlService.class.getName());
        Intent intent = new Intent();
        intent.setComponent(componentName);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "messenger service onServiceConnected");
                remotePersonAidl = RemotePersonAidl.Stub.asInterface(service);

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "messenger service onServiceDisconnected");
                remotePersonAidl = null;

            }
        }, BIND_AUTO_CREATE);
    }
}
