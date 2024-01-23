package org.firstinspires.ftc.teamcode.OpModes;

import android.annotation.SuppressLint;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@Autonomous
public class CompetitionAutonomousLeft extends OpMode {

    Servo wristLeft, wristRight, latchLeft, latchRight, planeLatch, intakeLeft, intakeRight;;
    DcMotorEx linkageMotorLeft, linkageMotorRight;
    VisionPortal vp;
    TfodProcessor tf;

    ElapsedTime pain = new ElapsedTime();

    @SuppressLint("SdCardPath")
    public static final String TFOD_MODEL_ASSET = "/sdcard/FIRST/tflitemodels/BlooBoi_Proto.tflite";
    public static final String[] LABELS = {"BLooboi"};

    @Override
    public void init() {
        Robot.initializeSubsystems(hardwareMap);

        linkageMotorLeft = hardwareMap.get(DcMotorEx.class, "linkageMotorLeft");
        linkageMotorRight = hardwareMap.get(DcMotorEx.class, "linkageMotorRight");
        intakeLeft = hardwareMap.get(Servo.class, "intakeLeft");
        intakeRight = hardwareMap.get(Servo.class, "intakeRight");
        wristLeft = hardwareMap.get(Servo.class, "wristLeft");
        wristRight = hardwareMap.get(Servo.class, "wristRight");
        latchLeft = hardwareMap.get(Servo.class, "latchLeft");
        latchRight = hardwareMap.get(Servo.class, "latchRight");
        planeLatch = hardwareMap.get(Servo.class, "planeLatch");

        wristRight.setDirection(Servo.Direction.FORWARD);
        wristLeft.setDirection(Servo.Direction.REVERSE);

        latchRight.setDirection(Servo.Direction.FORWARD);
        latchLeft.setDirection(Servo.Direction.REVERSE);

        intakeRight.setDirection(Servo.Direction.FORWARD);
        intakeLeft.setDirection(Servo.Direction.REVERSE);

        linkageMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linkageMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        linkageMotorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        linkageMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linkageMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        linkageMotorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        linkageMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linkageMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        tf = new TfodProcessor.Builder()
                .setModelFileName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .setMaxNumRecognitions(1)
                .build();
        tf.setMinResultConfidence(0.8f);

        vp = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "cam1"))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .setCameraResolution(new Size(640, 480))
                .enableLiveView(false)
                .addProcessor(tf)
                .build();


    }

    @Override
    public void loop() {
        while(pain.time() - pain.startTime() < 3) {
            Drivetrain.SINGLETON.MoveTeleOp(-1, 0, 0); // Left
        }
    }
}
