package org.firstinspires.ftc.teamcode.OpModes;

import android.annotation.SuppressLint;
import android.util.Size;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
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
import org.firstinspires.ftc.teamcode.Subsystems.PickleAccumulator;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@Autonomous
public class CompetitionAutonomousRedFar extends OpMode {

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

        /*
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


         */

    }

    @Override
    public void start() {

        Pose2d startPose = new Pose2d(0, 0, 0);
        Drivetrain.drivetrain.setPoseEstimate(startPose);

        /* Defines a filler int variable that will be which tape that the pickle starts at.
        1 is the tape straight ahead, 2 is to the left, 3 is to the right (This is just for testing)
        */
        int tapePos = 1;


        TrajectorySequence trajCenter = Drivetrain.drivetrain.trajectorySequenceBuilder(startPose)
                .forward(35)
                .build();

        TrajectorySequence trajRight = Drivetrain.drivetrain.trajectorySequenceBuilder(startPose)
                .forward(30)
                .strafeLeft(40)
                .forward(20)
                .strafeRight(40)
                .build();

        TrajectorySequence trajLeft = Drivetrain.drivetrain.trajectorySequenceBuilder(startPose)
                .forward(20)
                .setTurnConstraint(10, 1)
                .turn(Math.toRadians(90))
                .forward(23)
                .back(10)
                .build();

        // A switch statement that will run a different trajectory based on the Tape
        switch (tapePos) {
            case 1:
                Drivetrain.drivetrain.followTrajectorySequence(trajCenter);
            case 2:
                Drivetrain.drivetrain.followTrajectorySequence(trajLeft);
            case 3:
                Drivetrain.drivetrain.followTrajectorySequence(trajRight);

        }
    }

    @Override
    public void loop() {

    }

        /*
        Drivetrain.drivetrain.followTrajectory(traj1);
        PickleAccumulator.openIntake();
        telemetry.addData("position", Drivetrain.drivetrain.getPoseEstimate());
        telemetry.update();
        //Drivetrain.drivetrain.followTrajectory(traj2);
        telemetry.addData("position", Drivetrain.drivetrain.getPoseEstimate());
        telemetry.update();
        */

}
