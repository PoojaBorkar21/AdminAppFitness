package pooja.borkar.adminappfitness.activities.ui.Profile;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.activities.HomeActivity;
import pooja.borkar.adminappfitness.activities.MainActivity;
import pooja.borkar.adminappfitness.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static pooja.borkar.adminappfitness.activities.MainActivity.appPreferences;


public class ProfileFragment extends Fragment {
    TextView  name_update,email_update,gender_update,dob_update,phone_update;
    ImageView prof_update;
    LinearLayout layout_name,layout_gender,layout_dob,layout_phone;
    String image;
    Button button_update;
    Uri path;
    private static final int IMG_REQUES=777;
    private Bitmap bitmap;
    public ProfileFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        prof_update =view.findViewById(R.id.prof_update);
        button_update=view.findViewById(R.id.button_update);
        name_update=view.findViewById(R.id.name_update);
        email_update=view.findViewById(R.id.email_update);
        dob_update=view.findViewById(R.id.dob_update);
        gender_update=view.findViewById(R.id.gender_update);
        phone_update=view.findViewById(R.id.phone_update);
        layout_name=view.findViewById(R.id.layout_name);
        layout_phone=view.findViewById(R.id.layout_phone);
        layout_gender=view.findViewById(R.id.layout_gender);
        layout_dob=view.findViewById(R.id.layout_dob);

        final String email=appPreferences.getDiaplayEmail();
        Call<User> call = MainActivity.serviceApi.getAdmin(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    String admin_email = response.body().getEmail().toString().trim();
                    String admin_name = response.body().getName().toString().trim();
                    String admin_dob = response.body().getDob().toString();
                    String admin_phone = response.body().getPhone().toString().trim();
                    String admin_gender = response.body().getGender().toString().trim();


                    dob_update.setText(admin_dob);
                    name_update.setText(admin_name);
                    email_update.setText(admin_email);
                    phone_update.setText(admin_phone);
                    gender_update.setText(admin_gender);

                    Glide.with(prof_update.getContext()).load(response.body().getImage()).into(prof_update);
                }
            }

            @Override
            public void onFailure(Call<User>call, Throwable t) {
                Log.d("Error", t.getMessage());


            }
        });
        prof_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();


            }
        });
email_update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "Not Editable", Toast.LENGTH_SHORT).show();
    }
});
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(path==null) {}
                else{
                image = imageToString();}

                String name= name_update.getText().toString();
                String phone=phone_update.getText().toString();
                String dob=dob_update.getText().toString();
                String gender=gender_update.getText().toString();

                Call<User> userCall = MainActivity.serviceApi.prof_update(name,email,gender,dob,phone,image);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        if(response.body().getResponse().equals("Updated"))
                        {

                            Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
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
        layout_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                final EditText edittext = new EditText(getContext());
                edittext.setInputType( InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                alert.setMessage("Enter name below");
                alert.setCancelable(false);

                alert.setView(edittext);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String EditTextValue = edittext.getText().toString();
                        if(!EditTextValue.isEmpty())
                            name_update.setText(EditTextValue);

                    }
                });

                alert.setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });





        layout_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose one");

                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getActivity(), items[item], Toast.LENGTH_SHORT).show();
                        gender_update.setText(items[item]);
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        layout_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dob_update.setText( year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        layout_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                final EditText edittext = new EditText(getContext());
                edittext.setInputType(InputType.TYPE_CLASS_NUMBER );
                alert.setMessage("Enter Phone Number");
                alert.setCancelable(false);

                alert.setView(edittext);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String EditTextValue = edittext.getText().toString();
                        if(!EditTextValue.isEmpty()&&!(EditTextValue.length()>10 )&& !(EditTextValue.length()<10))
                            phone_update.setText(EditTextValue);
                        else{
                            Toast.makeText(getContext(), "Enter Correct Number", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                alert.setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });
        return view;
    }



    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMG_REQUES);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUES && resultCode == RESULT_OK && data != null ) {
           path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                prof_update.setImageBitmap(bitmap);
                prof_update.setVisibility(View.VISIBLE);



            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }
    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
}