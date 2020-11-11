package com.shopping_point.user_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.databinding.ActivityPaymentResultBinding;
import com.shopping_point.user_shopping_point.model.Product;
import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT;
public class PaymentResultActivity extends AppCompatActivity {
private ActivityPaymentResultBinding binding;

Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_result);
        Bundle bundle = getIntent().getExtras();
        product = getIntent().getParcelableExtra(PRODUCT);

        if( bundle.getString("paymentStatus").equals("success"))
        {

            binding.imgPaymentResult.setImageResource(R.drawable.ic_payment_sucess);
           binding.paymentStatus.setText("Payment Successful");
            binding.txvPaymentOrderId.setVisibility(View.GONE);
            binding.txvPaymentAmmount.setText("Product Price : " + product.getProductPrice() + " ₹ ");
            binding.txvPaymentProductName.setText("Product Name : " + product.getProductName());
        }else
        {
           binding.imgPaymentResult.setImageResource(R.drawable.ic_payment_failed);
            binding.paymentStatus.setText("Payment Failed");
            binding.txvPaymentOrderId.setVisibility(View.GONE);
            binding.txvPaymentAmmount.setVisibility(View.GONE);
            binding.txvPaymentProductName.setVisibility(View.GONE);

 }

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(PaymentResultActivity.this, ProductActivity.class);
                startActivity(homeIntent);
            }
        });



    }
}