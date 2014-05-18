package com.example.twoactivitytest;

import java.util.ArrayList;

import org.w3c.dom.Element;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;


public class MyPolygon extends MyBoundedShape{

	public MyPolygon( int x1, int y1, int x2, int y2, int thickness, int color, int color2, boolean gradient, boolean filled, boolean dashed, int dlength )
    {
		super( x1, y1, x2, y2, thickness, color, color2, gradient, filled, dashed, dlength ); //calls super constructor
    } // end MyLine constructor
	  public void draw()
	    {

	        //g2.drawLine( getX1(), getY1(), getX2(), getY2() ); //draws the lines according to the coordinates
	    } // end method draw
	    public void draw( Canvas canvas )
	    {
			Paint paint = new Paint();

			paint.setColor(getColor());

			paint.setStrokeWidth(getThickness());

			if(!getFilled()){
				paint.setStyle(Paint.Style.STROKE);
			}
	        ArrayList<Integer> xPts=getXPts();
	        ArrayList<Integer> yPts=getYPts();
	        int s=xPts.size();
	        Path wallpath = new Path();
	        wallpath.moveTo(xPts.get(0), yPts.get(0));
	        for(int i=0;i<xPts.size();i++){
	        	wallpath.lineTo(xPts.get((i+1)%s), yPts.get((i+1)%s));
	        	//canvas.drawLine(xPts.get(i), yPts.get(i), xPts.get((i+1)%s), yPts.get((i+1)%s), paint);
	        }
	        canvas.drawPath(wallpath, paint);
	    } // end method draw
	  public void setX2(int x2){
		  addXPoint(x2);
	  }
	  public void setY2(int y2){
		  addYPoint(y2);
	  }
	  public int[] convertIntegers(ArrayList<Integer> integers)
	  {
	      int[] ret = new int[integers.size()];
	      for (int i=0; i < ret.length; i++)
	      {
	          ret[i] = integers.get(i).intValue();
	      }
	      return ret;
	  }
	 
	    public Element saveData(Document doc ){
	    	return saveData("MyPolygon", doc, getFilled());
	    }
}
