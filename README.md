# EasyNaviBar
[![Travis](https://img.shields.io/travis/rust-lang/rust.svg)](https://github.com/Ccapton/EasyNaviBar) [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/Ccapton/EasyNaviBar) ![](https://img.shields.io/badge/api-16-blue.svg)

This is a useful and efficient indicator view for switching fragments in an activity.It's very easy for you to use in your android application!

![](https://raw.githubusercontent.com/Ccapton/EasyNaviBar/master/ENBlogo.jpg)

## Display

**You guys can set the EasyNaviBar like this below:**
 
<img src="https://raw.githubusercontent.com/Ccapton/EasyNaviBar/master/enb.gif" width = "300" height = "533"  align=center /> <img src="https://raw.githubusercontent.com/Ccapton/EasyNaviBar/master/enb_display.png" width = "300" height = "533"  align=center /> 

## How to Get it
**build.gradle(Project)**
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
**build.gradle(Module:app)**
```
dependencies {
         compile 'com.github.Ccapton:EasyNaviBar:1.0.3'
   }
```
## Demo code 
in your layout.xml
```
 <com.capton.enb.EasyNaviBar 
        android:id="@+id/navibar"
        android:background="@color/white"  
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
```
in activity 
```
        EasyNaviBar naviBar= (EasyNaviBar) findViewById(R.id.navibar);
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
        naviBar.setBadgeMessage(2);
        naviBar.setOnTabClickListener(new EasyNaviBar.OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                Toast.makeText(getBaseContext(),"Hello I'm Ccapton "+position,Toast.LENGTH_SHORT).show();
            }
        });
```
## Mainly Methods
method | useage
------------ | -------------
setTabData(String []textStrs,int []iconReses,int []checkIconReses) | you must set these arrays (text,icons' resId and checked icons' resId)
setOnTabClickListener(OnTabClickListener onTabClickListener) | set a clicklistener for EasyNaviBar 
check(int position) | set a tab checked ,not by touching
setDefaultCheckPosition(int position) | set a tab checked at the first time we see it,not by touching
showDivider(boolean showDivider) | show the top divider line or not
setBadgeMessage(int position) | (position: The index of each tab) show message tips
setWidth(int width) | set view width 
setHeight(int height) | set view height
setIconWidth(int width) | set icon view width
setIconHeight(int height | set icon view height
setTextSize(int textSize) | set text size
setIconMarinTop(int marginTop) |  ~
setIconMarinTop(int position,int marginTop) |  ~ 
setIconMarginBottom(int marginBottom) | ~
setIconMarinBottom(int position,int marginTop) |  ~
setTextMarinTop(int marginTop) |  ~
setTextMarinTop(int position,int marginTop) |  ~
setTextMarginBottom(int marginBottom) | ~
setTextMarinBottom(int position,int marginTop) |  ~
setTextColor(int color) | ~
setTextColor(int postion,int color) | ~
setCheckTextColor(int color) | ~
setCheckTextColor(int postion,int color) | ~
setDividerColor(int color) | ~
... | ~
lots of get Methods | ~
... | ~

## Thanks 
**liyanxi/BadgeView** https://github.com/liyanxi/BadgeView

**QuadFlask/colorpicker** https://github.com/QuadFlask/colorpicker

## License
```
Copyright 2016-2017 Ccapton

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


