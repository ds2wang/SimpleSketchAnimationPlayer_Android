package com.example.twoactivitytest;
// MyLine.java
// Declaration of class MyLine


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
class MyLine extends MyShape
{
    //default constructor
    public MyLine(){ 
        super(); //calls super constructor
    }
    
    // constructor with input values
    public MyLine( int x1, int y1, int x2, int y2, int thickness, int color, int color2, boolean gradient, boolean dashed, int dlength )
    {
        super( x1, y1, x2, y2, thickness, color, color2, gradient, dashed, dlength );
    } // end MyLine constructor
    
    // draws the line
    public void draw(  )
    {

    } // end method draw
    public void draw( Canvas canvas )
    {
		Paint paint = new Paint();
		paint.setColor(getColor());

		paint.setStrokeWidth(getThickness());

		paint.setStyle(Paint.Style.STROKE);
		canvas.drawLine(getX1(),getY1(), getX2(), getY2(), paint);
    } // end method draw
    public Element saveData(Document doc ){
    	return saveData("MyLine", doc);
    }
} // end class MyLine
