package org.firstinspires.ftc.teamcode.OpModes;

import android.graphics.Camera;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.CameraArray;

@Autonomous(name = "Camera Auto", preselectTeleOp = "TeleOp")
public class VisionAutonomous extends OpMode {

    @Override
    public void init() {
        Robot.initializeSubsystems(hardwareMap);
    }


    @Override
    public void loop() {

    }


}
