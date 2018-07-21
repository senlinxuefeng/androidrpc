package love.com.studyaidldemo.messager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import love.com.studyaidldemo.ConstantBean;
import love.com.studyaidldemo.R;

import static love.com.studyaidldemo.ConstantBean.TAG;

public class MessengerActivity extends AppCompatActivity {

    Messenger clientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantBean.SERVER_SEND_MESSAGE_TO_CLIENT:
                    Log.i(TAG, msg.getData().getString("serverMsg", ""));
                    break;

            }
        }
    });


    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        bindMessengerService();
        findViewById(R.id.sendToServer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.what = ConstantBean.CLIENT_SEND_MESSAGE_TO_SEVER;
                Bundle bundle = new Bundle();
                bundle.putString("clientMsg", "收到来自客户端的消息……");
                message.setData(bundle);
                try {
                    messenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void registerClientListener() {
        Message message = Message.obtain();
        message.what = ConstantBean.REGISTER_MESSENGER_CLIENT_LISTENER;
        message.replyTo = clientMessenger;
        Bundle bundle = new Bundle();
        bundle.putString("name", "messengerClient");
        message.setData(bundle);
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindMessengerService() {
        ComponentName componentName = new ComponentName(getPackageName(), MessengerService.class.getName());
        Intent intent = new Intent();
        intent.setComponent(componentName);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "messenger service onServiceConnected");
                messenger = new Messenger(service);
                registerClientListener();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "messenger service onServiceDisconnected");
                messenger = null;

            }
        }, BIND_AUTO_CREATE);
    }
}
