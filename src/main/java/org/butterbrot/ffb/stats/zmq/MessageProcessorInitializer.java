package org.butterbrot.ffb.stats.zmq;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MessageProcessorInitializer implements InitializingBean {

    @Resource
    private MessageProcessor messageProcessor;

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(messageProcessor).start();
    }
}
