package dto.submodules.myGrowth.features.progress.GET;

import java.util.List;

/**
 * @author nadia.silva
 */
public class Data {

    private Boolean hasNextLevel;

    private List<Item> items = null;

    private String level;

    private String nextLevel;

    private String parent;

    public Boolean getHasNextLevel() {
        return hasNextLevel;
    }

    public void setHasNextLevel(Boolean hasNextLevel) {
        this.hasNextLevel = hasNextLevel;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

}
