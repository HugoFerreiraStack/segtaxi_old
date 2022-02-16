package br.com.seg.econotaxi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService {

    protected Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

}
