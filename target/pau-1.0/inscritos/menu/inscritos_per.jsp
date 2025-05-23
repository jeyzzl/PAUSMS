<%@page import="aca.catalogo.CatCarreraUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.EstadisticaUtil"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="kardexU" scope="page" class="aca.kardex.ActualUtil"/>
<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="Estadistica" scope="page" class="aca.vista.Estadistica"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="FacultadU" scope="page" class="aca.catalogo.FacultadUtil"/>
<jsp:useBean id="CatPeriodoUtil" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>

<script type="text/javascript">

	function cambiaPeriodo(periodoId){
		document.location.href="inscritos_per?PeriodoId="+periodoId+"&cambioPeriodo=1";
	}
	
	function cambiaCarga(){		
		document.location.href="inscritos_per?CargaId="+document.forma.CargaId.value+"&cambioCarga=S";
	}
	
	function Show( ){			
		document.location.href="inscritos_per?Accion=1";
	}
	
</script>

<!-- inicio de estructura -->
<%	
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigo			= (String) session.getAttribute("codigoPersonal");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();	
	String lisModo[] 		= modalidades.replace("'", "").split(",");	
	
	// Lista de periodos
	ArrayList<aca.catalogo.CatPeriodo> listaPeriodos = CatPeriodoUtil.getListAll(conEnoc, "ORDER BY PERIODO_ID");

	if(request.getParameter("cambioPeriodo")!=null && !request.getParameter("cambioPeriodo").equals("")){
		session.setAttribute("periodo", request.getParameter("PeriodoId"));
	}
	String periodoId 		= (String)session.getAttribute("periodo");
	
	// Lista de cargas
	ArrayList<aca.carga.Carga> lisCarga = new aca.carga.CargaUtil().getListAll(conEnoc,"WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY CARGA_ID");
	
	if(request.getParameter("cambioPeriodo")!=null&&!request.getParameter("cambioPeriodo").equals("")&&!lisCarga.isEmpty()){
		session.setAttribute("cargaId", lisCarga.get(0).getCargaId());		
	}else if(request.getParameter("cambioCarga")!=null&&!request.getParameter("cambioCarga").equals("")){
		session.setAttribute("cargaId", request.getParameter("CargaId"));		
	}	
	String cargaId 			= (String)session.getAttribute("cargaId");
	if(lisCarga.isEmpty()){
		session.setAttribute("cargaId", "");
	}
	
	String sMatricula 		= "X";
	String sNombreAlumno 	= "X";
	String sFacultad 		= "X";
	String sCarrera 		= "X";
	String sCarreraTemp		= "X";
	String sNombreFacultad 	= "X";
	String sNombreCarrera	= "X";
	String sSexo 			= "X";
	String sResidencia 		= "X";
	String sModalidad		= "0";	
	String sFechaNac 		= "X";
	
	acceso = AccesoU.mapeaRegId(conEnoc, codigo);	
	String sBgcolor			= "";
	
	int cont 				= 1;
	int nInscritos 			= 0, nCalculos 		= 0;
	int nHombres			= 0, nMujeres		= 0;
	int nInternos			= 0, nExternos 		= 0;
	int nNacional			= 0, nExtranjero	= 0;
	int i 					= 0;
	
	if (cargaId==null){ cargaId	= (String)session.getAttribute("cargaId");  }
%>
<div class="container-fluid">
<h1>Inscritos por Cargas</h1>
<form name="forma">
<div class="alert alert-info">
	<a href="menu" class="btn btn-primary">Menú</a>&nbsp;&nbsp;
   	<b>Período:</b>
        <select onchange='javascript:cambiaPeriodo(this.value);' name="PeriodoId" class="input input-medium">
<%	for(int j=0; j<listaPeriodos.size(); j++){
		aca.catalogo.CatPeriodo periodo = listaPeriodos.get(j);%>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print("Selected");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%	}%>
        </select>&nbsp;&nbsp;
	<b>Carga:</b>
	    <select onchange='javascript:cambiaCarga();' name="CargaId" style="width:350px;" class="input input-xlarge">
