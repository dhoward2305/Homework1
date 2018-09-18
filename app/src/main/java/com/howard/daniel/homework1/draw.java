package com.howard.daniel.homework1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class draw extends Fragment
{
    private canvas canvasView;
    private int trm = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.draw, container, false);

        canvasView = (canvas) rootView.findViewById(R.id.canvas);

        Button cButton = (Button) rootView.findViewById(R.id.cButton);

        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasView.clearCanvas();
            }
        });

        final Button colorChange = (Button) rootView.findViewById(R.id.colorChange);

        colorChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.colorChange:
                        changeStrokeColor();
                        clearPath(view);
                        break;
                    default:
                        break;
                }

            }
        });

        Button saveButton = (Button) rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveBitmap(canvasView.mBitmap);
                } else {
                    requestStoragePermission();
                }
            }
        });

        return rootView;
    }

    public void clearPath(View v) {
        canvasView.clearPath();
    }

    public void changeStrokeColor() {

        final ColorPicker cp = new ColorPicker(getActivity(), 0, 0, 0);

        cp.show();

        cp.enableAutoClose();

        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {

                canvasView.changeColor(color);

            }
        });
    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission Needed")
                    .setMessage("Do you accept?")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, trm);
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == trm) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getContext(),
//                        "Granted " ,
//                        Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(getContext(),
//                        "Not Granted " ,
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private void saveBitmap(Bitmap bm){
        File file = Environment.getExternalStorageDirectory();
        File newFile = new File(file, "test.PNG");

        try {
            FileOutputStream fos = new FileOutputStream(newFile);
            boolean compress = bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(getContext(),
                    "Saved Image: " + fos.toString(),
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),
                    "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),
                    "Error1: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


}

