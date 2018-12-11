package com.mycompany.app;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsMode;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.AttendeeCollection;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.Mailbox;

public class MeetingService {
  ExchangeService exchangeService = new ExchangeService(ExchangeVersion.Exchange2010_SP2);

  public MeetingService() throws URISyntaxException {
    String username = System.getenv("ADAPT_SMTP_USER");
    String password = System.getenv("ADAPT_SMTP_PASSWORD");
    String url = System.getenv("ADAPT_EWS_URL");

    exchangeService.setCredentials(new WebCredentials(username, password));
    exchangeService.setUrl(new URI(url));
  }

  public void sendMeeting(String subject, String organizer, String attendee, Date date) {
    Calendar startDate = Calendar.getInstance();
    startDate.setTime(date);
    startDate.set(Calendar.HOUR_OF_DAY, 13);

    Calendar endDate = startDate;
    endDate.add(Calendar.MINUTE, 30);

    try {
      Appointment appointment = new Appointment(exchangeService);
      AttendeeCollection requiredAttendees = appointment.getRequiredAttendees();
      FolderId folderId = new FolderId(WellKnownFolderName.Calendar, Mailbox.getMailboxFromString(organizer));

      appointment.setSubject(subject);
      appointment.setStart(startDate.getTime());
      appointment.setEnd(endDate.getTime());
      requiredAttendees.add(attendee);

      appointment.save(folderId, SendInvitationsMode.SendOnlyToAll);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
