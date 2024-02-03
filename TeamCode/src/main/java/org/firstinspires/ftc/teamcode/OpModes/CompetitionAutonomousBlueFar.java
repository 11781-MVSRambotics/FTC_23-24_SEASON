package org.firstinspires.ftc.teamcode.OpModes;

import android.annotation.SuppressLint;
import android.util.Size;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.profile.VelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.CameraArray;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.PickleAccumulator;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@Autonomous
public class CompetitionAutonomousBlueFar extends OpMode {

    int estimatedGameElementPosition;
    int pos1Votes = 0;
    int pos2Votes = 0;
    int pos3Votes = 0;

    @Override
    public void init() {
        Robot.initializeSubsystems(hardwareMap);
    }

    @Override
    public void init_loop() {
        int currentEstimatedPos = CameraArray.getGameElementPosition();
        switch(currentEstimatedPos) {
            case 1:
                pos1Votes++;
                break;
            case 2:
                pos2Votes++;
                break;
            case 3:
                pos3Votes++;
                break;
        }
    }

    @Override
    public void start() {

        if(pos1Votes > pos2Votes && pos1Votes > pos3Votes) {
            estimatedGameElementPosition = 1;
        } else if(pos2Votes > pos1Votes && pos1Votes > pos3Votes) {
            estimatedGameElementPosition = 2;
        } else if(pos3Votes > pos1Votes && pos1Votes > pos2Votes) {
            estimatedGameElementPosition = 3;
        }

        Pose2d startPose = new Pose2d(0, 0, 0);
        Drivetrain.drivetrain.setPoseEstimate(startPose);

        /* Defines a filler int variable that will be which tape that the pickle starts at.
        1 is the tape straight ahead, 2 is to the left, 3 is to the right (This is just for testing)
        */
        int tapePos = 3;


        TrajectorySequence trajCenter = Drivetrain.drivetrain.trajectorySequenceBuilder(startPose)
                .forward(35)
                .build();

        TrajectorySequence trajLeft = Drivetrain.drivetrain.trajectorySequenceBuilder(startPose)
                .forward(30)
                .strafeRight(40)
                .forward(20)
                .strafeLeft(40)
                .build();

        TrajectorySequence trajRight = Drivetrain.drivetrain.trajectorySequenceBuilder(startPose)
                .forward(20)
                .setTurnConstraint(1, 1)
                .turn(Math.toRadians(-90))
                .forward(20)
                .back(10)
                .build();

        // A switch statement that will run a different trajectory based on the Tape
        switch (tapePos) {
            case 1:
                Drivetrain.drivetrain.followTrajectorySequence(trajCenter);
                break;
            case 2:
                Drivetrain.drivetrain.followTrajectorySequence(trajLeft);
                break;
            case 3:
                Drivetrain.drivetrain.followTrajectorySequence(trajRight);
                break;
        }
    }

    @Override
    public void loop() {

        }
}
