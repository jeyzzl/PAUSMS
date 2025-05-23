<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="indicadores" scope="page" class="aca.bsc.Indicadores"/>
<jsp:useBean id="IndicadoresUtil" scope="page" class="aca.bsc.IndicadoresUtil"/>

<%
	String accion = request.getParameter("accion");
	String idItemE = request.getParameter("idItemE");
	String idItem = request.getParameter("idItem");
	String nombre = request.getParameter("nombre");
	String nbInicio = request.getParameter("nbInicio");
	String nbFinal = request.getParameter("nbFinal");
	String nmInicio = request.getParameter("nmInicio");
	String nmFinal = request.getParameter("nmFinal");
	String nsInicio = request.getParameter("nsInicio");
	String nsFinal = request.getParameter("nsFinal");
	
	if (accion == null) accion = "";
	if (idItem == null) idItem = "";
	if (nombre == null) nombre = "";
	if (nbInicio == null) nbInicio = "";
	if (nbFinal == null) nbFinal = "";
	if (nmInicio == null) nmInicio = "";
	if (nmFinal == null) nmFinal = "";
	if (nsInicio == null) nsInicio = "";
	if (nsFinal == null) nsFinal = "";
	if (accion.equals("guardar")){
		indicadores.setIdItem(idItem);
		indicadores.setNombre(nombre);
		indicadores.setNbInicio(nbInicio);
		indicadores.setNbFinal(nbFinal);
		indicadores.setNmInicio(nmInicio);
		indicadores.setNmFinal(nmFinal);
		indicadores.setNsInicio(nsInicio);
		indicadores.setNsFinal(nsFinal);
		IndicadoresUtil.insertReg(conEnoc);
	}else if (accion.equals("eliminar")){
		indicadores = IndicadoresUtil.mapeaRegId(conEnoc,idItemE);
		IndicadoresUtil.deleteReg(conEnoc);
	}else if (accion.equals("update")){
		indicadores.setIdItem(idItem);
		indicadores.setNombre(nombre);
		indicadores.setNbInicio(nbInicio);
		indicadores.setNbFinal(nbFinal);
		indicadores.setNmInicio(nmInicio);
		indicadores.setNmFinal(nmFinal);
		indicadores.setNsInicio(nsInicio);
		indicadores.setNsFinal(nsFinal);
		IndicadoresUtil.updateReg(conEnoc);
	}
	ArrayList<aca.bsc.Indicadores> vIndicadores = IndicadoresUtil.getListAll(conEnoc,"order by nombre");
%>
<script>
	function accion(ac){
		document.forma.accion.value=ac;
		document.forma.submit();
	}
	function eliminar(id){
		if (confirm("¿Estas seguro de eliminar el registro?")){
			document.forma.idItemE.value = id;
			accion('eliminar');
		}		
	}
	function modificar(id){
			document.forma.idItemE.value = id;
			accion('modificar');		
	}
	function checkContenido(){
		if (document.forma.nombre.value == "" ||
			document.forma.nbInicio.value == "" ||
			document.forma.nbFinal.value == "" ||
			document.forma.nmInicio.value == "" ||
			document.forma.nmFinal.value == "" ||
			document.forma.nsInicio.value == "" ||
			document.forma.nsFinal.value == "")
		{
			alert("Llene todos los campos");
			return false;
		}else
			return true;
	}
	function checkContinuidad(){
		if (document.forma.nbFinal.value==document.forma.nmInicio.value &&
			document.forma.nmFinal.value==document.forma.nsInicio.value)
			return true;
		else{
			alert("Los rangos estan mal asignados.");
			return false;
		}
	}
	function guardar(){
		if (checkContenido() && checkContinuidad()) 
			accion("guardar");			
	}
	function update(){
		if (checkContenido() && checkContinuidad()) 
			accion("update");			
	}
</script>
<div class="container-fluid">
	<h1>Indicadores</h1>
	<form action="indicadores" method='post' name='forma'>
		<input type='hidden' name='idItemE'>
		<input type='hidden' name='accion'>	
		<div class="alert alert-info">	
			<a class="btn btn-primary" href="javascript:accion('nuevo')"><spring:message code='aca.Nuevo'/></a>
		</div>
		<table class="table table-bordered">
		<thead class="table-info">
			<tr>
				<th width="10%" align='center'><spring:message code="aca.Operacion"/></th>
				<th width="45%" align='center'><spring:message code="aca.Nombre"/></th>
				<th width="15%" align='center'>Nivel Bajo</th>
				<th width="15%" align='center'>Nivel Medio</th>
				<th width="15%" align='center'>Nivel Superior</th>
			</tr>
		</thead>	
<%	if (accion.equals("nuevo")){%>
			<tr>
				<input type='hidden' name='idItem' value='<%=IndicadoresUtil.nextIdItem(conEnoc)%>'>
				<td align='left'><input type='text' name = "nombre" size='35'></td>
				<td align='center'><input type='text' name="nbInicio" size='2'> - <input type='text' name="nbFinal" size='2'></td>
				<td align='center'><input type='text' name="nmInicio" size='2'> - <input type='text' name="nmFinal" size='2'></td>
				<td align='center'><input type='text' name="nsInicio" size='2'> - <input type='text' name="nsFinal" size='2'></td>
				<td><a class="btn btn-primary" href="javascript:guardar()"><spring:message code="aca.Guardar"/></a></td>
			</tr>
		<script>document.forma.nombre.focus()</script>
<%	}
	for (int i=0;i<vIndicadores.size();i++){
		indicadores = (aca.bsc.Indicadores) vIndicadores.get(i);
		if (accion.equals("modificar")&& idItemE.equals(indicadores.getIdItem())){
%>
			<tr class="tr2">
				<input type='hidden' name='idItem' value='<%=indicadores.getIdItem()%>'>
				<td align='left'><input type='text' name = "nombre" size='35' value='<%=indicadores.getNombre()%>'></td>
				<td align='center'>
					<input type='text' name="nbInicio" size='2' value ='<%=indicadores.getNbInicio()%>'>-
					<input type='text' name="nbFinal" size='2' value='<%=indicadores.getNbFinal()%>'>
				</td>
				<td align='center'>
					<input type='text' name="nmInicio" size='2' value ='<%=indicadores.getNmInicio()%>'>-
					<input type='text' name="nmFinal" size='2' value='<%=indicadores.getNmFinal()%>'>
				</td>
				<td align='center'>
					<input type='text' name="nsInicio" size='2' value ='<%=indicadores.getNsInicio()%>'>-
					<input type='text' name="nsFinal" size='2' value='<%=indicadores.getNsFinal()%>'>
				</td>
				<td><a class="btn btn-primary" href="javascript:update()">Guardar</a></td>
			</tr>
			
<%		}else{
%>				
			<tr class="tr2">
				<td align="center">
					<a href="javascript:modificar('<%=indicadores.getIdItem()%>')"><img  src='../../imagenes/editar.gif'></a>
					<a href="javascript:eliminar('<%=indicadores.getIdItem()%>')"><img  src='../../imagenes/no.png'></a>
				</td>
				<td align='left'><%=indicadores.getNombre()%></td>
				<td align='center'><%=indicadores.getNbInicio()%> - <%=indicadores.getNbFinal()%></td>
				<td align='center'><%=indicadores.getNmInicio()%> - <%=indicadores.getNmFinal()%></td>
				<td align='center'><%=indicadores.getNsInicio()%> - <%=indicadores.getNsFinal()%></td>
			</tr>
<%		}
	}
%>
		</table>
	</form>		
</div>
<%@ include file= "../../cierra_enoc.jsp" %>