package org.firstinspires.ftc.teamcode.Subsystems;

import android.annotation.SuppressLint;
import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.HashMap;
import java.util.List;


public enum CameraArray implements Subsystem {SINGLETON;

    public enum ProcessorMode {
        TFOD,
        APRILTAGS
    }

    private static ProcessorMode mode;

    private static boolean isStreaming = true;

    @SuppressLint("SdCardPath")
    private static final String TFOD_MODEL_ASSET = "/sdcard/FIRST/tflitemodels/BlooBoi_Proto.tflite";
    private static final String[] LABELS = {"Blooboi"};

    private static VisionPortal rightPortal;
    private static VisionPortal leftPortal;

    private static AprilTagProcessor atProcessorRight;
    private static AprilTagProcessor atProcessorLeft;
    private static TfodProcessor tfProcessorRight;
    private static TfodProcessor tfProcessorLeft;

    @Override
    public void initializeHardware(HardwareMap hardwareMap) {

        startProcessors();

        int[] previewIDs = VisionPortal.makeMultiPortalView(2, VisionPortal.MultiPortalLayout.HORIZONTAL);

        rightPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "rightCam"))
                .setCameraResolution(new Size(1280, 720))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setLiveViewContainerId(previewIDs[0])
                .addProcessor(tfProcessorRight)
                .addProcessor(atProcessorRight)
                .build();
        leftPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "leftCam"))
                .setCameraResolution(new Size(1280, 720))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setLiveViewContainerId(previewIDs[1])
                .addProcessor(tfProcessorLeft)
                .addProcessor(atProcessorLeft)
                .build();

        rightPortal.setProcessorEnabled(tfProcessorRight, false);
        rightPortal.setProcessorEnabled(atProcessorRight, false);
        leftPortal.setProcessorEnabled(tfProcessorLeft, false);
        leftPortal.setProcessorEnabled(atProcessorLeft, false);

        setMode(ProcessorMode.TFOD);
    }

    private static void startProcessors() {
        atProcessorRight = new AprilTagProcessor.Builder() // Figure this out later
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setDrawTagID(true)
                .setDrawAxes(true)
                .build();
        atProcessorLeft = new AprilTagProcessor.Builder() // Figure this out later
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setDrawTagID(true)
                .setDrawAxes(true)
                .build();

        tfProcessorRight = new TfodProcessor.Builder()
                .setModelFileName(TFOD_MODEL_ASSET)
                .setUseObjectTracker(false)
                .setMaxNumRecognitions(1)
                .setModelLabels(LABELS)
                .build();
        tfProcessorLeft = new TfodProcessor.Builder()
                .setModelFileName(TFOD_MODEL_ASSET)
                .setUseObjectTracker(false)
                .setMaxNumRecognitions(1)
                .setModelLabels(LABELS)
                .build();
        tfProcessorRight.setMinResultConfidence(0.75f);
        tfProcessorLeft.setMinResultConfidence(0.75f);
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

    public static void setMode(ProcessorMode mode) {
        if(mode == ProcessorMode.APRILTAGS) {
            rightPortal.setProcessorEnabled(atProcessorRight, true);
            leftPortal.setProcessorEnabled(atProcessorLeft, true);

            rightPortal.setProcessorEnabled(tfProcessorRight, false);
            leftPortal.setProcessorEnabled(tfProcessorLeft, false);

            CameraArray.mode = ProcessorMode.APRILTAGS;
        } else if(mode == ProcessorMode.TFOD) {
            rightPortal.setProcessorEnabled(tfProcessorRight, true);
            leftPortal.setProcessorEnabled(tfProcessorLeft, true);

            rightPortal.setProcessorEnabled(atProcessorRight, false);
            leftPortal.setProcessorEnabled(atProcessorLeft, false);

            CameraArray.mode = ProcessorMode.TFOD;
        }
    }

    public static ProcessorMode getMode() {
        return CameraArray.mode;
    }

    public static int getGameElementPosition() {

        boolean isRight;
        boolean isLeft;
        boolean farRight = false;
        boolean farLeft = false;

        double rightPos;
        double leftPos;

        List<Recognition> rightRecognitions = tfProcessorRight.getRecognitions();
        isRight = !rightRecognitions.isEmpty();
        if(isRight) {
            rightPos = 1 - (rightRecognitions.get(0).getTop() + rightRecognitions.get(0).getBottom()) / (2 * rightRecognitions.get(0).getImageHeight());
            farRight = (rightPos > 0.5);
        }

        List<Recognition> leftRecognitions = tfProcessorLeft.getRecognitions();
        isLeft = !leftRecognitions.isEmpty();
        if(isLeft) {
            leftPos = 1 - (leftRecognitions.get(0).getTop() + leftRecognitions.get(0).getBottom()) / (2 * leftRecognitions.get(0).getImageHeight());
            farLeft = (leftPos < 0.5);
        }

        if(isRight && isLeft) {
            if(farRight && farLeft){
                if(rightRecognitions.get(0).getConfidence() > leftRecognitions.get(0).getConfidence()) {
                    return 3;
                } else {
                    return 2;
                }
            }
        }

        if(farRight) {
            return 3;
        }

        if(farLeft){
            return 2;
        }

        return 1;

    }

    public static boolean isSteaming() {
        return isStreaming;
    }
}
