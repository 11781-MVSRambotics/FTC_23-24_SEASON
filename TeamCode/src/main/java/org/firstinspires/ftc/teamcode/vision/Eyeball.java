package org.firstinspires.ftc.teamcode.vision;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.CameraManagerInternal;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.openftc.easyopencv.OpenCvCamera;

import java.util.concurrent.TimeUnit;

public class Eyeball {
    String cameraId = null;
    CameraManager manager;
    CameraDevice cameraDevice;

    public Eyeball(HardwareMap hardwareMap) {
        manager = (CameraManager) hardwareMap.appContext.getSystemService(Context.CAMERA_SERVICE);

        try {
            for (String id : manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(id);

                // Check if LENS_FACING is not null before comparison
                Integer lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                    cameraId = id;
                    break;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    public void openThing() {

        CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                cameraDevice = camera;
                // Do something after the camera is opened
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {
                camera.close();
                cameraDevice = null;
            }

            @Override
            public void onError(@NonNull CameraDevice camera, int error) {
                camera.close();
                cameraDevice = null;
            }
        };

        try {
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException | SecurityException e) {
            e.printStackTrace();
        }
    }
}
