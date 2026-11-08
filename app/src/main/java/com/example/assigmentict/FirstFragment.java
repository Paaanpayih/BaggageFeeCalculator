package com.example.assigmentict;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.assigmentict.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCalculate.setOnClickListener(v -> calculateFee());
        binding.btnClear.setOnClickListener(v -> clearFields());
    }

    private void calculateFee() {
        String weightStr = String.valueOf(binding.etWeight.getText());
        String rateStr = String.valueOf(binding.etRate.getText());

        if (weightStr.isEmpty() || rateStr.isEmpty()) {
            Snackbar.make(binding.getRoot(), R.string.error_empty_fields, Snackbar.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double rate = Double.parseDouble(rateStr);

            double allowedWeight = binding.rbEconomy.isChecked() ? 20.0 : 40.0;

            double excessWeight = weight - allowedWeight;
            if (excessWeight < 0) {
                excessWeight = 0;
            }

            double charge = excessWeight * rate;
            double total = charge + (charge * 0.08);

            binding.tvResultWeight.setText(getString(R.string.result_excess_weight, excessWeight));
            binding.tvResultCharge.setText(getString(R.string.result_charge, charge));
            
            // Concatenate the string manually to avoid String.format throwing an error due to the "%" in "8%"
            String totalStr = String.format(java.util.Locale.getDefault(), "%.2f", total);
            binding.tvResultTotal.setText(getString(R.string.result_total_prefix) + totalStr);

        } catch (NumberFormatException e) {
            Snackbar.make(binding.getRoot(), R.string.error_invalid_input, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        binding.etWeight.setText("");
        binding.etRate.setText("");
        binding.rbEconomy.setChecked(true);
        binding.tvResultWeight.setText(getString(R.string.result_excess_weight_default));
        binding.tvResultCharge.setText(getString(R.string.result_charge_default));
        binding.tvResultTotal.setText(getString(R.string.result_total_default));
        binding.etWeight.requestFocus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}