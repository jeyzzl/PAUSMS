package digital;

import java.sql.*;
import java.applet.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import SK.gnome.twain.*;
import com.sun.image.codec.jpeg.*;
import java.net.*;

public class Escaner extends Applet{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;	
	
	ByteArrayOutputStream output;
	URL baseURL;
	String s_thoja,s_nhoja,s_iddocumento,s_codigo_personal,archivo, base,s_color,s_paso,s_config,s_origen,s_usuario; 	
	Escaner p;
	
	public void init(){
		s_paso 				= getParameter("paso");
		s_config 			= getParameter("config");
		s_origen 			= getParameter("origen");
		s_thoja 			= getParameter("thoja");
		s_nhoja 			= getParameter("nhoja");			 		
		s_iddocumento	 	= getParameter("f_documento");
		s_codigo_personal 	= getParameter("f_codigo_personal");
		s_color 			= getParameter("color");
		s_usuario 			= getParameter("usuario");
		archivo 			= "escaner.jpg";  //"c:\\escaner.jpg"
		baseURL 			= getCodeBase();
		base 				= baseURL.toString(); 
		p					= new Escaner();				  	
	}
	
	public void start(){ 
		boolean config = false;
		if (s_paso.equals("1")){
			if (s_config.equals("1")){
				config = true;
			}
			p.Escanear(archivo,s_thoja,s_color,config); 
			try{
				getAppletContext().showDocument(new URL(base+"addimg.jsp?forigen="+s_origen+"&f_codigo_personal="+s_codigo_personal+"&f_documento="+s_iddocumento+"&Prev=4&nhoja="+s_nhoja+"&thoja="+s_thoja), "_self");
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			boolean ok;
			String paso="0";
			ok = p.upload(archivo,s_codigo_personal,s_iddocumento,s_nhoja,s_origen,s_usuario);
			if (ok) paso = "6";		
			try{
				getAppletContext().showDocument(new URL(base+"addimg.jsp?forigen="+s_origen+"&f_codigo_personal="+s_codigo_personal+"&f_documento="+s_iddocumento+"&Prev="+paso+"&nhoja="+s_nhoja+"&thoja="+s_thoja), "_self");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	void scan(String TamanoHoja,String color,boolean config) throws Exception{			
		Twain twain		= new Twain();
		
		twain.setSelectSource(true);		
		twain.setVisible(config);
		if (color.equals("RGB")) twain.setPixelType(Twain.RGB);	else twain.setPixelType(Twain.GRAY);
		twain.setCurrentUnits(Twain.CENTIMETERS);
		twain.setXResolution(350);twain.setYResolution(350);
		if (TamanoHoja.equals("1")) twain.setFrame(0,0,21.59,27.94); else twain.setFrame(0,0,21.59,35.56);
		twain.setBrightness(-100);
		twain.setContrast(1000);
		twain.setXScaling(1);
		twain.setYScaling(1);
		twain.setAutoBrightness(false);
		twain.setOrientation(Twain.ROT90);
		twain.setShadow(0);
		twain.setHighLight(0);
		
		image=Toolkit.getDefaultToolkit().createImage(twain);
		TwainException twainException= Twain.getLastException();
		if(null!=twainException)
			System.err.println(twainException);	
	}

	public void Escanear(String NombreArchivo, String TamanoHoja,String color, boolean config){
		try{
			scan(TamanoHoja,color,config);
		}catch(Exception e){
			
		}
		save(NombreArchivo);
	}
	
	public void save(String NombreArchivo){ 
		File f = new File(NombreArchivo);
		try{ 
			int w = image.getWidth(null);
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
		}catch (Exception ex){ 
			ex.printStackTrace();
		}
	}	
	
	public boolean upload(String filename, String matricula, String iddoc, String numhoja, String origen, String usuario){
		Connection  conn2 = null;
		Statement   stmt2 = null;
		ResultSet   rset2 = null;
		boolean dev = false;
		String COMANDO = "";
		PreparedStatement pstmt= null;
		try{		
			DriverManager.registerDriver (new org.postgresql.Driver());
			conn2 = DriverManager.getConnection("jdbc:postgresql://172.16.251.11:5432/archivo","postgres","jete17");	
			stmt2=conn2.createStatement();
		}catch (Exception ex){
			ex.printStackTrace();
		};
		
		File file = new File(filename);
		if (file.exists()){	
			long oid=0;
			org.postgresql.largeobject.LargeObjectManager lom = null;
			org.postgresql.largeobject.LargeObject obj = null;
			FileInputStream fis=null;
			
			try{
				conn2.setAutoCommit(false);
				lom = ((org.postgresql.PGConnection)conn2).getLargeObjectAPI();			
				oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
				obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
			}catch (Exception ex){				
				ex.printStackTrace();
			};
			
			try{			
				COMANDO = "SELECT HOJA FROM ENOC.ARCH_DOCALUM WHERE IDDOCUMENTO = '"+iddoc+"' AND MATRICULA = '"+matricula+"' AND HOJA = "+numhoja;			 
				rset2 = stmt2.executeQuery(COMANDO);	
			}catch (Exception ex){
				ex.printStackTrace();
			};
			
			try{			
				if (rset2.next()) {
					COMANDO = "UPDATE ENOC.ARCH_DOCALUM " + 
							"SET IMAGEN = ?," +
							"FUENTE = 'E', " +
							"TIPO = 'U', " +
							"ORIGEN = '"+origen+"', " +
							"F_UPDATE = NOW(), " +
							"USUARIO = '"+usuario+"' "+
							"WHERE IDDOCUMENTO = '"+iddoc+"' " +
							"AND MATRICULA = '"+matricula+"' " +
							"AND HOJA = '"+numhoja+"'";
				}
				else {
					COMANDO = "INSERT INTO ENOC.ARCH_DOCALUM(MATRICULA,IDDOCUMENTO,IMAGEN,HOJA,FUENTE,TIPO,ORIGEN,F_INSERT,F_UPDATE,USUARIO) " + 
							"VALUES ('"+matricula+"','"+iddoc+"',?,"+numhoja+",'E','I','"+origen+"',now(),now(),'"+usuario+"')";
				}
				pstmt = conn2.prepareStatement(COMANDO);
			}catch (Exception ex){
				ex.printStackTrace();
			};
			
			try{
				fis = new FileInputStream(file);
				byte buf[] = new byte[(int)file.length()];
				int le; while ((le=fis.read(buf)) !=-1){obj.write(buf,0,le);}
				pstmt.setLong(1,oid);			
			}catch (Exception ex){
				ex.printStackTrace();
			};
			
			try{
				pstmt.execute();
				conn2.commit();				
				fis.close();
				file.delete();			
			}catch (Exception ex){
				ex.printStackTrace();
			};
			
			dev = true;	
			
			conn2.setAutoCommit(true);
		}
		return dev;
	}	
}