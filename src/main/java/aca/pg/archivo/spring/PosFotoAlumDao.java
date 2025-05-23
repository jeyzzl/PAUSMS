package aca.pg.archivo.spring;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class PosFotoAlumDao {
	
	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;
	
	public boolean insertReg(PosFotoAlum foto) {
		boolean ok 				= false;
		try{
			String comando = "INSERT INTO ALUM_FOTO (CODIGO_PERSONAL,TIPO,FOTO,FECHA,USUARIO,RECHAZADA,COMENTARIO)"
					+ " VALUES(?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				foto.getCodigoPersonal(), foto.getTipo(), foto.getFoto(), foto.getFecha(), foto.getUsuario(),
				foto.getRechazada(), foto.getComentario()
	 		};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|insertReg|:"+ex);	
		}
		
		return ok;
	}
	
	public boolean updateReg(PosFotoAlum foto) {
		boolean ok = false;
		try{
			String comando = "UPDATE ALUM_FOTO"
					+ " SET FOTO = ?,"					
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " USUARIO = ?,"
					+ " RECHAZADA = ?,"
					+ " COMENTARIO = ?"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND TIPO = ?";
			
			Object[] parametros = new Object[] {
				foto.getFoto(),foto.getFecha(),foto.getUsuario(),foto.getRechazada(), 
				foto.getComentario(),foto.getCodigoPersonal(), foto.getTipo()
			};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String matricula, String tipo) {
		boolean ok = false;
		try{
		    String comando = "DELETE FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?";
		    Object[] parametros = new Object[] {matricula, tipo};	
			if (archivoJdbc.update(comando,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public PosFotoAlum mapeaRegId(String matricula, String tipo) {
		PosFotoAlum foto = new PosFotoAlum();
		try{ 
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?"; 			
 			Object[] parametros = new Object[] {matricula, tipo};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT CODIGO_PERSONAL, TIPO, FOTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, RECHAZADA, COMENTARIO"
					+ " FROM ALUM_FOTO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND TIPO = ?";			
				foto = archivoJdbc.queryForObject(comando, new PosFotoAlumMapper(), parametros);
			}							
		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|mapeaRegId|:"+ex);
 		}		
		return foto;
	}
	
	public boolean existeReg(String matricula, String tipo) {
 		boolean ok = false; 		
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?"; 			
 			Object[] parametros = new Object[] {matricula, tipo};
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}	 			
 		}catch(Exception ex){ 			
 			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|existeReg|:"+ex);
 		} 		
 		return ok;
 	}

	public List<String> tieneFoto(String tipo){
		List<String> lista = new ArrayList<String>();		
		try{
			String query = "SELECT CODIGO_PERSONAL FROM ALUM_FOTO WHERE TIPO = ?";
			Object[] parametros = new Object[]{tipo};				
			lista = archivoJdbc.queryForList(query, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|tieneFoto|:"+ex);
		}
		
		return lista;
	}	
	
	public HashMap<String,String> mapaRechazada(String rechazada){
		List<String> lista = new ArrayList<String>();	
		HashMap<String,String> mapa = new HashMap<String,String>();
		
		try{
			String query = "SELECT CODIGO_PERSONAL FROM ALUM_FOTO WHERE RECHAZADA = ?";
			Object[] parametros = new Object[]{rechazada};				
			lista = archivoJdbc.queryForList(query, String.class, parametros);
			
			for(String matricula : lista) {
				mapa.put(matricula, matricula);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|mapaRechazada|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaFoto(){
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();	
		HashMap<String,String> mapa = new HashMap<String,String>();
		
		try{
			String query = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(TIPO) AS VALOR FROM ALUM_FOTO WHERE FOTO IS NOT NULL GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[]{};				
			lista = archivoJdbc.query(query, new aca.MapaMapper());
			
			for(aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|mapaFoto|:"+ex);
		}
		
		return mapa;
	}
	
	public List<PosFotoAlum> lisEmpleados(String tipo){
		
		List<PosFotoAlum> lista 	= new ArrayList<PosFotoAlum>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, TIPO, FECHA, USUARIO RECHAZADA, COMENTARIO FROM ALUM_FOTO WHERE CODIGO_PERSONAL LIKE '9%' AND FOTO IS NOT NULL AND TIPO = ?";
			Object[] parametros = new Object[]{"O"};
			lista = archivoJdbc.query(comando, new PosFotoAlumMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|lisEmpleados|:"+ex);
		}	
		
		return lista;
	}
	
	public byte[] getFotoByte(String codigoPersonal, String tipo){
		
		PosFotoAlum alumFoto	= new PosFotoAlum();
		byte[] fotoByte			= null;	
		
		try{
			String query = "SELECT COUNT(CODIGO_PERSONAL) FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?";
			Object[] parametros = new Object[]{codigoPersonal,tipo};
			if (archivoJdbc.queryForObject(query, Integer.class, parametros) >= 1){				
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO RECHAZADA, COMENTARIO, USUARIO FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?";				
				alumFoto = archivoJdbc.queryForObject(query, new PosFotoAlumMapper(), parametros);				
				fotoByte = alumFoto.getFoto();				
			}else {
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO, RECHAZADA, COMENTARIO, USUARIO FROM ALUM_FOTO WHERE CODIGO_PERSONAL = 'nofoto' AND TIPO = 'O'";				
				alumFoto = archivoJdbc.queryForObject(query, new PosFotoAlumMapper());				
				fotoByte = alumFoto.getFoto();
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|getFotoByte|:"+ex);
		}
		
		return fotoByte;
	}
	
	public String getDimensiones(String codigoPersonal, String tipo){
		
		PosFotoAlum alumFoto	= new PosFotoAlum();
		String dimensiones		= "Width:0,Height:0,X:0,Y:0";
		try{
			String query = "SELECT COUNT(CODIGO_PERSONAL) FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?";
			Object[] parametros = new Object[]{codigoPersonal,tipo};
			if (archivoJdbc.queryForObject(query, Integer.class, parametros) >= 1){				
				// Busca la foto 
				query = "SELECT CODIGO_PERSONAL, TIPO, FOTO, FECHA, USUARIO RECHAZADA, COMENTARIO, USUARIO FROM ALUM_FOTO WHERE CODIGO_PERSONAL = ? AND TIPO = ?";			
				alumFoto = archivoJdbc.queryForObject(query, new PosFotoAlumMapper(), parametros);				
				
				ByteArrayInputStream fotoInput = new ByteArrayInputStream(alumFoto.getFoto());
			    BufferedImage imagenOriginal = ImageIO.read(fotoInput);			    
				//InputStream fotoInput 	= new ByteArrayInputStream(fotoByte);
        		//BufferedImage imagenOriginal 	= ImageIO.read(fotoInput);
			    
				int posX 	= 0;
				int posY 	= 0;
				int largo	= 0;
				boolean horizontal = false;
				if (imagenOriginal.getWidth() > imagenOriginal.getHeight()) { 
					posX 	= (imagenOriginal.getWidth() - imagenOriginal.getHeight()) / 2;
					posY	= 0;
					largo 	= imagenOriginal.getHeight();
					horizontal = true;
				}else if (imagenOriginal.getWidth() < imagenOriginal.getHeight()) {
					posX	= 0; 
					posY 	= (imagenOriginal.getHeight()-imagenOriginal.getWidth()) / 2;
					largo 	= imagenOriginal.getWidth();
				}	
				
				int posIni=0, posFin=0;				
				int mediaPixel;
				Color colorAux;
				
				for( int i = 0; i < imagenOriginal.getWidth(); i++ ){		            
					//Almacenamos el color del píxel
	                colorAux=new Color(imagenOriginal.getRGB(i, imagenOriginal.getHeight()/3));
	                //Calculamos la media de los tres canales (rojo, verde, azul)
	                mediaPixel=(int)((colorAux.getRed()+colorAux.getGreen()+colorAux.getBlue())/3);
	                if (mediaPixel<200 && posIni == 0)
	                	posIni = i;
	                else if (mediaPixel<200)
	                	posFin = i;
		        }
				
				int posTemp = imagenOriginal.getWidth()-posFin;
				int diferencia = 0;
				if (horizontal && posTemp > posIni){
					diferencia = (posTemp-posIni) /2;
					posX = posX-diferencia;
				}else if (horizontal && posTemp < posIni) {
					diferencia = (posIni-posTemp) /2;
					posX = posX+diferencia;
				}
				
				dimensiones = "Width:"+imagenOriginal.getWidth()+",Height:"+imagenOriginal.getHeight()+",X:"+posX+",Y:"+posY;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.pg.archivo.spring.PosFotoAlumDao|getDimensiones|:"+ex);
		}
		
		return dimensiones;
	}
	
}