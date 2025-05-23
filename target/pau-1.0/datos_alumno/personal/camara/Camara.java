
package camara;

import javax.swing.JApplet;
import javax.swing.JLabel;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.media.*;
import javax.media.control.*;
import javax.media.protocol.*;
import javax.media.util.BufferToImage;
import javax.media.format.VideoFormat;

import javax.swing.JOptionPane;

public class Camara extends JApplet {
	private camDataSource dataSource;
    
    private DataSource camSource;
    private Processor processor;
    private camStateHelper playhelper;
    
    private javax.swing.JPanel centerPanel;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JToolBar mainToolBar;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JPanel northPanel;
    private javax.swing.JButton fotoButton;
    private javax.swing.JLabel tapa;
    private javax.swing.JPanel southPanel;

    private StringBuffer sb = new StringBuffer();
    private String matricula, page;
    
	public void init()
	{
		matricula = getParameter("matricula");
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		dataSource = new camDataSource(null);
        dataSource.setMainSource();
        dataSource.makeDataSourceCloneable();        
        dataSource.startProcessing();
        /***/
        
        //this.dataSource = dataSource;
        this.dataSource.setParent(this);
        camSource = dataSource.cloneCamSource();
        initComponents();
        try{        	
            processor = Manager.createProcessor(camSource);
        }catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
               "Exception creating processor: " + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }catch (NoProcessorException e) {
            JOptionPane.showMessageDialog(this, 
               "Exception creating processor: " + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        playhelper = new camStateHelper(processor);
        if(!playhelper.configure(10000)){
            JOptionPane.showMessageDialog(this, 
               "cannot configure processor", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        //checkIncoding(processor.getTrackControls());
        processor.setContentDescriptor(null);
        if(!playhelper.realize(10000)){
            JOptionPane.showMessageDialog(this, 
               "cannot realize processor", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        setJPEGQuality(processor, 1.0f);
        processor.start();
        processor.getVisualComponent().setBackground(Color.gray);
        processor.getVisualComponent().setBounds(-140,0,640,480);
        centerPanel.add(processor.getVisualComponent(), BorderLayout.CENTER);
        setSize(360,535);
        
	}
    public void checkIncoding(TrackControl track[]){
        for (int i = 0; i < track.length; i++) {
            Format format = track[i].getFormat();
            if (track[i].isEnabled() && format instanceof VideoFormat) {
                Dimension size = ((VideoFormat)format).getSize();
                float frameRate = ((VideoFormat)format).getFrameRate();
                int w = (size.width % 8 == 0 ? size.width :(int)(size.width / 8) * 8);
                int h = (size.height % 8 == 0 ? size.height :(int)(size.height / 8) * 8);
                VideoFormat jpegFormat = new VideoFormat(
                   VideoFormat.JPEG_RTP, new Dimension(w, h), Format.NOT_SPECIFIED, Format.byteArray, frameRate);
            }
        }
    }
	void setJPEGQuality(Player p, float val) {
        Control cs[] = p.getControls();
        QualityControl qc = null;
        VideoFormat jpegFmt = new VideoFormat(VideoFormat.JPEG);
        
        // Loop through the controls to find the Quality control for
        // the JPEG encoder.
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] instanceof QualityControl && cs[i] instanceof Owned) {
                Object owner = ((Owned)cs[i]).getOwner();
                // Check to see if the owner is a Codec.
                // Then check for the output format.
                if (owner instanceof Codec) {
                    Format fmts[] = ((Codec)owner).getSupportedOutputFormats(null);
                    for (int j = 0; j < fmts.length; j++) {
                        if (fmts[j].matches(jpegFmt)) {
                            qc = (QualityControl)cs[i];
                            qc.setQuality(val);
                            break;
                        }
                    }
                }
                if (qc != null) break;
            }
        }
    }

	private void initComponents() {//GEN-BEGIN:initComponents
        northPanel = new javax.swing.JPanel();
        messageLabel = new javax.swing.JLabel();
        southPanel = new javax.swing.JPanel();
        mainToolBar = new javax.swing.JToolBar();
        fotoButton = new javax.swing.JButton();
        //fileLabel = new javax.swing.JLabel();
        centerPanel = new javax.swing.JPanel();
        tapa = new JLabel();
        
        messageLabel.setText("Listo");
        
        northPanel.setLayout(new java.awt.BorderLayout());
        northPanel.add(messageLabel, java.awt.BorderLayout.CENTER);
        getContentPane().add(northPanel, java.awt.BorderLayout.NORTH);

        southPanel.setLayout(new java.awt.BorderLayout());

        fotoButton.setText("             Tomar Foto             ");
        fotoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fotoButtonActionPerformed(evt);
            }
        });

        mainToolBar.add(fotoButton);

