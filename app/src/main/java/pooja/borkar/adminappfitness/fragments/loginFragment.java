package pooja.borkar.adminappfitness.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.activities.MainActivity;
import pooja.borkar.adminappfitness.model.User;
import pooja.borkar.adminappfitness.services.MyInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginFragment extends Fragment {
    MyInterface myInterface_login;
Button loginbtn,buttonConfirm;
 EditText emailInput,passwordInput;

 EditText editTextotp;
TextView registerTV,tv_forgot_pass;
public static String email;
public loginFragment()
{

}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
   emailInput=view.findViewById(R.id.emailInput);
   passwordInput=view.findViewById(R.id.passwordInput);
   loginbtn=view.findViewById(R.id.loginBtn);
   tv_forgot_pass=view.findViewById(R.id.tv_forgot_pass);
        registerTV=view.findViewById(R.id.registerTV);

        tv_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                forgotPassword("Welcome "," Enter Email ID below to change Account Password");

            }
        });
   loginbtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {

       loginUser();
       }
   });
   registerTV.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Toast.makeText(getActivity(), "Register", Toast.LENGTH_SHORT).show();
           myInterface_login.register();
       }
   });
        return view;


    }

    private void forgotPassword(String title, String input) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(input);
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_verify, null);
        builder.setView(customLayout);
        builder.setCancelable(false);
        buttonConfirm =  customLayout.findViewById(R.id.buttonConfirm);
        editTextotp = customLayout.findViewById(R.id.editTextOtp);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =editTextotp.getText().toString();
                Call<User> userCall = MainActivity.serviceApi.forgotPassword(email);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        if(response.body().getResponse().equals("email_send"))
                        {
                            Toast.makeText(getActivity(), "Email is send to entered Email ID", Toast.LENGTH_SHORT).show();


                        }
                        else if(response.body().getResponse().equals("email_not_send"))
                        {
                            Toast.makeText(getActivity(), "Email not send,Try again", Toast.LENGTH_SHORT).show();
                            editTextotp.setText("");

                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println("myerror" + t.getMessage());
                        Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });

        builder.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                builder.setCancelable(true);


            }
        });
        builder.show();
    }

    private void loginUser() {
       email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            MainActivity.appPreferences.showToast("Your email is invalid");
        } else if (TextUtils.isEmpty(password)) {
            MainActivity.appPreferences.showToast("Enter your pass");
        } else if (password.length() < 6) {
            MainActivity.appPreferences.showToast("your password length will not match to patern");
        } else {
            Call<User> userCall = MainActivity.serviceApi.doLogin( email, password);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(response.body().getResponse().equals("data"))
                {
                    MainActivity.appPreferences.setLoginStatus(true);
                    myInterface_login.login(response.body().getName(),response.body().getEmail());
                    Toast.makeText(getActivity(), "login successfull", Toast.LENGTH_SHORT).show();
                }
                else if(response.body().getResponse().equals("login_failed"))
                {
                    Toast.makeText(getActivity(), "login_failed", Toast.LENGTH_SHORT).show();
                  emailInput.setText("");
                    passwordInput.setText("");
                }
                else if(response.body().getResponse().equals("verify_otp")) {
                    show_Message("Already Registered"," Verify otp");
                }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println("myerror" + t.getMessage());
                    Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    private void show_Message(String title, String input) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(input);
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_verify, null);
        builder.setView(customLayout);
        buttonConfirm =  customLayout.findViewById(R.id.buttonConfirm);
        editTextotp = customLayout.findViewById(R.id.editTextOtp);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp =editTextotp.getText().toString();
                Call<User> userCall = MainActivity.serviceApi.doverify(otp);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        if(response.body().getResponse().equals("correct"))
                        {

                            myInterface_login.logout();
                            Toast.makeText(getActivity(), "verify  successfull", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.body().getResponse().equals("incorrect"))
                        {
                            Toast.makeText(getActivity(), "wrong otp", Toast.LENGTH_SHORT).show();
                            editTextotp.setText("");

                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println("myerror" + t.getMessage());
                        Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                builder.setCancelable(true);


            }
        });
        builder.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface_login= (MyInterface) activity;
    }


}