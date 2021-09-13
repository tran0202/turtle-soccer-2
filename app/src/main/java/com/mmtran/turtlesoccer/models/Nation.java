package com.mmtran.turtlesoccer.models;

import com.google.firebase.firestore.PropertyName;

public class Nation {

    private String id;
    private String name;

    @PropertyName("official_name")
    private String officialName;

    @PropertyName("parent_nation_id")
    private String parentNationId;

    @PropertyName("confederation_id")
    private String confederationId;

    @PropertyName("nation_type_id")
    private String nationTypeId;

    @PropertyName("flag_filename")
    String flagFilename;

    private String code;

    @PropertyName("start_date")
    private String startDate;

    @PropertyName("end_date")
    private String endDate;

    public Nation() {}

    public Nation(String id, String name, String officialName, String parentNationId, String confederationId, String nationTypeId, String flagFilename, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.officialName = officialName;
        this.parentNationId = parentNationId;
        this.confederationId = confederationId;
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

    @PropertyName("parent_nation_id")
    public String getParentNationId() {
        return parentNationId;
    }

    @PropertyName("confederation_id")
    public String getConfederationId() {
        return confederationId;
    }

    @PropertyName("nation_type_id")
    public String getNationTypeId() {
        return nationTypeId;
    }

    @PropertyName("flag_filename")
    public String getFlagFilename() {
        return flagFilename;
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

    @PropertyName("parent_nation_id")
    public void setParentNationId(String parentNationId) {
        this.parentNationId = parentNationId;
    }

    @PropertyName("confederation_id")
    public void setConfederationId(String confederationId) {
        this.confederationId = confederationId;
    }

    @PropertyName("nation_type_id")
    public void setNationTypeId(String nationTypeId) {
        this.nationTypeId = nationTypeId;
    }

    @PropertyName("flag_filename")
    public void setFlagFilename(String flagFilename) {
        this.flagFilename = flagFilename;
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
}
