package com.eru.gui.dynamo;

import com.eru.scene.control.LevelBar;
import com.eru.util.TagLinksManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by mtrujillo on 8/26/17.
 */
public class EruLevelBar extends LevelBar{
    /**
     * The map to linkToConnections {@code EruDisplay} and {@code Tags}. This map is useful for
     * finding a specific EruDisplay within the scene graph and get the setted tag. While the id of a Node
     * should be unique within the scene graph, this uniqueness is supported by the {@code ComponentsIdsGenerator}.
     */
    private StringProperty currentValueTagID;
    private StringProperty currentTitleTagID;

    public EruLevelBar() {
        super();
        this.currentValueTagID = new SimpleStringProperty(this, "currentValueTagID", "");
        this.currentTitleTagID = new SimpleStringProperty(this, "currentTitleTagID", "");
    }

    public String getCurrentValueTagID() {
        return currentValueTagID.get();
    }
    public StringProperty currentValueTagIDProperty() {
        return currentValueTagID;
    }
    public void setCurrentValueTagID(String currentValueTagID) {
        this.currentValueTagID.set(currentValueTagID);
        TagLinksManager.DYNAMO_ID_VS_TAG_ID.put(getId(), currentValueTagID);
    }

    public String getCurrentTitleTagID() {
        return currentTitleTagID.get();
    }
    public StringProperty currentTitleTagIDProperty() {
        return currentTitleTagID;
    }
    public void setCurrentTitleTagID(String currentTitleTagID) {
        this.currentTitleTagID.set(currentTitleTagID);
    }
}
