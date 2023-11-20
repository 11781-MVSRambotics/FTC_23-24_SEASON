package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robot;

@SuppressWarnings("unused")
@TeleOp
public class TeleOpForShiggles extends OpMode {

    DcMotorEx FrontRightMotor;
    DcMotorEx FrontLeftMotor;
    DcMotorEx BackRightMotor;
    DcMotorEx BackLeftMotor;

    int power = 5;
    boolean powerChangedFlag = false;

    @Override
    public void init()
    {
        FrontLeftMotor = hardwareMap.get(DcMotorEx.class, "FrontLeftMotor");
        FrontRightMotor = hardwareMap.get(DcMotorEx.class, "FrontRightMotor");
        BackRightMotor = hardwareMap.get(DcMotorEx.class, "BackRightMotor");
        BackLeftMotor = hardwareMap.get(DcMotorEx.class, "BackLeftMotor");

        Robot.initializeSubsystems(hardwareMap);
    }



    @Override
    public void init_loop()
    {

    }

    @Override
    public void start()
    {
        // Reversing the necessary motors so that the signs of power values match rotational direction
        FrontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Configure motors so stop when not under load to avoid coasting during TeleOp
        FrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop()
    {
        if(gamepad1.dpad_up && power < 10 && !powerChangedFlag)
        {
            power += 1;
            powerChangedFlag = true;
            telemetry.addLine("Power increased");
        }
        else if(gamepad1.dpad_down && power > 1 && !powerChangedFlag)
        {
            power -= 1;
            powerChangedFlag = true;
            telemetry.addLine("Power decreased");
        }
        else if(!gamepad1.dpad_up && !gamepad1.dpad_down){
            powerChangedFlag = false;
        }


        // Maximum power value so we can normalize the power vectors
        double max;
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double yaw = gamepad1.right_stick_x * -1;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double frontLeftPower  = y + x + yaw;
        double frontRightPower = y - x - yaw;
        double backLeftPower   = y - x + yaw;
        double backRightPower  = y + x - yaw;

        // Check which motor has received the maximum power value
        max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        // Normalize all motor powers as a percentage of the maximum
        if (max > 1.0) {
            frontLeftPower  /= max;
            frontRightPower /= max;
            backLeftPower   /= max;
            backRightPower  /= max;
        }
        // Scale the normalized value to the desired power percentage
        frontLeftPower  *= ((double) power /10);
        frontRightPower *= ((double) power /10);
        backLeftPower   *= ((double) power /10);
        backRightPower  *= ((double) power /10);

        // Send calculated power to wheels
        FrontRightMotor.setPower(frontRightPower);
        FrontLeftMotor.setPower(frontLeftPower);
        BackRightMotor.setPower(backRightPower);
        BackLeftMotor.setPower(backLeftPower);

        telemetry.addData("Current Power Multiplier", ((double) power/10));
        telemetry.update();
    }

    @Override
    public void stop()
    {

    }
}
