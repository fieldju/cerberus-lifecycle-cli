package com.nike.cerberus.domain.environment;

import java.util.HashMap;
import java.util.Map;

/**
 * General purpose environment data that isn't sensitive.
 */
public class Environment {

    private String az1;

    private String az2;

    private String az3;

    private Map<StackName, String> stackMap;

    private Map<StackName, String> serverCertificateIdMap;

    private String configKeyId;

    private String replicationBucketName;

    /**
     * Is the environment configured for continuous delivery
     */
    private boolean isCd;

    public Environment() {
        stackMap = new HashMap<>();
        for (StackName stackName : StackName.values()) {
            stackMap.put(stackName, "");
        }

        serverCertificateIdMap = new HashMap<>();
        serverCertificateIdMap.put(StackName.GATEWAY, "");
        serverCertificateIdMap.put(StackName.CMS, "");
        serverCertificateIdMap.put(StackName.VAULT, "");
        serverCertificateIdMap.put(StackName.CONSUL, "");
    }

    public String getAz1() {
        return az1;
    }

    public Environment setAz1(String az1) {
        this.az1 = az1;
        return this;
    }

    public String getAz2() {
        return az2;
    }

    public Environment setAz2(String az2) {
        this.az2 = az2;
        return this;
    }

    public String getAz3() {
        return az3;
    }

    public Environment setAz3(String az3) {
        this.az3 = az3;
        return this;
    }

    public Map<StackName, String> getStackMap() {
        return stackMap;
    }

    public Environment setStackMap(Map<StackName, String> stackMap) {
        this.stackMap = stackMap;
        return this;
    }

    public Map<StackName, String> getServerCertificateIdMap() {
        return serverCertificateIdMap;
    }

    public Environment setServerCertificateIdMap(Map<StackName, String> serverCertificateIdMap) {
        this.serverCertificateIdMap = serverCertificateIdMap;
        return this;
    }

    public String getConfigKeyId() {
        return configKeyId;
    }

    public Environment setConfigKeyId(String configKeyId) {
        this.configKeyId = configKeyId;
        return this;
    }

    public boolean isCd() {
        return isCd;
    }

    public Environment setCd(boolean cd) {
        isCd = cd;
        return this;
    }

    public String getReplicationBucketName() {
        return replicationBucketName;
    }

    public Environment setReplicationBucketName(String replicationBucketName) {
        this.replicationBucketName = replicationBucketName;
        return this;
    }
}