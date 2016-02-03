package com.abhishek.travel;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.Menu;
import android.view.MenuItem;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginScreen extends AppCompatActivity {

    Client mKinveyClient;
    String appKey="kid_ZkMTx2eVTl",appSecret="0a5312c4b0f2419b97f821317ed8ef7c";
    String TAG="LoginScreen";
    Toolbar toolbar;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login_screen);
        mKinveyClient = new Client.Builder(appKey, appSecret
                , this.getApplicationContext()).build();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loginButton=(LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends,email");
        setSupportActionBar(toolbar);
        mKinveyClient.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Log.e(TAG, "Kinvey Ping Failed", t);
            }
            public void onSuccess(Boolean b) {
                Log.d(TAG, "Kinvey Ping Success");
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final String accessToken= loginResult.getAccessToken().getToken();
                Log.d(TAG,accessToken+" success");
                loginFacebookKinveyUser(accessToken);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.d(TAG,object.toString());
                                Log.d(TAG,response.toString());
                                try {
                                    String userName=object.getString("name");
                                    String userID=object.getString("id");
                                    String userEmail=object.getString("email");
                                    JSONObject picture = object.getJSONObject("picture");
                                    JSONObject data=picture.getJSONObject("data");
                                    String url=data.getString("url");
                                    preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("UserID",userID);
                                    editor.putString("UserAccessToken",accessToken);
                                    editor.apply();

                                    String firstTime = preferences.getString("FirstTime", "");
                                    if(new String("").equals(firstTime))
                                    {
//                                        Intent intent = new Intent(LoginScreen.this,AddClothesActivity.class);
//                                        intent.putExtra("url",url);
//                                        startActivity(intent);
                                    }
                                    else
                                    {

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,picture.width(400).height(400),email");
                request.setParameters(parameters);
                request.executeAsync();
                Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d(TAG," cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"error "+error);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void loginFacebookKinveyUser(String accessToken) {
        if(mKinveyClient.user().isUserLoggedIn())
            mKinveyClient.user().logout().execute();
        mKinveyClient.user().loginFacebook(accessToken, new KinveyUserCallback() {
            @Override
            public void onFailure(Throwable e) {
                //error(progressDialog, "Kinvey: " + e.getMessage());
                Log.e(TAG, "failed Kinvey facebook login " + e);
            }
            @Override
            public void onSuccess(User u) {
                Log.d(TAG, "successfully logged in with facebook " + u.toString());
            }
        });
    }
}

