seamstresses
import android.app.FragmentTransaction;ll
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
lDr
import com.google.androidxlzdlt
.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.googllvxllsle.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class MainActivity extends Activity {

    //region globals
	private static final String TAG="MainActivity";

    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG,"@onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,new FragColorPicker(),"fcp").commit();



        Log.v(TAG,"@onCreate-adding ad...");
        final AdView mAdView = (AdView) findViewById(R.id.adMob);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });

    }

	@Override
	protected void onStart()
	{
		Log.v(TAG,"@onStart");
		
		super.onStart ( );
	}
	
	
	/*public void largeButtonClick(View v){
		Log.v(TAG,"@largeButtonClick");
	}*/
	
	

    //region option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            final Dialog d=new Dialog(this);
			d.setContentView(R.layout.dialog_about);
			d.setTitle(getResources().getString(R.string.action_about));
			
			
			
            Button dialogButton=(Button)d.findViewById(R.id.dialogAboutButtonDone);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 d.dismiss();
                }
            });
			d.show();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion
}
