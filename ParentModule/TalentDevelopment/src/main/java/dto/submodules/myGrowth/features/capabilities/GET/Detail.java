package dto.submodules.myGrowth.features.capabilities.GET;

import java.util.List;
/**
 * @author nadia.silva
 */
public class Detail {
    private Integer workingEcosystemId;
   
    private String workigEcosystemName;
  
    private List<Data> data = null;

    public Integer getWorkingEcosystemId() {
        return workingEcosystemId;
    }

    public void setWorkingEcosystemId(Integer workingEcosystemId) {
        this.workingEcosystemId = workingEcosystemId;
    }

    public String getWorkigEcosystemName() {
        return workigEcosystemName;
    }

    public void setWorkigEcosystemName(String workigEcosystemName) {
        this.workigEcosystemName = workigEcosystemName;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
