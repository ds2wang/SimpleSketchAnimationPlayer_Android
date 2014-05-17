package com.example.twoactivitytest;
//allows the user to draw ovals


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
class MyOval extends MyBoundedShape
{
    //default constructor
    public MyOval(){ 
        super(); //calls super constructor
    }    
    // constructor with input values
    public MyOval( int x1, int y1, int x2, int y2, int thickness, int color, int color2, boolean gradient, boolean filled, boolean dashed, int dlength )
    {
        super( x1, y1, x2, y2, thickness, color, color2, gradient, filled, dashed, dlength ); //calls super constructor
        
    } //end MyOval constructor
    
    //draws the ovals
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
		RectF r=new RectF();
		r.left=getX1();
		r.right=getX2();
		r.top=getY1();
		r.bottom=getY2();
		canvas.drawOval(r, paint);
		//canvas.drawRect(getUpperLeftX(),getUpperLeftY(), getX2(), getY2(), paint);

    } // end method draw
    public Element saveData(Document doc ){
    	return saveData("MyOval", doc, getFilled());
    }
} // end class MyOval