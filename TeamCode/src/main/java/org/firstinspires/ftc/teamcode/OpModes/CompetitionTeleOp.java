package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@TeleOp
@SuppressWarnings("unused")
public class CompetitionTeleOp extends OpMode {

    Servo wristLeft, wristRight, latchLeft, latchRight, planeLatch, intakeLeft, intakeRight;;
    DcMotorEx linkageMotorLeft, linkageMotorRight;

    boolean aWasPressed = false, wristUp = false;
    boolean bWasPressed = false, latchOpen = false;
    boolean xWasPressed = false, intakeOpen = false;

    @Override
    public void init()
    {
        Robot.initializeSubsystems(hardwareMap);

        linkageMotorLeft = hardwareMap.get(DcMotorEx.class, "linkageMotorLeft");
        linkageMotorRight = hardwareMap.get(DcMotorEx.class, "linkageMotorRight");
        intakeLeft = hardwareMap.get(Servo.class, "intakeLeft");
        intakeRight = hardwareMap.get(Servo.class, "intakeRight");
        wristLeft = hardwareMap.get(Servo.class, "wristLeft");
        wristRight = hardwareMap.get(Servo.class, "wristRight");
        latchLeft = hardwareMap.get(Servo.class, "latchLeft");
        latchRight = hardwareMap.get(Servo.class, "latchRight");
        planeLatch = hardwareMap.get(Servo.class, "planeLatch");

        wristRight.setDirection(Servo.Direction.FORWARD);
        wristLeft.setDirection(Servo.Direction.REVERSE);

        latchRight.setDirection(Servo.Direction.FORWARD);
        latchLeft.setDirection(Servo.Direction.REVERSE);

        intakeRight.setDirection(Servo.Direction.REVERSE);
        intakeLeft.setDirection(Servo.Direction.FORWARD);

        linkageMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linkageMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        linkageMotorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        linkageMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linkageMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        linkageMotorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        linkageMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linkageMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    @Override
    public void loop()
    {
        Drivetrain.SINGLETON.MoveTeleOp(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        // Wrist toggled by gamepad A
        if(gamepad1.a && !wristUp) { // A is pressed and wrist is down (raise it)
            //wristRight.setPosition(0.6);
            wristLeft.setPosition(1);
            aWasPressed = true;
        } else if(gamepad1.a && wristUp) { // A is pressed and wrist is up (drop it)
            //wristRight.setPosition(0.4);
            wristLeft.setPosition(0.3);
            aWasPressed = true;
        } else if(!gamepad1.a && aWasPressed){ // A isn't pressed but was (toggle state)
            wristUp = !wristUp;
            aWasPressed = false;
        }

        // Latch toggled by gamepad B
        if(gamepad1.b && !latchOpen) { // B is pressed and latch is closed (open it)
            latchRight.setPosition(0.6);
            latchLeft.setPosition(0.6);
            bWasPressed = true;
        } else if(gamepad1.b && latchOpen) { // B is pressed and latch is open (close it)
            latchRight.setPosition(0.2);
            latchLeft.setPosition(0.2);
            bWasPressed = true;
        } else if(!gamepad1.b && bWasPressed){ // B isn't pressed but was (toggle state)
            latchOpen = !latchOpen;
            bWasPressed = false;
        }

        // Latch toggled by gamepad X
        if(gamepad1.x && !intakeOpen) { // X is pressed and intake is closed (open it)
            intakeRight.setPosition(1);
            intakeLeft.setPosition(1);
            xWasPressed = true;
        } else if(gamepad1.x && intakeOpen) { // X is pressed and intake is open (close it)
            intakeRight.setPosition(0.6);
            intakeLeft.setPosition(0.6);
            xWasPressed = true;
        } else if(!gamepad1.x && xWasPressed){ // X isn't pressed but was (toggle state)
            intakeOpen = !intakeOpen;
            xWasPressed = false;
        }

        // Release the plane
        if(gamepad1.y) {
            planeLatch.setPosition(0);
        }

        // 4-Bar is on the triggers
        if (gamepad1.right_trigger > 0) {
            linkageMotorLeft.setPower(gamepad1.right_trigger);
            linkageMotorRight.setPower(gamepad1.right_trigger);
        } else if (gamepad1.left_trigger > 0) {
            linkageMotorLeft.setPower(-gamepad1.left_trigger);
            linkageMotorRight.setPower(-gamepad1.left_trigger);
        } else {
            linkageMotorLeft.setPower(0.01);
            linkageMotorRight.setPower(0.01);
        }

        telemetry.addData("WristUp", wristUp);
        telemetry.addData("LatchOpen", latchOpen);
        telemetry.addData("IntakeOpen", intakeOpen);
        telemetry.update();
    }
}
