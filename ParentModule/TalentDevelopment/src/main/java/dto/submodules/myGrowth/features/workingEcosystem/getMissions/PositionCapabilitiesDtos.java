package dto.submodules.myGrowth.features.workingEcosystem.getMissions;

public class PositionCapabilitiesDtos{
    /**
     * @author nadia.silva
     */
    private String name;

    private Areas[] areas;

    private String id;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Areas[] getAreas ()
    {
        return areas;
    }

    public void setAreas (Areas[] areas)
    {
        this.areas = areas;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

}