package messenger.hfad.com.fragment070518;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvLanguage);
        editText = findViewById(R.id.etName);
    }


    public void onSelectName(View view) {
        String name = editText.getText().toString();
        Intent intent = new Intent(this,LanguageActivity.class);
        intent.putExtra(Name.NAMEONE, name);
        startActivityForResult(intent,RequestCodeMy.ONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case RequestCodeMy.ONE:

                    String language = data.getStringExtra(Name.NAMETWO);
                    textView.setText(language);

            }


        }
    }
}
