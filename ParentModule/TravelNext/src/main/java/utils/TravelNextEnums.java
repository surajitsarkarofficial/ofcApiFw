package utils;

/**
 * @author josealberto.gomez
 *
 */
public class TravelNextEnums extends GlowEnums {
    public static enum ExecutionSuiteGroups{
        DontDeleteTrip(ExeGroups.DontDeleteTrip),
        SprintOne(ExeGroups.SprintOne),
        SprintTwo(ExeGroups.SprintTwo),
        SprintThree(ExeGroups.SprintThree),
        SprintFour(ExeGroups.SprintFour),
        SprintFive(ExeGroups.SprintFive),
        SprintSix(ExeGroups.SprintSix),
        SprintSeven(ExeGroups.SprintSeven),
        SprintEight(ExeGroups.SprintEight),
        SprintNine(ExeGroups.SprintNine),
        SprintTen(ExeGroups.SprintTen);

        public class ExeGroups{
            public static final String DontDeleteTrip = "DontDeleteTrip";
            public static final String SprintOne = "SprintOne";
            public static final String SprintTwo = "SprintTwo";
            public static final String SprintThree = "SprintThree";
            public static final String SprintFour = "SprintFour";
            public static final String SprintFive = "SprintFive";
            public static final String SprintSix = "SprintSix";
            public static final String SprintSeven = "SprintSeven";
            public static final String SprintEight = "SprintEight";
            public static final String SprintNine = "SprintNine";
            public static final String SprintTen = "SprintTen";
        }

        private final String group;
        private ExecutionSuiteGroups(String group) {
            this.group = group;
        }
        public String getGroup() {
            return group;
        }
    }
}
