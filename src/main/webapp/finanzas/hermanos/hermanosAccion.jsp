<%@ include file= "../../con_enoc.jsp" %>

<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumFamilia" scope="page" class="aca.alumno.AlumFamilia"/>
<jsp:useBean id="AlumFamiliaU" scope="page" class="aca.alumno.AlumFamiliaUtil"/>
<jsp:useBean id="AlumHermano" scope="page" class="aca.alumno.AlumHermanos"/>
<jsp:useBean id="AlumHermanosU" scope="page" class="aca.alumno.AlumHermanosUtil"/>
<jsp:useBean id="AlumPersonal2" scope="page" class="aca.alumno.AlumPersonal"/>
<%
	String accion = request.getParameter("Accion");
	String matriculaSesion = request.getParameter("Matricula");
	%>var content = "<head><meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'></head>";<%
	if(accion.equals("1")){		
		AlumFamilia.setFamiliaId(request.getParameter("FamiliaId"));
		AlumFamilia.setEstado("F");
		if(!AlumFamiliaU.updateReg(conEnoc, AlumFamilia)){
			%>Error<%
		}
	}
	else if(accion.equals("2")){
		conEnoc.setAutoCommit(false);
		
		ArrayList<aca.alumno.AlumHermanos> listaHermanos = AlumHermanosU.getListAll(conEnoc, "WHERE FAMILIA_ID='"+request.getParameter("FamiliaId")+"'");
		boolean mal = false;
		for(aca.alumno.AlumHermanos alumHermano : listaHermanos){
			if(!AlumHermanosU.deleteReg(conEnoc, alumHermano.getFamiliaId(), alumHermano.getCodigoPersonal())){
				conEnoc.rollback();
				mal = true;
				break;
			}
		}
		if(!mal){			
			AlumFamilia.setFamiliaId(request.getParameter("FamiliaId"));
			if(!AlumFamiliaU.deleteReg(conEnoc, request.getParameter("FamiliaId"))){
				conEnoc.rollback();
				%>Error<%
			}
		}
		conEnoc.commit();
		conEnoc.setAutoCommit(true);
	}
	else if(accion.equals("3")){		
		AlumFamilia.setFamiliaId(request.getParameter("FamiliaId"));
		AlumFamilia.setEstado("A");
		if(!AlumFamiliaU.updateReg(conEnoc, AlumFamilia)){
			%>Error<%
		}
	}
	else if(accion.equals("4")){
		aca.alumno.AlumHermanos AlumHermanos = new aca.alumno.AlumHermanos();
		AlumHermanos.mapeaRegIdPorMatricula(conEnoc, matriculaSesion);
		AlumHermanos.setEstado("A");
		if(!AlumHermanosU.updateReg(conEnoc, AlumHermanos)){
			%>Error<%
		}
	}
	else if(accion.equals("5")){
		conEnoc.setAutoCommit(false);
		aca.alumno.AlumHermanos AlumHermanos = new aca.alumno.AlumHermanos();
		AlumHermanos.mapeaRegIdPorMatricula(conEnoc, matriculaSesion);
		
		aca.alumno.AlumHermanosUtil AlumHermanosUtil = new aca.alumno.AlumHermanosUtil();
		ArrayList<aca.alumno.AlumHermanos> listaHermanos = AlumHermanosUtil.getListAll(conEnoc, "WHERE FAMILIA_ID='"+AlumHermanos.getFamiliaId()+"'");

		AlumHermanos.setEstado("F");
		if(!AlumHermanosU.updateReg(conEnoc, AlumHermanos)){
			conEnoc.rollback();
			%>Error<%
		}
		else{
			int autorizados = 0;
			for(aca.alumno.AlumHermanos alumHermano : listaHermanos){
				if(alumHermano.getEstado().equals("F")) autorizados++;
			}
			if(autorizados+1==listaHermanos.size()){				
				AlumFamilia.mapeaRegId(conEnoc, AlumHermanos.getFamiliaId());
				AlumFamilia.setEstado("F");
				if(!AlumFamiliaU.updateReg(conEnoc, AlumFamilia)){
					conEnoc.rollback();
					%>Error<%
				}
			}
		}
		conEnoc.commit();
		conEnoc.setAutoCommit(true);
	}
	else if(accion.equals("6")){
		conEnoc.setAutoCommit(false);
		aca.alumno.AlumHermanos AlumHermanos = new aca.alumno.AlumHermanos();
		AlumHermanos.mapeaRegIdPorMatricula(conEnoc, request.getParameter("Matricula"));
		if(AlumHermanosU.deleteReg(conEnoc, AlumHermanos.getFamiliaId(), AlumHermanos.getCodigoPersonal() )){
			ArrayList<aca.alumno.AlumHermanos> listaHermanos = new aca.alumno.AlumHermanosUtil().getListAll(conEnoc, "WHERE FAMILIA_ID='"+AlumHermanos.getFamiliaId()+"'");
			if(listaHermanos.size()==1){
				if(AlumHermanosU.deleteReg(conEnoc, listaHermanos.get(0).getFamiliaId(), listaHermanos.get(0).getCodigoPersonal())){					
					AlumFamilia.setFamiliaId(AlumHermanos.getFamiliaId());
					if(!AlumFamiliaU.deleteReg(conEnoc, AlumFamilia.getFamiliaId())){
						conEnoc.rollback();
						%>Error<%						
					}
				}
				else{
					conEnoc.rollback();
					%>Error<%
				}
			}
		}
		else{
			conEnoc.rollback();
			%>Error<%
		}
		conEnoc.commit();
		conEnoc.setAutoCommit(true);
	}
%>
<%@ include file= "../../cierra_enoc2.jsf" %>