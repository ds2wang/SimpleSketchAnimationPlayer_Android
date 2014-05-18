package com.example.twoactivitytest;
//allows the user to draw rectangles


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
public class MyRectangle extends MyBoundedShape
{
	int isEraser=0;
    //default constructor
    public MyRectangle(){ 
        super(); //calls the super constructor
    }    
    // constructor with input values
    public MyRectangle( int x1, int y1, int x2, int y2, int thickness, int color, int color2, boolean gradient, boolean filled, boolean dashed, int dlength )
    {
        super( x1, y1, x2, y2, thickness, color, color2, gradient, filled, dashed, dlength );//calls super constructor
    }// end MyRectangle constructor
    public MyRectangle( int x1, int y1, int x2, int y2, int thickness, int color, int color2, boolean gradient, boolean filled, boolean dashed, int dlength, int isEraser )
    {
        super( x1, y1, x2, y2, thickness, color, color2, gradient, filled, dashed, dlength );//calls super constructor
        this.isEraser=isEraser;
    }// end MyRectangle constructor

    // draws the rectangles
    public void draw(  )
    {

    } // end method draw
    public void draw( Canvas canvas )
    {
		Paint paint = new Paint();

		paint.setColor(getColor());

		paint.setStrokeWidth(getThickness());
		if(!getFilled()){
			paint.setStyle(Paint.Style.STROKE);
		}
		
		canvas.drawRect(getUpperLeftX(),getUpperLeftY(), getUpperLeftX()+getWidth(),getUpperLeftY()+getHeight(), paint);
    } // end method draw
  
   
	    public Element saveData(Document doc ){
	    	return saveData("MyRectangle", doc, getFilled());
	    }
} // end class MyRectangle