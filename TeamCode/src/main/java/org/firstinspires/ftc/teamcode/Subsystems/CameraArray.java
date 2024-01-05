package org.firstinspires.ftc.teamcode.Subsystems;

import android.annotation.SuppressLint;
import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.tensorflow.lite.support.common.TensorProcessor;

import java.util.List;


public enum CameraArray implements Subsystem {SINGLETON;

    @SuppressLint("SdCardPath")
    public static final String TFOD_MODEL_ASSET = "/sdcard/FIRST/tflitemodels/BlooBoi_Proto.tflite";
    public static final String[] LABELS = {"BLooboi"};

    public static VisionPortal vp_left;
    public static VisionPortal vp_right;
    public static TfodProcessor tfod_left;
    public static TfodProcessor tfod_right;

    @Override
    public void initializeHardware(HardwareMap hardwareMap) {
        int[] ids = VisionPortal.makeMultiPortalView(2, VisionPortal.MultiPortalLayout.HORIZONTAL);

        tfod_left = new TfodProcessor.Builder()
                .setModelFileName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .setUseObjectTracker(false)
                .setMaxNumRecognitions(1)
                .build();
        vp_left = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "cam1"))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .setLiveViewContainerId(ids[0])
                .setCameraResolution(new Size(640, 480))
                .enableLiveView(false)
                .addProcessor(tfod_left)
                .build();
        tfod_left.setMinResultConfidence(0.8f);

        tfod_right = new TfodProcessor.Builder()
                .setModelFileName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .setUseObjectTracker(false)
                .setMaxNumRecognitions(1)
                .build();
        vp_right = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "cam2"))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .setLiveViewContainerId(ids[1])
                .setCameraResolution(new Size(640, 480))
                .enableLiveView(false)
                .addProcessor(tfod_right)
                .build();
        tfod_right.setMinResultConfidence(0.8f);
    }
}
