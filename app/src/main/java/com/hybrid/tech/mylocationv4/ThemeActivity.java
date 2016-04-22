package com.hybrid.tech.mylocationv4;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaActionSound;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ThemeActivity extends Activity {
public static String myTheme;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
    relativeLayout=(RelativeLayout)findViewById(R.id.main_layout);
    }

    public void onThemeClick(View view)
    {

       // Toast.makeText(this,"Button Clicked "+view.toString(),Toast.LENGTH_SHORT).show();
                                //   String name;
                                    //Intent intent;
                        //ImageButton button=(ImageButton)view;
        switch(view.getId())
        {
            case R.id.imageButton:
             //   Toast.makeText(this,"Last Button Clicked",Toast.LENGTH_SHORT).show();

               // resource.toString();
             //  Toast.makeText(this,"Drawable "+button.getTag().toString(),Toast.LENGTH_SHORT).show();
                //relativeLayout=(RelativeLayout)//
              // main.findViewById(R.id.main_layout).setBackground(drawable);
                                                        //        name="theme2";
                                                            //int i=R.drawable.theme2;
            //    button.getResources().getColor(i);
             //   relativeLayout.setBackgroundColor(button.getResources().getColor(i));
                                                            //intent = new Intent(this, MainActivity.class);
                                                            //     intent.putExtra(MainActivity.EXTRA_MESSAGE,name);

                                                              //                startActivity(intent);
             //   relativeLayout.setBackground(drawable);

                myTheme="theme2";
               break;

            case R.id.imageButton2:
              //  name="theme";
                myTheme="theme";
              //  intent = new Intent(this, MainActivity.class);
                //intent.putExtra(MainActivity.EXTRA_MESSAGE,name);
                //startActivity(intent);
                break;

            case R.id.imageButton3:
              //  name="theme4";
                //intent = new Intent(this, MainActivity.class);
                //intent.putExtra(MainActivity.EXTRA_MESSAGE, name);
                //startActivity(intent);

                myTheme="theme4";
                break;

            case R.id.imageButton4:
              //  name="theme3";
               // intent = new Intent(this, MainActivity.class);
                //intent.putExtra(MainActivity.EXTRA_MESSAGE,name);
                //startActivity(intent);

                myTheme="theme3";
                break;

            case R.id.imageButton6:
             //   name="theme5";
              //  intent = new Intent(this, MainActivity.class);
              //  intent.putExtra(MainActivity.EXTRA_MESSAGE,name);
               // startActivity(intent);
                myTheme="theme5";
                break;
        }
        finish();
    }


    public static String getMyTheme()
    {
        return myTheme;
    }
}
