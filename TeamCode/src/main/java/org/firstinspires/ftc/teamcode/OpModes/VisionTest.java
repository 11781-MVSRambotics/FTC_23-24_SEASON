package org.firstinspires.ftc.teamcode.OpModes;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.CameraArray;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous
public class VisionTest extends OpMode {

    long averageTimeDifference = -1;

    @Override
    public void init() {
        CameraArray.INSTANCE.initializeHardware(hardwareMap);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

        //CameraArray.INSTANCE.calculateDepthmap();
    }

    @Override
    public void stop() {
        FtcDashboard.getInstance().clearTelemetry();
    }
}
