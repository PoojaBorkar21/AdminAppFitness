package pooja.borkar.adminappfitness.activities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.activities.add.AddActivity;
import pooja.borkar.adminappfitness.activities.expertList.ExpertListActivity;
import pooja.borkar.adminappfitness.activities.MainActivity;
import pooja.borkar.adminappfitness.activities.subscribe_user.Subscribed_Users;
import pooja.borkar.adminappfitness.activities.userList.UserListActivity;

import pooja.borkar.adminappfitness.model.Usercount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    ImageView users_dashboard,experts_dashboard,add_img,user_subsc;
TextView tPcount,tUcount;
    String total_user,active_user,total_expert,active_expert;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        users_dashboard=view.findViewById(R.id.users_dashboard);
        experts_dashboard=view.findViewById(R.id.experts_dashboard);
      add_img=view.findViewById(R.id. add_img);
        user_subsc=view.findViewById(R.id. user_subsc);

        tPcount=view.findViewById(R.id.tPcount);
        tUcount=view.findViewById(R.id.tUcount);
              getCount();


        user_subsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), Subscribed_Users.class));

            }
        });


        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // getFragmentManager().beginTransaction().replace(R.id.framelayout_id, new AddFragment()).commit();
                startActivity(new Intent(getActivity(), AddActivity.class));

            }
        });

        users_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserListActivity.class));

            }
        });

        experts_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExpertListActivity.class));
            }
        });
        return view;
    }

    private void getCount() {
        Call<Usercount> call = MainActivity.serviceApi.getUserCount();
        call.enqueue(new Callback<Usercount>() {
            @Override
            public void onResponse(Call<Usercount> call, Response<Usercount> response) {
               if (response.isSuccessful()) {
                 active_user = response.body().getActive_user().toString().trim();
                   total_user = response.body().getTotal_user().toString().trim();
                   active_expert = response.body().getActive_expert().toString().trim();
                   total_expert = response.body().getTotal_expert().toString().trim();
               }
               tUcount.setText(total_user);
               tPcount.setText(total_expert);
            }

            @Override
            public void onFailure(Call<Usercount>call, Throwable t) {
                Log.d("Error", t.getMessage());


            }
        });

    }


}