package com.bootcamp.portal.mgr.dto;

import java.util.List;

public class TabSpec {
	public final static Integer TAB_ALL_ACCESS = 0;
	private String label;
	private String action;
	private String url;
	private Integer accessedFor = 0; // 0 - for all;
	private boolean active;
	private boolean dropdown;
	private List<TabSpec> subTabs;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public TabSpec(String label, String action, String url, Integer accessedFor) {
		this.label = label;
		this.action = action;
		this.url = url;
		this.accessedFor = accessedFor;
	}

	public TabSpec(String urlFormat, long parentId, Class<?> clazz,
			Integer accessedFor) {
		this(clazz.getSimpleName(), "Tab" + clazz.getSimpleName(), String
				.format(urlFormat, parentId, clazz.getSimpleName()),
				accessedFor);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getAccessedFor() {
		return accessedFor;
	}

	public boolean isDropdown() {
		return dropdown;
	}

	public void setDropdown(boolean dropdown) {
		this.dropdown = dropdown;
	}

	public List<TabSpec> getSubTabs() {
		return subTabs;
	}

	public void setSubTabs(List<TabSpec> childTabs) {
		this.subTabs = childTabs;
	}

}
