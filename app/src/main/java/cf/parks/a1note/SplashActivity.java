package cf.parks.a1note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cf.parks.a1note.activities.AskPasswordActivity;
import cf.parks.a1note.activities.SetPasswordActivity;
import cf.parks.a1note.dbdb.LogPassDAO;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LogPassDAO logPassDAO = new LogPassDAO(SplashActivity.this);
        String pass = logPassDAO.getAppPassword();
        logPassDAO.close();
        if (pass == null) startActivity(new Intent(SplashActivity.this, SetPasswordActivity.class));
        else startActivity(new Intent(SplashActivity.this, AskPasswordActivity.class));
        finish();
    }
}
