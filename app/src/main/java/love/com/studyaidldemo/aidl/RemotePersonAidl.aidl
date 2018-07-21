package love.com.studyaidldemo.aidl;
import love.com.studyaidldemo.aidl.PersonBean;

interface RemotePersonAidl{
    void addPerson(in PersonBean book);
    List<PersonBean> getPerson();
}