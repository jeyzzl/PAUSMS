import java.sql.*;
import java.util.*;
import java.applet.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import java.net.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;

public class subirFotos extends Applet 
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
			 cont = Subir("c:\\Credenciales\\FotosEmpleados");
	   	}catch(Exception e){e.printStackTrace();}
		if (cont>0) {
			paso = "3";		
		}else{
			lblMensaje.setText("El directorio no existe o algun error ha ocurrido...");
			lblMensaje.setForeground(Color.red);
			lblMensaje.repaint();
			lblProgreso.setText("Click en transferir para intentar de nuevo");				
			btnAceptar.setEnabled(true);
		}
	}
	
  public int Subir(String direc) throws Exception
  { 
			Connection  cn = null;
			Statement   stmt = null;
	 		ResultSet   rset = null;					
			String COMANDO = "";
			int ok=0;
			FileOutputStream flog=null;
			try{				
				System.out.print("Creando conexion...");
				DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
				cn=DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora1","enoc","caminacondios");
				stmt=cn.createStatement();				
				System.out.println("OK!");
			}catch (Exception ex){ex.printStackTrace();};		
			try{
				System.out.print("Obteniendo Lista...");	
				File Dir = new File(direc);
				String Lista[] = Dir.list();
				String tabla="",campo="";
				String matricula="";
				File ListaArchivos[] = Dir.listFiles();	
				System.out.println("OK!");					
				tFotos=Lista.length;
				flog = new FileOutputStream("C:\\Credenciales\\SubirEmp.txt");
				for (int i=0;i<Lista.length;i++)
				{ 
					int folio=0;
					if (i%100==0){
						lblMensaje.setText("Reconectando...");
						cn.close();
						DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
	     				cn=DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora1","enoc","caminacondios");
						stmt=cn.createStatement();
						lblMensaje.setText("Ok!...reanudando transferencia");
					}
					System.out.print("-> ");
					matricula = ListaArchivos[i].getName();						
					matricula = matricula.substring(0,matricula.indexOf("."));
					System.out.print(matricula+" > ");												
					COMANDO = "select fotografia from aron.empleado Where id_empleado = '"+matricula+"'";
				    rset = stmt.executeQuery(COMANDO);		
					if (rset.next()){
						tabla = "aron.empleado";
						campo = "id_empleado";
					}else{
						if (matricula.length()>=7){
							folio=Integer.parseInt(matricula.substring(8,9));
							matricula=matricula.substring(0,7);
						}
						COMANDO = "select fotografia from aron.dependiente Where id_empleado = '"+matricula+"'";
						COMANDO += " and folio='"+folio+"'"; 
					    rset = stmt.executeQuery(COMANDO);		
						if (rset.next()){
							tabla = "aron.dependiente";
							campo = "id_empleado";
						}else tabla = "";
					}						
					nFotos=i+1;
					lblMensaje.setText(ListaArchivos[i].getName());
					lblProgreso.setText((int)((double)nFotos/(double)tFotos*100) + "% Completado...");						
			    	if (!tabla.equals("")){
						System.out.print(" Encontrado... ");
						try{
							cn.setAutoCommit(false);	
						}catch (Exception ex){ex.printStackTrace();};				
						try{
							COMANDO = "update "+tabla+" set fotografia = empty_blob() Where "+campo+" = '"+matricula+"'";
							if(folio>0)COMANDO += " and folio ="+folio;
							rset = stmt.executeQuery(COMANDO);
						  	COMANDO = "select fotografia from "+tabla+" Where "+campo+" = '"+matricula+"' ";
							if(folio>0)COMANDO += " and folio ="+folio;
							COMANDO += " for update";
							rset = stmt.executeQuery(COMANDO);
							rset.next();					
							BLOB blob=((OracleResultSet)rset).getBLOB("fotografia");
							FileInputStream instream = new FileInputStream(ListaArchivos[i].getAbsolutePath());
							OutputStream outstream = blob.getBinaryOutputStream();        
							int chunk = blob.getChunkSize();
							byte[] buff = new byte[chunk];  
							int le; while ( (le=instream.read(buff)) !=-1){outstream.write(buff,0,le);}
							outstream.close();
							instream.close();
							System.out.println("OK... cargada con exito!"); 
							n.setText((i+1)+" de "+(Lista.length)+". "+nFail+" error(es)");
						}catch (Exception ex){
							nFail++;
							n.setText((i+1)+" de "+(Lista.length)+"    "+nFail+" error(es)");
							flog.write((String.valueOf(nFail)+". ").getBytes());
							flog.write(ListaArchivos[i].getName().getBytes());
							flog.write('\r');flog.write('\n');
							flog.flush();
							ex.printStackTrace();
						};		
						repaint();
						//subirFotos.doEvents();
					}else{
						System.out.println("No encontrado");
						nFail++;
						n.setText((i+1)+" de "+(Lista.length)+"    "+nFail+" error(es)");
						flog.write((String.valueOf(nFail)+". ").getBytes());
						flog.write(ListaArchivos[i].getName().getBytes());
						flog.write('\r');flog.write('\n');
						flog.flush();
					} 
				}	
				ok=1;
				lblMensaje.setText("Fotos transferidas...");				
			}catch (Exception ex){ex.printStackTrace();}
			finally{
				cn.close();	
				flog.close();
			};
			return ok;
	}
}