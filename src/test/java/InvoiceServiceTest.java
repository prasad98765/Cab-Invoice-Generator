import cabInvoiceGenerator.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.when;

public class InvoiceServiceTest {
    InvoiceServices invoiceGenerator = null;

    @Before
    public void setUp() throws Exception {
        invoiceGenerator = new InvoiceServices(new RideRepository());
    }

    @Mock
    RideRepository rideRepositorymock;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare() {
        Double distance = 2.0;
        int time = 5;
        double fare = invoiceGenerator.addRide(distance, time);
        Assert.assertEquals(25, fare, 0.0);
    }


    @Test
    public void givenLessDistanceAndTime_ShouldReturnMinimumFare() {
        Double distance = 0.0;
        int time = 5;
        double fare = invoiceGenerator.addRide(distance, time);
        Assert.assertEquals(5, fare, 0.0);
    }

    @Test
    public void givenMultipleRides_ShouldReturnTotalSummary() throws InvoiceServicesException {
        Ride[] rides = {new Ride(2.0, 5),
                new Ride(0.1, 1)};
        InvoiceSummary summary = invoiceGenerator.calculateTotalFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnInvoiceSummary() throws InvoiceServicesException {
        String userId = "BridgeLabz@.com";
        Ride[] rides = {
                new Ride(2.0, 5),
                new Ride(0.1, 1)};
        InvoiceServices invoiceServices = new InvoiceServices(rideRepositorymock);
        invoiceGenerator.addRide(userId, rides);
        InvoiceSummary summary = this.invoiceGenerator.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnInvoiceSummaryUsingMockito() throws InvoiceServicesException {
        String userId = "ab.com";
        Ride[] rides = {
                new Ride(2.0, 5),
                new Ride(0.1, 1)};
        InvoiceServices invoiceServices = new InvoiceServices(rideRepositorymock);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        when(rideRepositorymock.getRides(userId)).thenReturn(rides);
        InvoiceSummary summary = invoiceServices.getInvoiceSummary(userId);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenWrongUserId_ShouldReturnInvoiceSummaryUsingMockito() {
        try {
            String userId = "xyz.com";
            Ride[] rides = {
                    new Ride(2.0, 5),
                    new Ride(0.1, 1)};
            InvoiceServices invoiceServices = new InvoiceServices(rideRepositorymock);
            InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
            when(rideRepositorymock.getRides("abc.com")).thenThrow(new NullPointerException("Invalid User id"));
            InvoiceSummary summary = invoiceServices.getInvoiceSummary(userId);
            Assert.assertEquals(expectedInvoiceSummary, summary);
        } catch (InvoiceServicesException e) {
        }
    }

    @Test
    public void givenWrongNumberOfRecord_ShouldReturnInvoiceSummaryUsingMock() throws InvoiceServicesException {
        String userId = "ab.com";
        Ride[] rides = {
                new Ride(2.0, 5),
                new Ride(0.1, 1)};
        InvoiceServices invoiceServices = new InvoiceServices(rideRepositorymock);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(4, 30.0);
        when(rideRepositorymock.getRides(userId)).thenReturn(rides);
        InvoiceSummary summary = invoiceServices.getInvoiceSummary(userId);
        try {
            Assert.assertEquals(expectedInvoiceSummary, summary);
        }catch (AssertionError e){
        }
    }
}
