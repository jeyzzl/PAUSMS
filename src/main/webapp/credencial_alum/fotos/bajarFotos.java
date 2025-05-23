import java.util.*;
import java.applet.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.net.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;

public class bajarFotos extends Applet{
	FileOutputStream fileOutStream;
	ByteArrayOutputStream output;
	URL baseURL;
	String base,mensaje, dir;
	int tFotos,nFotos,nFail;
	Label lblMensaje = new Label  ("Da clic en el boton Transferir para "),
				lblProgreso= new Label  ("iniciar la transferencia..."),
				n = new Label  ("");
	Button btnAceptar = new Button ("Transferir!");
	public void init()
	{		
		nFotos=nFail=0;				
		tFotos = Integer.parseInt(getParameter("total"));
		dir = getParameter("dir");
		setLayout(null);
		baseURL = getCodeBase();
		base = baseURL.toString(); 
		lblMensaje.setBounds(10,5,300,20);
		lblProgreso.setBounds(10,25,300,20);
		n.setBounds(10,45,300,25);
		btnAceptar.setBounds(10,65,140,25);
		add(lblMensaje);
		add(lblProgreso);
		add(n);
		add(btnAceptar);		
		this.setBackground(Color.white);
		lblMensaje.setBackground(Color.white);
		lblProgreso.setBackground(Color.white);		
		n.setBackground(Color.white);		
	}
	public boolean action(Event x, Object y)
	{	
		if (x.target == btnAceptar){
			lblMensaje.setForeground(Color.black);
			lblMensaje.setText("Iniciando trasferencia");
			lblProgreso.setText("Por favor, espere...");
			btnAceptar.setEnabled(false);
			Transferir();
		}
		return true;
	}
	
	public void Transferir(){    
		String paso = "2",matricula="";;
		int error=0;	
		(new File(dir)).mkdirs();
		try {
				for (int i=0;i<tFotos;i++){
	        // Create a URL for the desired page
					matricula = getParameter("foto"+String.valueOf(i));
					lblMensaje.setText("Copiando "+matricula);
					lblMensaje.setForeground(Color.BLACK);
					
	        URL url = new URL(base+"foto.jsp?mat="+matricula);
					FileOutputStream fo = new FileOutputStream(dir+"/"+matricula+".jpg");
					InputStream in = url.openStream();
					int r;
					while ((r=in.read())!=-1)	fo.write(r);
					fo.close();
					lblProgreso.setText(String.valueOf( (int) ((i+1)*100)/tFotos )+"% completado...");		
					lblMensaje.repaint();
				}
    } catch (Exception e) {
			e.printStackTrace();
			error=1;
    }
	 	if (error==0) {
			lblMensaje.setText("Transferencia Terminada...");		
			lblMensaje.repaint();
			paso = "2";		
		}else{
			lblMensaje.setText("Ha ocurrido un error...");
			lblMensaje.setForeground(Color.red);
			lblMensaje.repaint();
			lblProgreso.setText("Click en transferir para intentar de nuevo");				
			btnAceptar.setEnabled(true);
		}
	}
	
	public int Bajar(String direc) throws Exception{ 
  		return 1;
	}
}