/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.js.smart.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.js.smart.common.app.AntiShakeOnClickListener;
import com.js.smart.ui.R;
import com.js.smart.ui.R2;
import com.js.smart.ui.UIRoute;
import com.js.smart.ui.app.UIBaseActivity;
import com.js.smart.ui.widget.Indicator;

import java.io.File;

import butterknife.BindView;

@Route(path = UIRoute.ui_photo)
public class PhotoPagerActivity extends UIBaseActivity {

    @BindView(R2.id.layout1)
    ViewGroup group;
    @BindView(R2.id.viewPager1)
    ViewPager mViewPager;

    @Autowired
    String[] images;
    @Autowired
    int current;

    @Override
    public int createView(Bundle savedInstanceState) {
        return R.layout.ac_photo_pager;
    }

    @Override
    public void initView() {
        titleUtil.setDefaultLeftClick();

        titleUtil.setRightText(getIntent().getStringExtra("titleRight")).setRightClick(new AntiShakeOnClickListener() {
            @Override
            protected void antiShakeOnClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("position",current);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        titleUtil.setTitle(1 +"/"+images.length);
        final Indicator indicator = new Indicator(context);
        indicator.setPager(images.length);
        mViewPager.setAdapter(new SamplePagerAdapter(images));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                titleUtil.setTitle(position+1 +"/"+images.length);
                indicator.setPosition(position);
                current = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(getIntent().getIntExtra("position",0));
        group.addView(indicator);
    }

    /**
     * Action
     */

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
            PhotoView photoView = new PhotoView(container.getContext());
            String path = images[position];
            if (path.contains(".jpg")){
                Glide.with(context).load(new File(path)).into(photoView);
            }else{
                Glide.with(context).load( path).into(photoView);
            }
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

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
