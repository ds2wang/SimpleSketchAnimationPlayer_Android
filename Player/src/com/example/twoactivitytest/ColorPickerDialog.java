package com.example.twoactivitytest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
public class ColorPickerDialog extends Dialog implements SeekBar.OnSeekBarChangeListener, TextWatcher, OnClickListener {
SeekBar redBar;
EditText redText;
SeekBar greenBar;
EditText greenText;
SeekBar blueBar;
EditText blueText;
SeekBar alphaBar;
EditText alphaText;
ImageView colorPreview;
Button ok;
Button cancel;
String color;
public interface OnColorChangedListener {
void colorChanged(int a, int r, int g, int b);
}
private OnColorChangedListener mListener;
public ColorPickerDialog(Context context, OnColorChangedListener listener, String color) {
super(context);
mListener = listener;
this.color = color;
}
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.color_layout);
setTitle("Color Picker");
redBar = (SeekBar) findViewById(R.id.red_bar);
redText = (EditText) findViewById(R.id.red_text);
greenBar = (SeekBar) findViewById(R.id.green_bar);
greenText = (EditText) findViewById(R.id.green_text);
blueBar = (SeekBar) findViewById(R.id.blue_bar);
blueText = (EditText) findViewById(R.id.blue_text);
alphaBar = (SeekBar) findViewById(R.id.alpha_bar);
alphaText = (EditText) findViewById(R.id.alpha_text);
ok = (Button) findViewById(R.id.ok);
cancel = (Button) findViewById(R.id.cancel);
colorPreview = (ImageView) findViewById(R.id.color_preview);
//set initial colors
String[] colorVal = color.split(",");
int a = Integer.parseInt(colorVal[0]);
int r = Integer.parseInt(colorVal[1]);
int g = Integer.parseInt(colorVal[2]);
int b = Integer.parseInt(colorVal[3]);
colorPreview.setBackgroundColor(Color.argb(a, r, g, b));
redBar.setProgress(r);
greenBar.setProgress(g);
blueBar.setProgress(b);
alphaBar.setProgress(a);
redText.setText(colorVal[1]);
greenText.setText(colorVal[2]);
blueText.setText(colorVal[3]);
alphaText.setText(colorVal[0]);
redBar.setOnSeekBarChangeListener(this);
greenBar.setOnSeekBarChangeListener(this);
blueBar.setOnSeekBarChangeListener(this);
alphaBar.setOnSeekBarChangeListener(this);
redText.addTextChangedListener(this);
greenText.addTextChangedListener(this);
blueText.addTextChangedListener(this);
alphaText.addTextChangedListener(this);
ok.setOnClickListener(this);
cancel.setOnClickListener(this);
setCancelable(false);
}
@Override
public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
if(fromUser){
switch (seekBar.getId()) {
case R.id.red_bar:
redText.setText(Integer.toString(progress));
break;
case R.id.green_bar:
greenText.setText(Integer.toString(progress));
break;
case R.id.blue_bar:
blueText.setText(Integer.toString(progress));
break;
case R.id.alpha_bar:
alphaText.setText(Integer.toString(progress));
break;
}
}
colorPreview.setBackgroundColor(Color.argb(alphaBar.getProgress(), redBar.getProgress(), greenBar.getProgress(), blueBar.getProgress()));
}
@Override
public void onStartTrackingTouch(SeekBar seekBar) {
}
@Override
public void onStopTrackingTouch(SeekBar seekBar) {
}
@Override
public void afterTextChanged(Editable arg0) {
if(Integer.parseInt(redText.getText().toString()) > 255)
redText.setText("255");
if(!redText.getText().toString().equals("")){
if(Integer.parseInt(redText.getText().toString()) > 255)
redText.setText("255");
redBar.setProgress(Integer.parseInt(redText.getText().toString()));
} else
redBar.setProgress(0);
if(!greenText.getText().toString().equals("")){
if(Integer.parseInt(greenText.getText().toString()) > 255)
greenText.setText("255");
greenBar.setProgress(Integer.parseInt(greenText.getText().toString()));
} else
greenBar.setProgress(0);
if(!blueText.getText().toString().equals("")){
if(Integer.parseInt(blueText.getText().toString()) > 255)
blueText.setText("255");
blueBar.setProgress(Integer.parseInt(blueText.getText().toString()));
}else
blueBar.setProgress(0);
if(!alphaText.getText().toString().equals("")){
if(Integer.parseInt(alphaText.getText().toString()) > 255)
alphaText.setText("255");
alphaBar.setProgress(Integer.parseInt(alphaText.getText().toString()));
}else
alphaBar.setProgress(0);
}
@Override
public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
}
@Override
public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
}
@Override
public void onClick(View v) {
switch (v.getId()) {
case R.id.ok:
mListener.colorChanged(alphaBar.getProgress(), redBar.getProgress(), greenBar.getProgress(), blueBar.getProgress());
dismiss();
break;
case R.id.cancel:
dismiss();
break;
}
}
}