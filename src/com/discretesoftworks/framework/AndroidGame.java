package com.discretesoftworks.framework;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.discretesoftworks.TestGame.R;



public abstract class AndroidGame extends Activity{

    private GLSurfaceView mGLView;
    private GameController controller;
    
    public static AndroidGame s_instance;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i("Android","Begin");
        
        s_instance = this;
        
        // Set fullscreen with no title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Create our GLSurface
        //mGLView = new MyGLSurfaceView(this,this);
        setContentView(R.layout.game);
    }

    public void getInput(final TextHolder td, final String promptA, final String promptB){
    	
    	final Activity activity = this;
    	final EditText input = new EditText(this);
    	
    	this.runOnUiThread(new Runnable() {
    		public void run() {
		    	new AlertDialog.Builder(activity)
			    .setTitle(promptA)
			    .setMessage(promptB)
			    .setView(input)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	td.setText(input.getText().toString());
			        }
			    })
			    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			    	public void onClick(DialogInterface dialog, int which) {
			    		td.setText(getString(R.string.emptyString));
			    	}
			    }
			    ).show();
		    }
		}
    	);
    }
    
    @Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	public GameController getController(){
		return controller;
	}
	
	public GLSurfaceView getMyGLSurfaceView(){
		return mGLView;
	}
	
	public void setController(GameController controller){
		GameRenderer.s_instance.clear();
		this.controller = null;
		this.controller = controller;
		System.gc(); // Do a garbage clean when we change controllers
	}
}