package cf.parks.a1note.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cf.parks.a1note.R;
import cf.parks.a1note.dbdb.LogPassDAO;

public class SetPasswordActivity extends Activity {
    @BindView(R.id.set_pass_pass) EditText _set_pass;
    @BindView(R.id.set_pass_repass) EditText _set_re_pass;
    @BindView(R.id.set_pass_btn) Button _set_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_password);

        ButterKnife.bind(this);
        _set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateSet()) {
                    String set_pass = _set_pass.getText().toString();
                    LogPassDAO dao = new LogPassDAO(SetPasswordActivity.this);
                    dao.setPassword(set_pass);
                    dao.close();
                    startActivity(new Intent(SetPasswordActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }
    private boolean validateSet() {
        boolean valid = true;
        String set_pass = _set_pass.getText().toString();
        String set_repass = _set_re_pass.getText().toString();

        if (set_pass.isEmpty() || set_pass.length() < 4) {
            _set_pass.setError("should have more than 3 characters");
            valid = false;
        } else {
            _set_pass.setError(null);
        }

        if (set_repass.isEmpty() || set_repass.length() < 4) {
            _set_re_pass.setError("should have more than 3 characters");
            valid = false;
        } else if(!set_pass.equals(set_repass)) {
            _set_re_pass.setError("password didn't match");
            valid = false;
        } else {
            _set_re_pass.setError(null);
        }
        return valid;
    }
}
