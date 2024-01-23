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

    static OpenCvWebcam leftCam;
    static OpenCvWebcam rightCam;

    @Override
    public void initializeHardware(HardwareMap hardwareMap) {
        leftCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "leftCam"));
        rightCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "rightCam"));

    }

    private void requestBitmap(OpenCvWebcam cam) {
        
        Continuation<? extends Consumer<Bitmap>> continuation = Continuation.createTrivial(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap value) {

            }
        });
    }
}
