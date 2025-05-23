<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.internado.spring.IntDormitorio"%>
<%@ page import= "aca.internado.spring.IntAcceso"%>
<%@ page import= "aca.internado.spring.IntCuarto"%>

<%	
	boolean esAdmin			= (boolean)request.getAttribute("esAdmin");
	boolean esPreceptor		= (boolean)request.getAttribute("esPreceptor");
	String accion			= (String)request.getAttribute("accion");
	String cE				= (String)request.getAttribute("cE");
	int cupoTotal			= (int)request.getAttribute("cupoTotal");
	String nextCuarto		= (String)request.getAttribute("nextCuarto");
	
	IntDormitorio dormi		= (IntDormitorio)request.getAttribute("dormi");
	
	List<IntCuarto> vCuartos	= (List<IntCuarto>)request.getAttribute("vCuartos");
	
	HashMap<String,String> mapaTieneAlumnos 	= (HashMap<String,String>)request.getAttribute("mapaTieneAlumnos");
	HashMap<String,String> mapaAsignados 		= (HashMap<String,String>)request.getAttribute("mapaAsignados");
	HashMap<String,String> mapaInscritos 		= (HashMap<String,String>)request.getAttribute("mapaInscritos");
%>

<%@ include file="portal.jsp" %>

<script>
	function agregar(){
		document.forma.accion.value = "agregar";
		document.forma.submit();
	}
	function eliminar(cuarto){
		if (confirm("�Are you sure you want to delete room "+cuarto+"?")){
			document.forma.cE.value = cuarto;
			document.forma.accion.value = "eliminar";
			document.forma.submit();
		}
	}
	function modificar(cuarto){
		document.forma.cE.value = cuarto;
		document.forma.accion.value = "modificar";
		document.forma.action = "cuartos#GO";
		document.forma.submit();
	}
	function update(cuarto){
		document.forma.cE.value = cuarto;
		document.forma.accion.value = "update";
		document.forma.submit();
	}
	function guardar(){
		if (document.forma.cuartoId.value == "" || document.forma.cupo.value == "")
			alert("Fill out all the fields");
		else{
			document.forma.accion.value="grabar";
			document.forma.submit();
		}
	}
	function noTecla(){
		alert("Enter only the person's ID number. The name will display automatically");
		return false;
	}
	function buscar(){
		abrirVentana("bem",600,500,100,250,"no","yes","yes","no","no","buscar",false);
	}
	function abrirVentana(strName,iW,iH,TOP,LEFT,R,S,SC,T,TB,URL,modal){
		var sF="";
		if (navigator.appName=="Microsoft Internet Explorer" && modal){
			sF+=T?'unadorned:'+T+';':'';
			sF+=TB?'help:'+TB+';':'';
			sF+=S?'status:'+S+';':'';
			sF+=SC?'scroll:'+SC+';':'';
			sF+=R?'resizable:'+R+';':'';
			sF+=iW?'dialogWidth:'+iW+'px;':'';
			sF+=iH?'dialogHeight:'+(parseInt(iH)+(S?42:0))+'px;':'';
			sF+=TOP?'dialogTop:'+TOP+'px;':'';
			sF+=LEFT?'dialogLeft:'+LEFT+'px;':'';	
			return window.showModalDialog(URL,"",sF);
		}else{
			sF+=iW?'width='+iW+',':'';
			sF+=iH?'height='+iH+',':'';
			sF+=R?'resizable='+R+',':'';
			sF+=S?'status='+S+',':'';
			sF+=SC?'scrollbars='+SC+',':'';
			sF+=T?'titlebar='+T+',':'';
			sF+=TB?'toolbar='+TB+',':'';
			sF+=TB?'menubar='+TB+',':'';
			sF+=TOP?'top='+TOP+',':'';
			sF+=LEFT?'left='+LEFT+',':'';
			return window.open(URL,strName?strName:'',sF).focus()
		}
	}			
	
</script>
<body>
<div class="container-fluid">
	<h3>		
		<%=dormi.getNombre()%> Bedroom catalog 
	</h3>
	<div class="alert alert-info">
