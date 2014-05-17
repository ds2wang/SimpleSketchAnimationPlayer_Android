package com.example.twoactivitytest;



import java.io.File;
import java.util.ArrayList;

import com.example.twoactivitytest.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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



public class MainActivity extends Activity 
{
	private int fps=60; 
	private int myColor=Color.WHITE;
	private DynamicStack<MyShape> shapeObjects; // the shapes stack
	private DynamicStack<MyShape> shapeObjects2; // the shapes stack for undoed
    TextView  textViewMessage;
    TextView  textViewMessage2;
    TextView  textViewMessage3;
    ImageView bgColor;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//setContentView(new MyView(this));
		// get The reference of The textView 
		shapeObjects = new DynamicStack<MyShape>();// creates new stack
		shapeObjects2 = new DynamicStack<MyShape>();// creates new stack
		textViewMessage=(TextView)findViewById(R.id.textViewMessage);
		textViewMessage2=(TextView)findViewById(R.id.textViewMessage2);
		textViewMessage3=(TextView)findViewById(R.id.textViewMessage3);
		bgColor=(ImageView)findViewById(R.id.bgColor);
	}
	// Method to handle the Click Event on GetMessage Button
	public void getMessage2(View V)
	{
	    // Create The  Intent and Start The Activity to get The message
	    Intent intentGetMessage=new Intent(this,SecondActivity.class);
	    startActivityForResult(intentGetMessage, 2);// Activity is started with requestCode 2
	}
	public void getMessage(View V)
	{
	    // Create The  Intent and Start The Activity to get The message
	    Intent intentGetMessage=new Intent(this,FileSelectActivity.class);
	    startActivityForResult(intentGetMessage, 2);// Activity is started with requestCode 2
	}
	public void getFPS(View V)
	{
	    // Create The  Intent and Start The Activity to get The message
	    Intent intentGetMessage=new Intent(this,SecondActivity.class);
	    startActivityForResult(intentGetMessage, 5);// Activity is started with requestCode 2
	}
	public void chooseColor(View V)
	{
	    // Create The  Intent and Start The Activity to get The message
	    Intent intentGetMessage=new Intent(this, MyActivity.class);
	    startActivityForResult(intentGetMessage, 4);// Activity is started with requestCode 2
	}
	public void goToDraw(String filename)
	{
	    // Create The  Intent and Start The Activity to get The message
	    Intent intentGetMessage=new Intent(this,AndroidDrawActivity.class);
	    intentGetMessage.putExtra("filename", filename);
	    intentGetMessage.putExtra("MyColor", myColor+"");
	    intentGetMessage.putExtra("FPS", fps+"");
	    startActivityForResult(intentGetMessage, 3);// Activity is started with requestCode 2
	}
	
	public void goDraw(String filename){
		goToDraw(filename);
	}
	public void readFile(String filename){

            File file = new File(filename);
            //This is where a real application would open the file.
            textViewMessage2.setText("Opening: " + file.getName() + "." );
        	try {

                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc = docBuilder.parse (file);
                textViewMessage2.setText("here");
                // normalize text representation
                doc.getDocumentElement ().normalize ();
                textViewMessage2.setText ("Root element of the doc is " + 
                     doc.getDocumentElement().getNodeName());
                Element shapeObjectsEl= (Element)doc.getElementsByTagName("shapeObjects").item(0);
                Element shapeObjects2El= (Element)doc.getElementsByTagName("shapeObjects2").item(0);
                Element timerEls= (Element)doc.getElementsByTagName("timerObjects").item(0);
                NodeList listOfMyShapes = shapeObjectsEl.getElementsByTagName("MyShape");
                NodeList listOfMyShapes2 = shapeObjects2El.getElementsByTagName("MyShape");;
                NodeList timerNodes = timerEls.getElementsByTagName("t");
                textViewMessage2.setText("no of timerObs :"+ timerNodes.getLength());
                for(int s=0; s<timerNodes.getLength() ; s++){
                	Node tNode = timerNodes.item(s);
                }
                int totalPersons = listOfMyShapes.getLength();
                textViewMessage2.setText("Total no of Shapes : " + totalPersons);
                for(int s=0; s<listOfMyShapes.getLength() ; s++){


                    Node shapeNode = listOfMyShapes.item(s);
                    if(shapeNode.getNodeType() == Node.ELEMENT_NODE){
                    	Element shapeElement = (Element)shapeNode.getFirstChild();
                    	String type=shapeElement.getTagName();
                    	//textViewMessage2.setText(type+ " index "+ s);

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
                        textViewMessage2.setText(tempN.getLength()+" x1 elements");
                        Element c=(Element)tempN.item(0);
                        textViewMessage2.setText(c.getFirstChild().getNodeValue());
                        
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
                    	textViewMessage2.setText(" starttime set");
                    	curLoadShape.setEndTime(SendTime);
                    	textViewMessage2.setText(" endtime set");
                    	curLoadShape.setPts(SxPts, SyPts);
                    	textViewMessage2.setText(" pts set");
                		curLoadShape.setDisplacement(SdxPts, SdyPts);
                		textViewMessage2.setText(" displacement set");
                		curLoadShape.setLastMoved(SlastMoved);
                		textViewMessage2.setText(" displacement set");
                		shapeObjects.push(curLoadShape);
                		
                    	textViewMessage2.setText(shapeElement.getTagName()+" element"+ s);
                    	
                    	
                    }
                    textViewMessage2.setText("Done");
                    goToDraw(filename);


                }//end of for loop with s var
                for(int s=0; s<listOfMyShapes2.getLength() ; s++){


                    Node shapeNode = listOfMyShapes2.item(s);
                    if(shapeNode.getNodeType() == Node.ELEMENT_NODE){
                    	Element shapeElement = (Element)shapeNode.getFirstChild();
                    	String type=shapeElement.getTagName();
                    	textViewMessage2.setText(type);
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
                        textViewMessage2.setText(tempN.getLength() +"s2");
                        Element c=(Element)tempN.item(0);
                        textViewMessage2.setText(c.getFirstChild().getNodeValue());
                        
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
                    	textViewMessage2.setText(shapeElement.getTagName()+" element"+ s);
                    }
                }


            }catch (SAXParseException err) {
            textViewMessage2.setText ("** Parsing error" + ", line " 
                 + err.getLineNumber () + ", uri " + err.getSystemId ());
            textViewMessage2.setText(" " + err.getMessage ());

            }catch (SAXException e) {
            Exception x = e.getException ();
            ((x == null) ? e : x).printStackTrace ();

            }catch (Throwable t) {
            t.printStackTrace ();
            }
        	

	}
	 public class MyView extends View {

		  	

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

					// TODO Auto-generated constructor stub

				}



				@Override

				protected void onDraw(Canvas canvas) {

					// TODO Auto-generated method stub

					super.onDraw(canvas);



					

					Paint paint = new Paint();

					paint.setColor(Color.WHITE);

					paint.setStrokeWidth(3);

					paint.setStyle(Paint.Style.STROKE);

					Path path = new Path();

					

					path.moveTo(myPath[0].x, myPath[0].y);

					for (int i = 1; i < myPath.length; i++){

						path.lineTo(myPath[i].x, myPath[i].y);

					}

					canvas.drawPath(path, paint);

					

				}



			}
	// Call Back method  to get the Message form other Activity
	@Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data)
	   {
   	      super.onActivityResult(requestCode, resultCode, data);

   	       // check if the request code is same as what is passed  here it is 2
             if(requestCode==2)//
   	         {
            	 if(null!=data)
            	 {	 	
            		 // fetch the message String
            		 String message=data.getStringExtra("MESSAGE"); 
            		 // Set the message string in textView
            		 textViewMessage2.setText("Message from second Activity: " + message);
            		 //readFile(message);
            		 if(message.split(" ").length==1)
            			 goDraw(message);
            	 }
   	         }else if (requestCode==5){
   	        	String message=data.getStringExtra("MESSAGE"); 
       		 // Set the message string in textView
   	        	
   	        	try{
   	        		fps=Integer.parseInt(message);
   	        		textViewMessage.setText("new Frame rate: " + message);
   	        	}catch (Exception pokemon){
   	        		
   	        	}
       		 //readFile(message);
   	         }else if (requestCode==4){
    	        	String message=data.getStringExtra("MESSAGE"); 
    	       		 // Set the message string in textView
    	   	        	
    	   	        	try{
    	   	        		myColor=Integer.parseInt(message);
    	   	        		textViewMessage3.setText("new Color: " + message);
    	   	        		bgColor.setBackgroundColor(myColor);
    	   	        	}catch (Exception pokemon){
    	   	        		
    	   	        	}
    	       		 //readFile(message);
   	         }
     }
}