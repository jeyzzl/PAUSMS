<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.tit.spring.TitTramite"%>
<%@ page import= "aca.tit.spring.TitAlumno"%>
<%@ page import= "aca.tit.spring.TitInstitucionUsuario"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head></head>
<%	 
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String institucion 			= (String) session.getAttribute("valInstitucion");
	String estado 				= (String) session.getAttribute("valEstado");

	ArrayList<TitTramite> lisTramites				= (ArrayList<TitTramite>)request.getAttribute("lisTramites");
	List<TitInstitucionUsuario> lisInstituciones	= (List<TitInstitucionUsuario>)request.getAttribute("lisInstituciones");
	HashMap<String,String> mapaTotTitulos			= (HashMap<String,String>)request.getAttribute("mapaTotTitulos");
	HashMap<String,String> mapaSenl					= (HashMap<String,String>)request.getAttribute("mapaSenl");
	HashMap<String,String> mapaAutorizados			= (HashMap<String,String>)request.getAttribute("mapaAutorizados");
	HashMap<String,String> mapaErrorFolio			= (HashMap<String,String>)request.getAttribute("mapaErrorFolio");
	int total = 0, totSenl=0, totAut = 0;	
%>
<body>
<div class="container-fluid">
	<h2>Procesos de titulación</h2>
	<form name="frmTramite" action="tramite" method="post">
	<div class="alert alert-info d-flex align-items-center">	
		Institución:&nbsp;
		<select name="Institucion" id="Institucion" class="form-select" style="width:140px;">
<%	for(TitInstitucionUsuario institucionUsuario : lisInstituciones){ %>
			<option value="<%=institucionUsuario.getInstitucion()%>" <%=institucion.equals(institucionUsuario.getInstitucion())?"selected":""%>><%=institucionUsuario.getInstitucion()%></option>
<% 	}%>						
		</select>
		&nbsp;&nbsp;Estado:&nbsp;
		<select name="Estado" id="Estado" class="form-select" style="width:140px;">
			<option value="A" <%=estado.equals("A")?"selected":""%>>Activo</option>
			<option value="F" <%=estado.equals("F")?"selected":""%>>Firmas</option>
			<option value="T" <%=estado.equals("T")?"selected":""%>>Terminado</option>
		</select>
		&nbsp;&nbsp;
		<a href="javascript:Refresh()" class="btn btn-primary"><i class="fas fa-sync-alt"></i></a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="btn btn-success" href="editar?Tramite=0&Estado=<%=estado%>"><i class="fa fa-plus" aria-hidden="true">Trámite</i></a>
	</div>
	</form>
	<table class="table table-condensed">
		<thead class="table-info">
			<tr>
				<th width="10%">Opción</th>
				<th width="2%">#</th>		
				<th width="7%">Fecha</th>
				<th width="3%">Trámite</th>
				<th width="25%">Descripción</th>
				<th width="5%">Recibo</th>
				<th width="7%">Institución</th>
				<th width="5%" class="right">#Doc</th>
				<th width="5%" class="right">#Error Folio</th>
				<th width="5%" class="right">#SENL</th>
				<th width="5%" class="right">#Aut.</th>
				<th width="5%" class="center">Firmar</th>
				<th width="32%" class="center">Consulta</th>
			</tr>
		</thead>
		<tbody>
