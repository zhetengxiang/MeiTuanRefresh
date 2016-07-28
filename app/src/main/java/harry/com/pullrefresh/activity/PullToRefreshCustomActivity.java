package harry.com.pullrefresh.activity;

import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harry.com.pullrefresh.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * author zhetengxiang
 * Date 2016/7/27
 */

public class PullToRefreshCustomActivity extends AppCompatActivity {
    private static final String TAG = PullToRefreshCustomActivity.class.getSimpleName();
    @BindView(R.id.tvDrag)
    TextView tvDrag;
    @BindView(R.id.ptrFrame)
    PtrFrameLayout ptrFrame;
    @BindView(R.id.ivPic0)
    ImageView ivPic0;
    @BindView(R.id.ivPic1)
    ImageView ivPic1;
    @BindView(R.id.seekBar)
    SeekBar seekBar;

    private Matrix mMatrix = new Matrix();
    private AnimationDrawable mSecondAnimation;
    private AnimationDrawable mThirdAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh_custom);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAnimations();
    }

    private void initView() {
        scaleImage(0.0f);
    }
    private void initEvent() {
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.refreshComplete();
                    }
                }, 2000);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = progress / 100.0f;
                scaleImage(scale);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void scaleImage(float scale) {
        mMatrix.setScale(scale, scale, ivPic0.getWidth() / 2, ivPic0.getHeight() / 2);
        // 这个会叠加缩放，例如之前0.1再滑动0.2 滑动之后会变成0.1x0.2越来越小
        // mMatrix.postScale(scale,scale,ivPic0.getWidth()/2,ivPic0.getHeight()/2)
        ivPic0.setImageMatrix(mMatrix);
    }

    @OnClick({R.id.btnSecond, R.id.btnThrid})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSecond:
                cancelAnimationThird();
                ivPic1.setImageResource(R.drawable.animation_refresh_second);
                mSecondAnimation = (AnimationDrawable) ivPic1.getDrawable();
                mSecondAnimation.start();
                break;
            case R.id.btnThrid:
                cancelAnimationSecond();
                ivPic1.setImageResource(R.drawable.animation_refresh_third);
                mThirdAnimation = (AnimationDrawable) ivPic1.getDrawable();
                mThirdAnimation.start();
                break;
        }
    }

    private void cancelAnimationSecond() {
        if (mSecondAnimation != null && mSecondAnimation.isRunning()) {
            mSecondAnimation.stop();
        }
    }

    private void cancelAnimationThird() {
        if (mThirdAnimation != null && mThirdAnimation.isRunning()) {
            mThirdAnimation.stop();
        }
    }

    private void cancelAnimations() {
        cancelAnimationSecond();
        cancelAnimationThird();
    }
}
