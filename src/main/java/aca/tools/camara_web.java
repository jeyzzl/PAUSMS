//package aca.tools;
//
//import javax.media.*;
//import javax.media.control.*;
//import javax.media.protocol.*;
//import javax.media.util.*;
//import javax.media.cdm.CaptureDeviceManager;
//import javax.media.format.*;
//import javax.media.protocol.*;
//
//import javax.swing.*;
//import javax.swing.event.*;
//import java.awt.Image;
//import java.util.Vector;
//import java.util.Iterator;
//import javax.imageio.*;
//import java.io.*;
//import java.awt.image.RenderedImage;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.image.*;
//
//public class camara_web extends JFrame implements ActionListener {
//Player player = null;
//String dispositivo = "vfw:Microsoft WDM Image Capture (Win32):0";
//JButton botonSalir,boton = null;
//JLabel etiqueta,etiqueta2;
//JPanel panelCam,panelBotones;
//Image img = null;
//
//public void imprime_camara() throws Exception{
//boton = new JButton("Captura");
//boton.setMnemonic('C');
//boton.setSize(100,40);
//boton.addActionListener(this);
//botonSalir = new JButton("Salir");
//botonSalir.setMnemonic('S');
//botonSalir.setSize(100,40);
//botonSalir.addActionListener(this);
//etiqueta=new JLabel("Toma Actual ");
//etiqueta2 = new JLabel("ELIGE ");
//panelCam = new JPanel();
//panelBotones = new JPanel();
//
//CaptureDeviceInfo dev = CaptureDeviceManager.getDevice(dispositivo);
//Format[] cfmts = dev.getFormats();
//RGBFormat fmt = (RGBFormat)cfmts[4];
//MediaLocator loc = dev.getLocator();
//player = Manager.createRealizedPlayer(loc);
//player.start();
//
//Component comp;
//if ((comp = player.getVisualComponent()) != null){
//panelCam.setBackground(Color.white);
//panelCam.add(etiqueta);
//panelCam.add(comp,BorderLayout.CENTER);
//getContentPane().add(panelCam,BorderLayout.CENTER);
//}
//panelBotones.setBackground(Color.white);
//panelBotones.add(etiqueta2);
//panelBotones.add(boton,BorderLayout.CENTER);
//panelBotones.add(botonSalir,BorderLayout.CENTER);
//getContentPane().add(panelBotones,BorderLayout.NORTH); setSize(500,350);
//setLocation(40,350);
//setResizable(false);
//setTitle(" CAPTURA DE IMAGEN");
//setVisible(true);
//this.addWindowListener(
//new WindowAdapter() {
//public void windowClosing(WindowEvent e) {
//dispose();
//System.exit(0);
//}
//}
//);
//}
//
//public void actionPerformed(ActionEvent e) {
//String ac = e.getActionCommand();
//if (ac.equals("Captura")) {
//int exito;
//FrameGrabbingControl fgc = (FrameGrabbingControl)
//player.getControl("javax.media.control.FrameGrabbi ngControl");
//Buffer buf = fgc.grabFrame();
//BufferToImage btoi = new BufferToImage((VideoFormat)buf.getFormat());
//img = btoi.createImage(buf);
//File imagenArch = new File("Figura.jpeg");
//String formato = "JPEG";
//try {
//ImageIO.write((RenderedImage) img,formato,imagenArch);
//} catch (IOException ioe) {
//System.out.println("Error al guardar la imagen");
//}
//img.flush();
//img = Toolkit.getDefaultToolkit().getImage("Figura.jpeg" );
//MediaTracker tracker = new MediaTracker( this );
//tracker.addImage( img,1 );
//try {
//if( !tracker.waitForAll( 1000 ) ) {
//exito=0;
//System.out.println( "problemas al tiempo de cargar" );
//}
//} catch( InterruptedException l ) {
//System.out.println( l );
//}
//}
//if (ac.equals("Salir")) {
//player.close();
//System.exit(0);
//}
//}
//
//public static void main (String[] args) throws Exception {
//System.out.println("Camara web");
//System.out.println("Inicializando...");
//camara_web camara = new camara_web();
//camara.imprime_camara();
//}
//}