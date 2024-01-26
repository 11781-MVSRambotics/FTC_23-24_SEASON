package org.firstinspires.ftc.teamcode.OpModes;

import android.hardware.camera2.CameraAccessException;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.CameraArray;

@Autonomous
public class VisionTest extends OpMode {

    CameraArray c;
    long averageTimeDifference = -1;

    @Override
    public void init() {
        c = new CameraArray();
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

        FtcDashboard.getInstance().getTelemetry().addData("CameraManager webcams", 0);
        FtcDashboard.getInstance().getTelemetry().update();
    }

    @Override
    public void stop() {
        FtcDashboard.getInstance().clearTelemetry();
    }
}
