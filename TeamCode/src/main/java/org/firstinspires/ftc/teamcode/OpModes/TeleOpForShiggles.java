package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;

@SuppressWarnings("unused")
@TeleOp
public class TeleOpForShiggles extends OpMode {

    DcMotorEx FrontRightMotor;
    DcMotorEx FrontLeftMotor;
    DcMotorEx BackRightMotor;
    DcMotorEx BackLeftMotor;
    DcMotorEx IntakeMotor;
    DcMotorEx LinkageMotor;

    Servo servo;

    @Override
    public void init()
    {
        FrontLeftMotor = hardwareMap.get(DcMotorEx.class, "leftFront");
        FrontRightMotor = hardwareMap.get(DcMotorEx.class, "rightFront");
        BackRightMotor = hardwareMap.get(DcMotorEx.class, "rightRear");
        BackLeftMotor = hardwareMap.get(DcMotorEx.class, "leftRear");
        //IntakeMotor = hardwareMap.get(DcMotorEx.class, "IntakeMotor");
        //LinkageMotor = hardwareMap.get(DcMotorEx.class, "LinkageMotor");

        //FrontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        //FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        //BackRightMotor.setDirection(DcMotor.Direction.REVERSE);
        //BackLeftMotor.setDirection(DcMotor.Direction.REVERSE);
    }



    @Override
    public void init_loop()
    {

    }

    @Override
    public void start()
    {
        // Configure motors so stop when not under load to avoid coasting during TeleOp
        FrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    @Override
    public void loop()
    {


        // Maximum power value so we can normalize the power vectors
        double max = 1;
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double yaw = gamepad1.right_stick_x;

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
        // Scale the normalized value to the desired power percqentage
        frontLeftPower  *= 0.5;
        frontRightPower *= 0.5;
        backLeftPower   *= 0.5;
        backRightPower  *= 0.5;

        /*
        if (gamepad1.y == true) {
            frontRightPower = 1;
    }
        if (gamepad1.x == true) {
            frontLeftPower = 1;
        }
        if (gamepad1.b == true) {
            backRightPower = 1;
        }
        if (gamepad1.a == true) {
            backLeftPower = 1;
        }
        */


        // Send calculated power to wheels
        FrontRightMotor.setPower(frontRightPower);
        FrontLeftMotor.setPower(frontLeftPower);
        BackRightMotor.setPower(backRightPower);
        BackLeftMotor.setPower(backLeftPower);
        //IntakeMotor.setPower(gamepad2.right_stick_y);
        //LinkageMotor.setPower(gamepad2.left_stick_y * 0.25);

    }

    @Override
    public void stop()
    {

    }
}
