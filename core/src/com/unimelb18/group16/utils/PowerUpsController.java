package com.unimelb18.group16.utils;

import com.unimelb18.group16.actors.Snake;

import java.util.Calendar;
import java.util.Date;

public class PowerUpsController {

    public static void applyPowerUp(Snake snake, int powerUpType) {

        boolean alreadyReceived = false;

        for (int j = 0; j < snake.getReceivedPowerUp().length; j++) {
            if (snake.getReceivedPowerUp()[j] == powerUpType) {
                alreadyReceived = true;
                break;
            }
        }

        if (!alreadyReceived) {
            switch (powerUpType) {
                case Constants.SPEED_POWER:
                    snake.setDefaultSpeed(400);
                    snake.addReceivedPower(Constants.SPEED_POWER);
                    break;
                case Constants.LONG_POWER:
                    snake.addReceivedPower(Constants.LONG_POWER);
                    for (int i = 0; i < 50; i++) {
                        snake.addSnakeBody();
                    }

            }
        }
    }

    public static Date lastShown;
    public static int currentPowerUpCount = 0;

    public static boolean showPowerUp() {

        Date currentTime = Calendar.getInstance().getTime();
        long difference;

        if (lastShown != null) {
            difference = (currentTime.getTime() - lastShown.getTime()) / 1000;
        } else {
            difference = 100;
        }

        if (difference > 2 && currentPowerUpCount < 4) {
            return true;
        } else {
            return false;
        }

    }
}
