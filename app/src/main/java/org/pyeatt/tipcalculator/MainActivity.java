package org.pyeatt.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
    private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double customPercent = 0.18;
    private TextView amountDisplayTextView;
    private TextView percentCustomTextView;
    private TextView tip15TextView;
    private TextView total15TextView;
    private TextView tipCustomTextView;
    private TextView totalCustomTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);

        // amountDisplayTextView.setText(CURRENCY_FORMAT.format(billAmount));
        updateStandard();
        updateCustom();

        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }

    private void updateStandard() {
        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;
        tip15TextView.setText(CURRENCY_FORMAT.format(fifteenPercentTip));
        total15TextView.setText(CURRENCY_FORMAT.format(fifteenPercentTotal));
    }
    private void updateCustom() {
        percentCustomTextView.setText(PERCENT_FORMAT.format(customPercent));
        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;
        tipCustomTextView.setText(CURRENCY_FORMAT.format(customTip));
        totalCustomTextView.setText(CURRENCY_FORMAT.format(customTotal));
    }

    private SeekBar.OnSeekBarChangeListener customSeekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
              @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                  customPercent = progress / 100.0;
                  updateCustom();
              }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
            } catch (NumberFormatException e) {
                billAmount = 0.0;
            }
            // amountDisplayTextView.setText(CURRENCY_FORMAT.format(billAmount));
            updateStandard();
            updateCustom();

        }
        @Override
        public void afterTextChanged(Editable e) {

        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    };
}
