package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.CameraArray;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@TeleOp
public class VisionTest extends OpMode {
    @Override
    public void init() {
        Robot.initializeSubsystems(hardwareMap);
    }

    @Override
    public void loop() {

        TfodProcessor t = TfodProcessor.easyCreateWithDefaults();

        telemetry.addData("Right cam fps: ", CameraArray.vp_right.getFps());
        telemetry.addData("Left cam fps: ", CameraArray.vp_left.getFps());
    }
}
