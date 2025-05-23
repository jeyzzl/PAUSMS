package camara;

/*
 * Created on 12/09/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.io.*;

import javax.media.*;
import javax.media.format.*;
import javax.media.util.*;
import javax.media.control.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import com.sun.image.codec.jpeg.*;

public class VideoCaptureView extends JPanel implements ActionListener
{
/*Counts the number of taken pictures and enumerates the saved files*/ 
	public static int pictureCount = 1;

	public static Player player = null;
	private CaptureDeviceInfo captureDeviceInfo = null;
	private MediaLocator mediaLocator = null;
	private Buffer buf = null;
	private Image img = null;
	private VideoFormat vf = new RGBFormat();
	
	private BufferToImage btoi = null;
	private Component comp = null;
	
	private Dimension viewSize = new Dimension(640, 480);
	private int viewDepth = 24;
	
	public static void main(String args[]){
		VideoCaptureView v = new VideoCaptureView();
		
	}
	public VideoCaptureView() 
	{
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		setLayout(new BorderLayout());
	
	
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		setLayout(new BorderLayout());
	
		String mediaFile = "vfw:Microsoft WDM Image Capture (Win32):0";//"vfw:Video Blaster WebCam Go Plus (VFW):1";
		captureDeviceInfo = CaptureDeviceManager.getDevice(mediaFile);
		mediaLocator = new MediaLocator(mediaFile);
		Format[] format = new Format[1];
		Format formatArray = new Format("YUV Video Format: Size = java.awt.Dimension[width=640,height=480] MaxDataLength = 460800 DataType = class [B yuvType = 2 StrideY = 640 StrideUV = 320 OffsetY = 0 OffsetU = 307200 OffsetV = 384000");
		format[0] = formatArray;
		captureDeviceInfo = new CaptureDeviceInfo(mediaFile,mediaLocator,format);//CaptureDeviceManager.getDevice(mediaFile);
		for(int i=0; i<captureDeviceInfo.getFormats().length; i++)
		{
			System.out.println(captureDeviceInfo.getFormats()[i].toString());
		}
		try 
		{
			player = Manager.createRealizedPlayer(mediaLocator);
			player.start();
			if ((comp = player.getVisualComponent()) != null)
			{
				add("Center", comp);
			}
			if ((comp = player.getControlPanelComponent()) != null)
			{
				add("North", comp);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}


public void playerclose() 
{
player.close();
player.deallocate();
}


public void actionPerformed(ActionEvent e) 
{ }

public void grabPicture()
{
// Grab a frame
// Grab a frame
FrameGrabbingControl fgc = (FrameGrabbingControl)
player.getControl("javax.media.control.FrameGrabbingControl");
buf = fgc.grabFrame();

// Convert it to an image
btoi = new BufferToImage((VideoFormat)buf.getFormat());
img = btoi.createImage(buf);

// save image
//saveJPG(img,Inititator.picturePathString+"\\"+pictureCount+".jpg");
pictureCount = pictureCount+1;


}


public static void saveJPG(Image img, String s)
{
BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
Graphics2D g2 = bi.createGraphics();
g2.drawImage(img, null, null);

FileOutputStream out = null;
try
{ 
out = new FileOutputStream(s); 
}
catch (java.io.FileNotFoundException io)
{ 
System.out.println("File Not Found"); 
}

JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
param.setQuality(0.5f,false);
encoder.setJPEGEncodeParam(param);

try 
{ 
encoder.encode(bi); 
out.close(); 
}
catch (java.io.IOException io) 
{
System.out.println("IOException"); 
}
} 
}