package phuhq.it.coffeeshoporder.A1_Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import phuhq.it.coffeeshoporder.A2_Login.View.A2_Login_Pass;
import phuhq.it.coffeeshoporder.R;

public class A1_Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1_intro);
        // full screen
        View view = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);
        // time count down
        countDownTimeShow();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    //region COUNT DOWN
    public void countDownTimeShow() {
        new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(A1_Intro.this, A2_Login_Pass.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    //endregion
}
