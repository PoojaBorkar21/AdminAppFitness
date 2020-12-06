package pooja.borkar.adminappfitness.activities.userList;

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
import pooja.borkar.adminappfitness.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {
    Context context;
    List<User> usersList;
    //list of filtered search
    List<User> filteredUserList;
    private UserAdapter.UserAdapterListener listener;

    public UserAdapter(Context context, List usersList, UserAdapter.UserAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.usersList = usersList;
        this.filteredUserList = usersList;

    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view1 = inflater.inflate(R.layout.recyclerproflist,null);
        return new UserAdapter.UserViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(UserAdapter.UserViewHolder holder, int position) {
        User users = filteredUserList.get(position);
        holder.fName.setText(users.getName());
        holder.phone.setText(users.getPhone());
        holder.email=users.getEmail().toString();
if(users.getActive().equals("1")){ holder.status.setText("Active");}
       else{holder.status.setText("Inactive");}

        Glide.with(context)
                .load(users.getImage())
                .into(holder.fImage);
    }
    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }
    //filter method
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredUserList = usersList;

                } else {
                    List filteredList = new ArrayList<>();
                    for (User row : usersList) {


                        //change this to filter according to your case
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filteredUserList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (ArrayList) filterResults.values;
                notifyDataSetChanged();

            }
        };

    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView fName,phone;
        ImageView fImage;
        TextView status;
        String email;

        public UserViewHolder(View itemView) {
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

                    alertDialogBuilder.setTitle("Perform Action On User : " );


                    alertDialogBuilder.setMessage(email)
                            .setCancelable(true)
                            .setPositiveButton("Active",new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, int id) {
                                    String active= "toactive";
                                    Call<User> userCall = MainActivity.serviceApi.doChangeuser( email, active);
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
                                    Call<User> userCall = MainActivity.serviceApi.doChangeuser( email, active);
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

    public interface UserAdapterListener {
        void onUserSelected(User user);
    }
}
