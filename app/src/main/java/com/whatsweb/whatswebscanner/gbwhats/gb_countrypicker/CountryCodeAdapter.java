package com.whatsweb.whatswebscanner.gbwhats.gb_countrypicker;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.whatsweb.whatswebscanner.gbwhats.R;

import java.util.ArrayList;
import java.util.List;


public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.CountryCodeViewHolder> implements SectionTitleProvider {
    CountryCodePicker codePicker;
    Context context;
    Dialog dialog;
    EditText editText_search;
    List<CCPCountry> filteredCountries = null;
    ImageView imgClearQuery;
    LayoutInflater inflater;
    List<CCPCountry> masterCountries = null;
    int preferredCountriesCount = 0;
    RelativeLayout rlQueryHolder;
    TextView textView_noResult;

    CountryCodeAdapter(Context context2, List<CCPCountry> list, CountryCodePicker countryCodePicker, RelativeLayout relativeLayout, EditText editText, TextView textView, Dialog dialog2, ImageView imageView) {
        this.context = context2;
        this.masterCountries = list;
        this.codePicker = countryCodePicker;
        this.dialog = dialog2;
        this.textView_noResult = textView;
        this.editText_search = editText;
        this.rlQueryHolder = relativeLayout;
        this.imgClearQuery = imageView;
        this.inflater = LayoutInflater.from(context2);
        this.filteredCountries = getFilteredCountries("");
        setSearchBar();
    }

    private void setSearchBar() {
        if (this.codePicker.isSearchAllowed()) {
            this.imgClearQuery.setVisibility(View.GONE);
            setTextWatcher();
            setQueryClearListener();
            return;
        }
        this.rlQueryHolder.setVisibility(View.GONE);
    }

    private void setQueryClearListener() {
        this.imgClearQuery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CountryCodeAdapter.this.editText_search.setText("");
            }
        });
    }

    private void setTextWatcher() {
        EditText editText = this.editText_search;
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable editable) {
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    CountryCodeAdapter.this.applyQuery(charSequence.toString());
                    if (charSequence.toString().trim().equals("")) {
                        CountryCodeAdapter.this.imgClearQuery.setVisibility(View.GONE);
                    } else {
                        CountryCodeAdapter.this.imgClearQuery.setVisibility(View.VISIBLE);
                    }
                }
            });
            this.editText_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                /* class com.whatsweb.whatswebscanner.gbwhats.countrypicker.CountryCodeAdapter.AnonymousClass3 */

                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 3) {
                        return false;
                    }
                    ((InputMethodManager) CountryCodeAdapter.this.context.getSystemService("input_method")).hideSoftInputFromWindow(CountryCodeAdapter.this.editText_search.getWindowToken(), 0);
                    return true;
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void applyQuery(String str) {
        this.textView_noResult.setVisibility(View.GONE);
        String lowerCase = str.toLowerCase();
        if (lowerCase.length() > 0 && lowerCase.charAt(0) == '+') {
            lowerCase = lowerCase.substring(1);
        }
        List<CCPCountry> filteredCountries2 = getFilteredCountries(lowerCase);
        this.filteredCountries = filteredCountries2;
        if (filteredCountries2.size() == 0) {
            this.textView_noResult.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    private List<CCPCountry> getFilteredCountries(String str) {
        ArrayList arrayList = new ArrayList();
        this.preferredCountriesCount = 0;
        if (this.codePicker.preferredCountries != null && this.codePicker.preferredCountries.size() > 0) {
            for (CCPCountry cCPCountry : this.codePicker.preferredCountries) {
                if (cCPCountry.isEligibleForQuery(str)) {
                    arrayList.add(cCPCountry);
                    this.preferredCountriesCount++;
                }
            }
            if (arrayList.size() > 0) {
                arrayList.add(null);
                this.preferredCountriesCount++;
            }
        }
        for (CCPCountry cCPCountry2 : this.masterCountries) {
            if (cCPCountry2.isEligibleForQuery(str)) {
                arrayList.add(cCPCountry2);
            }
        }
        return arrayList;
    }


    public CountryCodeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CountryCodeViewHolder(this.inflater.inflate(R.layout.gb_layout_recycler_country_tile, viewGroup, false));
    }

    public void onBindViewHolder(CountryCodeViewHolder countryCodeViewHolder, final int i) {
        countryCodeViewHolder.setCountry(this.filteredCountries.get(i));
        if (this.filteredCountries.size() <= i || this.filteredCountries.get(i) == null) {
            countryCodeViewHolder.rlll.setOnClickListener(null);
        } else {
            countryCodeViewHolder.rlll.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    if (CountryCodeAdapter.this.filteredCountries != null && CountryCodeAdapter.this.filteredCountries.size() > i) {
                        CountryCodeAdapter.this.codePicker.onUserTappedCountry(CountryCodeAdapter.this.filteredCountries.get(i));
                    }
                    if (view != null && CountryCodeAdapter.this.filteredCountries != null && CountryCodeAdapter.this.filteredCountries.size() > i && CountryCodeAdapter.this.filteredCountries.get(i) != null) {
                        ((InputMethodManager) CountryCodeAdapter.this.context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                        CountryCodeAdapter.this.dialog.dismiss();
                    }
                }
            });
        }
    }

    public int getItemCount() {
        return this.filteredCountries.size();
    }

    public String getSectionTitle(int i) {
        CCPCountry cCPCountry = this.filteredCountries.get(i);
        if (this.preferredCountriesCount > i) {
            return "★";
        }
        return cCPCountry != null ? cCPCountry.getName().substring(0, 1) : "☺";
    }

    public class CountryCodeViewHolder extends RecyclerView.ViewHolder {

        public View divider;
        public ImageView imageViewFlag;
        public TextView textView_name;
        public TextView textView_code;
        public LinearLayout linearFlagHolder;
        public RelativeLayout rlll;

        public CountryCodeViewHolder(View itemView) {
            super(itemView);

            this.divider = (View) itemView.findViewById(R.id.preferenceDivider);
            this.imageViewFlag = (ImageView) itemView.findViewById(R.id.image_flag);
            this.textView_name = (TextView) itemView.findViewById(R.id.textView_countryName);
            this.textView_code = (TextView) itemView.findViewById(R.id.textView_code);
            this.linearFlagHolder = (LinearLayout) itemView.findViewById(R.id.linear_flag_holder);
            this.rlll = (RelativeLayout) itemView.findViewById(R.id.rlll);

            if (CountryCodeAdapter.this.codePicker.getDialogTextColor() != 0) {
                this.textView_name.setTextColor(CountryCodeAdapter.this.codePicker.getDialogTextColor());
                this.textView_code.setTextColor(CountryCodeAdapter.this.codePicker.getDialogTextColor());
                this.divider.setBackgroundColor(CountryCodeAdapter.this.codePicker.getDialogTextColor());
            }
            try {
                if (CountryCodeAdapter.this.codePicker.getDialogTypeFace() == null) {
                    return;
                }
                if (CountryCodeAdapter.this.codePicker.getDialogTypeFaceStyle() != -99) {
                    this.textView_code.setTypeface(CountryCodeAdapter.this.codePicker.getDialogTypeFace(), CountryCodeAdapter.this.codePicker.getDialogTypeFaceStyle());
                    this.textView_name.setTypeface(CountryCodeAdapter.this.codePicker.getDialogTypeFace(), CountryCodeAdapter.this.codePicker.getDialogTypeFaceStyle());
                    return;
                }
                this.textView_code.setTypeface(CountryCodeAdapter.this.codePicker.getDialogTypeFace());
                this.textView_name.setTypeface(CountryCodeAdapter.this.codePicker.getDialogTypeFace());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setCountry(CCPCountry cCPCountry) {
            if (cCPCountry != null) {
                this.divider.setVisibility(View.GONE);
                this.textView_name.setVisibility(View.VISIBLE);
                this.textView_code.setVisibility(View.VISIBLE);
                if (CountryCodeAdapter.this.codePicker.isCcpDialogShowPhoneCode()) {
                    this.textView_code.setVisibility(View.VISIBLE);
                } else {
                    this.textView_code.setVisibility(View.GONE);
                }
                String str = "";
                if (CountryCodeAdapter.this.codePicker.getCcpDialogShowFlag() && CountryCodeAdapter.this.codePicker.ccpUseEmoji) {
                    str = str + CCPCountry.getFlagEmoji(cCPCountry) + "   ";
                }
                String str2 = str + cCPCountry.getName();
                if (CountryCodeAdapter.this.codePicker.getCcpDialogShowNameCode()) {
                    str2 = str2 + " (" + cCPCountry.getNameCode().toUpperCase() + ")";
                }
                this.textView_name.setText(str2);
                this.textView_code.setText("+" + cCPCountry.getPhoneCode());
                if (!CountryCodeAdapter.this.codePicker.getCcpDialogShowFlag() || CountryCodeAdapter.this.codePicker.ccpUseEmoji) {
                    this.linearFlagHolder.setVisibility(View.GONE);
                    return;
                }
                this.linearFlagHolder.setVisibility(View.VISIBLE);
                this.imageViewFlag.setImageResource(cCPCountry.getFlagID());
                return;
            }
            this.divider.setVisibility(View.VISIBLE);
            this.textView_name.setVisibility(View.GONE);
            this.textView_code.setVisibility(View.GONE);
            this.linearFlagHolder.setVisibility(View.GONE);
        }
    }
}
