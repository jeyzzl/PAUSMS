<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.vista.spring.CargaAcademica"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%	
	String sCargaId 		= (String)request.getAttribute("cargaId");
	
	int i 					= 0;	
	int con				    = 0;
	int numAlumnos			= 0;
	int numAsignados		= 0;
	int numBajas			= 0;
	int promedio			= 0;
	
	String bgColor			= "";
	
	String facultad			= "";
	String carreraTemp		= "X";
	String strEstado		= ""; 
	
	java.text.DecimalFormat getFormato		= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");
	List<CargaAcademica> lisMateria 		= (List<CargaAcademica>) request.getAttribute("lisMaterias");
	List<Carga> lisCarga 					= (List<Carga>) request.getAttribute("lisCargas");
	
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaBajas				= (HashMap<String,String>)request.getAttribute("mapaBajas");
	HashMap<String,String> mapaAcreditados			= (HashMap<String,String>)request.getAttribute("mapaAcreditados");
	HashMap<String,String> mapaReprobados			= (HashMap<String,String>)request.getAttribute("mapaReprobados");
	HashMap<String,String> mapaAsignados			= (HashMap<String,String>)request.getAttribute("mapaAsignados");
	HashMap<String,String> mapaInscritos			= (HashMap<String,String>)request.getAttribute("mapaInscritos");
	HashMap<String,String> mapaPendientes			= (HashMap<String,String>)request.getAttribute("mapaPendientes");
	HashMap<String,String> mapaPromediosPorMateria	= (HashMap<String,String>)request.getAttribute("mapaPromediosPorMateria");
	
	Carga carga = new Carga();
	
%>
<div class="container-fluid">
	<h1>Análisis de Materias con alumnos</h1>
	<form id="noayuda" name="forma" action="concentrado" method='post'>
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="../reportes/menu"><i class="fas fa-arrow-left"></i></a>	
	   	Carga: [ <%=sCargaId%> ] 
    	<select name="CargaId" onchange='document.forma.submit()' class="input input-xlarge">
<%		for( i=0;i<lisCarga.size();i++){
			carga = lisCarga.get(i);
%>			<option value='<%=carga.getCargaId()%>' <%if (sCargaId.equals(carga.getCargaId()))out.print("selected");%>>
				<%=carga.getNombreCarga()%>
			</option>
<%		}
%>		</select>	
	</div>	
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
	 	<th width="3%" align="center"><h3>#</h3></th>
	 	<th width="3%" align="center"><h3>Acta</h3></th>
	 	<th width="20%" align="center"><h3><spring:message code="aca.Maestro"/></h3></th>
	 	<th width="20%" align="center"><h3><spring:message code="aca.Facultad"/></h3></th>
	 	<th width="20%" align="center"><h3><spring:message code="aca.Carrera"/></h3></th>
	 	<th width="3%" align="center"><h3>Sem</h3></th>
	 	<th width="20%" align="center"><h3><spring:message code="aca.Materia"/></h3></th>
	 	<th width="5%" align="center"><h3><spring:message code="aca.Estado"/></h3></th>
	 	<th width="3%" align="center"><h3>#Al.</h3></th>
	 	<th width="3%" align="center"><h3>C.M.</h3></th>
	 	<th width="3%" align="center"><h3>Ins.</h3></th>
	 	<th width="3%" align="center"><h3>AC</h3></th>
	 	<th width="3%" align="center"><h3>NA</h3></th>
	 	<th width="3%" align="center"><h3>%NA</h3></th>
	 	<th width="3%" align="center"><h3>CP</h3></th>
	 	<th width="3%" align="center"><h3>BA</h3></th>  	
	 	<th width="3%" align="center"><h3>Prom.</h3></th>
	</tr>
	</thead>
	<tbody>
