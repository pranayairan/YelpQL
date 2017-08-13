package binarybricks.com.yelpql.search;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import binarybricks.com.yelpql.R;
import binarybricks.com.yelpql.details.BusinessDetailsActivity;
import binarybricks.com.yelpql.network.SearchAPI;
import binarybricks.com.yelpql.network.model.Business;
import binarybricks.com.yelpql.utils.AuthenticationTokenUtil;
import binarybricks.com.yelpql.utils.EndlessRecyclerViewScrollListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.OnListItemClicked {

    @BindView(R.id.rvNearbyRestaurant)
    RecyclerView rvNearbyRestaurant;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    private SearchAdapter searchAdapter;

    private RxPermissions rxPermissions;
    private RxLocation rxLocation;
    private Disposable subscribe;

    private int offset = 0;

    private Location lastKnownLocation;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private String searchTerm = "Restaurant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);
        rxLocation = new RxLocation(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvNearbyRestaurant.setLayoutManager(linearLayoutManager);

        // get users last known location
        getLastKnownLocation(searchTerm);

        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                offset = totalItemsCount;
                loadData(searchTerm, lastKnownLocation, offset);
            }
        };

        rvNearbyRestaurant.addOnScrollListener(endlessRecyclerViewScrollListener);
    }

    // ask for permission before querying the location api
    private void getLastKnownLocation(final String searchTerm) {
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @SuppressWarnings("MissingPermission")
                    @Override
                    public void accept(@NonNull Boolean locationPermissionGranted) throws Exception {
                        // if location permission is granted query the api
                        if (locationPermissionGranted) {
                            rxLocation.location().lastLocation()
                                    .doOnSuccess(new Consumer<Location>() {
                                        @Override
                                        public void accept(@NonNull Location location) throws Exception {
                                            lastKnownLocation = location;
                                            loadData(searchTerm, location, offset);
                                        }
                                    })
                                    .doOnComplete(new Action() {
                                        @Override
                                        public void run() throws Exception {
                                            // if last location is null try to get latest location
                                            updateLocationInRealTime(searchTerm);
                                        }
                                    }).subscribe();
                        } else {
                            Toast.makeText(SearchActivity.this, "Error, we need location permission to work", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @SuppressWarnings("MissingPermission")
    private void updateLocationInRealTime(final String searchTerm) {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000);

        subscribe = rxLocation.location().updates(locationRequest).subscribe(new Consumer<Location>() {
            @Override
            public void accept(@NonNull Location location) throws Exception {
                lastKnownLocation = location;
                loadData(searchTerm, location, offset);
            }
        });

    }

    private void loadData(final String searchTerm, final Location location, final int offsetValue) {

        progressbar.setVisibility(View.VISIBLE);

        // get the authentication token and find results
        AuthenticationTokenUtil.fetchAndUpdateAuthenticationToken(this)
                .flatMap(new Function<String, SingleSource<List<Business>>>() {
                    @Override
                    public SingleSource<List<Business>> apply(@NonNull String authenticationToken) throws Exception {
                        return SearchAPI.searchYelp(authenticationToken, searchTerm, location.getLatitude(), location.getLongitude(), offsetValue);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Business>>() {
                               @Override
                               public void accept(@NonNull List<Business> businessList) throws Exception {
                                   progressbar.setVisibility(View.GONE);
                                   if (offsetValue == 0) {
                                       searchAdapter = new SearchAdapter(businessList, SearchActivity.this, SearchActivity.this);
                                       rvNearbyRestaurant.setAdapter(searchAdapter);
                                   } else {
                                       searchAdapter.addBusinessList(businessList);
                                       searchAdapter.notifyDataSetChanged();
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                progressbar.setVisibility(View.GONE);
                                Toast.makeText(SearchActivity.this, "Error " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    @Override
    public void showBusinessDetails(Business business) {
        Intent businessDetailsIntent = BusinessDetailsActivity.getBusinessDetailsIntent(this, business.getId(),
                lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        startActivity(businessDetailsIntent);
    }

    @OnEditorAction(R.id.etSearch)
    boolean onSearchClicked(EditText v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            offset = 0;
            searchTerm = v.getText().toString();
            getLastKnownLocation(searchTerm);
            hideKeyboard();
            return true;
        }
        return false;
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
