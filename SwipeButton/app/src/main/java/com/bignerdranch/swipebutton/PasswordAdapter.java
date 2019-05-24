package com.bignerdranch.swipebutton;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {
    public interface dbCallBack {
        void removeFromDb(WifiInfo wi);

        void addToDbCB(WifiInfo wi);

        void moveToHistory(WifiInfo wi);
    }

    private List<WifiInfo> passwordList, passwordListCopy;
    private Context context;
    private dbCallBack db;
    private LinearLayout desertPlaceholder;
    private boolean cardHide = true;
    private Drawable copy, share;
    private int collapsedHeight;

    PasswordAdapter(Context context, dbCallBack db, LinearLayout desertPlaceholder) {
        this.context = context;
        this.passwordList = new ArrayList<>();//new ArrayList<>(passwordList);
        passwordListCopy = new ArrayList<>();
        this.db = db;
        this.desertPlaceholder = desertPlaceholder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            copy = ContextCompat.getDrawable(context, R.drawable.ic_content_copy_black_24px);
            share = ContextCompat.getDrawable(context, R.drawable.ic_share_black_24px);
        } else {
            copy =
                    VectorDrawableCompat.create(context.getResources(), R.drawable.ic_content_copy_black_24px,
                            context.getTheme());
            share = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_share_black_24px,
                    context.getTheme());
        }
    }

    void filter(String text) {
        passwordList.clear();
        if (text.isEmpty()) {
            passwordList.addAll(passwordListCopy);
        } else {
            text = text.toLowerCase();
            for (WifiInfo item : passwordListCopy) {
                if (item.SSID.toLowerCase().contains(text)) {
                    passwordList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override public int getItemCount() {
        desertPlaceholder.setVisibility(passwordList.size() > 0 ? View.GONE : View.VISIBLE);
        return passwordList.size();
    }

    @Override public void onBindViewHolder(final PasswordViewHolder passVH, int i) {
        final WifiInfo wi = passwordList.get(i);
        passVH.vSSID.setText(wi.SSID);
        passVH.vPassword.setText(wi.password);

        String pattern = ((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM,
                Locale.getDefault())).toPattern();
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        if (passVH.swipeLayout.isOpened()) {
            passVH.swipeLayout.close(false);
        }

        if (!cardHide) {
            passVH.cardMore.setVisibility(View.GONE);
            passVH.delete.setLayoutParams(
                    new FrameLayout.LayoutParams(passVH.delete.getWidth(), collapsedHeight));
            cardHide = true;
            checkArrow(passVH.cardArrow);
        }

        passVH.ivShare.setImageDrawable(share);
        passVH.ivCopy.setImageDrawable(copy);
        passVH.vDate.setText(dateFormat.format(wi.date));
        if (wi.hidden) {
            passVH.vHide.setVisibility(View.VISIBLE);
            passVH.vDot.setVisibility(View.VISIBLE);
        }
        if (wi.state.equals("active")) {
            Drawable drawable;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable =
                        VectorDrawableCompat.create(context.getResources(), R.drawable.ic_archive_black_24px,
                                context.getTheme());
            } else {
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_archive_black_24px);
            }
            passVH.ivDelete.setImageDrawable(drawable);
            passVH.vDelete.setText(context.getString(R.string.to_archive));
            passVH.delete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    db.moveToHistory(wi);
                    passVH.swipeLayout.close(true);
                }
            });
        } else {
            Drawable drawable;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable =
                        VectorDrawableCompat.create(context.getResources(), R.drawable.ic_delete_black_24px,
                                context.getTheme());
            } else {
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_delete_black_24px);
            }
            passVH.ivDelete.setImageDrawable(drawable);
            passVH.vDelete.setText(context.getString(R.string.delete));
            passVH.delete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    App.selectContent("card", "удалить");
                    removeItem(wi);
                    db.removeFromDb(wi);
                    Snackbar.make(passVH.itemView, wi.SSID + " " + context.getString(R.string.removed),
                            Snackbar.LENGTH_LONG).setAction(R.string.cancel, new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            addItem(wi);
                            db.addToDbCB(wi);
                        }
                    }).show();
                }
            });
        }
        passVH.vCopy.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                App.selectContent("card", "копировать");
                Tools.CopyToClipboard(context, wi.password);
                Toast.makeText(context, R.string.Copy_value, Toast.LENGTH_LONG).show();
            }
        });
        passVH.vConnect.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                App.selectContent("card", "подключить сеть");
                WifiManager wifiManager =
                        (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager == null) {
                    return;
                }
                final Activity activity = (Activity) context;
                activity.runOnUiThread(new Runnable() {
                    @Override public void run() {
                        Toast.makeText(context, R.string.wifi_changing_network, Toast.LENGTH_SHORT).show();
                    }
                });
                WifiConfigManager wcf = new WifiConfigManager(wifiManager);
                if (wcf.getStatus().toString().equals("PENDING")) wcf.execute(wi);
            }
        });
        passVH.vShare.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                PopUpMenuEventHandle popUpMenuEventHandle = new PopUpMenuEventHandle(context, wi);
                popupMenu.setOnMenuItemClickListener(popUpMenuEventHandle);
                menuInflater.inflate(R.menu.share_popup, popupMenu.getMenu());
                popupMenu.show();
            }
        });
        passVH.cardMain.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (cardHide) {
                    passVH.cardMore.setVisibility(View.VISIBLE);
                    collapsedHeight = passVH.cardMain.getHeight();
                    passVH.delete.setLayoutParams(new FrameLayout.LayoutParams(passVH.delete.getWidth(),
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    cardHide = false;
                } else {
                    passVH.cardMore.setVisibility(View.GONE);
                    passVH.delete.setLayoutParams(
                            new FrameLayout.LayoutParams(passVH.delete.getWidth(), collapsedHeight));
                    cardHide = true;
                }
                checkArrow(passVH.cardArrow);
            }
        });
    }

    @Override public PasswordViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_network, viewGroup, false);
        return new PasswordViewHolder(itemView);
    }

    private void checkArrow(ImageView imageView) {
        if (cardHide) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                imageView.setImageDrawable(VectorDrawableCompat.create(context.getResources(),
                        R.drawable.ic_keyboard_arrow_down_black_24dp, context.getTheme()));
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.ic_keyboard_arrow_down_black_24dp));
            }
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                imageView.setImageDrawable(VectorDrawableCompat.create(context.getResources(),
                        R.drawable.ic_keyboard_arrow_up_black_24dp, context.getTheme()));
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.ic_keyboard_arrow_up_black_24dp));
            }
        }
    }

    public List<WifiInfo> getList() {
        return passwordList;
    }

    public void addAll(List<WifiInfo> list) {
        passwordList.addAll(list);
        passwordListCopy.addAll(list);
        notifyDataSetChanged();
    }

    public void clearAll() {
        passwordList.clear();
        passwordListCopy.clear();
        notifyDataSetChanged();
    }

    WifiInfo removeItem(int position) {
        WifiInfo model = passwordList.remove(position);
        passwordListCopy.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, passwordList.size());
        return model;
    }

    void removeItem(final WifiInfo model) {
        int pos = passwordList.indexOf(model);
        if (pos > -1 && pos < passwordList.size()) {
            passwordList.remove(model);
            passwordListCopy.remove(model);
            notifyItemRemoved(pos);
        }
    }

    void addItem(WifiInfo model) {
        if (!passwordListCopy.contains(model)) {
            passwordList.add(model);
            passwordListCopy.add(model);
            Tools.sort(passwordList);
            Tools.sort(passwordListCopy);
            notifyItemInserted(passwordList.indexOf(model));
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        final WifiInfo model = passwordList.remove(fromPosition);
        passwordList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public WifiInfo getItem(int position) {
        return passwordList.get(position);
    }

    static class PasswordViewHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout swipeLayout;
        LinearLayout cardMain, cardMore, vCopy, vShare;
        TextView vSSID, vPassword, vHide, vDate, vConnect, vDot, vDelete;
        FrameLayout delete;
        ImageView cardArrow, ivCopy, ivShare, ivDelete;

        PasswordViewHolder(View v) {
            super(v);
            vDelete = v.findViewById(R.id.tv_delete);
            ivDelete = v.findViewById(R.id.iv_delete);
            delete = v.findViewById(R.id.delete_layout);
            swipeLayout = v.findViewById(R.id.swipe_layout);
            cardMain = v.findViewById(R.id.card_main);
            cardMore = v.findViewById(R.id.card_more);
            vSSID = v.findViewById(R.id.SSID);
            vPassword = v.findViewById(R.id.password);
            vHide = v.findViewById(R.id.tv_hide);
            vCopy = v.findViewById(R.id.CopyButton);
            vConnect = v.findViewById(R.id.ConnectButton);
            vShare = v.findViewById(R.id.ShareButton);
            vDate = v.findViewById(R.id.date);
            cardArrow = v.findViewById(R.id.card_arrow);
            vDot = v.findViewById(R.id.tv_dot);
            ivCopy = v.findViewById(R.id.iv_card_copy);
            ivShare = v.findViewById(R.id.iv_card_share);
            vPassword.setCompoundDrawablesWithIntrinsicBounds(
                    AppCompatResources.getDrawable(vPassword.getContext(), R.drawable.ic_vpn_key_black_24dp),
                    null, null, null);
        }
    }
}}
