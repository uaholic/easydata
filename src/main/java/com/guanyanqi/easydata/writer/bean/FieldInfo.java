package com.guanyanqi.easydata.writer.bean;

import java.lang.reflect.Field;

/**
 * @author 关岩奇
 * @email admin@guanyanqi.com
 */
public class FieldInfo {

    private Field field;

    private int exportOrder;

    public FieldInfo(Field field, int exportOrder) {
        this.field = field;
        this.exportOrder = exportOrder;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getExportOrder() {
        return exportOrder;
    }

    public void setExportOrder(int exportOrder) {
        this.exportOrder = exportOrder;
    }
}
