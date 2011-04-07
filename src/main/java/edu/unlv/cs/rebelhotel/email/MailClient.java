package edu.unlv.cs.rebelhotel.email;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class MailClient
extends Authenticator
{
public static final int SHOW_MESSAGES = 1;
public static final int CLEAR_MESSAGES = 2;
public static final int SHOW_AND_CLEAR =
SHOW_MESSAGES + CLEAR_MESSAGES;

protected String from;
protected Session session;
protected PasswordAuthentication authentication;

public MailClient(String user, String host)
{
this(user, host, false);
}

public MailClient(String user, String host, boolean debug)
{
from = user + '@' + host;
authentication = new PasswordAuthentication(user, user);
Properties props = new Properties();
props.put("mail.user", user);
props.put("mail.host", host);
props.put("mail.debug", debug ? "true" : "false");
props.put("mail.store.protocol", "pop3");
props.put("mail.transport.protocol", "smtp");
session = Session.getInstance(props, this);
}

public PasswordAuthentication getPasswordAuthentication()
{
return authentication;
}

public void sendMessage(
String to, String subject, String content)
  throws MessagingException
{
System.out.println("SENDING message from " + from + " to " + to);
System.out.println();
MimeMessage msg = new MimeMessage(session);
msg.addRecipients(Message.RecipientType.TO, to);
msg.setSubject(subject);
msg.setText(content);
Transport.send(msg);
}

public void checkInbox(int mode)
   throws MessagingException, IOException
{
if (mode == 0) return;
boolean show = (mode & SHOW_MESSAGES) > 0;
boolean clear = (mode & CLEAR_MESSAGES) > 0;
String action =
  (show ? "Show" : "") +
  (show && clear ? " and " : "") +
  (clear ? "Clear" : "");
System.out.println(action + " INBOX for " + from);
Store store = session.getStore();
store.connect();
Folder root = store.getDefaultFolder();
Folder inbox = root.getFolder("inbox");
inbox.open(Folder.READ_WRITE);
Message[] msgs = inbox.getMessages();
if (msgs.length == 0 && show)
{
  System.out.println("No messages in inbox");
}
for (int i = 0; i < msgs.length; i++)
{
  MimeMessage msg = (MimeMessage)msgs[i];
  if (show)
  {
    System.out.println("    From: " + msg.getFrom()[0]);
    System.out.println(" Subject: " + msg.getSubject());
    System.out.println(" Content: " + msg.getContent());
  }
  if (clear)
  {
    msg.setFlag(Flags.Flag.DELETED, true);
  }
  }
  inbox.close(true);
  store.close();
  System.out.println();
  }
  }
