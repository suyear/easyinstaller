package com.jianglibo.vaadin.dashboard.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.jianglibo.vaadin.dashboard.annotation.VaadinFormField;
import com.jianglibo.vaadin.dashboard.annotation.VaadinFormField.Ft;
import com.jianglibo.vaadin.dashboard.annotation.VaadinTable;
import com.jianglibo.vaadin.dashboard.annotation.VaadinTableColumn;
import com.vaadin.ui.themes.ValoTheme;

@Entity
@VaadinTable(name = Box.VAADIN_TABLE_NAME, messagePrefix="domain.box.", styleNames={ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_NO_HORIZONTAL_LINES, ValoTheme.TABLE_COMPACT}, selectable=true, fullSize=true)
@Table(name = "box", uniqueConstraints = { @UniqueConstraint(columnNames = "ip") })
public class Box extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String VAADIN_TABLE_NAME = "box";
	
	@NotNull
	@VaadinTableColumn(order = 0, name="ip")
	@VaadinFormField(name="ip", order = 0, caption = "id")
	private String ip;
	
	@VaadinTableColumn(order = 1, name = "name")
	@VaadinFormField(name="name", order = 1, caption = "name")
	private String name;
	
	@VaadinTableColumn(name="osType", order=2)
	@VaadinFormField(name="osType", caption="osType", order = 2, fieldType=Ft.COMBO_BOX, comboKey="ostype")
	private String osType;
	
	@VaadinFormField(order = 3, caption="description", name = "description", fieldType=Ft.TEXT_AREA)
	private String description;
	
	@VaadinFormField(order = 4, name = "keyFileContent", fieldType=Ft.TEXT_AREA)
	@Lob
	private String keyFileContent;
	
	@VaadinFormField(order = 5, name = "keyFilePath")
	private String keyFilePath;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getKeyFileContent() {
		return keyFileContent;
	}

	public void setKeyFileContent(String keyFileContent) {
		this.keyFileContent = keyFileContent;
	}

	public String getKeyFilePath() {
		return keyFilePath;
	}

	public void setKeyFilePath(String keyFilePath) {
		this.keyFilePath = keyFilePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}
	
}
