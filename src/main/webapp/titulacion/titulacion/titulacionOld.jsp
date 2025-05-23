<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.tit.RequisitoVO"%>
<%@ page import= "aca.tit.DocAlumVO"%>
<%@ page import= "aca.tit.TituloVO"%>

<jsp:useBean id="bDocumentos"  class="aca.tit.Documentos" scope="page"/>
<jsp:useBean id="bDocAlumno"  class="aca.tit.DocAlumno" scope="page"/>
<jsp:useBean id="bPagoAlumno"  class="aca.tit.PagoAlumno" scope="page"/>
<jsp:useBean id="bTitulo"  class="aca.tit.Titulo" scope="page"/>
<jsp:useBean id="bPagos"  class="aca.tit.Pagos" scope="page"/>
<jsp:useBean id="requisitoVO"  class="aca.tit.RequisitoVO" scope="page"/>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="tipoCal" scope="page" class="aca.catalogo.TipoCalUtil"/>
<%	
	String matricula = (String) session.getAttribute("codigoAlumno");
	
	String cat			= request.getParameter("cat")==null?"":request.getParameter("cat");	
	String saccion		= request.getParameter("accion")==null?"0":request.getParameter("accion");	
	String sdocumentoId	= request.getParameter("documentoId")==null?"0":request.getParameter("documentoId");
	String fecha		= request.getParameter("fecha")==null?"":request.getParameter("fecha");	
	String formapago	= request.getParameter("formapago")==null?"":request.getParameter("formapago");	
	String fechapago	= request.getParameter("fechapago")==null?"":request.getParameter("fechapago");	
	String cantidad		= request.getParameter("cantidad")==null?"":request.getParameter("cantidad");
	String comentario	= request.getParameter("comentario")==null?"":request.getParameter("comentario");	
	String entregado	= request.getParameter("entregado")==null?"":request.getParameter("entregado");	
	String planId		= request.getParameter("planId")==null?"":request.getParameter("planId");	
	String tituloId		= request.getParameter("tituloId")==null?"":request.getParameter("tituloId");	
	String descripcion	= request.getParameter("descripcion")==null?"":request.getParameter("descripcion");
	String sid			= request.getParameter("id")==null?"0":request.getParameter("id");
	
	int accion			= Integer.parseInt(saccion);
	int documentoId		= Integer.parseInt(sdocumentoId);
	int id				= Integer.parseInt(sid);
	
	alumno = AlumUtil.mapeaRegId(conEnoc,matricula);

	boolean nograbado=false,noborro=false;
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
%>

