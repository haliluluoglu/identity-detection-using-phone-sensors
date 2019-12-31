package com.example.sensordata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener2 {
    SensorManager manager;

    Button buttonStart;
    Button buttonStop;
    boolean isRunning;

    final String TAG = "SensorLog";
    FileWriter writerAccelerometerX;
    FileWriter writerAccelerometerY;
    FileWriter writerAccelerometerZ;
    FileWriter writerGyroX;
    FileWriter writerGyroY;
    FileWriter writerGyroZ;
    FileWriter writerMagneticX;
    FileWriter writerMagneticY;
    FileWriter writerMagneticZ;
    FileWriter writerRotationX;
    FileWriter writerRotationY;
    FileWriter writerRotationZ;
    FileWriter writerLight;

    CheckBox accelerometerSensorCB;
    CheckBox gyroscopeSensorCB;
    CheckBox magneticSensorCB;
    CheckBox rotationSensorCB;
    CheckBox lightSensorCB;

    Sensor accelerometerSensor;
    Sensor gyroscopeSensor;
    Sensor magneticSensor;
    Sensor rotationSensor;
    Sensor lightSensor;

    TextView accelerometerTextViewX;
    TextView accelerometerTextViewY;
    TextView accelerometerTextViewZ;
    TextView gyroTextViewX;
    TextView gyroTextViewY;
    TextView gyroTextViewZ;
    TextView magneticTextViewX;
    TextView magneticTextViewY;
    TextView magneticTextViewZ;
    TextView rotationTextViewX;
    TextView rotationTextViewY;
    TextView rotationTextViewZ;
    TextView lightTextView;

    String modelName = Build.MANUFACTURER + Build.MODEL;
    int numberOfData;
    int accelerometerCounter=1;
    int gyroCounter=1;
    int magneticCounter=1;
    int rotationCounter=1;
    int lightCounter=1;
    int temp;
    EditText aNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isRunning = false;

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        buttonStart = (Button)findViewById(R.id.buttonStart);
        buttonStop = (Button)findViewById(R.id.buttonStop);

        accelerometerSensorCB = (CheckBox) findViewById(R.id.accelerometerSensorCheckBox);
        gyroscopeSensorCB = (CheckBox) findViewById(R.id.gyroscopeSensorCheckBox);
        magneticSensorCB = (CheckBox) findViewById(R.id.magneticSensorCheckBox);
        rotationSensorCB = (CheckBox) findViewById(R.id.rotationSensorCheckBox);
        lightSensorCB = (CheckBox) findViewById(R.id.lightSensorCheckBox);


        accelerometerSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        magneticSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        rotationSensor = manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        lightSensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);

        aNumber = findViewById(R.id.numberOfDataEditText);

        accelerometerTextViewX= (TextView) findViewById(R.id.accelerometerTWX);
        accelerometerTextViewY= (TextView) findViewById(R.id.accelerometerTWY);
        accelerometerTextViewZ= (TextView) findViewById(R.id.accelerometerTWZ);
        gyroTextViewX= (TextView) findViewById(R.id.gyroTWX);
        gyroTextViewY= (TextView) findViewById(R.id.gyroTWY);
        gyroTextViewZ= (TextView) findViewById(R.id.gyroTWZ);
        magneticTextViewX= (TextView) findViewById(R.id.magneticTWX);
        magneticTextViewY= (TextView) findViewById(R.id.magneticTWY);
        magneticTextViewZ= (TextView) findViewById(R.id.magneticTWZ);
        rotationTextViewX= (TextView) findViewById(R.id.rotationTWX);
        rotationTextViewY= (TextView) findViewById(R.id.rotationTWY);
        rotationTextViewZ= (TextView) findViewById(R.id.rotationTWZ);
        lightTextView= (TextView) findViewById(R.id.lightTW);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "Writing to " + getStorageDir());
        buttonStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);
                isRunning=true;
                manager.registerListener(MainActivity.this,accelerometerSensor,manager.SENSOR_DELAY_FASTEST);
                manager.registerListener(MainActivity.this,gyroscopeSensor,manager.SENSOR_DELAY_FASTEST);
                manager.registerListener(MainActivity.this,magneticSensor,manager.SENSOR_DELAY_FASTEST);
                manager.registerListener(MainActivity.this,rotationSensor,manager.SENSOR_DELAY_FASTEST);
                manager.registerListener(MainActivity.this,lightSensor,manager.SENSOR_DELAY_FASTEST);
                numberOfData = Integer.parseInt(aNumber.getText().toString());


                try {

                    writerAccelerometerX = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_AccelerometerX" + ".txt"));
                    writerAccelerometerY = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_AccelerometerY" + ".txt"));
                    writerAccelerometerZ = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_AccelerometerZ" + ".txt"));
                    writerGyroX = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_GyroX" + ".txt"));
                    writerGyroY = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_GyroY" + ".txt"));
                    writerGyroZ = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_GyroZ" + ".txt"));
                    writerMagneticX = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_MagneticX" + ".txt"));
                    writerMagneticY = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_MagneticY" + ".txt"));
                    writerMagneticZ = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_MagneticZ" + ".txt"));
                    writerRotationX = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_RotationX" + ".txt"));
                    writerRotationY = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_RotationY" + ".txt"));
                    writerRotationZ = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_RotationZ" + ".txt"));
                    writerLight = new FileWriter(new File(getStorageDir(), modelName + "_Sensor_Data_Light" + ".txt"));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });

        buttonStop.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent motionEvent) {

                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
                isRunning = false;

                manager.flush(MainActivity.this);
                manager.unregisterListener(MainActivity.this);
                try {
                    writerAccelerometerX.close();
                    writerAccelerometerY.close();
                    writerAccelerometerZ.close();
                    writerGyroX.close();
                    writerGyroY.close();
                    writerGyroZ.close();
                    writerMagneticX.close();
                    writerMagneticY.close();
                    writerMagneticZ.close();
                    writerRotationX.close();
                    writerRotationY.close();
                    writerRotationZ.close();
                    writerLight.close();
                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
                    String aMessage;
                    System.out.println("Counters"+accelerometerCounter+gyroCounter+magneticCounter+rotationCounter+lightCounter);
                    if(temp>5)
                    {
                        aBuilder.setTitle("Program has been successfully terminated.");
                        aMessage="Manufactor and model name: " + modelName + "\n\n";
                        aMessage+= numberOfData + " number of sensor data has been created from " + getStorageDir();
                        aBuilder.setMessage(aMessage);
                        aBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }
                    else
                    {
                        aBuilder.setTitle("Something happened!");
                        aBuilder.setMessage("Program exited.Try again,please.");
                        aBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }
                    AlertDialog alertDialog = aBuilder.create();
                    alertDialog.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    private String getStorageDir() {
        return this.getExternalFilesDir(null).getAbsolutePath();
    }


    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onSensorChanged(SensorEvent evt) {
        if(isRunning) {

            if(accelerometerSensorCB.isChecked() && (evt.sensor.getType()==Sensor.TYPE_ACCELEROMETER) && (accelerometerCounter<=numberOfData))
            {
                try {
                    writerAccelerometerX.write(String.format("%f;", evt.values[0]));
                    writerAccelerometerY.write(String.format("%f;", evt.values[1]));
                    writerAccelerometerZ.write(String.format("%f;", evt.values[2]));
                    accelerometerTextViewX.setText(Float.toString(evt.values[0]));
                    accelerometerTextViewY.setText(Float.toString(evt.values[1]));
                    accelerometerTextViewZ.setText(Float.toString(evt.values[2]));
                    accelerometerCounter++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (gyroscopeSensorCB.isChecked() && (evt.sensor.getType()==Sensor.TYPE_GYROSCOPE) && (gyroCounter<=numberOfData))
            {
                try {
                    writerGyroX.write(String.format("%f;", evt.values[0]));
                    writerGyroY.write(String.format("%f;", evt.values[1]));
                    writerGyroZ.write(String.format("%f;", evt.values[2]));
                    gyroTextViewX.setText(Float.toString(evt.values[0]));
                    gyroTextViewY.setText(Float.toString(evt.values[1]));
                    gyroTextViewZ.setText(Float.toString(evt.values[2]));
                    gyroCounter++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (magneticSensorCB.isChecked() && (evt.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD) && (magneticCounter<=numberOfData))
            {
                try {
                    writerMagneticX.write(String.format("%f;",evt.values[0]));
                    writerMagneticY.write(String.format("%f;",evt.values[1]));
                    writerMagneticZ.write(String.format("%f;",evt.values[2]));
                    magneticTextViewX.setText(Float.toString(evt.values[0]));
                    magneticTextViewY.setText(Float.toString(evt.values[1]));
                    magneticTextViewZ.setText(Float.toString(evt.values[2]));
                    magneticCounter++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (rotationSensorCB.isChecked() && (evt.sensor.getType()==Sensor.TYPE_ROTATION_VECTOR) && (rotationCounter<=numberOfData))
            {
                try {
                    writerRotationX.write(String.format("%f;",evt.values[0]));
                    writerRotationY.write(String.format("%f;",evt.values[1]));
                    writerRotationZ.write(String.format("%f;",evt.values[2]));
                    rotationTextViewX.setText(Float.toString(evt.values[0]));
                    rotationTextViewY.setText(Float.toString(evt.values[1]));
                    rotationTextViewZ.setText(Float.toString(evt.values[2]));
                    rotationCounter++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (lightSensorCB.isChecked() && (evt.sensor.getType()==Sensor.TYPE_LIGHT) && (lightCounter<=numberOfData))
            {
                try {
                    writerLight.write(String.format("%f;", evt.values[0]));
                    lightTextView.setText(Float.toString(evt.values[0]));
                    lightCounter++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
            }
            temp=accelerometerCounter+gyroCounter+magneticCounter+rotationCounter+lightCounter;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}