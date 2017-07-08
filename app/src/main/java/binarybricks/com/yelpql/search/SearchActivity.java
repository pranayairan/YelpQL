package binarybricks.com.yelpql.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import binarybricks.com.yelpql.R;
import binarybricks.com.yelpql.utils.AuthenticationTokenUtil;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String authenticationToken = AuthenticationTokenUtil.fetchAndUpdateAuthenticationToken(this);

        if (TextUtils.isEmpty(authenticationToken)) {
            Toast.makeText(this, "No authentication token found, fetching token,please restart the app", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "authtokens " + authenticationToken, Toast.LENGTH_LONG).show();
        }
    }
}
