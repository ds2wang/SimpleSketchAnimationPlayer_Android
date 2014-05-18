package com.example.twoactivitytest;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class MyPen extends MyShape{

	public MyPen( int x1, int y1, int x2, int y2, int thickness, int color, int color2, boolean gradient, boolean dashed, int dlength )
    {
        super( x1, y1, x2, y2, thickness, color, color2, gradient, dashed, dlength );
    } // end MyLine constructor
	  public void draw(  )
	    {
	        //g2.drawLine( getX1(), getY1(), getX2(), getY2() ); //draws the lines according to the coordinates
	    } // end method draw
	    public void draw( Canvas canvas )
	    {
			Paint paint = new Paint();

			paint.setColor(getColor());

			paint.setStrokeWidth(getThickness());

			paint.setStyle(Paint.Style.STROKE);
	        ArrayList<Integer> xPts=getXPts();
	        ArrayList<Integer> yPts=getYPts();
	        for(int i=0;i<xPts.size()-1;i++){
	        	canvas.drawLine(xPts.get(i), yPts.get(i), xPts.get(i+1), yPts.get(i+1), paint);
	        }
	    } // end method draw
	  public void setX2(int x2){
		  addXPoint(x2);
	  }
	  public void setY2(int y2){
		  addYPoint(y2);
	  }
	 
	 
	    public Element saveData(Document doc ){
	    	return saveData("MyPen", doc);
	    }
}
