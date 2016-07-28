package harry.com.pullrefresh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import harry.com.pullrefresh.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.btnPullToRefresh,R.id.btnPullToRefreshCustom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPullToRefresh:
                startActivity(new Intent(MainActivity.this, PullToRefreshActivity.class));
                break;
            case R.id.btnPullToRefreshCustom:
                startActivity(new Intent(MainActivity.this, PullToRefreshCustomActivity.class));
                break;
        }
    }
}
