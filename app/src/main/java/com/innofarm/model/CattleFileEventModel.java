package com.innofarm.model;

/**
 * CattleFile EventList
 */
public class CattleFileEventModel {
    private String eventId;
    private String   cattleId;
    private String   eventIns;
    private String   eventName;
    private String   eventTime;

    public String getEventId() {
        return eventId;
    }

    public String getCattleId() {
        return cattleId;
    }

    public String getEventIns() {
        return eventIns;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventTime() {
        return eventTime;
    }


    @Override
    public String toString() {
        return "CattleFileEvent{" +
                "eventId='" + eventId + '\'' +
                ", cattleId='" + cattleId + '\'' +
                ", eventIns='" + eventIns + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventTime='" + eventTime + '\'' +
                '}';
    }
}
