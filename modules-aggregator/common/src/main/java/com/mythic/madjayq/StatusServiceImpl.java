package com.mythic.madjayq;

import com.google.common.base.Verify;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class StatusServiceImpl implements StatusService {

    public static Logger log = LoggerFactory.getLogger(StatusService.class);

    private final Map<String, ServiceStatus> serviceStatusByName =Maps.newHashMap();

    @Override
    public void setServiceStatus(Class<?> clazz, ServiceStatus status) {
        String serviceName = clazz.getName();
        if(serviceStatusByName.containsKey(serviceName)) {
            log.info("Service " + serviceName + " transitioned from " + getServiceStatus(clazz) + " to " + status);
        }
        else {
            log.info("Service " + serviceName + " entered state:" + status);
        }
        serviceStatusByName.put(serviceName, status);
    }

    @Override
    public ServiceStatus getServiceStatus(Class<?> clazz) {
        //Verify.verify(serviceStatusByName.containsKey(clazz.getName()));
        return serviceStatusByName.get(clazz.getName());
    }
}