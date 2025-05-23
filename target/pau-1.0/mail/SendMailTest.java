package mail;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  TEst para enviar mails
 *
 *@author     <a href=mailto:al AT javahispano DOT org>Alberto Molpeceres</a>
 */
public class SendMailTest {
    public boolean validaMail(String mail) {
        mail = mail.toLowerCase();
        Pattern p = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
        Matcher m = p.matcher(mail);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
    public Vector listacorreos(){
        Vector ve = new Vector();
        File archivo = new File("C:/tmp/exaum.txt");
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        try{
            fis = new FileInputStream(archivo);
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            while (dis.available() != 0) {
                String linea = dis.readLine();
                if(validaMail(linea.trim())){
                    ve.add(linea.trim());
                }
            }
        }catch (FileNotFoundException fnfe){
            System.err.println(fnfe);
        }catch (IOException ioe){
            System.err.println(ioe);
        }
        return ve;
      }
  public static void main(String[] a) {
  	/**
    if (args.length != 5) {
      System.out.println("Uso: java org.javahispano.mailer.test.SendMailTest host usuario password remitente destinatario"
        );
      System.exit(-1);
    }
    **/
    
    SMTPSession smtp = new SMTPSession();
    smtp.setDebug(true);
    SMTPMail mail = new SMTPMail("Informacion Um", "no-reply@um.edu.mx", "Instituto de misiones");
    SendMailTest smt = new SendMailTest();
    Vector ve = new Vector();
    ve = smt.listacorreos();
    for(int i=0;i<ve.size();i++){
        mail.addToAddress("Usuario", (String) ve.elementAt(i));
        mail.addReplyToAddress("Abimael Lozano","institutodemisiones@um.edu.mx");
        mail.setMessageText("<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
            "<head><meta http-equiv=\"Content-Type\" content=\"text/html; " +
            "charset=utf-8\" /><title>Documento sin título</title></head><body>" +
            "<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" " +
            "cellspacing=\"0\">  <tr>    <td style=\"text-align: center\">" +
            "<img src=\"http://centauro.um.edu.mx/imisiones.png\" width=\"648\" " +
            "height=\"341\" /></td>  </tr>  <tr>    <td><table width=\"100%\" " +
            "border=\"0\" align=\"right\" cellpadding=\"10\" cellspacing=\"0\">" +
            "      <tr>        <td width=\"200\" style=\"text-align: right; " +
            "font-family: Verdana, Geneva, sans-serif; font-weight: bold; font-size: " +
            "12px;\">Fecha:</td>        <td style=\"font-family: Verdana, Geneva, " +
            "sans-serif; font-size: 12px; text-align: left;\">Próximo viernes 13 " +
            "y sábado 14 marzo</td>      </tr>      <tr>        <td valign=\"top\" " +
            "style=\"text-align: right; font-family: Verdana, Geneva, sans-serif; " +
            "font-weight: bold; font-size: 12px;\">Invitados: </td>        " +
            "<td style=\"font-family: Verdana, Geneva, sans-serif; font-size: 12px; " +
            "text-align: left;\"><p>Barbara y Homer Trecartin.  El pastor Trecartin es:<br /> " +
            "         <em>Secretario Asociado Asociación General</em><br />  " +
            "<em> Dir. Mundial de  Servicio Voluntario<br />  </em><em>Dir. de  " +
            "Planeación de Misión Mundial</em>&nbsp; </p></td>      </tr>      " +
            "<tr>        <td colspan=\"2\" valign=\"top\" style=\"text-align: " +
            "center; font-family: Verdana, Geneva, sans-serif; font-weight: bold;" +
            " font-size: 12px;\">PROGRAMA</td>        </tr>      <tr>       " +
            " <td valign=\"top\" style=\"text-align: right; font-family: Verdana," +
            " Geneva, sans-serif; font-weight: bold; font-size: 12px;\">Viernes</td>   " +
            "     <td style=\"font-family: Verdana, Geneva, sans-serif; font-size: " +
            "12px; text-align: left;\">&nbsp;</td>      </tr>      <tr>      " +
            "  <td valign=\"top\" style=\"text-align: right; font-family:" +
            " Verdana, Geneva, sans-serif; font-weight: bold; font-size: 12px;\">" +
            "08:30 am</td>        <td style=\"font-family: Verdana, Geneva, " +
            "sans-serif; font-size: 12px; text-align: left;\">Seminario:  " +
            "<em>Estrategias para involucrar y  estimular a la iglesia local" +
            " en la misión mundial</em><br />  Ponente:&nbsp;  Homer Trecartin<br />" +
            "  Lugar:&nbsp;  Capilla de Teología</td>      </tr>      <tr>   " +
            "     <td valign=\"top\" style=\"text-align: right; font-family: " +
            "Verdana, Geneva, sans-serif; font-weight: bold; font-size: 12px;\">" +
            "07:30 pm</td>        <td style=\"font-family: Verdana, Geneva, " +
            "sans-serif; font-size: 12px; text-align: left;\">Servicio de Gratitud " +
            "y Testimonios<br />          Invitado:&nbsp; Barbara Trecartin<br />" +
            "  Lugar:&nbsp; Iglesia  central<br /></td>      </tr>      <tr>     " +
            "   <td valign=\"top\" style=\"text-align: right; font-family: Verdana, " +
            "Geneva, sans-serif; font-weight: bold; font-size: 12px;\">Sábado</td> " +
            "       <td style=\"font-family: Verdana, Geneva, sans-serif; font-size:" +
            " 12px; text-align: left;\">&nbsp;</td>      </tr>      <tr>       " +
            " <td valign=\"top\" style=\"text-align: right; font-family: Verdana," +
            " Geneva, sans-serif; font-weight: bold; font-size: 12px;\">08:30 am</td>    " +
            "    <td style=\"font-family: Verdana, Geneva, sans-serif; font-size: 12px;" +
            " text-align: left;\">Culto Divino:&nbsp; <em>Celebrando nuestro  " +
            "compromiso con Dios y su Misión</em></td>      </tr>      <tr>     " +
            "   <td valign=\"top\" style=\"text-align: right; font-family: " +
            "Verdana, Geneva, sans-serif; font-weight: bold; font-size: 12px;\">10:00 am</td> " +
            "       <td style=\"font-family: Verdana, Geneva, sans-serif; " +
            "font-size: 12px; text-align: left;\"><p>Programa Especial de Escuela Sabática:&nbsp;" +
            " <em>Sábado  Mundial</em></p></td>      </tr>      <tr>       " +
            " <td valign=\"top\" style=\"text-align: right; font-family: Verdana," +
            " Geneva, sans-serif; font-weight: bold; font-size: 12px;\">12:00 pm</td>  " +
            "      <td style=\"font-family: Verdana, Geneva, sans-serif; font-size: 12px; " +
            "text-align: left;\">Comida con Voluntarios</td>      </tr>      <tr>      " +
            "  <td valign=\"top\" style=\"text-align: right; font-family: Verdana, Geneva," +
            " sans-serif; font-weight: bold; font-size: 12px;\">03:30 pm</td>      " +
            "  <td style=\"font-family: Verdana, Geneva, sans-serif; font-size: 12px; " +
            "text-align: left;\">Videopresentación:&nbsp; <em>El propósito global  de" +
            " Dios</em></td>      </tr>      <tr>        " +
            "<td valign=\"top\" style=\"text-align: right;" +
            " font-family: Verdana, Geneva, sans-serif; " +
            "font-weight: bold; font-size: 12px;\">04:00 pm</td>" +
            "        <td style=\"font-family: Verdana, Geneva," +
            " sans-serif; font-size: 12px; text-align: left;\">Seminario:&nbsp;" +
            " <em>Desafíos de la Misión Hoy y  Oportunidades de Servicio Voluntario" +
            "</em></td>      </tr>      <tr>        <td valign=\"top\" style=\"text-align: " +
            "right; font-family: Verdana, Geneva, sans-serif; font-weight: bold; font-size: 12px;\">" +
            "05:30 pm</td>        <td style=\"font-family: Verdana, Geneva, sans-serif; font-size:" +
            " 12px; text-align: left;\">Foro con invitados</td>      </tr>      <tr>        " +
            "<td valign=\"top\" style=\"text-align: right; font-family: Verdana, " +
            "Geneva, sans-serif; font-weight: bold; font-size: 12px;\">06:30 pm</td>   " +
            "     <td style=\"font-family: Verdana, Geneva, sans-serif; font-size: " +
            "12px; text-align: left;\">Despedida de Sábado:&nbsp; proyectos misioneros " +
            " y llamados de servicio voluntario</td>      </tr>      <tr>      " +
            "  <td colspan=\"2\" valign=\"top\" style=\"text-align: center; " +
            "font-family: Verdana, Geneva, sans-serif; font-size: 12px;\">" +
            "<p align=\"center\"><strong>¿QUIERES SER UN VOLUNTARIO?</strong><br />   " +
            "       Visita el Módulo  de Servicio Voluntario y aplica para una posición.<br /> " +
            "         (junto al  Conservatorio)</p></td>        </tr>    </table></td>" +
            "  </tr></table><blockquote>&nbsp;</blockquote></body></html>");

        try {
          System.out.println("\n\n[SendMailTest] Enviando...." + ve.elementAt(i));
          String daniel ="";
          smtp.sendMail(mail);
          System.out.println("[SendMailTest] Test realizado con exito  " + ve.elementAt(i));
        } catch (Exception e) {
          System.out.println("[SendMailTest] No se pudo conectar: " + e.getMessage());
          e.printStackTrace();
        }finally{
            break;
        }

    }
  }

}