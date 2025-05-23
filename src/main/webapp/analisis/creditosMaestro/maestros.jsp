<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "java.util.HashMap"%>

<jsp:useBean id="MaestrosU" scope="page" class="aca.vista.MaestrosUtil"/>
<jsp:useBean id="CargaAcademicaU" scope="page" class="aca.vista.CargaAcaUtil"/>
<jsp:useBean id="AlumnoCursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}	
	function Detalle(){		
		document.location.href="detallado?Accion=1";
	}
</script>

<%
	java.text.DecimalFormat formato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

	String cargas			= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	if(cargas.equals("")) cargas="''";
	if(modalidades.equals("")) modalidades="''";
	String lisModo[] 		= modalidades.replace("'", "").split(",");
%>

<div class="container-fluid">
	<h1>Créditos por maestro</h1>
	<form id="forma" name="forma" action="maestros?Accion=1" method="post">
	<div class="alert alert-info">
		<b>Cargas:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
		<b>Modalidades:</b>
<%
		for(String mod:lisModo){
			String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
			out.print("["+nombreModalidad+"] ");	
		}		
%>
		<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
		&nbsp;&nbsp;&nbsp;
		<a href="javascript:Detalle();" class="btn btn-success btn-sm"><i class="icon-file icon-white"></i> Ver por materias</a>
	</div>
	</form>
