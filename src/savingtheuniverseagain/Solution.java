/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package savingtheuniverseagain;

import java.util.*;
import java.io.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();
        for (int i = 1; i <= t; ++i) {
            int shield = in.nextInt();
            String command = in.next();
            int hackTime = hackTheRobot(shield, command);
            String result = Integer.toString(hackTime);
            if (hackTime < 0) {
                result = "IMPOSSIBLE";
            }
            System.out.println("Case #" + i + ": " + result);
        }
    }

    public static int hackTheRobot(int shield, String command) {
        String[] commands = command.split("");
        int currentDamage = getTotalDamage(commands);
        int requireReducedDamage = currentDamage - shield;
        int hackTime = 0;
        if (requireReducedDamage > 0) {
            hackTime = hack(requireReducedDamage, commands);
            if (!save(shield, commands)) {
                hackTime = -1;
            }
        } 
        return hackTime;
    }

    public static int hack(int reduceReq, String[] commands) {
        int hackTime = 0;
        for (int i = commands.length - 1; i > 0; i--) {
            //if found CS
            if (commands[i].equals("S") && commands[i - 1].equals("C")) {
                swap(commands, i - 1);
                int countCharge = getChargeLevelAtPosition(commands, i);
                reduceReq = reduceReq - getDamageAtCharge(countCharge) + getDamageAtCharge(countCharge - 1);
                if (reduceReq > 0) {
                    return ++hackTime + hack(reduceReq, commands);
                } else {
                    return ++hackTime;
                }
            }
        }
        return hackTime;
    }

    public static int getChargeLevelAtPosition(String[] commands, int index) {
        int count = 1;
        for (int i = 0; i <= index; i++) {
            if (commands[i].equals("C")) {
                count++;
            }
        }
        return count;
    }

    public static int getDamageAtCharge(int chargeLevel) {
        return (int) Math.pow(2, chargeLevel - 1);
    }

    public static String[] swap(String[] commands, int position) {
        String temp = commands[position];
        commands[position] = commands[position + 1];
        commands[position + 1] = temp;
        return commands;
    }

    public static boolean save(int shield, String[] commands) {
        return shield >= getTotalDamage(commands);
    }

    public static int getTotalDamage(String[] commands) {
        int totalDamage = 0;
        int damage = 1;
        for (String x : commands) {
            if (x.equals("S")) {
                totalDamage += damage;
            } else if (x.equals("C")) {
                damage *= 2;
            }
        }
        return totalDamage;
    }
}
