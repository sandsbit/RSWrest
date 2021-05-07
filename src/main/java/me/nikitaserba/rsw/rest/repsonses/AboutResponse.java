package me.nikitaserba.rsw.rest.repsonses;

import com.google.gson.annotations.SerializedName;

public final class AboutResponse {

    @SerializedName("service_name")
    private final String serviceName = "RSW rest-api";
    private final String version = "v1.0";
    @SerializedName("version_code")
    private final int versionCode = 0;
    @SerializedName("api_version")
    private final int apiVersion = 0;

    public AboutResponse() {}

    public String getServiceName() {
        return serviceName;
    }

    public String getVersion() {
        return version;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public int getApiVersion() {
        return apiVersion;
    }
}
