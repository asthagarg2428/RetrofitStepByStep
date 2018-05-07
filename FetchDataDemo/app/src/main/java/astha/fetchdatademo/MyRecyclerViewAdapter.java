package astha.fetchdatademo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import astha.fetchdatademo.model.UserModel;

/**
 * Created by root on 7/5/18.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private UserModel[] userModels = null;
    private Context context = null;

    MyRecyclerViewAdapter(UserModel[] userArray, Context context)
    {
        this.context = context;
        userModels = userArray;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.users_row_item,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(userModels != null)
        {
            UserModel userModel = userModels[position];
            if(userModel != null)
            {
                holder.name.setText(userModel.getFirst_name() + " " +userModel.getLast_name());
                Glide.with(context).load(userModel.getAvatar()).into(holder.avatar);
            }
        }
    }

    @Override
    public int getItemCount() {
        return userModels.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView avatar;
        TextView name;
        MyViewHolder(View view)
        {
            super(view);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            name = (TextView) view.findViewById(R.id.name);
        }
    }
}
