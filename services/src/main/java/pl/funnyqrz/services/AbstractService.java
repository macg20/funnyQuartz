package pl.funnyqrz.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService {

    Logger logger = LoggerFactory.getLogger(getClass());

    public Logger getLogger() {
        return logger;
    }
}
