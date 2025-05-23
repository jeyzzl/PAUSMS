<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PeriodoU" scope="page" class="aca.musica.MusiPeriodoUtil"/>
<jsp:useBean id="Pagare" scope="page" class="aca.musica.MusiPagare"/>

<head>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	function recarga(){
		document.frmPagare.Accion.value = "0";
		document.frmPagare.submit();
	}
	
	
	function Grabar(){
		if(document.frmPagare.Pagare1.value!="" && document.frmPagare.Pagare2.value!="" && document.frmPagare.Pagare3.value!="" &&  document.frmPagare.Comentario.value!="" ){
			document.frmPagare.Accion.value="2";
			document.frmPagare.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
</script>
</head>

<% 

	String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int numAccion			= Integer.parseInt(accion);
	String resultado 		= "";	
	String year				= request.getParameter("Year")==null?aca.util.Fecha.getHoy().substring(6,10):request.getParameter("Year");
	
	if (periodoId.equals("0")){
		periodoId 			= aca.musica.MusiPeriodo.getPeriodoActual(conEnoc);
	}

// Operaciones a realizar en la pantalla
	switch (numAccion){
		
		case 2: { // Grabar
			Pagare.setPeriodoId(periodoId);
			Pagare.setPagare1(request.getParameter("Pagare1"));
			Pagare.setPagare2(request.getParameter("Pagare2"));
			Pagare.setPagare3(request.getParameter("Pagare3"));
			Pagare.setComentario(request.getParameter("Comentario"));
			if (Pagare.existeReg(conEnoc) == false){
				if (Pagare.insertReg(conEnoc)){
					resultado = "Grabado: "+Pagare.getPeriodoId();					
				}else{
					resultado = "Error al grabar..! : "+Pagare.getPeriodoId();
				}
			}else{
				if (Pagare.updateReg(conEnoc)){
					resultado = "Modificado: "+Pagare.getPeriodoId();					
				}else{
					resultado = "Error al modificar: "+Pagare.getPeriodoId();
				}				
			}		
			
			break;			
		}

	}

	String bgColor			= "";
	ArrayList<aca.musica.MusiPeriodo> lisPeriodos	= PeriodoU.getListAll(conEnoc," ORDER BY ENOC.MUSI_PERIODO.F_INICIO DESC, PERIODO_ID");
	Pagare.mapeaRegId(conEnoc, periodoId);
%>
<body>
<div class="container-fluid">
	<h1>Vencimiento de Pagarés</h1>
	<form action="pagare" method="post" name="frmPagare" target="_self">
	<input type="hidden" name="Accion">
	<div class="alert alert-info">
		<b>Periodo: </b>
  			<select onchange='javascript:recarga()' name="PeriodoId">
<%
			for (int i=0; i< lisPeriodos.size(); i++){
			aca.musica.MusiPeriodo periodo = (aca.musica.MusiPeriodo) lisPeriodos.get(i);
%>
			<option <%if( periodoId.equals(periodo.getPeriodoId() ))out.print(" Selected ");%> value="<%= periodo.getPeriodoId() %>">[<%= periodo.getPeriodoId() %>] <%= periodo.getPeriodoNombre() %></option>
<%
	}
%>
 			</select> 			
	</div>
	<table style="width:40%" class="table">
	<tr>  
    	<td width="15%" align="center" ><strong>Pagare 1:</strong></td>
       	<td>
			<input name="Pagare1" type="text" class="text" id="Pagare1" data-date-format="dd/mm/yyyy" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%=Pagare.getPagare1()%>">
            (DD/MM/AAAA)
		</td>
	</tr>	
    <tr> 
    	<td width="15%" align="center" ><strong>Pagare 2:</strong></td>
    	<td>
			<input name="Pagare2" type="text" class="text" id="Pagare2" data-date-format="dd/mm/yyyy" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%=Pagare.getPagare2()%>">(DD/MM/AAAA)
		</td>	
	</tr>
	<tr> 
    	<td align = "center" ><strong >Pagare3:</strong></td>
        <td>
			<input name="Pagare3" type="text" class="text" id="Pagare3" data-date-format="dd/mm/yyyy" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%=Pagare.getPagare3()%>">
            (DD/MM/AAAA)
		</td>
    </tr>		  
	<tr> 
    	<td align = "center" ><strong ><spring:message code="aca.Comentario"/>:</strong></td>
        <td>
			<textarea name="Comentario" class="text" id="Comentario" cols="70" rows="2"><%= Pagare.getComentario()%></textarea>
		</td>
    </tr>    	         
    </table>
    <div class="alert alert-info">
    	<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
    </div>    
</form>
</div>
</body>
<!-- fin de estructura -->
<script>
	jQuery('#Pagare1').datepicker();
	jQuery('#Pagare2').datepicker();
	jQuery('#Pagare3').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>