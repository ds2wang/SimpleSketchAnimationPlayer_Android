package com.example.twoactivitytest;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


import android.os.Bundle;
import android.app.Activity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.ViewGroup;


import android.content.Context;

import android.graphics.Canvas;

import android.graphics.Color;

import android.graphics.Paint;

import android.graphics.Path;
import android.graphics.drawable.Drawable;

import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;



public class AndroidDrawActivity extends Activity {
	MyView curView;
	private int fps=60;
	private int myColor=Color.WHITE;
	private DynamicStack<MyShape> shapeObjects; // the shapes stack
	private DynamicStack<MyShape> shapeObjects2; // the shapes stack for undoed
	private ArrayList<TimerObject> timerObjects = new ArrayList<TimerObject>();
	/** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
	shapeObjects = new DynamicStack<MyShape>();// creates new stack
	shapeObjects2 = new DynamicStack<MyShape>();// creates new stack
      super.onCreate(savedInstanceState);
      curView=new MyView(this);
      Timer t=new Timer();
      t.scheduleAtFixedRate(new UpdateTimer(), new Date(), 1000/60);
      getMessage();
  }
  class UpdateTimer extends TimerTask {
	  public void run() {
          curView.updateTime();
	  }
  }
  class RemindTask extends TimerTask {
      public void run() {
          if(curView.getCurFrame()<=timerObjects.size()){
        	  if(!curView.getStopped()){
	        	  curView.transObjs();
	        	  curView.incFrame();
        	  }
          }else{
        	  try {
                  synchronized (this) {
                      //wait(2000); 
                      wait(2); 
                  }
              } catch (InterruptedException e) {

              }
          }
          curView.postInvalidate();
          
      }
  }
  public void run() {
      System.out.println("working at fixed rate delay");      
   }  

  public DynamicStack<MyShape> getShapeObjects(){
	  return shapeObjects;
  }
  public DynamicStack<MyShape> getShapeObjects2(){
	  return shapeObjects2;
  }
  public void getMessage(){
	  String filename = getIntent().getExtras().getString("filename");
	  curView.setFileName(filename);
	  readFile(filename);
	  curView.invalidate();
	  try{
		  int frameRate=Integer.parseInt(getIntent().getExtras().getString("FPS"));
		  fps=frameRate;
		   TimerTask tasknew = new RemindTask();
		      Timer timer = new Timer();
		      
		      // scheduling the task at fixed rate
		      //timer.scheduleAtFixedRate(tasknew,new Date(),1000/fps);
		      setContentView(curView);
		      timer = new Timer();
		      timer.schedule(new RemindTask(), 1000/fps);
		      timer.scheduleAtFixedRate(tasknew,new Date(),1000/fps);
	  }catch (Exception pokemon){
	      TimerTask tasknew = new RemindTask();
	      Timer timer = new Timer();
	      
	      // scheduling the task at fixed rate
	      //timer.scheduleAtFixedRate(tasknew,new Date(),1000/fps);
	      setContentView(curView);
	      timer = new Timer();
	      timer.schedule(new RemindTask(), 1000/fps);
	      timer.scheduleAtFixedRate(tasknew,new Date(),1000/fps);
	  }
	  try{
		  int curColor=Integer.parseInt(getIntent().getExtras().getString("MyColor"));
		  myColor=curColor;
	  }catch (Exception pokemon){
		  
	  }
	  curView.setMyColor(myColor);
  }
  public void readFile(String filename){

      File file = new File(filename);
      //This is where a real application would open the file.
      curView.setFileName("Opening: " + file.getName() + "." );//
  	try {

          DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
          Document doc = docBuilder.parse (file);
          curView.setFileName("here");
          // normalize text representation
          doc.getDocumentElement ().normalize ();
          curView.setFileName ("Root element of the doc is " + 
               doc.getDocumentElement().getNodeName());
          Element shapeObjectsEl= (Element)doc.getElementsByTagName("shapeObjects").item(0);
          Element shapeObjects2El= (Element)doc.getElementsByTagName("shapeObjects2").item(0);
          Element timerEls= (Element)doc.getElementsByTagName("timerObjects").item(0);
          NodeList listOfMyShapes = shapeObjectsEl.getElementsByTagName("MyShape");
          NodeList listOfMyShapes2 = shapeObjects2El.getElementsByTagName("MyShape");;
          NodeList timerNodes = timerEls.getElementsByTagName("t");
          curView.setFileName("no of timerObs :"+ timerNodes.getLength());
          for(int s=0; s<timerNodes.getLength() ; s++){
          	Node tNode = timerNodes.item(s);
          	timerObjects.add(new TimerObject(0,0));
          }//
          int totalPersons = listOfMyShapes.getLength();
          curView.setFileName("Total no of Shapes : " + totalPersons);
          for(int s=0; s<listOfMyShapes.getLength() ; s++){


              Node shapeNode = listOfMyShapes.item(s);
              if(shapeNode.getNodeType() == Node.ELEMENT_NODE){
              	Element shapeElement = (Element)shapeNode.getFirstChild();
              	String type=shapeElement.getTagName();
              	//curView.setFileName(type+ " index "+ s);

                  int Sx1; // x coordinate of first endpoint
                  int Sy1; // y coordinate of first endpoint
                  int Sx2; // x coordinate of second endpoint
                  int Sy2; // y coordinate of second endpoint
                  int Sthickness;  // thickness of the line
                  boolean Sdashed; //if line is dashed
                  boolean Sgradient; //whether or not to draw with gradient
                  int SmyColor; // color of this shape
                  int SmyColor2; // color of this shape
                  int Sdlength; //dash length
                  
                  ArrayList<Integer> SxPts=new ArrayList<Integer>();
                  ArrayList<Integer> SyPts=new ArrayList<Integer>();
                  boolean SisFinal;
                  int SstartTime;
                  int SendTime;
                  ArrayList<Integer> SdxPts=new ArrayList<Integer>();
                  ArrayList<Integer> SdyPts=new ArrayList<Integer>();
                  int SlastMoved;
                  
                  NodeList tempN=shapeElement.getElementsByTagName("x1");
                  curView.setFileName(tempN.getLength()+" x1 elements");
                  Element c=(Element)tempN.item(0);
                  curView.setFileName(c.getFirstChild().getNodeValue());
                  
          		Sx1=Integer.parseInt(((Element)shapeElement.getElementsByTagName("x1").item(0)).getFirstChild().getNodeValue());
          		Sx2=Integer.parseInt(((Element)shapeElement.getElementsByTagName("x2").item(0)).getFirstChild().getNodeValue());
          		Sy1=Integer.parseInt(((Element)shapeElement.getElementsByTagName("y1").item(0)).getFirstChild().getNodeValue());
          		Sy2=Integer.parseInt(((Element)shapeElement.getElementsByTagName("y2").item(0)).getFirstChild().getNodeValue());
          		Sthickness=Integer.parseInt(((Element)shapeElement.getElementsByTagName("thickness").item(0)).getFirstChild().getNodeValue());
          		Sdashed=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("dashed").item(0)).getFirstChild().getNodeValue());
          		Sgradient=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("gradient").item(0)).getFirstChild().getNodeValue());
          		SmyColor=Integer.parseInt(((Element)shapeElement.getElementsByTagName("myColor").item(0)).getFirstChild().getNodeValue());
          		SmyColor2=Integer.parseInt(((Element)shapeElement.getElementsByTagName("myColor2").item(0)).getFirstChild().getNodeValue());
          		Sdlength=Integer.parseInt(((Element)shapeElement.getElementsByTagName("dlength").item(0)).getFirstChild().getNodeValue());
          		SisFinal=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("isFinal").item(0)).getFirstChild().getNodeValue());
          		SstartTime=Integer.parseInt(((Element)shapeElement.getElementsByTagName("startTime").item(0)).getFirstChild().getNodeValue());
          		SendTime=Integer.parseInt(((Element)shapeElement.getElementsByTagName("endTime").item(0)).getFirstChild().getNodeValue());
          		SlastMoved=Integer.parseInt(((Element)shapeElement.getElementsByTagName("lastMoved").item(0)).getFirstChild().getNodeValue());
          		NodeList xPtsNodes=shapeElement.getElementsByTagName("x");
          		NodeList yPtsNodes=shapeElement.getElementsByTagName("y");
          		NodeList dxPtsNodes=shapeElement.getElementsByTagName("dx");
          		NodeList dyPtsNodes=shapeElement.getElementsByTagName("dy");
          		
          		for(int i=0;i<xPtsNodes.getLength();i++){
          			SxPts.add(Integer.parseInt(((Element)xPtsNodes.item(i)).getFirstChild().getNodeValue()));
          			SyPts.add(Integer.parseInt(((Element)yPtsNodes.item(i)).getFirstChild().getNodeValue()));
          		}
          		for(int i=0;i<dxPtsNodes.getLength();i++){
          			SdxPts.add(Integer.parseInt(((Element)dxPtsNodes.item(i)).getFirstChild().getNodeValue()));
          			SdyPts.add(Integer.parseInt(((Element)dyPtsNodes.item(i)).getFirstChild().getNodeValue()));
          		}
                  MyShape curLoadShape;
              	if(type.equals("MyPen")){
              		curLoadShape= new MyPen(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sdashed, Sdlength);
              		
              	}else if(type.equals("MyLine")){
              		curLoadShape= new MyLine(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sdashed, Sdlength);
              		
              	}else if(type.equals("MyOval")){
              		boolean Sfilled=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("filled").item(0)).getFirstChild().getNodeValue());
              		curLoadShape = new MyOval(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sfilled, 
								Sdashed, Sdlength);
              	}else if(type.equals("MyRectangle")){
              		boolean Sfilled=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("filled").item(0)).getFirstChild().getNodeValue());                    		
              		curLoadShape = new MyRectangle(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sfilled, 
								Sdashed, Sdlength);
              		
              	}else if(type.equals("MyRoundRect")){
              		boolean Sfilled=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("filled").item(0)).getFirstChild().getNodeValue());                    		
              		curLoadShape = new MyRoundRect(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sfilled, 
								Sdashed, Sdlength);
              	}else{ 
              		boolean Sfilled=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("filled").item(0)).getFirstChild().getNodeValue());                    		
              		curLoadShape = new MyPolygon(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sfilled, 
								Sdashed, Sdlength);
              	}
              	if(SisFinal)
              		curLoadShape.setFinal();
              	curLoadShape.setStartTime(SstartTime);
              	curView.setFileName(" starttime set");
              	curLoadShape.setEndTime(SendTime);
              	curView.setFileName(" endtime set");
              	curLoadShape.setPts(SxPts, SyPts);
              	curView.setFileName(" pts set");
          		curLoadShape.setDisplacement(SdxPts, SdyPts);
          		curView.setFileName(" displacement set");
          		curLoadShape.setLastMoved(SlastMoved);
          		curView.setFileName(" displacement set");
          		shapeObjects.push(curLoadShape);
          		
              	curView.setFileName(shapeElement.getTagName()+" element"+ s);
              	
              	
              }
              curView.setFileName("Done");


          }//end of for loop with s var
          for(int s=0; s<listOfMyShapes2.getLength() ; s++){


              Node shapeNode = listOfMyShapes2.item(s);
              if(shapeNode.getNodeType() == Node.ELEMENT_NODE){
              	Element shapeElement = (Element)shapeNode.getFirstChild();
              	String type=shapeElement.getTagName();
              	curView.setFileName(type);
                  int Sx1; // x coordinate of first endpoint
                  int Sy1; // y coordinate of first endpoint
                  int Sx2; // x coordinate of second endpoint
                  int Sy2; // y coordinate of second endpoint
                  int Sthickness;  // thickness of the line
                  boolean Sdashed; //if line is dashed
                  boolean Sgradient; //whether or not to draw with gradient
                  int SmyColor; // color of this shape
                  int SmyColor2; // color of this shape
                  int Sdlength; //dash length
                  ArrayList<Integer> SxPts=new ArrayList<Integer>();
                  ArrayList<Integer> SyPts=new ArrayList<Integer>();
                  boolean SisFinal;
                  int SstartTime;
                  int SendTime;
                  ArrayList<Integer> SdxPts=new ArrayList<Integer>();
                  ArrayList<Integer> SdyPts=new ArrayList<Integer>();
                  int SlastMoved;
                  NodeList tempN=shapeElement.getElementsByTagName("x1");
                  curView.setFileName(tempN.getLength() +"s2");
                  Element c=(Element)tempN.item(0);
                  curView.setFileName(c.getFirstChild().getNodeValue());
                  
          		Sx1=Integer.parseInt(((Element)shapeElement.getElementsByTagName("x1").item(0)).getFirstChild().getNodeValue());
          		Sx2=Integer.parseInt(((Element)shapeElement.getElementsByTagName("x2").item(0)).getFirstChild().getNodeValue());
          		Sy1=Integer.parseInt(((Element)shapeElement.getElementsByTagName("y1").item(0)).getFirstChild().getNodeValue());
          		Sy2=Integer.parseInt(((Element)shapeElement.getElementsByTagName("y2").item(0)).getFirstChild().getNodeValue());
          		Sthickness=Integer.parseInt(((Element)shapeElement.getElementsByTagName("thickness").item(0)).getFirstChild().getNodeValue());
          		Sdashed=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("dashed").item(0)).getFirstChild().getNodeValue());
          		Sgradient=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("gradient").item(0)).getFirstChild().getNodeValue());
          		SmyColor=Integer.parseInt(((Element)shapeElement.getElementsByTagName("myColor").item(0)).getFirstChild().getNodeValue());
          		SmyColor2=Integer.parseInt(((Element)shapeElement.getElementsByTagName("myColor2").item(0)).getFirstChild().getNodeValue());
          		Sdlength=Integer.parseInt(((Element)shapeElement.getElementsByTagName("dlength").item(0)).getFirstChild().getNodeValue());
          		SisFinal=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("isFinal").item(0)).getFirstChild().getNodeValue());
          		SstartTime=Integer.parseInt(((Element)shapeElement.getElementsByTagName("startTime").item(0)).getFirstChild().getNodeValue());
          		SendTime=Integer.parseInt(((Element)shapeElement.getElementsByTagName("endTime").item(0)).getFirstChild().getNodeValue());
          		SlastMoved=Integer.parseInt(((Element)shapeElement.getElementsByTagName("lastMoved").item(0)).getFirstChild().getNodeValue());
          		NodeList xPtsNodes=shapeElement.getElementsByTagName("x");
          		NodeList yPtsNodes=shapeElement.getElementsByTagName("y");
          		NodeList dxPtsNodes=shapeElement.getElementsByTagName("dx");
          		NodeList dyPtsNodes=shapeElement.getElementsByTagName("dy");
          		for(int i=0;i<xPtsNodes.getLength();i++){
          			SxPts.add(Integer.parseInt(((Element)xPtsNodes.item(i)).getFirstChild().getNodeValue()));
          			SyPts.add(Integer.parseInt(((Element)yPtsNodes.item(i)).getFirstChild().getNodeValue()));
          		}
          		for(int i=0;i<dxPtsNodes.getLength();i++){
          			SdxPts.add(Integer.parseInt(((Element)dxPtsNodes.item(i)).getFirstChild().getNodeValue()));
          			SdyPts.add(Integer.parseInt(((Element)dyPtsNodes.item(i)).getFirstChild().getNodeValue()));
          		}
                  MyShape curLoadShape;
              	if(type.equals("MyPen")){
              		curLoadShape= new MyPen(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sdashed, Sdlength);
              		
              	}else if(type.equals("MyLine")){
              		curLoadShape= new MyLine(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sdashed, Sdlength);
              		
              	}else if(type.equals("MyOval")){
              		boolean Sfilled=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("filled").item(0)).getFirstChild().getNodeValue());
              		curLoadShape = new MyOval(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sfilled, 
								Sdashed, Sdlength);
              	}else if(type.equals("MyRectangle")){
              		boolean Sfilled=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("filled").item(0)).getFirstChild().getNodeValue());                    		curLoadShape = new MyRectangle(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sfilled, 
								Sdashed, Sdlength);
              		
              	}else if(type.equals("MyRoundRect")){
              		boolean Sfilled=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("filled").item(0)).getFirstChild().getNodeValue());                    		curLoadShape = new MyRoundRect(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sfilled, 
								Sdashed, Sdlength);
              	}else{ 
              		boolean Sfilled=Boolean.parseBoolean(((Element)shapeElement.getElementsByTagName("filled").item(0)).getFirstChild().getNodeValue());                    		curLoadShape = new MyPolygon(Sx1, Sy1, Sx2, Sy2,
								Sthickness, SmyColor,
								SmyColor2, Sgradient,
								Sfilled, 
								Sdashed, Sdlength);
              	}
              	if(SisFinal)
              		curLoadShape.setFinal();
              	curLoadShape.setStartTime(SstartTime);
              	curLoadShape.setEndTime(SendTime);
              	curLoadShape.setPts(SxPts, SyPts);
          		curLoadShape.setDisplacement(SdxPts, SdyPts);
          		curLoadShape.setLastMoved(SlastMoved);
          		shapeObjects.push(curLoadShape);
              	curView.setFileName(shapeElement.getTagName()+" element"+ s);
              }
          }


      }catch (SAXParseException err) {
      curView.setFileName ("** Parsing error" + ", line " 
           + err.getLineNumber () + ", uri " + err.getSystemId ());
      curView.setFileName(" " + err.getMessage ());

      }catch (SAXException e) {
      Exception x = e.getException ();
      ((x == null) ? e : x).printStackTrace ();

      }catch (Throwable t) {
      t.printStackTrace ();
      }
  	curView.setShapeObjects(shapeObjects);
  	curView.setShapeObjects2(shapeObjects2);
  	curView.setTimerObjs(timerObjects);
  	curView.invalidate();
}

	class TimerObject {
		int dx;
		int dy;

		public TimerObject(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

	}
  public class MyView extends View {
	  public int cMyColor=Color.WHITE;
	  String filename;
	  public boolean stopped=false;
	private DynamicStack<MyShape> CshapeObjects; // the shapes stack
	private DynamicStack<MyShape> CshapeObjects2; // the shapes stack for undoed
	private ArrayList<TimerObject> CtimerObjects = new ArrayList<TimerObject>();
	private int lastTouch=0;
	private int timerVal=0;
	private int curFrame=0;
	public boolean doubleClick=false;
  	class Pt{

  		float x, y;

  		

  		Pt(float _x, float _y){

  			x = _x;

  			y = _y;

  		}

  	}

  	

  	Pt[] myPath = { new Pt(100, 100),

  					new Pt(200, 200),

  					new Pt(200, 500),

  					new Pt(400, 500),

  					new Pt(400, 200)

  					};

  	

		public MyView(Context context) {

			super(context);
			setFocusable(true); 
			// TODO Auto-generated constructor stub

		}
		public void incFrame(){
			curFrame++;
		}
		public void reset(){
			
		}
		public boolean getStopped(){
			return stopped;
		}
		public void transObjsNoob(){
			MyShape cur;
			for (int j = 0; j < shapeObjects.getSize(); j++) {
				cur = shapeObjects.get(j);
				int dx = 1;
				int dy = 1;
				if (dx != 0 || dy != 0) {
					// System.out.println("dx: "+dx+"  dy: "+ dy
					// +"  timer:"+i);
					cur.trans(dx, dy);
				}
			}
		}
		public void transObjs(){
			MyShape cur;
			for (int j = 0; j < shapeObjects.getSize(); j++) {
				cur = shapeObjects.get(j);
				int dx = cur.getDX(curFrame);
				int dy = cur.getDY(curFrame);
				if (dx != 0 || dy != 0) {
					// System.out.println("dx: "+dx+"  dy: "+ dy
					// +"  timer:"+i);
					cur.trans(dx, dy);
				}
			}
		}
		public void setPlayPause(){
			stopped=!stopped;
		}
		public void updateTime(){
			timerVal++;
		}
		public void setMyColor(int newColor){
			cMyColor=newColor;
		}
		public void setTimerObjs(ArrayList<TimerObject> to){
			CtimerObjects=to;
		}
		public void setCurFrame(int cf){
			curFrame=cf;
		}
		public void setFileName(String filename){
			this.filename=filename;
		}
		public void setShapeObjects(DynamicStack<MyShape> SO){
			CshapeObjects=SO;
		}
		public void setShapeObjects2(DynamicStack<MyShape> SO2){
			CshapeObjects2=SO2;
		}
		  public int getCurFrame(){
			  return curFrame;
		  }
		  public void resetDraw(){
				for (int i = curFrame - 1; i >= 0; i--) {
					MyShape cur;
					for (int j = 0; j < CshapeObjects.getSize(); j++) {
						cur = CshapeObjects.get(j);
						int dx = cur.getDX(i);
						int dy = cur.getDY(i);
						if (dx != 0 || dy != 0) {
							// System.out.println("dx: "+dx+"  dy: "+ dy
							// +"  timer:"+i);
							cur.trans(-dx, -dy);
						}
					}
				}
				curFrame=0;
				//stopped=true;
		  }

		  @Override
		  public boolean onTouchEvent(MotionEvent event){
		      //return true;
			  
			   switch(event.getAction()){
		      	case MotionEvent.ACTION_MOVE:
		      		stopped=!stopped;
		      		return true;
		      	case MotionEvent.ACTION_DOWN:
		      		if(timerVal-lastTouch<20){
	      				if(curFrame>timerObjects.size()){
	      					resetDraw();
	      				}else{
	      					finish();
	      				}
		      		}else{
		      			doubleClick=false;
		      		}
		      		lastTouch=timerVal;
		      		stopped=!stopped;
		      		break;
	      		default:
		      		break;
		      			
		      }
		      return super.onTouchEvent(event);
		  }
		@Override

		protected void onDraw(Canvas canvas) {

			// TODO Auto-generated method stub

			super.onDraw(canvas);


			canvas.drawColor(cMyColor);

			Paint paint = new Paint();

			paint.setColor(Color.WHITE);

			paint.setStrokeWidth(3);

			paint.setStyle(Paint.Style.STROKE);

			Path path = new Path();

			

			path.moveTo(myPath[0].x, myPath[0].y);

			for (int i = 1; i < myPath.length; i++){

				path.lineTo(myPath[i].x, myPath[i].y);

			}

			//canvas.drawPath(path, paint);
			paint.setStrokeWidth(1);
			//canvas.drawText(filename, 123, 123, paint);

			MyShape drawShape; // MyShape variable for temporary storage of the
			// MyShape items in the stack

// calling individual draw methods of each MyShape subclass to draw the
// individual shapes from the stack
			
			//canvas.drawText(CshapeObjects.getSize()+"", 123, 123, paint);
			
			if (CshapeObjects.getSize() != 0) {
			// goes through the stack in reverse order, drawing the shape each
			// time
				for (int i = CshapeObjects.getSize() - 1; i >= 0; i--) {
					drawShape = CshapeObjects.get(i);
					if (drawShape.getStartTime() <= curFrame
							&& drawShape.getEndTime() > curFrame) {
						drawShape.draw(canvas);
					}
					
				}
			}

		}



	}

}