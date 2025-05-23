package aca.portafolio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public class InfoEncPuesto {
	
	public Map<String,String> getJefes(Connection con, String idejercicio){
		Map<String, String> salida = new HashMap<String, String>();
		try{
			PreparedStatement pst = con.prepareStatement("SELECT J.CCOSTO_ID,  E.NOMBRE || ' ' || E.APPATERNO || ' ' || E.APMATERNO AS NOMBRE"
					+ " FROM ARON.CAT_JEFES J "
					+ "INNER JOIN ARON.EMPLEADO E ON J.JEFE_ID=E.ID "
					+ "WHERE j.EJERCICIO_ID='"+ idejercicio +"' and j.status='A'");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				salida.put(rs.getString("ccosto_id"), rs.getString("nombre"));
			}
			rs.close();
			pst.close();
			
		}catch(SQLException sqle){
			System.err.println("Error en getJefes " + sqle );
		}
		return salida;
		
	}

	public List<PuestoEnc> getEncabezado(Connection con , String claveempleado, String idejercicio){
		List<PuestoEnc> salida = new ArrayList<PuestoEnc>();
		try{
			Map<String, String> mapJefes = new HashMap<String, String>();
			mapJefes.putAll(getJefes(con, idejercicio));
			
			PreparedStatement pst = con.prepareStatement("select  "
					+ "e.id"
					+ ", e.clave"
					+ ",  ep.id_ccosto"
					+ ", cc.NOMBRE as departamento"
					+ ", ep.PUESTO_ID"
					+ ",  p.DESCRIPCION as puesto"
					+ ",  ca.NOMBRE as categoria"
					+ ",  el.escalafon as escalafon"
					+ ", g.NOMBRE as grupo "
					+ "from aron.empleado e "
					+ " inner join aron.empleadolaborales el ON e.id=el.ID "
					+ " inner join ARON.EMPLEADO_PUESTOS ep ON e.id=ep.empleado_id and ep.id_ejercicio='"+ idejercicio +"' and ep.status='A' "
					+ " LEFT join ARON.SECCION p ON ep.PUESTO_ID=p.ID "
					+ " left join aron.categoria ca ON p.CATEGORIA_ID=ca.id "
					+ " left join aron.grupo g on el.ID_GRUPO=g.id  "
					+ " left join mateo.cont_ccosto cc on ep.id_ccosto=cc.ID_CCOSTO and cc.ID_EJERCICIO='"+ idejercicio +"'  "
					+ " where e.STATUS='A'"
					+ " and clave='"+ claveempleado +"'");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				PuestoEnc pe = new PuestoEnc();
				pe.setDependenciaAdministrativa(rs.getString("categoria"));
				pe.setDireccionDepartamento(rs.getString("departamento"));
				pe.setGrupoSalarial(rs.getString("grupo"));
				pe.setRangoEscalafon(rs.getString("escalafon"));
				pe.setTituloPuesto(rs.getString("puesto"));
				pe.setPuesto_id(rs.getInt("PUESTO_ID"));
				pe.setCcosto(rs.getString("id_ccosto"));
				if(mapJefes.containsKey(rs.getString("id_ccosto"))){
					pe.setJefeInmediato(mapJefes.get(rs.getString("id_ccosto")));
				}
				salida.add(pe);
			}
			rs.close();
			pst.close();
			
			
		}catch(SQLException sqle ){
			System.err.println("Error en getEncabezado " + sqle );
		}
		return salida;
	}
	
	public List<PorPuesto> getPuesto(Connection con, String numnomina, String departamento, String puesto_id){
		List<PorPuesto> salida = new ArrayList<PorPuesto>();
		try{
			
			String sql = "SELECT "
					+ "id, 	fecha_agregado, 	departamento, 	"
					+ "puesto_id, 	numnomina, 	objetivo_puesto, 	"
					+ "juntas_preside, 	juntas_pertenece, 	"
					+ "personal_dependiente, 	deberes_repe, 	"
					+ "deberes_peri, 	deberes_irre, 	"
					+ "supervision, 	relaciones_inte, 	"
					+ "relaciones_exte, 	estandares_dese, 	"
					+ "escolaridad, 	destreza, 	responsabilidad, 	"
					+ "ergonomia, 	ambiente_trabajo, 	riesgo, 	"
					+ "esfuerzo  "
					+ "FROM DANIEL.PORTAFOLIO_PUESTO WHERE id<>0 ";
			
			if(!numnomina.equals("")){
				sql += " and numnomina='"+ numnomina +"' ";
			}
			if(!departamento.equals("")){
				sql += " and departamento='"+ departamento +"' ";
			}
			if(!puesto_id.equals("")){
				sql += " and puesto_id="+ puesto_id +" ";
			}
			
			
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				PorPuesto pp = new PorPuesto();
				pp.setAmbiente_trabajo(rs.getString("ambiente_trabajo")!=null && !rs.getString("ambiente_trabajo").equals("null") ? rs.getString("ambiente_trabajo") : "");
				pp.setDeberes_irre(rs.getString("deberes_irre")!=null && !rs.getString("deberes_irre").equals("null") ? rs.getString("deberes_irre") : "");
				pp.setDeberes_peri(rs.getString("deberes_peri")!=null && !rs.getString("deberes_peri").equals("null") ? rs.getString("deberes_peri") : "");
				pp.setDeberes_repe(rs.getString("deberes_repe")!=null && !rs.getString("deberes_repe").equals("null") ? rs.getString("deberes_repe") : "");
				pp.setDepartamento(rs.getString("departamento"));
				pp.setDestreza(rs.getString("destreza")!=null && !rs.getString("destreza").equals("null") ? rs.getString("destreza") : "");
				pp.setErgonomia(rs.getString("ergonomia")!=null && !rs.getString("ergonomia").equals("null") ? rs.getString("ergonomia") : "");
				pp.setEscolaridad(rs.getString("escolaridad")!=null && !rs.getString("escolaridad").equals("null") ? rs.getString("escolaridad") : "");
				pp.setEsfuerzo(rs.getString("esfuerzo")!=null && !rs.getString("esfuerzo").equals("null") ? rs.getString("esfuerzo") : "");
				pp.setEstandares_dese(rs.getString("ESTANDARES_DESE")!=null && !rs.getString("ESTANDARES_DESE").equals("null") ? rs.getString("ESTANDARES_DESE") : "");
				pp.setFecha_agregado(rs.getString("fecha_agregado"));
				pp.setId(rs.getInt("id"));
				pp.setJuntas_pertenece(rs.getString("juntas_pertenece")!=null && !rs.getString("juntas_pertenece").equals("null") ? rs.getString("juntas_pertenece") : "");
				pp.setJuntas_preside(rs.getString("juntas_preside")!=null && !rs.getString("juntas_preside").equals("null") ? rs.getString("juntas_preside") : "");
				pp.setNumnomina(rs.getString("numnomina"));
				pp.setObjetivo_puesto(rs.getString("objetivo_puesto")!=null && !rs.getString("objetivo_puesto").equals("null") ? rs.getString("objetivo_puesto") : "");
				pp.setPersonal_dependiente(rs.getString("personal_dependiente")!=null && !rs.getString("personal_dependiente").equals("null") ? rs.getString("personal_dependiente") : "");
				pp.setPuesto_id(rs.getInt("puesto_id"));
				pp.setRelaciones_exte(rs.getString("relaciones_exte")!=null && !rs.getString("relaciones_exte").equals("null") ? rs.getString("relaciones_exte") : "");
				pp.setRelaciones_inte(rs.getString("relaciones_inte")!=null && !rs.getString("relaciones_inte").equals("null") ? rs.getString("relaciones_inte") : "");
				pp.setResponsabilidad(rs.getString("relaciones_inte")!=null && !rs.getString("relaciones_inte").equals("null") ? rs.getString("responsabilidad") : "");
				pp.setRiesgo(rs.getString("riesgo")!=null && !rs.getString("riesgo").equals("null") ? rs.getString("riesgo") : "");
				pp.setSupervision(rs.getString("supervision")!=null && !rs.getString("supervision").equals("null") ? rs.getString("supervision") : "");
				
				salida.add(pp);
				
			}
			rs.close();
			pst.close();
		}catch(SQLException sqle){
			System.err.println("error en get puesto " + sqle);
		}
		return salida;
	}
	
	private Integer getMaxId(Connection con){
		Integer salida = 0;
		try{
			PreparedStatement pst = con.prepareStatement("select max(id) as max from daniel.portafolio_puesto");
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()){
				salida = rs.getInt("max") + 1;
			}
		}catch(SQLException sqle){
			System.err.println("error en maxid " + sqle);
		}
		return salida;
	}
	
	public void updDescPuesto(Connection con, HttpServletRequest request){
		try{
			
			
			
			String sql = "UPDATE DANIEL.PORTAFOLIO_PUESTO SET "
		    		+ "fecha_agregado=current_date, "
					+ "objetivo_puesto=?, 	juntas_preside=?, 	"
					+ "juntas_pertenece=?, 	personal_dependiente=?, 	deberes_repe=?, 	"
					+ "deberes_peri=?, 	deberes_irre=?, 	supervision=?, 	"
					+ "relaciones_inte=?, 	relaciones_exte=?, 	"
					+ "escolaridad=?, 	destreza=?, 	responsabilidad=?, 	"
					+ "ergonomia=?, 	ambiente_trabajo=?, 	riesgo=?, 	esfuerzo=?, estandares_dese=?"
					+ " where id=? and numnomina=?  and puesto_id=? and departamento=? ";
			
			PreparedStatement pst = con.prepareStatement(sql);
			
			
			pst.setInt(19, Integer.valueOf(request.getParameter("id")));
			pst.setString(22, request.getParameter("departamento"));
			pst.setInt(21, Integer.valueOf(request.getParameter("puesto_id")));
			pst.setString(20, request.getParameter("numnomina"));
			
			pst.setString(1, request.getParameter("objetivo_puesto"));
			pst.setString(2, request.getParameter("juntas_preside"));
			pst.setString(3, request.getParameter("juntas_pertenece"));
			pst.setString(4, request.getParameter("personal_dependiente"));
			pst.setString(5, request.getParameter("deberes_repe"));
			pst.setString(6, request.getParameter("deberes_peri"));
			pst.setString(7, request.getParameter("deberes_irre"));
			pst.setString(8, request.getParameter("supervision"));
			pst.setString(9, request.getParameter("relaciones_inte"));
			pst.setString(10, request.getParameter("relaciones_exte"));
			
			pst.setString(11, request.getParameter("escolaridad"));
			pst.setString(12, request.getParameter("destreza"));
			pst.setString(13, request.getParameter("responsabilidad"));
			pst.setString(14, request.getParameter("ergonomia"));
			pst.setString(15, request.getParameter("ambiente_trabajo"));
			pst.setString(16, request.getParameter("riesgo"));
			pst.setString(17, request.getParameter("esfuerzo"));
			pst.setString(18, request.getParameter("estandares_dese"));
			
			
			
			pst.executeUpdate();
			
		}catch(SQLException sqle){
			System.err.println("Error en addDescPuesto" + sqle);
		}
		
		
	}
	
	public void addDescPuesto(Connection con, HttpServletRequest request){
		try{
			String sql = "INSERT INTO DANIEL.PORTAFOLIO_PUESTO(id, 	fecha_agregado, 	departamento, 	"
					+ "puesto_id, 	numnomina, 	objetivo_puesto, 	juntas_preside, 	"
					+ "juntas_pertenece, 	personal_dependiente, 	deberes_repe, 	"
					+ "deberes_peri, 	deberes_irre, 	supervision, 	"
					+ "relaciones_inte, 	relaciones_exte, 	estandares_dese, 	"
					+ "escolaridad, 	destreza, 	responsabilidad, 	"
					+ "ergonomia, 	ambiente_trabajo, 	riesgo, 	esfuerzo) "
					+ "VALUES(?,current_date,?,"
					+ "?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,?) ";
			PreparedStatement pst = con.prepareStatement(sql);
			
			System.err.println("probando juntas que pertence " + request.getParameter("juntas_pertenece")!=null ? request.getParameter("juntas_pertenece") : "");
			
			
			pst.setInt(1, getMaxId(con));
			pst.setString(2, request.getParameter("departamento"));
			pst.setInt(3, Integer.valueOf(request.getParameter("puesto_id")));
			pst.setString(4, request.getParameter("numnomina"));
			pst.setString(5, request.getParameter("objetivo_puesto")!=null ? request.getParameter("objetivo_puesto") : "");
			pst.setString(6, request.getParameter("juntas_preside")!=null ? request.getParameter("juntas_preside") : "");
			pst.setString(7, request.getParameter("juntas_pertenece")!=null ? request.getParameter("juntas_pertenece") : "");
			pst.setString(8, request.getParameter("personal_dependiente")!=null ? request.getParameter("personal_dependiente") : "");
			pst.setString(9, request.getParameter("deberes_repe")!=null ? request.getParameter("deberes_repe") : "");
			pst.setString(10, request.getParameter("deberes_peri")!=null ? request.getParameter("deberes_peri") : "");
			pst.setString(11, request.getParameter("deberes_irre")!=null ? request.getParameter("deberes_irre") : "");
			pst.setString(12, request.getParameter("supervision")!=null ? request.getParameter("supervision") : "");
			pst.setString(13, request.getParameter("relaciones_inte")!=null ? request.getParameter("relaciones_inte") : "");
			pst.setString(14, request.getParameter("relaciones_exte")!=null ? request.getParameter("relaciones_exte") : "");
			pst.setString(15, request.getParameter("estandares_dese")!=null ? request.getParameter("estandares_dese") : "");
			pst.setString(16, request.getParameter("escolaridad")!=null ? request.getParameter("escolaridad") : "");
			pst.setString(17, request.getParameter("destreza")!=null ? request.getParameter("destreza") : "");
			pst.setString(18, request.getParameter("responsabilidad")!=null ? request.getParameter("responsabilidad") : "");
			pst.setString(19, request.getParameter("ergonomia")!=null ? request.getParameter("ergonomia") : "");
			pst.setString(20, request.getParameter("ambiente_trabajo")!=null ? request.getParameter("ambiente_trabajo") : "");
			pst.setString(21, request.getParameter("riesgo")!=null ? request.getParameter("riesgo") : "");
			pst.setString(22, request.getParameter("esfuerzo")!=null ? request.getParameter("esfuerzo") : "");
			
			pst.executeUpdate();
			
		}catch(SQLException sqle){
			System.err.println("Error en addDescPuesto" + sqle);
		}
		
		
	}
	
	public Map<Integer, String> getPuestosReferencias(Connection con){
		Map<Integer,String> salida = new HashMap<Integer,String>();
		try{
			PreparedStatement pst = con.prepareStatement("select * from daniel.puestos where activo=1");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				salida.put(rs.getInt("id"), rs.getString("titulopuesto"));
			}
			rs.close();
			pst.close();
			
		}catch(SQLException sqle){
			System.out.println("error en getPuestosReferencias " + sqle);
		}
		return salida;
	}
	
	
	
	public PuestoReferencia getReferencia(Connection con, Integer idpuesto) throws SQLException{
		PuestoReferencia salida = null;
		try{
			PreparedStatement pst = con.prepareStatement("select * from daniel.puestos where id=?");
			pst.setInt(1, idpuesto);
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				salida = new PuestoReferencia();
				salida.setId(rs.getInt("id"));
				salida.setTitulopuesto(rs.getString("TITULOPUESTO")!=null && !rs.getString("TITULOPUESTO").equals("null") ? rs.getString("TITULOPUESTO") : "");
				salida.setDepartamento(rs.getString("DEPARTAMENTO")!=null && !rs.getString("DEPARTAMENTO").equals("null") ? rs.getString("DEPARTAMENTO") : "");
				salida.setJefeinmediato(rs.getString("JEFEINMEDIATO")!=null && !rs.getString("JEFEINMEDIATO").equals("null") ? rs.getString("JEFEINMEDIATO") : "");
				salida.setHorario(rs.getString("HORARIO")!=null && !rs.getString("HORARIO").equals("null") ? rs.getString("HORARIO") : "");
				salida.setFechamodificacion(rs.getString("FECHAMODIFICACION")!=null && !rs.getString("FECHAMODIFICACION").equals("null") ? rs.getString("FECHAMODIFICACION") : "");
				salida.setObjetivopuesto(rs.getString("OBJETIVOPUESTO")!=null && !rs.getString("OBJETIVOPUESTO").equals("null") ? rs.getString("OBJETIVOPUESTO") : "");
				salida.setDestreza(rs.getString("DESTREZA")!=null && !rs.getString("DESTREZA").equals("null") ? rs.getString("DESTREZA") : "");
				salida.setResponsabilidad(rs.getString("RESPONSABILIDAD")!=null && !rs.getString("RESPONSABILIDAD").equals("null") ? rs.getString("RESPONSABILIDAD") : "");
				salida.setAmbientetrabajo(rs.getString("AMBIENTETRABAJO")!=null && !rs.getString("AMBIENTETRABAJO").equals("null") ? rs.getString("AMBIENTETRABAJO") : "");
				salida.setRiesgo(rs.getString("RIESGO")!=null && !rs.getString("RIESGO").equals("null") ? rs.getString("RIESGO") : "");
				salida.setEsfuerzo(rs.getString("ESFUERZO")!=null && !rs.getString("ESFUERZO").equals("null") ? rs.getString("ESFUERZO") : "");
				salida.setDeberesrepetitivos(rs.getString("DEBERESREPETITIVOS")!=null && !rs.getString("DEBERESREPETITIVOS").equals("null") ? rs.getString("DEBERESREPETITIVOS") : "");
				salida.setDeberesperiodicos(rs.getString("DEBERESPERIODICOS")!=null && !rs.getString("DEBERESPERIODICOS").equals("null") ? rs.getString("DEBERESPERIODICOS") : "");
				salida.setDeberesirregulares(rs.getString("DEBERESIRREGULARES")!=null && !rs.getString("DEBERESIRREGULARES").equals("null") ? rs.getString("DEBERESIRREGULARES") : "");
				salida.setSupervisa(rs.getString("SUPERVISA")!=null && !rs.getString("SUPERVISA").equals("null") ? rs.getString("SUPERVISA") : "");
				salida.setRelacionesinternas(rs.getString("RELACIONESEXTERNAS")!=null && !rs.getString("RELACIONESEXTERNAS").equals("null") ? rs.getString("RELACIONESEXTERNAS") : "");
				salida.setRelacionesexternas(rs.getString("RELACIONESINTERNAS")!=null && !rs.getString("RELACIONESINTERNAS").equals("null") ? rs.getString("RELACIONESINTERNAS") : "");
				
			}
			rs.close();
			pst.close();
		}catch(SQLException sqle){
			System.out.println("error en getReferencia" + sqle);
		}
		return salida;
	}
	
}
