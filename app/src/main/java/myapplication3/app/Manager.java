package myapplication3.app;

import android.content.Context;
import android.util.AttributeSet;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Nurlan on 16-Feb-16.
 */
public class Manager extends SlideLayout {
    public Manager(@NotNull Context context) {
        super(context);
    }

    public Manager(@NotNull Context context, @NotNull AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideHandler getSlideHandler(){return new SlideHandler() {
        @Override
        public boolean checkCanDoRefresh() {
            return false;
        }

        @Override
        public boolean checkCanLoadMore() {
            return false;
        }

        @Override
        public void onRefreshBegin() {

        }

        @Override
        public void onLoadMoreBegin() {

        }
    };
    }
    SlideLayout getLayout(Context context){return new SlideLayout(context);}
}
