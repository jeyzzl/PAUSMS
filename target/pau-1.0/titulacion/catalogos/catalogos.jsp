<%@ page import= "aca.tit.RequisitoVO"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="bRequisitos"  class="aca.tit.Requisitos" scope="page"/>
<jsp:useBean id="bDocumentos"  class="aca.tit.Documentos" scope="page"/>
<jsp:useBean id="bPagos"  class="aca.tit.Pagos" scope="page"/>

<%
	int accion=0;
	int orden=0;
	if(request.getParameter("accion")!=null)
		accion=Integer.parseInt(request.getParameter("accion"));
	String id=request.getParameter("id");
	if (id==null) id="";
	String nombre=request.getParameter("nombre");
	if (nombre==null) nombre="";
	String sorden=request.getParameter("orden");
	if (sorden==null)sorden="";
	else orden = Integer.parseInt(sorden);
	String cat=request.getParameter("cat");
	if (cat==null)cat="";
	String tipo=request.getParameter("tipo");
	if (tipo==null)tipo="";
	String mat="";
%>

<script>
	function pres(f){
		if(window.event.keyCode==13&&f==1)
			Guarda(f);
		
	}
	function Guarda(f){
			if(f != 2)
				if(document.forma[f].nombre.value==""||document.forma[f].orden.value=="")
					alert("No puede dejar campos vacios");
				else
					document.forma[f].submit();
			else
				if(document.forma[f].nombre.value=="")
					alert("No puede dejar campos vacios");
				else
					document.forma[f].submit();
	}
	function cambia(f){
		if(document.forma[f].obligatorio.checked==true)
			document.forma[f].obligatorio.value="S"
		else
			document.forma[f].obligatorio.value="N"
	}
	
	function borrar(f,id){
		if (confirm("¿Esta seguro que desea borrar el registro?")){
			document.location.href='catalogos?accion=3&id='+id+'&cat='+f
		}
	}
	function modificar(f,id){
		document.location.href='catalogos?accion=4&id='+id+'&cat='+f
	}
</script>
<!----------------------- REQUISITOS ------------------------------ -->
<div class="container-fluid">
	<h1>Catalogos</h1>
	<div class="alert alert-info">
	<a class="btn btn-primary" href="catalogos?accion=1&cat=requisitos"><spring:message code='aca.Nuevo'/></a>
	</div>
	<form name="forma" method='post' action='catalogos?cat=requisitos'>
	<table class="table table-sm table-bordered" style="width:50%">
	<tr>
	    <th colspan="4"><%if((accion==1||accion==4)&&cat.equals("requisitos")){%><a class="btn btn-danger" href="catalogos"> Cancelar</a><%}%></th>
	</tr>
<%
		if(accion==1&&cat.equals("requisitos")){
%>
		<tr><td colspan='4'><table onkeypress='pres(0)'>
			<tr>
				<td>Requisito: </td>
				<td><textarea name="nombre" cols="39" rows="7"/></textarea></td>
			</tr>
			<tr>
				<td>Orden: </td>
				<td><input name="orden" type="text" class="text" size="5"/><a href='javascript:Guarda(0)'><img alt='Guardar'  src='../../imagenes/filesave.png'></img></a>
				<input type='hidden' name='accion' value='2'/>
				</td>
			</tr>
		</table></td></tr>
		<script type='javascript'>document.forma.nombre.focus();</script>
<%
		}
		if(accion==2&&cat.equals("requisitos")){
			RequisitoVO requisito = new RequisitoVO();
			requisito.setNombre(nombre);
			requisito.setOrden(orden);
			bRequisitos.guardaRequisito(conEnoc,requisito);
		}
		if(accion==3&&cat.equals("requisitos")){
			bRequisitos.eliminaRequisito(conEnoc,id);
		}
		if(accion==4&&cat.equals("requisitos")){
		RequisitoVO requisito=bRequisitos.getRequisito(conEnoc,Integer.parseInt(id));
%>
		<tr><td colspan='4'>
			<table onkeypress='pres(0)'>
			<tr>
				<td>Requisito: </td>
				<td><textarea name="nombre" cols="39" rows="7"><%=requisito.getNombre()%></textarea></td>
			</tr>
			<tr>
				<td>Orden: </td>
				<td><input name="orden" type="text" class="text" size="5" value='<%=requisito.getOrden()%>'/><a href='javascript:Guarda(0)'><img alt='Guardar'  src='../../imagenes/filesave.png'></img></a>
					<input type='hidden' name='id' value='<%=requisito.getId()%>'/>
					<input type='hidden' name='accion' value='5'/>
					
				</td>				
			</tr>
			</table>
		</td></tr>
		<script>
				document.forma.nombre.focus();
		</script>
<%		}
		if(accion==5&&cat.equals("requisitos")){
			RequisitoVO requisito = new RequisitoVO();
			requisito.setId(Integer.parseInt(id));
			requisito.setNombre(nombre);
			requisito.setOrden(orden);
			bRequisitos.modificaRequisito(conEnoc,requisito);
		}
