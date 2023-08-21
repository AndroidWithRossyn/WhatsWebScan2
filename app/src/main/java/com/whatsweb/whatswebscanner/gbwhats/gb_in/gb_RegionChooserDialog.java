package com.whatsweb.whatswebscanner.gbwhats.gb_in;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anchorfree.partner.api.data.Country;
import com.anchorfree.partner.api.response.AvailableCountries;
import com.anchorfree.sdk.UnifiedSDK;
import com.anchorfree.vpnsdk.exceptions.VpnException;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_SplashSCreenActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class gb_RegionChooserDialog extends DialogFragment implements gb_RegionListAdapter.RegionListAdapterInterface, gb_CountryAdapter.selecTionCountry, View.OnClickListener {

    public static final String TAG = gb_RegionChooserDialog.class.getSimpleName();


    Context context;
    String[] vip = {"de", "hk", "jp", "dk", "gb", "us", "ch", "au"};
    String[] free = {"no", "ru", "ua", "fr", "br", "se", "sg", "id", "ie", "in", "it", "mx", "es", "ar", "cz", "ro", "nl", "tr"};
    LinearLayout llFree;
    LinearLayout llVip;
    TextView tvFree;
    TextView tvVip;
    TextView tvOops;
    TextView tvFreebg;
    TextView tvVipbg;
    RecyclerView rvCountryList;
    ProgressBar regionsProgress;
    ArrayList<gb_CountryModal> freeCountry, vipCountry, list;
    ImageView ivBAck;
    View view;
    boolean isServerLoad = false;
    RegionChooserInterface regionChooserInterface;
    View mainView;
    gb_CountryAdapter hyCountryAdapter;

    public gb_RegionChooserDialog() {
    }


    public gb_RegionChooserDialog(Context context) {
        this.context = context;
    }

    public static gb_RegionChooserDialog newInstance(Context context) {
        gb_RegionChooserDialog frag = new gb_RegionChooserDialog(context);
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.gb_layout_server_fragmnet, container);


        Window window = getActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.gb_black));
        window.setNavigationBarColor(getResources().getColor(R.color.gb_black));

        init(mainView);
        this.view = mainView;
        return mainView;
    }


    private void init(View view) {

        rvCountryList = view.findViewById(R.id.countrylist);
        regionsProgress = view.findViewById(R.id.regions_progress);
        llFree = view.findViewById(R.id.as_ll_free_servers);
        llVip = view.findViewById(R.id.as_ll_vip_servers);
        tvFree = view.findViewById(R.id.as_tv_free_servers);
        tvVip = view.findViewById(R.id.as_tv_vip_servers);
        ivBAck = view.findViewById(R.id.as_back);
        tvOops = view.findViewById(R.id.tv_oops);
        tvFreebg = view.findViewById(R.id.as_tv_ll_bg_free);
        tvVipbg = view.findViewById(R.id.as_tv_ll_bg_vip);


        ivBAck.setOnClickListener(this);
        llFree.setOnClickListener(gb_RegionChooserDialog.this);
        llVip.setOnClickListener(gb_RegionChooserDialog.this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadServer();

    }

    private void loadServer() {
        ShowProgress();

        UnifiedSDK.getInstance().getBackend().countries(new com.anchorfree.vpnsdk.callbacks.Callback<AvailableCountries>() {
            @Override
            public void success(@NonNull final AvailableCountries countries) {
                isServerLoad = true;
                hideProress();

                gb_SplashSCreenActivity.list = new ArrayList<>();
                list = new ArrayList<>();

                gb_SplashSCreenActivity.list = countries.getCountries();
                for (Country country : gb_SplashSCreenActivity.list) {
                    for (gb_CountryModal counteyModal : gb_SplashSCreenActivity.listCountry) {
                        if (country.getCountry().equalsIgnoreCase(counteyModal.getCode())) {
                            gb_CountryModal newModal = new gb_CountryModal();
                            String a = counteyModal.getName();
                            newModal.setName(a);
                            newModal.setCode(counteyModal.getCode());
                            newModal.setCountry(country);
                            list.add(newModal);
                        }
                    }
                }

                String jsonObject = loadJSONFromAsset();
                ArrayList<sModal> list1 = new ArrayList<>();
                try {
                    JSONObject mObject = new JSONObject(jsonObject);
                    JSONArray mArray = mObject.getJSONArray("country_flag");

                    for (int i = 0; i < mArray.length(); i++) {
                        JSONObject object = mArray.getJSONObject(i);
                        String path = object.getString("url");
                        String name = object.getString("Name");
                        list1.add(new sModal(path, name));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                for (gb_CountryModal aa : list) {
                    for (sModal sModal : list1) {
                        if (aa.getName().equalsIgnoreCase(sModal.name)) {
                            aa.setPathFlag(sModal.path);
                        }
                    }

                }
                freeCountry = new ArrayList<>();
                vipCountry = new ArrayList<>();

                for (gb_CountryModal aa : list) {
                    for (String as : vip) {
                        if (aa.getCode().equalsIgnoreCase(as)) {
                            if (!vipCountry.contains(aa)) {
                                vipCountry.add(aa);
                            }
                        }
                    }
                }

                for (gb_CountryModal aa : list) {
                    for (String as : free) {
                        if (aa.getCode().equalsIgnoreCase(as)) {
                            if (!freeCountry.contains(aa)) {
                                freeCountry.add(aa);
                            }
                        }
                    }
                }


                hyCountryAdapter = new gb_CountryAdapter(context, freeCountry, 1);
                rvCountryList.setAdapter(hyCountryAdapter);
                hyCountryAdapter.setCallBack(gb_RegionChooserDialog.this);

                RecyclerView.LayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                rvCountryList.setLayoutManager(manager);


            }


            @Override
            public void failure(VpnException e) {
                hideProress();
                tvOops.setVisibility(View.VISIBLE);
            }
        });
    }

    private void ShowProgress() {
        regionsProgress.setVisibility(View.VISIBLE);
        rvCountryList.setVisibility(View.INVISIBLE);
        tvOops.setVisibility(View.GONE);
    }

    private void hideProress() {


        regionsProgress.setVisibility(View.GONE);
        rvCountryList.setVisibility(View.VISIBLE);


    }

    @Override
    public void onCountrySelected(Country item) {

        regionChooserInterface.onRegionSelected(item);
        dismiss();


    }

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        if (ctx instanceof RegionChooserInterface) {
            regionChooserInterface = (RegionChooserInterface) ctx;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        regionChooserInterface = null;
    }

    @Override
    public void onSelectionCountry(Country countryName) {
        regionChooserInterface.onRegionSelected(countryName);
        dismiss();
    }

    @Override
    public void onClick(View v) {

        if (v == llFree) {
            if (isServerLoad) {
                hyCountryAdapter = new gb_CountryAdapter(context, freeCountry, 1);
                rvCountryList.setAdapter(hyCountryAdapter);
                hyCountryAdapter.setCallBack(gb_RegionChooserDialog.this);

                RecyclerView.LayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                rvCountryList.setLayoutManager(manager);
                tvFree.setTextColor(Color.parseColor("#ffffff"));
                tvVip.setTextColor(Color.parseColor("#ffffff"));
                tvFreebg.setVisibility(View.VISIBLE);
                tvVipbg.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(context, "please wait... fetching server detail", Toast.LENGTH_SHORT).show();
            }

        }

        if (v == llVip) {
            if (isServerLoad) {
                hyCountryAdapter = new gb_CountryAdapter(context, vipCountry, 2);
                rvCountryList.setAdapter(hyCountryAdapter);
                hyCountryAdapter.setCallBack(gb_RegionChooserDialog.this);

                RecyclerView.LayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                rvCountryList.setLayoutManager(manager);

                tvVip.setTextColor(Color.parseColor("#ffffff"));
                tvFree.setTextColor(Color.parseColor("#ffffff"));
                tvVipbg.setVisibility(View.VISIBLE);
                tvFreebg.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(context, "please wait... fetching server detail", Toast.LENGTH_SHORT).show();
            }


        }

        if (v == ivBAck) {
            dismiss();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("country_flag.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public interface RegionChooserInterface {
        void onRegionSelected(Country item);
    }


    class sModal {
        public String name;
        public String path;

        public sModal(String path, String name) {
            this.path = path;
            this.name = name;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        AppManage.showNativeAd(getActivity(), mainView.findViewById(R.id.bottom_native_container), true);
    }
}