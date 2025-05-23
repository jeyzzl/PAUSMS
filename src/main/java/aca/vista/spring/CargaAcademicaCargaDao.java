package aca.vista.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaAcademicaCargaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<CargaAcademicaCarga> listCursosDisponibles(String matricula, String carga, String planId, String modalidadId) {
        List<CargaAcademicaCarga> lista  = new ArrayList<CargaAcademicaCarga>();
        try{
            
            String comando = " SELECT " 
                    + " CASE a.PLAN_ID WHEN ? THEN '1' ELSE '2' END AS PLAN_ID, "
                    + " A.CARRERA_ID, A.CICLO, A.CURSO_ID, A.CURSO_CARGA_ID, A.NOMBRE_CURSO, "
                    + " COALESCE(A.GRUPO,' ') AS GRUPO, "
                    + " TRIM(COALESCE(A.GRUPO,'X')) AS LETRA, "
                    + " COALESCE(ENOC.EMP_APELLIDO(A.CODIGO_PERSONAL),'Sin Maestro') AS PROFESOR, "
                    + " ENOC.NOMBRE_CARRERA(CARRERA_ID) AS CARRERA, "
                    + " ENOC.NOMBRE_CARRERA_CORTO(CARRERA_ID) AS L_CARRERA, "
                    + " (COALESCE(A.HT,0)+COALESCE(A.HP,0)) AS HORAS, "
                    + " A.CREDITOS, A.ESTADO, ENOC.MODALIDAD_NOMBRE(A.MODALIDAD_ID) AS MODALIDAD, "
                    + " COALESCE(A.BLOQUE_ID, 0) AS BLOQUE, "
                    + " A.OPTATIVA AS OPTATIVA "
                    + " FROM ENOC.CARGA_ACADEMICA A, ENOC.CARGA B "
                    + " WHERE B.CARGA_ID = ? "
                    + " AND A.CARGA_ID = B.CARGA_ID "
                    + " AND B.ESTADO = '1' "
                    + " AND A.MODALIDAD_ID in ("+modalidadId+") "
                    + " AND (A.CURSO_ID IN ("
                    + " 	SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?"
                    + " 	AND NOT CURSO_ID IN("
                    + "			SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN ('1','M','I') "
                    + "			AND CURSO_ID NOT IN( SELECT CURSO_ID FROM ENOC.KRDX_CURSO_ACT WHERE TIPO = 'B' AND CODIGO_PERSONAL= ? )"
                    + " 	)"
                    + " AND NOT CURSO_ID IN ("
                    + " 	SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL  = ? "
                    + " 	AND PROCESO_ID IN ('C','A','T','R') "
                    + " 	AND EST_MAT != 'N')"
                    + " )"
                    + " AND A.CURSO_CARGA_ID NOT IN("
                    + "		SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? )"
                    + "	)"
                    + " ORDER BY CICLO, NOMBRE_CURSO, CARRERA_ID ";

            lista = enocJdbc.query(comando, new CargaAcademicaCargaMapper(),planId,carga,planId,matricula,matricula,matricula,matricula);//
            
            for(CargaAcademicaCarga objeto : lista) {
            	String cursoCargaId = objeto.getCursoCargaId();
            	
            	String comando2 = "SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMHORAS FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID='"+cursoCargaId+"'";
            
            	int horasRequeridas = Integer.parseInt(objeto.getHoras());
            	int horasMateria = enocJdbc.queryForObject(comando2, Integer.class);
            	
            	if (horasMateria >= horasRequeridas) {
            		objeto.setTieneHorario("si");
            	}
            }
        }catch(Exception ex){
            System.out.println("Error - aca.vista.spring.CargaAcademicaCargaDao|listCursosDisponibles|:"+ex);
        }
        
        return lista;
    }
}