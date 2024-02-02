package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.CustomMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public enum Drivetrain implements Subsystem {SINGLETON;

    public static SampleMecanumDrive drivetrain;

    @Override
    public void initializeHardware(HardwareMap hardwareMap) {
        drivetrain = new SampleMecanumDrive(hardwareMap);
    }

    public static void MoveTeleOp(double x, double y, double yaw)
    {
        // Converting from joystick convention to roadrunner convention
        // ↳ positive x is forward, positive y is left, positive heading is counterclockwise
        Pose2d rrDirection = new Pose2d(-y, -x, -yaw);

        drivetrain.setWeightedDrivePower(rrDirection);
    }
}
