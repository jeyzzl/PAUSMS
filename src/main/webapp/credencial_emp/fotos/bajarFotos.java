package credencial_emp.fotos;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Label;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.jdbc.driver.OracleResultSet;
import oracle.sql.BLOB;

public class bajarFotos extends Applet 
{
  private FileOutputStream fileOutStream;		
	ByteArrayOutputStream output;
	URL baseURL;
	String base,mensaje; 		
	int tFotos,nFotos,nFail;
	boolean bCancel;
	Label lblMensaje = new Label  ("Da clic en el boton para"),
	lblProgreso= new Label  ("iniciar la transferencia..."),
	n = new Label  ("");
	Button btnAceptar = new Button ("Transferir!");
	Button btnCancel = new Button ("Cancelar");
	public void init()
	{		
		setLayout(null);
		baseURL = getCodeBase();
		base = baseURL.toString(); 
		tFotos=nFotos=nFail=0;				
		lblMensaje.setBounds(10,5,300,20);
		lblProgreso.setBounds(10,25,300,20);
		n.setBounds(10,45,300,25);
		btnAceptar.setBounds(10,65,140,25);
		btnCancel.setBounds(150,65,140,25);
		add(lblMensaje);
		add(lblProgreso);
		add(n);
		add(btnAceptar);		
		//add(btnCancel);	
		this.setBackground(Color.white);
		lblMensaje.setBackground(Color.white);
		lblProgreso.setBackground(Color.white);		
		n.setBackground(Color.white);
		System.out.println("inicializado");
	}
	public boolean action(Event x, Object y)
	{	
		if (x.target == btnAceptar){
			bCancel = false;
			lblMensaje.setForeground(Color.black);
			lblMensaje.setText("Iniciando trasferencia");
			lblProgreso.setText("Por favor, espere...");
			btnAceptar.setEnabled(false);
			Transferir();
		}
		if (x.target == btnCancel){
			bCancel = true;
			lblMensaje.setForeground(Color.black);
			lblMensaje.setText("Transferencia cancelada...");
			lblProgreso.setText("");
			btnAceptar.setEnabled(true);
		}
		
		return true;
	}
	
	public void Transferir()
	{    
			String paso = "2";
			int cont=0;	
			try{
				// cont = Bajar("c:\\Credenciales\\FotosEmpleados","Aron.dependiente","id_empleado");
				 cont = Bajar("c:\\Credenciales\\FotosEmpleados","Aron.empleado","id_empleado");
	   	}catch(Exception e){e.printStackTrace();}
		 	if (cont>0) {
				paso = "3";		
			}else{
				lblMensaje.setText("Algun error ha ocurrido...");
				lblMensaje.setForeground(Color.red);
				lblMensaje.repaint();
				lblProgreso.setText("Click en transferir para intentar de nuevo");				
				btnAceptar.setEnabled(true);
			}
	}
	
  public int Bajar(String direc,String tabla,String campo) throws Exception
  { 
			Connection  cn = null;
			Statement   stmt = null;
	 		ResultSet   rset = null;					
			String COMANDO = "",matricula="",folio="";
			int ok=0;
			nFotos=0;
			FileOutputStream flog=null;
			try{				
				System.out.print("Creando conexion...");
				DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
				cn=DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora1","enoc","caminacondios");
				stmt=cn.createStatement();				
				System.out.println("OK!");
			}catch (Exception ex){ex.printStackTrace();};		
			try{
				InputStream blobStream;
				File Comp;
				cn.setAutoCommit(false);
				COMANDO = "select count(*) total from "+tabla;
				rset = stmt.executeQuery(COMANDO);
				rset.next();
				tFotos = rset.getInt(1);				
				flog = new FileOutputStream("C:\\Credenciales\\EmpleadoNoFoto.txt");				
				
				COMANDO = "select "+campo;
				if (tabla.equals("Aron.dependiente")) COMANDO+=",folio ";
				COMANDO += " from "+tabla+" order by "+campo;
				rset = stmt.executeQuery(COMANDO);
				
				COMANDO="select fotografia from "+tabla+" Where "+campo+" = ?"; 
				if (tabla.equals("Aron.dependiente")) COMANDO+=" and folio = ?";

				PreparedStatement ps = cn.prepareStatement(COMANDO);
				ResultSet rset2=null;
				while (rset.next()){
					nFotos++;
					matricula = rset.getString(1);
					folio="";
					if (tabla.equals("Aron.dependiente"))folio=rset.getString(2);
					if (tabla.equals("Aron.empleado"))lblMensaje.setText(matricula);
					else lblMensaje.setText(matricula+"-"+folio);
					lblProgreso.setText((int)((double)nFotos/(double)tFotos*100) + "% Completado...");						
					ps.setString(1,matricula);
					if (tabla.equals("Aron.dependiente")) ps.setString(2,folio);
					rset2 = ps.executeQuery();	
					rset2.next();
					String nombreCompleto="";
					if (tabla.equals("Aron.empleado"))nombreCompleto = direc+"\\"+matricula+".jpg";
					else nombreCompleto = direc+"\\"+matricula+"-"+folio+".jpg";
					BLOB blob =((OracleResultSet)rset2).getBLOB("fotografia");
					if (blob!=null){
						blobStream = blob.getBinaryStream();   
						FileOutputStream fileOutStream= new FileOutputStream(nombreCompleto);
						byte[] buffer = new byte[10];        
						int nbytes = 0;     
						while ((nbytes = blobStream.read(buffer))!= -1)        
					   	fileOutStream.write(buffer,0,nbytes);        
						fileOutStream.flush();        
						fileOutStream.close();        
						blobStream.close();
					}else{
						nFail++;
						flog.write((String.valueOf(nFail)+". ").getBytes());
						flog.write(matricula.getBytes());
						flog.write('\r');flog.write('\n');
						flog.flush();
					}
					if (tabla.equals("Aron.empleado"))n.setText(nFotos+" de "+tFotos+", "+nFail+" empleado(s) sin foto(s).");
					else n.setText(nFotos+" de "+tFotos+", "+nFail+" dependiente(s) sin foto(s).");
				}
				lblMensaje.setText("Ok! "+ String.valueOf(tFotos-nFail) + " fotos descargadas...");				
				ok = 1;
			}catch (Exception ex){ex.printStackTrace();}
			finally{
				cn.setAutoCommit(true);
	 			 cn.close();	
				 flog.close();
			};
			return ok;
	}
}