package model;

import java.util.List;

/**
 *
 * @author HO_THI_THOM
 */
public class AllPointId {
    String id;
    
    List<AllPoint> allPoint;

    public AllPointId(String id, List<AllPoint> allPoint) {
        this.id = id;
        this.allPoint = allPoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AllPoint> getAllPoint() {
        return allPoint;
    }

    public void setAllPoint(List<AllPoint> allPoint) {
        this.allPoint = allPoint;
    }

    public AllPointId() {
    }
    
}
