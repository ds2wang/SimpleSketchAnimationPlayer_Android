package com.example.twoactivitytest;
//allows the user to draw rounded rectangles


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
public class MyRoundRect extends MyBoundedShape
{
	private int lol;
    //default constructor
    public MyRoundRect(){ 
        super(); //calls the super constructor
    }    
    // constructor with input values
    public MyRoundRect( int x1, int y1, int x2, int y2, int thickness, int color, int color2, boolean gradient, boolean filled, boolean dashed, int dlength )
    {
        super( x1, y1, x2, y2, thickness, color, color2, gradient, filled, dashed, dlength );//calls super constructor
    } // end MyRoundRect constructor

    // draws the rounded rectangles
    public void draw( )
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
		RectF r=new RectF();
		r.left=getUpperLeftX();
		r.right=getUpperLeftX()+getWidth();
		r.top=getUpperLeftY();
		r.bottom=getUpperLeftY()+getHeight();
		canvas.drawRoundRect(r, 20, 20, paint);
    } // end method draw
    public Element saveData(Document doc ){
    	return saveData("MyRoundRect", doc, getFilled());
    }
} // end class MyRoundRect