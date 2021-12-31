package com.mmtran.turtlesoccer.models;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;

public class Nation implements Serializable {

    private String id;
    private String name;

    @PropertyName("official_name")
    private String officialName;

    @PropertyName("parent_nation")
    private Nation parentNation;

    @PropertyName("confederation")
    private Confederation confederation;

    @PropertyName("nation_type_id")
    private String nationTypeId;

    @PropertyName("flag_filename")
    String flagFilename;

    @PropertyName("flag_max_width")
    String flagMaxWidth;

    private String code;

    @PropertyName("start_date")
    private String startDate;

    @PropertyName("end_date")
    private String endDate;

    @PropertyName("time_stamp")
    private String timeStamp;

    public Nation() {}

    public Nation(String id, String name, String officialName, String nationTypeId, String flagFilename, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.officialName = officialName;
        this.nationTypeId = nationTypeId;
        this.flagFilename = flagFilename;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @PropertyName("official_name")
    public String getOfficialName() {
        return officialName;
    }

    @PropertyName("parent_nation")
    public Nation getParentNation() { return parentNation; }

    @PropertyName("confederation")
    public Confederation getConfederation() {
        return confederation;
    }

    @PropertyName("nation_type_id")
    public String getNationTypeId() {
        return nationTypeId;
    }

    @PropertyName("flag_filename")
    public String getFlagFilename() {
        return flagFilename;
    }

    @PropertyName("flag_max_width")
    public String getFlagMaxWidth() {
        return flagMaxWidth;
    }

    public String getCode() {
        return code;
    }

    @PropertyName("start_date")
    public String getStartDate() {
        return startDate;
    }

    @PropertyName("end_date")
    public String getEndDate() {
        return endDate;
    }

    @PropertyName("time_stamp")
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("official_name")
    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    @PropertyName("parent_nation")
    public void setParentNation(Nation parentNation) {
        this.parentNation = parentNation;
    }

    @PropertyName("confederation")
    public void setConfederation(Confederation confederation) { this.confederation = confederation; }

    @PropertyName("nation_type_id")
    public void setNationTypeId(String nationTypeId) {
        this.nationTypeId = nationTypeId;
    }

    @PropertyName("flag_filename")
    public void setFlagFilename(String flagFilename) {
        this.flagFilename = flagFilename;
    }

    @PropertyName("flag_max_width")
    public void setFlagMaxWidth(String flagMaxWidth) {
        this.flagMaxWidth = flagMaxWidth;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @PropertyName("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @PropertyName("end_date")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @PropertyName("time_stamp")
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
