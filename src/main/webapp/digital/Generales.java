/**
 * 
 */
package digital;

//import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JTextField;


/**
 * @author ifo
 *
 */
public class Generales extends JApplet {
	private static final long serialVersionUID = 1L;
	
	private Frame frame;
	private String path;
	private String[] listaArchivos;
	private String usuario;
	//private String carpetaText;
	private String recortarText;
	private String subirText;
	private FileDialog fileDialog;
	private JLabel carpetaBox;
	private JLabel recortarBox;
	private JLabel subirBox;
	private int fotosRecortadas;
	private int fotosSubidas;

	public void init(){
		carpetaBox = new JLabel();
		recortarBox = new JLabel();
		subirBox = new JLabel();
		usuario = getParameter("usuario");
		fotosRecortadas = 0;
		fotosSubidas = 0;		
	}
	
	public void start(){		
		JLabel titulo = new JLabel();
		JLabel carpetaText = new JLabel();
		JLabel recortarText = new JLabel();
		JLabel subirText = new JLabel();
		
		Container panel;
		panel = new JPanel();
		setContentPane(panel);
		
		panel.setLayout(new GridLayout(0, 1));
		
		panel.add(titulo);
		titulo.setText("Estado del proceso");
		panel.add(carpetaText);
		carpetaText.setText("1. Elegir Carpeta");
		panel.add(carpetaBox);
		panel.add(recortarText);
		recortarText.setText("2. Recortar fotografias");
		panel.add(recortarBox);
		panel.add(subirText);
		subirText.setText("3. Subir fotografias");
		panel.add(subirBox);
		subirBox.setBackground(new Color(255,0,0));
		repaint();
		/*try {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {*/
	            	iniciar();
	            /*}
	        });
	    } catch (Exception e) {
	        System.err.println("No se pudo iniciar el proceso---");
	    }*/
	}
	
