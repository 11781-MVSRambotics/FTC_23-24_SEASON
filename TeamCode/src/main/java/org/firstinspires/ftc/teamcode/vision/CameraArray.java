package org.firstinspires.ftc.teamcode.vision;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.CameraManagerInternal;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.openftc.easyopencv.OpenCvCamera;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CameraArray {
    public Eyeball left;
    public Eyeball right;

    public CameraArray() {

    }


}