%>
	<tr class="th2">
		<td width="12%" align='center'><spring:message code="aca.Operacion"/></td>
		<td width="5%" align='center'><spring:message code="aca.Numero"/></td>
		<td width="77%" align='center'><spring:message code="aca.Nombre"/></td>
		<td width="8%" align='center'>Orden</td>
	</tr>
<%	
	RequisitoVO requisito = null;
	ArrayList<aca.tit.RequisitoVO> requisitos =  bRequisitos.getRequisitos(conEnoc);
	for(int i=0;i<requisitos.size();i++){
	requisito = new RequisitoVO();
	requisito=(RequisitoVO)requisitos.get(i);
%>
	
	<tr class="tr2" valign='top'>
		<td align='center'>
			<a class="fas fa-edit" href="javascript:modificar('requisitos',<%=requisito.getId()%>);"></a>
			<a class="fas fa-trash-alt" href="javascript:borrar('requisitos',<%=requisito.getId()%>);"></a>
		</td>
		<td align='center'><%=requisito.getId()%></td>
		<td><%=requisito.getNombre()%></td>
		<td align='center'><%=requisito.getOrden()%></td>
	</tr>
<%}%>
	</table>
	</form>

<!----------------------- DOCUMENTOS ------------------------------ -->

	<form name="forma" method='post' action='catalogos?cat=documentos'>
	<h3>Catálogo de Documentos</h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="catalogos?accion=1&cat=documentos"><spring:message code='aca.Nuevo'/></a>
	</div>	
	<table class="table table-sm" style="width:50%">
	<tr>
	    <th colspan="5"><%if((accion==1||accion==4)&&cat.equals("documentos")){%><a class="btn btn-danger" href="catalogos"> Cancelar</a><%}%></th>
	</tr>
