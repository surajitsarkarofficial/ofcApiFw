package dto.submodules.myGrowth.features.capabilities.GET;

import java.util.List;
/**
 * @author nadia.silva
 */
public class Data {
    private Integer id;
    private String name;
    private List<Area> areas = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

}
