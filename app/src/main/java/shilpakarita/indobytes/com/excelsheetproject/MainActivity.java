package shilpakarita.indobytes.com.excelsheetproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import shilpakarita.indobytes.com.excelsheetproject.Helper.WebServiceCallBack;
import shilpakarita.indobytes.com.excelsheetproject.Helper.WebserviceHelper;

public class MainActivity extends AppCompatActivity implements WebServiceCallBack {
    RecyclerView rv_task_sheet;
    ArrayList<Taskmodel> main_array_list;
    Dialog mDeails_list_dialog;
    ImageView image_dialog_id;
    SwipeRefreshLayout swipe_fresh_id;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.cancel();
        progressDialog.setMessage("Loading...");
        new WebserviceHelper(this).getData(getString(R.string.base_url) + "11zGzJADZmPA-t_7BwVMN5jnyHO0n1k977JyZpeTY4PE/od6/public/values?alt=json", "raj", this);
        progressDialog.show();
        rv_task_sheet = (RecyclerView) findViewById(R.id.rv_task_sheet);

        mDeails_list_dialog = new Dialog(this);
        mDeails_list_dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDeails_list_dialog.setContentView(R.layout.image_view_dialog);
        image_dialog_id = (ImageView) mDeails_list_dialog.findViewById(R.id.image_dialog_id);
        swipe_fresh_id = (SwipeRefreshLayout) findViewById(R.id.swipe_fresh_id);

        swipe_fresh_id.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
                swipe_fresh_id.setRefreshing(true);
            }
        });

       /* CircleProgressbar circleProgressbar = (CircleProgressbar)findViewById(R.id.circle_pbar);
        circleProgressbar.setForegroundProgressColor(Color.RED);
        circleProgressbar.setBackgroundColor(Color.GREEN);
        circleProgressbar.setBackgroundProgressWidth(15);
        circleProgressbar.setForegroundProgressWidth(20);
        circleProgressbar.enabledTouch(true);
        circleProgressbar.setRoundedCorner(true);
        circleProgressbar.setClockwise(true);
        int animationDuration = 2500; // 2500ms = 2,5s
        circleProgressbar.setProgressWithAnimation(65, animationDuration);*/

    }


    @Override
    public void onJSONResponse(String jsonResponse, String type) throws IOException {

        switch (type) {
            case "raj":
                Log.e("Family_response", jsonResponse);
                progressDialog.dismiss();
                swipe_fresh_id.setRefreshing(false);
                try {
                    JSONObject jsonObject_update = new JSONObject(jsonResponse);
                    JSONObject jsonObject = jsonObject_update.getJSONObject("feed");
                    JSONArray jsonArray = jsonObject.getJSONArray("entry");
                    Log.e("json_array_lll", jsonArray.length() + "");

                    main_array_list = new ArrayList<>();
                    main_array_list.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsondata = jsonArray.getJSONObject(i);

                        JSONObject main_data_json = jsondata.getJSONObject("gsx$date");
                        String date_string = main_data_json.getString("$t");

                        JSONObject main_data_json_projectname = jsondata.getJSONObject("gsx$projectname");
                        String project_name_string = main_data_json_projectname.getString("$t");

                        JSONObject main_data_json_task = jsondata.getJSONObject("gsx$task");
                        String task_string = main_data_json_task.getString("$t");

                        JSONObject main_data_comment = jsondata.getJSONObject("gsx$comments");
                        String comment_string = main_data_comment.getString("$t");

                        Log.e("JSONDATA_ll_ll", date_string + "\n" + project_name_string + "\n" + task_string + "\n" + comment_string);
                        Taskmodel taskmodel = new Taskmodel(project_name_string, task_string, date_string, comment_string);
                        main_array_list.add(taskmodel);
                    }

                    rv_task_sheet.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    rv_task_sheet.setLayoutManager(layoutManager);
                    rv_task_sheet.setItemAnimator(new DefaultItemAnimator());
                    ProcessingAdapter details_adapter = new ProcessingAdapter(this, main_array_list);
                    rv_task_sheet.setAdapter(details_adapter);
                    details_adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e("JSONEXCEPTION:", e.toString());
                    Log.e("JSONDATA_ll_ll", e.toString());
                }
                break;
        }
    }

    private void refreshContent() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                new WebserviceHelper(MainActivity.this).getData(getString(R.string.base_url) + "11zGzJADZmPA-t_7BwVMN5jnyHO0n1k977JyZpeTY4PE/od6/public/values?alt=json", "11zGzJADZmPA-t_7BwVMN5jnyHO0n1k977JyZpeTY4PE/od6/public/values?alt=json", MainActivity.this);
            }
        }, 100);

    }

    @Override
    public void onFailure() {

    }

    class ProcessingAdapter extends RecyclerView.Adapter<ProcessingAdapter.MyViewHolder> {

        ArrayList<Taskmodel> post_titles;
        private Context mContext;


         class MyViewHolder extends RecyclerView.ViewHolder {
             TextView tv_name_id, tv_task_id, tv_date_id;
             ImageView image_id;

             MyViewHolder(View rowView) {
                super(rowView);
                tv_name_id = (TextView) rowView.findViewById(R.id.tv_name_id);
                tv_task_id = (TextView) rowView.findViewById(R.id.tv_task_id);
                tv_date_id = (TextView) rowView.findViewById(R.id.tv_date_id);
                image_id = (ImageView) rowView.findViewById(R.id.image_id);
            }
        }

         ProcessingAdapter(Context context, ArrayList<Taskmodel> titles) {
            mContext = context;
            this.post_titles = titles;
        }

        @Override
        public ProcessingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_sheet_list_item, parent, false);

            return new ProcessingAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ProcessingAdapter.MyViewHolder holder, final int position) {
            try {
                holder.tv_name_id.setText(post_titles.get(position).getStg_name());
                holder.tv_date_id.setText("DATE: " + post_titles.get(position).getStg_date());
                holder.tv_task_id.setText(post_titles.get(position).getStg_task());
                holder.image_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDeails_list_dialog.show();

                        Picasso.with(mContext)
                                .load(post_titles.get(position).getStg_comment())
                                .into(image_dialog_id);
                    }
                });
                Picasso.with(mContext)
                        .load(post_titles.get(position).getStg_comment())
                        .into(holder.image_id);
            } catch (Exception e) {
                //Log.e("AdapterError", e.toString());
            }

        }

        @Override
        public int getItemCount() {
            return post_titles.size();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();

    }

}
