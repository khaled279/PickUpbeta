package com.ksabri.pickupbeta;

import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksabri.pickupbeta.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
        ConstraintLayout dotsLayout ;
         ImageView[] dots ;
         TextView txt ;


//         private int index ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        dotsLayout = findViewById(R.id.dotslayout);
        creatDots(0);
        changeText(0);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Login.class);
                startActivity(intent);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                creatDots(i);
                changeText(i);

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
private  void creatDots(int currentPosition){

        dots = new ImageView[2] ;
        for (int i =0 ; i<dots.length ; i++){
          switch (i) {
              case 0:     dots[i] = (ImageView) findViewById(R.id.dot1);

              break;
              case 1: dots[i] = findViewById(R.id.dot2);
                  Log.d("follow up", "creatDots: "+ dots[i].getId());
              break;
          }
            if (i == currentPosition){
                dots[i].setBackgroundResource(R.drawable.dot);
            }else dots[i].setBackgroundResource(R.drawable.white_dot);


        }}
       private void changeText(int i){
           txt = findViewById(R.id.textView2) ;
            switch (i){
                case 0: txt.setText(R.string.skip);
                break;
                case 1: txt.setText(R.string.done);

            }
    }


}