/* SPDX-License-Identifier: Apache 2.0 */
/* Copyright Contributors to the ODPi Egeria project. */

package org.odpi.openmetadata.viewservices.dino.api.properties;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.odpi.openmetadata.adminservices.configuration.properties.ResourceEndpointConfig;
import org.odpi.openmetadata.adminservices.rest.ServerTypeClassificationSummary;
import org.odpi.openmetadata.platformservices.properties.ServerStatus;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;

@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ServerOverview {

    private static final long    serialVersionUID = 1L;
    
    private String                          serverInstanceName;
    private String                          serverName;
    private String                          platformRootURL;    // e.g. "https://localhost:9443"
    private String                          description;
    private String                          serverOrigin;
    private ServerTypeClassificationSummary serverClassification;
    private boolean                         isActive;
    private Map<String,ServerCohortDetails> cohortDetails;
    private ServerStatus serverStatus;
    private List<String>                    serverServicesList;



    /**
     * Default Constructor sets the properties to nulls
     */
    public ServerOverview()
    {
        /*
         * Nothing to do.
         */
    }


    public ServerOverview(String                          serverInstanceName,
                          String                          serverName,
                          String                          description,
                          String                          platformRootURL,
                          String                          serverOrigin,
                          ServerTypeClassificationSummary serverClassification,
                          boolean                         isActive,
                          Map<String,ServerCohortDetails> cohortDetails,
                          ServerStatus                    serverStatus,
                          List<String>                    serverServicesList) {

        this.serverInstanceName          = serverInstanceName;
        this.serverName                  = serverName;
        this.description                 = description;
        this.platformRootURL             = platformRootURL;
        this.serverOrigin                = serverOrigin;
        this.serverClassification        = serverClassification;
        this.isActive                    = isActive;
        this.cohortDetails               = cohortDetails;
        this.serverStatus                = serverStatus;
        this.serverServicesList          = serverServicesList;
    }


    /*
     * Config constructor - create a skeletal PlatformOverview from a ResourceEndpointConfig
     */
    public ServerOverview(ResourceEndpointConfig cfg) {

        this.serverInstanceName  = cfg.getServerInstanceName();
        this.serverName          = cfg.getServerName();
        this.description         = cfg.getDescription();
        this.platformRootURL     = cfg.getPlatformRootURL();
    }


    public String getServerInstanceName() {
        return serverInstanceName;
    }

    public void setServerInstanceName(String serverInstanceName) {
        this.serverInstanceName = serverInstanceName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlatformRootURL() {
        return platformRootURL;
    }

    public void setPlatformRootURL(String platformRootURL) {
        this.platformRootURL = platformRootURL;
    }

    public String getServerOrigin() {
        return serverOrigin;
    }

    public void setServerOrigin(String serverOrigin) {
        this.serverOrigin = serverOrigin;
    }

    public ServerTypeClassificationSummary getServerClassification() {
        return serverClassification;
    }

    public void setServerClassification(ServerTypeClassificationSummary serverClassification) {
        this.serverClassification = serverClassification;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Map<String, ServerCohortDetails> getCohortDetails() {
        return cohortDetails;
    }

    public void setCohortDetails(Map<String, ServerCohortDetails> cohortDetails) { this.cohortDetails = cohortDetails; }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
    }

    public List<String> getServerServicesList() {
        return serverServicesList;
    }

    public void setServerServicesList(List<String> serverServicesList) {
        this.serverServicesList = serverServicesList;
    }

    /**
     * Standard toString method.
     *
     * @return JSON style description of variables.
     */
    @Override
    public String toString()
    {
        return "ServerOverview{" +
                ", description='" + description + '\'' +
                ", platformRootURL=" + platformRootURL +'\'' +
                ", serverName=" + serverName +'\'' +
                ", serverInstanceName=" + serverInstanceName +'\'' +
                ", serverOrigin=" + serverOrigin +'\'' +
                ", serverClassification=" + serverClassification +'\'' +
                ", isActive=" + isActive +'\'' +
                ", cohortDetails=" + cohortDetails +'\'' +
                ", serverStatus=" + serverStatus +'\'' +
                ", serverServicesList=" + serverServicesList +'\'' +
                '}';
    }


    /**
     * Validate that an object is equal depending on their stored values.
     *
     * @param objectToCompare object
     * @return boolean result
     */
    @Override
    public boolean equals(Object objectToCompare)
    {
        if (this == objectToCompare)
        {
            return true;
        }
        if (objectToCompare == null || getClass() != objectToCompare.getClass())
        {
            return false;
        }
        ServerOverview that = (ServerOverview) objectToCompare;
        return Objects.equals(getServerInstanceName(), that.getServerInstanceName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getPlatformRootURL(), that.getPlatformRootURL()) &&
                Objects.equals(getServerName(), that.getServerName()) &&
                Objects.equals(getServerOrigin(), that.getServerOrigin()) &&
                Objects.equals(getIsActive(), that.getIsActive()) &&
                Objects.equals(getCohortDetails(), that.getCohortDetails()) &&
                Objects.equals(getServerStatus(), that.getServerStatus());
    }


    /**
     * Return a hash code based on the values of this object.
     *
     * @return in hash code
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(getServerInstanceName(), getDescription(), getPlatformRootURL(), getServerName(),  getServerOrigin(), getIsActive(), getCohortDetails(), getServerStatus());
    }

}
