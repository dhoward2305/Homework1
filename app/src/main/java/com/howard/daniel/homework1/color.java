package com.howard.daniel.homework1;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class color extends Fragment {

    private Random rnd = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.color, container, false);


        Button button1 = (Button) rootView.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int r = rnd.nextInt(256);
                int g = rnd.nextInt(256);
                int b = rnd.nextInt(256);

                int color = Color.argb(255, r, g, b);
                EditText changeText = (EditText) rootView.findViewById(R.id.changeText);
                TextView colorFind = (TextView) rootView.findViewById(R.id.colorFind);

                changeText.setTextColor(color);

                String color1 = "";
                color1 = Integer.toHexString(color);
                color1.trim().charAt(0);
                color1.trim().charAt(1);
                colorFind.setText("COLOR: " + r + "r " + g +"g " + b + "b " + "#" + color1.toUpperCase());

            }
        });
        return rootView;
    }
}
