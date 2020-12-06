package pooja.borkar.adminappfitness.activities.expertList;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.activities.MainActivity;
import pooja.borkar.adminappfitness.model.Experts;
import pooja.borkar.adminappfitness.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ExpertViewHolder> implements Filterable {

    Context context;
    List<Experts> expertsList;
    //list of filtered search
    List<Experts> filteredExpertList;
    private ExpertAdapterListener listener;

    public ExpertAdapter(Context context, List expertsList, ExpertAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.expertsList = expertsList;
        this.filteredExpertList = expertsList;

    }

    @Override
    public ExpertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view1 = inflater.inflate(R.layout.recyclerproflist,null);
        return new ExpertViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(ExpertViewHolder holder, int position) {
        Experts experts = filteredExpertList.get(position);
        holder.fName.setText(experts.getName());
        holder.phone.setText(experts.getPhone());
        holder.email=experts.getEmail().toString();

        if(experts.getActive().equals("1")){ holder.status.setText("Active");}
        else{holder.status.setText("Inactive");}


        Glide.with(context)
                .load(experts.getImage())
                .into(holder.fImage);
    }
    @Override
    public int getItemCount() {
        return filteredExpertList.size();
    }
    //filter method
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredExpertList = expertsList;

                } else {
                    List filteredList = new ArrayList<>();
                    for (Experts row : expertsList) {


                        //change this to filter according to your case
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filteredExpertList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredExpertList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredExpertList = (ArrayList) filterResults.values;
                notifyDataSetChanged();

            }
        };

    }

    public class ExpertViewHolder extends RecyclerView.ViewHolder {
        TextView fName,phone;
        ImageView fImage;
 TextView status;
String email;

        public ExpertViewHolder(View itemView) {
            super(itemView);
            fName = itemView.findViewById(R.id.name_rec);
            fImage = itemView.findViewById(R.id.image_rec);
            phone = itemView.findViewById(R.id.phone_rec);
            status=itemView.findViewById(R.id.status_rec);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    Toast.makeText(view.getContext(), email, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(view.getContext());

                    alertDialogBuilder.setTitle("Perform Action On Expert : " );


                    alertDialogBuilder.setMessage(email)
                            .setCancelable(true)
                            .setPositiveButton("Active",new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, int id) {
                                    String active= "toactive";
                                    Call<User> userCall = MainActivity.serviceApi.doChange( email, active);
                                    userCall.enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                                            if(response.body().getResponse().equals("activated"))
                                            {


                                                Toast.makeText(view.getContext(), "Activated successfull", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            System.out.println("myerror" + t.getMessage());
                                            Toast.makeText(view.getContext(), "failure", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }
                            })
                            .setNegativeButton("Inactive",new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, int id) {
                                    String active= "toinactive";
                                    Call<User> userCall = MainActivity.serviceApi.doChange( email, active);
                                    userCall.enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                                            if(response.body().getResponse().equals("inactivated"))
                                            {
                                                Toast.makeText(view.getContext(), "Deactivate successfull", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            System.out.println("myerror" + t.getMessage());
                                            Toast.makeText(view.getContext(), "failure", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                    //dialog.cancel();
                                }
                            });


                    AlertDialog alertDialog = alertDialogBuilder.create();


                    alertDialog.show();
                }
            });
        }
    }

    public interface ExpertAdapterListener {
        void onExpertSelected(Experts expert);
    }
}