<%@page import="java.util.HashMap"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<jsp:useBean id="grupoUtil" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="facultadU" scope="page" class="aca.catalogo.FacultadUtil"/>
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="carreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>

<%
	String sCargaId 									= request.getParameter("cargaId")==null?(String) session.getAttribute("cargaId") :request.getParameter("cargaId");
	ArrayList <String> lisAllMaestros					= aca.carga.CargaGrupoUtil.lisAllMaestros(conEnoc, sCargaId);
	ArrayList <aca.catalogo.CatFacultad> lisFacultades 	= facultadU.facultadPorCarga(conEnoc, sCargaId, "ORDER BY FACULTAD_ID");
	HashMap <String, String> mapEmpleado 				= grupoUtil.profesorCarga(conEnoc, sCargaId);
	HashMap <String, String> mapEvalAcademico			= grupoUtil.mapEvalCargaAcademico(conEnoc, sCargaId);
	HashMap <String, String> mapEvalEcuarenta			= grupoUtil.mapEvalCargaECuarentaYDos(conEnoc, sCargaId);
	HashMap <String, String> mapMaestrosMaterias		= grupoUtil.mapMateriasMaestros(conEnoc, sCargaId);
	String nombre										= "";
	String academico = "", ecuarenta = "";
	String materias ="";
	
	int i 					= 0;	
%>
<div class="container-fluid">
   <h1>Uso e42</h1>	
   <form id="noayuda" name="forma" action="uso.jsp" method='post'>
     <div class="alert alert-info">
     <a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Menú</a>
     &nbsp; &nbsp;
     <strong><spring:message code="aca.Carga"/>: </strong> [ <%=sCargaId%> ] 
       <select name="cargaId" onchange='document.forma.submit()' class="input input-xlarge">
<%      ArrayList<aca.carga.Carga> lisCarga = cargaU.getListAll(conEnoc,"ORDER BY NOMBRE_CARGA");
        for( i=0;i<lisCarga.size();i++){
          carga = (aca.carga.Carga) lisCarga.get(i);
%>
          <option value='<%=carga.getCargaId()%>' <%if (sCargaId.equals(carga.getCargaId()))out.print("selected");%>>
<%=       carga.getNombreCarga()
%>
          </option>
<%     }
%>
       </select>
     </div>
     </form>
	
<%
	
	for (aca.catalogo.CatFacultad facultades : lisFacultades){
%>		
		<div class="alert alert-info"><h2><%=facultades.getNombreFacultad() %></h2> </div>
<%
		ArrayList <aca.catalogo.CatCarrera> lisCarreras = carreraU.carrerasPorCarga(conEnoc, facultades.getFacultadId(), sCargaId, "ORDER BY CARRERA_ID");
		for (aca.catalogo.CatCarrera carreras : lisCarreras){
%>
 
<%			
			int cont = 1;
				//System.out.println(carreras.getNombreCarrera()+" "+carreras.getCarreraId());
%>
			<div class="alert alert-danger"><h4><%=carreras.getNombreCarrera() %></h4> </div>
    		 <br>
				<table class="table table-condensed table-bordered">
					<tr>
						<th>#</th>
						<th><spring:message code="aca.Maestro"/></th>
						<th><spring:message code="aca.Eval"/> Academico</th>
						<th><spring:message code="aca.Eval"/> E42</th>
						<th><spring:message code="aca.Total"/> <spring:message code="aca.Materias"/></th>
					</tr>
			
<%	
			
			for(String facultad : lisAllMaestros){
			//	System.out.println(facultad.split("@@")[1]);
			if(facultad.split("@@")[1].equals(carreras.getCarreraId())){
				
				if(mapEmpleado.containsKey(facultad.split("@@")[2])){
					nombre 	= mapEmpleado.get(facultad.split("@@")[2]);
				}
				
				if(mapEvalAcademico.containsKey(facultad.split("@@")[2])){
					academico 	= mapEvalAcademico.get(facultad.split("@@")[2]);
				}
				
				if(mapEvalEcuarenta.containsKey(facultad.split("@@")[2])){
					ecuarenta 	= mapEvalEcuarenta.get(facultad.split("@@")[2]);
				}
				
				if(mapMaestrosMaterias.containsKey(facultad.split("@@")[2])){
					materias 	= mapMaestrosMaterias.get(facultad.split("@@")[2]);
				}
				
			
%>			
					<tr>
						<td><%=cont %></td>
						<td><%=nombre%></td>
						<td><%=academico %></td>
						<td><%=ecuarenta %></td>
						<td><%=materias %></td>		
					</tr>
<%
				cont++;	
			}
		}
%>
	</table>
<%cont = 1;
}
%>
  
<% } %> 
</div>
<%@ include file= "../../cierra_enoc.jsp" %>