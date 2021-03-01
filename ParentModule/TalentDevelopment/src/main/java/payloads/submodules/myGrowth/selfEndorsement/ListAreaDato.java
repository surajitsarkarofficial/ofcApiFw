package payloads.submodules.myGrowth.selfEndorsement;


import java.util.Random;

/**
 * @author nadia.silva
 */
public class ListAreaDato {

        private Integer value;

        private String name;

        private Integer id;

        private String idPositionCapability;

    /**
     * Create an evaluation
     */
    public ListAreaDato(){
            this.id = 17;
            this.idPositionCapability = "1";
            this.name = "NFRs";
            Random r = new Random();
            int low = 1;
            int high = 5;
            int result = r.nextInt(high-low) + low;
            this.value = result;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getIdPositionCapability() {
            return idPositionCapability;
        }

        public void setIdPositionCapability(String idPositionCapability) {
            this.idPositionCapability = idPositionCapability;
        }
}
