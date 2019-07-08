package com.js.smart.http.presenter;


import com.js.smart.http.Api;
import com.js.smart.http.bean.Charges;
import com.js.smart.common.app.BaseCompatActivity;
import com.js.smart.http.result.HttpResult;
import com.js.smart.http.ApiMethod;
import com.js.smart.http.presenter.action.SystemAction;

public class SystemPresenter implements SystemAction.Presenter {

    private BaseCompatActivity context;
    private SystemAction.View action;

    public SystemPresenter(BaseCompatActivity context, SystemAction.View action) {
        this.context = context;
        this.action = action;
    }
    @Override
    public void getRegisterSmsCode(String mobile) {
        action.showLoading();
        Api.system().getSmsCode(ApiMethod.getSmsCode, 0, mobile)
                .compose(context.applySchedulers())
                .compose(context.bindToLifecycle())
                .subscribe(new HttpResult<String>(context) {
                    @Override
                    protected void onResult(String result) {
                        action.hideLoading();
                        action.getSmsCodeDone();
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        action.hideLoading();
                        action.getSmsCodeFailDone();
                    }
                });
    }

    @Override
    public void getForgotSmsCode(String mobile) {
        action.showLoading();
        Api.system().getSmsCode(ApiMethod.getSmsCode, 1, mobile)
                .compose(context.applySchedulers())
                .compose(context.bindToLifecycle())
                .subscribe(new HttpResult<String>(context) {
                    @Override
                    protected void onResult(String result) {
                        action.hideLoading();
                        action.getSmsCodeDone();
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        action.hideLoading();
                        action.getSmsCodeFailDone();
                    }
                });
    }

    @Override
    public void getRegisterEmailCode(String email) {
        action.showLoading();
        Api.system().getEmailCode(ApiMethod.getEmailCode, 0, email)
                .compose(context.applySchedulers())
                .compose(context.bindToLifecycle())
                .subscribe(new HttpResult<String>(context) {
                    @Override
                    protected void onResult(String result) {
                        action.hideLoading();
                        action.getSmsCodeDone();
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        action.hideLoading();
                        action.getSmsCodeFailDone();
                    }
                });
    }

    @Override
    public void getForgotEmailCode(String email) {
        action.showLoading();
        Api.system().getEmailCode(ApiMethod.getEmailCode, 1, email)
                .compose(context.applySchedulers())
                .compose(context.bindToLifecycle())
                .subscribe(new HttpResult<String>(context) {
                    @Override
                    protected void onResult(String result) {
                        action.hideLoading();
                        action.getSmsCodeDone();
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        action.hideLoading();
                        action.getSmsCodeFailDone();
                    }
                });
    }

    @Override
    public void logout(String userName) {
        action.showLoading();
        Api.system().logout(ApiMethod.logout, userName)
                .compose(context.applySchedulers())
                .compose(context.bindToLifecycle())
                .subscribe(new HttpResult<String>(context) {
                    @Override
                    protected void onResult(String result) {
                        action.hideLoading();
                        action.logoutDone();
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        action.hideLoading();
                    }
                });
    }

    @Override
    public void getCharges() {
        action.showLoading();
        Api.system().getCharges(ApiMethod.getCharges)
                .compose(context.applySchedulers())
                .compose(context.bindToLifecycle())
                .subscribe(new HttpResult<Charges>(context) {
                    @Override
                    protected void onResult(Charges result) {
                        action.hideLoading();
                        action.getChargesDone(result);
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        action.hideLoading();
                    }
                });
    }
}
