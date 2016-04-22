package com.hybrid.tech.mylocationv4;

/**
 * Created by Admins on 4/19/2016.
 */
public class VariousThemes {

    private int imageID;

  public static final  VariousThemes [] themes= {new VariousThemes(R.drawable.theme),new VariousThemes(R.drawable.theme2),new VariousThemes(R.drawable.theme3),new VariousThemes(R.drawable.theme4),new VariousThemes(R.drawable.theme5)};

    public VariousThemes(int imageID)
    {
        this.imageID=imageID;
    }

    public int getId()
    {
        return imageID;
    }
}
