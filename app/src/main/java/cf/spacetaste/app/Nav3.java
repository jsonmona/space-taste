package cf.spacetaste.app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Nav3 extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter = null;
    Spinner spinner = null;
    private RatingBar ratingbar1,ratingbar2,ratingbar3,ratingbar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav3);

        adapter = ArrayAdapter.createFromResource(this, R.array.nav3Cate, android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        ratingbar1 = findViewById(R.id.rate1);
        ratingbar2 = findViewById(R.id.rate2);
        ratingbar3 = findViewById(R.id.rate3);
        ratingbar4 = findViewById(R.id.rate4);

        ratingbar1.setOnRatingBarChangeListener(new Listener());
        ratingbar2.setOnRatingBarChangeListener(new Listener());
        ratingbar3.setOnRatingBarChangeListener(new Listener());
        ratingbar4.setOnRatingBarChangeListener(new Listener());
    }
}

class Listener implements RatingBar.OnRatingBarChangeListener
{
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        ratingBar.setRating(rating);
    }
}

