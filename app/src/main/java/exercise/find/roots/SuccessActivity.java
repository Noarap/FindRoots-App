package exercise.find.roots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SuccessActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Intent intent = getIntent();
        String numOrigStr = "The original number is:\n\n" + intent.getLongExtra("original_number", 0);
        String rootStr1 = "Root1:\n\n" + intent.getLongExtra("root1", 0);
        String rootStr2 = "Root2:\n\n" + intent.getLongExtra("root2", 0);
        String numSecStr = "Total calculations seconds: " + intent.getLongExtra("time_until_give_up_seconds", 0);

        TextView numOrigText = findViewById(R.id.numOrig);
        TextView rootText1 = findViewById(R.id.root1);
        TextView rootText2 = findViewById(R.id.root2);
        TextView numSecondsText = findViewById(R.id.numSeconds);

        numOrigText.setText(numOrigStr);
        rootText1.setText(rootStr1);
        rootText2.setText(rootStr2);
        numSecondsText.setText(numSecStr);

    }
}
