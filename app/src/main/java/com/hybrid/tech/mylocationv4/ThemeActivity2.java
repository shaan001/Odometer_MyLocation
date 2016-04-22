package com.hybrid.tech.mylocationv4;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class ThemeActivity2 extends Activity {
    public static String myTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme2);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        int[] imageid = new int[VariousThemes.themes.length];
        for (int i = 0; i < imageid.length; i++) {
            imageid[i] = VariousThemes.themes[i].getId();
        }
        MyThemeAdapter adapter=new MyThemeAdapter(imageid);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);


        adapter.setListener(new MyThemeAdapter.Listener() {
            //int j=0;
            @Override
            public void onClick(int position) {
                switch (position)
                {
                    case 0:
                        myTheme="theme";
                     //   Toast.makeText(ThemeActivity2.this,"Inside switch "+myTheme,Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        myTheme="theme2";
                        break;
                    case 2:
                        myTheme="theme3";
                        break;
                    case 3:
                        myTheme="theme4";
                        break;
                    case 4:
                        myTheme="theme5";
                        break;
                }
             //   Toast.makeText(ThemeActivity2.this,"now i am on the OUtSIDE "+myTheme+" "+Integer.toString(position),Toast.LENGTH_SHORT).show();
                finish();
                }
        });

    }

    public static String getMyTheme2()
    {
        return myTheme;
    }
}



