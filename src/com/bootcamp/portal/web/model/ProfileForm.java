package com.bootcamp.portal.web.model;

import org.springframework.web.multipart.MultipartFile;

import com.bootcamp.portal.domain.User;
import com.bootcamp.portal.mgr.dto.TabControlSpec;

public class ProfileForm {
	private String redirectUrl;
	private String applicationName;
	private User object;
	private TabControlSpec tabController = new TabControlSpec();
	private String activeTab;
	private boolean showOtherTabs = true;
	private MultipartFile file;
	private String htmlImage;
	private String location = "loc1";
	private String fileName = "fn";

	public ProfileForm() {
	}

	public ProfileForm(User user) {
		this();
		this.object = user;
		this.redirectUrl = "/user/list";
		createTabController();
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public User getObject() {
		return object;
	}

	public void setObject(User object) {
		this.object = object;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	private void createTabController() {
		tabController.setGeneralTabName("Data");
	}

	public TabControlSpec getTabController() {
		return tabController;
	}

	public void setTabController(TabControlSpec tabController) {
		this.tabController = tabController;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public boolean isShowOtherTabs() {
		return showOtherTabs;
	}

	public void setShowOtherTabs(boolean showOtherTabs) {
		this.showOtherTabs = showOtherTabs;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getHtmlImage() {
		return htmlImage;
	}

	public void setHtmlImage(String htmlImage) {
		this.htmlImage = htmlImage;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
