package endpoints.submodules.myGrowth.features.missions;

import endpoints.submodules.myGrowth.MyGrowthEndpoints;

public class MissionEndpoints extends MyGrowthEndpoints {

    public static String missionsUrl = "/v1/missions/";
    public static String progressMissionsUrl = "/my-growth/progress/we/%s/glober/%s";
    public static String mentoringMissionUrl = "/v1/mentor/missions";
    public static String updateStatusMision="/v1/missions/mission/glober";
    public static String editMentorinMissionsUrl="/v1/mentor/mission";
    public static String listMenteeSkills="/v1/mentor/skills?workingEcosystemId=%s&globerId=%s";
    public static String missionListEndpoint="/v1/missions/we/%s/glober/%s";


}