<%	
	if (accion.equals("1")){
	
		// Lista de maestros con materias en las cargas y modalidades seleccionadas
		ArrayList<aca.vista.Maestros> listaMaestros  =  MaestrosU.ListaEnCargasyModalidades(conEnoc, cargas, modalidades, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		// Map del numero de materias 
		java.util.HashMap<String, String> mapMaterias 	= CargaAcademicaU.mapMatPorMaestro(conEnoc, cargas, modalidades);
		
		// Map del numero de materias 
		java.util.HashMap<String, String> mapAlumnos 	= AlumnoCursoU.mapAlumPorMaestro(conEnoc, cargas, modalidades, "'I','1','2','4','5','6'");
		
		// Map del numero de materias 
		java.util.HashMap<String, String> mapCreditos 	= AlumnoCursoU.mapCreditosPorMaestro(conEnoc, cargas, modalidades, "'I','1','2','4','5','6'");
	
%>	
	<table id="noayuda" width="90%"  class="table table-condensed">
		<tr>
			<th colspan="4" style="text-align:center">Información del maestro</th>
		    <th colspan="2" style="text-align:center">Materias/Alumnos</th>
		    <th colspan="7" style="text-align:center">Créditos por niveles</th>
		</tr>		
		<tr>
		    <th>#</th>
		    <th>Codigo</th>
		    <th>Maestro</th>
		    <th>Tipo</th>
		    <th class="right">#Mat.</th>
		    <th class="right">#Al.</th>
		    <th class="right">Pre.</th>
		    <th class="right">Uni.</th>
		    <th class="right">Esp.</th>
		    <th class="right">Mae.</th>
		    <th class="right">Doc.</th>	    
		    <th class="right">Ed.Cont.</th>
		    <th class="right">Total</th>
		  </tr>
<% 
	int row = 0;
	double totPrepa = 0, totLic = 0, totEsp=0, totMae=0, totDoc=0, totEdu=0, totGeneral=0;
	for(aca.vista.Maestros maestro : listaMaestros){
		row++;
		
		String materias = "0";
		if ( mapMaterias.containsKey(maestro.getCodigoPersonal()) ){
			materias = mapMaterias.get(maestro.getCodigoPersonal());
		}
		
		String alumnos = "0";
		if ( mapAlumnos.containsKey(maestro.getCodigoPersonal()) ){
			alumnos = mapAlumnos.get(maestro.getCodigoPersonal());
		}
		
		String prepa = "0";
		if ( mapCreditos.containsKey(maestro.getCodigoPersonal()+"1") ){
			prepa = mapCreditos.get(maestro.getCodigoPersonal()+"1");
		}
		
		String lic = "0";
		if ( mapCreditos.containsKey(maestro.getCodigoPersonal()+"2") ){
			lic = mapCreditos.get(maestro.getCodigoPersonal()+"2");
		}
		
		String esp = "0";
		if ( mapCreditos.containsKey(maestro.getCodigoPersonal()+"6") ){
			esp = mapCreditos.get(maestro.getCodigoPersonal()+"6");
		}
		
		String mae = "0";
		if ( mapCreditos.containsKey(maestro.getCodigoPersonal()+"3") ){
			mae = mapCreditos.get(maestro.getCodigoPersonal()+"3");
		}
		
		String doc = "0";
		if ( mapCreditos.containsKey(maestro.getCodigoPersonal()+"4") ){
			doc = mapCreditos.get(maestro.getCodigoPersonal()+"4");
		}
		
		String educ = "0";
		if ( mapCreditos.containsKey(maestro.getCodigoPersonal()+"5") ){
			educ = mapCreditos.get(maestro.getCodigoPersonal()+"5");
		}
		
		// Suma los totales 
		totPrepa += Double.parseDouble(prepa);
		totLic += Double.parseDouble(lic);
		totEsp += Double.parseDouble(esp);
		totMae += Double.parseDouble(mae);
		totDoc += Double.parseDouble(doc);
		totEdu += Double.parseDouble(educ);
		
		String idEmpleado		= aca.emp.EmpleadoUtil.getId(conEnoc, maestro.getCodigoPersonal());
		String tipoEmpleado 	= aca.emp.EmpleadoUtil.getTipoEmpleado(conEnoc, idEmpleado);		
		String tipoNombre 		= "-";
		//if (tipoEmpleado.equals("0")&&maestro.getCodigoPersonal().substring(0, 3).equals("989")) tipoNombre = "Foraneo";
		if (tipoEmpleado.equals("1")) tipoNombre = "Denominacional";
		if (tipoEmpleado.equals("2")) tipoNombre = "Inter-División";
		if (tipoEmpleado.equals("3")) tipoNombre = "Inter-Unión";
		if (tipoEmpleado.equals("5")) tipoNombre = "Servicio misionero";
		if (tipoEmpleado.equals("6")) tipoNombre = "Jubilado";
		if (tipoEmpleado.equals("7")) tipoNombre = "Contrato";
		if (tipoEmpleado.equals("8")) tipoNombre = "Voluntario";
		if (tipoEmpleado.equals("9")) tipoNombre = "PorHoras";
		if (tipoEmpleado.equals("10")) tipoNombre = "Serv.Social";
		if (tipoEmpleado.equals("11")) tipoNombre = "Hospital";
		if (tipoEmpleado.equals("14")||tipoEmpleado.equals("0")) tipoNombre = "Otros";
		
		
		double total = Double.valueOf(prepa)+Double.valueOf(lic)+Double.valueOf(mae)+Double.valueOf(doc)+Double.valueOf(educ);
		totGeneral += total;
%>
		<tr>
		    <td><%=row%></td>
		    <td><%=maestro.getCodigoPersonal()%></td>
		    <td><%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%>, <%=maestro.getNombre()%></td>
		    <td><%=tipoNombre%></td>
		    <td class="right"><%=materias%></td>
		    <td class="right"><%=alumnos%></td>
		    <td class="right"><%=prepa%></td>
		    <td class="right"><%=lic%></td>
		    <td class="right"><%=esp%></td>
		    <td class="right"><%=mae%></td>
		    <td class="right"><%=doc%></td>
		    <td class="right"><%=educ%></td>
		    <td class="right"><%=formato.format(total)%></td>
		  </tr>
<%		
		}	
%>  
		<tr>
		    <th colspan="6" class="right">T O T A L E S --></th>		    
		    <th class="right"><%=formato.format(totPrepa)%></th>
		    <th class="right"><%=formato.format(totLic)%></th>
		    <th class="right"><%=formato.format(totEsp)%></th>
		    <th class="right"><%=formato.format(totMae)%></th>
		    <th class="right"><%=formato.format(totDoc)%></th>
		    <th class="right"><%=formato.format(totEdu)%></th>		    
		    <th class="right"><%=formato.format(totGeneral)%></th>
		</tr>
	</table>
<%	} // If de accion.equals("1")%>	
</div>	
<%@ include file= "../../cierra_enoc.jsp" %>