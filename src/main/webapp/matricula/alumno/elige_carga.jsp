<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	Statement stmt = conEnoc.createStatement();
	ResultSet rset = null;
	String COMANDO = "";

	String s_codigo_personal = (String) session.getAttribute("codigoAlumno");
%>
<table class="goback">
	<tr>
		<td><a href="inscripcion?Accion=0">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
	</tr>
</table>
<form name="documento" method="post" action="elige_bloque">
	<table align="CENTER" width="100%" >
		<tr>
			<td class="titulo">Elegir Carga</td>
		</tr>
		<tr>
			<td align="center">&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
			<select name="f_carga" size="10">
					<%
						COMANDO = "select " + "carga_id, " + "nombre_carga "
								+ "from ENOC.carga " + "Where estado = '1' Order By 2";
						rset = stmt.executeQuery(COMANDO);
						while (rset.next()) {
							out.print("<option value='" + rset.getString("carga_id") + "' ");
							out.print(" >" + rset.getString("nombre_carga") + "</option>");
						}
					%>
			</select>
			</td>
		</tr>
		<tr>
			<td align="center"><input type="submit" class="btn btn-primary"
				name="Aceptar" value="Aceptar"> <input type="hidden"
				name="f_codigo_personal" value="<%=s_codigo_personal%>">
			</td>
		</tr>
	</table>
</form>
<%
	if (stmt != null) {	stmt.close();}
	if (rset != null) {	rset.close();}
%>
<!-- fin de estructura -->
<%@ include file="../../cierra_enoc.jsp"%>
