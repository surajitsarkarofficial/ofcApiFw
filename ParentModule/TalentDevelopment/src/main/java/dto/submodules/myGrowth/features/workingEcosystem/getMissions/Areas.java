package dto.submodules.myGrowth.features.workingEcosystem.getMissions;

public class Areas{
    /**
     * @author nadia.silva
     */
    private Subjects[] subjects;

    private String name;

    private String id;

    private String endorsed;

    public Subjects[] getSubjects ()
    {
        return subjects;
    }

    public void setSubjects (Subjects[] subjects)
    {
        this.subjects = subjects;
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

    public String getEndorsed ()
    {
        return endorsed;
    }

    public void setEndorsed (String endorsed)
    {
        this.endorsed = endorsed;
    }

}