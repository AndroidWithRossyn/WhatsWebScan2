package com.whatsweb.whatswebscanner.gbwhats.gb_countrypicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsweb.whatswebscanner.gbwhats.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class CountryCodePicker extends RelativeLayout {
    private static String ANDROID_NAME_SPACE = "http://schemas.android.com/apk/res/android";
    static String BUNDLE_SELECTED_CODE = "selectedCode";
    static final int DEFAULT_UNSET = -99;
    static int LIB_DEFAULT_COUNTRY_CODE = 91;
    static String TAG = "CCP";
    private static int TEXT_GRAVITY_CENTER = 0;
    private static int TEXT_GRAVITY_LEFT = -1;
    private static int TEXT_GRAVITY_RIGHT = 1;
    String CCP_PREF_FILE = "CCP_PREF_FILE";
    TextWatcher areaCodeCountryDetectorTextWatcher;
    int arrowColor = DEFAULT_UNSET;
    boolean autoDetectCountryEnabled = false;
    boolean autoDetectLanguageEnabled = false;
    int borderFlagColor;
    boolean ccpClickable = true;
    boolean ccpDialogInitialScrollToSelection = false;
    boolean ccpDialogShowFlag = true;
    boolean ccpDialogShowNameCode = true;
    boolean ccpDialogShowPhoneCode = true;
    boolean ccpDialogShowTitle = true;
    int ccpTextgGravity = TEXT_GRAVITY_CENTER;
    boolean ccpUseDummyEmojiForPreview = false;
    boolean ccpUseEmoji = false;
    CountryCodePicker codePicker;
    int contentColor = DEFAULT_UNSET;
    Context context;
    boolean countryChangedDueToAreaCode = false;
    OnClickListener countryCodeHolderClickListener = new OnClickListener() {
        /* class com.whatsweb.whatswebscanner.gbwhats.countrypicker.CountryCodePicker.AnonymousClass1 */

        public void onClick(View view) {
            if (CountryCodePicker.this.customClickListener != null) {
                CountryCodePicker.this.customClickListener.onClick(view);
            } else if (!CountryCodePicker.this.isCcpClickable()) {
            } else {
                if (CountryCodePicker.this.ccpDialogInitialScrollToSelection) {
                    CountryCodePicker countryCodePicker = CountryCodePicker.this;
                    countryCodePicker.launchCountrySelectionDialog(countryCodePicker.getSelectedCountryNameCode());
                    return;
                }
                CountryCodePicker.this.launchCountrySelectionDialog();
            }
        }
    };
    boolean countryDetectionBasedOnAreaAllowed;
    String countryPreference;
    private CCPCountryGroup currentCountryGroup;
    TextGravity currentTextGravity;
    private OnClickListener customClickListener;
    Language customDefaultLanguage = Language.ENGLISH;
    private CustomDialogTextProvider customDialogTextProvider;
    List<CCPCountry> customMasterCountriesList;
    String customMasterCountriesParam;
    CCPCountry defaultCCPCountry;
    int defaultCountryCode;
    String defaultCountryNameCode;
    boolean detectCountryWithAreaCode = true;
    private int dialogBackgroundColor;
    private DialogEventsListener dialogEventsListener;
    boolean dialogKeyboardAutoPopup = true;
    private int dialogSearchEditTextTintColor;
    private int dialogTextColor;
    Typeface dialogTypeFace;
    int dialogTypeFaceStyle;
    EditText editText_registeredCarrierNumber;
    String excludedCountriesParam;
    private FailureListener failureListener;
    int fastScrollerBubbleColor = 0;
    private int fastScrollerBubbleTextAppearance = 0;
    private int fastScrollerHandleColor = 0;
    InternationalPhoneTextWatcher formattingTextWatcher;
    boolean hintExampleNumberEnabled = false;
    PhoneNumberType hintExampleNumberType = PhoneNumberType.MOBILE;
    RelativeLayout holder;
    View holderView;
    ImageView imageViewArrow;
    ImageView imageViewFlag;
    boolean internationalFormattingOnly = true;
    Language languageToApply = Language.ENGLISH;
    String lastCheckedAreaCode = null;
    int lastCursorPosition = 0;
    LinearLayout linearFlagBorder;
    LinearLayout linearFlagHolder;
    LayoutInflater mInflater;
    boolean numberAutoFormattingEnabled = true;
    private OnCountryChangeListener onCountryChangeListener;
    String originalHint = "";
    private PhoneNumberValidityChangeListener phoneNumberValidityChangeListener;
    PhoneNumberUtil phoneUtil;
    List<CCPCountry> preferredCountries;
    RelativeLayout relativeClickConsumer;
    boolean rememberLastSelection = false;
    boolean reportedValidity;
    boolean searchAllowed = true;
    AutoDetectionPref selectedAutoDetectionPref = AutoDetectionPref.SIM_NETWORK_LOCALE;
    CCPCountry selectedCCPCountry;
    String selectionMemoryTag = "ccp_last_selection";
    boolean showArrow = true;
    boolean showCloseIcon = false;
    boolean showFastScroller = true;
    boolean showFlag = true;
    boolean showFullName = false;
    boolean showNameCode = true;
    boolean showPhoneCode = true;
    TextView textView_selectedCountry;
    TextWatcher validityTextWatcher;
    String xmlWidth = "notSet";

    public interface CustomDialogTextProvider {
        String getCCPDialogNoResultACK(Language language, String str);

        String getCCPDialogSearchHintText(Language language, String str);

        String getCCPDialogTitle(Language language, String str);
    }

    public interface DialogEventsListener {
        void onCcpDialogCancel(DialogInterface dialogInterface);

        void onCcpDialogDismiss(DialogInterface dialogInterface);

        void onCcpDialogOpen(Dialog dialog);
    }

    public interface FailureListener {
        void onCountryAutoDetectionFailed();
    }

    public interface OnCountryChangeListener {
        void onCountrySelected();
    }

    public enum PhoneNumberType {
        MOBILE,
        FIXED_LINE,
        FIXED_LINE_OR_MOBILE,
        TOLL_FREE,
        PREMIUM_RATE,
        SHARED_COST,
        VOIP,
        PERSONAL_NUMBER,
        PAGER,
        UAN,
        VOICEMAIL,
        UNKNOWN
    }

    public interface PhoneNumberValidityChangeListener {
        void onValidityChanged(boolean z);
    }

    public CountryCodePicker(Context context2) {
        super(context2);
        this.context = context2;
        init(null);
    }

    public CountryCodePicker(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        this.context = context2;
        init(attributeSet);
    }

    public CountryCodePicker(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        this.context = context2;
        init(attributeSet);
    }

    private boolean isNumberAutoFormattingEnabled() {
        return this.numberAutoFormattingEnabled;
    }

    public void setNumberAutoFormattingEnabled(boolean z) {
        this.numberAutoFormattingEnabled = z;
        if (this.editText_registeredCarrierNumber != null) {
            updateFormattingTextWatcher();
        }
    }

    private boolean isInternationalFormattingOnlyEnabled() {
        return this.internationalFormattingOnly;
    }

    public void setInternationalFormattingOnly(boolean z) {
        this.internationalFormattingOnly = z;
        if (this.editText_registeredCarrierNumber != null) {
            updateFormattingTextWatcher();
        }
    }

    private void init(AttributeSet attributeSet) {
        String str;
        this.mInflater = LayoutInflater.from(this.context);
        if (attributeSet != null) {
            this.xmlWidth = attributeSet.getAttributeValue(ANDROID_NAME_SPACE, "layout_width");
        }
        removeAllViewsInLayout();
        if (attributeSet == null || (str = this.xmlWidth) == null || (!str.equals("-1") && !this.xmlWidth.equals("-1") && !this.xmlWidth.equals("fill_parent") && !this.xmlWidth.equals("match_parent"))) {
            this.holderView = this.mInflater.inflate(R.layout.gb_layout_code_picker, (ViewGroup) this, true);
        } else {
            this.holderView = this.mInflater.inflate(R.layout.gb_layout_full_width_code_picker, (ViewGroup) this, true);
        }
        this.textView_selectedCountry = (TextView) this.holderView.findViewById(R.id.textView_selectedCountry);
        this.holder = (RelativeLayout) this.holderView.findViewById(R.id.countryCodeHolder);
        this.imageViewArrow = (ImageView) this.holderView.findViewById(R.id.imageView_arrow);
        this.imageViewFlag = (ImageView) this.holderView.findViewById(R.id.image_flag);
        this.linearFlagHolder = (LinearLayout) this.holderView.findViewById(R.id.linear_flag_holder);
        this.linearFlagBorder = (LinearLayout) this.holderView.findViewById(R.id.linear_flag_border);
        this.relativeClickConsumer = (RelativeLayout) this.holderView.findViewById(R.id.rlClickConsumer);
        this.codePicker = this;
        if (attributeSet != null) {
            applyCustomProperty(attributeSet);
        }
        this.relativeClickConsumer.setOnClickListener(this.countryCodeHolderClickListener);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x01b4  */
    private void applyCustomProperty(AttributeSet attributeSet) {
        boolean z;
        int i;
        int i2;
        TypedArray obtainStyledAttributes = this.context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CountryCodePicker, 0, 0);
        try {
            this.showNameCode = obtainStyledAttributes.getBoolean(39, true);
            this.numberAutoFormattingEnabled = obtainStyledAttributes.getBoolean(20, true);
            boolean z2 = obtainStyledAttributes.getBoolean(40, true);
            this.showPhoneCode = z2;
            this.ccpDialogShowPhoneCode = obtainStyledAttributes.getBoolean(12, z2);
            this.ccpDialogShowNameCode = obtainStyledAttributes.getBoolean(11, true);
            this.ccpDialogShowTitle = obtainStyledAttributes.getBoolean(13, true);
            this.ccpUseEmoji = obtainStyledAttributes.getBoolean(44, false);
            this.ccpUseDummyEmojiForPreview = obtainStyledAttributes.getBoolean(43, false);
            this.ccpDialogShowFlag = obtainStyledAttributes.getBoolean(10, true);
            this.ccpDialogInitialScrollToSelection = obtainStyledAttributes.getBoolean(5, false);
            this.showFullName = obtainStyledAttributes.getBoolean(38, false);
            this.showFastScroller = obtainStyledAttributes.getBoolean(9, true);
            this.fastScrollerBubbleColor = obtainStyledAttributes.getColor(2, 0);
            this.fastScrollerHandleColor = obtainStyledAttributes.getColor(4, 0);
            this.fastScrollerBubbleTextAppearance = obtainStyledAttributes.getResourceId(3, 0);
            this.autoDetectLanguageEnabled = obtainStyledAttributes.getBoolean(19, false);
            this.detectCountryWithAreaCode = obtainStyledAttributes.getBoolean(15, true);
            this.rememberLastSelection = obtainStyledAttributes.getBoolean(34, false);
            this.hintExampleNumberEnabled = obtainStyledAttributes.getBoolean(31, false);
            this.internationalFormattingOnly = obtainStyledAttributes.getBoolean(33, true);
            this.hintExampleNumberType = PhoneNumberType.values()[obtainStyledAttributes.getInt(32, 0)];
            String string = obtainStyledAttributes.getString(35);
            this.selectionMemoryTag = string;
            if (string == null) {
                this.selectionMemoryTag = "CCP_last_selection";
            }
            this.selectedAutoDetectionPref = AutoDetectionPref.getPrefForValue(String.valueOf(obtainStyledAttributes.getInt(23, 123)));
            this.autoDetectCountryEnabled = obtainStyledAttributes.getBoolean(18, false);
            this.showArrow = obtainStyledAttributes.getBoolean(36, true);
            refreshArrowViewVisibility();
            this.showCloseIcon = obtainStyledAttributes.getBoolean(8, false);
            showFlag(obtainStyledAttributes.getBoolean(37, true));
            setDialogKeyboardAutoPopup(obtainStyledAttributes.getBoolean(6, true));
            this.customDefaultLanguage = getLanguageEnum(obtainStyledAttributes.getInt(26, Language.ENGLISH.ordinal()));
            updateLanguageToApply();
            this.customMasterCountriesParam = obtainStyledAttributes.getString(25);
            this.excludedCountriesParam = obtainStyledAttributes.getString(29);
            if (!isInEditMode()) {
                refreshCustomMasterList();
            }
            this.countryPreference = obtainStyledAttributes.getString(24);
            if (!isInEditMode()) {
                refreshPreferredCountries();
            }
            if (obtainStyledAttributes.hasValue(41)) {
                this.ccpTextgGravity = obtainStyledAttributes.getInt(41, TEXT_GRAVITY_CENTER);
            }
            applyTextGravity(this.ccpTextgGravity);
            String string2 = obtainStyledAttributes.getString(27);
            this.defaultCountryNameCode = string2;
            if (string2 == null || string2.length() == 0) {
                z = false;
            } else {
                if (!isInEditMode()) {
                    if (CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), this.defaultCountryNameCode) != null) {
                        setDefaultCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), this.defaultCountryNameCode));
                        setSelectedCountry(this.defaultCCPCountry);
                    }
                    z = false;
                    if (!z) {
                        setDefaultCountry(CCPCountry.getCountryForNameCodeFromEnglishList("IN"));
                        setSelectedCountry(this.defaultCCPCountry);
                        z = true;
                    }
                } else {
                    if (CCPCountry.getCountryForNameCodeFromEnglishList(this.defaultCountryNameCode) != null) {
                        setDefaultCountry(CCPCountry.getCountryForNameCodeFromEnglishList(this.defaultCountryNameCode));
                        setSelectedCountry(this.defaultCCPCountry);
                    }
                    z = false;
                    if (!z) {
                    }
                }
                z = true;
                if (!z) {
                }
            }
            int integer = obtainStyledAttributes.getInteger(28, -1);
            if (!z && integer != -1) {
                if (!isInEditMode()) {
                    if (integer != -1 && CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), this.preferredCountries, integer) == null) {
                        integer = LIB_DEFAULT_COUNTRY_CODE;
                    }
                    setDefaultCountryUsingPhoneCode(integer);
                    setSelectedCountry(this.defaultCCPCountry);
                } else {
                    CCPCountry countryForCodeFromEnglishList = CCPCountry.getCountryForCodeFromEnglishList(integer + "");
                    if (countryForCodeFromEnglishList == null) {
                        countryForCodeFromEnglishList = CCPCountry.getCountryForCodeFromEnglishList(LIB_DEFAULT_COUNTRY_CODE + "");
                    }
                    setDefaultCountry(countryForCodeFromEnglishList);
                    setSelectedCountry(countryForCodeFromEnglishList);
                }
            }
            if (getDefaultCountry() == null) {
                setDefaultCountry(CCPCountry.getCountryForNameCodeFromEnglishList("IN"));
                if (getSelectedCountry() == null) {
                    setSelectedCountry(this.defaultCCPCountry);
                }
            }
            if (isAutoDetectCountryEnabled() && !isInEditMode()) {
                setAutoDetectedCountry(true);
            }
            if (this.rememberLastSelection && !isInEditMode()) {
                loadLastSelectedCountryInCCP();
            }
            setArrowColor(obtainStyledAttributes.getColor(16, DEFAULT_UNSET));
            if (isInEditMode()) {
                i = obtainStyledAttributes.getColor(22, DEFAULT_UNSET);
            } else {
                i = obtainStyledAttributes.getColor(22, this.context.getResources().getColor(R.color.gb_colorPrimary));
            }
            if (i != DEFAULT_UNSET) {
                setContentColor(i);
            }
            if (isInEditMode()) {
                i2 = obtainStyledAttributes.getColor(30, 0);
            } else {
                i2 = obtainStyledAttributes.getColor(30, this.context.getResources().getColor(R.color.gb_colorPrimary));
            }
            if (i2 != 0) {
                setFlagBorderColor(i2);
            }
            setDialogBackgroundColor(obtainStyledAttributes.getColor(1, 0));
            setDialogTextColor(obtainStyledAttributes.getColor(14, 0));
            setDialogSearchEditTextTintColor(obtainStyledAttributes.getColor(7, 0));
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(42, 0);
            if (dimensionPixelSize > 0) {
                this.textView_selectedCountry.setTextSize(0, (float) dimensionPixelSize);
                setFlagSize(dimensionPixelSize);
                setArrowSize(dimensionPixelSize);
            }
            int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(17, 0);
            if (dimensionPixelSize2 > 0) {
                setArrowSize(dimensionPixelSize2);
            }
            this.searchAllowed = obtainStyledAttributes.getBoolean(0, true);
            setCcpClickable(obtainStyledAttributes.getBoolean(21, true));
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
        obtainStyledAttributes.recycle();
    }

    private void refreshArrowViewVisibility() {
        if (this.showArrow) {
            this.imageViewArrow.setVisibility(View.VISIBLE);
        } else {
            this.imageViewArrow.setVisibility(View.GONE);
        }
    }

    private void loadLastSelectedCountryInCCP() {
        String string = this.context.getSharedPreferences(this.CCP_PREF_FILE, 0).getString(this.selectionMemoryTag, null);
        if (string != null) {
            setCountryForNameCode(string);
        }
    }


    public void storeSelectedCountryNameCode(String str) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences(this.CCP_PREF_FILE, 0).edit();
        edit.putString(this.selectionMemoryTag, str);
        edit.apply();
    }


    public boolean isCcpDialogShowPhoneCode() {
        return this.ccpDialogShowPhoneCode;
    }

    public void setCcpDialogShowPhoneCode(boolean z) {
        this.ccpDialogShowPhoneCode = z;
    }

    public boolean getCcpDialogShowNameCode() {
        return this.ccpDialogShowNameCode;
    }

    public void setCcpDialogShowNameCode(boolean z) {
        this.ccpDialogShowNameCode = z;
    }

    public boolean getCcpDialogShowTitle() {
        return this.ccpDialogShowTitle;
    }

    public void setCcpDialogShowTitle(boolean z) {
        this.ccpDialogShowTitle = z;
    }

    public boolean getCcpDialogShowFlag() {
        return this.ccpDialogShowFlag;
    }

    public void setCcpDialogShowFlag(boolean z) {
        this.ccpDialogShowFlag = z;
    }


    public boolean isShowPhoneCode() {
        return this.showPhoneCode;
    }

    public void setShowPhoneCode(boolean z) {
        this.showPhoneCode = z;
        setSelectedCountry(this.selectedCCPCountry);
    }

    
    public DialogEventsListener getDialogEventsListener() {
        return this.dialogEventsListener;
    }

    public void setDialogEventsListener(DialogEventsListener dialogEventsListener2) {
        this.dialogEventsListener = dialogEventsListener2;
    }


    public int getFastScrollerBubbleTextAppearance() {
        return this.fastScrollerBubbleTextAppearance;
    }

    public void setFastScrollerBubbleTextAppearance(int i) {
        this.fastScrollerBubbleTextAppearance = i;
    }


    public int getFastScrollerHandleColor() {
        return this.fastScrollerHandleColor;
    }

    public void setFastScrollerHandleColor(int i) {
        this.fastScrollerHandleColor = i;
    }


    public int getFastScrollerBubbleColor() {
        return this.fastScrollerBubbleColor;
    }

    public void setFastScrollerBubbleColor(int i) {
        this.fastScrollerBubbleColor = i;
    }


    public TextGravity getCurrentTextGravity() {
        return this.currentTextGravity;
    }

    public void setCurrentTextGravity(TextGravity textGravity) {
        this.currentTextGravity = textGravity;
        applyTextGravity(textGravity.enumIndex);
    }

    private void applyTextGravity(int i) {
        if (i == TextGravity.LEFT.enumIndex) {
            this.textView_selectedCountry.setGravity(3);
        } else if (i == TextGravity.CENTER.enumIndex) {
            this.textView_selectedCountry.setGravity(17);
        } else {
            this.textView_selectedCountry.setGravity(5);
        }
    }

    private void updateLanguageToApply() {
        if (isInEditMode()) {
            Language language = this.customDefaultLanguage;
            if (language != null) {
                this.languageToApply = language;
            } else {
                this.languageToApply = Language.ENGLISH;
            }
        } else if (isAutoDetectLanguageEnabled()) {
            Language cCPLanguageFromLocale = getCCPLanguageFromLocale();
            if (cCPLanguageFromLocale != null) {
                this.languageToApply = cCPLanguageFromLocale;
            } else if (getCustomDefaultLanguage() != null) {
                this.languageToApply = getCustomDefaultLanguage();
            } else {
                this.languageToApply = Language.ENGLISH;
            }
        } else if (getCustomDefaultLanguage() != null) {
            this.languageToApply = this.customDefaultLanguage;
        } else {
            this.languageToApply = Language.ENGLISH;
        }
    }

    private Language getCCPLanguageFromLocale() {
        Locale locale = this.context.getResources().getConfiguration().locale;
        Language[] values = Language.values();
        for (Language language : values) {
            if (language.getCode().equalsIgnoreCase(locale.getLanguage()) && (language.getCountry() == null || language.getCountry().equalsIgnoreCase(locale.getCountry()) || (Build.VERSION.SDK_INT >= 21 && (language.getScript() == null || language.getScript().equalsIgnoreCase(locale.getScript()))))) {
                return language;
            }
        }
        return null;
    }

    private CCPCountry getDefaultCountry() {
        return this.defaultCCPCountry;
    }

    private void setDefaultCountry(CCPCountry cCPCountry) {
        this.defaultCCPCountry = cCPCountry;
    }

    public TextView getTextView_selectedCountry() {
        return this.textView_selectedCountry;
    }

    public void setTextView_selectedCountry(TextView textView) {
        this.textView_selectedCountry = textView;
    }

    public ImageView getImageViewFlag() {
        return this.imageViewFlag;
    }

    public void setImageViewFlag(ImageView imageView) {
        this.imageViewFlag = imageView;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private CCPCountry getSelectedCountry() {
        if (this.selectedCCPCountry == null) {
            setSelectedCountry(getDefaultCountry());
        }
        return this.selectedCCPCountry;
    }


    public void setSelectedCountry(CCPCountry cCPCountry) {
        this.countryDetectionBasedOnAreaAllowed = false;
        String str = "";
        this.lastCheckedAreaCode = str;
        if (cCPCountry != null || (cCPCountry = CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), this.preferredCountries, this.defaultCountryCode)) != null) {
            this.selectedCCPCountry = cCPCountry;
            if (this.showFlag && this.ccpUseEmoji) {
                if (!isInEditMode()) {
                    str = str + CCPCountry.getFlagEmoji(cCPCountry) + "  ";
                } else if (this.ccpUseDummyEmojiForPreview) {
                    str = str + "🏁​ ";
                } else {
                    str = str + CCPCountry.getFlagEmoji(cCPCountry) + "​ ";
                }
            }
            if (this.showFullName) {
                str = str + cCPCountry.getName();
            }
            if (this.showNameCode) {
                if (this.showFullName) {
                    str = str + " (" + cCPCountry.getNameCode().toUpperCase() + ")";
                } else {
                    str = str + " " + cCPCountry.getNameCode().toUpperCase();
                }
            }
            if (this.showPhoneCode) {
                if (str.length() > 0) {
                    str = str + "  ";
                }
                str = str + "+" + cCPCountry.getPhoneCode();
            }
            this.textView_selectedCountry.setText(str);
            if (!this.showFlag && str.length() == 0) {
                this.textView_selectedCountry.setText(str + "+" + cCPCountry.getPhoneCode());
            }
            this.imageViewFlag.setImageResource(cCPCountry.getFlagID());
            OnCountryChangeListener onCountryChangeListener2 = this.onCountryChangeListener;
            if (onCountryChangeListener2 != null) {
                onCountryChangeListener2.onCountrySelected();
            }
            updateFormattingTextWatcher();
            updateHint();
            if (!(this.editText_registeredCarrierNumber == null || this.phoneNumberValidityChangeListener == null)) {
                boolean isValidFullNumber = isValidFullNumber();
                this.reportedValidity = isValidFullNumber;
                this.phoneNumberValidityChangeListener.onValidityChanged(isValidFullNumber);
            }
            this.countryDetectionBasedOnAreaAllowed = true;
            if (this.countryChangedDueToAreaCode) {
                try {
                    this.editText_registeredCarrierNumber.setSelection(this.lastCursorPosition);
                    this.countryChangedDueToAreaCode = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            updateCountryGroup();
        }
    }

    private void updateCountryGroup() {
        this.currentCountryGroup = CCPCountryGroup.getCountryGroupForPhoneCode(getSelectedCountryCodeAsInt());
    }

    private void updateHint() {
        String str;
        if (this.editText_registeredCarrierNumber != null && this.hintExampleNumberEnabled) {
            Phonenumber.PhoneNumber exampleNumberForType = getPhoneUtil().getExampleNumberForType(getSelectedCountryNameCode(), getSelectedHintNumberType());
            String str2 = "";
            if (exampleNumberForType != null) {
                String str3 = exampleNumberForType.getNationalNumber() + str2;
                if (Build.VERSION.SDK_INT >= 21) {
                    str = PhoneNumberUtils.formatNumber(getSelectedCountryCodeWithPlus() + str3, getSelectedCountryNameCode());
                } else {
                    str = PhoneNumberUtils.formatNumber(getSelectedCountryCodeWithPlus() + str3);
                }
                str2 = str;
                if (str2 != null) {
                    str2 = str2.substring(getSelectedCountryCodeWithPlus().length()).trim();
                }
            }
            if (str2 == null) {
                str2 = this.originalHint;
            }
            this.editText_registeredCarrierNumber.setHint(str2);
        }
    }


    /* renamed from: com.whatsweb.whatswebscanner.gbwhats.countrypicker.CountryCodePicker$4  reason: invalid class name */
    public static class AnonymousClass4 {
        static final int[] $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType;

        /* JADX WARNING: Can't wrap try/catch for region: R(26:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|26) */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0078 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0084 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            int[] iArr = new int[PhoneNumberType.values().length];
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType = iArr;
            iArr[PhoneNumberType.MOBILE.ordinal()] = 1;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.FIXED_LINE.ordinal()] = 2;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.FIXED_LINE_OR_MOBILE.ordinal()] = 3;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.TOLL_FREE.ordinal()] = 4;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.PREMIUM_RATE.ordinal()] = 5;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.SHARED_COST.ordinal()] = 6;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.VOIP.ordinal()] = 7;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.PERSONAL_NUMBER.ordinal()] = 8;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.PAGER.ordinal()] = 9;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.UAN.ordinal()] = 10;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.VOICEMAIL.ordinal()] = 11;
            $SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[PhoneNumberType.UNKNOWN.ordinal()] = 12;
        }
    }

    private PhoneNumberUtil.PhoneNumberType getSelectedHintNumberType() {
        switch (AnonymousClass4.$SwitchMap$com$whatsweb$directmessages$countrypicker$CountryCodePicker$PhoneNumberType[this.hintExampleNumberType.ordinal()]) {
            case 1:
                return PhoneNumberUtil.PhoneNumberType.MOBILE;
            case 2:
                return PhoneNumberUtil.PhoneNumberType.FIXED_LINE;
            case 3:
                return PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE;
            case 4:
                return PhoneNumberUtil.PhoneNumberType.TOLL_FREE;
            case 5:
                return PhoneNumberUtil.PhoneNumberType.PREMIUM_RATE;
            case 6:
                return PhoneNumberUtil.PhoneNumberType.SHARED_COST;
            case 7:
                return PhoneNumberUtil.PhoneNumberType.VOIP;
            case 8:
                return PhoneNumberUtil.PhoneNumberType.PERSONAL_NUMBER;
            case 9:
                return PhoneNumberUtil.PhoneNumberType.PAGER;
            case 10:
                return PhoneNumberUtil.PhoneNumberType.UAN;
            case 11:
                return PhoneNumberUtil.PhoneNumberType.VOICEMAIL;
            case 12:
                return PhoneNumberUtil.PhoneNumberType.UNKNOWN;
            default:
                return PhoneNumberUtil.PhoneNumberType.MOBILE;
        }
    }

    public Language getLanguageToApply() {
        if (this.languageToApply == null) {
            updateLanguageToApply();
        }
        return this.languageToApply;
    }


    public void setLanguageToApply(Language language) {
        this.languageToApply = language;
    }

    private void updateFormattingTextWatcher() {
        EditText editText = this.editText_registeredCarrierNumber;
        if (editText != null && this.selectedCCPCountry != null) {
            String normalizeDigitsOnly = PhoneNumberUtil.normalizeDigitsOnly(getEditText_registeredCarrierNumber().getText().toString());
            InternationalPhoneTextWatcher internationalPhoneTextWatcher = this.formattingTextWatcher;
            if (internationalPhoneTextWatcher != null) {
                this.editText_registeredCarrierNumber.removeTextChangedListener(internationalPhoneTextWatcher);
            }
            TextWatcher textWatcher = this.areaCodeCountryDetectorTextWatcher;
            if (textWatcher != null) {
                this.editText_registeredCarrierNumber.removeTextChangedListener(textWatcher);
            }
            if (this.numberAutoFormattingEnabled) {
                InternationalPhoneTextWatcher internationalPhoneTextWatcher2 = new InternationalPhoneTextWatcher(this.context, getSelectedCountryNameCode(), getSelectedCountryCodeAsInt(), this.internationalFormattingOnly);
                this.formattingTextWatcher = internationalPhoneTextWatcher2;
                this.editText_registeredCarrierNumber.addTextChangedListener(internationalPhoneTextWatcher2);
            }
            if (this.detectCountryWithAreaCode) {
                TextWatcher countryDetectorTextWatcher = getCountryDetectorTextWatcher();
                this.areaCodeCountryDetectorTextWatcher = countryDetectorTextWatcher;
                this.editText_registeredCarrierNumber.addTextChangedListener(countryDetectorTextWatcher);
            }
            this.editText_registeredCarrierNumber.setText("");
            this.editText_registeredCarrierNumber.setText(normalizeDigitsOnly);
            EditText editText2 = this.editText_registeredCarrierNumber;
            editText2.setSelection(editText2.getText().length());
        } else if (editText == null) {
            String str = TAG;
            Log.v(str, "updateFormattingTextWatcher: EditText not registered " + this.selectionMemoryTag);
        } else {
            String str2 = TAG;
            Log.v(str2, "updateFormattingTextWatcher: selected country is null " + this.selectionMemoryTag);
        }
    }

    private TextWatcher getCountryDetectorTextWatcher() {
        if (this.editText_registeredCarrierNumber != null && this.areaCodeCountryDetectorTextWatcher == null) {
            this.areaCodeCountryDetectorTextWatcher = new TextWatcher() {
                /* class com.whatsweb.whatswebscanner.gbwhats.countrypicker.CountryCodePicker.AnonymousClass2 */
                String lastCheckedNumber = null;

                public void afterTextChanged(Editable editable) {
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    CCPCountry selectedCountry = CountryCodePicker.this.getSelectedCountry();
                    if (selectedCountry != null) {
                        String str = this.lastCheckedNumber;
                        if ((str == null || !str.equals(charSequence.toString())) && CountryCodePicker.this.countryDetectionBasedOnAreaAllowed) {
                            if (CountryCodePicker.this.currentCountryGroup != null) {
                                String obj = CountryCodePicker.this.getEditText_registeredCarrierNumber().getText().toString();
                                if (obj.length() >= CountryCodePicker.this.currentCountryGroup.areaCodeLength) {
                                    String normalizeDigitsOnly = PhoneNumberUtil.normalizeDigitsOnly(obj);
                                    if (normalizeDigitsOnly.length() >= CountryCodePicker.this.currentCountryGroup.areaCodeLength) {
                                        String substring = normalizeDigitsOnly.substring(0, CountryCodePicker.this.currentCountryGroup.areaCodeLength);
                                        if (!substring.equals(CountryCodePicker.this.lastCheckedAreaCode)) {
                                            CCPCountry countryForAreaCode = CountryCodePicker.this.currentCountryGroup.getCountryForAreaCode(CountryCodePicker.this.context, CountryCodePicker.this.getLanguageToApply(), substring);
                                            if (!countryForAreaCode.equals(selectedCountry)) {
                                                CountryCodePicker.this.countryChangedDueToAreaCode = true;
                                                CountryCodePicker.this.lastCursorPosition = Selection.getSelectionEnd(charSequence);
                                                CountryCodePicker.this.setSelectedCountry(countryForAreaCode);
                                            }
                                            CountryCodePicker.this.lastCheckedAreaCode = substring;
                                        }
                                    }
                                }
                            }
                            this.lastCheckedNumber = charSequence.toString();
                        }
                    }
                }
            };
        }
        return this.areaCodeCountryDetectorTextWatcher;
    }


    public Language getCustomDefaultLanguage() {
        return this.customDefaultLanguage;
    }

    private void setCustomDefaultLanguage(Language language) {
        this.customDefaultLanguage = language;
        updateLanguageToApply();
        setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(this.context, getLanguageToApply(), this.selectedCCPCountry.getNameCode()));
    }

    private View getHolderView() {
        return this.holderView;
    }

    private void setHolderView(View view) {
        this.holderView = view;
    }

    public RelativeLayout getHolder() {
        return this.holder;
    }

    private void setHolder(RelativeLayout relativeLayout) {
        this.holder = relativeLayout;
    }


    public boolean isAutoDetectLanguageEnabled() {
        return this.autoDetectLanguageEnabled;
    }


    public boolean isAutoDetectCountryEnabled() {
        return this.autoDetectCountryEnabled;
    }


    public boolean isDialogKeyboardAutoPopup() {
        return this.dialogKeyboardAutoPopup;
    }

    public void setDialogKeyboardAutoPopup(boolean z) {
        this.dialogKeyboardAutoPopup = z;
    }


    public boolean isShowFastScroller() {
        return this.showFastScroller;
    }

    public void setShowFastScroller(boolean z) {
        this.showFastScroller = z;
    }

    
    public boolean isShowCloseIcon() {
        return this.showCloseIcon;
    }

    public void showCloseIcon(boolean z) {
        this.showCloseIcon = z;
    }


    public EditText getEditText_registeredCarrierNumber() {
        return this.editText_registeredCarrierNumber;
    }

    public void setEditText_registeredCarrierNumber(EditText editText) {
        this.editText_registeredCarrierNumber = editText;
        if (editText.getHint() != null) {
            this.originalHint = this.editText_registeredCarrierNumber.getHint().toString();
        }
        updateValidityTextWatcher();
        updateFormattingTextWatcher();
        updateHint();
    }

    private void updateValidityTextWatcher() {
        try {
            this.editText_registeredCarrierNumber.removeTextChangedListener(this.validityTextWatcher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isValidFullNumber = isValidFullNumber();
        this.reportedValidity = isValidFullNumber;
        PhoneNumberValidityChangeListener phoneNumberValidityChangeListener2 = this.phoneNumberValidityChangeListener;
        if (phoneNumberValidityChangeListener2 != null) {
            phoneNumberValidityChangeListener2.onValidityChanged(isValidFullNumber);
        }
        TextWatcher r0 = new TextWatcher() {
            /* class com.whatsweb.whatswebscanner.gbwhats.countrypicker.CountryCodePicker.AnonymousClass3 */

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                boolean isValidFullNumber;
                if (CountryCodePicker.this.phoneNumberValidityChangeListener != null && (isValidFullNumber = CountryCodePicker.this.isValidFullNumber()) != CountryCodePicker.this.reportedValidity) {
                    CountryCodePicker.this.reportedValidity = isValidFullNumber;
                    CountryCodePicker.this.phoneNumberValidityChangeListener.onValidityChanged(CountryCodePicker.this.reportedValidity);
                }
            }
        };
        this.validityTextWatcher = r0;
        this.editText_registeredCarrierNumber.addTextChangedListener(r0);
    }

    private LayoutInflater getmInflater() {
        return this.mInflater;
    }

    private OnClickListener getCountryCodeHolderClickListener() {
        return this.countryCodeHolderClickListener;
    }


    public int getDialogBackgroundColor() {
        return this.dialogBackgroundColor;
    }

    public void setDialogBackgroundColor(int i) {
        this.dialogBackgroundColor = i;
    }


    public int getDialogSearchEditTextTintColor() {
        return this.dialogSearchEditTextTintColor;
    }

    public void setDialogSearchEditTextTintColor(int i) {
        this.dialogSearchEditTextTintColor = i;
    }


    public int getDialogTextColor() {
        return this.dialogTextColor;
    }

    public void setDialogTextColor(int i) {
        this.dialogTextColor = i;
    }


    public int getDialogTypeFaceStyle() {
        return this.dialogTypeFaceStyle;
    }


    public Typeface getDialogTypeFace() {
        return this.dialogTypeFace;
    }

    public void setDialogTypeFace(Typeface typeface) {
        try {
            this.dialogTypeFace = typeface;
            this.dialogTypeFaceStyle = DEFAULT_UNSET;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void refreshPreferredCountries() {
        String str = this.countryPreference;
        if (str == null || str.length() == 0) {
            this.preferredCountries = null;
        } else {
            ArrayList arrayList = new ArrayList();
            for (String str2 : this.countryPreference.split(",")) {
                CCPCountry countryForNameCodeFromCustomMasterList = CCPCountry.getCountryForNameCodeFromCustomMasterList(getContext(), this.customMasterCountriesList, getLanguageToApply(), str2);
                if (countryForNameCodeFromCustomMasterList != null && !isAlreadyInList(countryForNameCodeFromCustomMasterList, arrayList)) {
                    arrayList.add(countryForNameCodeFromCustomMasterList);
                }
            }
            if (arrayList.size() == 0) {
                this.preferredCountries = null;
            } else {
                this.preferredCountries = arrayList;
            }
        }
        List<CCPCountry> list = this.preferredCountries;
        if (list != null) {
            for (CCPCountry cCPCountry : list) {
                cCPCountry.log();
            }
        }
    }


    public void refreshCustomMasterList() {
        String str = this.customMasterCountriesParam;
        if (str == null || str.length() == 0) {
            String str2 = this.excludedCountriesParam;
            if (str2 == null || str2.length() == 0) {
                this.customMasterCountriesList = null;
            } else {
                this.excludedCountriesParam = this.excludedCountriesParam.toLowerCase();
                List<CCPCountry> libraryMasterCountryList = CCPCountry.getLibraryMasterCountryList(this.context, getLanguageToApply());
                ArrayList arrayList = new ArrayList();
                for (CCPCountry cCPCountry : libraryMasterCountryList) {
                    if (!this.excludedCountriesParam.contains(cCPCountry.getNameCode().toLowerCase())) {
                        arrayList.add(cCPCountry);
                    }
                }
                if (arrayList.size() > 0) {
                    this.customMasterCountriesList = arrayList;
                } else {
                    this.customMasterCountriesList = null;
                }
            }
        } else {
            ArrayList arrayList2 = new ArrayList();
            for (String str3 : this.customMasterCountriesParam.split(",")) {
                CCPCountry countryForNameCodeFromLibraryMasterList = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), str3);
                if (countryForNameCodeFromLibraryMasterList != null && !isAlreadyInList(countryForNameCodeFromLibraryMasterList, arrayList2)) {
                    arrayList2.add(countryForNameCodeFromLibraryMasterList);
                }
            }
            if (arrayList2.size() == 0) {
                this.customMasterCountriesList = null;
            } else {
                this.customMasterCountriesList = arrayList2;
            }
        }
        List<CCPCountry> list = this.customMasterCountriesList;
        if (list != null) {
            for (CCPCountry cCPCountry2 : list) {
                cCPCountry2.log();
            }
        }
    }


    public List<CCPCountry> getCustomMasterCountriesList() {
        return this.customMasterCountriesList;
    }


    public void setCustomMasterCountriesList(List<CCPCountry> list) {
        this.customMasterCountriesList = list;
    }


    public String getCustomMasterCountriesParam() {
        return this.customMasterCountriesParam;
    }

    public void setCustomMasterCountries(String str) {
        this.customMasterCountriesParam = str;
    }

    public void setExcludedCountries(String str) {
        this.excludedCountriesParam = str;
        refreshCustomMasterList();
    }


    public boolean isCcpClickable() {
        return this.ccpClickable;
    }

    public void setCcpClickable(boolean z) {
        this.ccpClickable = z;
        if (!z) {
            this.relativeClickConsumer.setOnClickListener(null);
            this.relativeClickConsumer.setClickable(false);
            this.relativeClickConsumer.setEnabled(false);
            return;
        }
        this.relativeClickConsumer.setOnClickListener(this.countryCodeHolderClickListener);
        this.relativeClickConsumer.setClickable(true);
        this.relativeClickConsumer.setEnabled(true);
    }

    private boolean isAlreadyInList(CCPCountry cCPCountry, List<CCPCountry> list) {
        if (cCPCountry == null || list == null) {
            return false;
        }
        for (CCPCountry cCPCountry2 : list) {
            if (cCPCountry2.getNameCode().equalsIgnoreCase(cCPCountry.getNameCode())) {
                return true;
            }
        }
        return false;
    }

    private String detectCarrierNumber(String str, CCPCountry cCPCountry) {
        int indexOf;
        return (cCPCountry == null || str == null || str.isEmpty() || (indexOf = str.indexOf(cCPCountry.getPhoneCode())) == -1) ? str : str.substring(indexOf + cCPCountry.getPhoneCode().length());
    }

    private Language getLanguageEnum(int i) {
        if (i < Language.values().length) {
            return Language.values()[i];
        }
        return Language.ENGLISH;
    }


    public String getDialogTitle() {
        String dialogTitle = CCPCountry.getDialogTitle(this.context, getLanguageToApply());
        CustomDialogTextProvider customDialogTextProvider2 = this.customDialogTextProvider;
        return customDialogTextProvider2 != null ? customDialogTextProvider2.getCCPDialogTitle(getLanguageToApply(), dialogTitle) : dialogTitle;
    }


    public String getSearchHintText() {
        String searchHintMessage = CCPCountry.getSearchHintMessage(this.context, getLanguageToApply());
        CustomDialogTextProvider customDialogTextProvider2 = this.customDialogTextProvider;
        return customDialogTextProvider2 != null ? customDialogTextProvider2.getCCPDialogSearchHintText(getLanguageToApply(), searchHintMessage) : searchHintMessage;
    }


    public String getNoResultACK() {
        String noResultFoundAckMessage = CCPCountry.getNoResultFoundAckMessage(this.context, getLanguageToApply());
        CustomDialogTextProvider customDialogTextProvider2 = this.customDialogTextProvider;
        return customDialogTextProvider2 != null ? customDialogTextProvider2.getCCPDialogNoResultACK(getLanguageToApply(), noResultFoundAckMessage) : noResultFoundAckMessage;
    }

    @Deprecated
    public void setDefaultCountryUsingPhoneCode(int i) {
        CCPCountry countryForCode = CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), this.preferredCountries, i);
        if (countryForCode != null) {
            this.defaultCountryCode = i;
            setDefaultCountry(countryForCode);
        }
    }

    public void setDefaultCountryUsingNameCode(String str) {
        CCPCountry countryForNameCodeFromLibraryMasterList = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), str);
        if (countryForNameCodeFromLibraryMasterList != null) {
            this.defaultCountryNameCode = countryForNameCodeFromLibraryMasterList.getNameCode();
            setDefaultCountry(countryForNameCodeFromLibraryMasterList);
        }
    }

    public String getDefaultCountryCode() {
        return this.defaultCCPCountry.phoneCode;
    }

    public int getDefaultCountryCodeAsInt() {
        try {
            return Integer.parseInt(getDefaultCountryCode());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getDefaultCountryCodeWithPlus() {
        return "+" + getDefaultCountryCode();
    }

    public String getDefaultCountryName() {
        return getDefaultCountry().name;
    }

    public String getDefaultCountryNameCode() {
        return getDefaultCountry().nameCode.toUpperCase();
    }

    public void resetToDefaultCountry() {
        CCPCountry countryForNameCodeFromLibraryMasterList = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), getDefaultCountryNameCode());
        this.defaultCCPCountry = countryForNameCodeFromLibraryMasterList;
        setSelectedCountry(countryForNameCodeFromLibraryMasterList);
    }

    public String getSelectedCountryCode() {
        return getSelectedCountry().phoneCode;
    }

    public String getSelectedCountryCodeWithPlus() {
        return "+" + getSelectedCountryCode();
    }

    public int getSelectedCountryCodeAsInt() {
        try {
            return Integer.parseInt(getSelectedCountryCode());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getSelectedCountryName() {
        return getSelectedCountry().name;
    }

    public String getSelectedCountryEnglishName() {
        return getSelectedCountry().getEnglishName();
    }

    public String getSelectedCountryNameCode() {
        return getSelectedCountry().nameCode.toUpperCase();
    }

    public void setCountryForPhoneCode(int i) {
        CCPCountry countryForCode = CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), this.preferredCountries, i);
        if (countryForCode == null) {
            if (this.defaultCCPCountry == null) {
                this.defaultCCPCountry = CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), this.preferredCountries, this.defaultCountryCode);
            }
            setSelectedCountry(this.defaultCCPCountry);
            return;
        }
        setSelectedCountry(countryForCode);
    }

    public void setCountryForNameCode(String str) {
        CCPCountry countryForNameCodeFromLibraryMasterList = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), str);
        if (countryForNameCodeFromLibraryMasterList == null) {
            if (this.defaultCCPCountry == null) {
                this.defaultCCPCountry = CCPCountry.getCountryForCode(getContext(), getLanguageToApply(), this.preferredCountries, this.defaultCountryCode);
            }
            setSelectedCountry(this.defaultCCPCountry);
            return;
        }
        setSelectedCountry(countryForNameCodeFromLibraryMasterList);
    }

    public void registerCarrierNumberEditText(EditText editText) {
        setEditText_registeredCarrierNumber(editText);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:2|3|4|5|6|8) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x0009 */
    public void deregisterCarrierNumberEditText() {
        EditText editText = this.editText_registeredCarrierNumber;
        if (editText != null) {
            editText.removeTextChangedListener(this.validityTextWatcher);
            this.editText_registeredCarrierNumber.removeTextChangedListener(this.formattingTextWatcher);
            this.editText_registeredCarrierNumber.setHint("");
            this.editText_registeredCarrierNumber = null;
        }
    }

    private Phonenumber.PhoneNumber getEnteredPhoneNumber() throws NumberParseException {
        EditText editText = this.editText_registeredCarrierNumber;
        return getPhoneUtil().parse(editText != null ? PhoneNumberUtil.normalizeDigitsOnly(editText.getText().toString()) : "", getSelectedCountryNameCode());
    }

    public String getFullNumber() {
        try {
            return getPhoneUtil().format(getEnteredPhoneNumber(), PhoneNumberUtil.PhoneNumberFormat.E164).substring(1);
        } catch (NumberParseException unused) {
            Log.e(TAG, "getFullNumber: Could not parse number");
            return getSelectedCountryCode();
        }
    }

    public void setFullNumber(String str) {
        CCPCountry countryForNumber = CCPCountry.getCountryForNumber(getContext(), getLanguageToApply(), this.preferredCountries, str);
        if (countryForNumber == null) {
            countryForNumber = getDefaultCountry();
        }
        setSelectedCountry(countryForNumber);
        String detectCarrierNumber = detectCarrierNumber(str, countryForNumber);
        if (getEditText_registeredCarrierNumber() != null) {
            getEditText_registeredCarrierNumber().setText(detectCarrierNumber);
            updateFormattingTextWatcher();
            return;
        }
        Log.w(TAG, "EditText for carrier number is not registered. Register it using registerCarrierNumberEditText() before getFullNumber() or setFullNumber().");
    }

    public String getFormattedFullNumber() {
        try {
            Phonenumber.PhoneNumber enteredPhoneNumber = getEnteredPhoneNumber();
            return "+" + getPhoneUtil().format(enteredPhoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL).substring(1);
        } catch (NumberParseException unused) {
            Log.e(TAG, "getFullNumber: Could not parse number");
            return getSelectedCountryCode();
        }
    }

    public String getFullNumberWithPlus() {
        try {
            return getPhoneUtil().format(getEnteredPhoneNumber(), PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException unused) {
            Log.e(TAG, "getFullNumber: Could not parse number");
            return getSelectedCountryCode();
        }
    }

    public int getContentColor() {
        return this.contentColor;
    }

    public void setContentColor(int i) {
        this.contentColor = i;
        this.textView_selectedCountry.setTextColor(i);
        if (this.arrowColor == DEFAULT_UNSET) {
            this.imageViewArrow.setColorFilter(this.contentColor, PorterDuff.Mode.SRC_IN);
        }
    }

    public void setArrowColor(int i) {
        this.arrowColor = i;
        if (i == DEFAULT_UNSET) {
            int i2 = this.contentColor;
            if (i2 != DEFAULT_UNSET) {
                this.imageViewArrow.setColorFilter(i2, PorterDuff.Mode.SRC_IN);
                return;
            }
            return;
        }
        this.imageViewArrow.setColorFilter(i, PorterDuff.Mode.SRC_IN);
    }

    public void setFlagBorderColor(int i) {
        this.borderFlagColor = i;
        this.linearFlagBorder.setBackgroundColor(i);
    }

    public void setTextSize(int i) {
        if (i > 0) {
            this.textView_selectedCountry.setTextSize(0, (float) i);
            setArrowSize(i);
            setFlagSize(i);
        }
    }

    public void setArrowSize(int i) {
        if (i > 0) {
            LayoutParams layoutParams = (LayoutParams) this.imageViewArrow.getLayoutParams();
            layoutParams.width = i;
            layoutParams.height = i;
            this.imageViewArrow.setLayoutParams(layoutParams);
        }
    }

    public void showNameCode(boolean z) {
        this.showNameCode = z;
        setSelectedCountry(this.selectedCCPCountry);
    }

    public void showArrow(boolean z) {
        this.showArrow = z;
        refreshArrowViewVisibility();
    }

    public void setCountryPreference(String str) {
        this.countryPreference = str;
    }

    public void changeDefaultLanguage(Language language) {
        setCustomDefaultLanguage(language);
    }

    public void setTypeFace(Typeface typeface) {
        try {
            this.textView_selectedCountry.setTypeface(typeface);
            setDialogTypeFace(typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDialogTypeFace(Typeface typeface, int i) {
        try {
            this.dialogTypeFace = typeface;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTypeFace(Typeface typeface, int i) {
        try {
            this.textView_selectedCountry.setTypeface(typeface, i);
            setDialogTypeFace(typeface, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnCountryChangeListener(OnCountryChangeListener onCountryChangeListener2) {
        this.onCountryChangeListener = onCountryChangeListener2;
    }

    public void setFlagSize(int i) {
        this.imageViewFlag.getLayoutParams().height = i;
        this.imageViewFlag.requestLayout();
    }

    public void showFlag(boolean z) {
        this.showFlag = z;
        refreshFlagVisibility();
        if (!isInEditMode()) {
            setSelectedCountry(this.selectedCCPCountry);
        }
    }

    private void refreshFlagVisibility() {
        if (!this.showFlag) {
            this.linearFlagHolder.setVisibility(View.GONE);
        } else if (this.ccpUseEmoji) {
            this.linearFlagHolder.setVisibility(View.GONE);
        } else {
            this.linearFlagHolder.setVisibility(View.VISIBLE);
        }
    }

    public void useFlagEmoji(boolean z) {
        this.ccpUseEmoji = z;
        refreshFlagVisibility();
        setSelectedCountry(this.selectedCCPCountry);
    }

    public void showFullName(boolean z) {
        this.showFullName = z;
        setSelectedCountry(this.selectedCCPCountry);
    }

    public boolean isSearchAllowed() {
        return this.searchAllowed;
    }

    public void setSearchAllowed(boolean z) {
        this.searchAllowed = z;
    }

    public void setPhoneNumberValidityChangeListener(PhoneNumberValidityChangeListener phoneNumberValidityChangeListener2) {
        this.phoneNumberValidityChangeListener = phoneNumberValidityChangeListener2;
        if (this.editText_registeredCarrierNumber != null) {
            boolean isValidFullNumber = isValidFullNumber();
            this.reportedValidity = isValidFullNumber;
            phoneNumberValidityChangeListener2.onValidityChanged(isValidFullNumber);
        }
    }

    public void setAutoDetectionFailureListener(FailureListener failureListener2) {
        this.failureListener = failureListener2;
    }

    public void setCustomDialogTextProvider(CustomDialogTextProvider customDialogTextProvider2) {
        this.customDialogTextProvider = customDialogTextProvider2;
    }

    public void launchCountrySelectionDialog() {
        launchCountrySelectionDialog(null);
    }

    public void launchCountrySelectionDialog(String str) {
        CountryCodeDialog.openCountryCodeDialog(this.codePicker, str);
    }

    public boolean isValidFullNumber() {
        try {
            if (getEditText_registeredCarrierNumber() == null || getEditText_registeredCarrierNumber().getText().length() == 0) {
                if (getEditText_registeredCarrierNumber() == null) {
                    Toast.makeText(this.context, "No editText for Carrier number found.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            PhoneNumberUtil phoneUtil2 = getPhoneUtil();
            return getPhoneUtil().isValidNumber(phoneUtil2.parse("+" + this.selectedCCPCountry.getPhoneCode() + getEditText_registeredCarrierNumber().getText().toString(), this.selectedCCPCountry.getNameCode()));
        } catch (NumberParseException unused) {
        }
        return false;
    }

    private PhoneNumberUtil getPhoneUtil() {
        if (this.phoneUtil == null) {
            this.phoneUtil = PhoneNumberUtil.createInstance(this.context);
        }
        return this.phoneUtil;
    }

    public void setAutoDetectedCountry(boolean z) {
        int i = 0;
        boolean z2 = false;
        while (true) {
            try {
                if (i < this.selectedAutoDetectionPref.representation.length()) {
                    switch (this.selectedAutoDetectionPref.representation.charAt(i)) {
                        case '1':
                            z2 = detectSIMCountry(false);
                            break;
                        case '2':
                            z2 = detectNetworkCountry(false);
                            break;
                        case '3':
                            z2 = detectLocaleCountry(false);
                            break;
                    }
                    if (!z2) {
                        FailureListener failureListener2 = this.failureListener;
                        if (failureListener2 != null) {
                            failureListener2.onCountryAutoDetectionFailed();
                        }
                        i++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                String str = TAG;
                Log.w(str, "setAutoDetectCountry: Exception" + e.getMessage());
                if (z) {
                    resetToDefaultCountry();
                    return;
                }
                return;
            }
        }
//        if (!z2 && z) {
//            resetToDefaultCountry();
//        }
    }

    public boolean detectSIMCountry(boolean z) {
        try {
            String simCountryIso = ((TelephonyManager) this.context.getSystemService("phone")).getSimCountryIso();
            if (simCountryIso != null) {
                if (!simCountryIso.isEmpty()) {
                    setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), simCountryIso));
                    return true;
                }
            }
            if (z) {
                resetToDefaultCountry();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            if (z) {
                resetToDefaultCountry();
            }
            return false;
        }
    }

    public boolean detectNetworkCountry(boolean z) {
        try {
            String networkCountryIso = ((TelephonyManager) this.context.getSystemService("phone")).getNetworkCountryIso();
            if (networkCountryIso != null) {
                if (!networkCountryIso.isEmpty()) {
                    setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), networkCountryIso));
                    return true;
                }
            }
            if (z) {
                resetToDefaultCountry();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            if (z) {
                resetToDefaultCountry();
            }
            return false;
        }
    }

    public boolean detectLocaleCountry(boolean z) {
        try {
            String country = this.context.getResources().getConfiguration().locale.getCountry();
            if (country != null) {
                if (!country.isEmpty()) {
                    setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getContext(), getLanguageToApply(), country));
                    return true;
                }
            }
            if (z) {
                resetToDefaultCountry();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            if (z) {
                resetToDefaultCountry();
            }
            return false;
        }
    }

    public void setCountryAutoDetectionPref(AutoDetectionPref autoDetectionPref) {
        this.selectedAutoDetectionPref = autoDetectionPref;
    }

    
    public void onUserTappedCountry(CCPCountry cCPCountry) {
        CountryCodePicker countryCodePicker = this.codePicker;
        if (countryCodePicker.rememberLastSelection) {
            countryCodePicker.storeSelectedCountryNameCode(cCPCountry.getNameCode());
        }
        setSelectedCountry(cCPCountry);
    }

    public void setDetectCountryWithAreaCode(boolean z) {
        this.detectCountryWithAreaCode = z;
        updateFormattingTextWatcher();
    }

    public void setHintExampleNumberEnabled(boolean z) {
        this.hintExampleNumberEnabled = z;
        updateHint();
    }

    public void setHintExampleNumberType(PhoneNumberType phoneNumberType) {
        this.hintExampleNumberType = phoneNumberType;
        updateHint();
    }

    public boolean isDialogInitialScrollToSelectionEnabled() {
        return this.ccpDialogInitialScrollToSelection;
    }

    public void enableDialogInitialScrollToSelection(boolean z) {
        this.ccpDialogInitialScrollToSelection = this.ccpDialogInitialScrollToSelection;
    }

    public void overrideClickListener(OnClickListener onClickListener) {
        this.customClickListener = onClickListener;
    }

    public enum Language {
        AFRIKAANS("af"),
        ARABIC("ar"),
        BENGALI("bn"),
        CHINESE_SIMPLIFIED("zh", "CN", "Hans"),
        CHINESE_TRADITIONAL("zh", "TW", "Hant"),
        CZECH("cs"),
        DANISH("da"),
        DUTCH("nl"),
        ENGLISH("en"),
        FARSI("fa"),
        FRENCH("fr"),
        GERMAN("de"),
        GREEK("el"),
        GUJARATI("gu"),
        HEBREW("iw"),
        HINDI("hi"),
        INDONESIA("in"),
        ITALIAN("it"),
        JAPANESE("ja"),
        KOREAN("ko"),
        POLISH("pl"),
        PORTUGUESE("pt"),
        PUNJABI("pa"),
        RUSSIAN("ru"),
        SLOVAK("sk"),
        SPANISH("es"),
        SWEDISH("sv"),
        TURKISH("tr"),
        UKRAINIAN("uk"),
        UZBEK("uz"),
        VIETNAMESE("vi");
        
        private String code;
        private String country;
        private String script;

        private Language(String str, String str2, String str3) {
            this.code = str;
            this.country = str2;
            this.script = str3;
        }

        private Language(String str) {
            this.code = str;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String str) {
            this.code = str;
        }

        public String getCountry() {
            return this.country;
        }

        public void setCountry(String str) {
            this.country = str;
        }

        public String getScript() {
            return this.script;
        }

        public void setScript(String str) {
            this.script = str;
        }
    }

    public enum AutoDetectionPref {
        SIM_ONLY("1"),
        NETWORK_ONLY("2"),
        LOCALE_ONLY("3"),
        SIM_NETWORK("12"),
        NETWORK_SIM("21"),
        SIM_LOCALE("13"),
        LOCALE_SIM("31"),
        NETWORK_LOCALE("23"),
        LOCALE_NETWORK("32"),
        SIM_NETWORK_LOCALE("123"),
        SIM_LOCALE_NETWORK("132"),
        NETWORK_SIM_LOCALE("213"),
        NETWORK_LOCALE_SIM("231"),
        LOCALE_SIM_NETWORK("312"),
        LOCALE_NETWORK_SIM("321");
        
        String representation;

        private AutoDetectionPref(String str) {
            this.representation = str;
        }

        public static AutoDetectionPref getPrefForValue(String str) {
            AutoDetectionPref[] values = values();
            for (AutoDetectionPref autoDetectionPref : values) {
                if (autoDetectionPref.representation.equals(str)) {
                    return autoDetectionPref;
                }
            }
            return SIM_NETWORK_LOCALE;
        }
    }

    public enum TextGravity {
        LEFT(-1),
        CENTER(0),
        RIGHT(1);
        
        int enumIndex;

        private TextGravity(int i) {
            this.enumIndex = i;
        }
    }

    
    public void onDetachedFromWindow() {
        CountryCodeDialog.clear();
        super.onDetachedFromWindow();
    }
}
