package pooja.borkar.adminappfitness.activities.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import pooja.borkar.adminappfitness.R;


public class AddFragment extends Fragment {
Button btn_banner,btn_type;

       public AddFragment() {

    }


     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_add, container, false);





        btn_banner=view.findViewById(R.id.btn_banner);
        btn_type=view.findViewById(R.id.btn_type);


        btn_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.add_container, new addBannerFragment()).addToBackStack(null).commit();

            }

        });
        btn_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.add_container, new AddTypeFragment()).addToBackStack(null).commit();

            }
        });
        return view;
    }
}