package com.bootcamp.portal.mgr.dto;

import java.util.ArrayList;
import java.util.List;

//TODO improve using TabControlSpec
public class TabControlSpec {
	private List<TabSpec> tabs = new ArrayList<>();
	private boolean generalTab = true;
	private String generalTabName = "General";
	private String generalTabId = "general";

	public List<TabSpec> getTabs() {
		return tabs;
	}

	public TabSpec byAction(String name) {
		for (TabSpec t : tabs) {
			if (t.getAction().equalsIgnoreCase(name)) {
				return t;
			}
		}
		return null;
	}

	public TabSpec getActive() {
		for (TabSpec t : tabs) {
			if (t.isActive()) {
				return t;
			}
		}
		return null;
	}

	public int getActiveTabNumber() {
		int counter = 0;
		for (TabSpec t : tabs) {
			if (t.isActive()) {
				return generalTab ? counter + 1 : counter;
			}
			counter++;
		}
		return 0;
	}

	public List<TabSpec> getAllTabs() {
		List<TabSpec> result = new ArrayList<>();
		for (TabSpec spec : tabs) {
			if (!spec.isDropdown()) {
				result.add(spec);
			} else {
				for (TabSpec child : spec.getSubTabs()) {
					result.add(child);
				}
			}
		}
		return result;
	}

	public void setTabs(List<TabSpec> tabs) {
		this.tabs = tabs;
	}

	public boolean isGeneralTab() {
		return generalTab;
	}

	public void setGeneralTab(boolean generalTab) {
		this.generalTab = generalTab;
	}

	public void addTab(TabSpec tab) {
		this.tabs.add(tab);
	}

	public void addTab(String label, String action, String url,
			Integer accessedFor) {
		addTab(new TabSpec(label, action, url, accessedFor));
	}

	public String getGeneralTabName() {
		return generalTabName;
	}

	public void setGeneralTabName(String generalTabName) {
		this.generalTabName = generalTabName;
	}

	public String getGeneralTabId() {
		return generalTabId;
	}

	public void setGeneralTabId(String generalTabId) {
		this.generalTabId = generalTabId;
	}
}
