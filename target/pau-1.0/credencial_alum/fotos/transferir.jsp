<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.FileInputStream"%>
<head>
<title>Fotos de alumnos</title>
<script type="text/javascript">
	function muestra(f){
		    document.forma.foto.focus();
    		DomiShell= new ActiveXObject("WScript.Shell");
		    DomiShell.SendKeys(f+"~");
		    //+"~"
	}
</script>
<STYLE TYPE="text/css">
.tabbox
	{
		background: #EFEFEF;
		border-left: 0pt gray solid;
		border-right: 0pt gray solid;
		border-bottom: 1pt gray solid;
	}
</STYLE>
<link href="/academico.css" rel="STYLESHEET" type="text/css">
</head>
<%
	String foto="";
	String directorio="";
	String num =request.getParameter("num");
	if(num==null)num="0";
	String accion=request.getParameter("accion");
	if(accion==null)accion="";
	directorio=request.getParameter("direc");
	String Lista[]=null;
	boolean grabado=false;
	if(accion.equals("2")){
		File Dir = new File(directorio);
		Lista = Dir.list();
        MultipartRequest multi = new MultipartRequest(request,application.getRealPath("/WEB-INF/fotos/")+"/",5*1024*1024); 
		num=Integer.parseInt(num)+1+"";
		if(Integer.parseInt(num)<Lista.length)
		grabado=true;
	}
	if(accion.equals("0")){
		int pos=0;
		directorio=directorio.replace('@','\\');
		if(directorio.lastIndexOf("\\")!=-1)pos=directorio.lastIndexOf("\\");
		else pos=directorio.lastIndexOf("/");
		directorio=directorio.substring(0,pos);
		File Dir = new File(directorio);
		Lista = Dir.list();
		File ListaArchivos[] = Dir.listFiles();
		foto=directorio+"\\"+Lista[Integer.parseInt(num)];
		accion="2";
		File nt = new File(directorio+"\\NoTransferidos.txt");
		if(nt.exists())nt.delete();
	}
	if(accion.equals("1")){
		File Dir = new File(directorio);
		Lista = Dir.list();
		File ListaArchivos[] = Dir.listFiles();
		if(Lista.length>Integer.parseInt(num))
			foto=directorio+"\\"+Lista[Integer.parseInt(num)];
		accion="2";
	}
	if(grabado&&accion.equals("2"))accion="1";
	String no = request.getParameter("no");
	if(no==null)no="0";
%>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0">
<table style="width:100%" height="100%" class="tabbox" cellspacing="10"  align="center">
	<form name='forma' method='post' enctype="multipart/form-data" action='transferir.jsp?accion=<%=accion%>&num=<%=num%>&direc=<%=directorio%>'>
	<tr>
		<td>
 		 <fieldset style="padding-bottom:10; margin-bottom: 6;">
 		 <font face='Verdana' size='2'><b>
<%			if(Integer.parseInt(num)<Lista.length){
				out.print("Transfiriendo: "+Lista[Integer.parseInt(num)]+"..."+
				"<br>"+new Double((Double.parseDouble(num)/Lista.length)*100).intValue()+"%"+
				"... "+num+" de " + Lista.length +
				"<br><font color='red'>NO DE CLIC EN OTRA VENTANA!!!</font>"+
				"<br><br><input size='40' type='file' id='foto' name='foto'/>");
			}
			else{
				String str="";
				//if(!no.equals("0"))no=Integer.parseInt(no)-1+"";
				if(Lista[Integer.parseInt(num)-1].equals("NoTransferidos.txt"))
					str="Revise en su carpeta: "+Lista[Integer.parseInt(num)-1]+"...";
				else
					str="Transfiriendo: "+Lista[Integer.parseInt(num)-1]+"...";
				out.print(str+"<br>"+new Double((Double.parseDouble(num)/Lista.length)*100).intValue()+"%");
				if(!no.equals("0")){
					num=Integer.parseInt(num)-1+"";
					no=Integer.parseInt(no)-1+"";
				}
				out.print("<br><font color='red'>Compleado... "+(Integer.parseInt(num)-Integer.parseInt(no))+" Imagenes, Excluidas: "+no+"</font><br><br>"+
				"<p align='center'><input onClick='window.close()' type='reset' value=' Aceptar ' name='cerrar'/></p>");
				if(!no.equals("0"))num=Integer.parseInt(num)+1+"";
			}
%>		</b></font>
		</fieldset>
		</td>
	</tr>
	</form>
</table>
	<script>
<%			if(Lista.length>Integer.parseInt(num)){
				if((Lista[Integer.parseInt(num)].indexOf("jpg")!=-1||Lista[Integer.parseInt(num)].indexOf("JPG")!=-1)&&(Lista[Integer.parseInt(num)].length()==11||Lista[Integer.parseInt(num)].length()==13)){
%>					document.forma.action+="&no="+<%=no%>;
					muestra('<%=foto%>');
				<%}else{
					if(!grabado){
						File nt = new File(directorio+"\\NoTransferidos.txt");
						String contenido="";
						if(nt.exists()){
							FileInputStream archivo = new FileInputStream(directorio+"\\NoTransferidos.txt");
							byte[] b = new byte[archivo.available()];  
							archivo.read(b);
							archivo.close();
							contenido = new String(b);
						}
						contenido+=Lista[Integer.parseInt(num)];
						FileOutputStream flog=null;
						flog = new FileOutputStream(directorio+"\\NoTransferidos.txt");
						flog.write(contenido.getBytes());
						flog.write('\r');flog.write('\n');
						flog.flush();
						flog.close();
						no=Integer.parseInt(no)+1+"";
					}
%>				
					document.forma.action+="&no="+<%=no%>;
					muestra('');
				<%}%>				
			<%}%>
	</script>
</body>