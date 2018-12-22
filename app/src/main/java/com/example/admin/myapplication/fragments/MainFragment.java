package com.example.admin.myapplication.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.activity.DecoderActivity;
import com.skyfishjy.library.RippleBackground;


public class MainFragment extends Fragment {
    ImageView imgScan;
    Boolean isClicked = false;
    Boolean isPermitted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingTimer();

    }

    /**
     * this method is used to set timer for audio play
     */
    private void settingTimer() {
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                if (isClicked == false)
                    audio();
            }

        }.start();
    }

    /**
     * this method is used for playing audio to click on the icon
     */
    private void audio() {
        MediaPlayer mPlayer = MediaPlayer.create(getContext(), R.raw.voice);
        mPlayer.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        imgScan = view.findViewById(R.id.img_qrscan);
        rippleEffect(view);
        imgScan.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           isClicked = true;
                                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                               if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                                   requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 1);


                                               } else {

                                                   Intent intent = new Intent(getActivity(), DecoderActivity

                                                           .class);
                                                   getActivity().startActivityForResult(intent, 10);
                                               }

                                           } else {
                                               Intent intent = new Intent(getActivity(), DecoderActivity

                                                       .class);
                                               getActivity().startActivityForResult(intent, 10);
                                           }
                                       }
                                   }
        );
        isClicked = false;
        return view;

    }

    /**
     * this method is used for making ripple effect on the screen
     */
    private void rippleEffect(View view) {
        final RippleBackground rippleBackground = (RippleBackground) view.findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults this method used to check whether permission is granted or not
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isPermitted = true;

                Toast.makeText(getContext(), "permission granted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DecoderActivity

                        .class);
                getActivity().startActivityForResult(intent, 10);

            } else {

                Toast.makeText(getContext(), "permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
