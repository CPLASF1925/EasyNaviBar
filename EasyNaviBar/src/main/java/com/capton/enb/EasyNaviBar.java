package com.capton.enb;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itingchunyu.badgeview.BadgeTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is a useful and efficient indicator view for switching fragments in an activity.
 * Created by capton on 2017/9/16.
 */

public class EasyNaviBar extends RelativeLayout {

    public interface OnTabClickListener {
        void onTabClick(int position);
    }

    private HorizontalScrollView scrollview;
    private View dividerView;
    private OnTabClickListener onTabClickListener;

    private int mWidth;
    private int mHeight;
    private List<TabData> tabDataList=new ArrayList<>();
    private int checkedPosition=0;
    /*
    * 这里的宽、高与margin初始数值默认单位为dp,字体大小默认单位为sp
    * */
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

    /*
    * Map存储每个postion的数值，用于个性化不同位置上的图标和文本的属性
    * */
    private Map<Integer,Integer> iconMarginTopMap=new HashMap<>();
    private Map<Integer,Integer> iconMarginBottomMap=new HashMap<>();
    private Map<Integer,Integer> textMarginTopMap=new HashMap<>();
    private Map<Integer,Integer> textMarginBottomMap=new HashMap<>();
    private Map<Integer,Integer> textColorMap=new HashMap<>();
    private Map<Integer,Integer> checkTextColorMap=new HashMap<>();
    private Map<Integer,Integer> textSizeMap=new HashMap<>();
    private Map<Integer,Integer> iconWidthMap=new HashMap<>();
    private Map<Integer,Integer> iconHeightMap=new HashMap<>();

    private List<TextView> textViews=new ArrayList<>();
    private List<ImageView> imageViews=new ArrayList<>();
    private List<View> tabViews=new ArrayList<>();

    private boolean setWidthByUser;
    private boolean showDivider=true;

    public EasyNaviBar(Context context) {
        this(context,null);
    }

    public EasyNaviBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EasyNaviBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        textColor=getResources().getColor(R.color.black);
        dividerColor=getResources().getColor(R.color.bg_grey);
        checkTextColor=getResources().getColor(R.color.red);

