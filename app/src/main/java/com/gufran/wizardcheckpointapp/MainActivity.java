package com.gufran.wizardcheckpointapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //  WizardCheckpointView wizardCheckpointView;
    WizardLineView wizardLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // wizardCheckpointView = (WizardCheckpointView) findViewById(R.id.wizardCheckpointView);
        wizardLineView = (WizardLineView) findViewById(R.id.wizardLineView);
    }

    public void onClick(View v) {
        //  wizardCheckpointView.setState(2);
        // wizardCheckpointView.startProgress(90);
        wizardLineView.startProgress(30);
    }

    public void onClick2(View v) {
        //  wizardCheckpointView.startProgress(90);
        //  wizardCheckpointView.setState(1);
        wizardLineView.startProgress(90);
    }
}
