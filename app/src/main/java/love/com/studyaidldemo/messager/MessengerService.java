package love.com.studyaidldemo.messager;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import love.com.studyaidldemo.ConstantBean;

import static love.com.studyaidldemo.ConstantBean.TAG;

/**
 * Created by love on 2018/7/21.
 */

public class MessengerService extends Service {


    HashMap<String, List<Messenger>> hashMap = new HashMap<>();


    Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantBean.REGISTER_MESSENGER_CLIENT_LISTENER:
                    Log.i(TAG, "注册回调客户端的监听器name：" + msg.getData().getString("name", ""));
                    List<Messenger> messengerList = hashMap.get(msg.getData().getString("name", ""));
                    if (messengerList == null) {
                        messengerList = new ArrayList<>();
                    }
                    messengerList.add(msg.replyTo);
                    final List<Messenger> finalMessengerList = messengerList;
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < finalMessengerList.size(); i++) {
                                Messenger tempMessenger = finalMessengerList.get(i);

                                Message tempMessage = Message.obtain();
                                tempMessage.what = ConstantBean.SERVER_SEND_MESSAGE_TO_CLIENT;
                                Bundle bundle = new Bundle();
                                bundle.putString("serverMsg", "收到来自服务端的消息……");
                                tempMessage.setData(bundle);
                                try {
                                    tempMessenger.send(tempMessage);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, 2000);
                    break;
                case ConstantBean.CLIENT_SEND_MESSAGE_TO_SEVER:
                    Log.i(TAG, msg.getData().getString("clientMsg", ""));
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