        scrollview= (HorizontalScrollView) LayoutInflater.from(getContext()).inflate(R.layout.easy_navi_bar_layout,this,false);
        dividerView= new View(context);
        dividerView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2));
        dividerView.setBackgroundColor(dividerColor);

        initData();
        initTabView();

        addView(scrollview);
        for (View view:
                tabViews) {
            ((LinearLayout)scrollview.getChildAt(0)).addView(view);
        }
        addView(dividerView);
    }

    private void initData(){
        String []textStrs=new String[]{"云盘","应用","日程","设置"};
        int []iconReses=new int[]{R.drawable.ic_cloud_circle_black_36dp,
                                 R.drawable.ic_dashboard_black_36dp,
                                 R.drawable.ic_event_note_black_36dp,
                                 R.drawable.ic_phonelink_setup_black_36dp};
        int []checkIconReses=new int[]{R.drawable.ic_cloud_circle_pink_500_36dp,
                R.drawable.ic_dashboard_pink_36dp,
                R.drawable.ic_event_note_light_blue_600_36dp,
                R.drawable.ic_phonelink_setup_green_36dp};
        for (int i = 0; i < textStrs.length; i++) {
            tabDataList.add(new TabData(iconReses[i],checkIconReses[i],textStrs[i]));
        }
    }


    private void initTabView(){
        for (int i = 0; i < tabDataList.size(); i++) {
              View tabview= LayoutInflater.from(getContext()).inflate(R.layout.item_tab_layout,(LinearLayout)scrollview.getChildAt(0),false);
             ImageView imageView = (ImageView) tabview.findViewById(R.id.icon);
             TextView textView = (TextView) tabview.findViewById(R.id.text);

            doSomeViewsParamsSettings(i,imageView,textView);

            if(i==checkedPosition){
                if(checkTextColorMap.get(i)!=null)
                    textView.setTextColor(getResources().getColor(checkTextColorMap.get(i)));
                else
                    textView.setTextColor(checkTextColor);
                imageView.setImageResource(tabDataList.get(i).checkIconRes);
            }

            textViews.add(textView);
            imageViews.add(imageView);

            tabview.setTag(i);
            tabview.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkedPosition=(Integer) v.getTag();
                    resetTabView();
                    imageViews.get(checkedPosition).setImageResource(tabDataList.get(checkedPosition).checkIconRes);
                    if(checkTextColorMap.get(checkedPosition)!=null)
                        textViews.get(checkedPosition).setTextColor(checkTextColorMap.get(checkedPosition));
                    else
                        textViews.get(checkedPosition).setTextColor(checkTextColor);

                    if(onTabClickListener!=null)
                        onTabClickListener.onTabClick(checkedPosition);
                }
            });

            tabViews.add(tabview);
        }
    }

    /**
     * 为生成的图标和文本视图设置所有属性
     * @param i  图标和文本所在项的位置
     * @param imageView
     * @param textView
     */
    private void doSomeViewsParamsSettings(int i,ImageView imageView,TextView textView){
        imageView.setImageResource(tabDataList.get(i).iconRes);
        textView.setText(tabDataList.get(i).text);

        if(textSizeMap.get(i)!=null)
            textView.setTextSize(textSizeMap.get(i));
        else
            textView.setTextSize(this.textSize);
        if(textColorMap.get(i)!=null)
            textView.setTextColor(textColorMap.get(i));
        else
            textView.setTextColor(textColor);

        int textMarginTopPx,textMarginBottomPx;
        if(textMarginTopMap.get(i)!=null)
            textMarginTopPx = DisplayUtil.dip2px(getContext(), textMarginTopMap.get(i));
        else
            textMarginTopPx = DisplayUtil.dip2px(getContext(),textMarginTop);
        if(textMarginBottomMap.get(i)!=null)
            textMarginBottomPx = DisplayUtil.dip2px(getContext(), textMarginBottomMap.get(i));
        else
            textMarginBottomPx = DisplayUtil.dip2px(getContext(),textMarginBottom);

        LinearLayout.LayoutParams textLp=(LinearLayout.LayoutParams) textView.getLayoutParams();
        if(textSizeMap.get(i)!=null)
            textLp.height=TextSizeUtil.getTextHeight(DisplayUtil.sp2px(getContext(),textSizeMap.get(i)));
        else
            textLp.height=TextSizeUtil.getTextHeight(DisplayUtil.sp2px(getContext(),textSize));
        if(textSize!=0)
        textLp.height=TextSizeUtil.getTextHeight(DisplayUtil.sp2px(getContext(),textSize));
        textLp.topMargin=textMarginTopPx;
        textLp.bottomMargin=textMarginBottomPx;

        int widthPx,heightPx,iconMarginTopPx,iconMarginBottomPx;
        if(iconWidthMap.get(i)!=null)
            widthPx = DisplayUtil.dip2px(getContext(), iconWidthMap.get(i));
        else
            widthPx=DisplayUtil.dip2px(getContext(),iconWidth);
        if(iconHeightMap.get(i)!=null)
            heightPx = DisplayUtil.dip2px(getContext(), iconHeightMap.get(i));
        else
            heightPx=DisplayUtil.dip2px(getContext(),iconHeight);
        if(iconMarginTopMap.get(i)!=null)
            iconMarginTopPx = DisplayUtil.dip2px(getContext(), iconMarginTopMap.get(i));
        else
            iconMarginTopPx=DisplayUtil.dip2px(getContext(),iconMarginTop);
        if(iconMarginBottomMap.get(i)!=null)
            iconMarginBottomPx = DisplayUtil.dip2px(getContext(), iconMarginBottomMap.get(i));
        else
            iconMarginBottomPx=DisplayUtil.dip2px(getContext(),iconMarginBottom);

        LinearLayout.LayoutParams iconLp= (LinearLayout.LayoutParams)imageView.getLayoutParams();
        iconLp.width=widthPx;
        if(heightPx!=0)
        iconLp.height=heightPx;
        iconLp.topMargin=iconMarginTopPx;
        iconLp.bottomMargin=iconMarginBottomPx;
    }

    private void setChildViewsParams(){
        for (int i = 0; i <tabViews.size(); i++) {
             ImageView imageView = imageViews.get(i);
             TextView textView = textViews.get(i);
            doSomeViewsParamsSettings(i,imageView,textView);
            if(i==checkedPosition){
                if(checkTextColorMap.get(i)!=null)
                    textView.setTextColor(checkTextColorMap.get(i));
                else
                    textView.setTextColor(checkTextColor);
                imageView.setImageResource(tabDataList.get(i).checkIconRes);
            }
        }
    }

    private void resetTabView(){
        for (int i = 0; i < imageViews.size(); i++) {
            imageViews.get(i).setImageResource(tabDataList.get(i).iconRes);
            textViews.get(i).setTextColor(textColor);
        }
    }

    int exactityHeight;
    int iconheightPx,textHeightPx,iconMarginTopPx,iconMarginBottomPx,textMarginTopPx,textMarginBottomPx;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);


        if(textMarginTop!=0||textMarginBottom!=0){
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) findViewById(R.id.text).getLayoutParams();
            lp.topMargin=DisplayUtil.dip2px(getContext(),textMarginTop);
            lp.bottomMargin=DisplayUtil.dip2px(getContext(),textMarginBottom);
        }

        if(iconMarginTop!=0||iconMarginBottom!=0){
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) findViewById(R.id.icon).getLayoutParams();
            lp.topMargin=DisplayUtil.dip2px(getContext(),iconMarginTop);
            lp.bottomMargin=DisplayUtil.dip2px(getContext(),iconMarginBottom);;
        }

        iconheightPx=DisplayUtil.dip2px(getContext(),iconHeight);
        textHeightPx=TextSizeUtil.getTextHeight(DisplayUtil.sp2px(getContext(),textSize));
        iconMarginTopPx=DisplayUtil.dip2px(getContext(),iconMarginTop);
        textMarginTopPx=DisplayUtil.dip2px(getContext(),textMarginTop);
        iconMarginBottomPx=DisplayUtil.dip2px(getContext(),iconMarginBottom);
        textMarginBottomPx=DisplayUtil.dip2px(getContext(),textMarginBottom);

        customTextHeight();
        customIconHeight();

        if(!setWidthByUser)
         mWidth=widthMode==MeasureSpec.EXACTLY? widthSize:((View)getParent()).getWidth();

        int sumHeight=iconheightPx+textHeightPx+iconMarginTopPx+iconMarginBottomPx+textMarginTopPx+textMarginBottomPx;
        mHeight=heightMode==MeasureSpec.EXACTLY?
                heightSize:(sumHeight<heightSize?sumHeight:heightSize);

        if(heightMode==MeasureSpec.EXACTLY) {
            exactityHeight = heightSize;
        }else {
            scrollview.getLayoutParams().height = sumHeight;
            scrollview.getChildAt(0).getLayoutParams().height = sumHeight;
            ((ViewGroup) scrollview.getChildAt(0)).getChildAt(0).getLayoutParams().height = sumHeight;
        }

        if(sumHeight<heightSize){
            if(exactityHeight!=0) {
                scrollview.getLayoutParams().height = exactityHeight;
                scrollview.getChildAt(0).getLayoutParams().height = exactityHeight;
                ((ViewGroup) scrollview.getChildAt(0)).getChildAt(0).getLayoutParams().height = exactityHeight;
            }
        }

        for (int i=0;i<tabViews.size();i++) {
            ViewGroup.LayoutParams lp= ( ViewGroup.LayoutParams)tabViews.get(i).getLayoutParams();
            lp.width=mWidth/tabViews.size();
        }

        setMeasuredDimension(mWidth,mHeight);
    }

    private void customTextHeight(){
        if(textSizeMap.size()>1) {
            ValueComparator bvc = new ValueComparator(textSizeMap);
            TreeMap<Integer, Integer> sorted_map = new TreeMap<Integer, Integer>(bvc);
            textHeightPx=TextSizeUtil.getTextHeight(DisplayUtil.sp2px(getContext(),sorted_map.get(0)));
        }else if(textSizeMap.size()==1){
            Iterator iter = textSizeMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                textHeightPx=TextSizeUtil.getTextHeight(DisplayUtil.sp2px(getContext(),(int)entry.getValue()));
            }
        }
    }
    private void customIconHeight(){
        if(iconHeightMap.size()>1) {
            ValueComparator bvc = new ValueComparator(iconHeightMap);
            TreeMap<Integer, Integer> sorted_map = new TreeMap<Integer, Integer>(bvc);
            iconheightPx=DisplayUtil.dip2px(getContext(),sorted_map.get(0));
        }else if(iconHeightMap.size()==1){
            Iterator iter = iconHeightMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                iconheightPx=DisplayUtil.dip2px(getContext(),(int)entry.getValue());
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
            getChildAt(0).layout(0, 0,mWidth, mHeight);
            getChildAt(1).layout(0,0,mWidth,2);
            setChildViewsParams();
    }

    public EasyNaviBar setTabData(String []textStrs,int []iconReses,int []checkIconReses){
        tabDataList.clear();
       if(textStrs.length!=iconReses.length||
               checkIconReses.length!=iconReses.length||
               textStrs.length!=checkIconReses.length){
           if(textStrs.length==0){
               try {
                   throw new ResourceNumException("Title and text number is 0!");
               } catch (ResourceNumException e) {
                   e.printStackTrace();
               }
           }else {
               try {
                   throw new ResourceNumException("Numbers of title and text are not the same!");
               } catch (ResourceNumException e) {
                   e.printStackTrace();
               }
           }
       }else {
           for (int i = 0; i < textStrs.length; i++) {
               tabDataList.add(new TabData(iconReses[i],checkIconReses[i],textStrs[i]));
           }
       }
       return this;
    }


    public EasyNaviBar setOnTabClickListener(OnTabClickListener onTabClickListener){
        this.onTabClickListener=onTabClickListener;
        return this;
    }
    /**
     * 导航栏高度
     * @param height 单位dp
     */
    public EasyNaviBar setHeight(int height){
       mHeight=DisplayUtil.dip2px(getContext(),height);
       getLayoutParams().height=mHeight;
        getChildAt(0).getLayoutParams().height=mHeight;
        return this;
    }
    /**
     * 导航栏宽度
     * @param width 单位dp
     */
    public EasyNaviBar setWidth(int width){
        mWidth=DisplayUtil.dip2px(getContext(),width);
        setWidthByUser=true;
        getLayoutParams().width=mWidth;
        for (int i = 0; i < tabViews.size(); i++) {
            if(badgeTextMap.get(i)!=null)
            setBadgeMessage(i);
        }

        return this;
    }

    /**
     * 设置控件生成时选中的位置
     * @param position
     */
    public EasyNaviBar setDefaultCheckPosition(int position){
        if(position<tabViews.size())
            checkedPosition=position;
        else
            checkedPosition=tabViews.size()-1;
        return this;
    }

    /**
     * 动态指定选中的位置
     * @param position
     */
    public EasyNaviBar check(int position){
        checkedPosition=(Integer) tabViews.get(position).getTag();
        resetTabView();
        imageViews.get(position).setImageResource(tabDataList.get(checkedPosition).checkIconRes);
        if(checkTextColorMap.get(checkedPosition)!=null)
            textViews.get(position).setTextColor(checkTextColorMap.get(checkedPosition));
        else
            textViews.get(position).setTextColor(checkTextColor);
        return this;
    }

    public EasyNaviBar setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        if(dividerView!=null)
            dividerView.setBackgroundColor(dividerColor);
        return this;
    }

    public EasyNaviBar setTextSize(int position, int textSize){
        textSizeMap.put(position,textSize);
        return this;
    }
    public EasyNaviBar setTextSize(int textSize){
        this.textSize=textSize;
        return this;
    }
    public EasyNaviBar setIconWidth(int width){
        this.iconWidth=width;
        return this;
    }
    public EasyNaviBar setIconWidth(int position,int width){
        iconWidthMap.put(position,width);
        return this;
    }
    public EasyNaviBar setIconHeight(int height){
        this.iconHeight=height;
        return this;
    }
    public EasyNaviBar setIconHeight(int position,int height){
        iconHeightMap.put(position,height);
        return this;
    }
    public EasyNaviBar setTextColor(int color){
        this.textColor=color;
        return this;
    }
    public EasyNaviBar setTextColor(int position,int color){
        textColorMap.put(position,color);
        return this;
    }
    public EasyNaviBar setCheckTextColor(int color){
        this.checkTextColor=color;
        return this;
    }
    public EasyNaviBar setCheckTextColor(int postion,int color){
        checkTextColorMap.put(postion,color);
        return this;
    }

    public EasyNaviBar setIconMarginTop(int marginTop){
        this.iconMarginTop=marginTop;
        return this;
    }
    public EasyNaviBar setIconMarginBottom(int marginBottom){
        this.iconMarginBottom=marginBottom;
        return this;
    }

    public EasyNaviBar setTextMarginTop(int marginTop){
        this.textMarginTop=marginTop;
        return this;
    }
    public EasyNaviBar setTextMarginBottom(int marginBottom){
        this.textMarginBottom=marginBottom;
        return this;
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public int getIconWidth() {
        return iconWidth;
    }

    public int getIconHeight() {
        return iconHeight;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getDividerColor() {
         return dividerColor;
    }

    public int getCheckTextColor() {
        return checkTextColor;
    }

    public int getIconMarginTop() {
        return iconMarginTop;
    }

    public int getIconMarginBottom() {
        return iconMarginBottom;
    }

    public int getTextMarginTop() {
        return textMarginTop;
    }

    public int getTextMarginBottom() {
        return textMarginBottom;
    }

    public Map<Integer, Integer> getIconMarginTopMap() {
        return iconMarginTopMap;
    }

    public Map<Integer, Integer> getIconMarginBottomMap() {
        return iconMarginBottomMap;
    }

    public Map<Integer, Integer> getTextMarginTopMap() {
        return textMarginTopMap;
    }

    public Map<Integer, Integer> getTextMarginBottomMap() {
        return textMarginBottomMap;
    }

    public Map<Integer, Integer> getTextColorMap() {
        return textColorMap;
    }

    public Map<Integer, Integer> getCheckTextColorMap() {
        return checkTextColorMap;
    }

    public Map<Integer, Integer> getTextSizeMap() {
        return textSizeMap;
    }

    public Map<Integer, Integer> getIconWidthMap() {
        return iconWidthMap;
    }

    public Map<Integer, Integer> getIconHeightMap() {
        return iconHeightMap;
    }

    /**
     * 获取对应位置的View(icon和text的父布局)
     * @param position
     * @return
     */
    public LinearLayout getTabView(int position){
        return (LinearLayout) tabViews.get(position);
    }

    /**
     * 获取对应位置加载icon的ImageView
     * @param position
     * @return
     */
    public ImageView getImageView(int position){
        return imageViews.get(position);
    }

    /**
     * 获取对应位置加载text的TextView
     * @param position
     * @return
     */
    public TextView getTextView(int position){
        return textViews.get(position);
    }

    /**
     * 获取头部分割线View
     * @return
     */
    public View getDividerView(){
        return dividerView;
    }

    public EasyNaviBar showDivider(boolean dividerShow){
        if(dividerShow){
            showDivider=true;
            dividerView.setVisibility(VISIBLE);
        }else {
            showDivider=false;
            dividerView.setVisibility(INVISIBLE);
        }
        return this;
    }
    public boolean isDividerShow(){
        return showDivider;
    }

    public int getViewHight(){
        return getHeight();
    }
    public int getViewWidth(){
        return  getWidth();
    }

    private Map<Integer,BadgeTextView> badgeTextMap=new HashMap<>();

    public EasyNaviBar setBadgeMessage(final int position){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                   BadgeTextView mBadgeView= new BadgeTextView(getContext());
                    mBadgeView.setTargetView(tabViews.get(position));
                    mBadgeView.setBadgeCount(0)
                            .setmDefaultTopPadding(DisplayUtil.dip2px(getContext(),iconMarginTop));
                    int marginRight = (tabViews.get(position).getWidth() - imageViews.get(position).getWidth()
                            - (int) mBadgeView.getTextSize()) / 2;
                    mBadgeView.setmDefaultRightPadding(marginRight);
                    badgeTextMap.put(position,mBadgeView);
            }
        },100);
        return this;
    }
    public EasyNaviBar setBadgeNumber(int position,int number,int paddingRight,int paddingTop){
        BadgeTextView mBadgeView=new BadgeTextView(getContext());
        mBadgeView.setTargetView(tabViews.get(position));
        mBadgeView.setBadgeCount(number)//数量(0:表示小红点)
                .setmDefaultRightPadding(paddingRight)//右依附视图距离
                .setmDefaultTopPadding(paddingTop);//上依附视图距离
        badgeTextMap.put(position,mBadgeView);
        return this;
    }

    public EasyNaviBar setBadgeMessage(int position,int paddingRight,int paddingTop){
        BadgeTextView mBadgeView=new BadgeTextView(getContext());
        mBadgeView.setTargetView(tabViews.get(position));//设置目标targetView(可任意:此处只做演示)
        mBadgeView.setBadgeCount(0)//数量(0:表示小红点)
                .setmDefaultRightPadding(paddingRight)//右依附视图距离
                .setmDefaultTopPadding(paddingTop);//上依附视图距离
        badgeTextMap.put(position,mBadgeView);
        return this;
    }
    public EasyNaviBar setBadgeColor(int position,int colorRes){
        if(badgeTextMap.get(position)!=null)
            badgeTextMap.get(position).setBadgeColor(getResources().getColor(colorRes));
        return this;
    }
    public EasyNaviBar setBadgeTextSize(int position,float textSize){
        if(badgeTextMap.get(position)!=null)
            badgeTextMap.get(position).setTextSize(textSize);
        return this;
    }

    public EasyNaviBar setBadgeShown(int position,boolean isShowBadge){
        if(badgeTextMap.get(position)!=null)
        badgeTextMap.get(position).setBadgeShown(isShowBadge);
        return this;
    }

}
