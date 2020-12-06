package pooja.borkar.adminappfitness.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.activities.MainActivity;
import pooja.borkar.adminappfitness.model.Usercount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.ViewHolder> {

    List<Usercount> images;
    LayoutInflater inflater;

    public imageAdapter(Context ctx,  List< Usercount> images){

        this.images = images;
        this.inflater = LayoutInflater.from(ctx);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.admin_baner_url=images.get(position).getAdmin_baner_url();
        Glide.with(holder.gridIcon.getContext())
                .load(images.get(position).getAdmin_baner_url())
                .into(holder.gridIcon);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
  ImageView deleteimg;
        ImageView gridIcon;
        String admin_baner_url;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteimg= itemView.findViewById(R.id.deleteimg);
            gridIcon = itemView.findViewById(R.id.imageView2);

            deleteimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(v.getContext());




                    alertDialogBuilder.setMessage("Do you Want to Delete this image?" )
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {


                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Call<Usercount> userCall = MainActivity.serviceApi.deleteBanner(admin_baner_url);
                                    userCall.enqueue(new Callback<Usercount>()

                                    {
                                        @Override
                                        public void onResponse
                                                (@NonNull Call < Usercount > call, @NonNull Response < Usercount > response)
                                        {
                                            if (response.body().getResponse().equals("delete")) {

                                                Toast.makeText(v.getContext(), "Banner banner deleted", Toast.LENGTH_SHORT).show();
                                            } else if (response.body().getResponse().equals("error")) {

                                                Toast.makeText(v.getContext(), "Error....Not deleted", Toast.LENGTH_SHORT).show();
                                            }

                                        }


                                        @Override
                                        public void onFailure (Call < Usercount > call, Throwable t){
                                            System.out.println("myerror" + t.getMessage());
                                            Toast.makeText(v.getContext(), "failure", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }


                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();


                    alertDialog.show();



                }


            });
        }
    }
}
