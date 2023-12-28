package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@TeleOp
@SuppressWarnings("unused")
public class CompetitionTeleOp extends OpMode {

    @Override
    public void init()
    {
        Robot.initializeSubsystems(hardwareMap);
    }

    @Override
    public void loop()
    {
        Drivetrain.SINGLETON.MoveTeleOp(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
    }
}
