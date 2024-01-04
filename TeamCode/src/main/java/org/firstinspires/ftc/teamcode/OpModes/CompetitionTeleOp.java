package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@TeleOp
@SuppressWarnings("unused")
public class CompetitionTeleOp extends OpMode {

    CRServo flywheelLeft, flywheelRight;
    Servo wristLeft, wristRight, latchLeft, latchRight;
    DcMotorEx linkageMotor;

    boolean latchOpen, wristUp = false;

    @Override
    public void init()
    {
        Robot.initializeSubsystems(hardwareMap);

        linkageMotor = hardwareMap.get(DcMotorEx.class, "linkageMotor");
        flywheelLeft = hardwareMap.get(CRServo.class, "flywheelLeft");
        flywheelRight = hardwareMap.get(CRServo.class, "flywheelRight");
        wristLeft = hardwareMap.get(Servo.class, "wrightLeft");
        wristRight = hardwareMap.get(Servo.class, "wrightRight");
        latchLeft = hardwareMap.get(Servo.class, "latchLeft");
        latchRight = hardwareMap.get(Servo.class, "latchRight");

    }

    @Override
    public void loop()
    {
        Drivetrain.SINGLETON.MoveTeleOp(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad1.b)
        {
            if (latchOpen)
            {
                latchLeft.setPosition(1);
                latchRight.setPosition(1);
                latchOpen = true;
            }
            else
            {
                latchLeft.setPosition(0);
                latchRight.setPosition(0);
                latchOpen = false;
            }
        }

        if (gamepad1.y)
        {
            flywheelLeft.setPower(0.5);
            flywheelRight.setPower(0.5);
        }
        else
        {
            flywheelLeft.setPower(0);
            flywheelRight.setPower(0);
        }

        if (gamepad1.a)
        {
            if (wristUp)
            {
                wristLeft.setPosition(1);
                wristRight.setPosition(1);
                wristUp = false;
            }
            else
            {
                wristLeft.setPosition(0);
                wristRight.setPosition(0);
                wristUp = true;
            }
        }

        if (gamepad1.right_trigger > 0)
        {
            linkageMotor.setPower(0.5);
        }


        if (gamepad1.left_trigger > 0)
        {
            linkageMotor.setPower(-0.5);
        }

    }
}
