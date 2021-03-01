package dto.submodules.myGrowth.features.workingEcosystem.getMissions;

public class Subjects{
    /**
     * @author nadia.silva
     */
    private Capabilities[] capabilities;

    private String blocked;

    private String name;

    private String description;

    private String id;

    private String value;

    public Capabilities[] getCapabilities ()
    {
        return capabilities;
    }

    public void setCapabilities (Capabilities[] capabilities)
    {
        this.capabilities = capabilities;
    }

    public String getBlocked ()
    {
        return blocked;
    }

    public void setBlocked (String blocked)
    {
        this.blocked = blocked;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

}