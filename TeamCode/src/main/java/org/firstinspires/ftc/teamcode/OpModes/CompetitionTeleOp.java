package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.PickleAccumulator;

@TeleOp
@SuppressWarnings("unused")
public class CompetitionTeleOp extends OpMode {

    private boolean isFourBarMoving = false;

    private boolean aWasPressed = false, basketUp = false;
    private boolean bWasPressed = false, intakeOpen = false;
    private boolean xWasPressed = false, outputOpen = false;

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

        if(gamepad1.a && !basketUp) { // A is pressed and basket is down (raise it)
            PickleAccumulator.raiseBasket();
            aWasPressed = true;
        } else if(gamepad1.a && basketUp) { // A is pressed and basket is up (drop it)
            PickleAccumulator.lowerBasket();
            aWasPressed = true;
        } else if(!gamepad1.a && aWasPressed){ // A isn't pressed but was (toggle state)
            basketUp = !basketUp;
            aWasPressed = false;
        }

        if(gamepad1.b && !intakeOpen) { // B is pressed and intake is closed (open it)
            PickleAccumulator.openIntake();
            bWasPressed = true;
        } else if(gamepad1.b && intakeOpen) { // B is pressed and intake is open (close it)
            PickleAccumulator.closeIntake();
            bWasPressed = true;
        } else if(!gamepad1.b && bWasPressed){ // B isn't pressed but was (toggle state)
            intakeOpen = !intakeOpen;
            bWasPressed = false;
        }

        if(gamepad1.x && !outputOpen) { // X is pressed and output is closed (open it)
            PickleAccumulator.openOutput();
            xWasPressed = true;
        } else if(gamepad1.x && outputOpen) { // X is pressed and output is open (close it)
            PickleAccumulator.closeOutput();
            xWasPressed = true;
        } else if(!gamepad1.x && xWasPressed){ // X isn't pressed but was (toggle state)
            outputOpen = !outputOpen;
            xWasPressed = false;
        }

        telemetry.addData("Four bar position", PickleAccumulator.getFourBarPosition());
        telemetry.update();
    }
}
