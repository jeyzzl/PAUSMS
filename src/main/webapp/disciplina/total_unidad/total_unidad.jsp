<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>


<jsp:useBean id="periodo" scope="page" class = "aca.catalogo.CatPeriodo" />
<jsp:useBean id="periodoU" scope="page" class="aca.catalogo.CatPeriodoUtil" />
<jsp:useBean id="alumUnidad" scope="page" class="aca.disciplina.CondAlumnoUtil" />

<%
	String sPeriodo		= request.getParameter("periodo");
	String accion 		= (String) request.getParameter("Accion");
	String FInicio		= request.getParameter("FInicio");
	String FFinal		= request.getParameter("FFinal");
	
	if(FInicio == null) FInicio = "";
	if(FFinal == null)	FFinal = "";
	if(accion == null ||FInicio=="" || FFinal =="") accion = "";
	
  	String codigoAlumno	= "";
  	String carreraAlumno= "";
  	String inscrito 	= "";
	String sFacultad	= "X";
	String sFacultadTmp	= "";	
	String sBgcolor		= "";
	String sElogio		= "";
	String sFalta		= "";
	String sNombre		= "";	
	
	int    nUnidad     	= 0;
	int    nElogio 		= 0;
	int    nFalta 		= 0;
	int    cont 		= 0;
	String fecha		= "";	
	
	if(sPeriodo == null)
									    	sPeriodo = aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc);
	ArrayList<aca.catalogo.CatPeriodo> lisPeriodo 	= periodoU.getListAll(conEnoc, "ORDER BY NOMBRE_PERIODO DESC");
					ArrayList<String> lisAlumnos 	= new ArrayList<String>();	
	
	if (accion.equals("1")){
		lisAlumnos 						= alumUnidad.getListAlumnosF(conEnoc, FInicio, FFinal, "ORDER BY ENOC.ALUM_CARRERA_ID(MATRICULA), ENOC.ALUM_NOMBRE(MATRICULA)");		
	}else{
		lisAlumnos 						= alumUnidad.getListAlumnos(conEnoc, sPeriodo, "ORDER BY ENOC.ALUM_CARRERA_ID(MATRICULA), ENOC.ALUM_NOMBRE(MATRICULA)");
	}
%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<div class="container-fluid">
<h2>Warnings Report by Date</h2>
<form id="forma" name="forma" action="total_unidad" method="post" id="noayuda">
<div class="alert alert-info d-flex align-items-center">
	<select id="periodo" name="periodo" class="form-select" style="width:240px"   onchange="document.forma.submit();">
	
<%
	for(int i = 0; i < lisPeriodo.size(); i++){
		periodo = (aca.catalogo.CatPeriodo) lisPeriodo.get(i);
%>
			<option value="<%=periodo.getPeriodoId() %>"<%=periodo.getPeriodoId().equals(sPeriodo)?" Selected":"" %>><%=periodo.getNombre() %></option>
<%
	}
%>
    	</select>&nbsp;
    	<input type="hidden" name="Accion" id="Accion" value="1">
    	<tr>
		<th align="center"> Date</th>&nbsp;
	  </tr>	
	
	  <tr>
		<td>
			<input type="text" class="form-control" data-date-format="dd/mm/yyyy" name="FInicio" id="FInicio" size="12" style="width:140px" value="<%=FInicio%>">
			<font color="#000099" size="2">&nbsp;&nbsp;to&nbsp;&nbsp;</font>	
			<input type="text" class="form-control" data-date-format="dd/mm/yyyy" name="FFinal" id="FFinal" size="12" style="width:140px" value="<%=FFinal%>">&nbsp;
		</td>
	  </tr>
	  <tr>
		<th>
		  <input class="btn btn-primary" type="submit" value="Aceptar">
		</th>
	  </tr>
</div>
</form>
<%
	for(int i=0; i<lisAlumnos.size(); i++){		
		codigoAlumno 	= (String)lisAlumnos.get(i);
		nUnidad = 0;

		// busca el total de unidades y elogios
		nElogio = Integer.parseInt(aca.disciplina.CondAlumnoUtil.getDisciplinaPer(conEnoc, aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc), codigoAlumno, "C"));
		nFalta  = Integer.parseInt(aca.disciplina.CondAlumnoUtil.getDisciplinaPer(conEnoc, aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc), codigoAlumno, "D"));
		
		if (nFalta > nElogio){ 
			nUnidad = nFalta - nElogio;			
			carreraAlumno 	= aca.alumno.PlanUtil.getCarreraId(conEnoc,codigoAlumno);
			
			sFacultadTmp 	= aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc,carreraAlumno);			
			fecha 			= aca.disciplina.CondAlumnoUtil.getUltimaFecha(conEnoc, aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc), codigoAlumno);
			
			sNombre			= aca.alumno.AlumUtil.getNombreAlumno(conEnoc,codigoAlumno,"NOMBRE");
			
			if (aca.alumno.AlumUtil.esInscrito(conEnoc,codigoAlumno)) 
				inscrito = "YES";
			else 
				inscrito="NO";
		}
		
		if(nUnidad > 0){
			if(!sFacultad.equals(sFacultadTmp)){
				sFacultad = sFacultadTmp;
%>
<table style="width:95%" >  
  <tr>
    <td  colspan="11"><b><font size="3"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,sFacultad)%></font></b></td>
  </tr>
<%    
		//	}//fin del if de facultades diferentes	    	
	   			
	   			cont = 1;
%>
</table>
<table class="table table-bordered">
  <tr class="table-dark"> 
    <th width="4%"  style="color:white"><spring:message code="aca.Numero"/></th>
    <th width="6%" style="color:white"><spring:message code="aca.Matricula"/></th>
    <th width="30%" style="color:white"><spring:message code="aca.Nombre"/></th>
    <th width="10%" style="color:white"><spring:message code="aca.Carrera"/></th>
    <th width="17%" style="color:white">Mentor</th>    
    <th width="5%"  style="color:white">Amon.</th>
    <th width="5%"  style="color:white">Elog.</th>
    <th width="5%"  style="color:white"><spring:message code="aca.Total"/></th>
    <th width="5%"  style="color:white"><spring:message code="aca.Inscrito"/></th>
    <th width="8%" style="color:white">Ultimo mov.</th>
    <th width="5%"  style="color:white"><spring:message code='aca.Dormitorio'/></th>
  </tr> 
<%			}
			if ((cont % 2) == 0){ } else { sBgcolor = ""; }
		
%>
  <tr> 
    <td><%=cont%></td>
    <td><%=codigoAlumno%></td>
    <td><%=sNombre%></td>
    <td><%=aca.catalogo.CatCarreraUtil.getNombreCorto(conEnoc,carreraAlumno)%></td>
    <td><%=aca.alumno.AlumUtil.getNombreCorto(conEnoc, aca.mentores.MentAlumno.getMentorActual(conEnoc, codigoAlumno, aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc)))%></td>
    <td align="center"><%=nFalta%></td>
    <td align="center"><%=nElogio%></td>
    <td align="center"><%=nUnidad%></td>
    <td align="center"><%=inscrito%></td>
    <td align="center"><%=fecha %></td>
    <td align="center"><%=aca.alumno.AcademicoUtil.getDormi(conEnoc, codigoAlumno)%></td>
  </tr>
<%		cont++;
			
		} // if (Unidad>0)		
	} // termina for principal
	
%>
</table>
</div>
<script>
	jQuery('#FInicio').datepicker();
	jQuery('#FFinal').datepicker();
</script>
<%	lisAlumnos = null;	%>

<%@ include file= "../../cierra_enoc.jsp" %>