<%
	int row = 0;
	for (TitTramite tramite : lisTramites){
		row++;
		
		String numDoc = "0";
		if (mapaTotTitulos.containsKey(tramite.getTramiteId()) ){
			numDoc 		= mapaTotTitulos.get(tramite.getTramiteId());
			total 		+= Integer.parseInt(numDoc);
		}
		
		String numSenl = "0";
		if (mapaSenl.containsKey(tramite.getTramiteId())){
			numSenl 	= mapaSenl.get(tramite.getTramiteId());
			totSenl		+= Integer.parseInt(numSenl);
		}
		
		String numAut = "0";
		if (mapaAutorizados.containsKey(tramite.getTramiteId())){
			numAut 		= mapaAutorizados.get(tramite.getTramiteId());
			totAut		+= Integer.parseInt(numAut);
		}
		
		String estadoNombre  = "-";
		if (tramite.getEstado().equals("A")) estadoNombre = "Activo";
		if (tramite.getEstado().equals("F")) estadoNombre = "Firma";	
		if (tramite.getEstado().equals("T")) estadoNombre = "Terminado";
		int numErrores = 0;
		if (mapaErrorFolio.containsKey(tramite.getTramiteId())){
			numErrores = Integer.parseInt(mapaErrorFolio.get(tramite.getTramiteId()));
		}
		
		boolean puedeFirmar = false;
		if ( estado.equals("F") && numErrores==0) puedeFirmar = true;		
%>
			<tr>
				<td>
<% 		if (estado.equals("A")){%>		
					<a class="btn btn-success" href="titulos?Tramite=<%=tramite.getTramiteId()%>"><i class="fa fa-plus" aria-hidden="true"></i></a>&nbsp;
<%		}%>			
					<a class="btn btn-primary" href="editar?Tramite=<%=tramite.getTramiteId()%>&Estado=<%=tramite.getEstado()%>"><i class="fas fa-pencil-alt"></i></a>&nbsp;			
					<a class="btn btn-info" href="alumnos?Institucion=<%=institucion%>&Estado=<%=tramite.getEstado()%>&Tramite=<%=tramite.getTramiteId()%>"><i class="fa fa-search" aria-hidden="true"></i></a>&nbsp;
<% 		if (numDoc.equals("0")){%>
					<a class="btn btn-danger" href="javascript:Borrar('<%=tramite.getTramiteId()%>','<%=institucion%>','<%=tramite.getEstado()%>');"><i class="fa fa-trash" aria-hidden="true"></i></a>
<%		} %>
		
				</td>
				<td><%=row%></td>
				<td><%=tramite.getFecha()%></td>
				<td class="center"><span class="badge bg-dark"><%=tramite.getTramiteId()%></span></td>		
				<td><%=tramite.getDescripcion()%></td>
				<td><%=tramite.getRecibo()%></td>
				<td><%=tramite.getInstitucion()%></td>
				<td class="right"><%=numDoc%></td>
				<td class="right"><%=numErrores%></td>
				<td class="right"><%=numSenl%></td>
				<td class="right"><%=numAut%></td>
				<td class="center">
<%		if (puedeFirmar==true){%>		
					<a href="firmar?Institucion=<%=institucion%>&Estado=<%=tramite.getEstado()%>&Tramite=<%=tramite.getTramiteId()%>">
						<i class='fas fa-pen fa-2x' aria-hidden="true" style='color:black'></i>			
					</a>
<%		}else{%>
			<i class="fas fa-exclamation-triangle"  style="color:orange"></i>
<%		} %>		
				</td>
				<td class="center">
<% 		if (tramite.getEstado().equals("F") || tramite.getEstado().equals("T")){%>
					<a href="actualizarXML?Institucion=<%=institucion%>&Estado=<%=tramite.getEstado()%>&Tramite=<%=tramite.getTramiteId()%>">
						<i class='fas fa-sync fa-2x' aria-hidden="true" style='color:black'></i>
					</a>
<%		}else{%>
					<i class="fas fa-exclamation-triangle"  style="color:orange"></i>					
<% 		}%>
				</td>
			</tr>
<%	} %>	
			<tr>
				<th class="right"></th>
				<th class="right"></th>
				<th class="right"></th>	
				<th class="right"></th>	
				<th class="right"></th>
				<th class="right"></th>
				<th class="right">TOTALES </th>	
				<th class="right"><%=total%></th>
				<td class="right">&nbsp;</td>
				<th class="right"><%=totSenl%></th>
				<th class="right"><%=totAut%></th>
				<th class="right"></th>
				<th class="right"></th>
			</tr>	
		</tbody>
	</table>		
</div>
</body>
<script type="text/javascript">
	function Refresh() {
		document.frmTramite.submit();
	}
	
	function Borrar(tramite, institucion, estado) {
		if (confirm("¿ Estás seguro de borrar éste tramite ?")){
			document.location.href = "borrar?Tramite="+tramite+"&Institucion="+institucion+"&Estado="+estado;
		}	
	}

	function QuitarXml(tramite, institucion, estado) {
		if (confirm("¿ Estás seguro de borrar el contenido de los XML ?")){
			document.location.href = "quitarXml?Tramite="+tramite+"&Institucion="+institucion+"&Estado="+estado;
		}	
	}
</script>
</html>