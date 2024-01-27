package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.PickleAccumulator;

@TeleOp
@SuppressWarnings("unused")
public class CompetitionTeleOp extends OpMode {

    private boolean isFourBarMoving = false;

    @Override
    public void init()
    {
        Robot.initializeSubsystems(hardwareMap);
    }

    @Override
    public void loop()
    {
        Drivetrain.MoveTeleOp(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        if(gamepad1.right_trigger > 0) {
            PickleAccumulator.raiseFourBar(gamepad1.right_trigger);
            isFourBarMoving = false;
        } else if(gamepad1.left_trigger > 0) {
            PickleAccumulator.lowerFourBar(gamepad1.left_trigger);
            isFourBarMoving = false;
        } else if(gamepad1.right_bumper) {
            PickleAccumulator.raiseFourBar();
            isFourBarMoving = true;
        } else if(gamepad1.left_bumper) {
            PickleAccumulator.lowerFourBar();
            isFourBarMoving = true;
        } else if(!isFourBarMoving) {
            PickleAccumulator.holdFourBar();
        }

        if(gamepad1.a) {
            PickleAccumulator.raiseBasket();
        } else if(gamepad1.b) {
            PickleAccumulator.lowerBasket();
        }

        if(gamepad1.dpad_right) {
            PickleAccumulator.openIntake();
        } else if(gamepad1.dpad_left) {
            PickleAccumulator.closeIntake();
        }

        if(gamepad1.dpad_up) {
            PickleAccumulator.openOutput();
        } else if(gamepad1.dpad_down) {
            PickleAccumulator.closeOutput();
        }

        telemetry.addData("Four bar position", PickleAccumulator.getFourBarPosition());
        telemetry.update();
    }
}
