package com.lanbing.spring.xnolscan.log.formatter;

import ch.qos.logback.classic.spi.ILoggingEvent;

public interface Formatter {
    String format(ILoggingEvent event);
}
