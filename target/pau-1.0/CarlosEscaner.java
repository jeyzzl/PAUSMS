import java.sql.*;
import java.util.*;
import java.applet.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import SK.gnome.twain.*;
import com.sun.image.codec.jpeg.*;
import java.net.*;

public class CarlosEscaner extends Applet 
{	private Image image;	
	private int width;
	private int height;
  private FileOutputStream fileOutStream;	
	ByteArrayOutputStream output;
	URL baseURL;
	String s_thoja,s_nhoja,s_iddocumento,s_codigo_personal,archivo, base,s_color; 	
	CarlosEscaner p;	
	public void init()
	{
		s_thoja = getParameter("thoja");
		s_nhoja = getParameter("nhoja");			 		
		s_iddocumento = getParameter("f_documento");
		s_codigo_personal = getParameter("f_codigo_personal");		
		s_color = getParameter("color");	
		archivo = "c:\\escaner.jpg";
 	  baseURL = getCodeBase();
		base = baseURL.toString(); 
	  p=new CarlosEscaner();				  	
	}
	public void start()
	{
	  boolean ok;
		String paso="0";
		p.Escanear(archivo,s_thoja,s_color); 
		ok = p.upload(archivo,s_codigo_personal,s_iddocumento,s_nhoja);
		if (ok) paso = "4";		
		try{getAppletContext().showDocument(new URL(base+"addimg.jsp?f_codigo_personal="+s_codigo_personal+"&f_documento="+s_iddocumento+"&Prev="+paso+"&nhoja="+s_nhoja+"&thoja="+s_thoja), "_self");}
		catch(Exception e){e.printStackTrace();}
	}
	void scan(String TamanoHoja,String color) throws Exception 
	{			
		Twain twain=new Twain();
		twain.setSelectSource(false);
		twain.setVisible(false);
		if (color.equals("RGB"))	twain.setPixelType(Twain.RGB);
		else twain.setPixelType(Twain.GRAY);
		twain.setCurrentUnits(Twain.CENTIMETERS);
		twain.setXResolution(350);twain.setYResolution(350);
		if (TamanoHoja.equals("1"))
		   twain.setFrame(0,0,21.59,27.94);
		else twain.setFrame(0,0,21.59,35.56);
		twain.setBrightness(-100);twain.setContrast(1000);
		twain.setXScaling(1);twain.setYScaling(1);
		twain.setAutoBrightness(false);
		twain.setOrientation(Twain.ROT90);
		twain.setShadow(0);
	  twain.setHighLight(0);			
		image=Toolkit.getDefaultToolkit().createImage(twain);
		TwainException twainException= Twain.getLastException();
		if(null!=twainException)
			System.err.println(twainException);	
}

	public void Escanear(String NombreArchivo, String TamanoHoja,String color)
	{
    try{scan(TamanoHoja,color);}catch(Exception e){}
		save(NombreArchivo);				
	}
  public void save(String NombreArchivo)
  { File f=new File(NombreArchivo);
    try
    { int w = image.getWidth(null);
      int h = image.getHeight(null);
      BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
      bimg.createGraphics().drawImage(image, 0, 0, null);
      FileOutputStream out = new FileOutputStream(f);
      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
      JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimg);
      param.setQuality(0.7f, false);
      encoder.setJPEGEncodeParam(param);
      encoder.encode(bimg);
      out.close();
    }
    catch (Exception ex)
    { ex.printStackTrace(); 
		}
  }	
	
	public boolean upload(String filename, String matricula, String iddoc, String numhoja)
	{
	 	Connection  conn2 = null;
	  Statement   stmt2 = null;
	  ResultSet   rset2 = null;
		boolean dev = false;
		String COMANDO = "";
   	PreparedStatement pstmt= null;
		try{		
	  DriverManager.registerDriver (new org.postgresql.Driver());		
	  conn2=DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
	  stmt2=conn2.createStatement();				
		}catch (Exception ex){ex.printStackTrace();};		
		File file = new File(filename);
		if (file.exists()){			
			long oid = 0;
			org.postgresql.largeobject.LargeObjectManager lom = null;
			org.postgresql.largeobject.LargeObject obj = null;
			FileInputStream fis = null;
			// Crea el large Object para guardar la imagen
			try{
				conn2.setAutoCommit(false);
			  	lom = ((org.postgresql.Connection)conn2).getLargeObjectAPI();			
				oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
				obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
			}catch (Exception ex){ex.printStackTrace();
			};
			// Selecciona el numero de hoja
			try{			
				COMANDO = "select hoja from arch_docalum where iddocumento = '"+iddoc+"' and matricula = '"+matricula+"' and hoja = "+numhoja;			 
				rset2 = stmt2.executeQuery(COMANDO);			
			}catch (Exception ex){ex.printStackTrace();
			};
			// Comando de insert o update 
			try{			
				if (rset2.next()) 
					COMANDO = "update arch_docalum set imagen = ?,fuente = 'E' where iddocumento = '"+iddoc+"' and matricula = '"+matricula+"' and hoja = '"+numhoja+"'"; 
				else 
					COMANDO = "insert into arch_docalum(matricula,iddocumento,imagen,hoja,fuente) Values ('"+matricula+"','"+iddoc+"',?,"+numhoja+",'E')";						 
				pstmt = conn2.prepareStatement(COMANDO);
			}catch (Exception ex){ex.printStackTrace();
			};
			// Crea el archivo 
			try{
				fis = new FileInputStream(file);
				byte buf[] = new byte[(int)file.length()];
				int le; while ((le=fis.read(buf)) !=-1){obj.write(buf,0,le);}
				pstmt.setInt(1,oid);			
			}catch (Exception ex){ex.printStackTrace();
			};
			// Graba el archivo en la base de datos
			try{
				pstmt.execute();
				conn2.commit();				
				fis.close();
				file.delete();			
			}catch (Exception ex){ex.printStackTrace();
			};			
			dev = true;
			conn2.setAutoCommit(true);
		}
		return dev;
	}	
}