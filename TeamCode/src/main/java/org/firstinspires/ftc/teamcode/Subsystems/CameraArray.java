package org.firstinspires.ftc.teamcode.Subsystems;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibrationHelper;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibrationIdentity;
import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.calib3d.StereoSGBM;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.easyopencv.TimestampedOpenCvPipeline;


public enum CameraArray implements Subsystem {INSTANCE;

    private static final Size CAMERA_RESOLUTION = new Size(640, 480);

    private OpenCvWebcam rightCamera;
    private ProcessingPipeline rightPipeline = new ProcessingPipeline();
    private OpenCvWebcam leftCamera;
    private ProcessingPipeline leftPipeline = new ProcessingPipeline();
    private Mat depthMap;

    @Override
    public void initializeHardware(HardwareMap hardwareMap) {
        int[] ids = VisionPortal.makeMultiPortalView(2, VisionPortal.MultiPortalLayout.HORIZONTAL);

        rightCamera = (OpenCvWebcam) OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), ids[0]);
        leftCamera = (OpenCvWebcam) OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 2"), ids[1]);

        rightCamera.setViewportRenderer(OpenCvCamera.ViewportRenderer.NATIVE_VIEW);
        leftCamera.setViewportRenderer(OpenCvCamera.ViewportRenderer.NATIVE_VIEW);

        rightCamera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                rightCamera.startStreaming(CAMERA_RESOLUTION.getWidth(), CAMERA_RESOLUTION.getHeight(), OpenCvCameraRotation.UPRIGHT);
                rightCamera.setPipeline(rightPipeline);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        leftCamera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                leftCamera.startStreaming(CAMERA_RESOLUTION.getWidth(), CAMERA_RESOLUTION.getHeight(), OpenCvCameraRotation.UPRIGHT);
                leftCamera.setPipeline(leftPipeline);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

    }

    public void calculateDepthmap() {
        Mat r = rightPipeline.lastCaptureFrame;
        Mat l = leftPipeline.lastCaptureFrame;

        Mat rr = new Mat();
        Mat ll = new Mat();

        Imgproc.cvtColor(rr, r, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(ll, l, Imgproc.COLOR_BGR2GRAY);

        Mat disparity = new Mat(l.size(), l.type());

        int numDisparity = (int) (l.size().width/8);

        StereoSGBM stereoAlgorithmThing = StereoSGBM.create(
                0,
                numDisparity,
                11,
                2*11*11,
                5*11*11,
                -1,
                63,
                10,
                0,
                32,
                0
        );

        stereoAlgorithmThing.compute(l, r, disparity);

        Core.normalize(disparity, disparity, 0, 256, Core.NORM_MINMAX);
        depthMap = disparity;

        r.release();
        l.release();
        rr.release();
        ll.release();
        disparity.release();
    }

    public long getFrametimeError() {
        return Math.abs(rightPipeline.lastCaptureTime - leftPipeline.lastCaptureTime);
    }

    class ProcessingPipeline extends TimestampedOpenCvPipeline {

        public long lastCaptureTime = 0;
        public Mat lastCaptureFrame;

        @Override
        public Mat processFrame(Mat input, long captureTimeNanos) {

            lastCaptureTime = captureTimeNanos;
            lastCaptureFrame = input.clone();

            if(depthMap == null) return input;
            return depthMap;
        }
    }

}
