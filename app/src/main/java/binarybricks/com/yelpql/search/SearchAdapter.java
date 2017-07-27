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
import butterknife.BindView;
import butterknife.ButterKnife;
import yelp.SearchYelp;

/**
 * Created by pairan on 7/25/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchRowVh> {

    private List<SearchYelp.Business> businessList;
    private Context context;

    public SearchAdapter(List<SearchYelp.Business> businessList, Context context) {
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
        SearchYelp.Business business = businessList.get(position);

        Picasso.with(context).load(business.photos().get(0)).into(holder.ivRestaurant);
        holder.tvFoodType.setText(business.categories().get(0).title());
        holder.tvRestaurantName.setText(business.name());
        holder.tvCost.setText(business.price());
        showRatingLogo(holder.ivRating, business.rating());

    }

    private void showRatingLogo(ImageView ratings, Double rating) {
        switch (String.valueOf(rating)) {

            case "0":
                ratings.setImageResource(R.drawable.stars_regular_0);
                break;
            case "1.0":
                ratings.setImageResource(R.drawable.stars_regular_1);
                break;
            case "1.5":
                ratings.setImageResource(R.drawable.stars_regular_1_half);
                break;
            case "2":
                ratings.setImageResource(R.drawable.stars_regular_2);
                break;
            case "2.5":
                ratings.setImageResource(R.drawable.stars_regular_2_half);
                break;
            case "3":
                ratings.setImageResource(R.drawable.stars_regular_3);
                break;
            case "3.5":
                ratings.setImageResource(R.drawable.stars_regular_3_half);
                break;
            case "4":
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
}
