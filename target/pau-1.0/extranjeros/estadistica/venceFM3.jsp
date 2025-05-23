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
	String year 				= aca.util.Fecha.getHoy().substring(6,10);
	String yearTemp				= "";
	String mes 					= "1";
	String mesTemp 				= "";	
		
	String diasr="",color="";
	Date hoy = new Date();	
	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	int dias=0;
	int cont = 1;
	int n_total = 0;
%>
<div class="container-fluid">
	<h3>VENCIMIENTO DE FM3</h3>
	<div class="alert alert-info">
		<a href="elegir" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>
	<table class="table table-bordered  table-sm">
	<tr class="text-center"> 
  		<th class="text-center"><spring:message code="aca.Numero"/></th>
  		<th align="center"><spring:message code="aca.Matricula"/></th>
  		<th align="center"><spring:message code="aca.Nombre"/></th>
  		<th align="center">Nacionalidad</th>
  		<th align="center">Sexo</strong></th>
  		<th align="center"><spring:message code="aca.Carrera"/></th>
  		<th><spring:message code="aca.Modalidad"/></th>
  		<th align="center">FM3</th>
  		<th align="center">D�as restantes</th>
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
				
				String fechaVence = "2000/01/01";
				if (mapaDocumentos.containsKey(estadistica.getCodigoPersonal()+"2")){
					fechaVence = mapaDocumentos.get(estadistica.getCodigoPersonal()+"2");
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
				mesTemp 		= fechaVence.substring(5,7);					
				yearTemp 		= fechaVence.substring(0,4);
				//System.out.println("Datos:"+fechaVence+":"+mesTemp+":"+yearTemp);
				if( (privilegios.indexOf(carreraCorto) != -1) || (privilegios.indexOf("*")!= -1)){
					if (yearTemp.equals(year)){						
						if (!mesTemp.equals(mes)){
							mes = mesTemp;
%> 
	<tr><td colspan="9" align="left">&nbsp;</td></tr>
	<tr><td colspan="9" align="center"><font size="2"><strong>Vencimiento de FM3: <%=aca.util.Fecha.getMesNombre(Integer.parseInt(mes))%> de <%=year%></strong></font></td></tr>
	<tr> 
  		<th class="text-center"><strong><spring:message code="aca.Numero"/></strong></th>
		<th align="center"><strong><spring:message code="aca.Matricula"/></strong></th>
		<th align="center"><strong><spring:message code="aca.Nombre"/></strong></th>
		<th align="center"><strong>Nacionalidad</strong></th>
		<th align="center"><strong>Genero</strong></th>
		<th align="center"><strong><spring:message code="aca.Carrera"/></strong></th>
		<th><spring:message code="aca.Modalidad"/></th>
		<th align="center"><strong>FM3</strong></th>
		<th align="center"><strong>D�as restantes</strong></th>
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
				n_total++;
			}
		}
%>
	</table>
	<table>
  	<tr> 
    	<td style="text-align:center;"><h3>Total de Extranjeros: <%=n_total%></h3></td>    
  	</tr>
	</table>

<%	}else{%>
	<br>
	<br>
	<center>
  	<font color="white" size="1"><strong>No hay Extranjeros inscritos en este bloque</strong></font>
	</center>	
<%	}%>
</div>