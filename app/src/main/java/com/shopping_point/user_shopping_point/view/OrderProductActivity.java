package com.shopping_point.user_shopping_point.view;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.OrderingViewModel;
import com.shopping_point.user_shopping_point.databinding.ActivityOrderProductBinding;
import com.shopping_point.user_shopping_point.model.Ordering;
import com.shopping_point.user_shopping_point.model.Product;
import com.shopping_point.user_shopping_point.storage.LoginUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT;


public class OrderProductActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultWithDataListener {

    private ActivityOrderProductBinding binding;
    private OrderingViewModel orderingViewModel;
    private Product product;
    String orderIdString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_product);
        product = getIntent().getParcelableExtra(PRODUCT);
        orderingViewModel = ViewModelProviders.of(this).get(OrderingViewModel.class);

binding.txtProductName.setText(product.getProductName());
        Glide.with(this)
                .load(product.getProductImage())
                .into(binding.imgProductImage);

binding.txtUserName.setText(" Name : " + LoginUtils.getInstance(this).getUserInfo().getUser_name());
binding.txtUserPhone.setText("Contact : " + LoginUtils.getInstance(this).getUserInfo().getUser_contact_number());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(product.getProductPrice());
binding.txtProductPrice.setText(formattedPrice + " ₹ ");
binding.txtProductDesc.setText(product.getProductDesc());

binding.amountAmountPayable.setText(formattedPrice + " ₹ ");
binding.amountDeliveryCharges.setText("FREE");
binding.AmountPrice.setText(formattedPrice + " ₹ ");


        binding.Rating.setText(product.getProductRating() + " ★ ");

        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpPayment();
            }
        });



    }

    private String generateOrderId() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min =1000, max= 9999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
        int randomNum = rand.nextInt((max - min) + 1) + min;
        orderIdString =  date+String.valueOf(randomNum);
        return orderIdString;
    }

    private void setUpPayment() {


        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.payment_logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Shopping Point");
            options.put("description", product.getProductName());
            options.put("image", "http://myleader.sparsematrix.co.in/arshop/backend/vendor/profile_image/mypic.png");
          //  options.put("order_id", generateOrderId());//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", product.getProductPrice()*100);//pass amount in currency subunits
            options.put("prefill.email", LoginUtils.getInstance(this).getEmail());
            options.put("prefill.contact",LoginUtils.getInstance(this).getPhone());
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("ERROR", "Error in starting Razorpay Checkout", e);
        }
    }



    @Override
    public void onPaymentSuccess(String razorpayPaymentId, PaymentData paymentData) {
   String orderid = "OD" + generateOrderId();

        Ordering ordering = new Ordering(orderid,paymentData.getPaymentId(),LoginUtils.getInstance(this).getUserInfo().getId(),product.getProductId());
        orderingViewModel.orderProduct(ordering).observe(this, responseBody -> {
            try {
                Toast.makeText(OrderProductActivity.this, responseBody.string() + "", Toast.LENGTH_SHORT).show();
                finish();
                Intent paymentResultIntent = new Intent(OrderProductActivity.this, PaymentResultActivity.class);
                paymentResultIntent.putExtra(PRODUCT, (product));
                paymentResultIntent.putExtra("paymentStatus", "success");
                paymentResultIntent.putExtra("orderId", orderid );
                startActivity( paymentResultIntent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Intent paymentResultIntent = new Intent(OrderProductActivity.this, PaymentResultActivity.class);

       // paymentResultIntent.putExtra(PRODUCT, (product));

        paymentResultIntent.putExtra("paymentStatus", "failed");
        startActivity( paymentResultIntent);
    }

    @Override
    public void onClick(View v) {

    }
}
