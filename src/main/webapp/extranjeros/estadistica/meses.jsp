<%@ page import= "java.util.Date,java.text.SimpleDateFormat" %>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Estadistica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%		
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String modalidades 			= (String) session.getAttribute("modalidadesReportes");
	String privilegios 			= (String) request.getAttribute("privilegios");
	
	List<Estadistica> lisExtranjeros 				= (List<Estadistica>) request.getAttribute("lisExtranjeros");
	HashMap<String,CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,CatPais> mapaPaises 				= (HashMap<String,CatPais>) request.getAttribute("mapaPaises");
	HashMap<String,String> mapaDocumentos 			= (HashMap<String,String>) request.getAttribute("mapaDocumentos");

	Date fechav;		
	String año 					= aca.util.Fecha.getHoy().substring(6,10);
	String añoTemp				= "";
	String mes 					= "1";
	String mesTemp 				= "";		
	String diasr="",color="";
	Date hoy = new Date();	
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	int dias=0;
	int cont = 1;
	int total = 0;
%>
<div class="container-fluid">
	<h3>VENCIMIENTO DE PASAPORTE</h3>
	<div class="alert alert-info">
		<a href="elegir" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>
	<table class="table table-bordered table-sm">
	<tr> 
  		<th align="center"><strong><spring:message code="aca.Numero"/></strong></th>
  		<th ><strong><spring:message code="aca.Matricula"/></strong></th>
  		<th align="center"><strong><spring:message code="aca.Nombre"/></strong></th>
  		<th align="center"><strong>Nacionalidad</strong></th>
  		<th align="center"><strong>Sexo</strong></th>
  		<th align="center"><strong><spring:message code="aca.Carrera"/></strong></th>
  		<th><strong><spring:message code="aca.Modalidad"/></strong></th>
  		<th align="center"><strong>Pasaporte</strong></th>
  		<th align="center"><strong>Días restantes</strong></th>
	</tr>
<%	
	mes = "0";		
	if(lisExtranjeros.size() != 0){
		String codigoPersonalTmp 	= "";
		for(Estadistica estadistica : lisExtranjeros){				
			if(!estadistica.getCodigoPersonal().equals(codigoPersonalTmp)){
				codigoPersonalTmp 	= estadistica.getCodigoPersonal();
				
				String nacionalidadNombre = "-";
				if (mapaPaises.containsKey(estadistica.getNacionalidad())){
					nacionalidadNombre = mapaPaises.get(estadistica.getNacionalidad()).getNombrePais();
				}
				
				String fechaVence = "01/01/2000";
				if (mapaDocumentos.containsKey(estadistica.getCodigoPersonal()+"3")){
					fechaVence = mapaDocumentos.get(estadistica.getCodigoPersonal()+"3");
				}
				String carreraCorto 	= "";
				String carreraNombre	= "";
				if (mapaCarreras.containsKey(estadistica.getCarreraId())){
					carreraCorto 	= mapaCarreras.get(estadistica.getCarreraId()).getNombreCorto();
					carreraNombre 	= mapaCarreras.get(estadistica.getCarreraId()).getNombreCarrera();
				}
				
				String modalidadNombre = "-";
				if (mapaModalidades.containsKey(estadistica.getModalidadId()) ){
					modalidadNombre = mapaModalidades.get(estadistica.getModalidadId()).getNombreModalidad();
				}
				
				mesTemp 		= fechaVence.substring(3,5);					
				añoTemp 		= fechaVence.substring(6,10);
				if( (privilegios.indexOf(carreraCorto) != -1) || (privilegios.indexOf("*")!= -1)){
					if (añoTemp.equals(año)){						
						if (!mesTemp.equals(mes)){
							mes = mesTemp;
%> 
	<tr><td colspan="9" align="left">&nbsp;</td></tr>
	<tr><td colspan="9" align="center"><><strong>Vencimiento de Pasaporte: <%=aca.util.Fecha.getMesNombre(Integer.parseInt(mes))%> de <%=año%></strong></font></td></tr>
	<tr> 
  		<th align="center"><strong><spring:message code="aca.Numero"/></strong></th>
		<th align="center"><strong><spring:message code="aca.Matricula"/></strong></th>
		<th align="center"><strong><spring:message code="aca.Nombre"/></strong></th>
		<th align="center"><strong>Nacionalidad</strong></th>
		<th align="center"><strong>Genero</strong></th>
		<th align="center"><strong><spring:message code="aca.Carrera"/></strong></th>
		<th><spring:message code="aca.Modalidad"/></th>
		<th align="center"><strong>Pasaporte</strong></th>
		<th align="center"><strong>Días restantes</strong></th>
	</tr>  		
<%   
						}
					}
					dias=0;
					if(fechaVence==null||fechaVence.equals(" ")||fechaVence.equals(""))
						diasr="-";
					else{
						fechav = df.parse(fechaVence);
						dias = new Long((fechav.getTime()-hoy.getTime())/1000/60/60/24).intValue();
						diasr=dias+"";
					}
					if(dias>29 && dias<61)color="#F39603";
					else if(dias<=30)color="#CE0000";
					else color="";
%>

	<tr class="tr2"> 
    	<td align="center"><font color='<%=color%>' size="1"><%=cont%></font></td>
    	<td align="center"><font color='<%=color%>' size="1"><%=estadistica.getCodigoPersonal()%></font></td>
    	<td><font color='<%=color%>' size="1"><%=estadistica.getApellidoPaterno()+" "+estadistica.getApellidoMaterno()+" "+estadistica.getNombre()%></font></td>
    	<td align="left"> <font color='<%=color%>' size="1"><%=nacionalidadNombre%></font></td>
    	<td align="center"><font color='<%=color%>' size="1"><%=estadistica.getSexo().equals("F")?"MUJER":"HOMBRE"%></font></td>
    	<td align="center" class="ayuda mensaje <%=carreraNombre %>"><font color='<%=color%>' size="1"><%=carreraCorto%></font></td>
    	<td><font color='<%=color%>' size="1"><%=modalidadNombre%></font></td>
    	<td align="center"><font color='<%=color%>' size="1"><%=fechaVence%></font></td>
    	<td align="center"><font color='<%=color%>' size="1"><%=diasr%></font></td>
	</tr>
<%
					cont++;	   		
				} // Fin del if de autoriza sector
					total++;
	   	  	}
		}
%>
	</table>	
  	<div class="alert alert-info">Total de Extranjeros: <%=total%></div>
<%	}else{%>
	<br>
	<br>	
  	<div class="alert alert-danger"><strong>No hay Extranjeros inscritos en este bloque</strong></div>		
<%	}%>
</div>