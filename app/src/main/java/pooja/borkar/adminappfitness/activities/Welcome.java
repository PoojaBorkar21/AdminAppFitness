package pooja.borkar.adminappfitness.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import pooja.borkar.adminappfitness.R;

public class Welcome extends AppCompatActivity {
    ImageView imageView;

    Thread timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        imageView=findViewById(R.id.imageView_splash);
        //Glide.with(this).load(R.drawable.logo_transparent).into(imageView);


        timer=new Thread()

        {
            public void run()
            {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                    Intent intent_my = new Intent(getApplicationContext(),AppFeatureActivity.class);
                    startActivity(intent_my);
                }
            }

        };
        timer.start();
    }
}