package io.ichi_go.ichigo;

/**
 * Created by ahernand on 4/21/15.
 */
public class UpdateFlag {

    private static int update = 1;

    public static int getUpdate() {
        return update;
    }

    public static void setUpdate(int update) {
        UpdateFlag.update = update;
    }
}
