package pooja.borkar.adminappfitness.activities.subscribe_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pooja.borkar.adminappfitness.R;
import pooja.borkar.adminappfitness.activities.MainActivity;
import pooja.borkar.adminappfitness.model.MyUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Subscribed_Users extends AppCompatActivity  implements UserAdapter.UserAdapterListener {
    RecyclerView recyclerView;
    List usersList;
    UserAdapter adapter;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed__users);
        recyclerView = findViewById(R.id.recyclernewuser);
        recyclerView.setHasFixedSize(true);

        //item decorator to separate the items
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //setting layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // toolbar fancy stuff
        getSupportActionBar().setTitle("Search Here");

        // white background notification bar
        whiteNotificationBar(recyclerView);

        //initialize fruits list
        usersList = new ArrayList<>();



        adapter = new UserAdapter(this,usersList,this);

        //method to load fruits
        getData();
        //onItemClickListener


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        if(item.getItemId()== android.R.id.home) {

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setSystemUiVisibility(0);
        }
    }



    //loading data from database.
    private void getData() {


        Call<List<MyUser>> call = MainActivity.serviceApi.getNewSubs();

        call.enqueue(new Callback<List<MyUser>>() {
            @Override
            public void onResponse(Call<List<MyUser>> call, Response<List<MyUser>> response) {

                if(response.isSuccessful()) {

                    List<MyUser> userList = response.body();

                    for (int i = 0; i < userList.size(); i++) {
                        String plan_sups_id = userList.get(i).getPlan_sups_id();
                        String user_name = userList.get(i).getUser_name();

                        String image = userList.get(i).getImage();

                        String expert_plans_name = userList.get(i).getExpert_plans_name();
                        String expert_name = userList.get(i).getExpert_name();

                        usersList.add(new MyUser(plan_sups_id, user_name, image, expert_plans_name, expert_name));
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<MyUser>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }


    @Override
    public void onUserSelected(
            MyUser user) {
        // startActivity(new Intent(getApplicationContext(), AddPlanActivity.class));
    }
}
