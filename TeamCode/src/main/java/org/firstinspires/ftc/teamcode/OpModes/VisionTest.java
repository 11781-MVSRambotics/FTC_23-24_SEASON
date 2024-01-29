package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Subsystems.CameraArray;

@Autonomous
public class VisionTest extends OpMode {

    @Override
    public void init() {
        CameraArray.SINGLETON.initializeHardware(hardwareMap);
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
