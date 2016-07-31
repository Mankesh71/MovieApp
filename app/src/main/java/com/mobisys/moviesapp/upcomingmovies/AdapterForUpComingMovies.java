package com.mobisys.moviesapp.upcomingmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mobisys.moviesapp.R;
import com.mobisys.moviesapp.utility.ApplicationConstants;
import com.mobisys.moviesapp.utility.OnClickFromAdapter;

import java.util.ArrayList;


/**
 * Created by Mankesh Mishra on 7/28/2016.
 */
public class AdapterForUpComingMovies extends RecyclerView.Adapter<AdapterForUpComingMovies.ViewHolder> {

    private Context mContext;
    private OnClickFromAdapter mOnClickFromAdapter;
    private LayoutInflater layoutInflater;
    private ArrayList<UpComingMoviesModel> mUpComingMoviesArrayList = new ArrayList<>();


    public AdapterForUpComingMovies(Context mContext, ArrayList<UpComingMoviesModel> mUpComingMoviesArrayList,OnClickFromAdapter mOnClickFromAdapter) {
        this.mContext = mContext;
        this.mOnClickFromAdapter = mOnClickFromAdapter;
        this.mUpComingMoviesArrayList = mUpComingMoviesArrayList;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewForSimpleImage;
        TextView tvDate, tvAdult, tvMovieName;
        ProgressBar pbScrollCenter;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            imageViewForSimpleImage = (ImageView) itemView.findViewById(R.id.ivForSimpleImage);
            tvAdult = (TextView) itemView.findViewById(R.id.tvAdult);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvMovieName = (TextView) itemView.findViewById(R.id.tvMovieName);
            pbScrollCenter = (ProgressBar) itemView.findViewById(R.id.pbScrollCenter);
        }

        @Override
        public void onClick(View v) {
            mOnClickFromAdapter.onClick(getPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterForUpComingMovies.ViewHolder holder, int position) {
        if (mUpComingMoviesArrayList.get(position).getStrPosterPath() != null && !"".equals(mUpComingMoviesArrayList.get(position).getStrPosterPath())) {
            holder.pbScrollCenter.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(ApplicationConstants.IMAGEAPI+mUpComingMoviesArrayList.get(position).getStrPosterPath())
                    .asBitmap()
                    .override(70, 70)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .skipMemoryCache(false)
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            holder.pbScrollCenter.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.pbScrollCenter.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.imageViewForSimpleImage);
        } else {
            holder.pbScrollCenter.setVisibility(View.GONE);
            holder.imageViewForSimpleImage.setImageResource(R.drawable.icon_noimage);
        }

        holder.tvDate.setText(mUpComingMoviesArrayList.get(position).getStrReleaseDate());
        if (mUpComingMoviesArrayList.get(position).isAdult()) {
            holder.tvAdult.setText(mContext.getResources().getString(R.string.adult));
        }else{
            holder.tvAdult.setText(mContext.getResources().getString(R.string.no_adult));
        }
        holder.tvMovieName.setText(mUpComingMoviesArrayList.get(position).getStrTitle());

    }

    public void notifyAdapter(ArrayList<UpComingMoviesModel> mArrayListMovies){
        this.mUpComingMoviesArrayList = mArrayListMovies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mUpComingMoviesArrayList.size();
    }

}
