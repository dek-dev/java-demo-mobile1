package com.oppo.marketdemo.custom;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MySensorEventListener implements SensorEventListener {
    float[] accelerometerValues = new float[3];
    float[] magneticValues = new float[3];
    OnEventClickListener listener ;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // 判断当前是加速度感应器还是地磁感应器
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //赋值调用clone方法
            accelerometerValues = sensorEvent.values.clone();
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            //赋值调用clone方法
            magneticValues = sensorEvent.values.clone();
        }
        float[] R = new float[9];
        float[] values = new float[3];

        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
        SensorManager.getOrientation(R, values);

        StringBuilder sb = new StringBuilder();
        sb.append("X方向的加速度：");
        sb.append(values[0]);
        sb.append("\nY方向的加速度：");
        sb.append(values[1]);
        sb.append("\nZ方向的加速度：");
        sb.append(values[2]);

        double degreeZ = Math.toDegrees(values[0]);
        double degreeX = Math.toDegrees(values[1]);
        double degreeY = Math.toDegrees(values[2]);
        sb.append("X方向的角度：");
        sb.append(degreeX);
        sb.append("\nY方向的加速度：");
        sb.append(degreeY);
        sb.append("\nZ方向的加速度：");
        sb.append(degreeZ);
        if(listener != null){
            listener.onOrientationChange(degreeX, degreeY, degreeZ);
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void GetDegreeListener(OnEventClickListener listener) {
        this.listener = listener;
    }

    public interface OnEventClickListener {
        void onOrientationChange(double degreeX, double degreeY, double degreeZ);

    }
}
