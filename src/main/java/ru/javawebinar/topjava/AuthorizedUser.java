package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

public class AuthorizedUser {
    private static int id = 1;

    public static int id() {
        return id;
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }
}
