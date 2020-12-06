package pooja.borkar.adminappfitness.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.activities.MainActivity;
import pooja.borkar.adminappfitness.model.User;
import pooja.borkar.adminappfitness.services.MyInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    EditText nameInput_reg,emailInput_reg,phoneInput_reg,passwordInput_reg,dob_reg,editTextotp;

    RadioGroup radioGroup;
    RadioButton radio_female, radio_male;
    Button button_reg,buttonConfirm;
    MyInterface myInterface_reg;
    DatePickerDialog picker;

    public RegisterFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_register, container, false);
        nameInput_reg=view.findViewById(R.id.nameInput);
        emailInput_reg=view.findViewById(R.id.emailInput);
        phoneInput_reg=view.findViewById(R.id.phoneInput);
        passwordInput_reg=view.findViewById(R.id.passwordInput);
        radio_male = view.findViewById(R.id.radio_male);
        radio_female = view.findViewById(R.id.radio_female);
        radioGroup =(RadioGroup) view.findViewById(R.id.radio_but);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int option = radioGroup.getCheckedRadioButtonId();
                //boolean checked = ((RadioButton) view).isChecked();
                switch (option) {

                    case R.id.radio_male:
                        if (radio_male.isChecked()) {
                            break;
                        }

                    case R.id.radio_female:
                        if (radio_female.isChecked()) {
                            break;

                        }
                }
            }
        });



        dob_reg=view.findViewById(R.id.dobInput);
        dob_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dob_reg.setText( year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                picker.show();
            }
        });


        button_reg=view.findViewById(R.id.regBtn);
        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }

            private void registerUser() {
                final String name=nameInput_reg.getText().toString().trim();
                String email=emailInput_reg.getText().toString().trim();
                String phone=phoneInput_reg.getText().toString().trim();
                String password=passwordInput_reg.getText().toString().trim();

                String dob=dob_reg.getText().toString().trim();





                if(TextUtils.isEmpty(name)){
                    // MainActivity.appPreferences.showToast("Enter your name");
                    Snackbar.make(nameInput_reg,"EnterYourName",Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#29DAC9"))
                            .setActionTextColor(Color.parseColor("red"))
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "retry", Toast.LENGTH_SHORT).show();
                                    clearText1();
                                }

                                private void clearText1() {

                                    nameInput_reg.setText("");


                                }
                            }).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // MainActivity.appPreferences.showToast("Your email is invalid");
                    Snackbar.make(nameInput_reg,"Your email is invalid",Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#29DAC9"))
                            .setActionTextColor(Color.parseColor("red"))
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "retry", Toast.LENGTH_SHORT).show();
                                    clearText1();
                                }

                                private void clearText1() {
                                    emailInput_reg.setText("");

                                }
                            }).show();
                }
                else if (radioGroup.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked

                    // MainActivity.appPreferences.showToast("Enter your gender");
                    Snackbar.make(nameInput_reg,"Enter your gender",Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#29DAC9"))
                            .setActionTextColor(Color.parseColor("red"))
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "retry", Toast.LENGTH_SHORT).show();
                                    clearText1();
                                }

                                private void clearText1() {

                                }
                            }).show();
                }
                else if(TextUtils.isEmpty(dob)){
                    // MainActivity.appPreferences.showToast("Enter your DOb");
                    Snackbar.make(nameInput_reg,"Enter your DOB",Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#29DAC9"))
                            .setActionTextColor(Color.parseColor("red"))

                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    clearText1();
                                }

                                private void clearText1() {
                                    dob_reg.setText("");
                                    passwordInput_reg.setText("");
                                    phoneInput_reg.setText("");
                                }
                            }).show();
                } else if(TextUtils.isEmpty(phone)){
                    // MainActivity.appPreferences.showToast("Enter your phone");
                    Snackbar.make(nameInput_reg,"Enter your phone",Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#29DAC9"))
                            .setActionTextColor(Color.parseColor("red"))
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "retry", Toast.LENGTH_SHORT).show();
                                    clearText1();
                                }

                                private void clearText1() {

                                    phoneInput_reg.setText("");
                                }
                            }).show();
                } else if (phone.length()>10 || phone.length()<10){

                    //  MainActivity.appPreferences.showToast("Enter correct no");
                    Snackbar.make(nameInput_reg,"Enter correct Phone",Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#29DAC9"))
                            .setActionTextColor(Color.parseColor("red"))
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "retry", Toast.LENGTH_SHORT).show();
                                    clearText1();
                                }

                                private void clearText1() {
                                    phoneInput_reg.setText("");
                                }
                            }).show();
                }
                else if(TextUtils.isEmpty(password)){
                    //MainActivity.appPreferences.showToast("Enter your pass");
                    Snackbar.make(nameInput_reg,"Enter your password",Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#29DAC9"))
                            .setActionTextColor(Color.parseColor("red"))
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "retry", Toast.LENGTH_SHORT).show();
                                    clearText1();
                                }

                                private void clearText1() {

                                    passwordInput_reg.setText("");

                                }
                            }).show();
                }  else if(password.length()<6)
                {
                    //   MainActivity.appPreferences.showToast("your email length will not match to patern");
                    Snackbar.make(nameInput_reg,"Enter password length greater than 5",Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#29DAC9"))
                            .setActionTextColor(Color.parseColor("red"))
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "retry", Toast.LENGTH_SHORT).show();
                                    clearText1();
                                }

                                private void clearText1() {

                                    passwordInput_reg.setText("");

                                }
                            }).show();
                }

                else
                {
                    String gender1 = ((RadioButton) view.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    Call<User> userCall=MainActivity.serviceApi.doRegisteration(name,email,gender1,dob,password,phone);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {

                            if(response.body().getResponse().matches("inserted"))
                            {
                                show_Message("Welcome "+name," Check Mail to Activate Account");

                                // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            }else if(response.body().getResponse().matches("exists"))
                            {

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                builder1.setMessage("Already Registered User");
                                builder1.setCancelable(false);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                myInterface_reg.logout();
                                            }
                                        });



                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }

                            Log.i("My response",response.body().getResponse());
                        }

                        private void show_Message(String title, String input) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle(title);
                            builder.setMessage(input);
                            builder.setCancelable(false);
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


                                                Toast.makeText(getActivity(), "Registered   successfully", Toast.LENGTH_SHORT).show();
                                                myInterface_reg.logout();
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
                        public void onFailure(Call<User> call, Throwable t) {
                            System.out.println("myerror"+t.getMessage());
                            Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();

                        }
                    });



                }




            }
        });


        return  view;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface_reg= (MyInterface) activity;
    }
}