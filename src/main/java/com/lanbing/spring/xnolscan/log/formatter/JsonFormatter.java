package com.lanbing.spring.xnolscan.log.formatter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.alibaba.fastjson.JSONObject;
import com.lanbing.spring.xnolscan.util.DateUtils;


import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class JsonFormatter implements Formatter {

    private boolean expectJsonMessage = false;
    private boolean includeMethodAndLineNumber = false;
    private Map extraPropertiesMap = null;

    public String format(ILoggingEvent event) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("level", event.getLevel().levelStr);
        jsonObject.put("class", event.getLoggerName());
        jsonObject.put("timestamp", DateUtils.getTimeString(new Date(event.getTimeStamp())));
        jsonObject.put("message", event.getFormattedMessage());

        if (includeMethodAndLineNumber) {
            StackTraceElement[] callerDataArray = event.getCallerData();
            if (callerDataArray != null && callerDataArray.length > 0) {
                StackTraceElement stackTraceElement = callerDataArray[0];
                jsonObject.put("method", stackTraceElement.getMethodName());
                jsonObject.put("line", stackTraceElement.getLineNumber() + "");
            }
        }
        if (this.extraPropertiesMap != null) {
            jsonObject.putAll(extraPropertiesMap);
        }
        return jsonObject.toJSONString();
    }

    public boolean getExpectJsonMessage() {
        return expectJsonMessage;
    }

    public void setExpectJsonMessage(boolean expectJsonMessage) {
        this.expectJsonMessage = expectJsonMessage;
    }

    public boolean getIncludeMethodAndLineNumber() {
        return includeMethodAndLineNumber;
    }

    public void setIncludeMethodAndLineNumber(boolean includeMethodAndLineNumber) {
        this.includeMethodAndLineNumber = includeMethodAndLineNumber;
    }

    public void setExtraProperties(String thatExtraProperties) {
        final Properties properties = new Properties();
        try {
            properties.load(new StringReader(thatExtraProperties));
            Enumeration enumeration = properties.propertyNames();
            extraPropertiesMap = new HashMap();
            while (enumeration.hasMoreElements()) {
                String name = (String) enumeration.nextElement();
                String value = properties.getProperty(name);
                extraPropertiesMap.put(name, value);
            }
        } catch (IOException e) {
            System.out.println("There was a problem reading the extra properties configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }
}