package com.gufran.wizardcheckpointapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    WizardCheckpointView wizardCheckpointView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wizardCheckpointView = (WizardCheckpointView) findViewById(R.id.wizardCheckpointView);
    }

    public void onClick(View v) {
        wizardCheckpointView.setState(0);
        wizardCheckpointView.startProgress(30);
    }

    public void onClick2(View v) {
        wizardCheckpointView.startProgress(90);
    }
}
