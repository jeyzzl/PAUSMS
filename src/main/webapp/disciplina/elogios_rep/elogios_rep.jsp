<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="carreraU" scope="page" class= "aca.catalogo.CarreraUtil" />
<jsp:useBean id="reporteU" scope="page" class= "aca.disciplina.CondReporteUtil" />
<jsp:useBean id="alumnoU" scope="page" class= "aca.disciplina.CondAlumnoUtil" />
<jsp:useBean id="nombreU" scope="page" class= "aca.vista.InscritosUtil" />

<!-- inicio de estructura -->
<%	String sCarrera			= "X";
	String sFacultad	 	= "X";
	String sReporte			= "X";
	String sAlumno			= "X";
	String sNombre		 	= "";
	String sCantidad 		= "";
	String sBgcolor			= "";
	String sPeriodo			= aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc);
	int cont 				= 1;
	//System.out.println("Paso 0");
	ArrayList lisCarrera		= new ArrayList();
	lisCarrera				= carreraU.getListDisciplina(conEnoc, "ORDER BY 1, 2");
	ArrayList lisReporte		= new ArrayList();
	lisReporte				= reporteU.getListTipo(conEnoc, "ORDER BY 3, 2 ASC");
	ArrayList lisAlumno		= new ArrayList();
	
%>


<h1>Misconduct and Praise Reports by School</h1>


<br>
<%	for(int i=0; i<lisCarrera.size(); i++){
		aca.catalogo.CatCarrera carrera = (aca.catalogo.CatCarrera)lisCarrera.get(i);	
		if(!sFacultad.equals(carrera.getFacultadId())){
			sFacultad = carrera.getFacultadId();
%>
<table style="width:84%"  align="center" height="41">
	 
  <tr> 
    <td align="center">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="4" height="20" align="center" class="titulo2"><b><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,sFacultad)%></b></td>
  </tr>
<%     	}//fin del if de facultades diferentes
	    if(!sCarrera.equals(carrera.getCarreraId())){
	   		sCarrera = carrera.getCarreraId();
	   		lisAlumno = alumnoU.getListCarrera(conEnoc, sPeriodo, carrera.getCarreraId(), "ORDER BY B.TIPO, B.IDREPORTE, ENOC.ALUM_NOMBRE(A.MATRICULA)");
%>
  <tr> 
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
 
 
<%		} // fin carreras
		for (int k =0; k<lisAlumno.size(); k++){
       		aca.disciplina.CondAlumno alumno = (aca.disciplina.CondAlumno)lisAlumno.get(k);
			sNombre = nombreU.getNombre(conEnoc, alumno.getMatricula(), "NOMBRE");
          	if (!sReporte.equals(alumno.getIdReporte())){
				sReporte = alumno.getIdReporte();
				cont = 1;
%>

  <tr><td colspan="4">&nbsp;</td></tr>
</table>
<table style="margin: 0 auto;">  
  <tr> 
    <td colspan="4"><font color="black" size="2" >Report:<%=" "+aca.disciplina.CondReporteUtil.nombreReporte(conEnoc, alumno.getIdReporte())%></font></b></td>
  </tr>
</table>  
<table  align="center" class="table table-bordered">
  <tr> 
    <th width="7%" height="22" align="center"><spring:message code="aca.Numero"/></th>
    <th width="15%" height="22" align="center"><b><spring:message code="aca.Matricula"/></b></th>
    <th width="34%" height="22" align="center"><b><spring:message code="aca.Nombre"/></b></th>
    <th width="34%" height="22" align="center"><b>Course</b></th>
    <th width="10%" height="22" align="center"><b><spring:message code='aca.Cantidad'/></b></th>
  </tr>
<%			}
			if (!sAlumno.equals(alumno.getMatricula()) && sReporte.equals(alumno.getIdReporte())){
				sAlumno = alumno.getMatricula();
				sCantidad = aca.disciplina.CondAlumnoUtil.getCantReporte(conEnoc, sAlumno, sPeriodo, alumno.getIdReporte());
				if ((k % 2) == 0 ){  }else{ sBgcolor = ""; }
%>
  <tr> 
    <td width="7%"><div align="center"><font size="1">&nbsp;</font><%=cont%></font></div></td>
    <td width="15%" align="center"><font size="1"><%=alumno.getMatricula()%></font></td>
    <td width="34%"><font size="1"><%=sNombre%></font></td>
      <td width="34%"><font size="1"><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,sCarrera)%></font></td>
    <td width="10%" align="center"><font size="1"><%=sCantidad%></font></td>
  </tr>
<%			}
   	 		cont++;
		}
		sAlumno = "X";
	}
%>
</table>
<%
	lisCarrera	= null;
	lisReporte	= null;
	lisAlumno	= null;
%>
<%@ include file= "../../cierra_enoc.jsp" %>