<%
	for (int j=0; j<lisMateria.size(); j++){ con++;
		CargaAcademica materia = lisMateria.get(j);
		
		if(mapaCarreras.containsKey(materia.getCarreraId())){
			facultad = mapaCarreras.get(materia.getCarreraId()).getFacultadId();
		}
		
		promedio = 0; numBajas = 0;
		
		if(mapaAlumnos.containsKey(materia.getCursoCargaId())){
			numAlumnos = Integer.parseInt(mapaAlumnos.get(materia.getCursoCargaId()));
		}
		
		if(mapaAsignados.containsKey(materia.getCursoCargaId())){
			numAsignados = Integer.parseInt(mapaAsignados.get(materia.getCursoCargaId()));
		}

		if(mapaBajas.containsKey(materia.getCursoCargaId())){
			numBajas = Integer.parseInt(mapaBajas.get(materia.getCursoCargaId()));
		}
		
		if( numAlumnos-numBajas <=0 ){
			promedio = 0;
		}else{
			if(mapaPromediosPorMateria.containsKey(materia.getCursoCargaId())){
				promedio = Integer.parseInt(mapaPromediosPorMateria.get(materia.getCursoCargaId()));
			}
		}

		if (materia.getEstado().equals("1")) strEstado = "<font color='#5E610B'><b>Creada</b></font>";
		if (materia.getEstado().equals("2")) strEstado = "<font color='blue'><b>Ordinario</b></font>";
		if (materia.getEstado().equals("3")) strEstado = "<font color='green'><b>Extra.</b></font>";
		if (materia.getEstado().equals("4")) strEstado = "<font color='red'><b>Cerrada</b></font>";
		if (materia.getEstado().equals("5")) strEstado = "<font color='black'><b>Registrada</b></font>";
		
		int AC = 0;
		if(mapaAcreditados.containsKey(materia.getCursoCargaId())){
			AC = Integer.parseInt(mapaAcreditados.get(materia.getCursoCargaId()));
		}
		int NA = 0;
		if(mapaReprobados.containsKey(materia.getCursoCargaId())){
			NA = Integer.parseInt(mapaReprobados.get(materia.getCursoCargaId()));
		}
		
		float NApor = 0;
		if(numAlumnos-numBajas>0){
			NApor = (float)(100*NA)/(float)(numAlumnos-numBajas);
		}
		String facultadCorto = "";
		if (mapaFacultades.containsKey(facultad)){
			facultadCorto = mapaFacultades.get(facultad).getNombreCorto();
		}
		String carreraNombre = "-";
		if (mapaCarreras.containsKey( materia.getCarreraId()) ){
			carreraNombre = mapaCarreras.get(materia.getCarreraId()).getNombreCarrera();
		}		
		String inscritos = "0";
		if (mapaInscritos.containsKey(materia.getCursoCargaId()) ){
			inscritos = mapaInscritos.get(materia.getCursoCargaId());
		}		
		String pendientes = "0";
		if (mapaPendientes.containsKey(materia.getCursoCargaId()) ){
			pendientes = mapaPendientes.get(materia.getCarreraId());
		}
		//System.out.println("Datos:"+materia.getCursoCargaId()+":"+materia.getEstado()+":"+strEstado);
%>
				
	 <tr class="tr2" <%=bgColor%>> 
		<td align="center"><font size="1"><%=con%></font></td>
		<td align="center"><font size="1"><%=materia.getCursoCargaId()%></font></td>
		<td align="left"><font size="1"><%=materia.getNombre() %></font></td>
		<td align="left"><font size="1"><%=facultadCorto %></font></td>
		<td align="left"><font size="1"><%=carreraNombre%></font></td>
		<td align="center"><font size="1"><%=materia.getCiclo() %></font></td>
		<td align="left"><font size="1"><%=materia.getNombreCurso() %></font></td>
		<td align="left"><font size="1"><%=strEstado %></font></td>
		<td align="left"><font size="1"><%=numAlumnos %></font></td>
		<td align="left"><font size="1"><%=numAsignados %></font></td>
		<td align="left"><font size="1"><%=inscritos %></font></td>
		<td align="left"><font size="1"><%=AC%></font></td>
		<td align="left"><font size="1"><%=NA%></font></td>
		<td align="left"><font size="1"><%=getFormato.format(NApor)+"%" %></font></td>
		<td align="left"><font size="1"><%=pendientes %></font></td>
		<td align="left"><font size="1"><%=numBajas %></font></td>
		<td align="left"><font size="1"><%=promedio  %></font></td>
	 </tr>
<% 
	}
%>
	</tbody>
	</table>	
	</form>
</div>