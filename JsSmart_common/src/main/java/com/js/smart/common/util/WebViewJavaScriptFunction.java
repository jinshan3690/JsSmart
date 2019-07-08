package com.js.smart.common.util;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.js.smart.common.app.BaseCompatActivity;


public class WebViewJavaScriptFunction {

	private BaseCompatActivity context;

	public WebViewJavaScriptFunction(BaseCompatActivity context) {
		this.context = context;
	}

	@JavascriptInterface
	public void toast(String name) {
		Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
	}

	@JavascriptInterface
	public void finish() {
		context.finish();
	}

}
