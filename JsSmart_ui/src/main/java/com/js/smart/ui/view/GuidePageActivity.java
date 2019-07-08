package com.js.smart.ui.view;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.js.smart.ui.R;
import com.js.smart.ui.R2;
import com.js.smart.ui.app.UIBaseActivity;
import com.js.smart.ui.widget.Indicator;
import com.js.smart.common.app.ac_anim.mode.AcAnimFast;
import com.js.smart.ui.UIRoute;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
@Route(path = UIRoute.ui_guide_page)
public class GuidePageActivity extends UIBaseActivity {

    @BindView(R2.id.viewPager1)
    ViewPager viewPager;

    @Autowired
    String nextRoute;

    @Override
    public int createView(Bundle savedInstanceState) {
        acManager.setStateBarColor(R.color.statusBarColor);
        return R.layout.ac_guide_page;
    }

    @Override
    public void initView() {
        acManager.setBackKey(false);

        final Indicator indicator = new Indicator(context);
        indicator.setPager(5);
        viewPager.setAdapter(new SamplePagerAdapter(new String[5]));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                indicator.setPosition(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ((RelativeLayout)view).addView(indicator);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            float downX;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float x = motionEvent.getX();

                        if (downX != 0 && viewPager.getCurrentItem() == viewPager.getChildCount() - 1
                                && x - downX < -100){
                            if (StringUtils.isBlank(nextRoute)) {
                                nextRoute = UIRoute.ui_index;
                            }
                            toActivity(view, nextRoute, AcAnimFast.get());
                            finish();
                        }
                        downX = 0;
                        break;
                }
                return false;
            }
        });
        viewPager.setOffscreenPageLimit(5 - 1);
    }

    class SamplePagerAdapter extends PagerAdapter {

        private String[] images;

        public SamplePagerAdapter(String[] images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView photoView = new ImageView(container.getContext());
//            Glide.with(context).load(ApiManager.HOST + images[position])
//                    .crossFade().into(photoView);
            photoView.setImageResource(R.mipmap.ico_logo);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
