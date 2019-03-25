package com.fjz.demo.androidx.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fjz.demo.androidx.R;
import com.fjz.demo.androidx.view.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.orhanobut.logger.Logger;

import java.util.Arrays;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Logger.i("getInstanceId failed:" + task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Logger.i("fcm token = %s", token);
                        Toast.makeText(TestActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void onBtn(View view) {


        int color1 = 0xffddff55;
        float[] hsv = new float[3];

        Color.RGBToHSV(
                Color.red(color1),
                Color.green(color1),
                Color.blue(color1),
                hsv
        );

        System.out.println(Arrays.toString(hsv));
        hsv[1] -= 0.1;
        hsv[2] += 0.2;
        System.out.println(Arrays.toString(hsv));
        int color2 = Color.HSVToColor(hsv);

        View view1 = findViewById(R.id.view_1);
        view1.setBackgroundColor(color1);
        System.out.println("color 1 = " + color1);
        System.out.println("color 2 = " + color1);
        View view2 = findViewById(R.id.view_2);
        view2.setBackgroundColor(color2);

        CountDownTimer timer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("millisUntilFinished = " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                System.out.println("count down timer finished!");
            }
        };
        timer.start();
    }
}
