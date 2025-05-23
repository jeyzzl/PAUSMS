<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.reportes.spring.AltasBajas"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp" %>


<jsp:useBean id="altasUtil" scope="page" class="aca.reportes.AltasBajasUtil"/>
<jsp:useBean id="cargaUtil" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="Acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="cUtil" scope="page" class="aca.catalogo.CarreraUtil"/>
<jsp:useBean id="fUtil" scope="page" class="aca.catalogo.FacultadUtil"/>
<jsp:useBean id="CatPeriodoUtil" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>


<script type="text/javascript">
	function cambiaPeriodo(periodoId){
		document.location.href="listado?PeriodoId="+periodoId+"&cambioPeriodo=1";
	}
	
	function cambiaCarga(){		
		document.location.href="listado?CargaId="+document.forma.CargaId.value+"&cambioCarga=S";
	}

	function refrescar(){
		document.forma.submit();
	}
	
	function Buscar( ){			
		document.forma.Accion.value = "1";
		document.forma.submit();
	}
	
</script>
<%	
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	String accion 					= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	String periodoId				= (String)request.getAttribute("periodoId");
	String cargaId					= (String)request.getAttribute("cargaId");
	String codigoPersonal			= (String)request.getAttribute("codigoPersonal");
	
	String matricula		= "";
	String accesoAdmin		= "";
	String accesoSupervisor	= "";
	String accesoCarrera	= "";
	String facultadTemp		= "";
	String carreraTemp		= "";
	String estadoAlumno		= "0";
	String colorFondo		= "";
	
	int calculo				= 0;
	int origen				= 0;
	int altas				= 0;
	int bajas				= 0;	
	int total 				= 0;
	
	int matCalculo			= 0;
	int matOrigen			= 0;
	int matAlta				= 0;
	int matBaja				= 0;
	int matTotal 			= 0;
	
	int contCalculo			= 0;
	int	contOrigen			= 0;
	int contAltas			= 0;
	int contBajas			= 0;
	int contTotal			= 0;
	
	int totMatC				= 0;
	int totMatO				= 0;
	int totMatA				= 0;
	int totMatB				= 0;
	int totMat				= 0;
	
	double costoAltas		= 0;
	double costoBajas		= 0;
	double costoRealAlta	= 0;
	double costoRealBaja	= 0;
	
	double totAltas			= 0;
	double totBajas			= 0;
	double totRealAlta		= 0;
	double totRealBaja		= 0;
	
	int contador			= 0;
	
	Acceso acceso 						= (Acceso)request.getAttribute("acceso");	
	List<CatPeriodo> lisPeriodos 	 	= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 				= (List<Carga>)request.getAttribute("lisCargas");	
	List<AltasBajas> lisAltas			= (List<AltasBajas>)request.getAttribute("lisAltas");
	
	// Map de costos por credito en la carga
	HashMap<String, String> mapCostoCredito 			= (HashMap<String, String>)request.getAttribute("mapCostoCredito");	
	HashMap<String, String> mapMatCalculo 				= (HashMap<String, String>)request.getAttribute("mapMatCalculo");
 	HashMap<String, String> mapMatOrigen 				= (HashMap<String, String>)request.getAttribute("mapMatOrigen");
 	HashMap<String, String> mapMatAlta 					= (HashMap<String, String>)request.getAttribute("mapMatAlta");
 	HashMap<String, String> mapMatBaja 					= (HashMap<String, String>)request.getAttribute("mapMatBaja"); 	 	
 	HashMap<String, String> mapCredCalculo 				= (HashMap<String, String>)request.getAttribute("mapCreditosCalculo");
 	HashMap<String, String> mapCredOrigen 				= (HashMap<String, String>)request.getAttribute("mapCreditosOrigen");
 	HashMap<String, String> mapCredAlta 				= (HashMap<String, String>)request.getAttribute("mapCreditosAlta");
 	HashMap<String, String> mapCredBaja 				= (HashMap<String, String>)request.getAttribute("mapCreditosBaja");
 	HashMap<String, CatFacultad> mapaFacultades			= (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultades");
 	HashMap<String, CatCarrera> mapaCarreras			= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras"); 	
 	HashMap<String, String> mapaModalidadesEnCarga 		= (HashMap<String, String>)request.getAttribute("mapaModalidadesEnCarga");
	HashMap<String, String> mapModalidades 				= (HashMap<String, String>)request.getAttribute("mapModalidades");	
%>
<html>	
<div class="container-fluid">
	<h1>Reporte de Altas y Bajas(con número de créditos)</h1>
	<form name="forma" action="listado" method="post">
	<input type="hidden" name="Accion" value="0">
	<div class="alert alert-info">	
         	<b>Período:</b>&nbsp;
         	<select name="PeriodoId" class="input input-medium" onchange="javascript:refrescar();">
<%		for(CatPeriodo periodo : lisPeriodos){%>
				<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print("Selected");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		}%>
          	</select> &nbsp;
	        <b>Carga:</b>&nbsp;
	        <select name="CargaId" style="width:350px;" class="input input-xlarge" onchange="javascript:refrescar();">
<%		for(Carga carga : lisCargas){%>
				<option <%if(cargaId.equals(carga.getCargaId()))out.print("Selected");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%		}%>				
         	</select> &nbsp;
         	<input class="btn btn-primary" type="button" value="Mostrar" onclick="javascript:Buscar();"/>
         	<p id="txt" valign="middle" style=" letter-spacing:1px;"></p>
	</div>
	</form>
	<table style="width:98%" >		
 <% 		
	for(AltasBajas ab : lisAltas){		
		
		if(acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || acceso.getAccesos().indexOf(ab.getCarreraId())!=-1){	
			
			if (mapCredAlta.containsKey(ab.getMatricula()) || mapCredBaja.containsKey(ab.getMatricula())){	
				
			if(!facultadTemp.equals(ab.getFacultadId())){
				facultadTemp = ab.getFacultadId();
			
			String nombreFacultad = "";
			if(mapaFacultades.containsKey(ab.getFacultadId())){
				nombreFacultad = mapaFacultades.get(ab.getFacultadId()).getNombreFacultad();
			}			
 %>
	</table>
	<table class="table table-bordered">
	<thead>
		<tr>
			<td align="center" colspan="20" class="titulo"> <b><font size="3"><%=nombreFacultad%></font></b> </td>
		</tr>
	</thead>
		<%	}
			if(!carreraTemp.equals(ab.getCarreraId())){
				carreraTemp = ab.getCarreraId();
			
			String nombreCarrera = "";
			if(mapaCarreras.containsKey(ab.getCarreraId())){
				nombreCarrera = mapaCarreras.get(ab.getCarreraId()).getNombreCarrera();
			}
		
		%>
	<thead>
		<tr>
		   	<td colspan="20">&nbsp;</td>
		</tr>
	</thead>
	<thead>
		<tr class="alert alert-info">
		   	<td height="20" colspan="4" class="titulo3 center"><b><%=nombreCarrera%></b></td>
		   	<td height="20" colspan="5" class="center"><b>C &nbsp; r &nbsp; é &nbsp; d &nbsp; i &nbsp; t &nbsp; o &nbsp;s</b></td>
		   	<td height="20" colspan="5" class="center"><b>M &nbsp; a &nbsp; t &nbsp; e &nbsp; r &nbsp; i &nbsp; a &nbsp;s</b></td>
		   	<td height="20" colspan="5" class="center"><b>C &nbsp; a &nbsp; n &nbsp; t &nbsp; i &nbsp; d &nbsp; a &nbsp; d &nbsp; e &nbsp; s</b></td>
			<td height="20" align="center"><b><font color="#000099">&nbsp;</font></b></td>
		</tr>
	</thead>
	<thead class="table-info">
		<tr>
	    	<th width="3%" height="16" align="center"><spring:message code="aca.Numero"/></th>
	    	<th width="7%" height="16" align="center"><spring:message code="aca.Plan"/></th>
	    	<th width="7%" height="16" align="center">MatrÍcula</th>
	    	<th width="35%" height="16" align="center"><spring:message code="aca.Nombre"/></th>
	    	<th width="35%" height="16" align="center">Modalidad</th>
	    	<th width="4%" height="16" style="text-align:right">C.C.</th>
	    	<th width="4%" height="16" style="text-align:right">C.O</th>
	    	<th width="4%" height="16" style="text-align:right">C.A.</th>
	    	<th width="4%" height="16" style="text-align:right">C.B.</th>
	    	<th width="4%" height="16" style="text-align:right">T.C.</th>
	    	<th width="4%" height="16" style="text-align:right">M.C.</th>
	    	<th width="4%" height="16" style="text-align:right">M.O.</th>
	    	<th width="4%" height="16" style="text-align:right">M.A.</th>
	    	<th width="4%" height="16" style="text-align:right">M.B.</th>
	    	<th width="4%" height="16" style="text-align:right">T.M.</th>
	    	<th width="4%" height="16" style="text-align:right">$Cred.</th>
	    	<th width="4%" height="16" style="text-align:right">$Altas</th>
	    	<th width="4%" height="16" style="text-align:right">$Bajas</th>
	    	<th width="4%" height="16" style="text-align:right">$Neto Alta</th>
	    	<th width="4%" height="16" style="text-align:right">$Neto Baja</th>
<%-- 	    	<th width="8%" height="16" style="text-align:right"><spring:message code="aca.Estado"/></th> --%>
		</tr>
	</thead>
<%				
				contador = 1;
			}
			
			calculo			= 0;
			if (mapCredCalculo.containsKey(ab.getMatricula())){
				calculo = Integer.parseInt(mapCredCalculo.get(ab.getMatricula()));
			}
			
			origen			= 0;			
			if (mapCredOrigen.containsKey(ab.getMatricula())){
				origen = Integer.parseInt(mapCredOrigen.get(ab.getMatricula()));
			}
			
			altas			= 0;
			if (mapCredAlta.containsKey(ab.getMatricula())){
				altas = Integer.parseInt(mapCredAlta.get(ab.getMatricula()));
			}
			
			bajas			= 0;
			if (mapCredBaja.containsKey(ab.getMatricula())){
				bajas = Integer.parseInt(mapCredBaja.get(ab.getMatricula()));
			}
			
			total 		= (origen + altas) - bajas;
			
			matCalculo = 0;
			if (mapMatCalculo.containsKey(ab.getMatricula())){
				matCalculo = Integer.parseInt(mapMatCalculo.get(ab.getMatricula()));
			}			
			
			matOrigen	= 0;
			if (mapMatOrigen.containsKey(ab.getMatricula())){
				matOrigen = Integer.parseInt(mapMatOrigen.get(ab.getMatricula()));
			}

			matAlta	= 0;
			if (mapMatAlta.containsKey(ab.getMatricula())){
				matAlta = Integer.parseInt(mapMatAlta.get(ab.getMatricula()));
			}
			
			matBaja		= 0;
			if (mapMatBaja.containsKey(ab.getMatricula())){
				matBaja = Integer.parseInt(mapMatBaja.get(ab.getMatricula()));
			}			
			matTotal		= matOrigen+matAlta-matBaja;		
			
			contCalculo 	= contCalculo + calculo;
			contOrigen		= contOrigen + origen;
			contAltas		= contAltas + altas;
			contBajas		= contBajas + bajas;
			contTotal 		= contTotal + total;
			
			totMatC			= totMatC + matCalculo;
			totMatO			= totMatO + matOrigen;
			totMatA			= totMatA + matAlta;
			totMatB			= totMatB + matBaja;
			totMat			= totMat  + matTotal;
			
			double costoCredito = 0;
			if (mapCostoCredito.containsKey(ab.getMatricula()+cargaId)){
				costoCredito = Double.valueOf(mapCostoCredito.get(ab.getMatricula()+cargaId));
			}			
			costoAltas 	= altas * costoCredito;
			costoBajas 	= bajas * costoCredito;
			
			costoRealAlta = 0; costoRealBaja = 0;
			if (total-calculo >= 0)
				costoRealAlta 	= (total-calculo) * costoCredito;
			else
				costoRealBaja  	= (calculo-total) * costoCredito;
			
			totAltas 	+= costoAltas;
			totBajas	+= costoBajas;			
			totRealAlta	+= costoRealAlta;
			totRealBaja	+= costoRealBaja;
			
			if(calculo!=origen){
				colorFondo = "bgcolor= '#FA5858'";
			}
			else if(estadoAlumno.length() > 3 && estadoAlumno.substring(3,4).equals("3")) {
				colorFondo = "bgcolor= '#FA5858'";
			}
			else{
				colorFondo = " ";
			}			

			String modalidad = "";
			if (mapaModalidadesEnCarga.containsKey(ab.getMatricula())){
				modalidad = mapaModalidadesEnCarga.get(ab.getMatricula());
				if(modalidad.contains(",")){
					String[] modaArray = modalidad.split(",");
					modalidad = "";
					for(String mo : modaArray){
						if(mapModalidades.containsKey(mo)){
							if(!modalidad.contains(mapModalidades.get(mo))){
								modalidad +=","+mapModalidades.get(mo);
							}
						}
					}
					modalidad = modalidad.substring(1);
				}else{
					if(mapModalidades.containsKey(modalidad)){
						modalidad = mapModalidades.get(modalidad);
					}
				}
			}			
%>
		<tr class="tr2" <%=colorFondo%>>
	    	<td width="2%" height="20"><%=contador%></td>
	    	<td width="3%" align="center"><%=ab.getPlanId() %></td>
	    	<td width="7%"><%=ab.getMatricula()%></td>
	    	<td width="29%"><%=ab.getNombre() %></td>
	    	<td width="29%"><%=modalidad %></td>
	    	<td width="4%" style="text-align:right"><%=calculo%></td>
	    	<td width="3%" style="text-align:right"><%=origen%></td>
	    	<td width="5%" style="text-align:right"><%=altas%></td>
	    	<td width="5%" style="text-align:right"><%=bajas%></td>
	    	<td width="4%" style="text-align:right"><%=total%></td>   
	    	<td width="4%" style="text-align:right"><%=matCalculo %></td>
			<td width="4%" style="text-align:right"><%=matOrigen%></td>
			<td width="4%" style="text-align:right"><%=matAlta%></td>
			<td width="4%" style="text-align:right"><%=matBaja%></td>	
	    	<td width="5%" style="text-align:right"><%=matTotal%></td>
	    	<td width="5%" style="text-align:right"><%=getFormato.format(costoCredito)%></td>
	    	<td width="5%" style="text-align:right"><%=getFormato.format(costoAltas)%></td>
	    	<td width="5%" style="text-align:right"><%=getFormato.format(costoBajas)%></td>
	    	<td width="5%" style="text-align:right"><%=getFormato.format(costoRealAlta)%></td>
	    	<td width="5%" style="text-align:right"><%=getFormato.format(costoRealBaja)%></td>
<%-- 	    	<td width="4%" style="text-align:right"><%=estadoAlumno%></td> --%>
		</tr>
<%
		}
			contador++;

			matCalculo	= 0;
			matOrigen	= 0;
			matAlta		= 0;
			matBaja		= 0;
			matTotal	= 0;
		}

	} // fin del for 
		%>
	  	<tr><td colspan="20">&nbsp;</td></tr>
	  	<tr>
	    	<th height="22" colspan="4">&nbsp;</th>
	    	<th width="4%" style="text-align:right">C.C.</th>
	    	<th width="3%" style="text-align:right">C.O.</th>
	    	<th width="5%" style="text-align:right">C.A.</th>
	    	<th width="5%" style="text-align:right">C.B.</th>
	    	<th width="4%" style="text-align:right">T.C.</th>
	    	<th width="5%" class="right">M.C.</th>
	    	<th width="5%" class="right">M.O.</th>
			<th width="5%" class="right">M.A.</th>
			<th width="5%" class="right">M.B.</th>
			<th width="5%" class="right">T.M.</th>
			<th width="5%" class="right">&nbsp;</th>
			<th width="5%" class="right">$Altas</th>
			<th width="5%" class="right">$Bajas</th>
			<th width="5%" class="right">$NetoAlta</th>
			<th width="5%" class="right">$NetoBaja</th>
			<th width="5%" class="right">&nbsp;</th>
	  	</tr>
	  	<tr>
	    	<th height="22" colspan="4">Totales . . .</th>
	    	<th width="4%" style="text-align:right"><%=contCalculo%></th>
	    	<th width="3%" style="text-align:right"><%=contOrigen%></th>
	    	<th width="5%" style="text-align:right"><%=contAltas%></th>
	    	<th width="5%" style="text-align:right"><%=contBajas%></th>
	    	<th width="4%" style="text-align:right"><%=contTotal%></th>
	    	<th width="5%" class="right"><%=totMatC%></th>
	    	<th width="5%" class="right"><%=totMatO%></th>
			<th width="5%" class="right"><%=totMatA%></th>
			<th width="5%" class="right"><%=totMatB%></th>
			<th width="5%" class="right"><%=totMat%></th>
			<th width="5%" class="right">&nbsp;</th>
			<th width="5%" class="right"><%=getFormato.format(totAltas)%></th>
			<th width="5%" class="right"><%=getFormato.format(totBajas)%></th>
			<th width="5%" class="right"><%=getFormato.format(totRealAlta)%></th>			
			<th width="5%" class="right"><%=getFormato.format(totRealBaja)%></th>
			<th width="5%" class="right">&nbsp;</th>
	  	</tr>
	</table>
	</div>
</html>