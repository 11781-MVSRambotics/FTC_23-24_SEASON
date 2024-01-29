package org.firstinspires.ftc.teamcode.Subsystems;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.media.ImageReader;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

public enum CameraArray implements Subsystem { SINGLETON;

    private static CameraManager manager;

    String rightCamID;
    CameraDevice rightCam;
    ImageReader rightImageBuffer;
    CameraCaptureSession rightCaptureSession;

    String leftCamID;
    CameraDevice leftCam;
    ImageReader leftImageBuffer;
    CameraCaptureSession leftCaptureSession;

    @Override
    public boolean isInitialized() {
        return (rightCam != null && leftCam != null);
    }

    @Override
    public void initializeHardware(HardwareMap hardwareMap) {

        // In the case that there are no camera permissions, just crash and burn and I'll worry about it later
        if(ContextCompat.checkSelfPermission(hardwareMap.appContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            throw new RuntimeException("Robot controller doesn't have permissions to open camera devices");
        }
        manager = (CameraManager) hardwareMap.appContext.getSystemService(Context.CAMERA_SERVICE);

        try {
            for(String id : manager.getCameraIdList()) {
                if(Objects.equals(id, hardwareMap.get(WebcamName.class, "right_cam").getDeviceName())) { rightCamID = id; }
                else if(Objects.equals(id, hardwareMap.get(WebcamName.class, "left_cam").getDeviceName())) { leftCamID = id; }
            }

            manager.openCamera(rightCamID, Executors.newSingleThreadExecutor(), new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    rightCam = camera;

                    // This is god awful and I hate it but it's better than deprecated API warnings
                    // Also this try is nested in the other try but I don't think it counts because we are
                    // declaring and interface right now so it's a try in a try but they are catching the
                    // same fucking exception so idek how maybe java is just fucking stupid as usual idek
                    try {
                        rightCam.createCaptureSession(new SessionConfiguration(
                                SessionConfiguration.SESSION_REGULAR,
                                Collections.singletonList(new OutputConfiguration(rightImageBuffer.getSurface())),
                                Executors.newSingleThreadExecutor(),
                                new CameraCaptureSession.StateCallback() {
                                    @Override
                                    public void onConfigured(@NonNull CameraCaptureSession session) {
                                        rightCaptureSession = session;
                                    }

                                    @Override
                                    public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                                    }
                                }
                        ));
                    } catch (CameraAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    camera.close();
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {

                }
            });
            manager.openCamera(leftCamID, Executors.newSingleThreadExecutor(), new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    leftCam = camera;

                    // This is similarly awful but worse cause I had to type the same damn thing again
                    try {
                        leftCam.createCaptureSession(new SessionConfiguration(
                                SessionConfiguration.SESSION_REGULAR,
                                Collections.singletonList(new OutputConfiguration(leftImageBuffer.getSurface())),
                                Executors.newSingleThreadExecutor(),
                                new CameraCaptureSession.StateCallback() {
                                    @Override
                                    public void onConfigured(@NonNull CameraCaptureSession session) {
                                        leftCaptureSession = session;
                                    }

                                    @Override
                                    public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                                    }
                                }
                        ));
                    } catch (CameraAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    camera.close();
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {

                }
            });
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }


    }

    private void takeSyncedCapture() {

        /*
        *   Having written this, I feel sick
        *   This was not a worthwhile endeavor
        *   Not because it was difficult, no
        *   It is because this is horrendous
        *   A sorry excuse for code, a tragedy
        *   I am going to abolish living
        *   Because nothing good comes of it
        */


        CyclicBarrier barrier = new CyclicBarrier(2);

        Thread captureRight = new Thread(() -> {
            try {
                CaptureRequest request = rightCam.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE).build();
                barrier.await();
                rightCaptureSession.capture(request, new CameraCaptureSession.CaptureCallback() {
                    @Override
                    public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
                        super.onCaptureStarted(session, request, timestamp, frameNumber);
                    }
                }, Handler.createAsync(Looper.getMainLooper()));

            } catch (CameraAccessException | BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread captureLeft = new Thread(() -> {
            try {
                CaptureRequest request = leftCam.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE).build();
                barrier.await();
                leftCaptureSession.capture(request, new CameraCaptureSession.CaptureCallback() {
                    @Override
                    public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
                        super.onCaptureStarted(session, request, timestamp, frameNumber);
                    }
                }, Handler.createAsync(Looper.getMainLooper()));

            } catch (CameraAccessException | BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        captureRight.start();
        captureLeft.start();
    }
}
