package com.capton.enbdemo;

import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.capton.enb.DisplayUtil;
import com.capton.enb.EasyNaviBar;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class MainActivity extends AppCompatActivity {

    EasyNaviBar naviBar;
    Toolbar toolbar;

    private int width;
    private int height;
    private int iconWidth=24;
    private int iconHeight=24;
    private int textSize=10;
    private int textColor;
    private int dividerColor;
    private int checkTextColor;
    private int iconMarginTop=2;
    private int iconMarginBottom=0;
    private int textMarginTop=0;
    private int textMarginBottom=2;
    private boolean showDivider=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setTitle("EasyNaviBar");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.go){
                    width=naviBar.getViewWidth();
                    height=naviBar.getViewHight();
                    dividerColor=naviBar.getDividerColor();
                    checkTextColor=naviBar.getCheckTextColor();
                    textColor=naviBar.getTextColor();
                    Intent intent=new Intent(MainActivity.this,DemoActivity.class);
                    Settings settings=new Settings();
                    settings.setHeight(height);
                    settings.setWidth(width);
                    settings.setCheckTextColor(checkTextColor);
                    settings.setTextColor(textColor);
                    settings.setTextSize(textSize);
                    settings.setIconWidth(iconWidth);
                    settings.setIconHeight(iconHeight);
                    settings.setDividerColor(dividerColor);
                    settings.setIconMarginTop(iconMarginTop);
                    settings.setIconMarginBottom(iconMarginBottom);
                    settings.setTextMarginTop(textMarginTop);
                    settings.setTextMarginBottom(textMarginBottom);
                    settings.setShowDivider(showDivider);
                    intent.putExtra("setting",settings);
                   startActivity(intent);
                }
                return true;
            }
        });

        naviBar= (EasyNaviBar) findViewById(R.id.navibar);
        String titles[]=new String[]{"应用","日程","设置","云盘"};
        int icons[]=new int[]{R.drawable.ic_dashboard_black_36dp,
                R.drawable.ic_event_note_black_36dp,
                R.drawable.ic_phonelink_setup_black_36dp,
                R.drawable.ic_cloud_circle_black_36dp};
        int checkIcons[]=new int[]{R.drawable.ic_dashboard_green_36dp,
                R.drawable.ic_event_note_amber_500_36dp,
                R.drawable.ic_phonelink_setup_green_36dp,
                R.drawable.ic_cloud_circle_light_blue_600_36dp};
        naviBar.setTabData(titles, icons, checkIcons);
        naviBar.setOnTabClickListener(new EasyNaviBar.OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                Toast.makeText(getBaseContext(),"Hello I'm Ccapton "+position,Toast.LENGTH_SHORT).show();
            }
        });


        ((SeekBar)findViewById(R.id.width)).setOnSeekBarChangeListener(new MyOnSeekBarChangelistenner(R.id.width));
        ((SeekBar)findViewById(R.id.width)).setProgress(DisplayUtil.getScreenWidthDp(this));
        ((SeekBar)findViewById(R.id.height)).setOnSeekBarChangeListener(new MyOnSeekBarChangelistenner(R.id.height));
        ((SeekBar)findViewById(R.id.iconSize)).setOnSeekBarChangeListener(new MyOnSeekBarChangelistenner(R.id.iconSize));
        ((SeekBar)findViewById(R.id.textSize)).setOnSeekBarChangeListener(new MyOnSeekBarChangelistenner(R.id.textSize));
        ((SeekBar)findViewById(R.id.iconMargintTop)).setOnSeekBarChangeListener(new MyOnSeekBarChangelistenner(R.id.iconMargintTop));
        ((SeekBar)findViewById(R.id.iconMarginBottom)).setOnSeekBarChangeListener(new MyOnSeekBarChangelistenner(R.id.iconMarginBottom));
        ((SeekBar)findViewById(R.id.textMarginTop)).setOnSeekBarChangeListener(new MyOnSeekBarChangelistenner(R.id.textMarginTop));
        ((SeekBar)findViewById(R.id.textMarginBottom)).setOnSeekBarChangeListener(new MyOnSeekBarChangelistenner(R.id.textMarginBottom));

        findViewById(R.id.textColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPicker(v,getResources().getColor(R.color.black));
            }
        });
        findViewById(R.id.textCheckColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPicker(v,getResources().getColor(R.color.red));
            }
        });
        findViewById(R.id.dividerColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPicker(v,getResources().getColor(R.color.bg_grey));
            }
        });
        ((Switch)findViewById(R.id.showDivider)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    showDivider=true;
                    naviBar.showDivider(true);
                }
                else {
                    showDivider = false;
                    naviBar.showDivider(false);
                }
            }
        });

        findViewById(R.id.copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager= (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                width=naviBar.getViewWidth();
                height=naviBar.getViewHight();
                dividerColor=naviBar.getDividerColor();
                checkTextColor=naviBar.getCheckTextColor();
                textColor=naviBar.getTextColor();
                String params="ViewWidth = "+DisplayUtil.px2dip(MainActivity.this,width)+"dp\n"
                        +"ViewHeight = "+DisplayUtil.px2dip(MainActivity.this,height)+"dp\n"
                        +"IconWidth=IconHeigh=IconSize = "+iconWidth+"dp\n"
                        +"TextSize = "+textSize+"sp\n"
                        +"IconMarginTop = "+iconMarginTop+"dp\n"
                        +"IconMarginBottom = "+iconMarginBottom+"dp\n"
                        +"TextMarginTop = "+textMarginTop+"dp\n"
                        +"TextMarginBottom = "+textMarginBottom+"dp\n"
                        +"TextColor = "+textColor+"\n"
                        +"TextCheckColor = "+checkTextColor+"\n"
                        +"DividerColor = "+dividerColor;
                clipboardManager.setText(params);
                Toast.makeText(MainActivity.this,"You have Copied the layoutParams of EasyNaviBar",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void showColorPicker(final View view, int color){
        ColorPickerDialogBuilder
                .with(MainActivity.this)
                .setTitle("Choose color")
                .initialColor(color)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        if(view.getId()==R.id.textColor) {
                            textColor = selectedColor;
                            naviBar.setTextColor(textColor);
                        } else if(view.getId()==R.id.textCheckColor){
                            checkTextColor = selectedColor;
                            naviBar.setCheckTextColor(checkTextColor);
                        }else if(view.getId()==R.id.dividerColor){
                            dividerColor = selectedColor;
                            naviBar.setDividerColor(dividerColor);
                        }
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                          view.setBackgroundColor(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

   private class MyOnSeekBarChangelistenner implements SeekBar.OnSeekBarChangeListener{
        int id;
        public MyOnSeekBarChangelistenner(int id) {this.id = id;}
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (id){
                case R.id.width:
                    naviBar.setWidth(progress);
                    break;
                case R.id.height:
                    naviBar.setHeight(progress);
                    break;
                case R.id.iconSize:
                    naviBar.setIconWidth(progress).setIconHeight(progress);
                    break;
                case R.id.textSize:
                    naviBar.setTextSize(progress);
                    break;
                case R.id.iconMargintTop:
                    naviBar.setIconMarginTop(progress);
                    break;
                case R.id.iconMarginBottom:
                    naviBar.setIconMarginBottom(progress);
                    break;
                case R.id.textMarginTop:
                    naviBar.setTextMarginTop(progress);
                    break;
                case R.id.textMarginBottom:
                    naviBar.setTextMarginBottom(progress);
                    break;
            }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            switch (id){
                case R.id.width:
                    width=seekBar.getProgress();
                    break;
                case R.id.height:
                    height=seekBar.getProgress();
                    break;
                case R.id.iconSize:
                    iconWidth=seekBar.getProgress();
                    iconHeight=seekBar.getProgress();
                    break;
                case R.id.textSize:
                    textSize=seekBar.getProgress();
                    break;
                case R.id.iconMargintTop:
                    iconMarginTop=seekBar.getProgress();
                    break;
                case R.id.iconMarginBottom:
                    iconMarginBottom=seekBar.getProgress();
                    break;
                case R.id.textMarginTop:
                    textMarginTop=seekBar.getProgress();
                    break;
                case R.id.textMarginBottom:
                    textMarginBottom=seekBar.getProgress();
                    break;
            }
        }
    }

}
