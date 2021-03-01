package dto.submodules.myGrowth.features.workingEcosystem.getMissions;

public class WorkingEcosystems {
    /**
     * @author nadia.silva
     */
    private String relatedMission;

    private String alberthaIdReference;

    private PositionCapabilitiesDtos[] positionCapabilitiesDtos;

    private String name;

    private String id;

    private PositionCapabilities[] positionCapabilities;

    private Boolean primary;

    public String getRelatedMission ()
    {
        return relatedMission;
    }

    public void setRelatedMission (String relatedMission)
    {
        this.relatedMission = relatedMission;
    }

    public String getAlberthaIdReference ()
    {
        return alberthaIdReference;
    }

    public void setAlberthaIdReference (String alberthaIdReference)
    {
        this.alberthaIdReference = alberthaIdReference;
    }

    public PositionCapabilitiesDtos[] getPositionCapabilitiesDtos ()
    {
        return positionCapabilitiesDtos;
    }

    public void setPositionCapabilitiesDtos (PositionCapabilitiesDtos[] positionCapabilitiesDtos)
    {
        this.positionCapabilitiesDtos = positionCapabilitiesDtos;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public PositionCapabilities[] getPositionCapabilities ()
    {
        return positionCapabilities;
    }

    public void setPositionCapabilities (PositionCapabilities[] positionCapabilities)
    {
        this.positionCapabilities = positionCapabilities;
    }

    public Boolean getPrimary ()
    {
        return primary;
    }

    public void setPrimary (Boolean primary)
    {
        this.primary = primary;
    }

}