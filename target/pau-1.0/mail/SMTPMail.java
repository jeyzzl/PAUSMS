package mail;


import java.util.*;
import jakarta.mail.internet.*;

public class SMTPMail {

  protected Map toAddresses = new HashMap();
  protected Map ccAddresses = new HashMap();
  protected Map bccAddresses = new HashMap();
  protected Map replyToAddresses = new HashMap();

  protected String fromName = null;
  protected String fromAddress = null;
  protected String subject = null;

  protected String messageText = new String();

  public SMTPMail(String fromName, String fromAddress, String subject) {
    this.fromName = fromName;
    this.fromAddress = fromAddress;
    this.subject = subject;
  }

  public InternetAddress getFrom() throws Exception{
    return new InternetAddress(fromAddress, fromName);
  }

  public String getSubject(){
    return subject;
  }

  public String getMessageText(){
    return messageText;
  }


  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  public void addToAddress(String name, String address) {
    toAddresses.put(name, address);
  }

  public void addCcAddress(String name, String address) {
    ccAddresses.put(name, address);
  }

  public void addBccAddress(String name, String address) {
    bccAddresses.put(name, address);
  }

  public void addReplyToAddress(String name, String address) {
    replyToAddresses.put(name, address);
  }

  public InternetAddress[] getToAddresses()  throws Exception{
    return getAddresses(toAddresses);
  }

  public InternetAddress[] getCcAddresses()  throws Exception{
    return getAddresses(ccAddresses);
  }


  public InternetAddress[] getBccAddresses()  throws Exception{
    return getAddresses(bccAddresses);
  }

  public InternetAddress[] getReplyToAddresses()  throws Exception{
    return getAddresses(replyToAddresses);
  }

  private InternetAddress[] getAddresses(Map addresses)
    throws Exception {
    InternetAddress address = null;
    ArrayList list = new ArrayList();
    String name = null;
    String email = null;
    Iterator i = addresses.keySet().iterator();

    while (i.hasNext()) {
      name = (String) i.next();
      email = (String) addresses.get(name);

      try {
        list.add(new InternetAddress(email, name));
      }
      catch (Exception ex) {
        System.out.println("Direccion de correo invalida: " + email);
      }
    }

    if (list.isEmpty()) {
      return null;
    }

    InternetAddress all[] = new InternetAddress[list.size()];
    all = (InternetAddress[]) list.toArray(all);
    return all;
  }

}