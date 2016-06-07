package com.github.fabtransitionactivity.demo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.fabtransitionactivity.SheetLayout;
import com.github.fabtransitionactivity.demo.model.Mail;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements SheetLayout.OnFabAnimationEndListener {
    public static final String TAG = "FAB";

    @Bind(R.id.bottom_sheet)
    SheetLayout mSheetLayout;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

//    @Bind(R.id.list_mails)
//    ListView listMails;

    @Bind(R.id.fab1)
    FloatingActionButton mFab1;
    @Bind(R.id.fab2)
    FloatingActionButton mFab2;
    @Bind(R.id.fab3)
    FloatingActionButton mFab3;

    @Bind(R.id.anchor)
    ImageView mAnchor;

    ArrayList<Mail> mailList = new ArrayList<>();


    private static final int FAB_ANIMATION_ENDED_REQUEST_CODE = 1;
    private int mAnimationDuration;
    private float mFabSize = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolbarWithTitle(getString(R.string.INBOX), false);

        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(this);

//        fillMailList();
//        listMails.setAdapter(new MailAdapter());

        mFab1.setY(200f);
        mFab2.setY(200f);
        mFab3.setY(200f);
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        mFab1.setVisibility(View.VISIBLE);
        mFab2.setVisibility(View.VISIBLE);
        mFab3.setVisibility(View.VISIBLE);

        float dx = ViewUtils.centerX(mSheetLayout) + getFabSizePx() - ViewUtils.centerX(mFab);
        float dy = ViewUtils.getRelativeTop(mSheetLayout) - ViewUtils.getRelativeTop(mFab)
                - (mFab.getHeight() - getFabSizePx()) / 2;

        Animator fabSlideYAnim = ObjectAnimator.ofPropertyValuesHolder(mFab,
                PropertyValuesHolder.ofFloat("translationY", 0f, dy));
        fabSlideYAnim.setDuration(mAnimationDuration / 2);

        AnimatorSet animSet = new AnimatorSet();

        /* building animations in reverse */
        final Runnable restore = new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(mFab1)
                        // Using straight values here to move back to original position of 0,0
                        .translationX(0)
                        .translationY(0)
                        .withLayer()
                        .setDuration(500)
                        .setInterpolator(AnimationUtils.loadInterpolator(MainActivity.this, android.R.interpolator.anticipate_overshoot));
            }
        };

        ViewCompat.animate(mFab1)
                .translationYBy(-200)
                .withLayer()
                .setDuration(500)
                .setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.anticipate_overshoot))
                .withEndAction(restore);

        final Runnable restore2 = new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(mFab2)
                        // Using straight values here to move back to original position of 0,0
                        .translationX(0)
                        .translationY(0)
                        .withLayer()
                        .setDuration(500)
                        .setStartDelay(100)
                        .setInterpolator(AnimationUtils.loadInterpolator(MainActivity.this, android.R.interpolator.anticipate_overshoot));
            }
        };

        ViewCompat.animate(mFab2)
                .translationYBy(-200)
                .withLayer()
                .setDuration(600)
                .setStartDelay(100)
                .setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.anticipate_overshoot))
                .withEndAction(restore2);

        final Runnable restore3 = new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(mFab3)
                        // Using straight values here to move back to original position of 0,0
                        .translationX(0)
                        .translationY(0)
                        .withLayer()
                        .setDuration(500)
                        .setStartDelay(200)
                        .setInterpolator(AnimationUtils.loadInterpolator(MainActivity.this, android.R.interpolator.anticipate_overshoot));
            }
        };

        ViewCompat.animate(mFab3)
                .translationYBy(-200)
                .withLayer()
                .setDuration(600)
                .setStartDelay(200)
                .setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.anticipate_overshoot))
                .withEndAction(restore3);

        mFab.setVisibility(View.GONE);
        mAnchor.setVisibility(View.VISIBLE);


//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in);
//
//        animation.setStartOffset(00);
//        mFab1.startAnimation(animation);


        mSheetLayout.expandFab();
    }

    @OnClick(R.id.anchor)
    public void onClickClose() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure you want to hide the action buttons?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//                        return;
//                    }
//                });
//        // Create the AlertDialog object and return it
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
        Log.i(TAG, "Making the FAB visible again");
        // TODO - alpha fade in/out
        mFab.setVisibility(View.VISIBLE);
        mFab1.clearAnimation();
        mFab2.clearAnimation();
        mFab3.clearAnimation();

        Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        slideOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFab1.setVisibility(View.GONE);
                mFab2.setVisibility(View.GONE);
                mFab3.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mFab1.setAnimation(slideOut);
        mFab2.setAnimation(slideOut);
        mFab3.setAnimation(slideOut);
        slideOut.start();
        // TODO - alpha fade
        mAnchor.setVisibility(View.INVISIBLE);
    }

    private int getFabSizePx() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(mFabSize * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onFabAnimationEnd() {
//        Intent intent = new Intent(this, AfterFabAnimationActivity.class);
//        startActivityForResult(intent, FAB_ANIMATION_ENDED_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FAB_ANIMATION_ENDED_REQUEST_CODE) {
//            mSheetLayout.contractFab();
        }
    }

    private void fillMailList() {
        String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";

        mailList.add(new Mail(1, "Abbrey Christensen", message, "Nov 5"));
        mailList.add(new Mail(2, "Alex Nelson", message, "Nov 5"));
        mailList.add(new Mail(3, "Mary Johnson", message, "Nov 4"));
        mailList.add(new Mail(4, "Peter Cartlsson", message, "Nov 3"));
        mailList.add(new Mail(5, "Trevor Hansen", message, "Nov 2"));
        mailList.add(new Mail(6, "Britta Holt", message, "Nov 2"));
        mailList.add(new Mail(7, "Sandra Adams", message, "Nov 2"));
        mailList.add(new Mail(8, "Cristopher Oyarzún", "Yeah!!", "Nov 2"));
    }

    public void contractFab(View view) {
        SheetLayout layout = (SheetLayout) view;
        layout.contractFab();

        mFab.setVisibility(View.VISIBLE);
    }

    private class MailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mailList.size();
        }

        @Override
        public Mail getItem(int position) {
            return mailList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.list_item_mail, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.imageEmail.setColorFilter(setColorFilter(mailList.get(position).getCircleColor()));
            holder.textLabelEmail.setText(mailList.get(position).getTitleEmail().substring(0, 1));
            holder.textTitleEmail.setText(mailList.get(position).getTitleEmail());
            holder.textMessageEmail.setText(mailList.get(position).getMessageEmail());
            holder.textDateEmail.setText(mailList.get(position).getDateEmail());

            return convertView;
        }
    }

    static class ViewHolder {
        @Bind(R.id.image_email)
        ImageView imageEmail;
        @Bind(R.id.text_label_email)
        TextView textLabelEmail;
        @Bind(R.id.text_title_email)
        TextView textTitleEmail;
        @Bind(R.id.text_message_email)
        TextView textMessageEmail;
        @Bind(R.id.text_date_email)
        TextView textDateEmail;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private int setColorFilter(int color) {
        if ((color % 4) == 0) {
            return ContextCompat.getColor(getApplicationContext(), R.color.one_round);
        }
        if ((color % 3) == 0) {
            return ContextCompat.getColor(getApplicationContext(), R.color.two_round);
        }
        if ((color % 2) == 0) {
            return ContextCompat.getColor(getApplicationContext(), R.color.three_round);
        } else {
            return ContextCompat.getColor(getApplicationContext(), R.color.four_round);
        }
    }

}