	public void iniciar(){
		System.setProperty("apple.awt.fileDialogForDirectories", "true");
		fileDialog = new FileDialog(frame, "Seleccione una carpeta", FileDialog.LOAD);
		fileDialog.setVisible(true);
		System.setProperty("apple.awt.fileDialogForDirectories", "false");
		
		path = fileDialog.getDirectory();
		if(fileDialog.getFile() != null){
			carpetaBox.setText("     "+path);
			repaint();
		}
		
		if(fileDialog.getFile() != null){
			File dir = new File(path);
			listaArchivos = dir.list();
			if(recortaFotos()){
				recortarBox.setText("     Recortx "+fotosRecortadas+" fotos");
				if(subeFotos()){
					subirBox.setText("     Subix "+fotosSubidas+" fotos");
					try{
						getAppletContext().showDocument(new URL("javascript:alert('Termino de subir las fotos');"));
					}catch(Exception e){e.printStackTrace();}
				}else{
					subirBox.setText("     Ocurrix un error al subir las fotos. Inicie el proceso nuevamente. \nSolo se subirxn las fotos que no se hayan subido");
				}
			}else{
				recortarBox.setText("     Ocurrix un error al recortar las fotos. Inicie el proceso nuevamente. \nSi el error persiste reportelo a sistemas");
			}
		}else{//Termina condicion de si selecciono algun archivo
			try{
				getAppletContext().showDocument(new URL("javascript:alert('No se seleccionx la carpeta');"));
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public boolean recortaFotos(){
		for(int i = 0; i < listaArchivos.length; i++){
			if(listaArchivos[i].substring(listaArchivos[i].length()-3).equals("jpg")){
				System.out.println("     recortando "+(i+1)+" de "+listaArchivos.length+" - "+listaArchivos[i]);
				recortarText = "     "+(i+1)+" de "+listaArchivos.length+" - "+listaArchivos[i];
				recortarBox.setText(recortarText);
				
				try{
					File foto = new File(path+"\\"+listaArchivos[i]);
				    BufferedImage bufferedImage = ImageIO.read(foto);//Imagen orignal
				    int width = bufferedImage.getWidth();
				    int height = bufferedImage.getHeight();
				    int x1 = 0;
				    int y1 = 0;
				    int x2 = 0;
				    int y2 = 0;
				    boolean filaNegra;
				    
				    //--------Parte negra inferior-----------------
				    for(int h = height-1; h >= 0; h--){
				    	filaNegra = true;
				    	for(int w = 0; w < width; w++){
				    		filaNegra &= (bufferedImage.getRGB(w, h) == -16777216);
				    	}
				    	if(filaNegra){
				    		y2 = h-2;
				    	}else
				    		break;
				    }
				    if(y2 == 0)
				    	y2 = height;
				    //-----------------------------
				    
				    //--------Parte negra derecha---------------------------------
				    for(int w = width-1; w >= 0; w--){
				    	filaNegra = true;
				    	for(int h = 0; h < height; h++){
				    		filaNegra &= (bufferedImage.getRGB(w, h) == -16777216);
				    	}
				    	if(filaNegra){
				    		x2 = w-2;
				    	}else
				    		break;
				    }
				    if(x2 == 0)
				    	x2 = width;
					//-----------------------------
					
					foto.delete();
					BufferedImage newBufferedImage = bufferedImage.getSubimage(x1, y1, x2, y2);//Imagen revisada y modificada
					ImageIO.write(newBufferedImage, "jpg", foto);
					fotosRecortadas++;
				}catch(Exception e){
					System.out.println("Error al recortar la foto: "+e);
					try{
						getAppletContext().showDocument(new URL("javascript:alert('Acurrix un error. Corra el proceso nuevamente');"));
					}catch(Exception ex){ex.printStackTrace();}
					return false;
				}//Termina try catch para cortar la foto
			}//Termina condicion de filtro de archivos
		}//Termina for de recorrido de archivos
		return true;
	}
	
	public boolean subeFotos(){
		Connection  conn = null;
		
		conn.setAutoCommit(false);
		
		for(int i = 0; i < listaArchivos.length; i++){
			if(listaArchivos[i].substring(listaArchivos[i].length()-3).equals("jpg")){
				System.out.println("     subiendo "+(i+1)+" de "+listaArchivos.length+" - "+listaArchivos[i]);
				subirText = "     "+(i+1)+" de "+listaArchivos.length+" - "+listaArchivos[i];
				subirBox.setText(subirText);
				try{		
					DriverManager.registerDriver (new org.postgresql.Driver());
					conn=DriverManager.getConnection("jdbc:postgresql://172.16.251.11/archivo","postgres","jete17");
					
				}catch (Exception ex){
					ex.printStackTrace();
				};
				
				try{
					ArchGeneral archGeneral = new ArchGeneral();
					
					archGeneral.setMatricula(listaArchivos[i].substring(0, 7));					
					archGeneral.setFolio(archGeneral.maximoReg(conn));
					
					// Obtiene la fecha
					Calendar date = new GregorianCalendar();					
					String mes = Integer.toString(date.get(Calendar.MONTH)+1);
					if (mes.length() == 1) mes = "0" + mes;					
					String fecha = Integer.toString(date.get(Calendar.DAY_OF_MONTH))+"/"+mes+"/"+Integer.toString(date.get(Calendar.YEAR));
								
					archGeneral.setFecha(fecha);					
					archGeneral.setUsuario(usuario);					
					
					//Subiendo imagen
					org.postgresql.largeobject.LargeObjectManager lom = null;
					org.postgresql.largeobject.LargeObject obj = null;
					FileInputStream fis=null;
					long oid;
					
					lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();			
					oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
					obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
					
					File foto = new File(path+"\\"+listaArchivos[i]);
					fis = new FileInputStream(foto);
					byte buf[] = new byte[(int)foto.length()];
					int le; while ((le=fis.read(buf)) !=-1){obj.write(buf,0,le);}
					
					archGeneral.setImagen(oid);
					//System.out.println("imagen = "+archGeneral.getImagen());
					
					if(archGeneral.insertReg(conn)){
						fotosSubidas++;
						conn.commit();
						fis.close();
						foto.renameTo(new File(path+"\\"+listaArchivos[i].substring(0, listaArchivos[i].length()-3)+"JPG"));
					}else{
						System.out.println("Error al guardar la foto: listaArchivos[i]");
						return false;
					}
					conn.close();
				}catch(Exception e){
					try{
						conn.rollback();
					}catch(Exception ex){
						System.out.println("Error con el rollback: "+ex);
					}
					try{
						getAppletContext().showDocument(new URL("javascript:alert('Acurrix un error. Corra el proceso nuevamente');"));
					}catch(Exception ex){ex.printStackTrace();}
					System.out.println("Error al subir la foto: listaArchivos[i] - "+e);
					return false;
				}finally{//Termina try catch para subir la foto
					conn = null;
				}
			}
		}
		
		conn.setAutoCommit(true);
		
		return true;
	}
	
	public class ArchGeneral {
		private String matricula;
		private String folio;
		private String fecha;
		private String usuario;
		private long imagen;
		
		public ArchGeneral(){
			matricula	= "";
			folio		= "";
			fecha		= "";
			usuario		= "";
			imagen		= -1;
		}

		/**
		 * @return the matricula
		 */
		public String getMatricula() {
			return matricula;
		}

		/**
		 * @param matricula the matricula to set
		 */
		public void setMatricula(String matricula) {
			this.matricula = matricula;
		}

		/**
		 * @return the folio
		 */
		public String getFolio() {
			return folio;
		}

		/**
		 * @param folio the folio to set
		 */
		public void setFolio(String folio) {
			this.folio = folio;
		}

		/**
		 * @return the fecha
		 */
		public String getFecha() {
			return fecha;
		}

		/**
		 * @param fecha the fecha to set
		 */
		public void setFecha(String fecha) {
			this.fecha = fecha;
		}

		/**
		 * @return the usuario
		 */
		public String getUsuario() {
			return usuario;
		}

		/**
		 * @param usuario the usuario to set
		 */
		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		/**
		 * @return the imagen
		 */
		public long getImagen() {
			return imagen;
		}

		/**
		 * @param imagen the imagen to set
		 */
		public void setImagen(long imagen) {
			this.imagen = imagen;
		}
		
		public boolean insertReg(Connection conn ) throws SQLException{
			boolean ok = false;
			PreparedStatement ps = null;
			try{
				ps = conn.prepareStatement("INSERT INTO ARCH_GENERAL(MATRICULA, FOLIO, FECHA, USUARIO, IMAGEN)" +
						" VALUES(?, TO_NUMBER(?,'999'),TO_DATE(?, 'DD/MM/YYYY'),?, ?)");
				
				ps.setString(1, matricula);
				ps.setString(2, folio);
				ps.setString(3, fecha);
				ps.setString(4, usuario);
				ps.setLong(5, imagen);
				
				if ( ps.executeUpdate()== 1){
					ok = true;
					conn.commit();
				}else{
					ok = false;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.archivo.ArchGeneral|insertReg|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
			return ok;
		}
		
		public boolean updateReg(Connection conn ) throws SQLException{
			boolean ok = false;
			PreparedStatement ps = null;
			try{
				ps = conn.prepareStatement("UPDATE ARCH_GENERAL" +
						" SET FECHA = TO_DATE(?, 'DD/MM/YYY')," +
						" USUARIO = ?," +
						" IMAGEN = ?" +
						" WHERE MATRICULA = ?" +
						" AND FOLIO = TO_NUMBER(?,'999')");
				
				ps.setString(1, fecha);
				ps.setString(2, usuario);
				ps.setLong(3, imagen);
				ps.setString(4, matricula);
				ps.setString(5, folio);
				
				if ( ps.executeUpdate()== 1){
					ok = true;
					conn.commit();
				}else{
					ok = false;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.archivo.ArchGeneral|updateReg|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
			return ok;
		}
		
		public boolean deleteReg(Connection conn ) throws SQLException{
			boolean ok = false;
			PreparedStatement ps = null;
			try{
				ps = conn.prepareStatement("DELETE FROM ARCH_GENERAL" +
						" WHERE MATRICULA = ?" +
						" AND FOLIO = TO_NUMBER(?,'999')");
				
				ps.setString(1, matricula);
				ps.setString(2, folio);
				
				if ( ps.executeUpdate()== 1){
					ok = true;
					conn.commit();
				}else{
					ok = false;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.archivo.ArchGeneral|deleteReg|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
			return ok;
		}
		
		public void mapeaReg(ResultSet rs ) throws SQLException{
			matricula	= rs.getString("MATRICULA");
			folio		= rs.getString("FOLIO");
			fecha		= rs.getString("FECHA");
			usuario		= rs.getString("USUARIO");
			imagen		= rs.getInt("IMAGEN");
		}
		
		public void mapeaRegId(Connection con, String IdDocumento) throws SQLException{
			PreparedStatement ps = null;
			ResultSet rs = null;		
			try{
				ps = con.prepareStatement("SELECT MATRICULA, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY'), USUARIO, IMAGEN" +				
						" FROM ARCH_GENERAL" +
						" WHERE MATRICULA = ?" +
						" AND FOLIO = TO_NUMBER(?,'999')");
				
				ps.setString(1, matricula);
				ps.setString(2, folio);
				
				rs = ps.executeQuery();
				
				if(rs.next()){
					mapeaReg(rs);
				}
			}catch(Exception ex){
				System.out.println("Error - aca.digital.Generales|existeReg|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { ps.close(); } catch (Exception ignore) { }
			}
		}
		
		public boolean existeReg(Connection conn) throws SQLException{
			boolean ok 			= false;
			ResultSet rs 			= null;
			PreparedStatement ps	= null;
			
			try{
				ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_DOCUMENTOS" + 
						" WHERE MATRICULA = ?" +
						" AND FOLIO = TO_NUMBER(?,'999') ");
				
				ps.setString(1, matricula);
				ps.setString(2, folio);
				
				rs= ps.executeQuery();		
				if(rs.next()){
					ok = true;
				}else{
					ok = false;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.digital.Generales|existeReg|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { ps.close(); } catch (Exception ignore) { }
			}
			
			return ok;
		}
		
		public String maximoReg(Connection conn) throws SQLException{
			String maximo 			= "1";
			ResultSet rs			= null;
			PreparedStatement ps	= null;
			
			try{
				ps = conn.prepareStatement("SELECT (MAX(FOLIO)+1) AS MAXIMO FROM ARCH_GENERAL" +
						" WHERE MATRICULA = ?");
				
				ps.setString(1, matricula);
				
				rs = ps.executeQuery();
				if (rs.next())
					maximo = rs.getString("MAXIMO");
				if(maximo == null)
					maximo = "1";
				
			}catch(Exception ex){
				System.out.println("Error - aca.archivo.ArchGeneral|maximoReg|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { ps.close(); } catch (Exception ignore) { }
			}
			
			return maximo;
		}
	}
}