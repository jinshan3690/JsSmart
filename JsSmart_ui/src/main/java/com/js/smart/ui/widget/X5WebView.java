package com.js.smart.ui.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;

import com.js.smart.common.app.BaseCompatActivity;
import com.js.smart.http.cookies.PersistentCookieStore;
import com.js.smart.common.util.ImageUtil;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class X5WebView extends WebView {

    private BaseCompatActivity context;
    private PersistentCookieStore cookieStore;

    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
            return super.shouldInterceptRequest(webView, s);
        }

        @Override
        public void onLoadResource(WebView webView, String s) {
            super.onLoadResource(webView, s);
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
//		context = (BaseCompatActivity) arg0;
//		cookieStore = new PersistentCookieStore(context);
        this.setWebViewClient(client);
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings();
        this.getView().setClickable(true);
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
//		webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSetting.setAllowFileAccess(true);
            webSetting.setAllowUniversalAccessFromFileURLs(true);
            webSetting.setAllowFileAccessFromFileURLs(true);
        }

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    public X5WebView(Context arg0) {
        super(arg0);
    }

    @Override
    public void loadUrl(String url) {
//		CookieSyncManager.createInstance(context);
//		CookieManager cookieManager = CookieManager.getInstance();
//		cookieManager.setAcceptCookie(true);
//		cookieManager.removeSessionCookie();
//		cookieManager.removeExpiredCookie();
//		cookieManager.removeAllCookie();
//		List<Cookie> cookies = cookieStore.getCookies();
//		String local = "";
//		for (int i = 0; i < cookies.size(); i++) {
//			Cookie cookie = cookies.get(i);
//			local += cookie.name() + "=" + cookie.value() +";path="+ cookie.path() +";";
//			if (cookie.name().contains("JSESSIONID"))
//				cookieManager.setCookie(url, cookie.name() + "=" + cookie.value() + ";path="+ cookie.path() +";" );
//		}
//		L.e(url +" LocalCookie= "+ local);
//		L.e(url +" Cookie= "+ cookieManager.getCookie(url));
//
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//			cookieManager.flush();
//		}else {
//			CookieSyncManager.getInstance().sync();
//		}
        super.loadUrl(url);
    }

    /**
     * 图片选择回调
     */
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    private WebChromeClient webChromeClient = new WebChromeClient() {

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            showOptions();
        }

        // For Android > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            showOptions();
        }

        // For Android > 5.0支持多张上传
        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> uploadMsg,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = uploadMsg;

            showOptions();
            return true;
        }

    };

    private class ReOnCancelListener implements DialogInterface.OnCancelListener {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
                ImageUtil.imageUri = null;
            }
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
                ImageUtil.imageUri = null;
            }
        }
    }

    /**
     * 包含拍照和相册选择
     */
    public void showOptions() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setOnCancelListener(new ReOnCancelListener());
        alertDialog.setTitle("选择");
        alertDialog.setItems(new String[]{"相机", "相册"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            ImageUtil.img_PhotoFromCapture(context);
                        } else {
                            ImageUtil.img_PhotoFromDCIM(context);
                        }
                    }
                });
        alertDialog.show();
    }

    /**
     * 回调到网页
     */
    public void onActivityResult(AppCompatActivity context, int requestCode, int resultCode, Intent data) throws IOException {
        ImageUtil.onActivityResult(context, requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            ImageUtil.imageUri = null;
        }

        if (mUploadCallbackAboveL != null) {
            Uri[] uris = new Uri[]{ImageUtil.imageUri};
            mUploadCallbackAboveL.onReceiveValue(uris);
            mUploadCallbackAboveL = null;
        } else if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(ImageUtil.imageUri);
            mUploadMessage = null;
        } else {
//			Toast.makeText(context, "无法获取数据", Toast.LENGTH_LONG).show();
        }
    }

    public void clear() {
        this.stopLoading();
        this.clearHistory();
        this.getSettings().setJavaScriptEnabled(false);
        this.clearCache(true);
        this.loadUrl("about:blank");
        this.pauseTimers();
        this.clearView();
        this.removeAllViews();
        this.destroy();
    }

}