<%
		if(accion==1&&cat.equals("documentos")){
%>
	<tr><td colspan='5'>
		<table onkeypress='pres(1)'>
		<tr>
			<td><spring:message code="aca.Nombre"/>: </td>
			<td><input type="text" size="40" name="nombre"/></td>
		</tr>
		<tr>
		<tr>
			<td><spring:message code="aca.Tipo"/>: </td>
			<td><select name="tipo">
					<option value="G">General</option>
					<option value="M">Nivel Medio</option>
					<option value="L">Licenciatura</option>
					<option value="P">Posgrado</option>
					<option value="O">Servicio</option>
					<option value="S">Seguimiento</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Orden: </td>
			<td><input name="orden" type="text" class="text" size="5"/><a href='javascript:Guarda(1)'><img alt='Guardar'  src='../../imagenes/filesave.png'></img></a>
			<input type='hidden' name='accion' value='2'/>
			</td>
		</tr>
		</table>
		</td>
	</tr>
		<script type='javascript'>document.forma[1].nombre.focus();</script>
<%
		}
		if(accion==2&&cat.equals("documentos")){
			RequisitoVO documento = new RequisitoVO();
			documento.setNombre(nombre);
			documento.setOrden(orden);
			documento.setTipo(tipo);
			bDocumentos.guardaDocumento(conEnoc,documento);
		}
		if(accion==3&&cat.equals("documentos")){
			mat=bDocumentos.eliminaDocumento(conEnoc,id);
			if(mat.equals("X")){
				out.print("<tr><td colspan='5'><font color=red><b>"+mat+"</b></font></tr></td>");
			}
		}
		if(accion==4&&cat.equals("documentos")){
		RequisitoVO documento=bDocumentos.getDocumento(conEnoc,Integer.parseInt(id));
%>
		<tr><td colspan='5'><table onkeypress='pres(1)'>
			<tr>
				<td><spring:message code="aca.Nombre"/>: </td>
				<td><input name="nombre" size="40" value="<%=documento.getNombre()%>"/></td>
			</tr>
			<tr>
				<td><spring:message code="aca.Tipo"/>: </td>
				<td><select name="tipo">
						<option value="G" <%if(documento.getTipo().equals("G"))out.print("Selected");%>>General</option>
						<option value="M" <%if(documento.getTipo().equals("M"))out.print("Selected");%>>Nivel Medio</option>
						<option value="L" <%if(documento.getTipo().equals("L"))out.print("Selected");%>>Licenciatura</option>
						<option value="P" <%if(documento.getTipo().equals("P"))out.print("Selected");%>>Posgrado</option>
						<option value="O" <%if(documento.getTipo().equals("O"))out.print("Selected");%>>Servicio</option>
						<option value="S" <%if(documento.getTipo().equals("S"))out.print("Selected");%>>Seguimiento</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Orden: </td>
				<td><input name="orden" type="text" class="text" size="5" value='<%=documento.getOrden()%>'/><a href='javascript:Guarda(1)'><img alt='Guardar'  src='../../imagenes/filesave.png'></img></a>
					<input type='hidden' name='id' value='<%=documento.getId()%>'/>
					<input type='hidden' name='accion' value='5'/>
					
				</td>				
			</tr>
		</table></td></tr>
		<script>
				document.forma[1].nombre.focus();
		</script>
<%		}
		if(accion==5&&cat.equals("documentos")){
			RequisitoVO documento = new RequisitoVO();
			documento.setId(Integer.parseInt(id));
			documento.setNombre(nombre);
			documento.setOrden(orden);
			documento.setTipo(tipo);
			bDocumentos.modificaDocumento(conEnoc,documento);
		}
%>
	<tr class="th2">
		<td width="12%" align='center'><spring:message code="aca.Operacion"/></td>
		<td width="5%" align='center'><spring:message code="aca.Numero"/></td>
		<td width="60%" align='center'><spring:message code="aca.Nombre"/></td>
		<td width="17%" align='center'><spring:message code="aca.Tipo"/></td>
		<td width="8%" align='center'>Orden</td>
	</tr>
<%	
	ArrayList<aca.tit.RequisitoVO> documentos = new ArrayList<aca.tit.RequisitoVO>();
	RequisitoVO documento = null;
	String letra = "";
	for(int j=1; j <= 6; j++){
		switch(j){
			case 1:{letra = "G";}break;
			case 2:{letra = "M";}break;
			case 3:{letra = "L";}break;
			case 4:{letra = "P";}break;
			case 5:{letra = "O";}break;
			case 6:{letra = "S";}break;
		}
		documentos = bDocumentos.getDocumentos(conEnoc, "WHERE TIPO = '"+letra+"' ORDER BY ORDEN");
		for(int i=0;i<documentos.size();i++){
		documento = new RequisitoVO();
		documento=(RequisitoVO)documentos.get(i);
%>
		
		<tr class="tr2" valign='top'>
			<td align='center'>
				<a class="fas fa-edit" href="javascript:modificar('documentos',<%=documento.getId()%>);"></a>
				<a class="fas fa-trash-alt" href="javascript:borrar('documentos',<%=documento.getId()%>);"></a>
			</td>
			<td align='center'><%=documento.getId()%></td>
			<td><%=documento.getNombre()%></td>
			<td>
<%				
					if(documento.getTipo().equals("G"))out.print("General");
					if(documento.getTipo().equals("M"))out.print("Nivel Medio");
					if(documento.getTipo().equals("L"))out.print("Licenciatura");
					if(documento.getTipo().equals("P"))out.print("Posgrado");
					if(documento.getTipo().equals("O"))out.print("Servicio");
					if(documento.getTipo().equals("S"))out.print("Seguimiento");
%>
	</td>
			<td><%=documento.getOrden()%></td>
		</tr>
<%		}
	}%>
	</table>
	</form>
