package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface Subsystem {

    boolean isInitialized();
    void initializeHardware(HardwareMap hardwareMap);
}
