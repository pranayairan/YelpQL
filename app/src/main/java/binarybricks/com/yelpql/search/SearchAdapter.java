package binarybricks.com.yelpql.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import binarybricks.com.yelpql.R;
import binarybricks.com.yelpql.network.model.Business;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pairan on 7/25/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchRowVh> {

    private List<Business> businessList;
    private Context context;

    public SearchAdapter(List<Business> businessList, Context context) {
        this.businessList = businessList;
        this.context = context;
    }

    @Override
    public SearchRowVh onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_row, null);
        SearchRowVh viewHolder = new SearchRowVh(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchRowVh holder, int position) {
        Business business = businessList.get(position);

        Picasso.with(context).load(business.getPhotos().get(0)).into(holder.ivRestaurant);
        holder.tvFoodType.setText(business.getCategories().get(0));
        holder.tvRestaurantName.setText(business.getName());
        holder.tvCost.setText(business.getPrice());
        holder.tvDistance.setText(business.getDistanceFromCurrent());
        showRatingLogo(holder.ivRating, business.getRating());

    }

    private void showRatingLogo(ImageView ratings, String rating) {
        switch (rating) {

            case "0":
                ratings.setImageResource(R.drawable.stars_regular_0);
                break;
            case "1.0":
                ratings.setImageResource(R.drawable.stars_regular_1);
                break;
            case "1.5":
                ratings.setImageResource(R.drawable.stars_regular_1_half);
                break;
            case "2.0":
                ratings.setImageResource(R.drawable.stars_regular_2);
                break;
            case "2.5":
                ratings.setImageResource(R.drawable.stars_regular_2_half);
                break;
            case "3.0":
                ratings.setImageResource(R.drawable.stars_regular_3);
                break;
            case "3.5":
                ratings.setImageResource(R.drawable.stars_regular_3_half);
                break;
            case "4.0":
                ratings.setImageResource(R.drawable.stars_regular_4);
                break;
            case "4.5":
                ratings.setImageResource(R.drawable.stars_regular_4_half);
                break;
            case "5.0":
                ratings.setImageResource(R.drawable.stars_regular_5);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (null != businessList ? businessList.size() : 0);
    }

    class SearchRowVh extends RecyclerView.ViewHolder {

        @BindView(R.id.ivRestaurant)
        ImageView ivRestaurant;
        @BindView(R.id.ivRating)
        ImageView ivRating;
        @BindView(R.id.tvRestaurantName)
        TextView tvRestaurantName;
        @BindView(R.id.tvFoodType)
        TextView tvFoodType;
        @BindView(R.id.tvDistance)
        TextView tvDistance;
        @BindView(R.id.tvCost)
        TextView tvCost;

        public SearchRowVh(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addBusinessList(List<Business> businessList) {
        this.businessList.addAll(businessList);
    }
}
