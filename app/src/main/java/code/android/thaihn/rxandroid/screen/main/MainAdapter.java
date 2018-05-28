package code.android.thaihn.rxandroid.screen.main;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import code.android.thaihn.rxandroid.R;
import code.android.thaihn.rxandroid.data.model.Collection;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ItemViewHolder> {

    private List<Collection> mCollections;
    private MainAdapter.OnClickListenerSong mOnClickListenerSong;

    public MainAdapter(List<Collection> songs) {
        this.mCollections = songs;
    }

    public void setOnClickListenerSong(MainAdapter.OnClickListenerSong onClickListenerSong) {
        mOnClickListenerSong = onClickListenerSong;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        return new ItemViewHolder(layoutItem, mOnClickListenerSong);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        holder.setData(mCollections.get(position));
    }

    @Override
    public int getItemCount() {
        return mCollections != null ? mCollections.size() : 0;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mAvatarSong, mIconMore;
        private TextView mTextNameSong, mTextSingerSong;
        private MainAdapter.OnClickListenerSong mClickListenerSong;
        private ConstraintLayout itemView;

        ItemViewHolder(View rootView, MainAdapter.OnClickListenerSong listener) {
            super(rootView);
            mAvatarSong = rootView.findViewById(R.id.image_avatar);
            mTextNameSong = rootView.findViewById(R.id.text_name);
            mTextSingerSong = rootView.findViewById(R.id.text_singer);
            mIconMore = rootView.findViewById(R.id.image_icon_more);
            itemView = rootView.findViewById(R.id.itemView);
            mClickListenerSong = listener;
            mIconMore.setOnClickListener(this);
            itemView.setOnClickListener(this);
            mTextNameSong.setSelected(true);
        }

        public void setData(Collection collection) {
            if (collection != null) {
                String url = collection.getTrack().getArtworkUrl() == null ?
                        collection.getTrack().getUser().getAvatarUrl() :
                        collection.getTrack().getArtworkUrl();
                Glide.with(mAvatarSong.getContext())
                        .load(url)
                        .into(mAvatarSong);
                mTextNameSong.setText(collection.getTrack().getTitle());
                mTextSingerSong.setText(collection.getTrack().getUser().getFullName());
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_icon_more: {
                    mClickListenerSong.onMoreClicked(getAdapterPosition());
                    break;
                }
                case R.id.itemView: {
                    mClickListenerSong.onItemClicked(getAdapterPosition());
                    break;
                }
            }
        }
    }


    public void setCollections(List<Collection> collections) {
        if (collections == null) return;
        mCollections = collections;
    }

    public List<Collection> getCollections() {
        return mCollections;
    }

    public void addData(List<Collection> list) {
        mCollections.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        mCollections.clear();
        notifyDataSetChanged();
    }

    public interface OnClickListenerSong {
        void onMoreClicked(int position);

        void onItemClicked(int position);
    }
}
