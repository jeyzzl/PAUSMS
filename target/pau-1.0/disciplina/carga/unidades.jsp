<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.HashMap"%>

<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="FacU" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="DisciplinaU" scope="page" class="aca.disciplina.CondAlumnoUtil"/>
<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>

<%
	String sCargaId 					= request.getParameter("cargaId");
	if (sCargaId == null) sCargaId 		= (String) session.getAttribute("cargaId");
	int cantidadAlumnos	= 0, cantidadInscritos= 0, porcentaje = 0;
	
	ArrayList<aca.catalogo.CatFacultad> lisFacu = FacU.getListCarga(conEnoc,sCargaId, "ORDER BY NOMBRE_FACULTAD");
	ArrayList <aca.catalogo.CatCarrera> lisCarrera = null;
	
	Carga = CargaU.mapeaRegId(conEnoc, sCargaId);
	HashMap<String,Integer> mapCantidadAlumnosSancionados = DisciplinaU.getMapUnidadesXCarrera(conEnoc, Carga.getFInicio(), Carga.getFFinal(), sCargaId);
	HashMap<String,Integer> mapCantidadUInscritos = EstadisticaU.getAumnosxCarrera(conEnoc, sCargaId);
%>
<div class="container-fluid">
<h2>Warnings by Load</h2>
<form id="noayuda" name="forma" action="unidades" method='post'>
<div class="alert alert-info d-flex align-items-center">
	Load:
	<select name="cargaId" class="form-select" style="width:380px" onchange='document.forma.submit()'>
<%		ArrayList lisCarga = cargaU.getListAll(conEnoc,"ORDER BY CARGA_ID DESC");
		for(int i=0;i<lisCarga.size();i++){
			aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i); 
%>  	<option value='<%=carga.getCargaId()%>' <%if (sCargaId.equals(carga.getCargaId()))out.print("selected");%>>
			<%=carga.getCargaId()%> - <%=carga.getNombreCarga()%>
		</option>
<%		}	%>		
	</select>
</div>
<table style="width:70%" >
  <tr>
    <td align="center" colspan="14" class="titulo2">
	</td>
  </tr>
</table>
<% for(int i=0; i<lisFacu.size(); i++){
	  aca.catalogo.CatFacultad fac = (aca.catalogo.CatFacultad) lisFacu.get(i); 
	  lisCarrera = CarreraU.getListCargaCarrera(conEnoc, fac.getFacultadId(),sCargaId, "ORDER BY 1"); 
%>
 <table   class="table table-striped"> 
	<tr>
	  <th style="border: 1px dashed gray;" colspan="4"><h3><%= fac.getNombreFacultad() %></h3></th>
	</tr>
	<tr class="table-dark">
	  <th width="50%"><spring:message code="aca.Carrera"/></th>
	  <th width="20%"># Enrolled</th>
	  <th width="20%"># Students</th>
	  <th width="20%">%</th>
	</tr>
	<tr>  
<%		for(int j=0; j< lisCarrera.size(); j++){
  				aca.catalogo.CatCarrera carrera = (aca.catalogo.CatCarrera) lisCarrera.get(j);
  				if (mapCantidadAlumnosSancionados.containsKey(carrera.getCarreraId())){
  					cantidadAlumnos			= mapCantidadAlumnosSancionados.get(carrera.getCarreraId());			
  				}else{
  					cantidadAlumnos = 0;
  				}
  				if (mapCantidadUInscritos.containsKey(carrera.getCarreraId())){
  					cantidadInscritos 		= mapCantidadUInscritos.get(carrera.getCarreraId());			
  				}else{
  					cantidadInscritos = 0;	
  				}
  				if(cantidadAlumnos>0){
	  					porcentaje = (cantidadAlumnos*100)/cantidadInscritos;
	  			}else{
	  					porcentaje = 0;
	  			}
%>	 
	  <td width="50%"><%= carrera.getNombreCarrera() %></td>
	  <td width="5%%"><%= cantidadInscritos %></td>
	  <td width="5%%"><%= cantidadAlumnos %></td>
	  <td width="5%%"><%= porcentaje %>%</td>  
	</tr>
  <% 	   } %>	
	</table>
<%	} %>
</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>