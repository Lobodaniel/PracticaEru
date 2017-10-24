package org.assemblits.eru.gui.dynamo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.assemblits.eru.scene.control.Alarm;

/**
 * Created by mtrujillo on 8/26/17.
 */
public class EruAlarm extends Alarm implements ValuableDynamo {

    private StringProperty currentValueTagName;

    public EruAlarm() {
        super();
        this.currentValueTagName = new SimpleStringProperty(this, "currentValueTagName", "");
    }

    @Override
    public void setCurrentTagValue(String value) {
        this.setCurrentValue(Boolean.parseBoolean(value));
    }

    @Override
    public String getCurrentTagValue() {
        return String.valueOf(getCurrentValue());
    }

    public String getCurrentValueTagName() {
        return currentValueTagName.get();
    }
    public StringProperty currentValueTagNameProperty() {
        return currentValueTagName;
    }
    public void setCurrentValueTagName(String currentValueTagName) {
        this.currentValueTagName.set(currentValueTagName);
    }

    @Override public String getUserAgentStylesheet() {
        return Alarm.class.getResource("alarm.css").toExternalForm();
    }
}
