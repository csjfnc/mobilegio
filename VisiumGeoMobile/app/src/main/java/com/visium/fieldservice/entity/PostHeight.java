package com.visium.fieldservice.entity;

import java.util.ArrayList;
import java.util.List;


public enum PostHeight {

    _0(0),
    _3(3),
    _4(4),
    _4_5(4.5),
    _5(5),
    _6(6),
    _7(7),
    _8(8),
    _8_5(8.5),
    _9(9),
    _9_5(9.5),
    _10(10),
    _10_5(10.5),
    _11(11),
    _12(12),
    _12_5(12.5),
    _13(13),
    _14(14),
    _15(15),
    _16(16),
    _17(17),
    _17_5(17.5),
    _18(18),
    _18_5(18.5),
    _19(19),
    _19_5(19.5),
    _20(20),
    _21(21),
    _21_5(21.5),
    _22(22),
    _23(23),
    _24(24),
    _25(25),
    _26(26),
    _27(27),
    _28(28),
    _30(30),
    _32(32);

    private double height;

    PostHeight(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public static PostHeight parse(double height) {
        for (PostHeight type : PostHeight.values()) {
            if (type.getHeight() == height) {
                return type;
            }
        }
        return _0;
    }

    public static List<Double> getHeights() {
        List<Double> names = new ArrayList<>();
        for (PostHeight rate : PostHeight.values()) {
            names.add(rate.getHeight());
        }
        return names;
    }

}