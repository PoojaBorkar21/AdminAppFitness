package pooja.borkar.adminappfitness.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.canstants.Constants;
import pooja.borkar.adminappfitness.extras.AppPreferences;
import pooja.borkar.adminappfitness.fragments.RegisterFragment;
import pooja.borkar.adminappfitness.fragments.loginFragment;
import pooja.borkar.adminappfitness.services.MyInterface;
import pooja.borkar.adminappfitness.services.RetrofitClient;
import pooja.borkar.adminappfitness.services.ServiceApi;

public class MainActivity extends AppCompatActivity implements MyInterface {
    public static AppPreferences appPreferences;
    public static ServiceApi serviceApi;
    FrameLayout container_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container_fragment=findViewById(R.id.fragment_container);
        appPreferences=new AppPreferences(this);
        serviceApi = RetrofitClient.getApiClient(Constants.baseUrl.Base_URL).create(ServiceApi.class);
        if(container_fragment!=null)
        {
            if(savedInstanceState!=null)
                if(savedInstanceState!=null)
                {
                    return;
                }
            if(appPreferences.getLoginStatus())
            {
                Intent intent=new Intent(this, HomeActivity.class);
                startActivity(intent);

            }

            else
            {

                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new loginFragment()).commit();

            }
        }

    }

    @Override
    public void register() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void login(String name, String email) {
        appPreferences.setDiaplayEmail(email);
        appPreferences.setDiaplayName(name);

        Intent intent=new Intent(this, HomeActivity.class);
        startActivity(intent);

    }



    @Override
    public void logout() {

        appPreferences.setLoginStatus(false);
        appPreferences.setDiaplayName("");
        appPreferences.setDiaplayEmail("");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new loginFragment())
                .addToBackStack(null)
                .commit();

    }
    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();

        if (manager.getBackStackEntryCount() > 0) {

            manager.popBackStack();

        } else {
            // Otherwise, ask user if he wants to leave :)
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            finishAffinity();
                            finish();
                            moveTaskToBack(true);
                        }
                    }).create().show();
        }
    }



}