<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="TramiteU" scope="page" class="aca.leg.LegTramiteUtil"/>
<jsp:useBean id="RequisitoU" scope="page" class="aca.leg.LegRequisitosUtil"/>
<jsp:useBean id="TramiteR" scope="page" class="aca.leg.LegTramreq"/>
<jsp:useBean id="TramiteRU" scope="page" class="aca.leg.LegTramreqUtil"/>
<jsp:useBean id="Tramite" scope="page" class="aca.leg.LegTramite"/>

<script type="text/javascript">
	function recarga(){
		document.frmTramite.submit();
	}
</script>

<%
	String tramiteId		= request.getParameter("IdTramite");
	ArrayList lisTramites 	= TramiteU.getListAll(conEnoc, "ORDER BY TRAMITE_NOMBRE");
	int numAccion 			= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));

	
	switch (numAccion){
	
		case 1: {
			TramiteR.setTramiteId(tramiteId);
			TramiteR.setRequisitoId(request.getParameter("RequisitoId"));
			if ( TramiteRU.existeReg(conEnoc, tramiteId, request.getParameter("RequisitoId"))== false ){
				if (TramiteRU.insertReg(conEnoc,TramiteR)){					
				}					
			}
			break;
		}	
		
		case 2:{
			TramiteR.setTramiteId(tramiteId);
			TramiteR.setRequisitoId(request.getParameter("RequisitoId"));
			if ( TramiteRU.existeReg(conEnoc, tramiteId, request.getParameter("RequisitoId"))== true ){
				if ( TramiteRU.deleteReg(conEnoc, tramiteId, request.getParameter("RequisitoId"))){					
				}				
			}
			break;
		}	
	}
%>




<form name="frmTramite" method="post" action="tramitereq">
<table align="CENTER" width="75%" >
  <tr> 
    <td class="titulo">Elige el Trámite</td>
  </tr>
  <tr> 
    <td align="center"> 
	  <select name="IdTramite" onchange="javascript:recarga()">
      <option value=""></option>
<%	
		for(int i=0;i< lisTramites.size(); i++){
			aca.leg.LegTramite tram = (aca.leg.LegTramite) lisTramites.get(i);
%>
			<option value="<%=tram.getTramiteId() %>" <%if(tram.getTramiteId().equals(tramiteId)) out.print("selected");%>><%=tram.getTramiteNombre()%></option>
<%		}
%>  
        </select>
    </td>
  </tr>
</table>
<%
	//Condicion para q aparezcan los requisitos (2 PARTE)
		if(tramiteId!= null){		
		ArrayList lisRequisitos =  RequisitoU.getListReqFaltan(conEnoc, tramiteId, "ORDER BY REQUISITO_ID");
%>		
		
	<table style="width:100%"  align="center"> 
	<tr>
		<td valign="top">
			<table style="width:100%"  align="center">
		  		<tr> 
		    		<td class="titulo2">Listado de Requisitos</td>
		  		</tr>
		  	</table>
			<table style="width:80%"  align="center" dwcopytype="CopyTableRow" height="23">
		  		<tr> 
		    		<th width="20%" height="18"><spring:message code="aca.Operacion"/></th>
		    		<th width="8%" height="18"><spring:message code="aca.Numero"/></th>
		   			<th width="80%" height="18"><spring:message code='aca.Descripcion'/></th>
		 		</tr>
			</table>
			<table style="width:80%" heigth="50%"  align="center" height="25">
<% 	// ArrayList que acomoda los datos
		for (int i=0; i< lisRequisitos.size(); i++){
			aca.leg.LegRequisitos req = (aca.leg.LegRequisitos) lisRequisitos.get(i);
			
			
%>  
		  		<tr> 
		    		<td width="18%" align="center"><a href="tramitereq?Accion=1&IdTramite=<%= tramiteId %>&RequisitoId=<%= req.getRequisitoId() %>">Agregar</a></td>
		    		<td width="18%" align="center"><font color="#000000" size="2"><%=req.getRequisitoId()  %></font></td>
		    		<td width="75%" align="left"><font color="#000000" size="2"><%=req.getRequisitoNombre() %></font></td>
		  		</tr>
  	<%
       }
	%>
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="end"><spring:message code="aca.FinDelListado"/></td>
				</tr>
			</table>	
		</td>
		<td valign="top">

			<table style="width:100%"  align="center">
		  		<tr> 
		    		<td class="titulo2">Requisitos para el Trámite</td>
		  		</tr>
			</table>
<%            
		ArrayList lisReqxTramite =  RequisitoU.getListReqTramite(conEnoc, tramiteId, "ORDER BY REQUISITO_ID");	

%>			
			<table style="width:80%"  align="center" dwcopytype="CopyTableRow" height="25">
		  		<tr> 
		    		<th width="20%" height="18"><spring:message code="aca.Operacion"/></th>
		    		<th width="8%" height="18"><spring:message code="aca.Numero"/></th>
		   			<th width="80%" height="18"><spring:message code='aca.Descripcion'/></th>
		 		</tr>
			</table>
			<table style="width:80%" heigth="100%"  align="center" height="25">
<% 	// ArrayList que acomoda los datos
		
		for (int i=0; i< lisReqxTramite.size(); i++){
			aca.leg.LegRequisitos req = (aca.leg.LegRequisitos) lisReqxTramite.get(i);
			
%>  
		  		<tr> 
		    		<td width="18%" align="center"><a href="tramitereq?Accion=2&IdTramite=<%= tramiteId %>&RequisitoId=<%=req.getRequisitoId()%>"><spring:message code='aca.Borrar'/></a></td>
		    		<td width="18%" align="center"><font color="#000000" size="2"><%= req.getRequisitoId() %></font></td>
		    		<td width="75%" align="left"><font color="#000000" size="2"><%= req.getRequisitoNombre() %></font></td>
		    	</tr>
	<%
       }
	%>	
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3" class="end"><spring:message code="aca.FinDelListado"/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<% }%>
</form>
<%@ include file= "../../cierra_enoc.jsp" %>