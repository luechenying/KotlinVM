package com.android.baselibrary.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.baselibrary.R;


import java.util.ArrayList;
import java.util.List;


public class MainNavigateTabBar extends LinearLayout {

    private static final String KEY_CURRENT_TAG = MainNavigateTabBar.class.getName() + "KEY";


    private List<ViewHolder> mViewHolderList;

    /**
     * Activity 当前选中的tag
     */
    private String mCurrentTag;

    /**
     * Activity重建时需要选中的tag
     */
    private String mRestoreTag;

    /**
     * 主内容显示的布局ID
     */
    private int mMainNavigateTabContainerId;

    /**
     * 当前选中的Tab的布局ID
     */
    private int mMainNavigateTabViewId;

    /**
     * 未选择的Tab文字颜色
     */
    private ColorStateList mNormalTextColor;

    /**
     * 选择的Tab文字颜色
     */
    private ColorStateList mSelectedTextColor;

    /**
     * 文字的大小
     */
    private float mTabTextSize;

    /**
     * 默认选择Tab的index
     */
    private int mDefaultSelectedTab = 0;
    private int mBackgroundColor;


    public MainNavigateTabBar(Context context) {
        this(context, null);
    }

    public MainNavigateTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainNavigateTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MainNavigateTabBar, 0, 0);
        ColorStateList normalTextColor = typedArray.getColorStateList(R.styleable.MainNavigateTabBar_navigateTabNormalTextColor);

        ColorStateList selectedTabTextColor = typedArray.getColorStateList(R.styleable.MainNavigateTabBar_navigateTabSelectedTextColor);


        // mBackgroundColor = typedArray.getColor(R.styleable.MainNavigateTabBar_navigateBackgroundColor, getResources().getColor(android.R.color.white));

        mMainNavigateTabContainerId = typedArray.getResourceId(R.styleable.MainNavigateTabBar_containerId, 0);

        mMainNavigateTabViewId = typedArray.getResourceId(R.styleable.MainNavigateTabBar_navigateTabViewId, R.layout.base_navigate_tabbar_item);

        mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.MainNavigateTabBar_navigateTabTextSize, 0);

        if (normalTextColor != null) {
            mNormalTextColor = normalTextColor;
        } else {
            mNormalTextColor = context.getResources().getColorStateList(R.color.tab_text_normal);
        }

        if (selectedTabTextColor != null) {
            mSelectedTextColor = selectedTabTextColor;
        } else {
            checkAppCompatTheme(context);
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            mSelectedTextColor = context.getResources().getColorStateList(typedValue.resourceId);
        }
        mViewHolderList = new ArrayList<>();
    }


    public void addTab(Class frameLayoutClass, TabParam tabParam) {

        if (TextUtils.isEmpty(tabParam.title)) {
            tabParam.title = getContext().getString(tabParam.titleStringRes);
        }

        View view = LayoutInflater.from(getContext()).inflate(mMainNavigateTabViewId, null);
        //  view.setFocusable(true);

        ViewHolder holder = new ViewHolder();
        holder.mTabIndex = mViewHolderList.size();
        holder.mFragmentClass = frameLayoutClass;
        holder.mTag = tabParam.title;
        holder.mTabParam = tabParam;
        holder.mTabIcon = (ImageView) view.findViewById(R.id.tab_icon);
        holder.mTabTitle = ((TextView) view.findViewById(R.id.tab_title));
        holder.mMsgView = (MsgView) view.findViewById(R.id.msg_view);
        holder.mView = view;

        if (TextUtils.isEmpty(tabParam.title)) {
            holder.mTabTitle.setVisibility(View.INVISIBLE);
        } else {
            holder.mTabTitle.setText(tabParam.title);
        }

        if (mTabTextSize != 0) {
            holder.mTabTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
        }
        if (mNormalTextColor != null) {
            holder.mTabTitle.setTextColor(mNormalTextColor);
        }

        if (tabParam.iconResId > 0) {
            holder.mTabIcon.setImageResource(tabParam.iconResId);
        } else {
            holder.mTabIcon.setVisibility(View.INVISIBLE);
        }

        view.setTag(holder);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Object object = view.getTag();
                if (object != null && object instanceof ViewHolder) {
                    ViewHolder holder = (ViewHolder) view.getTag();

                    if (holder.mTag.equals(mCurrentTag)) {
                        Fragment fragment = getFragmentByTag(holder.mTag);
                        if (fragment != null && fragment instanceof onRepeatClickListener) {
                            ((onRepeatClickListener) fragment).onRepeatClick();
                        }
                    }

                    //tab按钮点击事件回调
                    if (mTabClickListener != null) {
                        if (mTabClickListener.onTabClick(holder)) {
                            if (holder.mFragmentClass != null) {
                                showFragment(holder.mTag);
                            }
                        }
                    } else {
                        showFragment(holder.mTag);
                    }

                    if (holder.mTag.equals(mCurrentTag)) {
                        Fragment fragment = getFragmentByTag(holder.mTag);
                        if (fragment != null && fragment instanceof OnTabReselectListener) {
                            ((OnTabReselectListener) fragment).onTabReselect();
                        }
                    }

                }
            }
        });
        mViewHolderList.add(holder);
        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0F));

    }

    FragmentManager mFragmentManager;

    public void init(FragmentManager fragmentManager) {
        if (fragmentManager == null) {
            throw new RuntimeException("fragmentManager Cannot be null");
        }


        if (mMainNavigateTabContainerId == 0) {
            throw new RuntimeException("mFrameLayoutId Cannot be 0");
        }
        if (mViewHolderList.size() == 0) {
            throw new RuntimeException("mViewHolderList.size Cannot be 0, Please call addTab()");
        }
        if (!(getContext() instanceof AppCompatActivity)) {
            throw new RuntimeException("parent activity must is extends AppCompatActivity");
        }

        mFragmentManager = fragmentManager;

        ViewHolder defaultHolder = null;


        if (!TextUtils.isEmpty(mRestoreTag)) {
            for (ViewHolder holder : mViewHolderList) {
                if (TextUtils.equals(mRestoreTag, holder.mTag)) {
                    defaultHolder = holder;
                    mRestoreTag = null;
                    break;
                }
            }
        } else {
            defaultHolder = mViewHolderList.get(mDefaultSelectedTab);
        }

        showFragment(defaultHolder.mTag);
    }


    public void showMsgView(String tag, int num) {
        if (mViewHolderList != null) {
            for (ViewHolder holder : mViewHolderList) {
                if (TextUtils.equals(tag, holder.mTag)) {
                    if (num > 0) {
                        holder.mMsgView.setVisibility(VISIBLE);
                        holder.mMsgView.setText(String.valueOf(num));
                    } else {
                        holder.mMsgView.setVisibility(GONE);
                    }
                }
            }
        }
    }

    public void clearAllMsgView() {
        if (mViewHolderList != null) {
            for (ViewHolder holder : mViewHolderList) {
                holder.mMsgView.setVisibility(GONE);
            }
        }
    }


    /**
     * 更新所有的fragment
     */
    public void updateAllFragment() {
        if (mViewHolderList != null) {
            for (ViewHolder holder : mViewHolderList) {
                Fragment fragment = mFragmentManager.findFragmentByTag(holder.mTag);
                if (fragment != null && fragment instanceof OnRefreshListener) {

                    ((OnRefreshListener) fragment).onRefresh();
                }
            }
        }
    }


    public void updateFragment(String tag) {
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment != null && fragment instanceof OnRefreshListener) {
            ((OnRefreshListener) fragment).onRefresh();
        }
    }


    /**
     * 显示holder对应的fragment
     */
    private void showFragment(String tag) {
        hideAllFragment();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = initFragmentByTag(tag);
            transaction.add(mMainNavigateTabContainerId, fragment, tag);
        } else {

            transaction.show(fragment);
        }
        transaction.commit();
        changeTabStyleByTag(tag);

    }


    public Fragment getFragmentByTag(String tag) {
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            return fragment;
        } else {
            return null;
        }


    }

    /**
     * 设置当前选中tab的图片和文字颜色
     *
     * @param tag
     */
    private void changeTabStyleByTag(String tag) {
        if (TextUtils.equals(mCurrentTag, tag)) {
            return;
        }
        for (ViewHolder holder : mViewHolderList) {
            if (TextUtils.equals(mCurrentTag, holder.mTag)) {
                if (holder.mTabParam.iconResId > 0) {
                    holder.mTabIcon.setImageResource(holder.mTabParam.iconResId);
                }

                holder.mTabTitle.setTextColor(mNormalTextColor);
                holder.mView.setSelected(false);

            } else if (TextUtils.equals(tag, holder.mTag)) {
                if (holder.mTabParam.iconSelectedResId > 0) {
                    holder.mTabIcon.setImageResource(holder.mTabParam.iconSelectedResId);
                }
                holder.mTabTitle.setTextColor(mSelectedTextColor);
                holder.mView.setSelected(true);
            }
        }
        mCurrentTag = tag;
    }


    /**
     * 创建fragment
     *
     * @param tag
     * @return
     */
    private Fragment initFragmentByTag(String tag) {
        Fragment fragment = null;
        for (ViewHolder holder : mViewHolderList) {
            if (TextUtils.equals(tag, holder.mTag)) {
                try {
                    fragment = (Fragment) Class.forName(holder.mFragmentClass.getName()).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return fragment;
    }


    /**
     * 隐藏所有的fragment
     */
    private void hideAllFragment() {
        if (mViewHolderList == null || mViewHolderList.size() == 0) {
            return;
        }


        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (ViewHolder holder : mViewHolderList) {
            Fragment fragment = mFragmentManager.findFragmentByTag(holder.mTag);
            if (fragment != null && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }

    public void setSelectedTabTextColor(ColorStateList selectedTextColor) {
        mSelectedTextColor = selectedTextColor;
    }

    public void setSelectedTabTextColor(int color) {
        mSelectedTextColor = ColorStateList.valueOf(color);
    }

    public void setTabTextColor(ColorStateList color) {
        mNormalTextColor = color;
    }

    public void setTabTextColor(int color) {
        mNormalTextColor = ColorStateList.valueOf(color);
    }

    public void setFrameLayoutId(int frameLayoutId) {
        mMainNavigateTabContainerId = frameLayoutId;
    }


    /**
     * Activity重建时恢复
     *
     * @param savedInstanceState
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRestoreTag = savedInstanceState.getString(KEY_CURRENT_TAG);
        }
    }


    /**
     * Activity销毁是存储
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_CURRENT_TAG, mCurrentTag);
    }

    public static class ViewHolder {
        public String mTag;
        public TabParam mTabParam;
        public ImageView mTabIcon;
        public TextView mTabTitle;
        public Class mFragmentClass;
        public int mTabIndex;
        public MsgView mMsgView;
        public View mView;

    }

    public static class TabParam {
        public int iconResId;
        public int iconSelectedResId;
        public int titleStringRes;
        //        public int tabViewResId;
        public String title;

        public TabParam(int iconResId, int iconSelectedResId, String title) {
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.title = title;
        }

        public TabParam(int iconResId, int iconSelectedResId, int titleStringRes) {
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.titleStringRes = titleStringRes;
        }


    }

    public void setCurrentSelectedTab(String tag) {
        if (tag != null && mViewHolderList != null) {
            for (ViewHolder holder : mViewHolderList) {
                if (holder.mTag.equals(tag)) {
                    showFragment(holder.mTag);
                }
            }
        }

    }


    private OnTabClickListener mTabClickListener;


    public void setTabClickListener(OnTabClickListener onTabClickListener) {
        mTabClickListener = onTabClickListener;
    }

    /**
     * tab点击事件
     */
    public interface OnTabClickListener {
        boolean onTabClick(MainNavigateTabBar.ViewHolder holder);
    }

    /**
     * 刷新fragment监听
     */
    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnTabReselectListener {
        void onTabReselect();
    }

    /**
     * 已经选中状态下点击
     */
    public interface onRepeatClickListener {
        void onRepeatClick();
    }


    private final int[] APPCOMPAT_CHECK_ATTRS = {R.attr.colorPrimary};

    private void checkAppCompatTheme(Context context) {
        TypedArray a = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        final boolean failed = !a.hasValue(0);
        if (a != null) {
            a.recycle();
        }
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme "
                    + "(or descendant) with the design library.");
        }
    }
}
