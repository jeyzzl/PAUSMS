<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumEnlinea" scope="page" class="aca.alumno.AlumEnlinea"/>
<jsp:useBean id="enlineaU" scope="page" class="aca.alumno.AlumEnlineaUtil"/>
<jsp:useBean id="lineaU" scope="page" class="aca.carga.CargaEnlineaUtil"/>

<head>
<script>
	function guardar(){
		document.forma.Accion.value="1";
		document.forma.submit();	
	}
</script>
<%	
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno			= (String) session.getAttribute("codigoAlumno");		
	
	String residencia			= aca.alumno.AcademicoUtil.getResidencia(conEnoc,codigoAlumno);		
	String inscrito				= "";
	String mensaje				= "";
	
	if (aca.alumno.AlumUtil.esInscrito(conEnoc,codigoAlumno)){ inscrito = "YES"; }else{ inscrito = "NO"; }
	
	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	if(accion.equals("1")){
		
		if(enlineaU.existeReg(conEnoc, codigoAlumno, request.getParameter("Carga"))){
			alumEnlinea.mapeaRegId(conEnoc, codigoAlumno, request.getParameter("Carga"));
			alumEnlinea.setUsuario(codigoPersonal);
			alumEnlinea.setResidenciaId(request.getParameter("Residencia"));
			
			if(enlineaU.updateReg(conEnoc, alumEnlinea)){
				mensaje="Updated";
			}else{
				mensaje="Error updating";
			}
		}else{
			alumEnlinea.setCodigoPersonal(codigoAlumno);
			alumEnlinea.setCargaId(request.getParameter("Carga"));
			alumEnlinea.setUsuario(codigoPersonal);
			alumEnlinea.setResidenciaId(request.getParameter("Residencia"));
			alumEnlinea.setSolicitud("N");
			alumEnlinea.setCoordinador("0000000");
			
			if(enlineaU.insertReg(conEnoc, alumEnlinea)){
				mensaje="Saved";
			}else{
				mensaje="Error saving";
			}
		}
	}
%>
</head>
<body>
<form action="residencia" method="post" name="forma" target="_self">
<input type="hidden" name="Accion">

<table id="table" class="table table-sm table-bordered">
  <tr><td height="15px"></td></tr>
  <tr> 
    <td height="25" align="center"><font color="#000066" size="4" face="Arial, Helvetica, sans-serif"><b>Residence Data</b></font></td>
  </tr>
  <tr>
    <td height="25" align="center">      
	    <strong>Student: <font size=1>[<%=codigoAlumno%>]</font></strong>&nbsp; 
      	<font color="#000066"><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoAlumno, "NOMBRE")%> </font>
	    <strong>&nbsp; Country: </strong><font color="#000066"><%=aca.catalogo.PaisUtil.getNombrePais(conEnoc, aca.alumno.AlumUtil.getPaisId(conEnoc,codigoAlumno) )%></font> 
	  	
    </td>
  </tr>
  <tr>
    <td align="center">
  		<strong>Course: </strong> <font color="#000066"><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc,codigoAlumno))%>
  	  </font><strong>&nbsp; Enrolled:</strong><font color="#000066"> <%=inscrito%></font>
  	</td>  	
  </tr>
  <tr>
  	<td align="center">
  		<font color="#000066" size="3">- <%if(residencia.equals("E")){out.println("Off-campus");}else{out.println("Dormitory");}%>-</font>
  	</td>
  </tr>
  <tr><td height="20px"></td></tr>
  <tr>
  	<td align="center">Load: 
  		<select name="Carga" id="Carga">
<%		
		ArrayList<aca.carga.CargaEnlinea> lisCargas	=  lineaU.getListActivas(conEnoc, "ORDER BY CARGA_ID");

		for (int i=0; i< lisCargas.size(); i++){
			aca.carga.CargaEnlinea carga = (aca.carga.CargaEnlinea) lisCargas.get(i);
%>
				<option  value="<%=carga.getCargaId()%>"><%=carga.getNombre()%></option>
<%			
		}
%>          	    
   	    </select>
  	</td>
  </tr>
  <tr><td height="5px"></td></tr>
  <tr>
  	<td align="center">Residence:
  		<select name="Residencia" id="Residencia"> 
  			<option <%if(residencia.equals("E")){out.println("selected");}%> value="E">Off-campus</option>
  			<option <%if(residencia.equals("I")){out.println("selected");}%> value="I">Dormitory</option>
  		</select>
  	</td>
  </tr>
  <tr><td height="15px"></td></tr>
  <tr>
  	<td style="text-align:center">
  		<input class="btn btn-primary" type="button" value="Save" onclick="guardar();">
  	</td>
  </tr>
  <tr><td height="15px"></td></tr>
  <tr>
  	<td align="center"><font size="3" color="blue"><%=mensaje%></font></td>
  </tr>
  <tr><td height="15px"></td></tr>
  <tr>
  	<td align="center"><font size="3">Residence Request:</font></td>
  </tr>
  <tr><td height="15px"></td></tr>
  </table>  
  <table style="margin: 0 auto; width:30%" class="table table-condensed">
  			  <tr>
			  	<th><spring:message code="aca.Carga"/></th>
			  	<th><spring:message code="aca.Residencia"/></th>
			  </tr>
<%	ArrayList<aca.alumno.AlumEnlinea> enlinea = aca.alumno.AlumEnlineaUtil.getListAlumno(conEnoc, codigoAlumno, "ORDER BY 1");
	for(int i=0; i<enlinea.size(); i++){
		aca.alumno.AlumEnlinea line = (aca.alumno.AlumEnlinea)enlinea.get(i);
%>
			<tr>
			  	<td align="center"><%=line.getCargaId()%></td>
			  	<td align="center"><%if(line.getResidenciaId().equals("E")){out.println("Off-campus");}else{out.println("Dormitory");}%></td>
			  </tr>
<%
	}	
	if(enlinea.size()==0){
%>
	 <tr>
	  	<td align="center" colspan="2"><font size="2">N o n e</font></td>
	  </tr>
<%	} %>
	</table>
</form>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>