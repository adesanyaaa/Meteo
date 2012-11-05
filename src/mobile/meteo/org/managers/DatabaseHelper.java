package mobile.meteo.org.managers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
 
    public SQLiteDatabase myDataBase;  
    private Context myContext;	
    private String  dbPath = DataManager.DB_PATH + DataManager.DB_NAME;

	public DatabaseHelper(Context context) {
		super(context, DataManager.DB_NAME, null, DataManager.DATABASE_VERSION);	
		myContext = context;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * 
	 * @throws IOException
	 */
	public void createDataBase() throws IOException{
 
    	if(checkDataBase()){
    		//do nothing - database already exist
    	} else {
 
    		//By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
        	this.getWritableDatabase(); //.getReadableDatabase();
 
        	try { 
    			copyDataBase(); 
    		} catch (IOException e) { 
        		throw new Error("Error copying database"); 
        	}
    	}
 
    }
	
	
	/**
	 * 
	 * 
	 * @return
	 */
	private boolean checkDataBase(){
		
		/*
		 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DataManager.DB_PATH + DataManager.DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){ 
    		//database does't exist yet. 
    	}
 
    	if(checkDB != null){ 
    		checkDB.close(); 
    	}
 
    	return checkDB != null ? true : false;
    	*/
		
		File dbFile = new File(DataManager.DB_PATH + DataManager.DB_NAME);
	    return dbFile.exists();
    }
	
	
	/**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     *
     */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DataManager.DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DataManager.DB_PATH + DataManager.DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
    
    /**
     * 
     * @throws SQLException
     */
    public void openDataBase() throws SQLException{
    	 
    	//Open the database
    	myDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);     
    }
    
    
    public synchronized void close() {   
    	
	    if(myDataBase != null)
		    myDataBase.close();
	    
	    super.close();
    }
	
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub		
	}
	
}