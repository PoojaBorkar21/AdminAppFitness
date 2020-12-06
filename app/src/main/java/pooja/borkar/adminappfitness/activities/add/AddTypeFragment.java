package pooja.borkar.adminappfitness.activities.add;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.activities.MainActivity;
import pooja.borkar.adminappfitness.canstants.Constants;
import pooja.borkar.adminappfitness.model.Expart_name;
import pooja.borkar.adminappfitness.model.Usercount;
import pooja.borkar.adminappfitness.services.RetrofitClient;
import pooja.borkar.adminappfitness.services.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTypeFragment extends Fragment {
Button btn_add,btn_remove;
EditText  edx_addtype, edx_addfees;
  Spinner expertlist;
    ArrayList<String> expert_array = new ArrayList<>();
    ServiceApi serviceApi;
      @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_add_type, container, false);
        btn_add=view.findViewById(R.id.btn_addtype);
        edx_addfees=view.findViewById(R.id.edx_addfees);
        edx_addtype=view.findViewById(R.id.edx_addtype);
        expertlist=view.findViewById(R.id.expertlist);

        getspinnerlist();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String experties_type_name= edx_addtype.getText().toString();
                String experties_type_fees= edx_addfees.getText().toString();
                if(experties_type_name.isEmpty()) {
                    Snackbar.make(edx_addtype, "Enter Expert Type", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#00a5ff"))
                            .setActionTextColor(Color.parseColor("red"))
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                }
                else if(experties_type_fees.isEmpty()){
                    Snackbar.make(edx_addtype, "Enter Fees", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#00a5ff"))
                            .setActionTextColor(Color.parseColor("red"))
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                }

                else{

                Call<Usercount> userCall = MainActivity.serviceApi.addExpertType(experties_type_name,experties_type_fees);
                userCall.enqueue(new Callback<Usercount>() {
                    @Override
                    public void onResponse(@NonNull Call<Usercount> call, @NonNull Response<Usercount> response) {
                        if(response.body().getResponse().equals("added"))
                        {

                            Toast.makeText(getActivity(), "Expert type Added", Toast.LENGTH_SHORT).show();
                        }
                        else  if(response.body().getResponse().equals("exists"))
                        {

                            Toast.makeText(getActivity(), "Already Exist", Toast.LENGTH_SHORT).show();
                        }

                        else  if(response.body().getResponse().equals("error"))
                        {

                            Toast.makeText(getActivity(), "Error....Not Added", Toast.LENGTH_SHORT).show();
                        }

                    }


                    @Override
                    public void onFailure(Call<Usercount> call, Throwable t) {
                        System.out.println("myerror" + t.getMessage());
                        Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();

                    }
                });
            }}
        });
       /* btn_remove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String experties_type_name= expertlist.getSelectedItem().toString().trim();
                Call<Usercount> userCall = MainActivity.serviceApi.removeExpertType(experties_type_name);
                userCall.enqueue(new Callback<Usercount>() {
                    @Override
                    public void onResponse(@NonNull Call<Usercount> call, @NonNull Response<Usercount> response) {
                        if(response.body().getResponse().equals("removed"))
                        {

                            Toast.makeText(getActivity(), "Expert Type removed", Toast.LENGTH_SHORT).show();
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
                });

            }
        });*/
        return view;
    }

    private void getspinnerlist() {
        serviceApi = RetrofitClient.getApiClient(Constants.baseUrl.Base_URL).create(ServiceApi.class);
        Call<List<Expart_name>> call= serviceApi.getExpertList();
        call.enqueue(new Callback<List<Expart_name>>() {
            @Override
            public void onResponse(Call<List<Expart_name>> call, Response<List<Expart_name>> response) {
                if(response.isSuccessful())
                {

                    List<Expart_name> expart_list=response.body();
                    for (int i=0;i<expart_list.size();i++){

                        String List =expart_list.get(i).getExperties_type_name();

                        expert_array.add(List);

                    }

                    System.out.println(expert_array+"++++++++++++++++++++++++++");
                    expert_array.add(0,"Select Expert Type ");
                    ArrayAdapter<String> dataAdapter;
                    dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, expert_array);

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    expertlist.setAdapter(dataAdapter);

                }
                else {
                    Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<List<Expart_name>> call, Throwable t) {

            }
        });
    }
}