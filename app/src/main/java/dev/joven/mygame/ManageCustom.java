package dev.joven.mygame;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class ManageCustom extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public ManageCustom(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
