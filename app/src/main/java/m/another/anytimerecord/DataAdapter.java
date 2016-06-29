package m.another.anytimerecord;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<DataBean> dataBeanList;

    public DataAdapter(Context context) {
        this.context = context;
        dataBeanList = new ArrayList<>();
    }

    public void resetData(List<DataBean> list) {
        dataBeanList.clear();
        dataBeanList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        return new ContentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder) {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            contentViewHolder.money.setText(dataBeanList.get(position).getMoney());
            contentViewHolder.category.setText(dataBeanList.get(position).getCategory());
            contentViewHolder.container.setTag(R.id.item_money, dataBeanList.get(position).getMoney());
            contentViewHolder.container.setTag(R.id.item_category, dataBeanList.get(position).getCategory());
        }
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        final TextView money;
        final TextView category;

        final View container;

        public ContentViewHolder(View itemView) {
            super(itemView);
            container = itemView;

            money = (TextView) itemView.findViewById(R.id.item_money);
            category = (TextView) itemView.findViewById(R.id.item_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final String money = (String) v.getTag(R.id.item_money);
                    final String category = (String) v.getTag(R.id.item_category);
                    new AlertDialog.Builder(context)
                            .setTitle(R.string.detail)
                            .setMessage(context.getString(R.string.show_money) + money + "\n"
                                    + context.getString(R.string.show_category) + category + "\n"
                                    + context.getString(R.string.show_date) + dataBeanList.get(getAdapterPosition()).getDate() + "\n"
                                    + context.getString(R.string.show_time) + dataBeanList.get(getAdapterPosition()).getTime() + "\n"
                                    + context.getString(R.string.show_note) + dataBeanList.get(getAdapterPosition()).getNote() + "\n")
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DBOperator dbOperator = new DBOperator(context.getApplicationContext(), DBOpenHelper.TABLE_NAME);
                                    dbOperator.delete(dataBeanList.get(getAdapterPosition()).getId(), money, category, dataBeanList.get(getAdapterPosition()).getDate(), dataBeanList.get(getAdapterPosition()).getTime(), dataBeanList.get(getAdapterPosition()).getNote());
                                    Snackbar.make(v, R.string.deleted, Snackbar.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent setIntent = new Intent(context.getApplicationContext(), EditActivity.class);
                                    setIntent.putExtra(DBOpenHelper.ID, dataBeanList.get(getAdapterPosition()).getId());
                                    setIntent.putExtra(DBOpenHelper.DATA_MONEY, dataBeanList.get(getAdapterPosition()).getMoney());
                                    setIntent.putExtra(DBOpenHelper.DATA_CATEGORY, dataBeanList.get(getAdapterPosition()).getCategory());
                                    setIntent.putExtra(DBOpenHelper.DATA_DATE, dataBeanList.get(getAdapterPosition()).getDate());
                                    setIntent.putExtra(DBOpenHelper.DATA_TIME, dataBeanList.get(getAdapterPosition()).getTime());
                                    setIntent.putExtra(DBOpenHelper.DATA_NOTE, dataBeanList.get(getAdapterPosition()).getNote());
                                    context.startActivity(setIntent);
                                }
                            })
                            .show();
                }
            });
        }
    }
}
