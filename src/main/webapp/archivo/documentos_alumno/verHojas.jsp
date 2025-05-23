<%@page import="aca.util.Fecha"%>
<%	
	// String codigoAlumno					= (String) session.getAttribute("codigoAlumno");	
	
	String documentoId		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
	String statusId			= request.getParameter("StatusId")==null?"0":request.getParameter("StatusId");
	String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String nombreDocumento	= (String)request.getAttribute("nombreDocumento");
	String nombreStatus		= (String)request.getAttribute("nombreStatus");
%>
	<h4>Elija el n&uacute;mero de hoja y el origen</h4>
	<table style="width:100%">	
	<tr>
		<td>
			<div style="height:100%; overflow: auto;">
				<table style="width:100%" class="tabla">
					<tr>
						<td><b>Documento: </b></td>
						<td><%=nombreDocumento%></td>
					</tr>
					<tr>
						<td><b>Estatus: </b></td>
						<td><%=nombreStatus%></td>
					</tr>
					<tr>
						<td><b>Hoja: </b></td>
						<td>
							<select class="input input-mini" id="hoja">
							<%	for(int i = 0; i < 30; i++){ %>
									<option value="<%=i+1 %>"><%=i+1 %></option>
							<% 	} %>
							</select>
						</td>
					</tr>
					<tr>
						<td><b>Status: </b></td>
						<td>
							<select class="input input-small" id="origen">
								<option value="O">Original</option>
								<option value="C">Copia</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td align="center">
			<input type="button" class="btn btn-primary" value="Asignar" onclick="asignarDocument('<%=documentoId%>', '<%=statusId%>', '<%=folio%>');" />
		</td>
	</tr>
	</table>