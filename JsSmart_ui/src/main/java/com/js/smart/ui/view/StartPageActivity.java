package com.js.smart.ui.view;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.js.smart.ui.R;
import com.js.smart.ui.R2;
import com.js.smart.ui.UIRoute;
import com.js.smart.ui.app.UIBaseActivity;
import com.js.smart.common.app.ac_anim.mode.AcAnimFast;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

@Route(path = UIRoute.ui_start_page)
public class StartPageActivity extends UIBaseActivity {

    @BindView(R2.id.textView1)
    TextView countTv;

    private Disposable disposable;
    private int count = 1;

    @Autowired
    String nextRoute;

    @Override
    public int createView(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);//恢复原有的样式
        acManager.setStatusTrans(true);
        return R.layout.ac_start_page;
    }

    @Override
    public void initView() {
        countTv.setText(String.valueOf(count));

        disposable = Observable.interval(1, TimeUnit.SECONDS)
                .compose(this.applySchedulers())
                .compose(this.bindToLifecycle())
                .subscribe(count -> {
                    if (this.count - (count + 1) >= 0)
                        countTv.setText(this.count - (count + 1) + "");
                    if (this.count - (count + 1) == 0) {
                        if (StringUtils.isBlank(nextRoute)) {
                            nextRoute = UIRoute.ui_index;
                        }
                        toActivity(nextRoute, AcAnimFast.get());
                        finish();
                    }
                });
    }

    private void disposable() {
        if (!disposable.isDisposed())
            disposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable();
    }
}
