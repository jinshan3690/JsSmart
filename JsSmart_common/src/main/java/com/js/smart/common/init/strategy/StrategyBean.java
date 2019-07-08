package com.js.smart.common.init.strategy;

import java.util.ArrayList;

public class StrategyBean {
	private int id;
	private String processName;
	private ArrayList<StrategyActionBean> actions;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public ArrayList<StrategyActionBean> getActions() {
		return actions;
	}
	public void setActions(ArrayList<StrategyActionBean> actions) {
		this.actions = actions;
	}
	
}