<% if(esAdmin || esPreceptor){ %><a href='../../internado/dormitorios/dormitorios' class='btn btn-info'><i class='icon-white icon-arrow-left'></i>Menu</a>&nbsp;<% }%>
		<a href="javascript:agregar()" class="btn btn-primary">Add Bedroom</a>
	</div>
	<form name = "forma" method = "post" action = "cuartos">
	<input type = "hidden" name = "accion">
	<input type = "hidden" name = "cE">
	<table class="table  table-bordered">
	<thead class="table-info">	
		<tr>
			<th class="text-center" width="10%">Operation</th>
			<th class="text-center" width="15%"><spring:message code="aca.Cuartos"/></th>			
			<th class="text-center" width="15%">Hallway</th>
			<th class="text-center" width="10%">Capacity</th>
			<th class="text-center" width="10%">No. Students</th>
			<th class="text-center" width="10%">Available Beds</th>
			<th class="text-center" width="10%">Not Enr. Students</th>
			<th class="text-center" width="10%"><spring:message code="aca.Status"/></th>
		</tr>
	</thead>
	<tbody>
<%	if (accion.equals("agregar")){ %>
		<tr>
			<td><a title='Save' href="javascript:guardar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>
				<input type='button' onclick='document.forma.submit()' class="btn btn-primary" value ='Cancel'></td>
			<td class="text-center">Bedroom: <input type='text' name='cuartoId' size='1' value='<%=nextCuarto%>'></td>						
			<td class="text-center">Hallway: 
				<select name ='pasillo'>
					<option value='A'>A</option>
					<option value='B'>B</option>
					<option value='C'>C</option>
					<option value='D'>D</option>
					<option value='E'>E</option>
					<option value='F'>F</option>
				</select>							
			</td>
			<td align='center'>Capacity: <input type='text' name='cupo' size='2'></td>
			<td align='center'><spring:message code="aca.Status"/>: 
				<select name ='estado'>
					<option value='A'><spring:message code='aca.Activo'/></option>
					<option value='I'><spring:message code='aca.Inactivo'/></option>
				</select>							
			</td>
		</tr>
			<script>document.forma.cupo.focus();</script>
<%	}
	String pasilloTemp  = "X";
	int totPasillo 		= 0;
	int disponibles 	= 0;
	int pendientes		= 0;
	int totAlumnos 		= 0;
	int totPendientes	= 0;
	for (IntCuarto cuarto : vCuartos){
		boolean tiene = false;
		if(mapaTieneAlumnos.containsKey(cuarto.getCuartoId()+cuarto.getDormitorioId())){
			tiene = true;
		}
		
		String numAlumnos = "0";
		if(mapaAsignados.containsKey(cuarto.getCuartoId())){
			numAlumnos = mapaAsignados.get(cuarto.getCuartoId());
		}
		
		disponibles = Integer.parseInt(cuarto.getCupo())-Integer.parseInt(numAlumnos);
		
		String numInscritos = "0";
		if(mapaInscritos.containsKey(cuarto.getCuartoId())){
			numInscritos = mapaInscritos.get(cuarto.getCuartoId());
		}
		
		pendientes = Integer.parseInt(numAlumnos)-Integer.parseInt(numInscritos);
		totPendientes += pendientes;
		if (!cuarto.getPasillo().equals(pasilloTemp)){
			if (!pasilloTemp.equals("X")){				
%>
		<tr class="table-secondary">
			<td colspan="3" class='text-end'><b>Hallway total <%=pasilloTemp%></b></td>
			<td class='text-center'><b><%=totPasillo%></b></td>
			<td colspan="4">&nbsp;</td>
		</tr>
<%					 
					totPasillo = 0;
			}
			pasilloTemp = cuarto.getPasillo();
		}	
		totPasillo = totPasillo + Integer.parseInt(cuarto.getCupo());		
		if (accion.equals("modificar") && cuarto.getCuartoId().equals(cE)){
%>		<tr><td><a name="GO"></td></tr>
		<tr>
			<td class="text-center"><a title='Save' href="javascript:update('<%=cuarto.getCuartoId()%>')" class="btn btn-primary"><spring:message code="aca.Guardar"/>:</a>
				<input type='button' onclick='document.forma.submit()' class="btn btn-primary" value ='Cancel'></td>
			<td class="text-center">Bedroom <%=cuarto.getCuartoId()%></td>							
			<td class="text-center align-content-center">
				<div class="d-flex align-items-center">
					Hallway:&nbsp; 
					<select name ='pasillo' class="form-select" style="width: 5rem;">
						<option value='A' <% if(cuarto.getPasillo().equals("A"))out.print("selected");%>>A</option>
						<option value='B' <% if(cuarto.getPasillo().equals("B"))out.print("selected");%>>B</option>
						<option value='C' <% if(cuarto.getPasillo().equals("C"))out.print("selected");%>>C</option>
						<option value='D' <% if(cuarto.getPasillo().equals("D"))out.print("selected");%>>D</option>
						<option value='E' <% if(cuarto.getPasillo().equals("E"))out.print("selected");%>>E</option>
						<option value='F' <% if(cuarto.getPasillo().equals("F"))out.print("selected");%>>F</option>
					</select>				
				</div>		
			</td>
			<td class="text-center">
				<div class="d-flex align-items-center">
				Capacity:&nbsp;<input class="form-control" style="width: 5rem;" type='text' name='cupo' size='2' value='<%=cuarto.getCupo()%>'>
				</div>
			</td>			
			<td class="text-center"> 
				<div class="d-flex align-items-center">
					<select name ='estado' class="form-select" style="width: 8rem;">
						<option value="A" <%=cuarto.getEstado().equals("A")?"Selected":""%>><spring:message code='aca.Activo'/></option>
						<option value="I" <%=cuarto.getEstado().equals("I")?"Selected":""%>><spring:message code='aca.Inactivo'/></option>
					</select>	
				</div>						
			</td>
		</tr>
<%		}else{
%>		<tr>
			<td class="text-center">
				<a title='Modify' href="javascript:modificar('<%=cuarto.getCuartoId()%>')"><i class="fas fa-edit"></i></a>
<%			if(!tiene){%>
				<a title='Delete' href="javascript:eliminar('<%=cuarto.getCuartoId()%>')" style="color:red"><i class="fas fa-trash-alt"></i></a>
<%			}%>
			</td>
			<td class="text-center">Bedroom <%=cuarto.getCuartoId()%></td>								
			<td class="text-center">Hallway <%=cuarto.getPasillo()%></td>
			<td class="text-center"><%=cuarto.getCupo()%></td>
			<td class="text-center"><%=numAlumnos%></td>
			<td class="text-center"><%=disponibles>=1?"<span class='badge bg-dark rounded-pill'>"+disponibles+"</span>":"<span class='badge bg-secondary rounded-pill'>"+disponibles+"</span>"%></td>
			<td class="text-center"><%=pendientes>=1?"<span class='badge bg-warning rounded-pill'>"+pendientes+"</span>":"<span class='badge bg-secondary rounded-pill'>"+pendientes+"</span>"%></td>
<%			if(cuarto.getEstado().equals("A")){ %>
			<td class="text-center">Active</td>
<%			}else{ %>
			<td class="text-center"><span class='badge bg-warning rounded-pill'>Inactive</span></td>
<%			} %>
		</tr>
<%
		}
		if (cuarto.getEstado().equals("A")){
			cupoTotal += Integer.parseInt(cuarto.getCupo());
			totAlumnos += Integer.parseInt(numAlumnos);				 
		}
	}
	int totDisponibles = cupoTotal-totAlumnos;
%>		<tr class="table-secondary">
			<td colspan="3" ><b>Hallway Total<%=pasilloTemp%></b></td>
			<td class='text-center'><b><%=totPasillo%></b></td>			
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<td style='text-align:right' colspan="3"><b>Totals:</b></td>
			<td class="text-center"><b><%=cupoTotal%></b></td>
			<td class="text-center"><b><%=totAlumnos%></b></td>
			<td class='text-center'><b><%=totDisponibles%></b></td>
			<td class='text-center'><b><%=totPendientes%></b></td>
			<td class="text-center">&nbsp;</td>
		</tr>
	</tbody>	
	</table>  
	<%-- <table style="width:100%; margin: 0 auto;"><tr><td background="../../imagenes/shadow.gif" height="4"></td></tr></table> --%>
	</form>
</div>	

<script>
	jQuery('.cuartos').addClass('active');
</script>