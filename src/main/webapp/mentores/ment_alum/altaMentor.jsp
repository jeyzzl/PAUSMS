<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.List" %>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%	
	String periodoId	 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String periodoNombre	 		= (String)request.getAttribute("periodoNombre");
	Acceso acceso 					= (Acceso) request.getAttribute("acceso");
	
	List<Maestros> lisMaestros	 	= (List<Maestros>) request.getAttribute("lisMaestros");
	List<CatCarrera> lisCarreras	= (List<CatCarrera>) request.getAttribute("lisCarreras");	
%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
	<h2>Add Mentor <small class="text-muted fs-4">(<%=periodoId%> - <%=periodoNombre%>)</small></h2>
	<form name="frmMentor" action="addMentor" method="post">
	<input type="hidden" name="PeriodoId" value="<%=periodoId%>">
	<div class="alert alert-info d-flex align-items-center">
	&nbsp;&nbsp;<a class="btn btn-primary" href="carrera"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<select name="CarreraId" class="form-select chosen" style="width:400px">
<%		for(CatCarrera carrera : lisCarreras){			
			if ((acceso.getAccesos().indexOf(carrera.getCarreraId()) != -1) || (acceso.getAdministrador().equals("S")) || (acceso.getSupervisor().equals("S"))){
%>
			<option value="<%=carrera.getCarreraId()%>"><%=carrera.getCarreraId()%>-<%=carrera.getNombreCarrera()%></option>
<%			}
		}
%>		
		</select>&nbsp;&nbsp;
		<select name="MentorId" class="form-select chosen" style="width:350px">
		<%for(Maestros maestro : lisMaestros){%>
			<option value="<%=maestro.getCodigoPersonal()%>"><%=maestro.getCodigoPersonal()%> - <%=maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()%></option>
		<%}%>
		</select>&nbsp;&nbsp;		
		<a href="javascript:document.frmMentor.submit();" class="btn btn-primary"><i class="fas fa-save"></i> Save</a>		
	</div>
	</form>	
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();	
</script>