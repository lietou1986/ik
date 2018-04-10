package com.zpcampus.runtime;
import org.apache.log4j.Level;

public class LogManager {

    private final static org.apache.log4j.Logger debugLogger = org.apache.log4j.LogManager.getLogger("debug");
    private final static org.apache.log4j.Logger infoLogger = org.apache.log4j.LogManager.getLogger("info");
    private final static org.apache.log4j.Logger warnLogger = org.apache.log4j.LogManager.getLogger("warn");
    private final static org.apache.log4j.Logger errorLogger = org.apache.log4j.LogManager.getLogger("error");
    private final static org.apache.log4j.Logger traceLogger = org.apache.log4j.LogManager.getLogger("trace");

    private static String FQCN = LogManager.class.getName();

    public static void setLogger(String name) {
        FQCN = name;
    }

    public static void trace(String msg) {
        traceLogger.log(FQCN, Level.TRACE, msg, null);
    }

    public static void trace(String msg, Throwable e) {
        traceLogger.log(FQCN, Level.TRACE, msg, e);
    }

    public static void debug(String msg) {
        debugLogger.log(FQCN, Level.DEBUG, msg, null);
    }

    public static void debug(String msg, Throwable e) {
        debugLogger.log(FQCN, Level.DEBUG, msg, e);
    }

    public static void info(String msg) {
        infoLogger.log(FQCN, Level.INFO, msg, null);
    }

    public static void info(String msg, Throwable e) {
        infoLogger.log(FQCN, Level.INFO, msg, e);
    }

    public static void warn(String msg) {
        warnLogger.log(FQCN, Level.WARN, msg, null);
    }

    public static void warn(String msg, Throwable e) {
        warnLogger.log(FQCN, Level.WARN, msg, e);
    }

    public static void error(String msg) {
        errorLogger.log(FQCN, Level.ERROR, msg, null);
    }

    public static void error(String msg, Throwable e) {
        errorLogger.log(FQCN, Level.ERROR, msg, e);
    }

    public static boolean isTraceEnabled() {
        return traceLogger.isTraceEnabled();
    }

    public static boolean isDebugEnabled() {
        return debugLogger.isDebugEnabled();
    }

    public static boolean isInfoEnabled() {
        return infoLogger.isInfoEnabled();
    }

    public static boolean isWarnEnabled() {
        return warnLogger.isEnabledFor(Level.WARN);
    }

    public static boolean isErrorEnabled() {
        return errorLogger.isEnabledFor(Level.ERROR);
    }

}
