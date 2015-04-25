package io.ichi_go.ichigo;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jmpergar.awesometext.AwesomeTextHandler;

/**
 * Created by ahernand on 4/20/15.
 */


public class HashtagsSpanRenderer implements AwesomeTextHandler.ViewSpanRenderer {

    private final static int textSizeInDips = 18;
    private final static int backgroundResource = R.drawable.common_hashtags_background;
    private final static int textColorResource = android.R.color.white;

    @Override
    public View getView(String text, Context context) {
        TextView view = new TextView(context);
        view.setText(text.substring(1));
        view.setTextSize(ScreenUtils.dipsToPixels(context, textSizeInDips));
        view.setBackgroundResource(backgroundResource);
        int textColor = context.getResources().getColor(textColorResource);
        view.setTextColor(textColor);
        return view;
    }
}