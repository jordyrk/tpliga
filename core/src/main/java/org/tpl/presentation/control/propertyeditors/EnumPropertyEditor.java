package org.tpl.presentation.control.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.EnumSet;

/**
 *
 */
public class EnumPropertyEditor<E extends Enum<E>> extends PropertyEditorSupport {
    private Class<E> en;
    private EnumSet<E> set;

    public EnumPropertyEditor(Class<E> en) {
        this.en = en;
        set = EnumSet.allOf(en);
    }

    public String getAsText() {
        if(getValue() != null){
            return getValue().toString();
        }
        return "";
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (text != null && text.length() > 0) {
            Enum<E> e = Enum.valueOf(en, text);
            setValue(e);
        } else {
            setValue(null);
        }
    }
}
