package com.example.myroom.retrofit;

public class GatewayUuidBody {

    String gatewayUUID;

    public GatewayUuidBody(String gatewayUUID) {
        this.gatewayUUID = gatewayUUID;
    }

    public String getGatewayUUID() {
        return gatewayUUID;
    }

    public void setGatewayUUID(String gatewayUUID) {
        this.gatewayUUID = gatewayUUID;
    }
}
