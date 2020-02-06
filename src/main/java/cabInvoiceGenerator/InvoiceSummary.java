package cabInvoiceGenerator;

public class InvoiceSummary {
    public int numOfRides;
    public double totalFare;

    public InvoiceSummary(int numofRides, double totalFare) {
        this.numOfRides = numofRides;
        this.totalFare = totalFare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceSummary that = (InvoiceSummary) o;
            return numOfRides == that.numOfRides &&
                    Double.compare(that.totalFare, totalFare) == 0;
    }
}
