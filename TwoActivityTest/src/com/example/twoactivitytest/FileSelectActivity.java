package com.example.twoactivitytest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


import java.io.File;

import java.util.ArrayList;

import java.util.List;

import javax.xml.transform.stream.StreamResult;

import android.app.AlertDialog;

import android.app.ListActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;

import android.widget.ListView;

import android.widget.TextView;



public class FileSelectActivity extends ListActivity {

 

 private List<String> item = null;

 private List<String> path = null;

 private String root="/";

 private TextView myPath;

 

    /** Called when the activity is first created. */

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        myPath = (TextView)findViewById(R.id.path);

        getDir(root);

    }

	public void submitMessage(View V)
	{
		// get the Entered  message
		String message="asdf";
		Intent intentMessage=new Intent();

		// put the message in Intent
		intentMessage.putExtra("MESSAGE",message);
		// Set The Result in Intent
		setResult(2,intentMessage);
		// finish The activity 
        finish();
	}
	public void submitMessage()
	{
		// get the Entered  message
		String message="asdf";
		Intent intentMessage=new Intent();

		// put the message in Intent
		intentMessage.putExtra("MESSAGE",message);
		// Set The Result in Intent
		setResult(2,intentMessage);
		// finish The activity 
        finish();
	}
	public void submitMessage(String s)
	{
		// get the Entered  message
		String message="asdf";
		Intent intentMessage=new Intent();

		// put the message in Intent
		intentMessage.putExtra("MESSAGE",s);
		// Set The Result in Intent
		setResult(2,intentMessage);
		// finish The activity 
        finish();
	}
    private void getDir(String dirPath)

    {

     myPath.setText("Location: " + dirPath);

     

     item = new ArrayList<String>();

     path = new ArrayList<String>();

     

     File f = new File(dirPath);

     File[] files = f.listFiles();

     

     if(!dirPath.equals(root))

     {



      item.add(root);

      path.add(root);

      

      item.add("../");

      path.add(f.getParent());

            

     }

     

     for(int i=0; i < files.length; i++)

     {

       File file = files[i];

       path.add(file.getPath());

       if(file.isDirectory())

        item.add(file.getName() + "/");

       else

        item.add(file.getName());

     }



     ArrayAdapter<String> fileList =

      new ArrayAdapter<String>(this, R.layout.row, item);

     setListAdapter(fileList);

    }



 @Override

 protected void onListItemClick(ListView l, View v, int position, long id) {

  

  File file = new File(path.get(position));

  

  if (file.isDirectory())

  {

   if(file.canRead())

    getDir(path.get(position));

   else

   {

    new AlertDialog.Builder(this)

    //.setIcon(R.drawable.icon)

    .setTitle("[" + file.getName() + "] folder can't be read!")

    .setPositiveButton("OK", 

      new DialogInterface.OnClickListener() {

       

       @Override

       public void onClick(DialogInterface dialog, int which) {

        // TODO Auto-generated method stub

       }

      }).show();

   }

  }

  else

  {

	  
   new AlertDialog.Builder(this)

   // .setIcon(R.drawable.icon)
   
    .setTitle("[" + file.getName() + "]")

    .setPositiveButton("OK", 

      new DialogInterface.OnClickListener() {

       

       @Override

       public void onClick(DialogInterface dialog, int which) {

        // TODO Auto-generated method stub

       }

      }).show();
   String extension = Utils.getExtension(file);
   if (extension != null) {
       if (extension.equals(Utils.xml) ||
           extension.equals(Utils.XML)) {
    	   submitMessage(file.getAbsoluteFile()+"");
       } else {
    	   submitMessage("Invalid file");
       }
   }else{
   }
   

  }

 }

}
