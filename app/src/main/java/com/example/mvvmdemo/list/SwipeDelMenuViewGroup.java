package com.example.mvvmdemo.list;
//https://blog.csdn.net/zxt0601/article/details/52303781

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class SwipeDelMenuViewGroup extends ViewGroup {
    private int mTouchSlop;

    public SwipeDelMenuViewGroup(Context context) {
        this(context,null);
    }

    public SwipeDelMenuViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeDelMenuViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public SwipeDelMenuViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int sumWidth = 0;
        int maxHeight = 0;
        boolean matchHeight = false;
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        final int count = getChildCount();
        // 测量子最大显示宽高组件，累加宽度，取最大的高度
        // 父组件测量模式是Exactly 还是 at_most，父组件的空间大小
        // setMeasuredDimension
        // 根据最大高度测量子view是match_parent
        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);
            final int freeWidthSpec = MeasureSpec.makeMeasureSpec(
                    MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.UNSPECIFIED);
            final int freeHeightSpec = MeasureSpec.makeMeasureSpec(
                    MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.UNSPECIFIED);

//            child.measure(freeWidthSpec, freeHeightSpec);
            measureChildWithMargins(child, freeWidthSpec, 0, freeHeightSpec, 0);

            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            maxHeight = Math.max(maxHeight,child.getMeasuredHeight());
            sumWidth = sumWidth + child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            if (heightMode != MeasureSpec.EXACTLY && lp.height == LinearLayout.LayoutParams.MATCH_PARENT) {
                // The height of the linear layout will scale, and at least one
                // child said it wanted to match our height. Set a flag indicating that
                // we need to remeasure at least that view when we know our height.
                matchHeight = true;
            }
        }
        int widthSizeAndState = resolveSizeAndState(sumWidth, widthMeasureSpec, 0);
        int heightSizeAndState = resolveSizeAndState(maxHeight, heightMeasureSpec, 0);

        setMeasuredDimension(widthSizeAndState,heightSizeAndState);

      // 处理子view match_parent情况
        if (matchHeight) {
            forceUniformHeight(count, widthMeasureSpec);
        }
    }
    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
                MeasureSpec.EXACTLY);
        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                if (lp.height == LinearLayout.LayoutParams.MATCH_PARENT) {
                    // Temporarily force children to reuse their old measured width
                    // FIXME: this may not be right for something like wrapping text?
                    int oldWidth = lp.width;
                    lp.width = child.getMeasuredWidth();

                    // Remeasure with new dimensions
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
        }
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams lp = null;
        int sumWidth = l;
        /**
         * 遍历所有childView根据其宽和高，以及margin进行布局
         */
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            lp = (MarginLayoutParams) childView.getLayoutParams();

            childView.layout(sumWidth + lp.leftMargin, getPaddingTop()+lp.topMargin,
                    sumWidth+lp.leftMargin+cWidth, getPaddingTop()+lp.topMargin+cHeight);

            sumWidth = sumWidth + lp.leftMargin+ lp.rightMargin + cWidth;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new MarginLayoutParams(getContext(), attributeSet);
    }


    private float mLastMotionX;
    private float mLastMotionY;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean doByMe;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //父view拦截条件

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
//                getParent().requestDisallowInterceptTouchEvent(true);
                // Remember where the motion event started
                getParent().requestDisallowInterceptTouchEvent(true);
                mLastMotionX = mInitialMotionX = ev.getX();
                mLastMotionY = mInitialMotionY = ev.getY();
                doByMe = true;
                break;
            case MotionEvent.ACTION_MOVE:
                final float x = ev.getX();
                final float dx = x - mLastMotionX;
                final float xDiff = Math.abs(dx);
                final float y = ev.getY();
                final float yDiff = Math.abs(y - mInitialMotionY);
                if (xDiff > mTouchSlop && xDiff * 0.5f > yDiff) {

                    mLastMotionX = x;
                    mLastMotionY = y;
                    scrollBy((int)-dx,0);
                    doByMe = true;
                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                    doByMe = false;
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                doByMe = false;
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                if(doByMe) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(doByMe) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    // 滑动冲突怎么解决？目前如果是recycleview中的子view
    // 在down的时候无法判断是应该子View还是父View来处理滑动事件，滑动一段距离后才会确定是横向滑动还是竖着滑动，才确定是父组件还是子组件来消费

}
