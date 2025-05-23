<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>
	
<jsp:useBean id="AvanceU" scope="page" class="aca.plan.MapaAvanceUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>

<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.HashMap"%>


<%
	Statement stmt 	= conEnoc.createStatement();
	ResultSet rs = null;
	
	Statement stmt2 	= conEnoc.createStatement();
	ResultSet rs2 = null;
	
	DecimalFormat dmf 	= new DecimalFormat("##,#0;(##,##)");
	
	// Creditos requeridos en los planes de estudio por ciclo y tipo de curso 
	HashMap mapPlanAvance 	= AvanceU.getMapAll(conEnoc, " ");
	
	String codigoPersonal	= "";
	String planId 			= "";
	
	boolean cumpleCred		= true;
	boolean cumpleComp		= true;

	// Busca todos los alumnos que inhgresaron en una carga
	String comando	=	"SELECT"+
			  " CODIGO_PERSONAL, PLAN_ID"+
			  " FROM ENOC.ESTADISTICA"+ 
			  " WHERE CARGA_ID = '03043A'"+
			  //" AND CODIGO_PERSONAL = '1001264'"+
			  " AND F_INSCRIPCION = (SELECT PRIMER_MATRICULA FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ESTADISTICA.CODIGO_PERSONAL AND PLAN_ID = ESTADISTICA.PLAN_ID AND PRIMER_MATRICULA = ESTADISTICA.F_INSCRIPCION)"+
			  " AND (SELECT F_EGRESO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ESTADISTICA.CODIGO_PERSONAL AND PLAN_ID = ESTADISTICA.PLAN_ID) IS NULL"; 
	rs = stmt.executeQuery( comando );

	while(rs.next() ){ 
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		planId 				= rs.getString("PLAN_ID");
		
		// Busca créditos requeridos en en plan de estudios
		int i=1,j=1, cred=0; 
		cumpleCred = true;
		
		while(i<20){
			j=1;
			while(j<10 && cumpleCred){
				//System.out.print("Buscando"+planId+":"+i+":"+j);				
				if (mapPlanAvance.containsKey(planId+i+j)){					
					aca.plan.MapaAvance  avance = (aca.plan.MapaAvance) mapPlanAvance.get(planId+i+j);
					//System.out.print("Encontro requisito"+planId+":"+i+":"+j+":"+avance.getCreditos()+"-");
					
					// Validar si el alumno termino el plan de estudios de acuerdo a la cantidad de requisitos por ciclo en el plan
					comando = "SELECT"+
							  " COALESCE(SUM(CREDITOS),0) AS CRED"+ 
							  " FROM ENOC.ALUMNO_CURSO"+
							  " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"+
							  " AND PLAN_ID = '"+planId+"'"+
							  " AND CICLO = '"+i+"'"+
							  " AND ENOC.TIPOCURSO_ID(CURSO_ID) = '"+j+"'"+
							  " AND TIPOCAL_ID = '1'";
					rs2 = stmt2.executeQuery( comando );
					if (rs2.next()){
						cred = rs2.getInt("CRED");
						//System.out.println(cred);
						if (cred < Integer.parseInt(dmf.format(Double.parseDouble(avance.getCreditos())))){
							cumpleCred = false;
						}
					}else{
						cumpleCred = false;
						//System.out.println("0");
					}
							  
				}else{
					//System.out.println("No Encontro"+planId+":"+i+":"+j);
				}
				j++;
			}		
			i++;
		}
		
		cumpleComp = true;
		if (cumpleCred){			
			int compPlan = 0, compAlumno = 0;
			
			// Numero de componentes del plan
			comando = "SELECT COALESCE(COUNT(CURSO_ID), 0) AS NUMCOMP FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"' AND TIPOCURSO_ID = 3";
			rs2 = stmt2.executeQuery( comando );
			if (rs2.next()){
				compPlan = rs2.getInt("NUMCOMP");
			}
			
			// Componentes acreditados del alumno
			comando = "SELECT COALESCE(COUNT(CURSO_ID),0) AS NUMCOMP FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND ENOC.CURSO_PLAN(CURSO_ID) = '"+planId+"' AND ENOC.TIPOCURSO_ID(CURSO_ID) = '3'  AND TIPOCAL_ID = '1'";
			rs2 = stmt2.executeQuery( comando );
			if (rs2.next()){
				compAlumno = rs2.getInt("NUMCOMP");
			}
			
			if (compPlan>compAlumno){
				cumpleComp = false;
			}
			
			String fecha = ""; String nivel = aca.plan.PlanUtil.getNivelId(conEnoc, planId);
			if (cumpleComp){				
				System.out.print("Alumno:"+codigoPersonal+":"+planId+":"+nivel+":CumpleCred"+": CumpleComp: ");
				
				// Buscar la fecha maxima de evaluacion
				comando = "SELECT TO_CHAR(MAX(F_EVALUACION),'DD/MM/YYYY') AS FECHA FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND CURSO_PLAN(CURSO_ID) = '"+planId+"'";
				rs2 = stmt2.executeQuery( comando );
				if (rs2.next()){
					fecha = rs2.getString("FECHA");
					System.out.println("Fecha: "+fecha);
					
					comando = "UPDATE ENOC.ALUM_PLAN SET F_EGRESO = TO_DATE('"+fecha+"','DD/MM/YYYY') "+
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PLAN_ID = '"+planId+"'";
					rs2 = stmt2.executeQuery( comando );
					if (rs2.next()){
						System.out.println("Modificado: "+codigoPersonal+":"+planId+":"+fecha);
					}else{
						System.out.println("Error: egreso|actualizar|"+codigoPersonal+":"+planId+":"+fecha);
					}					
				}
				
			}else{
				System.out.println("Alumno:"+codigoPersonal+":"+planId+":CumpleCreditos"+": NoCumpleComp"+":"+compPlan+":"+compAlumno);
			}
			
		}else{ 
			//System.out.println("Alumno:"+codigoPersonal+":"+planId+":NoCumple");
		}	
	}
%>
<%@ include file= "../../cierra_enoc.jsp" %>