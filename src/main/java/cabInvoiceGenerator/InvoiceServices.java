package cabInvoiceGenerator;

public class InvoiceServices {
    private static final double MINIMUM_COST_PER_TIME_KM = 10.0;
    private static final int COST_PER_TIME = 1;
    private static final double MINIMUM_FARE = 5;
    private  RideRepository rideRepository;

    public InvoiceServices(RideRepository rideRepositorymock) {
        this.rideRepository = rideRepositorymock;
    }

    public double addRide(Double distance, int time) {

        double totalFare = distance * MINIMUM_COST_PER_TIME_KM + time * COST_PER_TIME;
        if (totalFare > MINIMUM_FARE)
            return totalFare;
        return MINIMUM_FARE;
    }

    public InvoiceSummary calculateTotalFare(Ride[] rides) throws InvoiceServicesException {
        double totalfare = 0;
        for (Ride ride : rides) {
            totalfare += this.addRide(ride.distance, ride.time);
        }
        return new InvoiceSummary(rides.length, totalfare);
    }

    public void addRide(String userId, Ride[] rides) {
        rideRepository.addRide(userId,rides);
    }

    public InvoiceSummary getInvoiceSummary(String userId) throws InvoiceServicesException {
        try {
            return this.calculateTotalFare(rideRepository.getRides(userId));
        }catch (NullPointerException e){
            throw new InvoiceServicesException(e.getMessage(),InvoiceServicesException.ExceptionType.INVALID_USER_ID);
        }
    }
}
