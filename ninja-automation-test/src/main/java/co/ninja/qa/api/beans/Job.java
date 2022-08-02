package co.ninja.qa.api.beans;

public class Job {
    private String pickup_date;
    private Contact pickup_address;
    private Timeslot pickup_timeslot;
    private Timeslot delivery_timeslot;

    public Job(String pickup_date, Contact pickup_address, Timeslot pickup_timeslot, Timeslot delivery_timeslot) {
        this.pickup_date = pickup_date;
        this.pickup_address = pickup_address;
        this.pickup_timeslot = pickup_timeslot;
        this.delivery_timeslot = delivery_timeslot;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public Contact getPickup_address() {
        return pickup_address;
    }

    public void setPickup_address(Contact pickup_address) {
        this.pickup_address = pickup_address;
    }

    public Timeslot getPickup_timeslot() {
        return pickup_timeslot;
    }

    public void setPickup_timeslot(Timeslot pickup_timeslot) {
        this.pickup_timeslot = pickup_timeslot;
    }

    public Timeslot getDelivery_timeslot() {
        return delivery_timeslot;
    }

    public void setDelivery_timeslot(Timeslot delivery_timeslot) {
        this.delivery_timeslot = delivery_timeslot;
    }

}
