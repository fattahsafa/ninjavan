package co.ninja.qa.api;

import co.ninja.qa.api.beans.Contact;
import co.ninja.qa.api.beans.Job;
import org.json.JSONPropertyName;

public class Order {
    private String service_type;
    private String service_level;
    private Contact from;
    private Contact to;

    public Order() {
    }

    private Job parcel_job;

    public Order(String service_type, String service_level, Contact from, Contact to, Job parcel_job) {
        this.service_type = service_type;
        this.service_level = service_level;
        this.from = from;
        this.to = to;
        this.parcel_job = parcel_job;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getService_level() {
        return service_level;
    }

    public void setService_level(String service_level) {
        this.service_level = service_level;
    }

    public Contact getFrom() {
        return from;
    }

    public void setFrom(Contact from) {
        this.from = from;
    }

    public Contact getTo() {
        return to;
    }

    public void setTo(Contact to) {
        this.to = to;
    }

    public Job getParcel_job() {
        return parcel_job;
    }

    public void setParcel_job(Job parcel_job) {
        this.parcel_job = parcel_job;
    }
    }
