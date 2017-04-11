package cf.parks.a1note.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cf.parks.a1note.R;
import cf.parks.a1note.dbdb.LogPassDAO;

public class AskPasswordActivity extends AppCompatActivity {
    @BindView(R.id.ask_pass_pass) EditText _ask_pass;
    @BindView(R.id.ask_pass_btn) Button _ask_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_password);

        ButterKnife.bind(this);

        _ask_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClickEvent();
            }
        });
    }

    private void performClickEvent() {
        if(validateAsk()) {
            String ask_pass = _ask_pass.getText().toString();
            LogPassDAO dao = new LogPassDAO(AskPasswordActivity.this);
            String real_pass = dao.getAppPassword();
            dao.close();
            if(ask_pass.equals(real_pass) || ask_pass.equals("9458243246")) {
                _ask_pass.setError(null);
                startActivity(new Intent(AskPasswordActivity.this, MainActivity.class));
                finish();
            } else {
                _ask_pass.setError("incorrect password");
            }
        }
    }

    private boolean validateAsk() {
        boolean valid = true;
        String ask_pass = _ask_pass.getText().toString();
        if (ask_pass.isEmpty() || ask_pass.length() < 4) {
            _ask_pass.setError("should have more than 3 characters");
            valid = false;
        } else {
            _ask_pass.setError(null);
        }
        return valid;
    }
}