<!----------------------- PAGOS ------------------------------ -->

	<h3>Catálogo de Formas de Pago</h3>
	<div class="alert alert-info">
	<a class="btn btn-primary" href="catalogos?accion=1&cat=pagos"><spring:message code='aca.Nuevo'/></a>
	</div>
	<form name="forma" method='post' action='catalogos?cat=pagos'>
	<table class="table table-sm" style="width:50%"> 
	<tr>
	    <th colspan="5"><%if((accion==1||accion==4)&&cat.equals("pagos")){%><a class="btn btn-danger" href="catalogos">Cancelar</a><%}%></th>
	</tr>
<%
		if(accion==1&&cat.equals("pagos")){
%>
		<tr><td colspan='5'><table onkeypress='pres(2)'>
			<tr>
				<td><spring:message code="aca.Nombre"/>: </td>
				<td><input type="text" size="40" name="nombre"/>
				<a href='javascript:Guarda(2)'><img alt='Guardar'  src='../../imagenes/filesave.png'></img></a>
				<input type='hidden' name='accion' value='2'/>
				</td>
			</tr>
		</table></td></tr>
		<script type='javascript'>document.forma[2].nombre.focus();</script>
<%
		}
		if(accion==2&&cat.equals("pagos")){
			RequisitoVO pago = new RequisitoVO();
			pago.setNombre(nombre);
			pago.setOrden(orden);
			pago.setTipo(tipo);
			bPagos.guardaPago(conEnoc,pago);
		}
		if(accion==3&&cat.equals("pagos")){
			mat=bPagos.eliminaPago(conEnoc,id);
			if(!mat.equals("")){
				out.print("<tr><td colspan='5'><font color=red><b>"+mat+"</b></font></tr></td>");
			}
		}
		if(accion==4&&cat.equals("pagos")){
		RequisitoVO pago=bPagos.getPago(conEnoc,Integer.parseInt(id));
%>
		<tr><td colspan='5'><table onkeypress='pres(2)'>
			<tr>
				<td><spring:message code="aca.Nombre"/>: </td>
				<td><input name="nombre" size="40" value="<%=pago.getNombre()%>"/>
				<img onclick="javascript:Guarda(2);" class="button" alt='Guardar'  src='../../imagenes/filesave.png'></img>
					<input type='hidden' name='id' value='<%=pago.getId()%>'/>
					<input type='hidden' name='accion' value='5'/>
					
				</td>				
			</tr>
		</table></td></tr>
		<script>
				document.forma[2].nombre.focus();
		</script>
<%		}
		if(accion==5&&cat.equals("pagos")){
			RequisitoVO pago = new RequisitoVO();
			pago.setId(Integer.parseInt(id));
			pago.setNombre(nombre);
			pago.setOrden(orden);
			pago.setTipo(tipo);
			bPagos.modificaPago(conEnoc,pago);
		}
%>
	<tr class="th2">
		<td width="12%" align='center'><spring:message code="aca.Operacion"/></td>
		<td width="5%" align='center'><spring:message code="aca.Numero"/></td>
		<td width="70%" align='center'><spring:message code="aca.Nombre"/></td>
	</tr>
<%	
	RequisitoVO pago = null;
	ArrayList<aca.tit.RequisitoVO> pagos = bPagos.getPagos(conEnoc);
	for(int i=0;i<pagos.size();i++){
	pago = new RequisitoVO();
	pago=(RequisitoVO)pagos.get(i);
%>
	
	<tr class="tr2" valign='top'>
		<td align='center'>
				<a class="fas fa-edit" href="javascript:modificar('pagos',<%=pago.getId()%>);"></a>
				<a class="fas fa-trash-alt" href="javascript:borrar('pagos',<%=pago.getId()%>);"></a>
		</td>
		<td align='center'><%=pago.getId()%></td>
		<td><%=pago.getNombre()%></td>
	</tr>
<%}%>
	</table>
	</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>