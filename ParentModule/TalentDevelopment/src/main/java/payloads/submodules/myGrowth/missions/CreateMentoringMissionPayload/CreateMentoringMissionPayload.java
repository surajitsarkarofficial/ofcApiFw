package payloads.submodules.myGrowth.missions.CreateMentoringMissionPayload;

public class CreateMentoringMissionPayload {
    /**
     * @author nadia.silva
     */
    private String title;

    private String description;

    private Subjects[] subjects;

    private RelatedLinks[] relatedLinks;

    private String workingEcosystem;

    private String mentor;

    private String mentee;

    public String getWorkingEcosystem ()
    {
        return workingEcosystem;
    }

    public void setWorkingEcosystem (String workingEcosystem)
    {
        this.workingEcosystem = workingEcosystem;
    }

    public String getMentor ()
    {
        return mentor;
    }

    public void setMentor (String mentor)
    {
        this.mentor = mentor;
    }

    public Subjects[] getSubjects ()
    {
        return subjects;
    }

    public void setSubjects (Subjects[] subjects)
    {
        this.subjects = subjects;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public RelatedLinks[] getRelatedLinks ()
    {
        return relatedLinks;
    }

    public void setRelatedLinks (RelatedLinks[] relatedLinks)
    {
        this.relatedLinks = relatedLinks;
    }

    public String getMentee ()
    {
        return mentee;
    }

    public void setMentee (String mentee)
    {
        this.mentee = mentee;
    }

    /**
     * Simple payload constructor
     * @param subID
     * @param subName
     * @param mentorId
     * @param menteeId
     */
    public CreateMentoringMissionPayload(String subID, String subName, String mentorId, String menteeId, String we){
        this.title = "Testing mentor mission";
        this.description = "Testing description for mentor: "+mentorId;

        RelatedLinks relatedLink = new RelatedLinks();
        relatedLink.setTitle("Acamica");
        relatedLink.setLink("www.acamica.com");
        RelatedLinks[] linkList = new RelatedLinks[1];
        linkList[0] = relatedLink;
        this.relatedLinks = linkList ;

        Subjects subject = new Subjects();
        subject.setId(subID);
        subject.setName(subName);
        Subjects[] subjectList = new Subjects[1];
        subjectList[0] = subject;
        this.subjects = subjectList;

        this.mentor = mentorId;
        this.mentee = menteeId;
        this.workingEcosystem = we;
    }

    /**
     * Create mentoring mission payload with fake subject
     * @param mentorId
     * @param menteeId
     * @param we
     */
    public CreateMentoringMissionPayload(String mentorId, String menteeId, String we){
        this.title = "Testing mentor mission";
        this.description = "Testing description for mentor: "+mentorId;

        RelatedLinks relatedLink = new RelatedLinks();
        relatedLink.setTitle("Acamica");
        relatedLink.setLink("www.acamica.com");
        RelatedLinks[] linkList = new RelatedLinks[1];
        linkList[0] = relatedLink;
        this.relatedLinks = linkList ;

        Subjects subject = new Subjects();
        subject.setId("8");
        subject.setName("fake subject");
        Subjects[] subjectList = new Subjects[1];
        subjectList[0] = subject;
        this.subjects = subjectList;

        this.mentor = mentorId;
        this.mentee = menteeId;
        this.workingEcosystem = we;
    }

}