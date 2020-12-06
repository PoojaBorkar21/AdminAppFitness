package pooja.borkar.adminappfitness.activities.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.activities.MainActivity;
import pooja.borkar.adminappfitness.canstants.Constants;
import pooja.borkar.adminappfitness.fragments.imageAdapter;
import pooja.borkar.adminappfitness.model.Usercount;
import pooja.borkar.adminappfitness.services.RetrofitClient;
import pooja.borkar.adminappfitness.services.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class addBannerFragment extends Fragment {
    RecyclerView dataList;

    ServiceApi serviceApi;

   imageAdapter adapter;

    Button btn_upload;
ImageView img_upload;
Uri path;
Spinner spinner_type;
    private static final int IMG_REQUES=777;
    private Bitmap bitmap;

    public addBannerFragment() {

    }


     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_add_banner, container, false);
       btn_upload=view.findViewById(R.id.btn_upload);
       img_upload=view.findViewById(R.id.img_upload);
        dataList = view.findViewById(R.id.dataList);
       spinner_type=view.findViewById(R.id.spinnertype);
       getimages();


        String [] usertype =
              {"Select Banner Type","Expert","User"};
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, usertype);
    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

   spinner_type.setAdapter(adapter);
   img_upload.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           selectImage();
       }
   });

   btn_upload.setOnClickListener(new View.OnClickListener() {

       @Override
       public void onClick(View view) {
           String admin_baner_type = spinner_type.getSelectedItem().toString();
           if(path==null) {
               Snackbar.make(btn_upload, "Select the Image", Snackbar.LENGTH_LONG)
                       .setBackgroundTint(Color.parseColor("#00a5ff"))
                       .setActionTextColor(Color.parseColor("red"))
                       .setAction("Retry", new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {

                           }
                       }).show();
           }
           else if(admin_baner_type.equals("Select Banner Type"))
           { Snackbar.make(btn_upload, "Select Banner Type", Snackbar.LENGTH_LONG)
                   .setBackgroundTint(Color.parseColor("#00a5ff"))
                   .setActionTextColor(Color.parseColor("red"))
                   .setAction("Retry", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                       }
                   }).show();}
           else{

           String admin_baner_url=imageToString();



               Call<Usercount> userCall = MainActivity.serviceApi.bannerUpload(admin_baner_url,admin_baner_type);
           userCall.enqueue(new Callback<Usercount>() {
               @Override
               public void onResponse(@NonNull Call<Usercount> call, @NonNull Response<Usercount> response) {
                   if(response.body().getResponse().equals("Uploaded"))
                   {

                       Toast.makeText(getActivity(), "Banner Uploaded", Toast.LENGTH_SHORT).show();
                   }
                   else  if(response.body().getResponse().equals("error"))
                   {

                       Toast.makeText(getActivity(), "Error....Not Uploaded", Toast.LENGTH_SHORT).show();
                   }

               }


               @Override
               public void onFailure(Call<Usercount> call, Throwable t) {
                   System.out.println("myerror" + t.getMessage());
                   Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();

               }
           });}

       }
   });
       return  view;
    }

    private void getimages() {
        serviceApi = RetrofitClient.getApiClient(Constants.baseUrl.Base_URL).create(ServiceApi.class);
        Call<List<Usercount>> call= serviceApi.getBanner();
        call.enqueue(new Callback<List<Usercount>>() {
            @Override
            public void onResponse(Call<List<Usercount>> call, Response<List<Usercount>> response) {
                if(response.isSuccessful())
                {

                    List<Usercount> images=response.body();
                   System.out.println(images+"+++++++++++++++++++URL+++++++++++");
                    adapter = new imageAdapter(getContext(),images);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
                    dataList.setLayoutManager(gridLayoutManager);
                    dataList.setAdapter(adapter);

                }
                else {
                    Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<List<Usercount>> call, Throwable t) {

            }
        });

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
                img_upload.setImageBitmap(bitmap);
                img_upload.setVisibility(View.VISIBLE);



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