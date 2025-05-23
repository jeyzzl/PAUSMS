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
	boolean tieneAcceso		= (boolean)request.getAttribute("tieneAcceso");
	Acceso acceso 			= (Acceso)request.getAttribute("acceso");
	
	String sBgcolor			= "";	
	
	
	List<Carga> lisCargas 			= (List<Carga>)	request.getAttribute("lisCargas");	
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
	<h2>Promedio por Cargas <small class="text-muted fs-4"> ( La formula del Promedio Ponderado es:  Sumatoria(nota X creditos) / Sumatoria(creditos) )</small></h2>
	<form name="forma" action="promedio" method="post">
	<div class="alert alert-info">
		Carga: [ <%=cargaId%> ] 
   		<select name="cargaId" onchange='document.forma.submit()' class="input input-xlarge" style="width:350px">
<%
		for(Carga carga : lisCargas){						
%>			<option value='<%=carga.getCargaId()%>' <%if (cargaId.equals(carga.getCargaId())) out.print("selected");%>>
			<%=carga.getCargaId()%>-<%=carga.getNombreCarga()%>
			</option>
<%		}
%>		</select>
		&nbsp;&nbsp;
		<a href="promedioprint?cargaId=<%=cargaId%>" class="btn btn-primary">Print</a>	
	</div>
	</form>	
	<table class="table table-bordered">
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
			String nomCarrera  = "-";
			String nomFacultad = "";
			if( (acceso.getAccesos().indexOf(inscrito.getCarreraId()) != -1) || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")){
				nInscritos++;
				if (inscrito.getSexo().equals("M")){ nHombres++; }else{ nMujeres++; }
				if (inscrito.getResidenciaId().equals("I")){ nInternos++; }else{ nExternos++; }
				
				// Pinta los totales cuando cambia la facultad o la carrera
				if ( !facultad.equals(inscrito.getFacultadId()) || !inscrito.getCarreraId().equals( carreraTemp) ){
					if (verTotal){
	   					out.print("<tr class='promedios'>");	   					
	   					out.print("<th colspan='6'>Promedio aritmético</th>");
	   					out.print("<th style='text-align:right;'>"+getformato.format(promCarr/contAlumnos)+"</th>");
	   					out.print("<th '>&nbsp;</th>");
	   					out.print("</tr>");	   					
	   				}else{
	   					verTotal = true;
	   				}
				}

				if(!facultad.equals(inscrito.getFacultadId())){
		   	 		facultad = inscrito.getFacultadId();
		   	 	
					if(mapaFacultades.containsKey(inscrito.getFacultadId())){
						nomFacultad = mapaFacultades.get(inscrito.getFacultadId()).getNombreFacultad();
					} 
%>
		<thead>
		<tr>
	    	<td style="text-align:left" colspan="8"><h2><%=nomFacultad%> <small class="text-muted fs-5">(Para ordenar la lista por <b>matrícula</b>, <b>apellido</b>, <b>nombre</b>, <b>residencia</b> o <b>género</b> haga clic sobre el título correspondiente)</small></h2></td>
	  	</tr>  	
	  	</thead>
<%					
       			}//fin del if de facultades diferentes       			
       			
				if(!inscrito.getCarreraId().equals( carreraTemp)){
	   				carreraTemp = inscrito.getCarreraId();
	   				
	   				if(mapaCarreras.containsKey(inscrito.getCarreraId())){
						nomCarrera = mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
					}
	   				
	   				// Inicializa los valores
   					promCarr 		= 0;
   					contAlumnos 	= 0;
	   				
	   				
%>	
		<thead>
	  	<tr><td class="titulo3" colspan="8">Programa: <%=nomCarrera%></td></tr>
	  	</thead>
		<thead class="table-info">
	  	<tr> 
	    	<th width="3%" align="center"><spring:message code="aca.Numero"/></th>
	    	<th width="8%" align="center" class="button" title='Ordenar por Matrícula' onclick="location.href='promedio?cargaId=<%=cargaId%>&Ordenar=1'"><spring:message code="aca.Matricula"/></th>
	    	<th width="20%" align="center" class="button" title='Ordenar por Apellidos' onclick="location.href='promedio?cargaId=<%=cargaId%>&Ordenar=2'">Apellidos</th>
	    	<th width="19%" align="center" class="button" title='Ordenar por Nombre' onclick="location.href='promedio?cargaId=<%=cargaId%>&Ordenar=3'"><spring:message code="aca.Nombre"/></th>
	    	<th width="5%" align="center" class="button" title='Ordenar por Residencia' onclick="location.href='promedio?cargaId=<%=cargaId%>&Ordenar=4'">Resi.</th>
	    	<th width="8%" align="center" class="button" title='Ordenar por Sexo' onclick="location.href='promedio?cargaId=<%=cargaId%>&Ordenar=5'"><spring:message code="aca.Genero"/></th>
	    	<th width="7%" class="right"><spring:message code="aca.Promedio"/></th>
	    	<th width="5%" align="center">¿Vale?</th>
	  	</tr>  
	  	</thead>
<%
          			cont = 1;
          		}//fin del if de carreras diferentes
				
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
	    	<td width="3%" height="21"><%=cont%></td>
	    	<td width="8%" align="center"><%=codigoAlumno%></td>
	    	<td width="20%"><%=inscrito.getApellidoPaterno()+" "+inscrito.getApellidoMaterno()%></td>
	    	<td width="19%"><%=inscrito.getNombre() %></td>
	    	<td width="5%" align="center"><%= inscrito.getResidenciaId().equals("E") ? "Externo" : "Interno" %></td>
	    	<td width="8%" align="center"><% if (inscrito.getSexo().equals("M")) out.print("Hombre"); else out.print("Mujer"); %></td>
	    	<td width="7%" style="text-align:right;">
		  	<a href="javascript:openwindow('promedio_alumno?matricula=<%=codigoAlumno%>&carga=<%=cargaId%>&promedio=<%=getformato.format(dPromedio)%>')">
		    	<%=getformato.format(dPromedio)%>
		  	</a>
			</td>
			<td width="8%" align="center"><%=promedia==true?"SI":"NO"%></td>
		</tr>  
<%
    			cont++;
			} // fin de if esta autorizado a ver la carrera
		}// fin del while
%>
		<tr class="promedios">
			<th colspan="6">Promedio aritmético</th>
			<th style="text-align:right;"><%=getformato.format(promCarr/contAlumnos) %></th>
			<th>&nbsp;</td>
		</tr>	
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
<div align="center"><font size="3"> <br><b><font color="#000099">Fin del Listado</font> </b> </font></div>
<%	}//fin if  de acceso %>
</div>