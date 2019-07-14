package com.js.smart.common.init.strategy;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.js.smart.common.R;
import com.js.smart.common.util.SystemUtil;
import com.js.smart.common.util.ThreadPool;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class InitStrategy {

    private String TAG = "InitStrategy";

    private Context context;
    private Retrofit retrofit;

    private String currentProcessName = R.class.getPackage().getName();

    public InitStrategy(Context context) {
        this.context = context;
    }

    /**
     * 存放适用与当前进程的“初始化策略列表”
     */
    private ArrayList<StrategyBean> currencyProcessInitStrategy = null;

    public InitStrategy(Context context, Retrofit retrofit) {
        this.context = context;
        this.retrofit = retrofit;
    }

    /**
     * 加载策略配置文件
     */
    private void load(int initStrategyResId) throws JDOMException, IOException {
        currencyProcessInitStrategy = new ArrayList<>();
        InputStream in = context.getResources().openRawResource(initStrategyResId);
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(in);//读入指定文件
        Element root = doc.getRootElement();//获得根节点
        List<Element> list = root.getChildren();//将根节点下的所有子节点放入List中
        currentProcessName = SystemUtil.currentProcessName(context);
        for (int i = 0; i < list.size(); i++) {
            Element strategyItem = list.get(i);//取得节点实例
            readStrategy(strategyItem);
        }
    }

    /**
     * 读取策略列表
     */
    private void readStrategy(Element item) {
        StrategyBean strategyBean = new StrategyBean();
        String processName = item.getAttributeValue("process");
        String strategyName = item.getAttributeValue("name");
        strategyBean.setProcessName(processName);
        if (TextUtils.isEmpty(processName)) {
            if (!context.getPackageName().equals(currentProcessName))
                return;
        } else {
            if (!processName.equals(currentProcessName))
                return;
        }
        Log.d(TAG, "准备执行策略:" + strategyName + ";当前进程:" + currentProcessName);
        List<Element> actionsEList = item.getChildren("actions");
        if (actionsEList != null && actionsEList.size() > 0) {
            Element actionsE = actionsEList.get(0);
            List<Element> actionEList = actionsE.getChildren("action");
            if (actionEList != null) {
                ArrayList<StrategyActionBean> actionBeans = new ArrayList<StrategyActionBean>();
                for (Element actionE : actionEList) {
                    StrategyActionBean strategyActionBean = new StrategyActionBean();
                    String name = actionE.getAttributeValue("name");
                    String asyncStr = actionE.getAttributeValue("async");
                    strategyActionBean.setName(name);
                    Log.d(TAG, "加载策略:" + name.substring(name.lastIndexOf(".")+1,name.length()) + ";asyncStr:" + asyncStr);
                    try {
                        Boolean async = (asyncStr != null) ? Boolean.parseBoolean(asyncStr) : false;
                        strategyActionBean.setAsync(async);
                    } catch (Exception e) {
                        strategyActionBean.setAsync(false);
                    }
                    actionBeans.add(strategyActionBean);
                }
                strategyBean.setActions(actionBeans);
            }
        }
        currencyProcessInitStrategy.add(strategyBean);
    }

    /**
     * 根据配置，依次执行初始化
     */
    public void execute(int initStrategyResId) {
        if (currencyProcessInitStrategy == null) {
            try {
                load(initStrategyResId);
            } catch (Exception e) {
            }
        }
        if (currencyProcessInitStrategy != null) {
            // 循环策略列表，以获取所有策略对象并执行他们的init方法。
            for (StrategyBean strategyBean : currencyProcessInitStrategy) {
                ArrayList<StrategyActionBean> strategyActionBeans = strategyBean.getActions();
                // 开始注入策略对象，并执行init方法
                for (StrategyActionBean strategyActionBean : strategyActionBeans) {
                    try {
                        // 查找对象，如果是策略对象 判断如果是async就异步执行
                        Class initStrategyClass = Class.forName(strategyActionBean.getName());
                        final InitTask action = (InitTask) initStrategyClass.newInstance();
                        if (strategyActionBean.getAsync()) {
                            ThreadPool.threadPool(new Runnable() {
                                @Override
                                public void run() {
                                    action.init(context, retrofit);
                                }
                            });
                        } else {
                            action.init(context, retrofit);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}