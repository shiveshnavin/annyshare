package in.hoptec.anyshare;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.FirebaseApp;
import in.hoptec.anyshare.model.GenricUser;
import com.rollbar.android.Rollbar;


/**
 * Created by shivesh on 2/8/18.
 */

public class App extends Application {

    public static GoogleSignInClient mGoogleApiClient;
    private static GenricUser userModel;
    public static Context mContext;

    public static GenricUser getGenricUser() {
        if(userModel==null)
        {
            userModel=utl.readUserData();
        }
        return userModel;
    }

    public static void setGenricUser(GenricUser userModel) {
        App.userModel = userModel;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        mContext=this;

        Rollbar.init(this);



        utl.init(this);

    }

    public static Context getAppContext()
    {
        return mContext;
    }


}
