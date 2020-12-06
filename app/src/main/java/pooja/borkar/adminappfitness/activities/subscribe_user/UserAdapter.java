package pooja.borkar.adminappfitness.activities.subscribe_user;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.activities.MainActivity;
import pooja.borkar.adminappfitness.model.MyUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {
    Context context;
    List<MyUser> usersList;
    //list of filtered search
    List<MyUser> filteredUserList;
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
        View view1 = inflater.inflate(R.layout.newuserlayout,null);
        return new UserAdapter.UserViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(UserAdapter.UserViewHolder holder, int position) {
        MyUser users = filteredUserList.get(position);
        holder.fName.setText(users.getUser_name());
        holder.expert_name.setText(users.getExpert_name());

        holder.plan_name.setText(users.getExpert_plans_name());
holder.plan_sups_id=users.getPlan_sups_id();
        Glide.with(context).load(users.getImage()).into(holder.fImage);
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
                    for (MyUser row : usersList) {


                        //change this to filter according to your case
                        if (row.getUser_name().toLowerCase().contains(charString.toLowerCase())) {
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
        TextView fName, plan_name ,expert_name;
        ImageView fImage;
       Button addusersub;
String plan_sups_id;

        public UserViewHolder(View itemView) {
            super(itemView);
            fName = itemView.findViewById(R.id.name_rec);
            plan_name = itemView.findViewById(R.id.plan_rec);
            expert_name= itemView.findViewById(R.id.expert_rec);
            fImage=itemView.findViewById(R.id.image1_rec);
            addusersub=itemView.findViewById(R.id.addusersub);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {




                }
            });

            addusersub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Call<MyUser> call= MainActivity.serviceApi.adminAddUser(plan_sups_id);
                    call.enqueue(new Callback<MyUser>() {
                        @Override
                        public void onResponse(Call<MyUser> call, Response<MyUser> response) {
                            if (response.body().getResponse().matches("added"))
                            {

                                Toast.makeText(view.getContext(), "added Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else if (response.body().getResponse().matches("error"))
                            {
                                Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MyUser> call, Throwable t) {
                            Log.d("Error", t.getMessage());

                        }
                    });

                }
            });
        }
    }

    public interface UserAdapterListener {
        void onUserSelected(MyUser user);
    }
}
