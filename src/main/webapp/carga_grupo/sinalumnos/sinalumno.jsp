<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");	
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String nombreCarga			= (String) request.getAttribute("nombreCarga");
	
	String sFacultad 			= "X";
	String sFacultadTmp			= "";			
	String sCarrera 			= "X";
	String sCarreraTemp			= "";	
	String facultadNombre 		= "-";
	String carreraNombre		= "-";
	int con						= 0;	
	
	// Lista de materias en una carga	
	List<CargaAcademica> listaCargas				=  (List<CargaAcademica>) request.getAttribute("listaCargas");
	HashMap<String, CatFacultad> mapaFacultades    	= (HashMap<String, CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras    	= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarreras");	
%>
<div class="container-fluid">
	<h2>Subjects without students <small class="text-muted fs-4">( <%=nombreCarga%> )</small></h2>
	<div class="alert alert-info">
		<a href="carga" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<a href="borrarAll?CargaId=<%=cargaId%>" class="btn btn-danger">Delete all</a>
	</div>
<% 	if(!mensaje.equals("-")){%>
	<div class="alert alert-success"><%=mensaje%></div>
<% 	}%>
	<table class="table table-bordered">
<%		
	for (CargaAcademica cargaA 	: listaCargas){
			sCarreraTemp = cargaA.getCarreraId();
			if (mapaCarreras.containsKey(cargaA.getCarreraId())){
				sFacultadTmp 	= mapaCarreras.get(cargaA.getCarreraId()).getFacultadId();
				carreraNombre 	= mapaCarreras.get(cargaA.getCarreraId()).getNombreCarrera();
				if (mapaFacultades.containsKey(sFacultadTmp)){
					facultadNombre = mapaFacultades.get(sFacultadTmp).getNombreFacultad();
				} 
			}			
			if(!sFacultad.equals(sFacultadTmp)){
		 		sFacultad = sFacultadTmp;		
%>
	<thead>
  	<tr> 
    	<td colspan="6"><div align="center"><b><font color="#000099" size="3"><%=facultadNombre%></font></b></div></td>
  	</tr>
  	</thead>
  <%  
       		}//fin del if de facultades diferentes
			if(sFacultad.equals(sFacultadTmp) && !sCarrera.equals(sCarreraTemp)){
					sCarrera = sCarreraTemp;
%>
	<thead>
  	<tr> 
    	<td colspan="6"><strong><font color="#000099">Course:&nbsp;<em>&nbsp;<%=carreraNombre%></em></font></strong></td>
	</tr>
  	</thead>
	<thead class="table-info">
	<tr> 
    	<th width="3%" height="22" align="center" size="3"><spring:message code="aca.Numero"/></th>
    	<th width="10%" height="22" align="center"><font size="3" face="Arial, Helvetica, sans-serif"><b>Sub-Load Key</b> </font></th>
    	<th width="11%" height="22" align="center"><font size="3" face="Arial, Helvetica, sans-serif"><b>Emp. ID</b></font></th>
    	<th width="30%" height="22" align="center"><font face="Times New Roman, Times, serif"><b><font size="3" face="Arial, Helvetica, sans-serif"><spring:message code="aca.Nombre"/></font></b></font></th>
    	<th width="39%" height="22" align="center"><b><font face="Arial, Helvetica, sans-serif" size="3">Subjects</font></b></th>
    	<th width="7%" align="center">&nbsp;</th>
  	</tr>
  	</thead>
<%
           }//fin del if de carreras diferentes
			con++;  
%>
	<tr class="tr2"> 
	  	<td><font size="2" face="Arial, Helvetica, sans-serif"><%=con%></font></td>
		<td><font size="2" face="Arial, Helvetica, sans-serif"><%=cargaA.getCursoCargaId()%></font></td>
		<td align="center"><font size="2" face="Arial, Helvetica, sans-serif"><%=cargaA.getCodigoPersonal()%></font></td>
		<td><font size="2" face="Arial, Helvetica, sans-serif"><%=cargaA.getNombre()%></font></td>
		<td align="rigth"><font size="2" face="Arial, Helvetica, sans-serif"><%=cargaA.getNombreCurso()%></font></td>
		<td width="7%" align="center"><a class="fas fa-trash-alt" href="borrar?CursoCargaId=<%=cargaA.getCursoCargaId()%>&CargaId=<%=cargaId%>"></a></td>
	</tr>
<% 	
	} // fin del For  		
%>
	</table> 
	<br>
	<div align="center"><font size="3" face="Times New Roman, Times, serif"> <br>
 		<b><font color="#000099"></font></b></font>
	</div>
</div>	