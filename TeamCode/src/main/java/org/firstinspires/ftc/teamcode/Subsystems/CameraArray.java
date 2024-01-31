package org.firstinspires.ftc.teamcode.Subsystems;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionPortalImpl;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraException;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.easyopencv.TimestampedOpenCvPipeline;
import org.tensorflow.lite.support.common.TensorProcessor;

import java.util.List;


public enum CameraArray implements Subsystem {SINGLETON;

    private static boolean isStreaming = true;

    @SuppressLint("SdCardPath")
    private static final String TFOD_MODEL_ASSET = "/sdcard/FIRST/tflitemodels/BlooBoi_Proto.tflite";
    private static final String[] LABELS = {"Blooboi"};

    private static VisionPortal rightPortal;
    private static VisionPortal leftPortal;

    private static AprilTagProcessor atProcessor;
    private static TfodProcessor tfProcessor;

    @Override
    public void initializeHardware(HardwareMap hardwareMap) {

        startProcessors();

        int[] previewIDs = VisionPortal.makeMultiPortalView(2, VisionPortal.MultiPortalLayout.HORIZONTAL);

        rightPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "rightCam"))
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setLiveViewContainerId(previewIDs[0])
                .addProcessor(tfProcessor)
                .build();
        leftPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "leftCam"))
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setLiveViewContainerId(previewIDs[1])
                .addProcessor(atProcessor)
                .build();
    }

    private static void startProcessors() {
        atProcessor = new AprilTagProcessor.Builder() // Figure this out later
                .build();

        tfProcessor = new TfodProcessor.Builder()
                .setModelFileName(TFOD_MODEL_ASSET)
                .setMaxNumRecognitions(1)
                .setModelLabels(LABELS)
                .build();
        tfProcessor.setMinResultConfidence(0.8f);
    }

    public static void stopStreaming() {
        rightPortal.stopLiveView();
        leftPortal.stopLiveView();

        rightPortal.stopStreaming();
        leftPortal.stopStreaming();

        isStreaming = false;
    }

    public static void resumeStreaming() {
        rightPortal.resumeStreaming();
        leftPortal.resumeStreaming();

        rightPortal.resumeLiveView();
        leftPortal.resumeLiveView();

        isStreaming = true;
    }

    public static boolean isSteaming() {
        return isStreaming;
    }
}