        southPanel.add(mainToolBar, java.awt.BorderLayout.CENTER);
        getContentPane().add(southPanel, java.awt.BorderLayout.SOUTH);
        centerPanel.setLayout(null);
        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);
    }
	
	private void fotoButtonActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("Tomando foto de "+matricula+"...");
		FrameGrabbingControl frameGrabber = (FrameGrabbingControl)processor.getControl("javax.media.control.FrameGrabbingControl");
		System.out.println("Procesando...");
		Buffer buf = frameGrabber.grabFrame();
		Image img = (new BufferToImage((VideoFormat)buf.getFormat()).createImage(buf));
		BufferedImage buffImg = new BufferedImage(360, 480, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		g.drawImage(img,-140,0, null);
		
		System.out.println("Creando direcorio c:\\FotosCredencial\\.....");
	    boolean success = (new File("c:\\FotosCredencial\\")).mkdirs();
	    if (!success) {
	    	System.out.println("Direcorio ya existe...");
	    }
		File f = new File("c:\\FotosCredencial\\"+matricula+".jpg");
		try {
			ImageIO.write(buffImg, "jpeg", f);
			System.out.println("Imagen guardada con exito...");
			System.out.println(f.getAbsolutePath());
			System.out.println("Enviando al servidor...");
			enviarFoto(f);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No se creo la imagen...");
		}
		messageLabel.setText("OK!");
		System.out.println("Listo!");
		try{
			getAppletContext().showDocument(new URL("javascript:refrescar()"));
		}
		catch(Exception e){e.printStackTrace();}
	}
	private void enviarFoto(File f){
	    URL url = null;
	    URLConnection urlConn = null;
	    DataOutputStream dOut = null;
	    BufferedReader bInp = null;
	    page = getCodeBase().toString()+"archivo.jsp?matricula="+matricula;
	    System.out.println(page);
	    try{
	      url = new URL(page);
	      // Setting up the connection for upload.
	      urlConn = url.openConnection();
	      urlConn.setDoInput (true);
	      urlConn.setDoOutput (true);
	      urlConn.setUseCaches (false);
	      String boundary = "-----------------------------" + getRandomString();
	      urlConn.setRequestProperty("Content-Type",
	                                 "multipart/form-data; boundary=" +
	                                 boundary.substring(2, boundary.length()));
	      String CRLF = "\r\n";

	      // Retrieve OutputStream For upload (Post).
	      dOut = new DataOutputStream(urlConn.getOutputStream());

	      // Actual Uploading part.
	      StringBuffer sb;
	      int uploadedLength = 0;
	      sb = new StringBuffer();
	      // Line 1.
	      sb.append(boundary);sb.append(CRLF);
	      // Line 2.
	      sb.append("Content-Disposition: form-data; name=\"File");
	      sb.append("\"; filename=\"");sb.append(f.toString());
	      sb.append("\"");sb.append(CRLF);
	      // Line 3 & Empty Line 4.
	      sb.append("Content-Type: application/octet-stream");
	      sb.append(CRLF);sb.append(CRLF);
	      // Write to Server the 4 Lines, a File and the CRLF.
	      dOut.writeBytes(sb.toString());
	      uploadFileStream(f,dOut);
	      dOut.writeBytes(CRLF);

	      // Telling the Server we have Finished.
	      dOut.writeBytes(boundary);dOut.writeBytes("--");dOut.writeBytes(CRLF);
	      dOut.flush ();

	      bInp = new BufferedReader(new InputStreamReader(urlConn.getInputStream ()));

	      String str;
	      while (null != ((str = bInp.readLine()))){
	        this.addServerOutPut(str);
	      }
	    }catch(Exception e){
	      e.printStackTrace();
	    }finally{
	      try{
	        bInp.close();
	      }catch(Exception e){}
	      bInp = null;
	      try{
	        dOut.close();
	      }catch(Exception e){}
	      dOut = null;
	      urlConn = null;
	      url = null;
	    }

	}
	private void uploadFileStream(File f, DataOutputStream dOut) throws FileNotFoundException, IOException{
		byte[] byteBuff = null;
		FileInputStream fis = null;
		try{
			int numBytes = 0;
			byteBuff = new byte[1024];
			fis = new FileInputStream(f);
			while(-1 != (numBytes = fis.read(byteBuff))){
				dOut.write(byteBuff, 0, numBytes);
			}
		}finally{
			try{
				fis.close();
			}catch(Exception e){}
			byteBuff = null;
		}
	}
	private void addServerOutPut(String s){
	    if(0 < sb.length() || !s.equals("")){
	      sb.append(s);
	    }
	}	
    private String getRandomString(){
        String alphaNum="1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sbRan = new StringBuffer(11);
        int num;
        for(int i = 0; i < 11; i++){
          num = (int)(Math.random()* (alphaNum.length() - 1));
          sbRan.append(alphaNum.substring(num, num+1));
        }
        return sbRan.toString();
      }
    public void stop() {    	
        System.out.println("Deteniendo camara...");
        dataSource.setProcessing(false);
        dataSource.stop();
        try {
			camSource.stop();
		} catch (IOException e) {
			System.out.println("Error parando camSource:");
			e.printStackTrace();
		}
		camSource.disconnect();
		System.out.println("...Deteniendo PROCESSOR 2");
        processor.stop();
        processor.close();
        processor.deallocate();
        playhelper.close();
        
        camSource = null;
		processor = null;
		dataSource = null;
		
        System.out.println("Listo!!!");
  }
}