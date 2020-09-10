package com.ntnt.dut.crawlerapi.binders;

import com.ntnt.dut.crawlerapi.enums.NotiType;

import java.beans.PropertyEditorSupport;

public class NotiTypeEditor extends PropertyEditorSupport {
    public void setAsText(String text) {
        try {
            setValue(NotiType.valueOf(text.toUpperCase()));
        } catch (Exception ex) {
            setValue(null);
        }
    }
}
