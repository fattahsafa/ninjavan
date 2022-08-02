package co.ninja.qa.api.beans;

public class Timeslot {
    private String start_time;
    private String end_time;
    private String timezone;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Timeslot(String start_time, String end_time, String timezone) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.timezone = timezone;
    }
}
