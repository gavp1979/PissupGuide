package uk.co.parkesfamily.parkes.pissup.guide;

import uk.co.parkesfamily.parkes.pissup.guide.helperclasses.DBOpenHelper;
import uk.co.parkesfamily.parkes.pissup.guide.winelist.WineListActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity 
{

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Copy the database onto the device 
        DBOpenHelper dbOpen = new DBOpenHelper(this, true);
        dbOpen.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void buttonClick(View view)
    {
    	if (view.getId() == R.id.btnViewWines)
    		launchWineList();
    }

	private void launchWineList()
	{
		Intent intWineList = new Intent(this, WineListActivity.class);
		startActivity(intWineList);
	}
}
