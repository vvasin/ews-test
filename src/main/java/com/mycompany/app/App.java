package com.mycompany.app;

import java.util.Date;

public class App 
{
    public static void main( String[] args )
    {
        try {
            MeetingService meetingService = new MeetingService();
            meetingService.sendMeeting("test meeting", "v.vasin@hh.ru", "v.vasin@hh.ru", new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