<script>
	function pres(f){
		if(window.event.keyCode==13)
			Guarda(f);
		
	}
	function Guarda(f){
		if(document.getElementById(f)){
			forma=document.getElementById(f)
			if(forma.fecha.value=="")
				alert("Escriba la fecha...");
			else
				forma.submit();
		}
	}

	function borrar(tid,f,id){
			if (confirm("¿Esta seguro que desea borrar el documento?")){
				document.location.href='titulacion.jsp?accion=3&id='+id+'&cat='+f+'&tituloId='+tid
			}
	}
	function modificar(tid,f,id){
			document.location.href='titulacion.jsp?accion=4&id='+id+'&cat='+f+'&tituloId='+tid
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
	function abrir(pag){
		abrirVentana("titulacion",800,600,10,10,"no","yes","yes","no","no",pag,false);
	}

	function abrirDocs(pag){
		abrirVentana("documentos",350,350,150,350,"no","yes","yes","no","no",pag,false);
	}
</script>
<div class="container-fluid">
	<h1>Estado de Avance de Titulación</h1>
<div class="alert alert-info">
	[<%=matricula%>] - [<%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%>]&nbsp;
	<a class="btn btn-primary" href="tit_requisitos.jsp" target="_blank"><b>Requisitos para Titulación</b></a>&nbsp;
	<a class="btn btn-primary" href='titulacion.jsp?cat=nuevoTitulo'><b>Agregar Título</b></a>
</div>

<!-- ---------------------- TITULO ------------------------------- -->
<%if(cat.equals("nuevoTitulo")){
	ArrayList<String> titulosPosibles = bTitulo.getTitulosPosibles(conEnoc,matricula);
	if(titulosPosibles.size()==0)out.print("<table><tr><td><dd><font color=red>El alumno ya tiene asignado el máximo de títulos.</font></td></tr></table>");
	else{
%>
	<form name='formaTitulo' method='post' action='titulacion.jsp?cat=guardaTitulo'>
	<table style="width:90%" class="table table-condesed"><tr><td><table>
	<tr>
		<td>Carrera: </td>
	    <td>
	    	<select name='planId'>
<%				for(int j=0;j<titulosPosibles.size();j++){
%>
	    		<option value='<%=titulosPosibles.get(j)%>'><%=titulosPosibles.get(j)%> - <%=AlumUtil.getCarrera(conEnoc,matricula,(String)titulosPosibles.get(j))%></option>
	    		<%}%>
	    	</select>
	    </td>
	</tr>
	<tr>
		<td>Descripcion: </td>
	    <td><input name='descripcion' size='50' maxlength='50' type='text'/></td>
	</tr>
	<tr>
	    <td colspan='2' style='text-align:center'><input class="btn btn-primary" name='submit' type='submit' value='Aceptar'/></td>
	</tr>
	</table>
	</td></tr>
	</table>
	</form>
	<script>document.formaTitulo.descripcion.focus();</script>
<%}}if(cat.equals("guardaTitulo")){
	TituloVO titulo = new TituloVO();
	titulo.setCodigoPersonal(matricula);
	titulo.setDescripcion(descripcion);
	titulo.setPlanId(planId);
	bTitulo.guardaTitulo(conEnoc,titulo);
 }if(cat.equals("modificarTitulo")){
%>
	<form name='formaTituloM' method='post' action='titulacion.jsp?cat=guardaTituloM'>
<%
	TituloVO titulo=bTitulo.getTitulo(conEnoc,tituloId);
%>
	<table style="width=90%"><tr><td><table>
		<tr>
			<td>Carrera: </td>
		    <td><%=AlumUtil.getCarrera(conEnoc,matricula,titulo.getPlanId())%>
		    </td>
		</tr>
		<tr>
			<td>Descripcion: </td>
		    <td>
		    	<input name='descripcion' size='30' maxlength='50' type='text' value='<%if(titulo.getDescripcion()!=null)out.print(titulo.getDescripcion());%>'/>
				<input type='hidden' name='planId' value='<%=titulo.getPlanId()%>'/>
				<input type='hidden' name='tituloId' value='<%=titulo.getTituloId()%>'/>
			</td>	
		</tr>
		<tr>
		    <td colspan='2' align='center'><input name='submit' type='submit' value='Aceptar'/></td>
		</tr></table>
		</td></tr>
	</table>
	</form>
	<script>document.formaTituloM.descripcion.focus();</script>
<%}if(cat.equals("guardaTituloM")){
	TituloVO titulo = new TituloVO();
	titulo.setCodigoPersonal(matricula);
	titulo.setDescripcion(descripcion);
	titulo.setPlanId(planId);
	titulo.setTituloId(tituloId);

	bTitulo.modificaTitulo(conEnoc,titulo);
  }if(cat.equals("eliminaTitulo")){
  	String ok=bTitulo.eliminaTitulo(conEnoc,tituloId);
  	if(!ok.equals(""))noborro=true;
  }
	ArrayList<aca.tit.TituloVO> planes=bTitulo.getTitulos(conEnoc,matricula);
	for(int j=0;j<planes.size();j++){
		TituloVO titulo = (TituloVO)planes.get(j);
%>
<!-- --------------------- DOCUMENTOS ------------------------------ -->
	<form id="forma<%=titulo.getPlanId()%>" name="forma<%=titulo.getPlanId()%>" method='post' action='titulacion.jsp?cat=documentos<%=titulo.getPlanId()%>'>
	<table style="width=90%" class="table table-condensed">
	<tr>
		<td colspan='6'>
		<img onclick="if (confirm('¿Esta seguro que desea borrar el título?')){document.location.href='titulacion.jsp?cat=eliminaTitulo&tituloId=<%=titulo.getTituloId()%>'}" class="fas fa-trash-alt" alt='Eliminar Título'  />
		<img onclick="document.location.href='titulacion.jsp?cat=modificarTitulo&tituloId=<%=titulo.getTituloId()%>'" class="fas fa-edit" alt='Modificar Titulo'  /> -
						<%=titulo.getPlanId()%> - <%=AlumUtil.getCarrera(conEnoc,matricula,titulo.getPlanId())%> <%if(titulo.getDescripcion()!=null)out.print(" - "+titulo.getDescripcion());%>
		<%if(noborro)out.print("<br><font color=red>No se pudo eliminar, el título tiene documentos.</font>");%>
		</td>
	</tr>
	<tr>
		<td colspan='6' align='center'><a class="btn btn-primary" href='titulacion.jsp?accion=1&cat=documentos<%=titulo.getPlanId()%>&planId=<%=titulo.getPlanId()%>'><b>Agregar Documento</b></a>
		<font size='2'><a class="btn btn-primary" href="javascript:abrirDocs('documentos.jsp?tituloId=<%=titulo.getTituloId()%>&plan=<%=titulo.getPlanId()%>')"><b> Documentos</b></a></font> 		
		<%if((accion==1||accion==4)&&cat.equals("documentos"+titulo.getPlanId())){%>| <a href='titulacion.jsp'><b>Cancelar</b></a></td><%}%>
	</tr>
<%
		if(accion==1&&cat.equals("documentos"+titulo.getPlanId())){
%>
	<tr><td colspan='6'><table onkeypress='pres(this.form)'>
	<tr>
		<td>Documento: </td>
		<td><select name="documentoId">
<%		
			ArrayList<aca.tit.RequisitoVO> documentos = bDocumentos.getDocumentos(conEnoc, " ");
			RequisitoVO documento=null;
			int nivel=bDocAlumno.getNivelPlan(conEnoc,titulo.getPlanId());
			for(int i=0;i<documentos.size();i++){
				documento=(RequisitoVO)documentos.get(i);
				if(documento.getTipo().equals("L")&&nivel==2){
%>					<option value='<%=documento.getId()%>'><%=documento.getNombre()%></option>
<%				}else if(documento.getTipo().equals("P")&&(nivel==3||nivel==4)){
%>				<option value='<%=documento.getId()%>'><%=documento.getNombre()%></option>
<%				}else if(documento.getTipo().equals("S")){
%>				<option value='<%=documento.getId()%>'><%=documento.getNombre()%></option>
<%				}else if(documento.getTipo().equals("G")){
%>				<option value='<%=documento.getId()%>'><%=documento.getNombre()%></option>
<%				}else if(documento.getTipo().equals("O")){
%>				<option value='<%=documento.getId()%>'><%=documento.getNombre()%></option>
<%				}
			}
%>
					</select>
				</td>
			</tr>
			<tr><td>Entregado:</td>
				<td><input type='checkbox' name="entregado" value="S"/>
			</tr>
			<tr>
				<td><spring:message code="aca.Fecha"/>: </td>
				<td><input name="fecha" type="text" class="text" value="<%=sdf.format(new java.util.Date())%>" size='10'/> 

				(DD/MM/AAAA) </td>
			<tr>
				<td>Comentario: </td>
				<td><input name="comentario" type="text" class="text" size='20'/> 
<a href="javascript:Guarda('forma<%=titulo.getPlanId()%>')"><img alt='Guardar'  src='../../imagenes/filesave.png'></img></a>
				<input type='hidden' name='accion' value='2'/>
				<input type='hidden' name='planId' value='<%=titulo.getPlanId()%>'/>
				<input type='hidden' name='tituloId' value='<%=titulo.getTituloId()%>'/>
				</td>
			</tr>
		</table></td></tr>
		<script type='javascript'>document.getElementById('forma<%=titulo.getPlanId()%>').fecha.focus();</script>
<%
		}
		if(accion==2&&cat.equals("documentos"+titulo.getPlanId())){
			java.text.DateFormat df = java.text.DateFormat.getDateInstance();
			DocAlumVO documento = new DocAlumVO();
			documento.setTituloId(tituloId);
			documento.setDocumentoId(documentoId);
			documento.setFecha(fecha);
			documento.setComentario(comentario);
			documento.setEntregado(entregado);
			if(!bDocAlumno.guardaDocumento(conEnoc,documento))
				nograbado=true;
		} 
  		if(accion==3&&cat.equals("documentos"+titulo.getPlanId())){
			bDocAlumno.eliminaDocumento(conEnoc,tituloId,id);
		}
		if(accion==4&&cat.equals("documentos"+titulo.getPlanId())){
		DocAlumVO doc=bDocAlumno.getDocumento(conEnoc,tituloId,id);
%>
		<tr><td colspan='6'><table onkeypress='pres(this.form)'>
			<tr>
				<td>Documento: </td>
				<td><b><%=bDocAlumno.getNombreDocumento(conEnoc,doc.getDocumentoId())%></b>
					<input name="documentoId" type="hidden" value='<%=doc.getDocumentoId()%>'/>  
				</td>
			</tr>
			<tr><td>Entregado:</td>
				<td><input type='checkbox' name="entregado" value="S" <%if(doc.getEntregado().equals("S"))out.print("checked");%>>
			</tr>
			<tr>
				<td><spring:message code="aca.Fecha"/>: </td>
				<td><input name="fecha" type="text" class="text" size='10' value='<%=doc.getFecha()%>'/>(DD/MM/AAAA)</td>
			</tr>
			<tr>
				<td>Comentario: </td>
				<td><input name="comentario" type="text" class="text" size='20' value='<%if(doc.getComentario()==null)out.print("");else out.print(doc.getComentario());%>'/>
				<a href="javascript:Guarda('forma<%=titulo.getPlanId()%>')"><img alt='Guardar'  

src='../../imagenes/filesave.png'></img></a>
				<input type='hidden' name='accion' value='5'/>
				<input type='hidden' name='planId' value='<%=titulo.getPlanId()%>'/>
				<input type='hidden' name='tituloId' value='<%=tituloId%>'/>
				</td>
			</tr>
		</table></td></tr>
		<script type='javascript'>document.getElementById('forma<%=titulo.getPlanId()%>').fecha.focus();</script>
<%		}
		if(accion==5&&cat.equals("documentos"+titulo.getPlanId())){
			DocAlumVO documento = new DocAlumVO();
			documento.setTituloId(tituloId);
			documento.setDocumentoId(documentoId);
			documento.setComentario(comentario);
			documento.setEntregado(entregado);
			documento.setFecha(fecha);
			bDocAlumno.modificaDocumento(conEnoc,documento);
		}
		if(nograbado){
%>
	<tr>
		<td colspan='4'><font color=red>No se grabó, el documento ya existe.</font></td>
	</tr>
<%}%>
	<tr bgcolor='orange'>
		<td width="3%" align='center'></td>
		<td width="2%" align='center'><spring:message code="aca.Numero"/></td>
		<td width="28%" align='center'>Entrega Documento</td>
		<td width="6%" align='center'><spring:message code="aca.Fecha"/></td>
		<td width="19%" align='center'><spring:message code="aca.Comentario"/></td>
	</tr>
<%	
	boolean entro = false, entro2 = false;
	DocAlumVO doc = null;
	ArrayList<aca.tit.DocAlumVO> docs  = bDocAlumno.getEntregaDocumentos(conEnoc,titulo.getTituloId());
	int horas=0;
	for(int i=0;i<docs.size();i++){
		doc = new DocAlumVO();
		doc =(DocAlumVO)docs.get(i);
%>
	<tr valign='top'>
		<td align='center'>
		<a class="fas fa-edit" href="javascript:modificar('<%=titulo.getTituloId()%>','documentos<%=titulo.getPlanId()%>',<%=doc.getDocumentoId()%>);"></a>
		<a class="fas fa-trash-alt" href="javascript:borrar('<%=titulo.getTituloId()%>','documentos<%=titulo.getPlanId()%>',<%=doc.getDocumentoId()%>);"></a>
		</td>
		<td align='center'><%=i+1%></td>
		<td><%if(doc.getEntregado().equals("N"))out.print("<font color=red>");%><%=bDocAlumno.getNombreDocumento(conEnoc,doc.getDocumentoId())%></td>
		<td align='center'><%if(doc.getEntregado().equals("S")) out.print(doc.getFecha()); %></td>
		<td align='center'><%if(doc.getComentario()==null)out.print("");else out.print(doc.getComentario());%></td>
	</tr>
<%	}
	if(docs.size()==0){
%>
		<tr>
			<td colspan='5'>Sin documentos...</td>
		</tr>	
<%	}%>	

	<tr><td>&nbsp;</td></tr>
	<tr bgcolor='orange'>
		<td width="3%" align='center'></td>
		<td width="2%" align='center'><spring:message code="aca.Numero"/></td>
		<td width="28%" align='center'>Servicio Solicitado</td>
		<td width="6%" align='center'><spring:message code="aca.Fecha"/></td>
		<td width="19%" align='center'><spring:message code="aca.Comentario"/></td>
	</tr>
<%	docs = bDocAlumno.getDocumentos(conEnoc,titulo.getTituloId(),"O");
	for(int i=0;i<docs.size();i++){
		doc = new DocAlumVO();
		doc =(DocAlumVO)docs.get(i);	
%>
	<tr valign='top'>
		<td align='center'>
		<a class="fas fa-edit" href="javascript:modificar('<%=titulo.getTituloId()%>','documentos<%=titulo.getPlanId()%>',<%=doc.getDocumentoId()%>);"></a>
		<a class="fas fa-trash-alt" href="javascript:borrar('<%=titulo.getTituloId()%>','documentos<%=titulo.getPlanId()%>',<%=doc.getDocumentoId()%>);"></a>
		</td>
		<td align='center'><%=i+1%></td>
		<td><%if(doc.getEntregado().equals("N"))out.print("<font color=red>");%><%=bDocAlumno.getNombreDocumento(conEnoc,doc.getDocumentoId())%></td>
		<td align='center'><%if(doc.getEntregado().equals("S")) out.print(doc.getFecha()); %></td>
		<td align='center'><%if(doc.getComentario()==null)out.print("");else out.print(doc.getComentario());%></td>
	</tr>
<%	}
	if(docs.size()==0){
%>
		<tr>
			<td colspan='5'>Sin documentos...</td>
		</tr>	
<%	}%>	

	<tr><td>&nbsp;</td></tr>
	<tr bgcolor='orange'>
		<td width="3%" align='center'></td>
		<td width="2%" align='center'><spring:message code="aca.Numero"/></td>
		<td width="28%" align='center'>Avance Documento</td>
		<td width="6%" align='center'><spring:message code="aca.Fecha"/></td>
		<td width="19%" align='center'><spring:message code="aca.Comentario"/></td>
	</tr>
<%		
	docs = bDocAlumno.getDocumentos(conEnoc,titulo.getTituloId(),"S");
	for(int i=0;i<docs.size();i++){
		doc = new DocAlumVO();
		doc =(DocAlumVO)docs.get(i);
%>
	<tr valign='top'>
		<td align='center'>
		<a class="fas fa-edit" href="javascript:modificar('<%=titulo.getTituloId()%>','documentos<%=titulo.getPlanId()%>',<%=doc.getDocumentoId()%>);"></a>
		<a class="fas fa-trash-alt" href="javascript:borrar('<%=titulo.getTituloId()%>','documentos<%=titulo.getPlanId()%>',<%=doc.getDocumentoId()%>);"></a>		
		</td>
		<td align='center'><%=i+1%></td>
		<td><%if(doc.getEntregado().equals("N"))out.print("<font color=red>");%><%=bDocAlumno.getNombreDocumento(conEnoc,doc.getDocumentoId())%></td>
		<td align='center'><%if(doc.getEntregado().equals("S")) out.print(doc.getFecha()); %></td>
		<td align='center'><%if(doc.getComentario()==null)out.print("");else out.print(doc.getComentario());%></td>
	</tr>
<%	}
	if(docs.size()==0){
%>
		<tr>
			<td colspan='5'>Sin documentos...</td>
		</tr>	
<%	}%>	
</table>
</form>
<form id="fpago<%=titulo.getPlanId()%>" name='fpago<%=titulo.getPlanId()%>' method='post' action='titulacion.jsp?cat=pago<%=titulo.getPlanId()%>&tituloId=<%=titulo.getTituloId()%>'>
<table style="width:90%">
<%
		if(accion==7&&cat.equals("pago"+titulo.getPlanId())){
			DocAlumVO vpago=new DocAlumVO();
			vpago.setDocumentoId(Integer.parseInt(formapago));
			vpago.setTituloId(tituloId);
			vpago.setCantidad(Double.parseDouble(cantidad));
			vpago.setComentario(comentario);
			vpago.setFecha(fechapago);
			bPagoAlumno.guardaPago(conEnoc,vpago);
		}
		if(accion==9&&cat.equals("pago"+titulo.getPlanId())){
			DocAlumVO vpago=new DocAlumVO();
			vpago.setDocumentoId(Integer.parseInt(formapago));
			vpago.setTituloId(tituloId);
			vpago.setCantidad(Double.parseDouble(cantidad));
			vpago.setComentario(comentario);
			vpago.setFecha(fechapago);
			bPagoAlumno.modificaPago(conEnoc,vpago);
		}
		if(accion==10&&cat.equals("pago"+titulo.getPlanId())){
			bPagoAlumno.eliminaPago(conEnoc,tituloId);
		}
		DocAlumVO pago = bPagoAlumno.getPago(conEnoc,titulo.getTituloId());
		if(pago!=null||accion==6&&cat.equals("pago"+titulo.getPlanId())){
%>
	<tr><td align='right'>
<%
		if(accion!=6&&pago!=null){
%>
		<img onclick="if (confirm('¿Esta seguro que desea borrar la forma de pago?')){document.location.href='titulacion.jsp?accion=10&tituloId=<%=titulo.getTituloId()%>&cat=pago<%=titulo.getPlanId()%>';}" alt='Eliminar'  class="fas fa-trash-alt" />
<%}%>
		<b>Forma de Pago:</b><td>
<%		
		if(accion==6&&cat.equals("pago"+titulo.getPlanId())){
			ArrayList<aca.tit.RequisitoVO> pagos=bPagos.getPagos(conEnoc);
			out.println("<select name='formapago'>");
			RequisitoVO vpago;
			for(int i=0;i<pagos.size();i++){
				vpago=new RequisitoVO();
				vpago = (RequisitoVO)pagos.get(i);
				out.println("<option value='"+vpago.getId()+"'>"+vpago.getNombre()+"</option>");
			}
			out.println("</select>");
		}else if(accion==8&&cat.equals("pago"+titulo.getPlanId())){
			ArrayList<aca.tit.RequisitoVO> pagos=bPagos.getPagos(conEnoc);
			out.println("<select name='formapago'>");
			RequisitoVO vpago=null;
			for(int i=0;i<pagos.size();i++){
				vpago = (RequisitoVO)pagos.get(i);
				if(pago.getDocumentoId()==vpago.getId())
					out.println("<option selected value='"+vpago.getId()+"'>"+vpago.getNombre()+"</option>");
				else
					out.println("<option value='"+vpago.getId()+"'>"+vpago.getNombre()+"</option>");
			}
			out.println("</select>");
		}
		else {out.print(bPagoAlumno.getNombrePago(conEnoc,pago.getDocumentoId())+
			", por la cantidad de $" + pago.getCantidad() + ", el día "+pago.getFecha());
			if(pago.getComentario()!=null)out.print(", "+ pago.getComentario());
			out.print("<a class='btn' href='titulacion.jsp?accion=8&cat=pago"+titulo.getPlanId()+"'>  Modificar </a>");
		}
%>
	</tr>
<%
	if((accion==6||accion==8)&&cat.equals("pago"+titulo.getPlanId())){
%>
	<tr><td align='right'><b>Cantidad:</b><td>
		<%if(accion==6&&cat.equals("pago"+titulo.getPlanId()))out.print("<input type='text' name='cantidad'/>");
		  else if(accion==8&&cat.equals("pago"+titulo.getPlanId()))out.print("<input value='"+pago.getCantidad()+"' type='text' name='cantidad'/>");
%>
	</tr>
	<tr><td align='right'><b><spring:message code="aca.Fecha"/>:</b><td>
		<%if(accion==6&&cat.equals("pago"+titulo.getPlanId()))out.print("<input type='text' name='fechapago'/><td> (DD/MM/AAAA)");
		  else if(accion==8&&cat.equals("pago"+titulo.getPlanId()))out.print("<input value='"+pago.getFecha()+"' type='text' name='fechapago'/><td> (DD/MM/AAAA)");
%>
	</tr>
	<tr><td align='right' colspan="2">
		<%if(accion==6&&cat.equals("pago"+titulo.getPlanId()))out.print("<b>Comentario: </b><input type='text' name='comentario'/>");
		  else if(accion==8&&cat.equals("pago"+titulo.getPlanId())){out.print("<b>Comentario: </b><input value='");
			  if(pago.getComentario()!=null)out.print(pago.getComentario());
			  out.print("' type='text' name='comentario'/>");
		  }
%>
	</tr>
<%	}if((accion==6&&cat.equals("pago"+titulo.getPlanId()))||(accion==8&&cat.equals("pago"+titulo.getPlanId()))){
		out.println("<tr><td align='center' colspan='2'><input type='submit' name='submit' value='Guardar'/>");
		out.println("<input type='button' name='botob' value='Cancelar' onClick=\"document.location.href=\'titulacion.jsp'\"/></td></tr>");
		out.println("<script type='javascript'>document.getElementById('fpago"+titulo.getPlanId()+"').cantidad.focus();</script>");
	}
	if(accion==6&&cat.equals("pago"+titulo.getPlanId()))out.println("<input type='hidden' name='accion' value='7'>");
	if(accion==8&&cat.equals("pago"+titulo.getPlanId()))out.println("<input type='hidden' name='accion' value='9'>");
	}
if(pago==null){
%>
	<tr><td><a href="titulacion.jsp?accion=6&cat=pago<%=titulo.getPlanId()%>">Agregar Forma de Pago</a></td></tr>
<%	}%>
</table>
</form>
</div>
<%}%>
<%@ include file= "../../cierra_enoc.jsp" %>