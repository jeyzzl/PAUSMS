package aca.objeto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Horario {
	private String cursoCargaId;
	private String turno;	
	private String cursoId;
	private String dia;
	private String periodo;
	private String inicio;
	private String fin;
	private String facultadId;
	private String empleadoId;
		
	public Horario(){
		cursoCargaId			= "";
		turno					= "";
		cursoId					= "";
		dia						= "";
		periodo					= "";
		inicio					= "";
		fin						= "";
		facultadId				= "";
		empleadoId				= "";
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getEmpleadoId() {
		return empleadoId;
	}

	public void setEmpleadoId(String empleadoId) {
		this.empleadoId = empleadoId;
	}
	
/*
	public static ArrayList<Horario> getListaHorariosPorAlumno(Connection conn, String matricula, String bloqueId, String cargaId, boolean inscripcion) throws SQLException{
		ArrayList<Horario> listaHorarios = new ArrayList<Horario>();

		ArrayList<aca.vista.AlumnoCurso> lisCursos = new aca.vista.AlumnoCursoUtil().getListAlumnoCarga(conn, matricula, cargaId, (inscripcion?"":"AND TIPOCAL_ID IN ('I') ")+" AND (BLOQUE_ID IN ("+bloqueId+")) ORDER BY NOMBRE_CURSO");
	
		HashMap<String, aca.catalogo.CatHorarioFac> mapaHorarioFacultad = new aca.catalogo.CatHorarioFacUtil().getMapHoras(conn, "");
		HashMap<String, String> mapaFacultadIdPorCursoCargaId = aca.carga.CargaGrupoUtil.getMapaFacultadIdPorCursoCargaId(conn, matricula, cargaId);
				
		for(aca.vista.AlumnoCurso alumnoCurso : lisCursos){
			String facultadIdAsignada = mapaFacultadIdPorCursoCargaId.get(alumnoCurso.getCursoCargaId());
			String horarioAsignado = alumnoCurso.getHorario();
			
			// Busca las posiciones ocupadas en el horario de todas las materias asignadas
			ArrayList<Integer> posiciones = new ArrayList<Integer>();
			while(horarioAsignado.contains("1")){
				posiciones.add(horarioAsignado.indexOf("1")+1);
				horarioAsignado = horarioAsignado.replaceFirst("1", "0");
			}
			// Llena la lista de horarios de todas las materias asignadas
			for(int num : posiciones){
				int periodo = (num/7)+1;
				int dia = num % 7;
				if(dia==0) dia = 7;
				int tur = num/70;
				String turno = "M";
				if(tur==1) turno = "V";
				else if(tur==2) turno = "N";
				aca.catalogo.CatHorarioFac horarioFac = mapaHorarioFacultad.get(facultadIdAsignada+periodo);
				if(horarioFac!=null){
					aca.objeto.Horario horario = new aca.objeto.Horario();
					horario.setCursoCargaId(alumnoCurso.getCursoCargaId());
					horario.setDia(dia+"");
					horario.setInicio(dia+horarioFac.getHoraIni());
					horario.setFin(dia+horarioFac.getHoraFin());
					horario.setPeriodo(periodo+"");
					horario.setTurno(turno);
					horario.setCursoId(alumnoCurso.getNombreCurso()); //SE UTILIZx EL CURSO_ID COMO NOMBRE DE LA MATERIA
					horario.setFacultadId(facultadIdAsignada);
					horario.setEmpleadoId(alumnoCurso.getMaestro());
					listaHorarios.add(horario);
				}
			}
		}
		java.util.Collections.sort(listaHorarios, new aca.util.Ordenar().getOrdenarHorarios());
		return listaHorarios;
	}
*/	
	public static ArrayList<Horario> getListaHorariosPorMaestro(Connection conn, String codigoEmpleado, String bloqueId, String cargaId) throws SQLException{
		ArrayList<Horario> listaHorarios = new ArrayList<Horario>();

		ArrayList<aca.carga.CargaGrupo> lisCursos = new aca.carga.CargaGrupoUtil().getListaCarga(conn, cargaId, "AND CODIGO_PERSONAL='"+codigoEmpleado+"' AND BLOQUE_ID='"+bloqueId+"'");
	
		HashMap<String, aca.catalogo.CatHorarioFac> mapaHorarioFacultad = new aca.catalogo.CatHorarioFacUtil().getMapHoras(conn, "");
		HashMap<String, String> mapaFacultadIdPorCursoCargaId = aca.carga.CargaGrupoUtil.getMapaFacultadIdPorCursoCargaIdMaestros(conn, codigoEmpleado, cargaId);
				
		for(aca.carga.CargaGrupo cargaGrupo : lisCursos){
			String facultadIdAsignada = mapaFacultadIdPorCursoCargaId.get(cargaGrupo.getCursoCargaId());
			String horarioAsignado = cargaGrupo.getHorario();
			
			// Busca las posiciones ocupadas en el horario de todas las materias asignadas
			ArrayList<Integer> posiciones = new ArrayList<Integer>();
			while(horarioAsignado.contains("1")){
				posiciones.add(horarioAsignado.indexOf("1")+1);
				horarioAsignado = horarioAsignado.replaceFirst("1", "0");
			}
			// Llena la lista de horarios de todas las materias asignadas
			for(int num : posiciones){
				int periodo = (num/7)+1;
				int dia = num % 7;
				int tur = num/70;
				String turno = "M";
				if(tur==1) turno = "V";
				else if(tur==2) turno = "N";
				aca.catalogo.CatHorarioFac horarioFac = mapaHorarioFacultad.get(facultadIdAsignada+periodo);
				if(horarioFac!=null){
					aca.objeto.Horario horario = new aca.objeto.Horario();
					horario.setCursoCargaId(cargaGrupo.getCursoCargaId());
					horario.setDia(dia+"");
					horario.setInicio(dia+horarioFac.getHoraIni());
					horario.setFin(dia+horarioFac.getHoraFin());
					horario.setPeriodo(periodo+"");
					horario.setTurno(turno);
					horario.setCursoId(aca.vista.CargaAcademica.getNombreMateria(conn, cargaGrupo.getCursoCargaId())); //SE UTILIZx EL CURSO_ID COMO NOMBRE DE LA MATERIA
					horario.setFacultadId(facultadIdAsignada);
					horario.setEmpleadoId(cargaGrupo.getCodigoPersonal());
					listaHorarios.add(horario);
				}
			}
		}
		java.util.Collections.sort(listaHorarios, new aca.util.Ordenar().getOrdenarHorarios());
		return listaHorarios;
	}
	
	public static HashMap<String, String> getMapaTurnosPorAlumnoInscritos(Connection conn, String cargasId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String tmpMatricula 	= "";
	
		try{
			String turno = "";
			boolean tieneM = false;
			boolean tieneV = false;
			int cont = 0;
			
			comando = "SELECT CODIGO_PERSONAL," +
					" CASE" +
					" 	WHEN INSTR(SUBSTR(HORARIO,1,70), '1', 1, 1)!=0 AND INSTR(SUBSTR(HORARIO,71,140), '1', 1, 1)!=0" +
					"		THEN 'M - V'" +
					"	WHEN INSTR(SUBSTR(HORARIO,1,70), '1', 1, 1)!=0" +
					"		THEN 'M'" +
					"	WHEN INSTR(SUBSTR(HORARIO,71,140), '1', 1, 1)!=0" +
					"		THEN 'V'" +
					" END AS TURNO" +
					" FROM ENOC.ALUMNO_CURSO WHERE CARGA_ID IN ("+cargasId+") AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" +
					" ORDER BY CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			
			while (rs.next()){
				if(cont==0) tmpMatricula = rs.getString("CODIGO_PERSONAL");
				
				if(!tmpMatricula.equals(rs.getString("CODIGO_PERSONAL"))){
					if(tieneM&&tieneV) mapa.put(tmpMatricula, "M - V");
					else if(tieneM) mapa.put(tmpMatricula, "M");
					else if(tieneV) mapa.put(tmpMatricula, "V");
					
					tieneM = false;
					tieneV = false;
					tmpMatricula = rs.getString("CODIGO_PERSONAL");
				}
				
				turno = rs.getString("TURNO");
				
				if(turno!=null){
					if(turno.equals("M")) tieneM = true;
					else if(turno.equals("V")) tieneV = true;
					else{
						tieneM = true;
						tieneV = true;
					}
				}
				
				cont++;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Horario|getMapaTurnosPorAlumnoInscritos|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	

}
