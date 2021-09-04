package com.mmtran.turtlesoccer.models;

public class Nation {

    private String id;
    private String name;
    private String officialName;
    private String parentNationId;
    private String confederationId;
    private String nationTypeId;
    private String flagFilename;
    private String code;
    private String startDate;
    private String endDate;

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

    public String getOfficialName() {
        return officialName;
    }

    public String getParentNationId() {
        return parentNationId;
    }

    public String getConfederationId() {
        return confederationId;
    }

    public String getNationTypeId() {
        return nationTypeId;
    }

    public String getFlagFilename() {
        return flagFilename;
    }

    public String getCode() {
        return code;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public void setParentNationId(String parentNationId) {
        this.parentNationId = parentNationId;
    }

    public void setConfederationId(String confederationId) {
        this.confederationId = confederationId;
    }

    public void setNationTypeId(String nationTypeId) {
        this.nationTypeId = nationTypeId;
    }

    public void setFlagFilename(String flagFilename) {
        this.flagFilename = flagFilename;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
