package messenger.hfad.com.fragment070518;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class LanguageActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        textView = findViewById(R.id.tvName);
        Intent intent = getIntent();
        String nameFrom = intent.getStringExtra(Name.NAMEONE);
        textView.setText("Здравствуйте," + nameFrom);
    }

    public void onLanguage(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.btnEnglish:
                intent.putExtra(Name.NAMETWO, "English");
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.btnRussian:
                intent.putExtra(Name.NAMETWO, "Russian");
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.btnGerman:
                intent.putExtra(Name.NAMETWO, "German");
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
