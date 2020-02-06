package cabInvoiceGenerator;

import java.io.Reader;
import java.util.*;

public class RideRepository {
    Map<String, ArrayList> UserRides = null;

    public void addRide(String userId, Ride[] rides) {
        ArrayList<Ride> rideArrayList = this.UserRides.get(userId);
        if(rideArrayList == null) {
            this.UserRides.put(userId, new ArrayList<>(Arrays.asList(rides)));
        }
    }

    public RideRepository() {
        this.UserRides=new HashMap<>();
    }

    public Ride[] getRides(String userid) throws InvoiceServicesException {
        try {
            return (Ride[]) this.UserRides.get(userid).toArray(new Ride[0]);
        }catch (NullPointerException e){
            throw new InvoiceServicesException(e.getMessage(),InvoiceServicesException.ExceptionType.INVALID_USER_ID);
        }
    }
}
