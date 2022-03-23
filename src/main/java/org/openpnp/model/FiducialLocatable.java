package org.openpnp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openpnp.util.IdentifiableList;
import org.pmw.tinylog.Logger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class FiducialLocatable extends AbstractModelObject implements PropertyChangeListener {
    
    @Attribute(required = false)
    private String name;
    
    @Element(required = false)
    protected Location dimensions = new Location(LengthUnit.Millimeters);

    @ElementList(required = false)
    protected IdentifiableList<Placement> placements = new IdentifiableList<>();

    protected transient File file;
    
    protected transient boolean dirty;


    public FiducialLocatable() {
    }
    
    public FiducialLocatable(FiducialLocatable fiducialLocatable) {
        this.name = fiducialLocatable.name;
        this.dimensions = fiducialLocatable.dimensions;
        placements = new IdentifiableList<>();
        for (Placement placement : fiducialLocatable.placements) {
            placements.add(new Placement(placement));
        }
        file = fiducialLocatable.file;
        dirty = fiducialLocatable.dirty;
    }
    
    public void setTo(FiducialLocatable fiducialLocatable) {
        this.name = fiducialLocatable.name;
        this.dimensions = fiducialLocatable.dimensions;
        placements = new IdentifiableList<>();
        for (Placement placement : fiducialLocatable.placements) {
            placements.add(new Placement(placement));
        }
        file = fiducialLocatable.file;
        dirty = fiducialLocatable.dirty;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        Object oldValue = this.name;
        this.name = name;
        firePropertyChange("name", oldValue, name);
    }

    public Location getDimensions() {
        return dimensions;
    }

    public void setDimensions(Location location) {
        Location oldValue = this.dimensions;
        this.dimensions = location;
        firePropertyChange("dimensions", oldValue, location);
    }

    public IdentifiableList<Placement> getPlacements() {
//        return Collections.unmodifiableList(placements);
        return new IdentifiableList<>(placements);
    }

    public void addPlacement(Placement placement) {
        Object oldValue = placements;
        placements = new IdentifiableList<>(placements);
        placements.add(placement);
        firePropertyChange("placements", oldValue, placements);
        if (placement != null) {
            placement.addPropertyChangeListener(this);
        }
    }

    public void removePlacement(Placement placement) {
        Object oldValue = placements;
        placements = new IdentifiableList<>(placements);
        placements.remove(placement);
        firePropertyChange("placements", oldValue, placements);
        if (placement != null) {
            placement.removePropertyChangeListener(this);
        }
    }

    public void removeAllPlacements() {
        ArrayList<Placement> oldValue = placements;
        placements = new IdentifiableList<>();
        firePropertyChange("placements", oldValue, placements);
        for (Placement placement : oldValue) {
            placement.removePropertyChangeListener(this);
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        Object oldValue = this.file;
        this.file = file;
        firePropertyChange("file", oldValue, file);
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        boolean oldValue = this.dirty;
        this.dirty = dirty;
        firePropertyChange("dirty", oldValue, dirty);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Logger.trace("PropertyChangeEvent = " + evt);
        if (evt.getSource() != FiducialLocatable.this || evt.getPropertyName() != "dirty") {
            dirty = true;
        }
        
    }

}
