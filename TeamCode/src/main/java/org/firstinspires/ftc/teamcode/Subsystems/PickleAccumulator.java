package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public enum PickleAccumulator implements Subsystem { SINGLETON;

    private static DcMotorEx fourBarLeft, fourBarRight;
    private static Servo wristLeft, wristRight, intakeLeft, intakeRight, output;

    @Override
    public void initializeHardware(HardwareMap hardwareMap) {
        fourBarRight = hardwareMap.get(DcMotorEx.class, "fourBarRight");
        fourBarLeft = hardwareMap.get(DcMotorEx.class, "fourBarLeft");

        fourBarRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fourBarLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fourBarRight.setDirection(DcMotorSimple.Direction.REVERSE);
        fourBarLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        wristRight = hardwareMap.get(Servo.class, "wristRight");
        wristLeft = hardwareMap.get(Servo.class, "wristLeft");

        wristRight.setDirection(Servo.Direction.FORWARD);
        wristLeft.setDirection(Servo.Direction.REVERSE);

        intakeRight = hardwareMap.get(Servo.class, "intakeRight");
        intakeLeft = hardwareMap.get(Servo.class, "intakeLeft");

        intakeRight.setDirection(Servo.Direction.REVERSE);
        intakeLeft.setDirection(Servo.Direction.FORWARD);

        output = hardwareMap.get(Servo.class, "output");
    }

    public static int getFourBarPosition() {
        return (fourBarRight.getCurrentPosition() + fourBarLeft.getCurrentPosition()) / 2;
    }

    public static void raiseBasket() {
        double BASKET_RAISED_POSITION = 1;
        wristRight.setPosition(BASKET_RAISED_POSITION);
        wristLeft.setPosition(BASKET_RAISED_POSITION);
    }

    public static void lowerBasket() {
        double BASKET_LOWERED_POSITION = 0.2;
        wristRight.setPosition(BASKET_LOWERED_POSITION);
        wristLeft.setPosition(BASKET_LOWERED_POSITION);
    }

    public static void openIntake() {
        double INTAKE_OPEN_POSITION = 0.8;
        intakeRight.setPosition(INTAKE_OPEN_POSITION);
        intakeLeft.setPosition(INTAKE_OPEN_POSITION);
    }

    public static void closeIntake() {
        double INTAKE_CLOSED_POSITION = 0.4;
        intakeRight.setPosition(INTAKE_CLOSED_POSITION);
        intakeLeft.setPosition(INTAKE_CLOSED_POSITION);
    }

    public static void openOutput() {
        double OUTPUT_OPEN_POSITION = 1;
        output.setPosition(OUTPUT_OPEN_POSITION);
    }

    public static void closeOutput() {
        double OUTPUT_CLOSED_POSITION = 0;
        output.setPosition(OUTPUT_CLOSED_POSITION);
    }

    public static void raiseFourBar() {
        int FOURBAR_RAISED_POSITION = 3000;
        fourBarRight.setTargetPosition(FOURBAR_RAISED_POSITION);
        fourBarLeft.setTargetPosition(FOURBAR_RAISED_POSITION);

        if(fourBarRight.getMode() != DcMotor.RunMode.RUN_TO_POSITION && fourBarLeft.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            fourBarRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fourBarLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        fourBarRight.setPower(1);
        fourBarLeft.setPower(1);
    }

    public static void raiseFourBar(double power) {
        int FOURBAR_LOWERED_POSITION = 0;
        if(fourBarRight.getMode() != DcMotor.RunMode.RUN_USING_ENCODER && fourBarLeft.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            fourBarRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fourBarLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        fourBarRight.setPower(power);
        fourBarLeft.setPower(power);
    }

    public static void lowerFourBar() {
        fourBarRight.setTargetPosition(0);
        fourBarLeft.setTargetPosition(0);

        if(fourBarRight.getMode() != DcMotor.RunMode.RUN_TO_POSITION && fourBarLeft.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            fourBarRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fourBarLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        fourBarRight.setPower(1);
        fourBarLeft.setPower(1);
    }

    public static void lowerFourBar(double power) {
        if(fourBarRight.getMode() != DcMotor.RunMode.RUN_USING_ENCODER && fourBarLeft.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            fourBarRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fourBarLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        fourBarRight.setPower(-power);
        fourBarLeft.setPower(-power);
    }

    public static void holdFourBar() {
        fourBarRight.setTargetPosition(fourBarRight.getCurrentPosition());
        fourBarLeft.setTargetPosition(fourBarLeft.getCurrentPosition());

        if(fourBarRight.getMode() != DcMotor.RunMode.RUN_TO_POSITION && fourBarLeft.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            fourBarRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fourBarLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        fourBarRight.setPower(1);
        fourBarLeft.setPower(1);
    }
}
