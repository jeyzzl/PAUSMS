<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.reportes.spring.AltasBajas"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import="aca.carga.spring.CargaBloque"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function cambiaPeriodo(periodoId){
		document.location.href="bajas?PeriodoId="+periodoId+"&cambioPeriodo=1";
	}
	
	function cambiaCarga(){		
		document.location.href="bajas?CargaId="+document.forma.CargaId.value+"&cambioCarga=S";
	}	
	
	function Show( ){			
		document.forma.Accion.value = "1";
		document.forma.submit();
	}	
</script>
<%	
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String periodoId	 	= (String) request.getAttribute("periodoId");
	String cargaId		 	= (String) request.getAttribute("cargaId");
	String bloqueId			= (String) request.getAttribute("bloqueId");
	Acceso acceso 			= (Acceso) request.getAttribute("acceso");
	String facultadTemp		= "X";
	String carreraTemp 		= "X";

	List<CatPeriodo> lisPeriodos 	= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas           = (List<Carga>) request.getAttribute("lisCargas");
	List<CargaBloque> lisBloques	= (List<CargaBloque>)request.getAttribute("lisBloques");
	List<AltasBajas> lisBajas 		= (List<AltasBajas>) request.getAttribute("lisBajas") ;
	
	HashMap<String,CatFacultad> mapaFacultad  		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultad");
	HashMap<String,CatCarrera>  mapaCarrera   		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarrera");	
	HashMap<String,String>  mapaCreditosEnCalculo   = (HashMap<String,String>) request.getAttribute("mapaCreditosEnCalculo");	
	HashMap<String,String>  mapaNumCursosAltas   	= (HashMap<String,String>) request.getAttribute("mapaNumCursosAltas");	
	HashMap<String,String>  mapaNumCursosBajas   	= (HashMap<String,String>) request.getAttribute("mapaNumCursosBajas");	
	
	int contCalculo			= 0;
	int contAltas			= 0;
	int contBajas			= 0;
	int contador			= 0;		
%>
<div class="container-fluid">
	<h1>Reporte de Bajas</h1>
	<form name="forma" action="bajas" method="post">
	<input type="hidden" name="Accion">
	<div class="alert alert-info d-flex align-items-center">
		<b>Período:</b>&nbsp;
        <select onchange='javascript:cambiaPeriodo(this.value);' name="PeriodoId" class="form-select" style="width:140px;">
<%	
	for(CatPeriodo periodo : lisPeriodos){
%>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print("Selected");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%	} %>
        </select> &nbsp;&nbsp;
    	<b>Carga:</b>&nbsp;
        <select onchange='javascript:cambiaCarga();' name="CargaId" style="width:350px;" class="form-select">
<%	
	for(Carga carga : lisCargas){
%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print("Selected");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%	}%>		    
       	</select> &nbsp;&nbsp;			
		<b>Bloque:</b>&nbsp;
		<select name="BloqueId" onchange="javascript:recargar()" class="form-select" style="width:230px">
<%
		for (aca.carga.spring.CargaBloque bloque : lisBloques){
			if (bloque.getCargaId().equals(cargaId)){
%>		
			<option value="<%=bloque.getBloqueId()%>" <%=bloqueId.equals(bloque.getBloqueId())?" Selected":""%>><%=bloque.getBloqueId()%>-<%=bloque.getNombreBloque()%></option>
<%	
			}
		} 
%>			
		</select>
		&nbsp;&nbsp;
		<input class="btn btn-primary" type="button" value="Mostrar" onclick="javascript:Show();"/>        
	</div>
	</form>
	<table class="table table-bordered">
<%	
	for(AltasBajas ab : lisBajas){
		if (acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || acceso.getAccesos().indexOf(ab.getCarreraId()) != -1){
			if(!facultadTemp.equals(ab.getFacultadId())){
					
				// Insertar renglon en blanco
				if (!facultadTemp.equals("X")){
					out.print("<tr> <td colspan='7'>&nbsp;</td></tr>");
				}
					
				facultadTemp = ab.getFacultadId();
				
				String nombreFacultad = "-";
				if(mapaFacultad.containsKey(ab.getFacultadId()) ){
					nombreFacultad = mapaFacultad.get(ab.getFacultadId()).getNombreFacultad();
				}
%>
		<thead>
  		<tr> 
  			<td align="center" colspan="7"> <h2><%=nombreFacultad%></h2></td>
  		</tr>
	  	</thead>
<%
			}
			if(!carreraTemp.equals(ab.getCarreraId())){
			carreraTemp = ab.getCarreraId();
			
			String nombreCarrera = "-";
			if(mapaCarrera.containsKey(ab.getCarreraId()) ){
				nombreCarrera = mapaCarrera.get(ab.getCarreraId()).getNombreCarrera();
			}
%>
		<thead>
		<tr> 
	    	<td height="28" colspan="7"><b>Programa: <%=nombreCarrera %></b></td>
	  	</tr>
	  	</thead>
		<thead class="table-info">
	  	<tr> 
	    	<th width="2%" height="16" align="center"><spring:message code="aca.Numero"/></th>
	    	<th width="4%" height="16" align="center"><spring:message code="aca.Plan"/></th>
	    	<th width="7%" height="16" align="center"><spring:message code="aca.Matricula"/></th>
	    	<th width="42%" height="16" align="center"><spring:message code="aca.Nombre"/></th>
	    	<th width="8%" height="16" class="right">Crd.Calc.</th>
	    	<th width="8%" height="16" class="right">Crd.Alta</th>
	    	<th width="8%" height="16" class="right">Crd.Baja</th>
	  	</tr>
	  	</thead>
  <%
				contador = 1;
			}
			
			String calculo = "0";
			if(mapaCreditosEnCalculo.containsKey(ab.getMatricula()) ){
				calculo = mapaCreditosEnCalculo.get(ab.getMatricula());
			}

			String altas = "0";
			if(mapaNumCursosAltas.containsKey(ab.getMatricula())){
				altas = mapaNumCursosAltas.get(ab.getMatricula());
			}
			
			String bajas = "0";
			if(mapaNumCursosBajas.containsKey(ab.getMatricula())){
				bajas = mapaNumCursosBajas.get(ab.getMatricula());
			}

			contCalculo = contCalculo + Integer.parseInt(calculo);
			contAltas	= contAltas + Integer.parseInt(altas);
			contBajas	= contBajas + Integer.parseInt(bajas);
%>
		<tr class="tr2"> 
	    	<td width="2%" height="20"><%=contador%></td>
	    	<td width="4%" align="center"><%=ab.getPlanId()%></td>
	    	<td width="7%"><%=ab.getMatricula()%></td>
	    	<td width="42%"><%=ab.getNombre()%></td>
	    	<td width="8%" style="text-align:right"><%=calculo%></td>
	    	<td width="8%" style="text-align:right"><%=altas%></td>
	    	<td width="8%" style="text-align:right"><%=bajas%></td>
	  	</tr>
<%
			contador++;	
		}
		
	} // fin del for
%>		
<% 	if (lisBajas.size()>0){%>
		<tr><td colspan="7">&nbsp;</td></tr>			
	  	<tr> 
	    	<th width="2%" height="22" colspan="4">Totales . . .</th>
		    <th width="8%" style="text-align:right"><%=contCalculo%></th>
		    <th width="8%" style="text-align:right"><%=contAltas%></th>
		    <th width="8%" style="text-align:right"><%=contBajas%></th>
	  	</tr>
<%	}else{%>
		<tr><td colspan="7">No se encontraron resultados...</td></tr>	  	
<%	}%>
	</table>
</div>	
<%
	lisBajas = null;
%>
