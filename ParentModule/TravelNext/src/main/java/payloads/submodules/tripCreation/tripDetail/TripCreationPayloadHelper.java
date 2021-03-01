package payloads.submodules.tripCreation.tripDetail;

import database.submodules.tripCreation.TripCreationDBHelper;
import dto.submodules.tripCreation.tripDetail.Trip;
import dto.submodules.tripCreation.tripDetail.TripCreationRequestDTO;
import payloads.TravelNextPayloadHelper;
import properties.TravelNextConstants;
import tests.testhelpers.TravelNextBaseHelper;

/**
 * @author josealberto.gomez
 *
 */
public class TripCreationPayloadHelper extends TravelNextPayloadHelper {
    protected static TripCreationDBHelper tripCreationDBHelper = new TripCreationDBHelper();
    /**
     * This method will create a TripCreationRequestDTO for create a Trip
     *
     * @return TripCreationRequestDTO
     * @throws Exception
     */
    public static TripCreationRequestDTO getTripCreationDTO() throws Exception {
        TripCreationRequestDTO tmpDTO = new TripCreationRequestDTO();

        tmpDTO.setTripName(TravelNextConstants.automationTestLabel + " "
                + TravelNextBaseHelper.nowDateTimeWithFormat(TravelNextConstants.dateTimeLongFormat));
        tmpDTO.setPurposeAndGoals(TravelNextConstants.automationPurposeLabel);
        tmpDTO.setCreatorMail(travelNextDBHelper.getRandomPassengerMail());
        tmpDTO.setTravelReason(tripCreationDBHelper.getRandomTravelReason());

        return tmpDTO;
    }

    /**
     * This method will create a TripCreationRequestDTO for update a Trip
     *
     * @param trip
     * @return TripCreationRequestDTO
     * @throws Exception
     */
    public static TripCreationRequestDTO getTripUpdateDTO(Trip trip) throws Exception {
        TripCreationRequestDTO tmpDTO = new TripCreationRequestDTO();

        tmpDTO.setTripId(trip.getTripId());
        tmpDTO.setTripName(TravelNextConstants.automationTestLabel + " "
                + TravelNextBaseHelper.nowDateTimeWithFormat(TravelNextConstants.dateTimeLongFormat));
        tmpDTO.setPurposeAndGoals(TravelNextConstants.automationPurposeUpdateLabel);
        tmpDTO.setCreatorMail(travelNextDBHelper.getRandomPassengerMail());
        tmpDTO.setTravelReason(tripCreationDBHelper.getRandomTravelReason());
        tmpDTO.setStatus("DRAFT");

        return tmpDTO;
    }
}
