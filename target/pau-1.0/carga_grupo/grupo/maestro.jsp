<%@ page import= "java.util.List"%>
<%@ page import= "aca.carga.spring.CargaGrupo"%>
<%@ page import= "aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function GrabarTitular(){		
		if(document.frmtitular.CodigoPersonal != "" && document.frmtitular.AsignaCambia != ""){			
			document.frmtitular.submit();			
		} else {
			alert("<spring:message code="aca.JSCompletar"/> ");
		}
	}
	
	function GrabarAuxiliar(){
		if(document.frmauxiliar.CodigoPersonal != "" && document.frmauxiliar.AsignaCambia != ""){
			document.frmauxiliar.submit();
			
		} else {
			alert("<spring:message code="aca.JSCompletar"/> ");
		}
	}
</script>
<%
	CargaGrupo cargaGrupo 	= (CargaGrupo) request.getAttribute("cargaGrupo");
	
	// Declaracion de variables
	String carreraId 		= cargaGrupo.getCarreraId();
	String planId 		    = (String)session.getAttribute("planId");
	String cursoCargaId		= (String)request.getAttribute("cursoCargaId");
	String bloque			= (String)request.getParameter("bloque");
	String nombreMateria	= (String)request.getAttribute("nombreMateria");
	
	String asignaCambia		= "-";
	
	String sBgcolor			= "";		
	String sResultado		= "";
	int i = 0;
	
	String maestroTitular   = cargaGrupo.getCodigoPersonal();
	String maestroAuxiliar  = cargaGrupo.getCodigoOtro();
	
	String nombreTitular	= (String) request.getAttribute("nombreTitular");
	String nombreAuxiliar	= (String) request.getAttribute("nombreAuxiliar");
	
	List<Maestros> lisMaestros = (List<Maestros>)request.getAttribute("lisMaestros");
%>
<html>
<head>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
</head>
<div class="container-fluid">
	<h2>Assign Lecturer <small class="text-muted fs-4">( <%=nombreMateria%> )</small></h2>		
	<div class="alert alert-info">
		<a href="grupo?CarreraId=<%=cargaGrupo.getCarreraId()%>&PlanId=<%=planId %>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
	</div>
	<table class="table table-bordered">	
	<tr>
		<!-- Form Maestro Titular -->
		<form action="guardarMaestro" method="post" name="frmtitular">
			<input type="hidden" name="CarreraId" value="<%=carreraId%>">
			<input type="hidden" name="PlanId" value="<%=planId%>">
			<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
			<input type="hidden" name="bloque" value="<%=bloque%>">
			<input type="hidden" name="AsignaCambia" value="1"> <!-- AsignaCambia 1 = Maestro titular -->
	
			<!-- Nombre -->
			<th width="50%">Regular Lecturer:
				<div class="align-items-center d-inline-flex">					
					<select class="form-select chosen" name="CodigoPersonal" style="width:400px">
					<!-- Si no tiene maestro se muestra "Elegir Maestro" -->
						<option value="0"> Select Lecturer </option>					
					<!-- Lista de Maestros disponibles -->
					<% for(Maestros maestro : lisMaestros){ %>
						<option value="<%=maestro.getCodigoPersonal()%>" <%=maestro.getCodigoPersonal().equals(maestroTitular)?"Selected":""%>><%=maestro.getCodigoPersonal()%> - <%=maestro.getNombre()%> <%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%></option>
					<% } %>
					</select>
					&nbsp;
					<a href="javascript:GrabarTitular()" class="btn btn-sm btn-primary"><i class="fas fa-save"></i></a>
			<% 		if(!nombreTitular.equals("Sin maestro")){%>
						&nbsp;<a href="quitarMaestro?CarreraId=<%=carreraId %>&PlanId=<%=planId %>&CursoCargaId=<%=cargaGrupo.getCursoCargaId()%>&bloque=<%=bloque%>&Opcion=1" type="button" class="btn btn-sm btn-danger"><i class="fas fa-trash-alt"></i></a>&nbsp;&nbsp;
			<% 		}%>
				</div>
			</th>
		</form>
		
		<!-- Form Maestro Auxiliar -->
		<form action="guardarMaestro" method="post" name="frmauxiliar">
			<input type="hidden" name="CarreraId" value="<%=carreraId%>">
			<input type="hidden" name="PlanId" value="<%=planId %>">
			<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
			<input type="hidden" name="bloque" value="<%=bloque %>">			
			<input type="hidden" name="AsignaCambia" value="2"><!-- AsignaCambia 1 = Maestro titular -->
			<!-- Nombre -->
			<th width="50%">Assistant Lecturer:
				<div class="align-items-center d-inline-flex">
					<select class="form-select chosen" name="CodigoPersonal" style="width: 400px">
					<!-- Si no tiene maestro se muestra "Elegir Maestro" -->
						<option value="0">Assistant Lecturer</option>						
					<!-- Lista de Maestros disponibles -->
					<% for(Maestros maestro : lisMaestros){ %>
						<option value="<%=maestro.getCodigoPersonal()%>" <%=maestro.getCodigoPersonal().equals(maestroAuxiliar)?"Selected":""%>>
							<%=maestro.getCodigoPersonal()%> - <%=maestro.getNombre()%> <%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%>
						</option>
					<% } %>
					</select>
					&nbsp;
					<a href="javascript:GrabarAuxiliar()" class="btn btn-sm btn-primary"><i class="fas fa-save"></i></a>
	<% 				if (!nombreAuxiliar.equals("Sin maestro")){%>
						&nbsp;<a href="quitarMaestro?CarreraId=<%=carreraId %>&PlanId=<%=planId %>&CursoCargaId=<%=cargaGrupo.getCursoCargaId()%>&bloque=<%=bloque%>&Opcion=2" type="button" class="btn btn-sm btn-danger"><i class="fas fa-trash-alt"></i></a>
	<% 				}%>	
				</div>				 
			</th>
		</form>
	</tr>	
	<!--  	**Info. Maestro**	-->
	<tr>
		<td width="50%">
			<img src="../../foto?Codigo=<%=cargaGrupo.getCodigoPersonal()%>&Tipo=O" width="70"><br>
			&nbsp;&nbsp;&nbsp;
			<b><%=cargaGrupo.getCodigoPersonal()%></b>
		</td>
		<td width="50%">
			<img src="../../foto?Codigo=<%=maestroAuxiliar%>&Tipo=O" width="70" border="1"><br>
			&nbsp;&nbsp;&nbsp;
			<b><%=maestroAuxiliar%></b>		
		</td>
	</tr>
	</table>
</form>
</div>
</html>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();
</script>