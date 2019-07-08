package com.js.smart.http.presenter.action;


import com.js.smart.http.bean.Charges;
import com.js.smart.common.app.BaseView;

public interface SystemAction {

        interface Presenter{
            void getRegisterSmsCode(String mobile);
            void getForgotSmsCode(String mobile);
            void getRegisterEmailCode(String email);
            void getForgotEmailCode(String email);
            void logout(String userName);
            void getCharges();
        }

        interface View extends BaseView {
            void getSmsCodeDone();
            void getEmailCodeDone();
            void getSmsCodeFailDone();
            void logoutDone();
            void getChargesDone(Charges charges);
        }

}
