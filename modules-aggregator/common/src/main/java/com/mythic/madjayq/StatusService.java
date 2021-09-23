package com.mythic.madjayq;

public interface StatusService {
    void setServiceStatus(Class<?> clazz, ServiceStatus status);
    ServiceStatus getServiceStatus(Class<?> clazz);
}