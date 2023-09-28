package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.acmerobotics.roadrunner.control.PIDCoefficients;

@SuppressWarnings("unused")
@Autonomous(name = "CompAutonomous")
public class RoadrunnerOpMode
{

    public void runOpMode()
    {


        // specify coefficients/gains
        PIDCoefficients coeffs = new PIDCoefficients();
        // create the controller
        PIDFController controller = new PIDFController(coeffs);


    }
}

