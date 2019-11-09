package in.hoptec.anyshare.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rollbar.android.Rollbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import in.hoptec.anyshare.Constants;
import in.hoptec.anyshare.R;
import in.hoptec.anyshare.adapter.GenriXAdapter;
import in.hoptec.anyshare.model.GenricUser;
import in.hoptec.anyshare.service.AnyShareService;
import in.hoptec.anyshare.utils.DatabaseHelper;
import in.hoptec.anyshare.utils.FadePopup;
import in.hoptec.anyshare.utils.NetworkRequestCallback;
import in.hoptec.anyshare.utl;
import in.hoptec.anyshare.views.RoundedImageView;


/**
 * Created by shivesh on 7/1/18.
 */

public class BaseActivity  extends AppCompatActivity {

    public static String TAG = "BaseApp";

    public FirebaseAuth mAuth;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    public Context ctx;
    public Activity act;
    public DatabaseHelper databaseHelper;
    public FirebaseFirestore firestore;
    public CollectionReference groupsRef;
    androidx.appcompat.widget.Toolbar toolbar; TextView titl;
    public void setUpToolbar()
    {
        toolbar =   findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_ham);
        logo=findViewById(R.id.logo);

    }


    @Override
    public void setTitle(CharSequence title) {

        super.setTitle("");
        View tool_cont=findViewById(R.id.tool_cont);
        if(tool_cont!=null)
        {
            titl =tool_cont.findViewById(R.id.title);
            titl.setText(title);

        }



    }
    public RoundedImageView logo;

    public GenricUser user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);

        try {
            if(utl.readUserData()!=null){

             user=utl.readUserData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ctx=this;
        act=this;
        databaseHelper=new DatabaseHelper(getApplicationContext());
        firestore=FirebaseFirestore.getInstance();
        groupsRef =firestore.collection("groups");

    }

    public int getcolor(@ColorRes int r){
        return getResources().getColor(r);
    }

    public Drawable getdrawable(@DrawableRes int r){
        return getResources().getDrawable(r);
    }

    public String getstring(@StringRes int res)
    {


    return getResources().getString(res);

    }


    View loading;
    public View list;



    public void delete(String idType, String id, NetworkRequestCallback cb)
    {

        JSONObject jo = new JSONObject();
        try {
            jo.put(idType, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        callPost(Constants.HOST+Constants.DELETE,jo,false,cb);
    }


    public void showD()
    {

        if(utl.readUserData()==null)
            lastDialog= utl.diagInfo2(getApplicationContext(), "Juntos : In It Together . \n\nDeveloped By:\nShivesh Navin", "Visit Developer", R.drawable.ic_logo_inv, new utl.ClickCallBack() {
                @Override
                public void done(DialogInterface dialogInterface) {

                    Intent it=new Intent(Intent.ACTION_VIEW);
                    it.setData(Uri.parse("http://www.hoptech.in"));
                    startActivity(it);

                }
            });
        else
        {
            utl.toast(ctx,"Developer : Shivesh Navin , www.hoptech.in");
        }


    }

    private AnimationDrawable getProgressBarAnimation(){

        GradientDrawable rainbow1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW});

        GradientDrawable rainbow2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] { Color.YELLOW, Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN});

        GradientDrawable rainbow3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] { Color.GREEN, Color.YELLOW, Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN });

        GradientDrawable rainbow4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] { Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED, Color.MAGENTA, Color.BLUE });

        GradientDrawable rainbow5 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] { Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED, Color.MAGENTA });

        GradientDrawable rainbow6 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED });


        GradientDrawable[]  gds = new GradientDrawable[] {rainbow1, rainbow2, rainbow3, rainbow4, rainbow5, rainbow6};

        AnimationDrawable animation = new AnimationDrawable();

        for (GradientDrawable gd : gds){
            animation.addFrame(gd, 100);

        }

        animation.setOneShot(false);

        return animation;


    }



    public void loadStart()
    {
        load(true);
    }
    public void loadStop()
    {
        load(false);
    }
    public void load (boolean isloading ){


        if(loading==null || list==null)
            return;

       // utl.animate_avd(loading);
        if(isloading)
        {

            loading.setAlpha(1.0f) ;
            list.setVisibility(View.GONE);

        }

        else {

            loading.setAlpha(0.0f) ;
            list.setVisibility(View.VISIBLE);

        }

    };



    public void callGet(String url, final boolean showLoading, final NetworkRequestCallback call)
    {
        if(showLoading)
            loadStart();
        utl.e("CallGET",url);
        AndroidNetworking.get(url).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                if(utl.DEBUG_ENABLED)
                  utl.e("CallGET",response.toString());

                call.onSuccess(response);

                if(showLoading)
                    loadStop();
            }

            @Override
            public void onError(ANError anError) {
                if(showLoading)
                    loadStop();
                utl.e("CallGET",anError.getErrorDetail());
                utl.e("CallGET",anError.getErrorBody());
                call.onFail(anError);
            }
        });
    }


    public void callPost(String url, final boolean showLoading, final NetworkRequestCallback call) {

        callPost(url,new JSONObject(),showLoading,call);

    }


    public void callPost(String url,Object body, final boolean showLoading, final NetworkRequestCallback call) {

        try {
            callPost(url,new JSONObject(utl.js.toJson(body)),showLoading,call);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void callPost(String url,JSONObject body, final boolean showLoading, final NetworkRequestCallback call)
    {

        if(showLoading)
            loadStart();


        if(utl.DEBUG_ENABLED)
        {
            utl.e("CallPost",url);
            utl.e("CallPost",body.toString());
        }
        AndroidNetworking.post(url).addJSONObjectBody(body).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                if(utl.DEBUG_ENABLED)
                    utl.e("CallPost",response.toString());

                call.onSuccess(response);
                call.onSuccessString(response.toString());

                if(showLoading)
                    loadStop();
            }

            @Override
            public void onError(ANError anError) {
                if(showLoading)
                    loadStop();
                utl.e("CallPost",anError.getErrorDetail());
                utl.e("CallPost",anError.getErrorBody());
                call.onFail(anError);
            }
        });
    }


    public void callPostString(String url,JSONObject body, final boolean showLoading, final NetworkRequestCallback call)
    {

        if(showLoading)
            loadStart();


        if(utl.DEBUG_ENABLED)
        {
            utl.e("CallPost",url);
            utl.e("CallPost",body.toString());
        }
        AndroidNetworking.post(url).addJSONObjectBody(body).build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String  response) {
                if(utl.DEBUG_ENABLED)
                    utl.e("CallPost",response.toString());

                call.onSuccessString(response);

                if(showLoading)
                    loadStop();
            }

            @Override
            public void onError(ANError anError) {
                if(showLoading)
                    loadStop();
                utl.e("CallGET",anError.getErrorDetail());
                utl.e("CallGET",anError.getErrorBody());
                call.onFail(anError);
            }
        });
    }


    public void addPressReleaseAnimation(final View base)
    {

        final Animation press= AnimationUtils.loadAnimation(base.getContext(),R.anim.rec_zoom_in);
        final Animation release= AnimationUtils.loadAnimation(base.getContext(),R.anim.rec_zoom_nomal);

        base.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        base.startAnimation(press);
                        break;
                    case MotionEvent.ACTION_UP:

                        base.startAnimation(release);

                    case MotionEvent.ACTION_CANCEL:

                        base.startAnimation(release);



                        break;
                    default:
                        break;
                }



                return false;
            }
        });




    }



    public void startMyAccount()
    {

        Intent it=new Intent(ctx,MyAccount.class);
        it.putExtra("userid",user.getId());
        startActivity(it);
    }

    public void startHome(){


        user=utl.readUserData();
        Crashlytics.setUserName(user.getName());
        Crashlytics.setUserIdentifier(user.getId());
        user=utl.readUserData();

        if(user==null)
        {
            Rollbar.instance().error("No User Err");
        }
        else {

        }
           startActivity(new Intent(ctx,HomeActivity.class));

        finishAffinity();

    };

    public void restartService()
    {

        if(!utl.isMyServiceRunning(AnyShareService.class,this))
        {

        }
        else
        {
            stopService(new Intent(getApplicationContext(),AnyShareService.class));
        }
        startService(new Intent(getApplicationContext(),AnyShareService.class));;
    }


    public void getUser (String userId){

        load(true);

        JSONObject jo=new JSONObject();
        try {

            jo.put("id", userId);
            jo.put("password", userId);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        callPost(Constants.HOST + Constants.API_USER, jo, false, new NetworkRequestCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                load(false);
                utl.log(response);

                if(response.toString().contains("Insufficient Paramerters"))
                {

                    startActivity(new Intent(ctx,Signup.class));

                    finishAffinity();

                }
                else
                {

                    utl.writeUserData(utl.js.fromJson(response.toString(),GenricUser.class),ctx);
                    GenricUser user=utl.readUserData();
                    if(user!=null)
                    {
                        if(user.getStatus().contains("BLOCKED"))
                        {
                            lastDialog= utl.diagInfo2(ctx, "Hey ! " + user.getAlias() + " , We have detected " +
                                            "some inappropriate or malicious behaviour associated with your account . " +
                                            "To protect our other users we have temporarily disabled your account . Please be patient " +
                                            "while we review the matter and come to a conclusion .", "DISMISS", R.drawable.ic_report_black_24dp,
                                    new utl.ClickCallBack() {
                                        @Override
                                        public void done(DialogInterface dialogInterface) {

                                            finish();

                                        }
                                    });
                        }
                        else
                        {

                            startHome();
                        }
                    }

                }
            }

            @Override
            public void onSuccessString(String response) {

            }

            @Override
            public void onFail(ANError job) {
                load(false);


                if(utl.readUserData()!=null)
                {
                    lastDialog=  utl.diagInfo2(ctx, "Login Failed ! Looks like internet connection is not available at the moment or connection is too " +
                                    "poor . You can choose to continue offline however you will not be able to use " +
                                    "online features like chatting and accessing resources etc.", "Continue Offline", R.drawable.ic_no_wifi,
                            new utl.ClickCallBack() {
                                @Override
                                public void done(DialogInterface dialogInterface) {

                                    startHome();
                                }
                            });


                }
                else
                    utl.snack(act,"Login Failed !");

            }
        });


    };


    public void updateUser ( JSONObject jo){



        try {

            jo.put("id", user.getId());
            jo.put("password", user.getId());

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        callPost(Constants.HOST + Constants.API_USER, jo, false, new NetworkRequestCallback() {
            @Override
            public void onSuccess(JSONObject response) {


                utl.e("user Updated!");

            }

            @Override
            public void onSuccessString(String response) {

            }

            @Override
            public void onFail(ANError job) {


            }
        });


    };

    public void updateFcm(){


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        String token = task.getResult().getToken();
                        user.setFcmToken(token);
                        utl.writeUserData(user,ctx);
                        JSONObject jop=new JSONObject();
                        try{

                            jop.put("fcmToken",user.getFcmToken());

                        }catch(Exception e)
                        {}
                        updateUser(jop);

                    }
                });

    }

    public void setUpNotfIcon (boolean forceClear) {

        HomeActivity home=(HomeActivity)this;

        if(home.mMenu==null)
            return;

        try {
            MenuItem menuItem=home.mMenu.findItem(R.id.notif);
            if(menuItem==null)
                return;
            if(utl.NotificationMessage.getAll(ctx).size()>0 && !forceClear)
            {
                menuItem.setIcon(R.drawable.ic_notifications_black_24dp_dotted);
            }
            else
            {

                menuItem.setIcon(R.drawable.ic_notifications_black_24dp);
            }
        } catch (Exception e) {

        }


    }



    Menu mMenu;
    public void showDrawer ( ){




        View navIcon = toolbar.getChildAt(1);

        FadePopup popup=new FadePopup(this,navIcon,null);

        popup.popup();
        LinearLayout cont=popup.getContainer();

        PopupMenu p  = new PopupMenu(this, null);
        Menu menu = p.getMenu();
        getMenuInflater().inflate(R.menu.menu_drawer, menu);

        int pad=utl.pxFromDp(ctx,30).intValue();
        cont.setPadding(pad,pad,pad,pad);



        int i=0;
        while (i<menu.size())
        {
            MenuItem mi=menu.getItem(i++);

            final View row=getLayoutInflater().inflate(R.layout.row_menu,null);
            final TextView tv=row.findViewById(R.id.txt);
            tv.setId(R.id.txt+100);
            ImageView im=row.findViewById(R.id.img);
            row.setId(mi.getItemId());
            row.setTag(mi.getTitle());;


            tv.setText(mi.getTitle());
            im.setImageDrawable(mi.getIcon());

            row.setOnClickListener((v)->{

                utl.e("ID ",v.getId()+" tag "+v.getTag());
                Intent mIntent = null;
                switch (v.getId())
                {
                    case R.id.menu_home:

                        mIntent = new Intent(ctx,HomeActivity.class);
                        finishAffinity();

                        break;
                    case R.id.notifications:



                        showNotifications();;
                       // mIntent = new Intent(ctx,CartActivity.class);

                        break;
                    case R.id.menu_account:

                        startMyAccount();
                       // mIntent = new Intent(ctx,MyAccount.class);

                        break;
                    case R.id.menu_about:

                        showAbout();

                       // mIntent = new Intent(ctx,AboutUs.class);

                        break;
                    case R.id.menu_logout:

                        utl.logout();
                        Intent intent = new Intent(getApplicationContext(), Splash.class);
                        finishAffinity();
                        startActivity(intent);
                        break;

                }

                if(mIntent!=null)
                    startActivity(mIntent);
                popup.dismiss();


            });

            cont.setOrientation(LinearLayout.VERTICAL);
            cont.setGravity(Gravity.CENTER);

            cont.addView(row);

        }



    };

    public void showAbout  () {


        String text=getstring(R.string.about);

        lastDialog= utl.diagInfo2(ctx, text, "Feedback", R.drawable.ic_logo_inv, new utl.ClickCallBack() {
            @Override
            public void done(DialogInterface dialogInterface) {



                ShareCompat.IntentBuilder.from(act)
                        .setType("message/rfc822")
                        .addEmailTo("admin@hoptech.in")
                        .setSubject("Feedback")
                        .setText("Hey ! I want to give feedback about app .")
                        .setChooserTitle("Send E-Mail")
                        .startChooser();



            }
        });


    }

    public void showNotifications()
    {
        ArrayList<utl.NotificationMessage> notificationMessages=utl.NotificationMessage.getAll(ctx);

        Collections.sort(notificationMessages,new Comparator<utl.NotificationMessage>() {
            @Override
            public int compare(utl.NotificationMessage notificationMessage, utl.NotificationMessage t1) {


             return    t1.time.compareTo(notificationMessage.time);

            }
        });

        GenriXAdapter<utl.NotificationMessage> adapter=new GenriXAdapter<utl.NotificationMessage>(ctx,R.layout.row_notification,notificationMessages)
        {
            @Override
            public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

                final utl.NotificationMessage nof=notificationMessages.get(viewHolder.getAdapterPosition());
                final  CustomViewHolder vh=(CustomViewHolder)viewHolder;

                vh.textView(R.id.title).setText(nof.title);
                if(nof.type.equals(utl.NotificationMessage.TYPE_REQUEST))
                {
                    vh.textView(R.id.title).setTextColor(getcolor(R.color.material_green_500));
                    vh.imageView(R.id.image).setImageResource(nof.icon);
                    vh.imageView(R.id.image).setColorFilter(ContextCompat.getColor(ctx, R.color.material_green_500),
                            android.graphics.PorterDuff.Mode.SRC_IN);

                }
                else
                {
                    vh.textView(R.id.title).setTextColor(getcolor(R.color.colorPrimary));
                    vh.imageView(R.id.image).setImageResource(nof.icon);
                    vh.imageView(R.id.image).setColorFilter(ContextCompat.getColor(ctx, R.color.colorPrimary),
                            android.graphics.PorterDuff.Mode.SRC_IN);

                }

                vh.textView(R.id.message).setText(nof.message);
                vh.textView(R.id.time).setText(nof.getTimeFormatted());
                vh.imageView(R.id.image).setImageResource(nof.icon);

                vh.base.setOnClickListener((v)-> {

                    Intent it=nof.getIntent(ctx);
                    startActivity(it);



                });

            }
        };


        View head_cont=findViewById(R.id.head_cont);
        RecyclerView rv=new RecyclerView(ctx);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rv.setLayoutParams(params);
        params.topMargin=utl.pxFromDp(ctx,40).intValue();
        FadePopup cp=new FadePopup(ctx,head_cont,rv);

        Dialog d;
        if(notificationMessages.size()<1)
        {
            TextView im=new TextView(ctx);
            im.setText("No new notifications !");
            params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    ,utl.pxFromDp(ctx,100).intValue());
            params.gravity=Gravity.CENTER;

            utl.setFace(R.font.font_regular,im);
            im.setTextColor(getcolor(R.color.black));
            im.setTextSize(20);
            im.setGravity(Gravity.CENTER);
            im.setLayoutParams(params);
            cp=new FadePopup(ctx,head_cont,im);


             d=cp.popup();
            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {


                    // utl.toast(ctx,"Cleared Notifications");
                    setUpNotfIcon(true);

                }
            });

            LinearLayout l=cp.getContainer();
            l.setGravity(Gravity.CENTER);

            im.setCompoundDrawables(null,getdrawable(R.drawable.ic_notifications_none_black_24dp),null,null);


        }
        else
        {
            d=cp.popup();
            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {

                  //  utl.NotificationMessage.deleteAll(ctx);
                    // utl.toast(ctx,"Cleared Notifications");


                }
            });
        }

        FadePopup cpp=cp;
        Button dismissBtn=cp.dismissBtn;
        dismissBtn.setVisibility(View.VISIBLE);
        dismissBtn.setText("Clear");
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utl.NotificationMessage.deleteAll(ctx);
              //  utl.toast(ctx,"Notifications Cleared !");

                cpp.dismiss();

            }
        });


        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(ctx));
        rv.setOverScrollMode(View.OVER_SCROLL_NEVER);



    }


    public void showHelp(String help_key) {

        if(utl.getKey(help_key,ctx)==null)
        {
            String text="";



            if(text.length()>3)
            {
                lastDialog=  utl.diagInfo2(ctx, text, "Don't Show Again", R.drawable.ic_logo_inv, new utl.ClickCallBack() {
                    @Override
                    public void done(DialogInterface dialogInterface) {


                        utl.setKey(help_key,"dontshow",ctx);


                    }
                });
            }


        }



    }

    public Dialog lastDialog=null;
    @Override
    protected void onDestroy() {

        if(lastDialog!=null && lastDialog.isShowing())
            lastDialog.dismiss();;

        super.onDestroy();
    }
}
