<%@page import="java.text.*" %>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.vista.spring.Estadistica"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<script>
	function openwindow(open){
		window.open(open,"Popup","toolbar=0, location=0, directories=0, status=0, menubar=0, scrollbars=1, resizable=1, width=500, height=400, top=0, left=0")
	}
</script>
<style>
	.promedios td{
		border:1px solid gray;
	}
</style>
<%
	String codigoUsuario	= (String) session.getAttribute("codigoPersonal");		
	String cargaId 			= (String)request.getAttribute("cargaId");
	String cargaNombre 		= (String)request.getAttribute("cargaNombre");
	boolean tieneAcceso		= (boolean)request.getAttribute("tieneAcceso");
	Acceso acceso 			= (Acceso)request.getAttribute("acceso");
	
	String sBgcolor			= "";	
		
	List<Estadistica> lisInscritos 	=  (List<Estadistica>) request.getAttribute("lisInscritos");
	
	HashMap<String, CatFacultad> mapaFacultades	= (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultades"); 
	HashMap<String, CatCarrera>	mapaCarreras	= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, String>		mapaPromedios	= (HashMap<String, String>)request.getAttribute("mapaPromedios");
	
	DecimalFormat getformato= new DecimalFormat("##0.00;(##0.00)");
	
	String codigoAlumno		= "X";	
	String facultad			= "X";	
	String carreraTemp		= "X";	
	String promedio			= "X";
	
	int nInscritos	= 0, nCalculos		= 0;
	int nHombres	= 0, nMujeres		= 0;
	int nInternos	= 0, nExternos		= 0;
	int nNacional	= 0, nExtranjero	= 0;

	int cont=1, nRow, nNota, nCreditos, nTemp, nPonderado;
	double dPromedio=0;	
%>
<div class="container-fluid">
	<h1>Promedio por Cargas</h1>	
	<div class="alert alert-info">
		<a href="promedio?cargaId=<%=cargaId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> &nbsp; &nbsp; Carga: <%=cargaId%> - <%=cargaNombre%>
	</div>	
	<table class="table table-bordered">
	<thead class="table-info">
	<tr> 
    	<th width="3%" align="center"><spring:message code="aca.Numero"/></th>
    	<th width="17%" align="center"><spring:message code="aca.Facultad"/></th>
    	<th width="20%" align="center"><spring:message code="aca.Carrera"/></th>
    	<th width="8%" align="center" class="button" title='Ordenar por Matrícula' onclick="location.href='promedio?cargaId=<%=cargaId%>&Ordenar=1'"><spring:message code="aca.Matricula"/></th>
    	<th width="14%" align="center" class="button" title='Ordenar por Apellidos' onclick="location.href='promedio?cargaId=<%=cargaId%>&Ordenar=2'">Apellidos</th>
    	<th width="14%" align="center" class="button" title='Ordenar por Nombre' onclick="location.href='promedio?cargaId=<%=cargaId%>&Ordenar=3'"><spring:message code="aca.Nombre"/></th>
    	<th width="5%" align="center" class="button" title='Ordenar por Residencia' onclick="location.href='promedio?cargaId=<%=cargaId%>&Ordenar=4'">Resi.</th>
    	<th width="4%" align="center" class="button" title='Ordenar por Sexo' onclick="location.href='promedio?cargaId=<%=cargaId%>&Ordenar=5'"><spring:message code="aca.Genero"/></th>
    	<th width="4%" style="text-align:right"><spring:message code="aca.Promedio"/></th>
    	<th width="4%" style="text-align:right">¿Vale?</th>
  	</tr>
  	</thead>
<%
	double promCarr 	= 0;
	int contAlumnos 	= 0;
	boolean verTotal 	= false;
	boolean promedia	= false;
	
	if (tieneAcceso ){	
		java.util.Iterator iter = lisInscritos.iterator();
		while(iter.hasNext()){
			Estadistica inscrito = (Estadistica)iter.next();
			
			codigoAlumno = inscrito.getCodigoPersonal();
			
			if( (acceso.getAccesos().indexOf(inscrito.getCarreraId()) != -1) || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")){
				nInscritos++;
				if (inscrito.getSexo().equals("M")){ nHombres++; }else{ nMujeres++; }
				if (inscrito.getResidenciaId().equals("I")){ nInternos++; }else{ nExternos++; }
				
				String nomFacultad = "";
				if(mapaFacultades.containsKey(inscrito.getFacultadId())){
					nomFacultad = mapaFacultades.get(inscrito.getFacultadId()).getNombreFacultad();
				}
				
				String nomCarrera  = "-";
	   			if(mapaCarreras.containsKey(inscrito.getCarreraId())){
					nomCarrera = mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
				}         		
				
          		if (mapaPromedios.containsKey(codigoAlumno)){
        			dPromedio = Double.parseDouble(mapaPromedios.get(codigoAlumno));
        			        			
        			if (dPromedio >= 2){
        				promedia = true;
        			}else{
        				promedia = false;
        			}
        		}
          		
          		// solamente considera los promedios semestrales mayores o iguales a 2 puntos
          		if (promedia){
          			promCarr += dPromedio;
          			contAlumnos ++;          			
          		}
%>		
	<tr class="tr2"> 
    	<td height="21"><%=cont%></td>
    	<td height="24"><%=nomFacultad%></td>
    	<td height="21"><%=nomCarrera%></td>
    	<td align="center"><%=codigoAlumno%></td>
    	<td><%=inscrito.getApellidoPaterno()+" "+inscrito.getApellidoMaterno()%></td>
    	<td><%=inscrito.getNombre() %></td>
    	<td align="center"><%= inscrito.getResidenciaId().equals("E") ? "Externo" : "Interno" %></td>
    	<td align="center"><% if (inscrito.getSexo().equals("M")) out.print("Hombre"); else out.print("Mujer"); %></td>
    	<td style="text-align:right;">
	    	<%=getformato.format(dPromedio)%>
		</td>
		<td style="text-align:right"><%=promedia==true?"SI":"NO"%></td>
	</tr>  
<%
    			cont++;
			} // fin de if esta autorizado a ver la carrera
		}// fin del while
%>	
	</table>
<%	
// 	cargaU		= null;
%> 
	<table style="width:95%">
	<tr> 
    	<th width="12%"><font size="1">Inscritos: <%=nInscritos%></font></th>
    	<th width="16%"><font size="1">Internos: <%=nInternos%></font></th>
    	<th width="14%" align="center"><font size="1">Externos: <%=nExternos%></font></th>
	</tr>
	</table>
<%	} %>
</div>