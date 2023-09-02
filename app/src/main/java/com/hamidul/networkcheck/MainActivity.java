package com.hamidul.networkcheck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button,buttonCancel,buttonExit;
    TextView toolBar;
    TextToSpeech textToSpeech;
    Animation zoomIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        toolBar = findViewById(R.id.toolBar);
        zoomIn = AnimationUtils.loadAnimation(MainActivity.this,R.anim.zoom_in);
        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });

        //Check Internet----------------------------------------------------------------------------------------------
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //=============================================================================================================

        if (networkInfo!=null && networkInfo.isConnected()){
            Toast.makeText(this, "Internet Connected", Toast.LENGTH_LONG).show();
        }else {
            new android.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("No Internet!!")
                    .setIcon(R.drawable.no_internet_icon)
                    .setMessage("Please connect to internet")
                    .show();
        }

        editText.addTextChangedListener(watcher);
        editText.setOnTouchListener(touchListener);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(editText.getText().toString(),TextToSpeech.QUEUE_FLUSH,null,null);
            }
        });


    }
    private void alertDialog(){
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.alertdialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);

        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonExit = view.findViewById(R.id.buttonExit);

        final AlertDialog alertDialog = builder.create();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textToSpeech.stop();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        alertDialog();
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            button.setEnabled(!editText.getText().toString().isEmpty());
            if (before>count){
                textToSpeech.stop();
            }else {
                textToSpeech.stop();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            toolBar.startAnimation(zoomIn);
            return false;
        }
    };

}