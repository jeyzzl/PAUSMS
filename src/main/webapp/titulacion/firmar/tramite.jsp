<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.tit.spring.TitTramite"%>
<%@ page import= "aca.tit.spring.TitInstitucionUsuario"%>
<%@ page import= "aca.tit.spring.TitFirma"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head></head>
<%	
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String institucion 			= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");

	ArrayList<TitTramite> lisTramites				= (ArrayList<TitTramite>)request.getAttribute("lisTramites");
	ArrayList<TitFirma> lisFirmas					= (ArrayList<TitFirma>)request.getAttribute("lisFirmas");
	List<TitInstitucionUsuario> lisInstituciones	= (List<TitInstitucionUsuario>)request.getAttribute("lisInstituciones");
	HashMap<String,String> mapaTotTitulos			= (HashMap<String,String>)request.getAttribute("mapaTotTitulos");
%>
<body>
<div class="container-fluid">
	<h2>Procesos de titulación</h2>
	<form name="frmTramite" action="tramite" method="post">
	<div class="alert alert-info d-flex align-items-center">		
		Institución:&nbsp;
		<select name="Institucion" id="Institucion" class="form-select" style="width:150px;">
<%	for(TitInstitucionUsuario institucionUsuario : lisInstituciones){ %>
		<option value="<%=institucionUsuario.getInstitucion()%>" <%=institucion.equals(institucionUsuario.getInstitucion())?"selected":""%>><%=institucionUsuario.getInstitucion()%></option>
<% 	}%>
		</select>	
		&nbsp;
		<a href="javascript:Refresh()" class="btn btn-primary"><i class="fas fa-sync-alt"></i></a>		
	</div>
	</form>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th width="10%">Opción</th>
		<th width="5%">#</th>
		<th width="10%">Fecha</th>
		<th width="30%">Descripción</th>
		<th width="10%">Institución</th>
		<th width="5%">No.Doc.</th>		
<%
	for (TitFirma firma : lisFirmas){
%>			
		<th width="10%" class="center"><%=firma.getCodigoPersonal()%></th>
<%	} %>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for (TitTramite tramite : lisTramites){
		row++;
		
		String total = "0";
		if (mapaTotTitulos.containsKey(tramite.getTramiteId()) ){
			total = mapaTotTitulos.get(tramite.getTramiteId());
		}		
%>
	<tr>
		<td>	
			<a class="btn btn-info" href="alumnos?Institucion=<%=institucion%>&Estado=<%=tramite.getEstado()%>&Tramite=<%=tramite.getTramiteId()%>"><i class="fas fa-search" ></i></a>			
		</td>
		<td><%=row%></td>
		<td><%=tramite.getFecha()%></td>
		<td><%=tramite.getDescripcion()%></td>
		<td><%=tramite.getInstitucion()%></td>
		<td><%=total%></td>
<%
		for (TitFirma firma : lisFirmas){
			if (codigoPersonal.equals(firma.getCodigoPersonal()) || codigoPersonal.equals("9800308") ){
%>			
		<td width="10%" class="center"><a href="subirFirma?Tramite=<%=tramite.getTramiteId()%>&CodigoPersonal=<%=firma.getCodigoPersonal()%>"><i class="fas fa-pencil-alt-square-o" ></i></a></td>
<%	
			}else{
				out.print("<td width='10%'>-</td>");
			}
		} 
%>		
	</tr>
<%		
	}
%>	
	</tbody>
	</table>		
</div>
</body>
<script type="text/javascript">
	function Refresh() {
		document.frmTramite.submit();
	}	
</script>
</html>