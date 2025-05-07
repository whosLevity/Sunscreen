package me.combimagnetron.sunscreen.image;

import me.combimagnetron.sunscreen.util.Pair;
import me.combimagnetron.sunscreen.util.Vec2d;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public interface PixelPattern {

    PixelPattern R00_00 = of(new String[] {"000", "000", "000"},0, 0, Vec2d.of(0, 0), '');

    PixelPattern R00_01 = of(new String[] {"100", "000", "000"},1, 1, Vec2d.of(1, 0), '');

    PixelPattern R00_02 = of(new String[] {"010", "000", "000"},1, 1, Vec2d.of(2, 0), '');

    PixelPattern R00_03 = of(new String[] {"110", "000", "000"},2, 2, Vec2d.of(3, 0), '');

    PixelPattern R00_04 = of(new String[] {"001", "000", "000"},1, 1, Vec2d.of(4, 0), '');

    PixelPattern R00_05 = of(new String[] {"101", "000", "000"},2, 3, Vec2d.of(5, 0), '');

    PixelPattern R00_06 = of(new String[] {"011", "000", "000"},2, 2, Vec2d.of(6, 0), '');

    PixelPattern R00_07 = of(new String[] {"111", "000", "000"},3, 3, Vec2d.of(7, 0), '');

    PixelPattern R00_08 = of(new String[] {"000", "100", "000"},1, 1, Vec2d.of(8, 0), '');

    PixelPattern R00_09 = of(new String[] {"100", "100", "000"},2, 1, Vec2d.of(9, 0), '');

    PixelPattern R00_10 = of(new String[] {"010", "100", "000"},2, 2, Vec2d.of(10, 0), '');

    PixelPattern R00_11 = of(new String[] {"110", "100", "000"},3, 2, Vec2d.of(11, 0), '');

    PixelPattern R00_12 = of(new String[] {"001", "100", "000"},2, 3, Vec2d.of(12, 0), '');

    PixelPattern R00_13 = of(new String[] {"101", "100", "000"},3, 3, Vec2d.of(13, 0), '');

    PixelPattern R00_14 = of(new String[] {"011", "100", "000"},3, 3, Vec2d.of(14, 0), '');

    PixelPattern R00_15 = of(new String[] {"111", "100", "000"},4, 3, Vec2d.of(15, 0), '');

    PixelPattern R01_00 = of(new String[] {"000", "010", "000"},1, 1, Vec2d.of(0, 1), '');

    PixelPattern R01_01 = of(new String[] {"100", "010", "000"},2, 2, Vec2d.of(1, 1), '');

    PixelPattern R01_02 = of(new String[] {"010", "010", "000"},2, 1, Vec2d.of(2, 1), '');

    PixelPattern R01_03 = of(new String[] {"110", "010", "000"},3, 2, Vec2d.of(3, 1), '');

    PixelPattern R01_04 = of(new String[] {"001", "010", "000"},2, 2, Vec2d.of(4, 1), '');

    PixelPattern R01_05 = of(new String[] {"101", "010", "000"},3, 3, Vec2d.of(5, 1), '');

    PixelPattern R01_06 = of(new String[] {"011", "010", "000"},3, 2, Vec2d.of(6, 1), '');

    PixelPattern R01_07 = of(new String[] {"111", "010", "000"},4, 3, Vec2d.of(7, 1), '');

    PixelPattern R01_08 = of(new String[] {"000", "110", "000"},2, 2, Vec2d.of(8, 1), '');

    PixelPattern R01_09 = of(new String[] {"100", "110", "000"},3, 2, Vec2d.of(9, 1), '');

    PixelPattern R01_10 = of(new String[] {"010", "110", "000"},3, 2, Vec2d.of(10, 1), '');

    PixelPattern R01_11 = of(new String[] {"110", "110", "000"},4, 2, Vec2d.of(11, 1), '');

    PixelPattern R01_12 = of(new String[] {"001", "110", "000"},3, 3, Vec2d.of(12, 1), '');

    PixelPattern R01_13 = of(new String[] {"101", "110", "000"},4, 3, Vec2d.of(13, 1), '');

    PixelPattern R01_14 = of(new String[] {"011", "110", "000"},4, 3, Vec2d.of(14, 1), '');

    PixelPattern R01_15 = of(new String[] {"111", "110", "000"},5, 3, Vec2d.of(15, 1), '');

    PixelPattern R02_00 = of(new String[] {"000", "001", "000"},1, 1, Vec2d.of(0, 2), '');

    PixelPattern R02_01 = of(new String[] {"100", "001", "000"},2, 3, Vec2d.of(1, 2), '');

    PixelPattern R02_02 = of(new String[] {"010", "001", "000"},2, 2, Vec2d.of(2, 2), '');

    PixelPattern R02_03 = of(new String[] {"110", "001", "000"},3, 3, Vec2d.of(3, 2), '');

    PixelPattern R02_04 = of(new String[] {"001", "001", "000"},2, 1, Vec2d.of(4, 2), '');

    PixelPattern R02_05 = of(new String[] {"101", "001", "000"},3, 3, Vec2d.of(5, 2), '');

    PixelPattern R02_06 = of(new String[] {"011", "001", "000"},3, 2, Vec2d.of(6, 2), '');

    PixelPattern R02_07 = of(new String[] {"111", "001", "000"},4, 3, Vec2d.of(7, 2), '');

    PixelPattern R02_08 = of(new String[] {"000", "101", "000"},2, 3, Vec2d.of(8, 2), '');

    PixelPattern R02_09 = of(new String[] {"100", "101", "000"},3, 3, Vec2d.of(9, 2), '');

    PixelPattern R02_10 = of(new String[] {"010", "101", "000"},3, 3, Vec2d.of(10, 2), '');

    PixelPattern R02_11 = of(new String[] {"110", "101", "000"},4, 3, Vec2d.of(11, 2), '');

    PixelPattern R02_12 = of(new String[] {"001", "101", "000"},3, 3, Vec2d.of(12, 2), '');

    PixelPattern R02_13 = of(new String[] {"101", "101", "000"},4, 3, Vec2d.of(13, 2), '');

    PixelPattern R02_14 = of(new String[] {"011", "101", "000"},4, 3, Vec2d.of(14, 2), '');

    PixelPattern R02_15 = of(new String[] {"111", "101", "000"},5, 3, Vec2d.of(15, 2), '');

    PixelPattern R03_00 = of(new String[] {"000", "011", "000"},2, 2, Vec2d.of(0, 3), '');

    PixelPattern R03_01 = of(new String[] {"100", "011", "000"},3, 3, Vec2d.of(1, 3), '');

    PixelPattern R03_02 = of(new String[] {"010", "011", "000"},3, 2, Vec2d.of(2, 3), '');

    PixelPattern R03_03 = of(new String[] {"110", "011", "000"},4, 3, Vec2d.of(3, 3), '');

    PixelPattern R03_04 = of(new String[] {"001", "011", "000"},3, 2, Vec2d.of(4, 3), '');

    PixelPattern R03_05 = of(new String[] {"101", "011", "000"},4, 3, Vec2d.of(5, 3), '');

    PixelPattern R03_06 = of(new String[] {"011", "011", "000"},4, 2, Vec2d.of(6, 3), '');

    PixelPattern R03_07 = of(new String[] {"111", "011", "000"},5, 3, Vec2d.of(7, 3), '');

    PixelPattern R03_08 = of(new String[] {"000", "111", "000"},3, 3, Vec2d.of(8, 3), '');

    PixelPattern R03_09 = of(new String[] {"100", "111", "000"},4, 3, Vec2d.of(9, 3), '');

    PixelPattern R03_10 = of(new String[] {"010", "111", "000"},4, 3, Vec2d.of(10, 3), '');

    PixelPattern R03_11 = of(new String[] {"110", "111", "000"},5, 3, Vec2d.of(11, 3), '');

    PixelPattern R03_12 = of(new String[] {"001", "111", "000"},4, 3, Vec2d.of(12, 3), '');

    PixelPattern R03_13 = of(new String[] {"101", "111", "000"},5, 3, Vec2d.of(13, 3), '');

    PixelPattern R03_14 = of(new String[] {"011", "111", "000"},5, 3, Vec2d.of(14, 3), '');

    PixelPattern R03_15 = of(new String[] {"111", "111", "000"},6, 3, Vec2d.of(15, 3), '');

    PixelPattern R04_00 = of(new String[] {"000", "000", "100"},1, 1, Vec2d.of(0, 4), '');

    PixelPattern R04_01 = of(new String[] {"100", "000", "100"},2, 1, Vec2d.of(1, 4), '');

    PixelPattern R04_02 = of(new String[] {"010", "000", "100"},2, 2, Vec2d.of(2, 4), '');

    PixelPattern R04_03 = of(new String[] {"110", "000", "100"},3, 2, Vec2d.of(3, 4), '');

    PixelPattern R04_04 = of(new String[] {"001", "000", "100"},2, 3, Vec2d.of(4, 4), '');

    PixelPattern R04_05 = of(new String[] {"101", "000", "100"},3, 3, Vec2d.of(5, 4), '');

    PixelPattern R04_06 = of(new String[] {"011", "000", "100"},3, 3, Vec2d.of(6, 4), '');

    PixelPattern R04_07 = of(new String[] {"111", "000", "100"},4, 3, Vec2d.of(7, 4), '');

    PixelPattern R04_08 = of(new String[] {"000", "100", "100"},2, 1, Vec2d.of(8, 4), '');

    PixelPattern R04_09 = of(new String[] {"100", "100", "100"},3, 1, Vec2d.of(9, 4), '');

    PixelPattern R04_10 = of(new String[] {"010", "100", "100"},3, 2, Vec2d.of(10, 4), '');

    PixelPattern R04_11 = of(new String[] {"110", "100", "100"},4, 2, Vec2d.of(11, 4), '');

    PixelPattern R04_12 = of(new String[] {"001", "100", "100"},3, 3, Vec2d.of(12, 4), '');

    PixelPattern R04_13 = of(new String[] {"101", "100", "100"},4, 3, Vec2d.of(13, 4), '');

    PixelPattern R04_14 = of(new String[] {"011", "100", "100"},4, 3, Vec2d.of(14, 4), '');

    PixelPattern R04_15 = of(new String[] {"111", "100", "100"},5, 3, Vec2d.of(15, 4), '');

    PixelPattern R05_00 = of(new String[] {"000", "010", "100"},2, 2, Vec2d.of(0, 5), '');

    PixelPattern R05_01 = of(new String[] {"100", "010", "100"},3, 2, Vec2d.of(1, 5), '');

    PixelPattern R05_02 = of(new String[] {"010", "010", "100"},3, 2, Vec2d.of(2, 5), '');

    PixelPattern R05_03 = of(new String[] {"110", "010", "100"},4, 2, Vec2d.of(3, 5), '');

    PixelPattern R05_04 = of(new String[] {"001", "010", "100"},3, 3, Vec2d.of(4, 5), '');

    PixelPattern R05_05 = of(new String[] {"101", "010", "100"},4, 3, Vec2d.of(5, 5), '');

    PixelPattern R05_06 = of(new String[] {"011", "010", "100"},4, 3, Vec2d.of(6, 5), '');

    PixelPattern R05_07 = of(new String[] {"111", "010", "100"},5, 3, Vec2d.of(7, 5), '');

    PixelPattern R05_08 = of(new String[] {"000", "110", "100"},3, 2, Vec2d.of(8, 5), '');

    PixelPattern R05_09 = of(new String[] {"100", "110", "100"},4, 2, Vec2d.of(9, 5), '');

    PixelPattern R05_10 = of(new String[] {"010", "110", "100"},4, 2, Vec2d.of(10, 5), '');

    PixelPattern R05_11 = of(new String[] {"110", "110", "100"},5, 2, Vec2d.of(11, 5), '');

    PixelPattern R05_12 = of(new String[] {"001", "110", "100"},4, 3, Vec2d.of(12, 5), '');

    PixelPattern R05_13 = of(new String[] {"101", "110", "100"},5, 3, Vec2d.of(13, 5), '');

    PixelPattern R05_14 = of(new String[] {"011", "110", "100"},5, 3, Vec2d.of(14, 5), '');

    PixelPattern R05_15 = of(new String[] {"111", "110", "100"},6, 3, Vec2d.of(15, 5), '');

    PixelPattern R06_00 = of(new String[] {"000", "001", "100"},2, 3, Vec2d.of(0, 6), '');

    PixelPattern R06_01 = of(new String[] {"100", "001", "100"},3, 3, Vec2d.of(1, 6), '');

    PixelPattern R06_02 = of(new String[] {"010", "001", "100"},3, 3, Vec2d.of(2, 6), '');

    PixelPattern R06_03 = of(new String[] {"110", "001", "100"},4, 3, Vec2d.of(3, 6), '');

    PixelPattern R06_04 = of(new String[] {"001", "001", "100"},3, 3, Vec2d.of(4, 6), '');

    PixelPattern R06_05 = of(new String[] {"101", "001", "100"},4, 3, Vec2d.of(5, 6), '');

    PixelPattern R06_06 = of(new String[] {"011", "001", "100"},4, 3, Vec2d.of(6, 6), '');

    PixelPattern R06_07 = of(new String[] {"111", "001", "100"},5, 3, Vec2d.of(7, 6), '');

    PixelPattern R06_08 = of(new String[] {"000", "101", "100"},3, 3, Vec2d.of(8, 6), '');

    PixelPattern R06_09 = of(new String[] {"100", "101", "100"},4, 3, Vec2d.of(9, 6), '');

    PixelPattern R06_10 = of(new String[] {"010", "101", "100"},4, 3, Vec2d.of(10, 6), '');

    PixelPattern R06_11 = of(new String[] {"110", "101", "100"},5, 3, Vec2d.of(11, 6), '');

    PixelPattern R06_12 = of(new String[] {"001", "101", "100"},4, 3, Vec2d.of(12, 6), '');

    PixelPattern R06_13 = of(new String[] {"101", "101", "100"},5, 3, Vec2d.of(13, 6), '');

    PixelPattern R06_14 = of(new String[] {"011", "101", "100"},5, 3, Vec2d.of(14, 6), '');

    PixelPattern R06_15 = of(new String[] {"111", "101", "100"},6, 3, Vec2d.of(15, 6), '');

    PixelPattern R07_00 = of(new String[] {"000", "011", "100"},3, 3, Vec2d.of(0, 7), '');

    PixelPattern R07_01 = of(new String[] {"100", "011", "100"},4, 3, Vec2d.of(1, 7), '');

    PixelPattern R07_02 = of(new String[] {"010", "011", "100"},4, 3, Vec2d.of(2, 7), '');

    PixelPattern R07_03 = of(new String[] {"110", "011", "100"},5, 3, Vec2d.of(3, 7), '');

    PixelPattern R07_04 = of(new String[] {"001", "011", "100"},4, 3, Vec2d.of(4, 7), '');

    PixelPattern R07_05 = of(new String[] {"101", "011", "100"},5, 3, Vec2d.of(5, 7), '');

    PixelPattern R07_06 = of(new String[] {"011", "011", "100"},5, 3, Vec2d.of(6, 7), '');

    PixelPattern R07_07 = of(new String[] {"111", "011", "100"},6, 3, Vec2d.of(7, 7), '');

    PixelPattern R07_08 = of(new String[] {"000", "111", "100"},4, 3, Vec2d.of(8, 7), '');

    PixelPattern R07_09 = of(new String[] {"100", "111", "100"},5, 3, Vec2d.of(9, 7), '');

    PixelPattern R07_10 = of(new String[] {"010", "111", "100"},5, 3, Vec2d.of(10, 7), '');

    PixelPattern R07_11 = of(new String[] {"110", "111", "100"},6, 3, Vec2d.of(11, 7), '');

    PixelPattern R07_12 = of(new String[] {"001", "111", "100"},5, 3, Vec2d.of(12, 7), '');

    PixelPattern R07_13 = of(new String[] {"101", "111", "100"},6, 3, Vec2d.of(13, 7), '');

    PixelPattern R07_14 = of(new String[] {"011", "111", "100"},6, 3, Vec2d.of(14, 7), '');

    PixelPattern R07_15 = of(new String[] {"111", "111", "100"},7, 3, Vec2d.of(15, 7), '');

    PixelPattern R08_00 = of(new String[] {"000", "000", "010"},1, 1, Vec2d.of(0, 8), '');

    PixelPattern R08_01 = of(new String[] {"100", "000", "010"},2, 2, Vec2d.of(1, 8), '');

    PixelPattern R08_02 = of(new String[] {"010", "000", "010"},2, 1, Vec2d.of(2, 8), '');

    PixelPattern R08_03 = of(new String[] {"110", "000", "010"},3, 2, Vec2d.of(3, 8), '');

    PixelPattern R08_04 = of(new String[] {"001", "000", "010"},2, 2, Vec2d.of(4, 8), '');

    PixelPattern R08_05 = of(new String[] {"101", "000", "010"},3, 3, Vec2d.of(5, 8), '');

    PixelPattern R08_06 = of(new String[] {"011", "000", "010"},3, 2, Vec2d.of(6, 8), '');

    PixelPattern R08_07 = of(new String[] {"111", "000", "010"},4, 3, Vec2d.of(7, 8), '');

    PixelPattern R08_08 = of(new String[] {"000", "100", "010"},2, 2, Vec2d.of(8, 8), '');

    PixelPattern R08_09 = of(new String[] {"100", "100", "010"},3, 2, Vec2d.of(9, 8), '');

    PixelPattern R08_10 = of(new String[] {"010", "100", "010"},3, 2, Vec2d.of(10, 8), '');

    PixelPattern R08_11 = of(new String[] {"110", "100", "010"},4, 2, Vec2d.of(11, 8), '');

    PixelPattern R08_12 = of(new String[] {"001", "100", "010"},3, 3, Vec2d.of(12, 8), '');

    PixelPattern R08_13 = of(new String[] {"101", "100", "010"},4, 3, Vec2d.of(13, 8), '');

    PixelPattern R08_14 = of(new String[] {"011", "100", "010"},4, 3, Vec2d.of(14, 8), '');

    PixelPattern R08_15 = of(new String[] {"111", "100", "010"},5, 3, Vec2d.of(15, 8), '');

    PixelPattern R09_00 = of(new String[] {"000", "010", "010"},2, 1, Vec2d.of(0, 9), '');

    PixelPattern R09_01 = of(new String[] {"100", "010", "010"},3, 2, Vec2d.of(1, 9), '');

    PixelPattern R09_02 = of(new String[] {"010", "010", "010"},3, 1, Vec2d.of(2, 9), '');

    PixelPattern R09_03 = of(new String[] {"110", "010", "010"},4, 2, Vec2d.of(3, 9), '');

    PixelPattern R09_04 = of(new String[] {"001", "010", "010"},3, 2, Vec2d.of(4, 9), '');

    PixelPattern R09_05 = of(new String[] {"101", "010", "010"},4, 3, Vec2d.of(5, 9), '');

    PixelPattern R09_06 = of(new String[] {"011", "010", "010"},4, 2, Vec2d.of(6, 9), '');

    PixelPattern R09_07 = of(new String[] {"111", "010", "010"},5, 3, Vec2d.of(7, 9), '');

    PixelPattern R09_08 = of(new String[] {"000", "110", "010"},3, 2, Vec2d.of(8, 9), '');

    PixelPattern R09_09 = of(new String[] {"100", "110", "010"},4, 2, Vec2d.of(9, 9), '');

    PixelPattern R09_10 = of(new String[] {"010", "110", "010"},4, 2, Vec2d.of(10, 9), '');

    PixelPattern R09_11 = of(new String[] {"110", "110", "010"},5, 2, Vec2d.of(11, 9), '');

    PixelPattern R09_12 = of(new String[] {"001", "110", "010"},4, 3, Vec2d.of(12, 9), '');

    PixelPattern R09_13 = of(new String[] {"101", "110", "010"},5, 3, Vec2d.of(13, 9), '');

    PixelPattern R09_14 = of(new String[] {"011", "110", "010"},5, 3, Vec2d.of(14, 9), '');

    PixelPattern R09_15 = of(new String[] {"111", "110", "010"},6, 3, Vec2d.of(15, 9), '');

    PixelPattern R10_00 = of(new String[] {"000", "001", "010"},2, 2, Vec2d.of(0, 10), '');

    PixelPattern R10_01 = of(new String[] {"100", "001", "010"},3, 3, Vec2d.of(1, 10), '');

    PixelPattern R10_02 = of(new String[] {"010", "001", "010"},3, 2, Vec2d.of(2, 10), '');

    PixelPattern R10_03 = of(new String[] {"110", "001", "010"},4, 3, Vec2d.of(3, 10), '');

    PixelPattern R10_04 = of(new String[] {"001", "001", "010"},3, 2, Vec2d.of(4, 10), '');

    PixelPattern R10_05 = of(new String[] {"101", "001", "010"},4, 3, Vec2d.of(5, 10), '');

    PixelPattern R10_06 = of(new String[] {"011", "001", "010"},4, 2, Vec2d.of(6, 10), '');

    PixelPattern R10_07 = of(new String[] {"111", "001", "010"},5, 3, Vec2d.of(7, 10), '');

    PixelPattern R10_08 = of(new String[] {"000", "101", "010"},3, 3, Vec2d.of(8, 10), '');

    PixelPattern R10_09 = of(new String[] {"100", "101", "010"},4, 3, Vec2d.of(9, 10), '');

    PixelPattern R10_10 = of(new String[] {"010", "101", "010"},4, 3, Vec2d.of(10, 10), '');

    PixelPattern R10_11 = of(new String[] {"110", "101", "010"},5, 3, Vec2d.of(11, 10), '');

    PixelPattern R10_12 = of(new String[] {"001", "101", "010"},4, 3, Vec2d.of(12, 10), '');

    PixelPattern R10_13 = of(new String[] {"101", "101", "010"},5, 3, Vec2d.of(13, 10), '');

    PixelPattern R10_14 = of(new String[] {"011", "101", "010"},5, 3, Vec2d.of(14, 10), '');

    PixelPattern R10_15 = of(new String[] {"111", "101", "010"},6, 3, Vec2d.of(15, 10), '');

    PixelPattern R11_00 = of(new String[] {"000", "011", "010"},3, 2, Vec2d.of(0, 11), '');

    PixelPattern R11_01 = of(new String[] {"100", "011", "010"},4, 3, Vec2d.of(1, 11), '');

    PixelPattern R11_02 = of(new String[] {"010", "011", "010"},4, 2, Vec2d.of(2, 11), '');

    PixelPattern R11_03 = of(new String[] {"110", "011", "010"},5, 3, Vec2d.of(3, 11), '');

    PixelPattern R11_04 = of(new String[] {"001", "011", "010"},4, 2, Vec2d.of(4, 11), '');

    PixelPattern R11_05 = of(new String[] {"101", "011", "010"},5, 3, Vec2d.of(5, 11), '');

    PixelPattern R11_06 = of(new String[] {"011", "011", "010"},5, 2, Vec2d.of(6, 11), '');

    PixelPattern R11_07 = of(new String[] {"111", "011", "010"},6, 3, Vec2d.of(7, 11), '');

    PixelPattern R11_08 = of(new String[] {"000", "111", "010"},4, 3, Vec2d.of(8, 11), '');

    PixelPattern R11_09 = of(new String[] {"100", "111", "010"},5, 3, Vec2d.of(9, 11), '');

    PixelPattern R11_10 = of(new String[] {"010", "111", "010"},5, 3, Vec2d.of(10, 11), '');

    PixelPattern R11_11 = of(new String[] {"110", "111", "010"},6, 3, Vec2d.of(11, 11), '');

    PixelPattern R11_12 = of(new String[] {"001", "111", "010"},5, 3, Vec2d.of(12, 11), '');

    PixelPattern R11_13 = of(new String[] {"101", "111", "010"},6, 3, Vec2d.of(13, 11), '');

    PixelPattern R11_14 = of(new String[] {"011", "111", "010"},6, 3, Vec2d.of(14, 11), '');

    PixelPattern R11_15 = of(new String[] {"111", "111", "010"},7, 3, Vec2d.of(15, 11), '');

    PixelPattern R12_00 = of(new String[] {"000", "000", "110"},2, 2, Vec2d.of(0, 12), '');

    PixelPattern R12_01 = of(new String[] {"100", "000", "110"},3, 2, Vec2d.of(1, 12), '');

    PixelPattern R12_02 = of(new String[] {"010", "000", "110"},3, 2, Vec2d.of(2, 12), '');

    PixelPattern R12_03 = of(new String[] {"110", "000", "110"},4, 2, Vec2d.of(3, 12), '');

    PixelPattern R12_04 = of(new String[] {"001", "000", "110"},3, 3, Vec2d.of(4, 12), '');

    PixelPattern R12_05 = of(new String[] {"101", "000", "110"},4, 3, Vec2d.of(5, 12), '');

    PixelPattern R12_06 = of(new String[] {"011", "000", "110"},4, 3, Vec2d.of(6, 12), '');

    PixelPattern R12_07 = of(new String[] {"111", "000", "110"},5, 3, Vec2d.of(7, 12), '');

    PixelPattern R12_08 = of(new String[] {"000", "100", "110"},3, 2, Vec2d.of(8, 12), '');

    PixelPattern R12_09 = of(new String[] {"100", "100", "110"},4, 2, Vec2d.of(9, 12), '');

    PixelPattern R12_10 = of(new String[] {"010", "100", "110"},4, 2, Vec2d.of(10, 12), '');

    PixelPattern R12_11 = of(new String[] {"110", "100", "110"},5, 2, Vec2d.of(11, 12), '');

    PixelPattern R12_12 = of(new String[] {"001", "100", "110"},4, 3, Vec2d.of(12, 12), '');

    PixelPattern R12_13 = of(new String[] {"101", "100", "110"},5, 3, Vec2d.of(13, 12), '');

    PixelPattern R12_14 = of(new String[] {"011", "100", "110"},5, 3, Vec2d.of(14, 12), '');

    PixelPattern R12_15 = of(new String[] {"111", "100", "110"},6, 3, Vec2d.of(15, 12), '');

    PixelPattern R13_00 = of(new String[] {"000", "010", "110"},3, 2, Vec2d.of(0, 13), '');

    PixelPattern R13_01 = of(new String[] {"100", "010", "110"},4, 2, Vec2d.of(1, 13), '');

    PixelPattern R13_02 = of(new String[] {"010", "010", "110"},4, 2, Vec2d.of(2, 13), '');

    PixelPattern R13_03 = of(new String[] {"110", "010", "110"},5, 2, Vec2d.of(3, 13), '');

    PixelPattern R13_04 = of(new String[] {"001", "010", "110"},4, 3, Vec2d.of(4, 13), '');

    PixelPattern R13_05 = of(new String[] {"101", "010", "110"},5, 3, Vec2d.of(5, 13), '');

    PixelPattern R13_06 = of(new String[] {"011", "010", "110"},5, 3, Vec2d.of(6, 13), '');

    PixelPattern R13_07 = of(new String[] {"111", "010", "110"},6, 3, Vec2d.of(7, 13), '');

    PixelPattern R13_08 = of(new String[] {"000", "110", "110"},4, 2, Vec2d.of(8, 13), '');

    PixelPattern R13_09 = of(new String[] {"100", "110", "110"},5, 2, Vec2d.of(9, 13), '');

    PixelPattern R13_10 = of(new String[] {"010", "110", "110"},5, 2, Vec2d.of(10, 13), '');

    PixelPattern R13_11 = of(new String[] {"110", "110", "110"},6, 2, Vec2d.of(11, 13), '');

    PixelPattern R13_12 = of(new String[] {"001", "110", "110"},5, 3, Vec2d.of(12, 13), '');

    PixelPattern R13_13 = of(new String[] {"101", "110", "110"},6, 3, Vec2d.of(13, 13), '');

    PixelPattern R13_14 = of(new String[] {"011", "110", "110"},6, 3, Vec2d.of(14, 13), '');

    PixelPattern R13_15 = of(new String[] {"111", "110", "110"},7, 3, Vec2d.of(15, 13), '');

    PixelPattern R14_00 = of(new String[] {"000", "001", "110"},3, 3, Vec2d.of(0, 14), '');

    PixelPattern R14_01 = of(new String[] {"100", "001", "110"},4, 3, Vec2d.of(1, 14), '');

    PixelPattern R14_02 = of(new String[] {"010", "001", "110"},4, 3, Vec2d.of(2, 14), '');

    PixelPattern R14_03 = of(new String[] {"110", "001", "110"},5, 3, Vec2d.of(3, 14), '');

    PixelPattern R14_04 = of(new String[] {"001", "001", "110"},4, 3, Vec2d.of(4, 14), '');

    PixelPattern R14_05 = of(new String[] {"101", "001", "110"},5, 3, Vec2d.of(5, 14), '');

    PixelPattern R14_06 = of(new String[] {"011", "001", "110"},5, 3, Vec2d.of(6, 14), '');

    PixelPattern R14_07 = of(new String[] {"111", "001", "110"},6, 3, Vec2d.of(7, 14), '');

    PixelPattern R14_08 = of(new String[] {"000", "101", "110"},4, 3, Vec2d.of(8, 14), '');

    PixelPattern R14_09 = of(new String[] {"100", "101", "110"},5, 3, Vec2d.of(9, 14), '');

    PixelPattern R14_10 = of(new String[] {"010", "101", "110"},5, 3, Vec2d.of(10, 14), '');

    PixelPattern R14_11 = of(new String[] {"110", "101", "110"},6, 3, Vec2d.of(11, 14), '');

    PixelPattern R14_12 = of(new String[] {"001", "101", "110"},5, 3, Vec2d.of(12, 14), '');

    PixelPattern R14_13 = of(new String[] {"101", "101", "110"},6, 3, Vec2d.of(13, 14), '');

    PixelPattern R14_14 = of(new String[] {"011", "101", "110"},6, 3, Vec2d.of(14, 14), '');

    PixelPattern R14_15 = of(new String[] {"111", "101", "110"},7, 3, Vec2d.of(15, 14), '');

    PixelPattern R15_00 = of(new String[] {"000", "011", "110"},4, 3, Vec2d.of(0, 15), '');

    PixelPattern R15_01 = of(new String[] {"100", "011", "110"},5, 3, Vec2d.of(1, 15), '');

    PixelPattern R15_02 = of(new String[] {"010", "011", "110"},5, 3, Vec2d.of(2, 15), '');

    PixelPattern R15_03 = of(new String[] {"110", "011", "110"},6, 3, Vec2d.of(3, 15), '');

    PixelPattern R15_04 = of(new String[] {"001", "011", "110"},5, 3, Vec2d.of(4, 15), '');

    PixelPattern R15_05 = of(new String[] {"101", "011", "110"},6, 3, Vec2d.of(5, 15), '');

    PixelPattern R15_06 = of(new String[] {"011", "011", "110"},6, 3, Vec2d.of(6, 15), '');

    PixelPattern R15_07 = of(new String[] {"111", "011", "110"},7, 3, Vec2d.of(7, 15), '');

    PixelPattern R15_08 = of(new String[] {"000", "111", "110"},5, 3, Vec2d.of(8, 15), '');

    PixelPattern R15_09 = of(new String[] {"100", "111", "110"},6, 3, Vec2d.of(9, 15), '');

    PixelPattern R15_10 = of(new String[] {"010", "111", "110"},6, 3, Vec2d.of(10, 15), '');

    PixelPattern R15_11 = of(new String[] {"110", "111", "110"},7, 3, Vec2d.of(11, 15), '');

    PixelPattern R15_12 = of(new String[] {"001", "111", "110"},6, 3, Vec2d.of(12, 15), '');

    PixelPattern R15_13 = of(new String[] {"101", "111", "110"},7, 3, Vec2d.of(13, 15), '');

    PixelPattern R15_14 = of(new String[] {"011", "111", "110"},7, 3, Vec2d.of(14, 15), '');

    PixelPattern R15_15 = of(new String[] {"111", "111", "110"},8, 3, Vec2d.of(15, 15), '');

    PixelPattern R16_00 = of(new String[] {"000", "000", "001"},1, 1, Vec2d.of(0, 16), '');

    PixelPattern R16_01 = of(new String[] {"100", "000", "001"},2, 3, Vec2d.of(1, 16), '');

    PixelPattern R16_02 = of(new String[] {"010", "000", "001"},2, 2, Vec2d.of(2, 16), '');

    PixelPattern R16_03 = of(new String[] {"110", "000", "001"},3, 3, Vec2d.of(3, 16), '');

    PixelPattern R16_04 = of(new String[] {"001", "000", "001"},2, 1, Vec2d.of(4, 16), '');

    PixelPattern R16_05 = of(new String[] {"101", "000", "001"},3, 3, Vec2d.of(5, 16), '');

    PixelPattern R16_06 = of(new String[] {"011", "000", "001"},3, 2, Vec2d.of(6, 16), '');

    PixelPattern R16_07 = of(new String[] {"111", "000", "001"},4, 3, Vec2d.of(7, 16), '');

    PixelPattern R16_08 = of(new String[] {"000", "100", "001"},2, 3, Vec2d.of(8, 16), '');

    PixelPattern R16_09 = of(new String[] {"100", "100", "001"},3, 3, Vec2d.of(9, 16), '');

    PixelPattern R16_10 = of(new String[] {"010", "100", "001"},3, 3, Vec2d.of(10, 16), '');

    PixelPattern R16_11 = of(new String[] {"110", "100", "001"},4, 3, Vec2d.of(11, 16), '');

    PixelPattern R16_12 = of(new String[] {"001", "100", "001"},3, 3, Vec2d.of(12, 16), '');

    PixelPattern R16_13 = of(new String[] {"101", "100", "001"},4, 3, Vec2d.of(13, 16), '');

    PixelPattern R16_14 = of(new String[] {"011", "100", "001"},4, 3, Vec2d.of(14, 16), '');

    PixelPattern R16_15 = of(new String[] {"111", "100", "001"},5, 3, Vec2d.of(15, 16), '');

    PixelPattern R17_00 = of(new String[] {"000", "010", "001"},2, 2, Vec2d.of(0, 17), '');

    PixelPattern R17_01 = of(new String[] {"100", "010", "001"},3, 3, Vec2d.of(1, 17), '');

    PixelPattern R17_02 = of(new String[] {"010", "010", "001"},3, 2, Vec2d.of(2, 17), '');

    PixelPattern R17_03 = of(new String[] {"110", "010", "001"},4, 3, Vec2d.of(3, 17), '');

    PixelPattern R17_04 = of(new String[] {"001", "010", "001"},3, 2, Vec2d.of(4, 17), '');

    PixelPattern R17_05 = of(new String[] {"101", "010", "001"},4, 3, Vec2d.of(5, 17), '');

    PixelPattern R17_06 = of(new String[] {"011", "010", "001"},4, 2, Vec2d.of(6, 17), '');

    PixelPattern R17_07 = of(new String[] {"111", "010", "001"},5, 3, Vec2d.of(7, 17), '');

    PixelPattern R17_08 = of(new String[] {"000", "110", "001"},3, 3, Vec2d.of(8, 17), '');

    PixelPattern R17_09 = of(new String[] {"100", "110", "001"},4, 3, Vec2d.of(9, 17), '');

    PixelPattern R17_10 = of(new String[] {"010", "110", "001"},4, 3, Vec2d.of(10, 17), '');

    PixelPattern R17_11 = of(new String[] {"110", "110", "001"},5, 3, Vec2d.of(11, 17), '');

    PixelPattern R17_12 = of(new String[] {"001", "110", "001"},4, 3, Vec2d.of(12, 17), '');

    PixelPattern R17_13 = of(new String[] {"101", "110", "001"},5, 3, Vec2d.of(13, 17), '');

    PixelPattern R17_14 = of(new String[] {"011", "110", "001"},5, 3, Vec2d.of(14, 17), '');

    PixelPattern R17_15 = of(new String[] {"111", "110", "001"},6, 3, Vec2d.of(15, 17), '');

    PixelPattern R18_00 = of(new String[] {"000", "001", "001"},2, 1, Vec2d.of(0, 18), '');

    PixelPattern R18_01 = of(new String[] {"100", "001", "001"},3, 3, Vec2d.of(1, 18), '');

    PixelPattern R18_02 = of(new String[] {"010", "001", "001"},3, 2, Vec2d.of(2, 18), '');

    PixelPattern R18_03 = of(new String[] {"110", "001", "001"},4, 3, Vec2d.of(3, 18), '');

    PixelPattern R18_04 = of(new String[] {"001", "001", "001"},3, 1, Vec2d.of(4, 18), '');

    PixelPattern R18_05 = of(new String[] {"101", "001", "001"},4, 3, Vec2d.of(5, 18), '');

    PixelPattern R18_06 = of(new String[] {"011", "001", "001"},4, 2, Vec2d.of(6, 18), '');

    PixelPattern R18_07 = of(new String[] {"111", "001", "001"},5, 3, Vec2d.of(7, 18), '');

    PixelPattern R18_08 = of(new String[] {"000", "101", "001"},3, 3, Vec2d.of(8, 18), '');

    PixelPattern R18_09 = of(new String[] {"100", "101", "001"},4, 3, Vec2d.of(9, 18), '');

    PixelPattern R18_10 = of(new String[] {"010", "101", "001"},4, 3, Vec2d.of(10, 18), '');

    PixelPattern R18_11 = of(new String[] {"110", "101", "001"},5, 3, Vec2d.of(11, 18), '');

    PixelPattern R18_12 = of(new String[] {"001", "101", "001"},4, 3, Vec2d.of(12, 18), '');

    PixelPattern R18_13 = of(new String[] {"101", "101", "001"},5, 3, Vec2d.of(13, 18), '');

    PixelPattern R18_14 = of(new String[] {"011", "101", "001"},5, 3, Vec2d.of(14, 18), '');

    PixelPattern R18_15 = of(new String[] {"111", "101", "001"},6, 3, Vec2d.of(15, 18), '');

    PixelPattern R19_00 = of(new String[] {"000", "011", "001"},3, 2, Vec2d.of(0, 19), '');

    PixelPattern R19_01 = of(new String[] {"100", "011", "001"},4, 3, Vec2d.of(1, 19), '');

    PixelPattern R19_02 = of(new String[] {"010", "011", "001"},4, 2, Vec2d.of(2, 19), '');

    PixelPattern R19_03 = of(new String[] {"110", "011", "001"},5, 3, Vec2d.of(3, 19), '');

    PixelPattern R19_04 = of(new String[] {"001", "011", "001"},4, 2, Vec2d.of(4, 19), '');

    PixelPattern R19_05 = of(new String[] {"101", "011", "001"},5, 3, Vec2d.of(5, 19), '');

    PixelPattern R19_06 = of(new String[] {"011", "011", "001"},5, 2, Vec2d.of(6, 19), '');

    PixelPattern R19_07 = of(new String[] {"111", "011", "001"},6, 3, Vec2d.of(7, 19), '');

    PixelPattern R19_08 = of(new String[] {"000", "111", "001"},4, 3, Vec2d.of(8, 19), '');

    PixelPattern R19_09 = of(new String[] {"100", "111", "001"},5, 3, Vec2d.of(9, 19), '');

    PixelPattern R19_10 = of(new String[] {"010", "111", "001"},5, 3, Vec2d.of(10, 19), '');

    PixelPattern R19_11 = of(new String[] {"110", "111", "001"},6, 3, Vec2d.of(11, 19), '');

    PixelPattern R19_12 = of(new String[] {"001", "111", "001"},5, 3, Vec2d.of(12, 19), '');

    PixelPattern R19_13 = of(new String[] {"101", "111", "001"},6, 3, Vec2d.of(13, 19), '');

    PixelPattern R19_14 = of(new String[] {"011", "111", "001"},6, 3, Vec2d.of(14, 19), '');

    PixelPattern R19_15 = of(new String[] {"111", "111", "001"},7, 3, Vec2d.of(15, 19), '');

    PixelPattern R20_00 = of(new String[] {"000", "000", "101"},2, 3, Vec2d.of(0, 20), '');

    PixelPattern R20_01 = of(new String[] {"100", "000", "101"},3, 3, Vec2d.of(1, 20), '');

    PixelPattern R20_02 = of(new String[] {"010", "000", "101"},3, 3, Vec2d.of(2, 20), '');

    PixelPattern R20_03 = of(new String[] {"110", "000", "101"},4, 3, Vec2d.of(3, 20), '');

    PixelPattern R20_04 = of(new String[] {"001", "000", "101"},3, 3, Vec2d.of(4, 20), '');

    PixelPattern R20_05 = of(new String[] {"101", "000", "101"},4, 3, Vec2d.of(5, 20), '');

    PixelPattern R20_06 = of(new String[] {"011", "000", "101"},4, 3, Vec2d.of(6, 20), '');

    PixelPattern R20_07 = of(new String[] {"111", "000", "101"},5, 3, Vec2d.of(7, 20), '');

    PixelPattern R20_08 = of(new String[] {"000", "100", "101"},3, 3, Vec2d.of(8, 20), '');

    PixelPattern R20_09 = of(new String[] {"100", "100", "101"},4, 3, Vec2d.of(9, 20), '');

    PixelPattern R20_10 = of(new String[] {"010", "100", "101"},4, 3, Vec2d.of(10, 20), '');

    PixelPattern R20_11 = of(new String[] {"110", "100", "101"},5, 3, Vec2d.of(11, 20), '');

    PixelPattern R20_12 = of(new String[] {"001", "100", "101"},4, 3, Vec2d.of(12, 20), '');

    PixelPattern R20_13 = of(new String[] {"101", "100", "101"},5, 3, Vec2d.of(13, 20), '');

    PixelPattern R20_14 = of(new String[] {"011", "100", "101"},5, 3, Vec2d.of(14, 20), '');

    PixelPattern R20_15 = of(new String[] {"111", "100", "101"},6, 3, Vec2d.of(15, 20), '');

    PixelPattern R21_00 = of(new String[] {"000", "010", "101"},3, 3, Vec2d.of(0, 21), '');

    PixelPattern R21_01 = of(new String[] {"100", "010", "101"},4, 3, Vec2d.of(1, 21), '');

    PixelPattern R21_02 = of(new String[] {"010", "010", "101"},4, 3, Vec2d.of(2, 21), '');

    PixelPattern R21_03 = of(new String[] {"110", "010", "101"},5, 3, Vec2d.of(3, 21), '');

    PixelPattern R21_04 = of(new String[] {"001", "010", "101"},4, 3, Vec2d.of(4, 21), '');

    PixelPattern R21_05 = of(new String[] {"101", "010", "101"},5, 3, Vec2d.of(5, 21), '');

    PixelPattern R21_06 = of(new String[] {"011", "010", "101"},5, 3, Vec2d.of(6, 21), '');

    PixelPattern R21_07 = of(new String[] {"111", "010", "101"},6, 3, Vec2d.of(7, 21), '');

    PixelPattern R21_08 = of(new String[] {"000", "110", "101"},4, 3, Vec2d.of(8, 21), '');

    PixelPattern R21_09 = of(new String[] {"100", "110", "101"},5, 3, Vec2d.of(9, 21), '');

    PixelPattern R21_10 = of(new String[] {"010", "110", "101"},5, 3, Vec2d.of(10, 21), '');

    PixelPattern R21_11 = of(new String[] {"110", "110", "101"},6, 3, Vec2d.of(11, 21), '');

    PixelPattern R21_12 = of(new String[] {"001", "110", "101"},5, 3, Vec2d.of(12, 21), '');

    PixelPattern R21_13 = of(new String[] {"101", "110", "101"},6, 3, Vec2d.of(13, 21), '');

    PixelPattern R21_14 = of(new String[] {"011", "110", "101"},6, 3, Vec2d.of(14, 21), '');

    PixelPattern R21_15 = of(new String[] {"111", "110", "101"},7, 3, Vec2d.of(15, 21), '');

    PixelPattern R22_00 = of(new String[] {"000", "001", "101"},3, 3, Vec2d.of(0, 22), '');

    PixelPattern R22_01 = of(new String[] {"100", "001", "101"},4, 3, Vec2d.of(1, 22), '');

    PixelPattern R22_02 = of(new String[] {"010", "001", "101"},4, 3, Vec2d.of(2, 22), '');

    PixelPattern R22_03 = of(new String[] {"110", "001", "101"},5, 3, Vec2d.of(3, 22), '');

    PixelPattern R22_04 = of(new String[] {"001", "001", "101"},4, 3, Vec2d.of(4, 22), '');

    PixelPattern R22_05 = of(new String[] {"101", "001", "101"},5, 3, Vec2d.of(5, 22), '');

    PixelPattern R22_06 = of(new String[] {"011", "001", "101"},5, 3, Vec2d.of(6, 22), '');

    PixelPattern R22_07 = of(new String[] {"111", "001", "101"},6, 3, Vec2d.of(7, 22), '');

    PixelPattern R22_08 = of(new String[] {"000", "101", "101"},4, 3, Vec2d.of(8, 22), '');

    PixelPattern R22_09 = of(new String[] {"100", "101", "101"},5, 3, Vec2d.of(9, 22), '');

    PixelPattern R22_10 = of(new String[] {"010", "101", "101"},5, 3, Vec2d.of(10, 22), '');

    PixelPattern R22_11 = of(new String[] {"110", "101", "101"},6, 3, Vec2d.of(11, 22), '');

    PixelPattern R22_12 = of(new String[] {"001", "101", "101"},5, 3, Vec2d.of(12, 22), '');

    PixelPattern R22_13 = of(new String[] {"101", "101", "101"},6, 3, Vec2d.of(13, 22), '');

    PixelPattern R22_14 = of(new String[] {"011", "101", "101"},6, 3, Vec2d.of(14, 22), '');

    PixelPattern R22_15 = of(new String[] {"111", "101", "101"},7, 3, Vec2d.of(15, 22), '');

    PixelPattern R23_00 = of(new String[] {"000", "011", "101"},4, 3, Vec2d.of(0, 23), '');

    PixelPattern R23_01 = of(new String[] {"100", "011", "101"},5, 3, Vec2d.of(1, 23), '');

    PixelPattern R23_02 = of(new String[] {"010", "011", "101"},5, 3, Vec2d.of(2, 23), '');

    PixelPattern R23_03 = of(new String[] {"110", "011", "101"},6, 3, Vec2d.of(3, 23), '');

    PixelPattern R23_04 = of(new String[] {"001", "011", "101"},5, 3, Vec2d.of(4, 23), '');

    PixelPattern R23_05 = of(new String[] {"101", "011", "101"},6, 3, Vec2d.of(5, 23), '');

    PixelPattern R23_06 = of(new String[] {"011", "011", "101"},6, 3, Vec2d.of(6, 23), '');

    PixelPattern R23_07 = of(new String[] {"111", "011", "101"},7, 3, Vec2d.of(7, 23), '');

    PixelPattern R23_08 = of(new String[] {"000", "111", "101"},5, 3, Vec2d.of(8, 23), '');

    PixelPattern R23_09 = of(new String[] {"100", "111", "101"},6, 3, Vec2d.of(9, 23), '');

    PixelPattern R23_10 = of(new String[] {"010", "111", "101"},6, 3, Vec2d.of(10, 23), '');

    PixelPattern R23_11 = of(new String[] {"110", "111", "101"},7, 3, Vec2d.of(11, 23), '');

    PixelPattern R23_12 = of(new String[] {"001", "111", "101"},6, 3, Vec2d.of(12, 23), '');

    PixelPattern R23_13 = of(new String[] {"101", "111", "101"},7, 3, Vec2d.of(13, 23), '');

    PixelPattern R23_14 = of(new String[] {"011", "111", "101"},7, 3, Vec2d.of(14, 23), '');

    PixelPattern R23_15 = of(new String[] {"111", "111", "101"},8, 3, Vec2d.of(15, 23), '');

    PixelPattern R24_00 = of(new String[] {"000", "000", "011"},2, 2, Vec2d.of(0, 24), '');

    PixelPattern R24_01 = of(new String[] {"100", "000", "011"},3, 3, Vec2d.of(1, 24), '');

    PixelPattern R24_02 = of(new String[] {"010", "000", "011"},3, 2, Vec2d.of(2, 24), '');

    PixelPattern R24_03 = of(new String[] {"110", "000", "011"},4, 3, Vec2d.of(3, 24), '');

    PixelPattern R24_04 = of(new String[] {"001", "000", "011"},3, 2, Vec2d.of(4, 24), '');

    PixelPattern R24_05 = of(new String[] {"101", "000", "011"},4, 3, Vec2d.of(5, 24), '');

    PixelPattern R24_06 = of(new String[] {"011", "000", "011"},4, 2, Vec2d.of(6, 24), '');

    PixelPattern R24_07 = of(new String[] {"111", "000", "011"},5, 3, Vec2d.of(7, 24), '');

    PixelPattern R24_08 = of(new String[] {"000", "100", "011"},3, 3, Vec2d.of(8, 24), '');

    PixelPattern R24_09 = of(new String[] {"100", "100", "011"},4, 3, Vec2d.of(9, 24), '');

    PixelPattern R24_10 = of(new String[] {"010", "100", "011"},4, 3, Vec2d.of(10, 24), '');

    PixelPattern R24_11 = of(new String[] {"110", "100", "011"},5, 3, Vec2d.of(11, 24), '');

    PixelPattern R24_12 = of(new String[] {"001", "100", "011"},4, 3, Vec2d.of(12, 24), '');

    PixelPattern R24_13 = of(new String[] {"101", "100", "011"},5, 3, Vec2d.of(13, 24), '');

    PixelPattern R24_14 = of(new String[] {"011", "100", "011"},5, 3, Vec2d.of(14, 24), '');

    PixelPattern R24_15 = of(new String[] {"111", "100", "011"},6, 3, Vec2d.of(15, 24), '');

    PixelPattern R25_00 = of(new String[] {"000", "010", "011"},3, 2, Vec2d.of(0, 25), '');

    PixelPattern R25_01 = of(new String[] {"100", "010", "011"},4, 3, Vec2d.of(1, 25), '');

    PixelPattern R25_02 = of(new String[] {"010", "010", "011"},4, 2, Vec2d.of(2, 25), '');

    PixelPattern R25_03 = of(new String[] {"110", "010", "011"},5, 3, Vec2d.of(3, 25), '');

    PixelPattern R25_04 = of(new String[] {"001", "010", "011"},4, 2, Vec2d.of(4, 25), '');

    PixelPattern R25_05 = of(new String[] {"101", "010", "011"},5, 3, Vec2d.of(5, 25), '');

    PixelPattern R25_06 = of(new String[] {"011", "010", "011"},5, 2, Vec2d.of(6, 25), '');

    PixelPattern R25_07 = of(new String[] {"111", "010", "011"},6, 3, Vec2d.of(7, 25), '');

    PixelPattern R25_08 = of(new String[] {"000", "110", "011"},4, 3, Vec2d.of(8, 25), '');

    PixelPattern R25_09 = of(new String[] {"100", "110", "011"},5, 3, Vec2d.of(9, 25), '');

    PixelPattern R25_10 = of(new String[] {"010", "110", "011"},5, 3, Vec2d.of(10, 25), '');

    PixelPattern R25_11 = of(new String[] {"110", "110", "011"},6, 3, Vec2d.of(11, 25), '');

    PixelPattern R25_12 = of(new String[] {"001", "110", "011"},5, 3, Vec2d.of(12, 25), '');

    PixelPattern R25_13 = of(new String[] {"101", "110", "011"},6, 3, Vec2d.of(13, 25), '');

    PixelPattern R25_14 = of(new String[] {"011", "110", "011"},6, 3, Vec2d.of(14, 25), '');

    PixelPattern R25_15 = of(new String[] {"111", "110", "011"},7, 3, Vec2d.of(15, 25), '');

    PixelPattern R26_00 = of(new String[] {"000", "001", "011"},3, 2, Vec2d.of(0, 26), '');

    PixelPattern R26_01 = of(new String[] {"100", "001", "011"},4, 3, Vec2d.of(1, 26), '');

    PixelPattern R26_02 = of(new String[] {"010", "001", "011"},4, 2, Vec2d.of(2, 26), '');

    PixelPattern R26_03 = of(new String[] {"110", "001", "011"},5, 3, Vec2d.of(3, 26), '');

    PixelPattern R26_04 = of(new String[] {"001", "001", "011"},4, 2, Vec2d.of(4, 26), '');

    PixelPattern R26_05 = of(new String[] {"101", "001", "011"},5, 3, Vec2d.of(5, 26), '');

    PixelPattern R26_06 = of(new String[] {"011", "001", "011"},5, 2, Vec2d.of(6, 26), '');

    PixelPattern R26_07 = of(new String[] {"111", "001", "011"},6, 3, Vec2d.of(7, 26), '');

    PixelPattern R26_08 = of(new String[] {"000", "101", "011"},4, 3, Vec2d.of(8, 26), '');

    PixelPattern R26_09 = of(new String[] {"100", "101", "011"},5, 3, Vec2d.of(9, 26), '');

    PixelPattern R26_10 = of(new String[] {"010", "101", "011"},5, 3, Vec2d.of(10, 26), '');

    PixelPattern R26_11 = of(new String[] {"110", "101", "011"},6, 3, Vec2d.of(11, 26), '');

    PixelPattern R26_12 = of(new String[] {"001", "101", "011"},5, 3, Vec2d.of(12, 26), '');

    PixelPattern R26_13 = of(new String[] {"101", "101", "011"},6, 3, Vec2d.of(13, 26), '');

    PixelPattern R26_14 = of(new String[] {"011", "101", "011"},6, 3, Vec2d.of(14, 26), '');

    PixelPattern R26_15 = of(new String[] {"111", "101", "011"},7, 3, Vec2d.of(15, 26), '');

    PixelPattern R27_00 = of(new String[] {"000", "011", "011"},4, 2, Vec2d.of(0, 27), '');

    PixelPattern R27_01 = of(new String[] {"100", "011", "011"},5, 3, Vec2d.of(1, 27), '');

    PixelPattern R27_02 = of(new String[] {"010", "011", "011"},5, 2, Vec2d.of(2, 27), '');

    PixelPattern R27_03 = of(new String[] {"110", "011", "011"},6, 3, Vec2d.of(3, 27), '');

    PixelPattern R27_04 = of(new String[] {"001", "011", "011"},5, 2, Vec2d.of(4, 27), '');

    PixelPattern R27_05 = of(new String[] {"101", "011", "011"},6, 3, Vec2d.of(5, 27), '');

    PixelPattern R27_06 = of(new String[] {"011", "011", "011"},6, 2, Vec2d.of(6, 27), '');

    PixelPattern R27_07 = of(new String[] {"111", "011", "011"},7, 3, Vec2d.of(7, 27), '');

    PixelPattern R27_08 = of(new String[] {"000", "111", "011"},5, 3, Vec2d.of(8, 27), '');

    PixelPattern R27_09 = of(new String[] {"100", "111", "011"},6, 3, Vec2d.of(9, 27), '');

    PixelPattern R27_10 = of(new String[] {"010", "111", "011"},6, 3, Vec2d.of(10, 27), '');

    PixelPattern R27_11 = of(new String[] {"110", "111", "011"},7, 3, Vec2d.of(11, 27), '');

    PixelPattern R27_12 = of(new String[] {"001", "111", "011"},6, 3, Vec2d.of(12, 27), '');

    PixelPattern R27_13 = of(new String[] {"101", "111", "011"},7, 3, Vec2d.of(13, 27), '');

    PixelPattern R27_14 = of(new String[] {"011", "111", "011"},7, 3, Vec2d.of(14, 27), '');

    PixelPattern R27_15 = of(new String[] {"111", "111", "011"},8, 3, Vec2d.of(15, 27), '');

    PixelPattern R28_00 = of(new String[] {"000", "000", "111"},3, 3, Vec2d.of(0, 28), '');

    PixelPattern R28_01 = of(new String[] {"100", "000", "111"},4, 3, Vec2d.of(1, 28), '');

    PixelPattern R28_02 = of(new String[] {"010", "000", "111"},4, 3, Vec2d.of(2, 28), '');

    PixelPattern R28_03 = of(new String[] {"110", "000", "111"},5, 3, Vec2d.of(3, 28), '');

    PixelPattern R28_04 = of(new String[] {"001", "000", "111"},4, 3, Vec2d.of(4, 28), '');

    PixelPattern R28_05 = of(new String[] {"101", "000", "111"},5, 3, Vec2d.of(5, 28), '');

    PixelPattern R28_06 = of(new String[] {"011", "000", "111"},5, 3, Vec2d.of(6, 28), '');

    PixelPattern R28_07 = of(new String[] {"111", "000", "111"},6, 3, Vec2d.of(7, 28), '');

    PixelPattern R28_08 = of(new String[] {"000", "100", "111"},4, 3, Vec2d.of(8, 28), '');

    PixelPattern R28_09 = of(new String[] {"100", "100", "111"},5, 3, Vec2d.of(9, 28), '');

    PixelPattern R28_10 = of(new String[] {"010", "100", "111"},5, 3, Vec2d.of(10, 28), '');

    PixelPattern R28_11 = of(new String[] {"110", "100", "111"},6, 3, Vec2d.of(11, 28), '');

    PixelPattern R28_12 = of(new String[] {"001", "100", "111"},5, 3, Vec2d.of(12, 28), '');

    PixelPattern R28_13 = of(new String[] {"101", "100", "111"},6, 3, Vec2d.of(13, 28), '');

    PixelPattern R28_14 = of(new String[] {"011", "100", "111"},6, 3, Vec2d.of(14, 28), '');

    PixelPattern R28_15 = of(new String[] {"111", "100", "111"},7, 3, Vec2d.of(15, 28), '');

    PixelPattern R29_00 = of(new String[] {"000", "010", "111"},4, 3, Vec2d.of(0, 29), '');

    PixelPattern R29_01 = of(new String[] {"100", "010", "111"},5, 3, Vec2d.of(1, 29), '');

    PixelPattern R29_02 = of(new String[] {"010", "010", "111"},5, 3, Vec2d.of(2, 29), '');

    PixelPattern R29_03 = of(new String[] {"110", "010", "111"},6, 3, Vec2d.of(3, 29), '');

    PixelPattern R29_04 = of(new String[] {"001", "010", "111"},5, 3, Vec2d.of(4, 29), '');

    PixelPattern R29_05 = of(new String[] {"101", "010", "111"},6, 3, Vec2d.of(5, 29), '');

    PixelPattern R29_06 = of(new String[] {"011", "010", "111"},6, 3, Vec2d.of(6, 29), '');

    PixelPattern R29_07 = of(new String[] {"111", "010", "111"},7, 3, Vec2d.of(7, 29), '');

    PixelPattern R29_08 = of(new String[] {"000", "110", "111"},5, 3, Vec2d.of(8, 29), '');

    PixelPattern R29_09 = of(new String[] {"100", "110", "111"},6, 3, Vec2d.of(9, 29), '');

    PixelPattern R29_10 = of(new String[] {"010", "110", "111"},6, 3, Vec2d.of(10, 29), '');

    PixelPattern R29_11 = of(new String[] {"110", "110", "111"},7, 3, Vec2d.of(11, 29), '');

    PixelPattern R29_12 = of(new String[] {"001", "110", "111"},6, 3, Vec2d.of(12, 29), '');

    PixelPattern R29_13 = of(new String[] {"101", "110", "111"},7, 3, Vec2d.of(13, 29), '');

    PixelPattern R29_14 = of(new String[] {"011", "110", "111"},7, 3, Vec2d.of(14, 29), '');

    PixelPattern R29_15 = of(new String[] {"111", "110", "111"},8, 3, Vec2d.of(15, 29), '');

    PixelPattern R30_00 = of(new String[] {"000", "001", "111"},4, 3, Vec2d.of(0, 30), '');

    PixelPattern R30_01 = of(new String[] {"100", "001", "111"},5, 3, Vec2d.of(1, 30), '');

    PixelPattern R30_02 = of(new String[] {"010", "001", "111"},5, 3, Vec2d.of(2, 30), '');

    PixelPattern R30_03 = of(new String[] {"110", "001", "111"},6, 3, Vec2d.of(3, 30), '');

    PixelPattern R30_04 = of(new String[] {"001", "001", "111"},5, 3, Vec2d.of(4, 30), '');

    PixelPattern R30_05 = of(new String[] {"101", "001", "111"},6, 3, Vec2d.of(5, 30), '');

    PixelPattern R30_06 = of(new String[] {"011", "001", "111"},6, 3, Vec2d.of(6, 30), '');

    PixelPattern R30_07 = of(new String[] {"111", "001", "111"},7, 3, Vec2d.of(7, 30), '');

    PixelPattern R30_08 = of(new String[] {"000", "101", "111"},5, 3, Vec2d.of(8, 30), '');

    PixelPattern R30_09 = of(new String[] {"100", "101", "111"},6, 3, Vec2d.of(9, 30), '');

    PixelPattern R30_10 = of(new String[] {"010", "101", "111"},6, 3, Vec2d.of(10, 30), '');

    PixelPattern R30_11 = of(new String[] {"110", "101", "111"},7, 3, Vec2d.of(11, 30), '');

    PixelPattern R30_12 = of(new String[] {"001", "101", "111"},6, 3, Vec2d.of(12, 30), '');

    PixelPattern R30_13 = of(new String[] {"101", "101", "111"},7, 3, Vec2d.of(13, 30), '');

    PixelPattern R30_14 = of(new String[] {"011", "101", "111"},7, 3, Vec2d.of(14, 30), '');

    PixelPattern R30_15 = of(new String[] {"111", "101", "111"},8, 3, Vec2d.of(15, 30), '');

    PixelPattern R31_00 = of(new String[] {"000", "011", "111"},5, 3, Vec2d.of(0, 31), '');

    PixelPattern R31_01 = of(new String[] {"100", "011", "111"},6, 3, Vec2d.of(1, 31), '');

    PixelPattern R31_02 = of(new String[] {"010", "011", "111"},6, 3, Vec2d.of(2, 31), '');

    PixelPattern R31_03 = of(new String[] {"110", "011", "111"},7, 3, Vec2d.of(3, 31), '');

    PixelPattern R31_04 = of(new String[] {"001", "011", "111"},6, 3, Vec2d.of(4, 31), '');

    PixelPattern R31_05 = of(new String[] {"101", "011", "111"},7, 3, Vec2d.of(5, 31), '');

    PixelPattern R31_06 = of(new String[] {"011", "011", "111"},7, 3, Vec2d.of(6, 31), '');

    PixelPattern R31_07 = of(new String[] {"111", "011", "111"},8, 3, Vec2d.of(7, 31), '');

    PixelPattern R31_08 = of(new String[] {"000", "111", "111"},6, 3, Vec2d.of(8, 31), '');

    PixelPattern R31_09 = of(new String[] {"100", "111", "111"},7, 3, Vec2d.of(9, 31), '');

    PixelPattern R31_10 = of(new String[] {"010", "111", "111"},7, 3, Vec2d.of(10, 31), '');

    PixelPattern R31_11 = of(new String[] {"110", "111", "111"},8, 3, Vec2d.of(11, 31), '');

    PixelPattern R31_12 = of(new String[] {"001", "111", "111"},7, 3, Vec2d.of(12, 31), '');

    PixelPattern R31_13 = of(new String[] {"101", "111", "111"},8, 3, Vec2d.of(13, 31), '');

    PixelPattern R31_14 = of(new String[] {"011", "111", "111"},8, 3, Vec2d.of(14, 31), '');

    PixelPattern R31_15 = of(new String[] {"111", "111", "111"},9, 3, Vec2d.of(15, 31), '');

    static class DummyStatic {
        public static final Map<String, PixelPattern> VALUES = new HashMap<>();


        static {
            VALUES.put("000100100", R04_08);
            VALUES.put("011010110", R13_06);
            VALUES.put("000100101", R20_08);
            VALUES.put("011010111", R29_06);
            VALUES.put("110100001", R16_11);
            VALUES.put("000111111", R31_08);
            VALUES.put("000111110", R15_08);
            VALUES.put("100000000", R00_01);
            VALUES.put("001001000", R02_04);
            VALUES.put("100000001", R16_01);
            VALUES.put("001001001", R18_04);
            VALUES.put("011101000", R02_14);
            VALUES.put("011101001", R18_14);
            VALUES.put("110100000", R00_11);
            VALUES.put("100011010", R11_01);
            VALUES.put("100011011", R27_01);
            VALUES.put("000100111", R28_08);
            VALUES.put("011010101", R21_06);
            VALUES.put("011010100", R05_06);
            VALUES.put("110100111", R28_11);
            VALUES.put("111001010", R10_07);
            VALUES.put("001001010", R10_04);
            VALUES.put("000111001", R19_08);
            VALUES.put("000111000", R03_08);
            VALUES.put("100000110", R12_01);
            VALUES.put("001001011", R26_04);
            VALUES.put("100000111", R28_01);
            VALUES.put("011101010", R10_14);
            VALUES.put("011101011", R26_14);
            VALUES.put("111001011", R26_07);
            VALUES.put("110100110", R12_11);
            VALUES.put("100011001", R19_01);
            VALUES.put("100011000", R03_01);
            VALUES.put("000100110", R12_08);
            VALUES.put("011011100", R07_06);
            VALUES.put("000100001", R16_08);
            VALUES.put("011010011", R25_06);
            VALUES.put("011010010", R09_06);
            VALUES.put("011011101", R23_06);
            VALUES.put("011101101", R22_14);
            VALUES.put("110111000", R03_11);
            VALUES.put("100001110", R14_01);
            VALUES.put("001001101", R22_04);
            VALUES.put("000111011", R27_08);
            VALUES.put("000111010", R11_08);
            VALUES.put("100001111", R30_01);
            VALUES.put("001000010", R08_04);
            VALUES.put("001001100", R06_04);
            VALUES.put("001000011", R24_04);
            VALUES.put("110111001", R19_11);
            VALUES.put("011100010", R08_14);
            VALUES.put("011101100", R06_14);
            VALUES.put("011100011", R24_14);
            VALUES.put("111001000", R02_07);
            VALUES.put("110101110", R14_11);
            VALUES.put("111001001", R18_07);
            VALUES.put("110101111", R30_11);
            VALUES.put("110010101", R21_03);
            VALUES.put("110010100", R05_03);
            VALUES.put("000100000", R00_08);
            VALUES.put("011010000", R01_06);
            VALUES.put("000101101", R22_08);
            VALUES.put("011010001", R17_06);
            VALUES.put("110101101", R22_11);
            VALUES.put("110111010", R11_11);
            VALUES.put("110111011", R27_11);
            VALUES.put("010110100", R05_10);
            VALUES.put("100000010", R08_01);
            VALUES.put("100001100", R06_01);
            VALUES.put("010110101", R21_10);
            VALUES.put("100000011", R24_01);
            VALUES.put("001001110", R14_04);
            VALUES.put("100001101", R22_01);
            VALUES.put("001001111", R30_04);
            VALUES.put("011101110", R14_14);
            VALUES.put("011101111", R30_14);
            VALUES.put("110100010", R08_11);
            VALUES.put("111001110", R14_07);
            VALUES.put("110101100", R06_11);
            VALUES.put("110100011", R24_11);
            VALUES.put("111001111", R30_07);
            VALUES.put("110010111", R29_03);
            VALUES.put("000101100", R06_08);
            VALUES.put("000100010", R08_08);
            VALUES.put("110010110", R13_03);
            VALUES.put("000100011", R24_08);
            VALUES.put("001011000", R03_04);
            VALUES.put("001011001", R19_04);
            VALUES.put("110110010", R09_11);
            VALUES.put("000110101", R21_08);
            VALUES.put("000110100", R05_08);
            VALUES.put("100001010", R10_01);
            VALUES.put("100001011", R26_01);
            VALUES.put("001000111", R28_04);
            VALUES.put("001000110", R12_04);
            VALUES.put("110111100", R07_11);
            VALUES.put("110110011", R25_11);
            VALUES.put("110111101", R23_11);
            VALUES.put("111000011", R24_07);
            VALUES.put("111000010", R08_07);
            VALUES.put("111001100", R06_07);
            VALUES.put("111001101", R22_07);
            VALUES.put("100101011", R26_09);
            VALUES.put("100101010", R10_09);
            VALUES.put("110010001", R17_03);
            VALUES.put("110010000", R01_03);
            VALUES.put("001011010", R11_04);
            VALUES.put("110011100", R07_03);
            VALUES.put("001011011", R27_04);
            VALUES.put("110011101", R23_03);
            VALUES.put("111011010", R11_07);
            VALUES.put("111011011", R27_07);
            VALUES.put("110111110", R15_11);
            VALUES.put("000110110", R13_08);
            VALUES.put("000110111", R29_08);
            VALUES.put("100001000", R02_01);
            VALUES.put("001000001", R16_04);
            VALUES.put("100001001", R18_01);
            VALUES.put("001000000", R00_04);
            VALUES.put("110111111", R31_11);
            VALUES.put("111000001", R16_07);
            VALUES.put("111000000", R00_07);
            VALUES.put("110010011", R25_03);
            VALUES.put("110010010", R09_03);
            VALUES.put("110011110", R15_03);
            VALUES.put("001010010", R09_04);
            VALUES.put("110011111", R31_03);
            VALUES.put("001011100", R07_04);
            VALUES.put("001010011", R25_04);
            VALUES.put("001011101", R23_04);
            VALUES.put("111011001", R19_07);
            VALUES.put("100101111", R30_09);
            VALUES.put("110100100", R04_11);
            VALUES.put("111011000", R03_07);
            VALUES.put("110100101", R20_11);
            VALUES.put("000110000", R01_08);
            VALUES.put("000110001", R17_08);
            VALUES.put("110000100", R04_03);
            VALUES.put("110000101", R20_03);
            VALUES.put("110110110", R13_11);
            VALUES.put("111000111", R28_07);
            VALUES.put("110110111", R29_11);
            VALUES.put("111000110", R12_07);
            VALUES.put("100101110", R14_09);
            VALUES.put("110011000", R03_03);
            VALUES.put("001011110", R15_04);
            VALUES.put("110011001", R19_03);
            VALUES.put("001011111", R31_04);
            VALUES.put("111011111", R31_07);
            VALUES.put("100101001", R18_09);
            VALUES.put("111011110", R15_07);
            VALUES.put("000111101", R23_08);
            VALUES.put("000110011", R25_08);
            VALUES.put("000111100", R07_08);
            VALUES.put("001000100", R04_04);
            VALUES.put("110000111", R28_03);
            VALUES.put("000110010", R09_08);
            VALUES.put("110000110", R12_03);
            VALUES.put("001000101", R20_04);
            VALUES.put("110110000", R01_11);
            VALUES.put("111000101", R20_07);
            VALUES.put("111000100", R04_07);
            VALUES.put("110110001", R17_11);
            VALUES.put("100101000", R02_09);
            VALUES.put("010100101", R20_10);
            VALUES.put("001010110", R13_04);
            VALUES.put("001010111", R29_04);
            VALUES.put("111011100", R07_07);
            VALUES.put("111010010", R09_07);
            VALUES.put("111011101", R23_07);
            VALUES.put("001101001", R18_12);
            VALUES.put("111010011", R25_07);
            VALUES.put("010111000", R03_10);
            VALUES.put("010111001", R19_10);
            VALUES.put("011110000", R01_14);
            VALUES.put("011110001", R17_14);
            VALUES.put("001101000", R02_12);
            VALUES.put("000011110", R15_00);
            VALUES.put("000011111", R31_00);
            VALUES.put("010100100", R04_10);
            VALUES.put("001010000", R01_04);
            VALUES.put("001010001", R17_04);
            VALUES.put("111010001", R17_07);
            VALUES.put("111010000", R01_07);
            VALUES.put("001101111", R30_12);
            VALUES.put("000000101", R20_00);
            VALUES.put("000000100", R04_00);
            VALUES.put("010111010", R11_10);
            VALUES.put("010111011", R27_10);
            VALUES.put("011110110", R13_14);
            VALUES.put("011110111", R29_14);
            VALUES.put("001101110", R14_12);
            VALUES.put("000011101", R23_00);
            VALUES.put("000010011", R25_00);
            VALUES.put("000010010", R09_00);
            VALUES.put("000011100", R07_00);
            VALUES.put("001101101", R22_12);
            VALUES.put("001100011", R24_12);
            VALUES.put("111010111", R29_07);
            VALUES.put("001101100", R06_12);
            VALUES.put("111010110", R13_07);
            VALUES.put("011110101", R21_14);
            VALUES.put("011110100", R05_14);
            VALUES.put("000010000", R01_00);
            VALUES.put("001100010", R08_12);
            VALUES.put("100010101", R21_01);
            VALUES.put("000010001", R17_00);
            VALUES.put("100010100", R05_01);
            VALUES.put("111010101", R21_07);
            VALUES.put("001010100", R05_04);
            VALUES.put("001010101", R21_04);
            VALUES.put("001100000", R00_12);
            VALUES.put("111010100", R05_07);
            VALUES.put("001100001", R16_12);
            VALUES.put("000010111", R29_00);
            VALUES.put("000010110", R13_00);
            VALUES.put("011111010", R11_14);
            VALUES.put("011100111", R28_14);
            VALUES.put("011111011", R27_14);
            VALUES.put("001111010", R11_12);
            VALUES.put("111101101", R22_15);
            VALUES.put("111100011", R24_15);
            VALUES.put("111101100", R06_15);
            VALUES.put("010110110", R13_10);
            VALUES.put("010110111", R29_10);
            VALUES.put("111100010", R08_15);
            VALUES.put("001111011", R27_12);
            VALUES.put("011100110", R12_14);
            VALUES.put("000010100", R05_00);
            VALUES.put("110101011", R26_11);
            VALUES.put("000010101", R21_00);
            VALUES.put("110101010", R10_11);
            VALUES.put("100010000", R01_01);
            VALUES.put("010101111", R30_10);
            VALUES.put("100010001", R17_01);
            VALUES.put("000101111", R30_08);
            VALUES.put("000101110", R14_08);
            VALUES.put("010101110", R14_10);
            VALUES.put("101010100", R05_05);
            VALUES.put("101010101", R21_05);
            VALUES.put("011100001", R16_14);
            VALUES.put("011111000", R03_14);
            VALUES.put("111101111", R30_15);
            VALUES.put("010110000", R01_10);
            VALUES.put("010110001", R17_10);
            VALUES.put("111101110", R14_15);
            VALUES.put("011111001", R19_14);
            VALUES.put("011100000", R00_14);
            VALUES.put("110101000", R02_11);
            VALUES.put("110101001", R18_11);
            VALUES.put("010101100", R06_10);
            VALUES.put("010100010", R08_10);
            VALUES.put("100010110", R13_01);
            VALUES.put("010101101", R22_10);
            VALUES.put("100010111", R29_01);
            VALUES.put("000101001", R18_08);
            VALUES.put("000101000", R02_08);
            VALUES.put("010100011", R24_10);
            VALUES.put("101010111", R29_05);
            VALUES.put("101010110", R13_05);
            VALUES.put("011111110", R15_14);
            VALUES.put("111101001", R18_15);
            VALUES.put("010110010", R09_10);
            VALUES.put("010111100", R07_10);
            VALUES.put("010110011", R25_10);
            VALUES.put("010111101", R23_10);
            VALUES.put("111101000", R02_15);
            VALUES.put("100000100", R04_01);
            VALUES.put("100000101", R20_01);
            VALUES.put("011111111", R31_14);
            VALUES.put("000101010", R10_08);
            VALUES.put("100011111", R31_01);
            VALUES.put("010100001", R16_10);
            VALUES.put("010100000", R00_10);
            VALUES.put("100011110", R15_01);
            VALUES.put("000101011", R26_08);
            VALUES.put("101010001", R17_05);
            VALUES.put("101010000", R01_05);
            VALUES.put("011100100", R04_14);
            VALUES.put("011110010", R09_14);
            VALUES.put("011100101", R20_14);
            VALUES.put("010111110", R15_10);
            VALUES.put("010111111", R31_10);
            VALUES.put("111101011", R26_15);
            VALUES.put("111101010", R10_15);
            VALUES.put("011111100", R07_14);
            VALUES.put("011110011", R25_14);
            VALUES.put("011111101", R23_14);
            VALUES.put("001101011", R26_12);
            VALUES.put("001101010", R10_12);
            VALUES.put("100011100", R07_01);
            VALUES.put("100010011", R25_01);
            VALUES.put("100011101", R23_01);
            VALUES.put("010100111", R28_10);
            VALUES.put("100010010", R09_01);
            VALUES.put("010100110", R12_10);
            VALUES.put("101011101", R23_05);
            VALUES.put("010011110", R15_02);
            VALUES.put("101011100", R07_05);
            VALUES.put("101010010", R09_05);
            VALUES.put("101010011", R25_05);
            VALUES.put("001110000", R01_12);
            VALUES.put("001110001", R17_12);
            VALUES.put("111111111", R31_15);
            VALUES.put("111111110", R15_15);
            VALUES.put("010011111", R31_02);
            VALUES.put("010011000", R03_02);
            VALUES.put("101011111", R31_05);
            VALUES.put("101011110", R15_05);
            VALUES.put("101101011", R26_13);
            VALUES.put("111100100", R04_15);
            VALUES.put("111100101", R20_15);
            VALUES.put("101000101", R20_05);
            VALUES.put("001111100", R07_12);
            VALUES.put("101101010", R10_13);
            VALUES.put("001110011", R25_12);
            VALUES.put("101000100", R04_05);
            VALUES.put("001110010", R09_12);
            VALUES.put("001111101", R23_12);
            VALUES.put("111111101", R23_15);
            VALUES.put("111111100", R07_15);
            VALUES.put("111110010", R09_15);
            VALUES.put("010011001", R19_02);
            VALUES.put("111110011", R25_15);
            VALUES.put("111111010", R11_15);
            VALUES.put("101011001", R19_05);
            VALUES.put("101011000", R03_05);
            VALUES.put("111100110", R12_15);
            VALUES.put("000001001", R18_00);
            VALUES.put("010000111", R28_02);
            VALUES.put("111100111", R28_15);
            VALUES.put("010000110", R12_02);
            VALUES.put("000001000", R02_00);
            VALUES.put("101000110", R12_05);
            VALUES.put("001111111", R31_12);
            VALUES.put("001111110", R15_12);
            VALUES.put("101000111", R28_05);
            VALUES.put("010010001", R17_02);
            VALUES.put("010010000", R01_02);
            VALUES.put("111111011", R27_15);
            VALUES.put("010101010", R10_10);
            VALUES.put("010101011", R26_10);
            VALUES.put("010010010", R09_02);
            VALUES.put("101011010", R11_05);
            VALUES.put("101011011", R27_05);
            VALUES.put("111100000", R00_15);
            VALUES.put("010000101", R20_02);
            VALUES.put("010000100", R04_02);
            VALUES.put("111100001", R16_15);
            VALUES.put("000001010", R10_00);
            VALUES.put("000001011", R26_00);
            VALUES.put("101000000", R00_05);
            VALUES.put("001111001", R19_12);
            VALUES.put("001111000", R03_12);
            VALUES.put("101000001", R16_05);
            VALUES.put("111111001", R19_15);
            VALUES.put("010011101", R23_02);
            VALUES.put("111111000", R03_15);
            VALUES.put("010011100", R07_02);
            VALUES.put("010010011", R25_02);
            VALUES.put("010101001", R18_10);
            VALUES.put("010101000", R02_10);
            VALUES.put("001100110", R12_12);
            VALUES.put("001100111", R28_12);
            VALUES.put("101101101", R22_13);
            VALUES.put("101101100", R06_13);
            VALUES.put("101100011", R24_13);
            VALUES.put("101100010", R08_13);
            VALUES.put("000000011", R24_00);
            VALUES.put("000001100", R06_00);
            VALUES.put("000001101", R22_00);
            VALUES.put("000000010", R08_00);
            VALUES.put("101001101", R22_05);
            VALUES.put("101000011", R24_05);
            VALUES.put("101001100", R06_05);
            VALUES.put("101000010", R08_05);
            VALUES.put("111110100", R05_15);
            VALUES.put("010010101", R21_02);
            VALUES.put("111110101", R21_15);
            VALUES.put("010010100", R05_02);
            VALUES.put("001100101", R20_12);
            VALUES.put("001100100", R04_12);
            VALUES.put("101100001", R16_13);
            VALUES.put("101100000", R00_13);
            VALUES.put("000001111", R30_00);
            VALUES.put("000001110", R14_00);
            VALUES.put("101001111", R30_05);
            VALUES.put("101001110", R14_05);
            VALUES.put("010010111", R29_02);
            VALUES.put("010010110", R13_02);
            VALUES.put("000011010", R11_00);
            VALUES.put("101101001", R18_13);
            VALUES.put("000000111", R28_00);
            VALUES.put("101101000", R02_13);
            VALUES.put("000000110", R12_00);
            VALUES.put("101001001", R18_05);
            VALUES.put("101001000", R02_05);
            VALUES.put("001110101", R21_12);
            VALUES.put("001110100", R05_12);
            VALUES.put("111110001", R17_15);
            VALUES.put("101111011", R27_13);
            VALUES.put("101111010", R11_13);
            VALUES.put("000011011", R27_00);
            VALUES.put("111110000", R01_15);
            VALUES.put("000000001", R16_00);
            VALUES.put("101101111", R30_13);
            VALUES.put("000000000", R00_00);
            VALUES.put("101101110", R14_13);
            VALUES.put("001110110", R13_12);
            VALUES.put("101001011", R26_05);
            VALUES.put("101001010", R10_05);
            VALUES.put("001110111", R29_12);
            VALUES.put("111110111", R29_15);
            VALUES.put("000011000", R03_00);
            VALUES.put("111110110", R13_15);
            VALUES.put("000011001", R19_00);
            VALUES.put("110011011", R27_03);
            VALUES.put("100100001", R16_09);
            VALUES.put("100100000", R00_09);
            VALUES.put("011001000", R02_06);
            VALUES.put("110000001", R16_03);
            VALUES.put("110000000", R00_03);
            VALUES.put("011001001", R18_06);
            VALUES.put("101111110", R15_13);
            VALUES.put("101111111", R31_13);
            VALUES.put("110011010", R11_03);
            VALUES.put("100101101", R22_09);
            VALUES.put("100101100", R06_09);
            VALUES.put("100100011", R24_09);
            VALUES.put("100111010", R11_09);
            VALUES.put("011001110", R14_06);
            VALUES.put("110000010", R08_03);
            VALUES.put("010001010", R10_02);
            VALUES.put("110001101", R22_03);
            VALUES.put("110001100", R06_03);
            VALUES.put("110000011", R24_03);
            VALUES.put("010001011", R26_02);
            VALUES.put("100111011", R27_09);
            VALUES.put("011001111", R30_06);
            VALUES.put("110110100", R05_11);
            VALUES.put("110110101", R21_11);
            VALUES.put("101111000", R03_13);
            VALUES.put("100100010", R08_09);
            VALUES.put("101111001", R19_13);
            VALUES.put("100100100", R04_09);
            VALUES.put("100100101", R20_09);
            VALUES.put("101100111", R28_13);
            VALUES.put("101100110", R12_13);
            VALUES.put("110001110", R14_03);
            VALUES.put("110001111", R30_03);
            VALUES.put("100111001", R19_09);
            VALUES.put("100111000", R03_09);
            VALUES.put("101110001", R17_13);
            VALUES.put("101110000", R01_13);
            VALUES.put("100100111", R28_09);
            VALUES.put("100100110", R12_09);
            VALUES.put("101100101", R20_13);
            VALUES.put("101100100", R04_13);
            VALUES.put("011001011", R26_06);
            VALUES.put("011001010", R10_06);
            VALUES.put("110001000", R02_03);
            VALUES.put("110001001", R18_03);
            VALUES.put("100111111", R31_09);
            VALUES.put("100111110", R15_09);
            VALUES.put("101110010", R09_13);
            VALUES.put("101111101", R23_13);
            VALUES.put("101111100", R07_13);
            VALUES.put("101110011", R25_13);
            VALUES.put("011011001", R19_06);
            VALUES.put("011011000", R03_06);
            VALUES.put("010001101", R22_02);
            VALUES.put("110001011", R26_03);
            VALUES.put("110001010", R10_03);
            VALUES.put("100111101", R23_09);
            VALUES.put("010001100", R06_02);
            VALUES.put("100111100", R07_09);
            VALUES.put("010000011", R24_02);
            VALUES.put("100110011", R25_09);
            VALUES.put("011000111", R28_06);
            VALUES.put("010000010", R08_02);
            VALUES.put("100110010", R09_09);
            VALUES.put("011000110", R12_06);
            VALUES.put("101110100", R05_13);
            VALUES.put("101110101", R21_13);
            VALUES.put("011011111", R31_06);
            VALUES.put("011011110", R15_06);
            VALUES.put("010000001", R16_02);
            VALUES.put("100110001", R17_09);
            VALUES.put("011000101", R20_06);
            VALUES.put("010000000", R00_02);
            VALUES.put("100110000", R01_09);
            VALUES.put("011000100", R04_06);
            VALUES.put("101110111", R29_13);
            VALUES.put("101110110", R13_13);
            VALUES.put("010011011", R27_02);
            VALUES.put("010011010", R11_02);
            VALUES.put("011000010", R08_06);
            VALUES.put("100110111", R29_09);
            VALUES.put("100110110", R13_09);
            VALUES.put("010001001", R18_02);
            VALUES.put("011001101", R22_06);
            VALUES.put("010001000", R02_02);
            VALUES.put("011001100", R06_06);
            VALUES.put("011000011", R24_06);
            VALUES.put("011011011", R27_06);
            VALUES.put("100110101", R21_09);
            VALUES.put("100110100", R05_09);
            VALUES.put("011000001", R16_06);
            VALUES.put("010001111", R30_02);
            VALUES.put("011000000", R00_06);
            VALUES.put("010001110", R14_02);
            VALUES.put("011011010", R11_06);
        }
    }

    String[] pattern();

    int filled();

    int width();

    Vec2d coords();

    char symbol();

    default int empty() {
        return 9 - filled();
    }

    String NAMESPACE = "comet";

    static PixelPattern of(String[] pattern, int filled, int width, Vec2d coords, char symbol) {
        return new Impl(pattern, filled, width, coords, symbol);
    }

    record Impl(String[] pattern, int filled, int width, Vec2d coords, char symbol) implements PixelPattern {

    }

    static Pair<Component, Integer> optimize(BufferedImage section, int font) {
        LinkedHashMap<java.awt.Color, ColorGroup> colorGroups = new LinkedHashMap<>();
        for (int y = 0; y < section.getHeight(); y++) {
            for (int x = 0; x < section.getWidth(); x++) {
                int rgb = section.getRGB(x, y);
                java.awt.Color color = new java.awt.Color(rgb);
                colorGroups.computeIfAbsent(color, c -> new ColorGroup());
                colorGroups.get(color).add(Vec2d.of(x, y));
            }
        }
        boolean first = true;
        Component component = Component.empty();
        int width = 0;
        for (Map.Entry<java.awt.Color, ColorGroup> group : colorGroups.entrySet()) {
            PixelPattern pixelPattern = find(group.getValue());
            if (pixelPattern == null) {
                continue;
            }
            width = Math.max(width, pixelPattern.width());
            if (!first) {
                component = component.append(Component.text("4").font(Key.key(NAMESPACE, "patterns_" + font)));
            }
            first = false;
            component = component.append(Component.text(pixelPattern.symbol()).color(TextColor.color(group.getKey().getRGB()))).font(Key.key(NAMESPACE, "patterns_" + font));
        }
        return Pair.of(component, width);
    }

    static PixelPattern find(ColorGroup group) {
        String[] pattern = new String[]{"000", "000", "000"};
        for (Vec2d coord : group.coords) {
            StringBuilder builder = new StringBuilder(pattern[coord.yi()]);
            builder.setCharAt(coord.xi(), '1');
            pattern[coord.yi()] = builder.toString();
        }
        return DummyStatic.VALUES.get(String.join("", pattern));
    }

    class ColorGroup {
        private final List<Vec2d> coords = new ArrayList<>();

        public void add(Vec2d pos) {
            coords.add(pos);
        }

        public List<Vec2d> coords() {
            return coords;
        }

        public int filled() {
            return coords.size();
        }

    }

}
