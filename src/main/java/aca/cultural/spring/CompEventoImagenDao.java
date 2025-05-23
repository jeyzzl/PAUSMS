package aca.cultural.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CompEventoImagenDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public CompEventoImagen mapeaRegId( String eventoId, String imagenId) {
		CompEventoImagen imagen = new CompEventoImagen();

		try{
		String comando = "SELECT"+
			" EVENTO_ID, IMAGEN_ID, DESCRIPCION, IMAGEN"+
			" FROM ENOC.COMP_EVENTO_IMAGEN"+ 
			" WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ";
		
		Object[] parametros = new Object[] {eventoId, imagenId};
		imagen = enocJdbc.queryForObject(comando, new CompEventoImagenMapper(), parametros);

		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|mapeaRegId|:"+ex);
		}
		return imagen;
	}
	
	public boolean insertRegByte( CompEventoImagen compEventoImagen) {
		boolean ok 				= false;
		
		try{
			String comando = "INSERT INTO ENOC.COMP_EVENTO_IMAGEN"+ 
				"(EVENTO_ID, IMAGEN_ID, DESCRIPCION, IMAGEN) "+
				"VALUES( ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {
					compEventoImagen.getEventoId(), compEventoImagen.getImagenId(), compEventoImagen.getDescripcion(), 
					compEventoImagen.getImagen()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|insertRegByte|:"+ex);			
		}
		return ok;
	}

	public boolean updateReg( CompEventoImagen compEventoImagen ) {
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.COMP_EVENTO_IMAGEN "+ 
				" SET DESCRIPCION = ?, "+
				" IMAGEN = ? "+
				" WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ";
			
			Object[] parametros = new Object[] {
					compEventoImagen.getDescripcion(), compEventoImagen.getImagen(), compEventoImagen.getEventoId(), 
					compEventoImagen.getImagenId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|updateReg|:"+ex);		
		}
		return ok;
	}

	public boolean deleteReg( String eventoId, String imagenId ) {
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.COMP_EVENTO_IMAGEN "+ 
				"WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ";
			
			Object[] parametros = new Object[] {eventoId, imagenId};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteImagen( String eventoId ) {
		boolean ok 				= false;
		
		try{
			String comando = "DELETE FROM ENOC.COMP_EVENTO_IMAGEN "+ 
				"WHERE EVENTO_ID = ?";
			
			Object[] parametros = new Object[] {eventoId};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|deleteImagen|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteImagenTodo(String eventos ) {
		
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.COMP_EVENTO_IMAGEN "+ 
				"WHERE EVENTO_ID IN (?)";
			
			Object[] parametros = new Object[] {eventos};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|deleteImagenTodo|:"+ex);			
		}
		return ok;
	}

	public boolean existeReg( String eventoId) {
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COMP_EVENTO_IMAGEN "+ 
				"WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ";
			
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg( String eventoId, String imagenId) {
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COMP_EVENTO_IMAGEN "+ 
				"WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ";
			
			Object[] parametros = new Object[] {eventoId, imagenId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String eventoId) {
 		String 		maximo 		= "1";
 		
 		try{
 			String comando = "SELECT MAX(IMAGEN_ID)+1 AS MAXIMO FROM ENOC.COMP_EVENTO_IMAGEN "+ 
 				" WHERE EVENTO_ID = ?";
 			
 			Object[] parametros = new Object[] {eventoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}

 		}catch(Exception ex){
 			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|maximoReg|:"+ex);
 		}
 		return maximo;
 	}

	public List<CompEventoImagen> getListAll( String orden ) {
		List<CompEventoImagen> list 	= new ArrayList<CompEventoImagen>();
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.COMP_EVENTO_IMAGEN "+orden;
			
			list = enocJdbc.query(comando, new CompEventoImagenMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|getListAll|:"+ex);
		}		
		return list;
	}
		
	
	public List<CompEventoImagen> getImagenEvento( String eventoId, String orden ) {
		List<CompEventoImagen> list 	= new ArrayList<CompEventoImagen>();
		String comando					= "";		
		try{
			comando = "SELECT * FROM ENOC.COMP_EVENTO_IMAGEN WHERE EVENTO_ID = ? "+orden;			
			list = enocJdbc.query(comando, new CompEventoImagenMapper(), eventoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|getImagenEvento|:"+ex);
		}
		return list;
	}

	public List<CompEventoImagen> imagenesParaManana( String orden ) {
		List<CompEventoImagen> list 	= new ArrayList<CompEventoImagen>();
		String comando					= "";
		
		try{
			comando = " SELECT * FROM ENOC.COMP_EVENTO_IMAGEN"+
					  " WHERE EVENTO_ID IN"+
					  " (SELECT EVENTO_ID FROM ENOC.COMP_EVENTO WHERE FECHA >= TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') AND ESTADO = '1')"+orden;
			
			list = enocJdbc.query(comando, new CompEventoImagenMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.spring.CompEventoImagenDao|imagenesParaMañana|:"+ex);
		}
		return list;
	}
}