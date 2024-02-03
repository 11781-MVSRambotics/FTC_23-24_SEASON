package org.firstinspires.ftc.teamcode;

import android.graphics.Camera;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.CameraArray;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.PickleAccumulator;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public enum Robot { SINGLETON;

    private static final List<? extends Subsystem> subsystems = Arrays.asList(Drivetrain.SINGLETON, PickleAccumulator.SINGLETON, CameraArray.SINGLETON);

    public static void initializeSubsystems(HardwareMap hardwareMap)
    {
        for(Subsystem system : subsystems) {
            system.initializeHardware(hardwareMap);
        }
    }
}
