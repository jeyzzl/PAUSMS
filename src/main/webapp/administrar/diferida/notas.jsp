<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Kardex" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="KardexU" scope="page" class="aca.kardex.ActualUtil"/>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="Materia" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MateriaU" scope="page" class="aca.plan.CursoUtil"/>

<%
	String codigoPersonal 		= (String) session.getAttribute("");
	String cargaId 				= "X"; 
	String maestro 				= "";
	String tipoCurso			= "";
	String carrera				= "x";	
	int row = 0;
%>
<head>
<script type="text/javascript">
	function ocultaCarga(cargaId){
		$("carga-"+cargaId).remove();
		$("enter-"+cargaId).remove();
	}
	
	function cancelaDiferidos(cargaId){
		if(confirm("¿Desea cancelar las calificaciones diferidas?")){
			var url = "notasAccion?Accion=5&cargaId="+cargaId;
			
			new Ajax.Request(url, {
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("Could not connect to the page\n");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}
	}
	
	var numCargando = 0;
	function muestraCargando(){
		numCargando++;
		if(!$("cargando")){
			var theWidth, theHeight;
			if (window.innerWidth){
			  theWidth = window.innerWidth 
			  theHeight = window.innerHeight 
			} 
			else if (document.documentElement && document.documentElement.clientWidth){
			  theWidth = document.documentElement.clientWidth 
			  theHeight = document.documentElement.clientHeight 
			} 
			else if (document.body){
			  theWidth = document.body.clientWidth 
			  theHeight = document.body.clientHeight 
			}
			$(document.body).insert({
				bottom: '<img id="cargando" src="../../imagenes/cargando2.gif" width="80px" />'
			});
			var cargando = $("cargando");
			cargando.style.position = "absolute";
			cargando.style.zIndex = "1000";
			cargando.style.top = "0px";
			cargando.style.left = (theWidth-cargando.offsetWidth - 100)+"px";
		}
	}
	
	function ocultaCargando(){
		numCargando--;
		if(numCargando == 0){
			$("cargando").remove();
		}
	}
</script>
</head>
<body>
<div class="container-fluid">
	<h2>Students with Deffered Subjects</h2>
	<hr>
<%
	ArrayList<aca.kardex.KrdxCursoAct> lisNotas = KardexU.getListDiferidas(conEnoc,"ORDER BY CURSO_CARGA_ID, ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
	for (int i=0; i<lisNotas.size(); i++ ){ 
		row++;
		Kardex = (aca.kardex.KrdxCursoAct) lisNotas.get(i);
		
		// Bean de Materia 
		Materia = MateriaU.mapeaRegId(conEnoc,Kardex.getCursoId());
		
		if ( !Kardex.getCursoCargaId().substring(0,6).equals(cargaId) ){
			cargaId = Kardex.getCursoCargaId().substring(0,6);
			Carga = CargaU.mapeaRegId(conEnoc,cargaId);
			row=1;
%>  	
	<p id="enter-<%=cargaId %>">&nbsp;</p>
 	<table style="width:97%" class="table table-sm table-bordered" id="carga-<%=cargaId%>" >
	<tr>
		<td align="left" colspan = "10"> 
			<em><spring:message code="aca.PeriodoAcademico"/>:</em>&nbsp;
			<b><%= Carga.getNombreCarga() %></b>&nbsp;&nbsp;&nbsp;
			<em><spring:message code="aca.FechaInicio"/>:</em> 
			<b><%= Carga.getFInicio() %></b> &nbsp;&nbsp;
			<em><spring:message code="aca.FechaFinal"/>:</em> 
			<b><%= Carga.getFFinal() %></b>	  
			&nbsp;<a class="btn btn-primary" href="javascript:cancelaDiferidos('<%=cargaId %>');">Cancelar calificaciones diferidas</a>
		</td>	
  	</tr>
  	<tr class="table-info">
    	<th><spring:message code="aca.Numero"/></th>
    	<th><spring:message code="aca.Matricula"/></th>
	    <th><spring:message code="aca.NombreDelAlumno"/></th>
	    <th><spring:message code="aca.Plan"/></th>
	    <th><spring:message code="aca.Materia"/></th>
	    <th><spring:message code="aca.Maestro"/></th>
		<th class="text-center"><spring:message code="aca.Tipo"/></th>
		<th class="text-center"><spring:message code="aca.Nota"/></th>
		<th class="text-center"><spring:message code="aca.Extra"/></th>
		<th class="text-center"><spring:message code="aca.Estado"/></th>
  	</tr>
<%	}
		 maestro 		= aca.carga.CargaGrupoUtil.getCodigoPersonal(conEnoc,Kardex.getCursoCargaId());
		 tipoCurso		= aca.catalogo.CatTipoCurso.getNombreTipo(conEnoc, Materia.getTipoCursoId());
		 carrera 		= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, aca.plan.PlanUtil.getCarreraId(conEnoc, Materia.getPlanId()));
%>
	<tr class="tr2">
    	<td align="center"><%=row %></td>
    	<td align="left"><%= Kardex.getCodigoPersonal() %></td>
    	<td align="left"><%= aca.alumno.AlumUtil.getNombreAlumno(conEnoc,Kardex.getCodigoPersonal(),"APELLIDO") %></td>
    	<td align="left" class="ayuda mensaje <%=carrera%>"><%= Materia.getPlanId() %></td>
    	<td align="left" class= "ayuda mensaje <%=tipoCurso%>"><%= Materia.getNombreCurso() %></td>
    	<td align="left" class="ayuda alumno <%= maestro %>"><%= maestro %></td>
    	<td align="center"><%= aca.catalogo.TipoCalUtil.getNombreTipoCal(conEnoc,Kardex.getTipoCalId()) %></td>
    	<td align="center"><%= Kardex.getNota() %></td>
    	<td align="center"><%= Kardex.getNotaExtra() %></td>
    	<td align="center"><%= Kardex.getNotaExtra() %></td>
  	</tr>
<%	}	%>
		<tr><td colspan="9" class="end">End of List..!!</td></tr>
	</table>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>