package com.example.morsecom;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.lights.Light;
import android.os.Bundle;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


/**
 * https://developer.android.com/guide/input */
public class Main extends AppCompatActivity{

    TextView textfield;
    InputDevice switchJoyCon;

    // todo --> apparently i could not find a way to make the controller it self vibrate, so
    //  i will just make the phone vibrate
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textfield = findViewById(R.id.textview);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        switchJoyCon = getRightNintendoSwitchJoyCon();
        System.out.println(switchJoyCon.getName());


        //Checks if the key codes for button A,B,X,Y are supported by the game controller
        boolean[] supportedInputs = switchJoyCon.hasKeys(
                KeyEvent.KEYCODE_BUTTON_A,
                KeyEvent.KEYCODE_BUTTON_X,
                KeyEvent.KEYCODE_BUTTON_Y,
                KeyEvent.KEYCODE_BUTTON_B);


    }



    /**
     * method that listens for keyevents
     * @param keyCode keycode of the button pressed
     * @param event the event from the input device
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //apparently it thinks that button A is button B so for now i switched their places
        switch (keyCode){
            case KeyEvent.KEYCODE_BUTTON_A: {
                System.out.println("button B ");
            }
            break;
            case KeyEvent.KEYCODE_BUTTON_B: System.out.println("button A");
                break;
            case KeyEvent.KEYCODE_BUTTON_X: {
                System.out.println("button X");
                vibrator.vibrate(300);
            }
            break;
            case KeyEvent.KEYCODE_BUTTON_Y: {
                System.out.println("button Y");
                vibrator.vibrate(1000);
            }
            break;
        }

        return super.onKeyDown(keyCode, event);
    }


    /**
     * has to overwrite this method since the B button is considered back key by android
     * (right now it is just an empty implementation, so that the back key does not do anything)
     */
    @Override
    public void onBackPressed() {}


    //todo --> maybe instead of returning null if a nintendo joy con is not detected it
    // should throw an exeption ?
    /**
     * Helper method that checks if the input device is a game controller. If so it Checks if the
     * game controller is a Right Nintendo Joy Con.
     *
     * @return A Right Nintendo Joy Con or null
     */
    public InputDevice getRightNintendoSwitchJoyCon() {

        //gets all the ID's of the connected input devices
        int[] deviceIds = InputDevice.getDeviceIds();

        //initializes each input device from its ID
        for (int deviceId : deviceIds) {
            InputDevice dev = InputDevice.getDevice(deviceId);
            int sources = dev.getSources();


            // Verify that the device has gamepad buttons, control sticks, or both.
            if (((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD)
                    || ((sources & InputDevice.SOURCE_JOYSTICK)
                    == InputDevice.SOURCE_JOYSTICK)) {

                // Checks if the gamecontroller is a right Nintendo Switch Joy Con
                if (dev.getName().equalsIgnoreCase("Nintendo Switch Right Joy-Con")) {
                    return dev;
                }
            }
        }
        return null;
    }


}



