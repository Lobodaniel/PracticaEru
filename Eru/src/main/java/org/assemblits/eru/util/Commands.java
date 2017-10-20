package org.assemblits.eru.util;

/**
 * Created by mtrujillo on 9/7/2015.
 */
public class Commands {
    public static final String INITIALIZE_SYSTEM             = "engine.initialize.system";
    public static final String LOAD_DATABASE                 = "database.load";
    public static final String PAUSE_SYNCHRONIZATION         = "engine.synchronize.pause";
    public static final String PAUSE_ALL_SYNCHRONIZATIONS    = "engine.synchronize.all.pause";
    public static final String CONTINUE_SYNCHRONIZATION      = "engine.synchronize.continue";
    public static final String CONTINUE_ALL_SYNCHRONIZATIONS = "engine.synchronize.all.continue";
    public static final String INITIALIZE_PM                 = "engine.initialize.pm";
    public static final String SYNCHRONIZE_PM                = "engine.synchronize.pm";
    public static final String SYNCHRONIZE_SYSTEM_PM         = "engine.synchronize.system.pm";
    public static final String ACKNOWLEDGE_ALARMS            = "alarms.acknowledge";
    public static final String CHECK_USER_ENTRY              = "user.check.entry";
    public static final String START_COMMUNICATIONS          = "user.protocols.load";
    public static final String STOP_COMMUNICATIONS           = "user.protocols.stopDirector";
    public static final String BLOCK_COMMUNICATIONS          = "user.protocols.block";
    public static final String RELEASE_COMMUNICATIONS        = "user.protocols.release";
    public static final String START_HISTORIAN               = "user.export.load";
    public static final String STOP_HISTORIAN                = "user.export.stopDirector";
    public static final String START_ALARMING                = "user.alarming.load";
    public static final String STOP_ALARMING                 = "user.alarming.stopDirector";
    public static final String WRITE_TAG                     = "write.tag";

}
