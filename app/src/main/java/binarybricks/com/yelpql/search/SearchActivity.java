package binarybricks.com.yelpql.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.rx2.Rx2Apollo;

import java.util.List;

import binarybricks.com.yelpql.R;
import binarybricks.com.yelpql.network.GraphQLClient;
import binarybricks.com.yelpql.utils.AuthenticationTokenUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yelp.SearchYelp;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.rvNearbyRestaurant)
    RecyclerView rvNearbyRestaurant;

    private SearchAdapter searchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        String authenticationToken = AuthenticationTokenUtil.fetchAndUpdateAuthenticationToken(this);

        rvNearbyRestaurant.setLayoutManager(new LinearLayoutManager(this));
        //rvNearbyRestaurant.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        if (TextUtils.isEmpty(authenticationToken)) {
            Toast.makeText(this, "No authentication token found, fetching token,please restart the app", Toast.LENGTH_LONG).show();
        } else {
            ApolloClient apolloClient = GraphQLClient.getApolloClient(authenticationToken);

            SearchYelp searchYelp = SearchYelp.builder()
                    .location("San Francisco")
                    .radius(10000d)
                    .build();

            ApolloQueryCall<SearchYelp.Data> query = apolloClient.query(searchYelp);

            Rx2Apollo.from(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<SearchYelp.Data>>() {
                        @Override
                        public void accept(@NonNull Response<SearchYelp.Data> dataResponse) throws Exception {
                            List<SearchYelp.Business> business = dataResponse.data().search().business();
                            searchAdapter = new SearchAdapter(business, SearchActivity.this);
                            rvNearbyRestaurant.setAdapter(searchAdapter);
                        }
                    });
        }
    }
}
