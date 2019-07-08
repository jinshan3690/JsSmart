package com.js.smart.common.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public class NavigationViewUtil {

    public static void setNavigationHeight(Context context, NavigationView navigationView, int height) {
        Field fieldByPressenter = null;
        try {
            fieldByPressenter = NavigationView.class.getDeclaredField("presenter");

            fieldByPressenter.setAccessible(true);
            NavigationMenuPresenter menuPresenter = (NavigationMenuPresenter) fieldByPressenter.get(navigationView);
            Field fieldByMenuView = menuPresenter.getClass().getDeclaredField("menuView");
            fieldByMenuView.setAccessible(true);
            final NavigationMenuView mMenuView = (NavigationMenuView) fieldByMenuView.get(menuPresenter);
            mMenuView.setPadding(0,0,0,0);
            mMenuView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {
                    RecyclerView.ViewHolder viewHolder = mMenuView.getChildViewHolder(view);
                    if (viewHolder != null)
                        if ("NormalViewHolder".equals(viewHolder.getClass().getSimpleName())) {
                            ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();
                            params.height = DensityUtil.dp2px(context, height);
                            viewHolder.itemView.setLayoutParams(params);
                        } else if ("SeparatorViewHolder".equals(viewHolder.getClass().getSimpleName())) {
                            viewHolder.itemView.setPadding(0, 0, 0, 0);
                        }else if ("HeaderViewHolder".equals(viewHolder.getClass().getSimpleName())) {
                            viewHolder.itemView.setPadding(0, 0, 0, 0);
                        }
                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
