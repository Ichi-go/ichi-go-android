package io.ichi_go.ichigo;

/**
 * Created by ahernand on 4/20/15.
 */
import android.content.Context;

public class ScreenUtils {

    public static int dipsToPixels(Context ctx, float dips) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        int px = (int) (dips * scale + 0.5f);
        return px;
    }
}