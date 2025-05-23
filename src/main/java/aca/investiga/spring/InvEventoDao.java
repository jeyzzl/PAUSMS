package aca.investiga.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.investiga.spring.InvEventoDao;

@Component
public class InvEventoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(InvEvento invEvento) throws SQLException{
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INV_EVENTO(CODIGO_PERSONAL, FOLIO, PROYECTO_ID, FECHA_SOLICITUD,"
					+ " FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, PARTICIPA, TIPO_BECA, ALUMNOS, HOSPEDAJE, TRANSPORTE,"
					+ " VIATICOS, GASTOS, DESCRIPCION, ESTADO)"
					+ " VALUES(?, TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99') , ?,"
					+ " TO_NUMBER(?,'99'), TO_NUMBER(?, '99'), ?, TO_NUMBER(?,'999999.99'),TO_NUMBER(?, '999999.99'), TO_NUMBER(?, '999999.99'), TO_NUMBER(?, '999999.99'), ?, ?)"; 
			
			Object[] parametros = new Object[] {
				invEvento.getCodigoPersonal(),invEvento.getFolio(),invEvento.getProyectoId(),invEvento.getFechaSolicitud(),invEvento.getFechaInicio(),
				invEvento.getLugar(),invEvento.getTipoEvento(),invEvento.getDias(),invEvento.getNombreEvento(),invEvento.getParticipa(),
				invEvento.getTipoBeca(),invEvento.getAlumnos(),invEvento.getHospedaje(),invEvento.getTransporte(),invEvento.getViaticos(),
				invEvento.getGastos(),invEvento.getDescripcion(),invEvento.getEstado()
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(InvEvento invEvento) throws SQLException{
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INV_EVENTO SET PROYECTO_ID = ?,"
					+ " FECHA_SOLICITUD = TO_DATE(?, 'DD/MM/YYYY'), "
					+ " FECHA_INICIO = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " LUGAR = ?,"
					+ " TIPO_EVENTO = TO_NUMBER(?,'99'),"
					+ " DIAS = TO_NUMBER(?,'99'),"
					+ " NOMBRE_EVENTO = ?,"
					+ " PARTICIPA = TO_NUMBER(?,'99'),"
					+ " TIPO_BECA = TO_NUMBER(?,'99'),"
					+ " ALUMNOS = ?,"
					+ " HOSPEDAJE = TO_NUMBER(?,'999999.99'),"
					+ " TRANSPORTE = TO_NUMBER(?,'999999.99'),"
					+ " VIATICOS = TO_NUMBER(?,'999999.99'),"
					+ " GASTOS = TO_NUMBER(?,'999999.99'),"
					+ " DESCRIPCION = ?,"
					+ " ESTADO = ?"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')";
			
			Object[] parametros = new Object[] {
				invEvento.getProyectoId(),invEvento.getFechaSolicitud(),invEvento.getFechaInicio(),invEvento.getLugar(),
				invEvento.getTipoEvento(),invEvento.getDias(),invEvento.getNombreEvento(),invEvento.getParticipa(),invEvento.getTipoBeca(),
				invEvento.getAlumnos(),invEvento.getHospedaje(),invEvento.getTransporte(),invEvento.getViaticos(),invEvento.getGastos(),
				invEvento.getDescripcion(),invEvento.getEstado(),invEvento.getCodigoPersonal(),invEvento.getFolio()
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String folio ) throws SQLException{
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')"; 
			
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|deleteReg|:"+ex);
		}

		return ok;
	}	
	
	public InvEvento mapeaRegId(String codigoPersonal, String folio) throws SQLException{
		InvEvento objeto = new InvEvento();
		
		try{ 
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, PROYECTO_ID, TO_CHAR(FECHA_SOLICITUD,'DD/MM/YYYY') AS FECHA_SOLICITUD,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, PARTICIPA, TIPO_BECA, "
					+ " ALUMNOS, HOSPEDAJE, TRANSPORTE, VIATICOS, GASTOS, DESCRIPCION, ESTADO "
					+ " FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')"; 
			
			Object[] parametros = new Object[] { codigoPersonal, folio };
			objeto = enocJdbc.queryForObject(comando,new InvEventoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String folio) throws SQLException{
		boolean ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999') "; 
			
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error -  aca.investiga.spring.InvEventoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateEstado(String folio, String codigoPersonal, String estado ) throws SQLException{
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INV_EVENTO "
					+ " SET ESTADO = ?"					
					+ " WHERE FOLIO = TO_NUMBER(?,'999') "
					+ " AND CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {
				estado,folio,codigoPersonal
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|updateEstado|:"+ex);
		}

		return ok;
	}
	
	public String getAlumnosSol(String codigoPersonal, String folio) throws SQLException{
		String opcion = " ";
		
		try{
			String comando = "SELECT ALUMNOS FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')"; 
			
			Object[] parametros = new Object[] {codigoPersonal, folio};
			opcion = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|getAlumnosSol|:"+ex);
		}
		
		return opcion;
	}
	
	public boolean updateAlumnos(String alumnos, String folio, String codigoPersonal ) throws SQLException{
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INV_EVENTO "+ 
				" SET ALUMNOS = ? "+
				" WHERE  FOLIO = ?"+ 
				" AND CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {alumnos, folio,codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|updateAlumnos|:"+ex);
		}

		return ok;
	}
	
	public String maxReg(String codigoPersonal) throws SQLException{
		String maximo = "1";
		
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {codigoPersonal};
			maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|maxReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getEstado(String folio, String codigoPersonal ) throws SQLException{
		String estado = "-";
		
		try{
			String comando = "SELECT ESTADO FROM ENOC.INV_EVENTO WHERE FOLIO = TO_NUMBER(?,'999') AND CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {folio,codigoPersonal};
			estado = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|getEstado|:"+ex);
		}
		
		return estado;
	}
	

	public List<InvEvento> getListAll(String orden ) throws SQLException{
		List<InvEvento> lista	= new ArrayList<InvEvento>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, PROYECTO_ID, TO_CHAR(FECHA_SOLICITUD, 'DD/MM/YYYY') AS FECHA_SOLICITUD,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, "
					+ " PARTICIPA, TIPO_BECA, ALUMNOS, HOSPEDAJE, TRANSPORTE,"
					+ " VIATICOS, GASTOS, DESCRIPCION, ESTADO FROM ENOC.INV_EVENTO "+orden; 
			
			lista = enocJdbc.query(comando, new InvEventoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<InvEvento> getListEventosEmpleado(String codigoPersonal, String orden ) throws SQLException{
		List<InvEvento> lista = new ArrayList<InvEvento>();
		
		try{			
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, PROYECTO_ID, TO_CHAR(FECHA_SOLICITUD, 'DD/MM/YYYY') AS FECHA_SOLICITUD,"
					+ " TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, PARTICIPA, TIPO_BECA, "
					+ " ALUMNOS, HOSPEDAJE, TRANSPORTE, VIATICOS, GASTOS, DESCRIPCION, ESTADO "
					+ " FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ? "+orden;		
			lista = enocJdbc.query(comando, new InvEventoMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvEventoDao|getListEventosEmpleado|:"+ex);
		}
		
		return lista;
	}
	
	public List<InvEvento> getListProyectosDirectorFac(String facultad, String orden ) throws SQLException{
		List<InvEvento> lista	= new ArrayList<InvEvento>();		
		String comando	= "";		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, PROYECTO_ID, TO_CHAR(FECHA_SOLICITUD, 'DD/MM/YYYY') AS FECHA_SOLICITUD,"
					+ " TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, PARTICIPA, TIPO_BECA,"
					+ " ALUMNOS, HOSPEDAJE, TRANSPORTE, VIATICOS, GASTOS, DESCRIPCION, ESTADO"
					+ " FROM ENOC.INV_EVENTO WHERE PROYECTO_ID IN (SELECT PROYECTO_ID FROM ENOC.INV_PROYECTO WHERE ENOC.FACULTAD(CARRERA_ID) = ?) "+orden;			
			lista = enocJdbc.query(comando, new InvEventoMapper(), facultad);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyectoUtil|getListProyectosDirectorFac|:"+ex);
		}		
		return lista;
	}

}
