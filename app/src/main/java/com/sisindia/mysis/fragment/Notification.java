package com.sisindia.mysis.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Adapter.NotificationAdapter;
import com.sisindia.mysis.Adapter.NotificationContentAdapter;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.NotificationModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Notification extends CSHeaderFragmentFragment {
    @BindView(R.id.notification_recycle_view)
    RecyclerView notification_RV;
    @BindView(R.id.countTV)
    TextView countTV;
    @BindView(R.id.noNotificationFoundTV)
    TextView noNotificationFoundTV;
    @BindView(R.id.toolbar_title_linear)
    LinearLayout toolbar_title_linear;
    private NotificationAdapter notificationAdapter;
    private List<NotificationModel> notificationModelList;
    private List<String> notificationDateList = new ArrayList<>();
    private List<String> notificationContentList = new ArrayList();

    @Override
    public int layoutResource() {
        return R.layout.activity_notification;
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        notificationModelList = CSApplicationHelper.application().getDatabaseInstance().notification_dao().getNotificationList(DateUtils.getCurrentDate_TIME_format());
        if (notificationModelList.size() > 0) {
            notification_RV.setAdapter(new NotificationContentAdapter(this, notificationModelList));
            noNotificationFoundTV.setVisibility(View.GONE);
            notification_RV.setVisibility(View.VISIBLE);
        } else {
            noNotificationFoundTV.setVisibility(View.VISIBLE);
            notification_RV.setVisibility(View.GONE);
        }
        toolbar_title_linear.setOnClickListener(view -> csActivity.onBackPressed());
        CSApplicationHelper.application().getDatabaseInstance().notification_dao().countUnRead(DateUtils.getCurrentDate_TIME_format()).observe(this, integer ->
        {
            if (integer >= 1) {
                countTV.setVisibility(View.VISIBLE);
                countTV.setText(String.valueOf(integer));
            } else {
                countTV.setVisibility(View.GONE);
            }
        });
//        NotificationContentAdapter
       /* notificationDateList.add("Today");
        notificationDateList.add("12, March");
        notificationDateList.add("11, March");
        notificationContentList.add("Leave Request Rejected");
        notificationContentList.add("Leave Request Accesspted");
        notificationContentList.add("Leave Request Accesspted");

        notificationAdapter = new NotificationAdapter(csActivity, notificationDateList,notificationContentList);
        notification_RV.setAdapter(notificationAdapter);
        ViewCompat.setNestedScrollingEnabled(notification_RV,true);*/

        // notification_RV.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
//        notificationAdapter.notifyDataSetChanged();
    }
}
