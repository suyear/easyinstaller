package com.jianglibo.vaadin.dashboard.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import com.google.gwt.thirdparty.guava.common.base.Strings;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.jianglibo.vaadin.dashboard.annotation.FormFields;
import com.jianglibo.vaadin.dashboard.annotation.VaadinFormField;
import com.jianglibo.vaadin.dashboard.annotation.VaadinTable;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@Component
public class FormFieldsFactory {
	
	@Autowired
	private ComboBoxFieldFactory comboBoxFieldFactory;
	
	@Autowired
	private MessageSource messageSource;
	
	public List<PropertyIdAndField> buildFields(VaadinTable vt, FormFields ffs) {
		List<PropertyIdAndField> fields = Lists.newArrayList();
		
        for(VaadinFormField vf : ffs.getVfs().values()) {
        	switch (vf.fieldType()) {
			case COMBO_BOX:
				ComboBox cb = comboBoxFieldFactory.createCombo(vt, vf);
				fields.add(new PropertyIdAndField(vf.name(), cb));
				break;
			
			default:
				String caption = vf.caption();
				if (Strings.isNullOrEmpty(caption)) {
					caption = vf.name();
				}
				try {
					caption = messageSource.getMessage(vt.messagePrefix() + "field." + caption, null, UI.getCurrent().getLocale());
				} catch (NoSuchMessageException e) {
				}
				TextField tf = new TextField(caption);
				fields.add(new PropertyIdAndField(vf.name(), tf));
				break;
			}
        }
        return fields;
	}
	
	public static class PropertyIdAndField {
		private String propertyId;
		private Field<?> field;
		
		public PropertyIdAndField(String propertyId, Field<?> field) {
			super();
			this.propertyId = propertyId;
			this.field = field;
		}

		public String getPropertyId() {
			return propertyId;
		}

		public void setPropertyId(String propertyId) {
			this.propertyId = propertyId;
		}

		public Field<?> getField() {
			return field;
		}

		public void setField(Field<?> field) {
			this.field = field;
		}
	}
}
