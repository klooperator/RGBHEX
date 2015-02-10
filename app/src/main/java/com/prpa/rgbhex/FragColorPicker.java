package com.prpa.rgbhex;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class FragColorPicker extends Fragment
{
    //region globals
    private static final String TAG="fragColorPicker";
	private static final String CHANGE_TO_BLACK="BLACK BACKGROUND";
	private static final String CHANGE_TO_WHITE="WHITE BACKGROUND";
	private static final String BUTTON_PASTE="CLICK TO PASTE \nFROM CLIPBOARD";
	private static final String COPY_TO="\nTO COPY CLIPBOARD";
    private static final String BUTTON_COPY="CLICK TO COPY";

	
	private String colorInHex;
	private String colorInInt;
	
	private CheckBox cbAlpha;
	
   private SeekBar sbRed;
   private SeekBar sbGreen;
   private SeekBar sbBlue;
   private SeekBar sbAlpha;

    private EditText tvRed;
	private EditText tvGreen;
	private EditText tvBlue;
	private EditText tvAlpha;
	private TextView tvColorTxt;
	private TextView tvAlphaPercent;
	
	private Button buttonBackgroundChange;
    private Button largeButton;
	
	private RelativeLayout masterLayout;
	
	private enum PasteOption{hex,hashhex,rgb,argb,rgba}
	private PasteOption pasteOption;
	private String pasteColor;
	
	
    //endregion

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.v(TAG,"[fcp]...onCreateView");
		View view = inflater.inflate(R.layout.frag_color_pick,container, false);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        return view;
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
		Log.v(TAG,"[fcp]...onViewCreated");
        //debug
		
		tvRed=(EditText)getActivity().findViewById(R.id.tvRed);
		tvGreen=(EditText)getActivity().findViewById(R.id.tvGreen);
		tvBlue=(EditText)getActivity().findViewById(R.id.tvBlue);
		tvAlpha=(EditText)getActivity().findViewById(R.id.tvAlpha);
		tvColorTxt=(TextView)getActivity().findViewById(R.id.tvColorTxt);
		tvAlphaPercent=(TextView)getActivity().findViewById(R.id.tvAlphaPercent);
		
		largeButton=(Button)getActivity().findViewById(R.id.largeButton);
		cbAlpha=(CheckBox)getActivity().findViewById(R.id.checkBoxAlpha);
		
	//	Log.d(TAG,"(fcp).tvRed text="+tvRed.getText());
		//tvRed.setText("init");
		//Log.d(TAG,"(fcp).tvRed text2="+tvRed.getText());


		sbRed=(SeekBar)getActivity().findViewById(R.id.seekBarRED);
		sbGreen=(SeekBar)getActivity().findViewById(R.id.seekBarGreen);
		sbBlue=(SeekBar)getActivity().findViewById(R.id.seekBarBlue);
		sbAlpha=(SeekBar)getActivity().findViewById(R.id.seekBarAlpha);
		sbAlpha.setProgress(255);
		
		tvRed.setText(""+sbRed.getProgress());
		tvGreen.setText(""+sbGreen.getProgress());
		tvBlue.setText(""+sbBlue.getProgress());
		tvAlpha.setText(""+sbAlpha.getProgress());

        //debug
        //largeButton.setText("Click to paste color");
		largeButton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				Log.v(TAG,"[fcp]...largeButton...onClick");
				if(largeButton.getText().toString().contains(BUTTON_COPY)){
					Log.d(TAG,"[fcp](largeButton.onClick) is COPY");
					String colorForm=null;
					
					//colorForm=colorInHex;
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("color", pasteColor);
                    clipboard.setPrimaryClip(clip);
					
					switch(pasteOption){
						case hex:
							if(cbAlpha.isChecked())
							colorForm="#"+colorInHex;
							else colorForm="#"+colorInHex.substring(2);
							pasteOption=PasteOption.hashhex;
							break;
						case hashhex:
							//colorForm="#"+colorInHex;
							if(!cbAlpha.isChecked()){
								colorForm="rgb("+tvRed.getText().toString()+","+tvGreen.getText().toString()+","+tvBlue.getText().toString()+")";
								pasteOption=PasteOption.rgb;	
							}else{
								colorForm="argb("+tvAlpha.getText().toString()+","+tvRed.getText().toString()+","+tvGreen.getText().toString()+","+tvBlue.getText().toString()+")";
								pasteOption=PasteOption.argb;
							}
							break;
						case rgb:
							
							if(cbAlpha.isChecked()){
								colorForm="argb("+tvAlpha.getText().toString()+","+tvRed.getText().toString()+","+tvGreen.getText().toString()+","+tvBlue.getText().toString()+")";
								pasteOption=PasteOption.argb;
								}
							else {
								resetPaste();
								colorForm=null;
								}
							break;
						case argb:
							DecimalFormat df=new DecimalFormat("#.##");
							colorForm="rgba("+tvRed.getText().toString()+","+tvGreen.getText().toString()+","+tvBlue.getText().toString()+","+df.format((float)sbAlpha.getProgress()/255f)+")";
							pasteOption=PasteOption.rgba;
							break;
						case rgba:
							//colorForm=colorInHex;
							//pasteOption=PasteOption.hex;
							colorForm=null;
							resetPaste();
							break;
								
					}
					/*ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("color", colorForm);
                    clipboard.setPrimaryClip(clip);*/

					if(colorForm!=null)pasteColor=colorForm;
					updateLargeButtonOverlay();
					
					
				}else if(largeButton.getText().toString().contains(getResources().getText(R.string.paste))){
					Log.d(TAG,"[fcp](largeButton.onClick) is PASTE");
                    //#ff00ff00
                    //#ff00ff
                    //rgb(255,255,255)
                    //argb(255,255,255,255)
                    //rgba(255,255,255,0.3)

                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    String pasteText = null;
                    if(clipboard.hasPrimaryClip()== true){
                        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                        pasteText = item.getText().toString();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.paste_nothing_there), Toast.LENGTH_SHORT).show();
                    }
                    if(pasteText!=null){
                        ColorValidator cv=new ColorValidator(getActivity());
                        int newColor=cv.getColor(pasteText);
                        sbAlpha.setProgress(Color.alpha(newColor));
						if(Color.alpha(newColor)!=255)cbAlpha.setChecked(true);
                        sbRed.setProgress(Color.red(newColor));
                        sbGreen.setProgress(Color.green(newColor));
                        sbBlue.setProgress(Color.blue(newColor));
                    }

				}else Log.e(TAG,"[fcp](largeButton.onClick) is ERROR");
			}
			
		});

		
		masterLayout=(RelativeLayout)getActivity().findViewById(R.id.masterLayoutFcp);
		
		buttonBackgroundChange=(Button)getActivity().findViewById(R.id.ButtonBackgroundColorChange);

		buttonBackgroundChange.setText(CHANGE_TO_WHITE);
		changeBackgroundState();
		buttonBackgroundChange.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				changeBackgroundState();
			}
		});


        //region edittext focuslost listeners
        tvRed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v.getId() == R.id.tvRed && !hasFocus) {

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                }
        });
        //endregion

        //region edittext textchange listeners

        tvRed.addTextChangedListener(new TextWatcher() {
            private int current;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				try{
					Log.v(TAG,"[fcp].(txtWatch).(before)");
					Log.d(TAG,"tvRed current text="+tvRed.getText().toString());
					Log.d(TAG,"s charSeq="+s);
                current=Integer.parseInt(s.toString());
				}catch(NumberFormatException e){
					Log.w(TAG,"char s="+s);
				}
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.v(TAG,"[fcp].(txtWatch).(inTxtChange)");
            }
            @Override
            public void afterTextChanged(Editable s) {
				Log.v(TAG,"[fcp].(txtWatch).(after)");
                int temp;
                try{
					
                    temp=Integer.parseInt(s.toString());
                    
                }catch (NumberFormatException e){
                    //Log.e(TAG,e.toString()+", current="+current);
					temp=current;
                    tvRed.setText(""+current);
					
                }
				if(temp>255)tvRed.setText(""+current);
				else{
					sbRed.setProgress(temp);
				}
            }
        });
        tvGreen.addTextChangedListener(new TextWatcher() {
            private int current;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                current = Integer.parseInt(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int temp;
                try {
                    temp = Integer.parseInt(s.toString());
                    if (temp > 255) tvGreen.setText("" + current);
                    else {
                        sbGreen.setProgress(temp);
                    }
                } catch (NumberFormatException e) {
                    Log.e(TAG, e.toString());
                    tvRed.setText("" + current);
                }
            }
        });
        tvBlue.addTextChangedListener(new TextWatcher() {
            private int current;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                current=Integer.parseInt(s.toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int temp;
                try{
                    temp=Integer.parseInt(s.toString());
                    if(temp>255)tvBlue.setText(""+current);
                    else{
                        sbBlue.setProgress(temp);
                    }
                }catch (NumberFormatException e){
                    Log.e(TAG,e.toString());
                    tvRed.setText(""+current);
                }
            }
        });
        tvAlpha.addTextChangedListener(new TextWatcher() {
            private int current;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                current=Integer.parseInt(s.toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int temp;
                try{
                    temp=Integer.parseInt(s.toString());
                    if(temp>255)tvAlpha.setText(""+current);
                    else{
                        sbAlpha.setProgress(temp);
                    }
                }catch (NumberFormatException e){
                    Log.e(TAG,e.toString());
                    tvRed.setText(""+current);
                }
            }
        });

        //endregion


        //region seekbar listeners
		sbRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					//Log.v(TAG, "[fcp].(seekRED) onProgressChanged");

					//Log.d(TAG,"id="+seekBar.getId()+"\nprogres="+progress+"\nprogress from sb="+seekBar.getProgress());

					tvRed.setText(""+progress);
					//updateColorTxt();
					//updateLargeButtonOverlay();
					updateAll();

				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {

				}
			});

		sbGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					tvGreen.setText(""+progress);
					//updateColorTxt();
					//updateLargeButtonOverlay();
					updateAll();
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {

				}
			});

		sbBlue.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					tvBlue.setText(""+progress);
					//updateColorTxt();
					//updateLargeButtonOverlay();
					updateAll();
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {

				}
			});
			
			sbAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					tvAlpha.setText(""+progress);
					Log.d(TAG,"alpha progress="+progress);
				//	updateColorTxt();
				//	updateLargeButtonOverlay();
				updateAll();
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {

				}
			});
			
			cbAlpha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				
				@Override
				public void onCheckedChanged(CompoundButton cb, boolean b){
					updateAll();
				}
				
			});
		//endregion
		updateAll();
    }

	@Override
	public void onAttach(Activity activity)
	{

		Log.v(TAG, "[fcp]...onAttach");
	
		super.onAttach(activity);
	}

	@Override
	public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState)
	{
		Log.v(TAG,"[fcp]...onInflate");
		super.onInflate ( activity, attrs, savedInstanceState );
	}

	@Override
	public void onStart()
	{
		Log.v(TAG,"[fcp]...onStart");
		
		
		
		super.onStart();
	}
	
	
	public void updateAll(){
		
		hexColor();
		updateColorTxt();
		resetPaste();
		updateLargeButtonOverlay();
		updateAlphaPercent();
		
	}
	
	
	public void hexColor(){
		Log.v(TAG,"[fcp]...hexColor");
		int r=Integer.parseInt(tvRed.getText().toString());
		int g=Integer.parseInt(tvGreen.getText().toString());
		int b=Integer.parseInt(tvBlue.getText().toString());
		if(cbAlpha.isChecked()){
		int a=Integer.parseInt(tvAlpha.getText().toString());
		colorInHex=Integer.toHexString(Color.argb(a,r,g,b));
		}
		else {
			colorInHex=Integer.toHexString(Color.rgb(r,g,b));
		//	colorInHex=colorInHex.substring(2);
		}
		
		while(colorInHex.length()!=8){
			colorInHex="0"+colorInHex;
		}
		//if(colorInHex.length() !=8)
		
		//return colorInHex;
	}
	
	public void updateColorTxt(){
		//Log.v(TAG,"[fcp]...updateColorTxt");
		if(colorInHex==null)hexColor();
		if(cbAlpha.isChecked())
		tvColorTxt.setText("#"+colorInHex);
		else
			tvColorTxt.setText("#"+colorInHex.substring(2));
		//Log.d(TAG,"color="+tvColorTxt.getText().toString());
		tvColorTxt.setTextColor(Color.parseColor("#" + colorInHex.substring(2)));
	}
	
	public void updateLargeButtonOverlay(){
		//Log.v(TAG,"[fcp]...updateLargeButtonOverlay");
		if(colorInHex==null)hexColor();
		//Log.d(TAG,"crash color= #"+colorInHex);// ispod broja 15, alpha =f pa fali 0 ispred...

        int c=Color.parseColor("#"+colorInHex);
		largeButton.setBackgroundColor(c);
		if( (tvRed.getText().toString()).equals("0") && tvGreen.getText().toString().equals("0") && tvBlue.getText().toString().equals("0"))
			largeButton.setText(getResources().getText(R.string.paste));
		else largeButton.setText(BUTTON_COPY+"\n"+pasteColor+COPY_TO );
        largeButton.setTextColor(Color.rgb(255-Color.red(c),255-Color.green(c),255-Color.blue(c)));
	}

	public void updateAlphaPercent(int progress){
		//Log.v(TAG;"[fcp]...updateAlphaPercent");
		DecimalFormat df=new DecimalFormat("#.##");
		tvAlphaPercent.setText(""+df.format(((float)(progress/255f)*100))+"%");
	}
	
	public void updateAlphaPercent(){
		//Log.v(TAG;"[fcp]...updateAlphaPercent");
		updateAlphaPercent(sbAlpha.getProgress());
	}
	
	public void changeBackgroundState(){
		if(buttonBackgroundChange.getText()==getResources().getText(R.string.change_to_white)){
			buttonBackgroundChange.setTextColor(Color.WHITE);
			buttonBackgroundChange.setBackgroundColor(Color.BLACK);
			masterLayout.setBackgroundColor(Color.WHITE);
			buttonBackgroundChange.setText(getResources().getText(R.string.change_to_black));
			
			((TextView)getActivity().findViewById(R.id.tvAlphaLetter)).setTextColor(Color.BLACK);
			tvRed.setTextColor(Color.BLACK);
			tvGreen.setTextColor(Color.BLACK);
			tvBlue.setTextColor(Color.BLACK);
			tvAlpha.setTextColor(Color.BLACK);
			tvAlphaPercent.setTextColor(Color.BLACK);
			cbAlpha.setTextColor(Color.BLACK);
			cbAlpha.setButtonDrawable(getResources().getSystem().getIdentifier("btn_check_holo_light", "drawable", "android"));
			
			((FrameLayout)getActivity().findViewById(R.id.fragment_placeholder)).setBackgroundColor(Color.WHITE);
			
		}else{
			buttonBackgroundChange.setTextColor(Color.BLACK);
			buttonBackgroundChange.setBackgroundColor(Color.WHITE);
			masterLayout.setBackgroundColor(Color.BLACK);
			buttonBackgroundChange.setText(getResources().getText(R.string.change_to_white));
			
			((TextView)getActivity().findViewById(R.id.tvAlphaLetter)).setTextColor(Color.WHITE);
			tvRed.setTextColor(Color.WHITE);
			tvGreen.setTextColor(Color.WHITE);
			tvBlue.setTextColor(Color.WHITE);
			tvAlpha.setTextColor(Color.WHITE);
			tvAlphaPercent.setTextColor(Color.WHITE);
			cbAlpha.setTextColor(Color.WHITE);
			cbAlpha.setButtonDrawable(getResources().getSystem().getIdentifier("btn_check_holo_dark", "drawable", "android"));
			
			
			((FrameLayout)getActivity().findViewById(R.id.fragment_placeholder)).setBackgroundColor(Color.BLACK);
			
			
		}
		
		
	}
	private int parseColor(String s){
        //#ff00ff00
        //#ff00ff
        //rgb(255,255,255)
        //argb(255,255,255,255)
        //rgba(255,255,255,0.3)


        return 0;
    }
	private void resetPaste(){
		Log.v(TAG,"[fcp]...resetPaste");
		pasteOption=PasteOption.hex;
		if(!cbAlpha.isChecked())pasteColor=colorInHex.substring(2);
		else pasteColor=colorInHex;
	}
}
