package com.example.twoactivitytest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
public class MyActivity extends Activity implements ColorPickerDialog.OnColorChangedListener {
//alpha, red, green, blue
	int myColor=Color.argb(255, 255, 000, 000);
String color = "255,255,000,000";
ImageView image;
public void onCreate(Bundle savedInstanceState) {
	
super.onCreate(savedInstanceState);
setContentView(R.layout.main2);
image = (ImageView) findViewById(R.id.selectColor);
String[] colorVal = color.split(",");
image.setBackgroundColor(1234);

image.setBackgroundColor(Color.argb(Integer.parseInt(colorVal[0]),
Integer.parseInt(colorVal[1]),
Integer.parseInt(colorVal[2]),
Integer.parseInt(colorVal[3])));
Button btn = (Button) findViewById(R.id.select);

btn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
new ColorPickerDialog(MyActivity.this, MyActivity.this, color).show();}});
}
@Override
public void colorChanged(int a, int r, int g, int b) {
color = a + "," + r + "," + g + "," + b;
image.setBackgroundColor(Color.argb(a,r,g,b));
myColor=Color.argb(a,r,g,b);
}
public void finish(View V)
{
	// get the Entered  message
	int finalColor=myColor;
	Intent intentMessage=new Intent();

	// put the message in Intent
	intentMessage.putExtra("MESSAGE", myColor+"");
	// Set The Result in Intent
	setResult(4,intentMessage);
	// finish The activity 
    finish();
}
}