<%	for(i=0; i<lisCarga.size(); i++){
		Carga = lisCarga.get(i);%>
			<option <%if(cargaId.equals(Carga.getCargaId()))out.print("Selected");%> value="<%=Carga.getCargaId()%>">[<%=Carga.getCargaId() %>] <%=Carga.getNombreCarga()%></option>
<%	}%>	
        </select>&nbsp;
        <%
        for(String mod:lisModo){
			String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
			out.print("["+nombreModalidad+"] ");	
		}        
        %> <a href="modalidades" class="btn btn-primary"><spring:message code="aca.Elegir"/></a>&nbsp;
       <input class="btn btn-info" type="button" value="Mostrar" onclick="javascript:Show();"/>
        <p id="txt" style=" letter-spacing:1px;"></p>
</div>
</form>
<%	
	
	// Si la acción es mostrar
	if (accion.equals("1")){
	
		//Map de Carreras
		HashMap <String, aca.catalogo.CatCarrera> mapaCarrera = CarreraU.getMapAll(conEnoc, "ORDER BY NOMBRE_CARRERA");
				
		// Map de facultades
		HashMap <String, aca.catalogo.CatFacultad> mapaFacultad = FacultadU.getMapAll(conEnoc, "ORDER BY NOMBRE_FACULTAD");
		
		// Map de modalidades
		HashMap <String, aca.catalogo.CatModalidad> mapaModalidad = aca.catalogo.ModalidadUtil.getMapAll(conEnoc, " ");
				
		// Map de países
		HashMap <String, aca.catalogo.CatPais> mapaPais = aca.catalogo.PaisUtil.getMapAll(conEnoc, "");
				
		// Map para contar numero total de materias
		java.util.HashMap<String, String> mapMaterias 		= kardexU.getNumAlumMatTipoModalidad(conEnoc, cargaId, modalidades, "'I','1','2','3','4','5','6'");		
		
		// Map de semestres
		HashMap <String, aca.financiero.FesCcobro> mapaCobro = aca.financiero.FesCcobroUtil.getMapInscritos(conEnoc, cargaId);
		
		// Map de costos de credito
		HashMap<String, String> mapCostoCredito 			= aca.financiero.FesCcMateriaUtil.mapCostoCredito(conEnoc, "'"+cargaId+"'", "'I'");
		
		// Map de creditos vendidos
		HashMap<String, String> mapCreditos 				= aca.financiero.FesCcMateriaUtil.mapCreditosCarga(conEnoc, "'"+cargaId+"'", "'I'");
		
		// Map de creditos vendidos
		HashMap<String, String> mapMovimiento 				= aca.financiero.FesCCMovimientoUtil.mapMovimientoCarga(conEnoc, "'"+cargaId+"'", "'01','02','03','04','06','07','08','21','24','27','28','29','30','31','51'", "'I'");
		
		
		// Lista de alumnos inscritos en una carga
		ArrayList<aca.vista.Estadistica> lista = EstadisticaU.getListOrdenModalidad(conEnoc, cargaId, modalidades, "ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
	
		String idFacultad			= "X";
		String codigoNuevo			= "";
		String codigoViejo			= "X";
	    for(aca.vista.Estadistica obj : lista){		
			sMatricula 			= obj.getCodigoPersonal();		
			sSexo 				= obj.getSexo();
			sResidencia 		= obj.getResidenciaId();
			//sModalidad			= rset.getString("MODALIDAD");
			sCarreraTemp 		= obj.getCarreraId();
			//semestre 			= rset.getString("SEMESTRE");
			sFechaNac 			= obj.getFNacimiento();
			//nacionalidad		= rset.getString("NACION");
			idFacultad 			= obj.getFacultadId();
			
			codigoNuevo = sMatricula;
			if(!codigoNuevo.equals(codigoViejo)){
				if( acceso.getAccesos().indexOf(sCarreraTemp)!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){		
					nInscritos++;
					if (sSexo.equals("M")){ nHombres++; }else{ nMujeres++; }	 			
					if (sResidencia.equals("Int.")){ nInternos++; }else{ nExternos++; }
					
					
					
					if(!sFacultad.equals(idFacultad)){
						if(mapaFacultad.containsKey(idFacultad)){
							sNombreFacultad = mapaFacultad.get(idFacultad).getTitulo()+": "+mapaFacultad.get(idFacultad).getNombreFacultad();
						}else{
							sNombreFacultad = "";
						}	
						sFacultad 		= idFacultad;
					
					//sFacultad = rset.getString("facultad");
				 	//sNombreFacultad = aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,sFacultad);
	%>	
	<table style="width:99%"  height="41" class="table table-condensed">
	  <tr>
	    <td align="center" colspan="25">&nbsp;</td>
	  </tr>
	  <tr>
	    <td class="titulo" height="20" align="center" colspan="25"><b><font  size="3"><%=sNombreFacultad%></font></b></td>
	  	 	 </tr>
	<%				
		       		}//fin del if de facultades diferentes
		       		if(!sCarrera.equals(sCarreraTemp)){
		       			if(mapaCarrera.containsKey(sCarreraTemp)){
		       				sNombreCarrera 	= mapaCarrera.get(sCarreraTemp).getNombreCarrera();
		       			}else{
		       				sNombreCarrera = "";
		       			}	
		       			sCarrera		= sCarreraTemp;
		       			cont = 1;
	       			
				
	%>
	  <tr><td colspan="25">&nbsp;</td></tr>
	  <tr> 
	    <td colspan="25" class="titulo3"><b><font  size="2" face="Arial Narrow, Arial">Programa:</font><font color="#000099" size="2"> 
	      <%=sNombreCarrera%></font></b></td>
	  </tr>
	  <tr> 
	    <th width="2%" height="22" align="center"><spring:message code="aca.Numero"/></th>
		<th width="4%" height="22" align="center"><spring:message code="aca.Plan"/></th>
	    <th width="4%" height="22" align="center"><b><spring:message code="aca.Matricula"/></b></th>
	    <th width="17%" height="22" align="center"><b><spring:message code="aca.Nombre"/></b></th>
	    <th width="5%" height="22" align="center"><b><spring:message code="aca.Modalidad"/></b></th>
	    <th width="2%" height="22" align="center"><b><spring:message code="aca.Res"/></b></th>
	    <th width="2%" height="22" align="center"><b><spring:message code="aca.Gen"/></b></th>
	    <th width="2%" height="22" align="center"><b>Sem.</b></th>
		<th width="4%" height="22" align="center"><b>F.Nac.</b></th>
		<th width="3%" height="22" align="center"><b>Nacionalidad</b></th>
		<th width="3%" height="22" align="center"><b># Mat.</b></th>
		<th width="2%" height="22" align="center"><b>Cr.</b></th>
		<th width="2%" height="22" align="center"><b>$ Cr.</b></th>
		<th width="4%" height="22" align="center"><b>$ Mat.</b></th>
		<th width="4%" height="22" align="center"><b>$ Enza.</b></th>
		<th width="4%" height="22" align="center"><b>$ Inter.</b></th>
		<th width="3%" height="22" align="center"><b>$ Pag.</b></th>
		<th width="4%" height="22" align="center"><b>$ Extemp.</b></th>
		<th width="4%" height="22" align="center"><b>$ Costo.</b></th>
		
		
	  </tr>
	  <%
	       			
					}//fin del if de carreras diferentes
				
					// Busca los valores en los maps
					String totMaterias = "0";
					if (mapMaterias.containsKey(sMatricula)) totMaterias = mapMaterias.get(sMatricula);					
					
					String modalidad = "";
					if (mapaModalidad.containsKey(obj.getModalidadId()) ){
						modalidad = mapaModalidad.get(obj.getModalidadId()).getNombreModalidad();				
					}
					
					String nacionalidad="";
					if(mapaPais.containsKey(obj.getPaisId())){
						nacionalidad = mapaPais.get(obj.getPaisId()).getNombrePais();
					}
					
					String semestre = "";
					if(mapaCobro.containsKey( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId() )){
						semestre = mapaCobro.get(obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()).getSemestre();
					}
					
					String creditos = "0";
					if (mapCreditos.containsKey( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId())){
						creditos = mapCreditos.get( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId());
					}
					
					String costoCredito = "0";
					if (mapCostoCredito.containsKey( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId())){
						costoCredito = mapCostoCredito.get( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId());
					}
					
					
					String costoMatricula = "0";
					if (mapMovimiento.containsKey( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()+"01")){
						costoMatricula = mapMovimiento.get( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()+"01");
					}
					
					String costoEnsenanza = "0";
					if (mapMovimiento.containsKey( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()+"02")){
						costoEnsenanza = mapMovimiento.get( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()+"02");
					}
					
					String costoInternado = "0";
					if (mapMovimiento.containsKey( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()+"03")){
						costoInternado = mapMovimiento.get( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()+"03");
					}
					
					String costoPagare = "0";
					if (mapMovimiento.containsKey( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()+"08")){
						costoPagare = mapMovimiento.get( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()+"08");			
					}	
					
					String extemporanea		= "0";
					if (mapMovimiento.containsKey( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()+"24")){
						extemporanea = mapMovimiento.get( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()+"24");
					}
					
					double costoTotal = Double.parseDouble(costoMatricula)+Double.parseDouble(costoEnsenanza)+Double.parseDouble(costoInternado)+Double.parseDouble(costoPagare);		
					
					if ((cont % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
	%>
	  <tr class="tr2" <%=sBgcolor%>> 
	    <td><font size="1">&nbsp;<%=cont%></font></td>
		<td><font size="1"><%=obj.getPlanId()%></font></td>
	    <td align="center"><font size="1"><%=sMatricula%></font></td>    
	    <td><font size="1"><%=obj.getApellidoPaterno()+" "+obj.getApellidoMaterno()+" "+obj.getNombre()%></font></td>
	    <td align="center"><font size="1"><%=modalidad%></font></td>    
	    <td align="center"><font size="1"><%=sResidencia%></font></td>  
	    <td align="center"><font size="1"><%=sSexo%></font></td>    
	    <td align="center"><font size="1"><%=semestre%></font></td>
		<td align="center"><font size="1"><%=sFechaNac%></font></td>
		<td align="center"><font size="1"><%=nacionalidad%></font></td>		
		<td align="center"><font size="1"><%=totMaterias%></font></td>
		<td align="center"><font size="1"><%=creditos%></font></td>	
		<td align="center"><font size="1"><%=costoCredito%></font></td>
		<td style="text-align:right"><%= getFormato.format(Double.parseDouble(costoMatricula))%></td>
		<td style="text-align:right"><%= getFormato.format(Double.parseDouble(costoEnsenanza))%></td>			
		<td style="text-align:right"><%= getFormato.format(Double.parseDouble(costoInternado))%></td>
		<td style="text-align:right"><%= getFormato.format(Double.parseDouble(costoPagare))%></td>
		<td style="text-align:right"><%= getFormato.format(Double.parseDouble(extemporanea))%></td>
		<td style="text-align:right"><%= getFormato.format(costoTotal)%></td>	
						
	  </tr>
	  <%
	     			cont++;
				}
	    	}
			codigoViejo = codigoNuevo;
 		} // fin del while	 
	 
%>
	</table> 
	<table style="width:99%" >
  	<tr> 
    	<th width="12%"><font size="1">Inscritos: <%=nInscritos%></font></th>
    	<th width="16%"><font size="1">Internos: <%=nInternos%></font></th>
    	<th width="14%" align="center"><font size="1">Externos: <%=nExternos%></font></th>
  	</tr>
	</table>
	<div align="center">
		<font size="3" face="Times New Roman, Times, serif"><br>
		<b><font color="#000099">Fin del Listado...</font> </b> </font>
	</div>

<%	} %>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>