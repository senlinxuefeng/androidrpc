package love.com.studyaidldemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static love.com.studyaidldemo.ConstantBean.TAG;

/**
 * Created by love on 2018/7/21.
 */

public class RemoteAidlService extends Service {

    private IBinder iBinder = new RemotePersonAidl.Stub() {

        @Override
        public void addPerson(PersonBean book) throws RemoteException {
            personBeanList.add(book);
            Log.i(TAG, "aidl service receive PersonBean:" + book.getName() + " : " + book.getAge());
        }

        @Override
        public List<PersonBean> getPerson() throws RemoteException {

            return personBeanList;
        }
    };
    private List<PersonBean> personBeanList;

    @Override
    public void onCreate() {
        super.onCreate();
        personBeanList = new ArrayList<>();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
}
