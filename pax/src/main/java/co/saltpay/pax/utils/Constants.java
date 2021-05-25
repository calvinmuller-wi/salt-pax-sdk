package co.saltpay.pax.utils;

public class Constants {

    public static final String AUTH_PAYMENT = "authorization";
    public static final String SALE_PAYMENT = "payment";
    public static final String REFUND_PAYMENT = "refund";

    public static final String ISK_CURRENCY = "ISK";
    public static final Integer ISK_AMOUNT_MULTIPLYING_FACTOR = 100;

    public static final String CREDIT_PAYMENT_CODE = "C";

    public static final String PAX_A80 = "A80";
    public static final String PAX_A920 = "A920";
    public static final String TELPO_TPS900 = "TPS900";

    public static final String FALLBACK_TYPE = "fallbackType";
    public static final String TXN_COUNTER_VALUE = "0000000";
    public static final String SIGNATURE_REQUIRED = "sign_required";
    public static final String PIN_ENTERED = "pin_entered";

    public static final String SUCCESS_RESPONSE = "success_response";
    public static final String SOFTWARE_UPDATE_URL = "software_update_url";
    public static final String CONFIG_UPDATE_REQUIRED = "config_update";
    public static final String CVM_TYPE = "cvm_type";

    public static final byte INDEX_TPK = 0x03;
    public static final byte INDEX_TIK = 0x01;
    public static final byte INDEX_TMK = 0x01;

    //CLCA Constants
    public static final String MASTERCARD_AID = "A000000004";
    public static final String BINARY_1E_VALUE = "11110";
    public static final String VALUE_1E = "1E";

    public static final String INACTIVE = "0";
    public static final String ACTIVE = "1";
    public static final char BIT_ACTIVE = '1';
    public static final int FALLBACK_ACTIVE = 1;
    public static final int MAX_LIMIT_REACHED = 2;
    public static final int MAX_AMOUNT_LENGTH = 12;
    public static final int SEE_PHONE_TIMEOUT = 10 * 1000;

    // Key for sharedsecret
    public static final String SHARED_SECRET_VALUE = "0102030405060708091011121314151617181920212223242526272829303132";
    public static final String PAX_KERNEL_VERSION = "650";
    public static final String TELPO_KERNEL_VERSION = "4.3";

    public static final String PAX_EXPECTED_PIN_LENGTH = "4,5,6,7,8,9,10,11,12";
    public static final byte PAX_MINIMUM_EXPECTED_PIN_LENGTH = 4;
    public static final String DEFAULT_VALUE_ZERO = "0";
    public static final String DEFAULT_TIP_AMOUNT = "000000000000";
    public static final int DEFAULT_START_INDEX = 0;
    public static final int MAX_SEE_PHONE_COUNTER = 2;
    public static final int MAX_BCD_VALUE_LENGTH = 10;

    public static final String FAILED_MS_COUNT_TELPO = "00";
    public static final long CASH_BACK_AMOUNT = 0;
    public static final byte SUPPORTED = 1;
    public static final byte NOT_SUPPORTED = 0;
    public static final byte ONLINE_PIN_TELPO = 0;
    public static final String PAYPASS_AID = "A0000000041010";
    public static final String PAYWAVE_AID = "A0000000031010";
    public static final String INTERAC_RID = "A000000277";
    public static final String RID_VISA = "A000000003";
    public static final String AUTH_RESPONSE_SUCCESS_CODE = "00";
    public static final String AUTH_RESPONSE_DECLINE_CODE = "05";
    public static final String AUTH_RESPONSE_NETWORK_FAILURE = "5A33";
    public static final String DISABLE_CTLS_APP_SELECTION = "00";
    public static final String MOBILE_PASS_CODE_CVM1 = "41";
    public static final String MOBILE_PASS_CODE_CVM2 = "01";

    public static final int CALLBACK_ERR = -1;
    public static final int CALLBACK_CANCEL = -2;
    public static final int CALLBACK_SUCCESS = 0;

    public static final int TELPO_SWIPE_CHECKING_TIME = 80;
    public static final int TELPO_NFC_CHECKING_TIME = 80;
    public static final int TELPO_CHIP_CHECKING_TIME = 80;
    public static final int TELPO_MAG = 0;
    public static final int TELPO_ICC = 1;
    public static final int TELPO_NFC = 2;
    public static final int TELPO_KEY_INDEX = 1;
    public static final int TELPO_MAX_PIN_LENGTH = 6;
    public static final int TELPO_MIN_PIN_LENGTH = 4;
    public static final int TELPO_FALLBACK_TO_CONTACT_MODE = -42;
    public static final int BEEP_COUNTER = 2;
    public static final int STATUS_WORD_OF_FINAL_SELECTION = 0x03;

    public static final String EMPTY_PIN_BLOCK = "0000000000000000";
    public static final String RID_MC = "A000000004";
    public static final String TAC_DENIAL_REFUND = "FFFFFFFFFF";
    public static final String TAC_ONLINE_REFUND = "0000000000";
    public static final String TAC_DEFAULT_REFUND = "0000000000";
    public static final String SCA_CVM_LIMIT = "00000000";
    public static final String SCA_FALLBACK_CONTACT = "00000";
    public static final String ASF_VALUE = "00";
    public static final String CANADA_COUNTRY_CODE = "CAN";
    public static final String SW_DATA = "9000";

    public static final String CONTACTLESS_BEEP_SUCCESS = "1500:500";
    public static final String CONTACTLESS_BEEP_ALERT = "750:200";
    public static final long CONTACTLESS_BEEP_ALERT_WAIT_MS = 200;
    public static final long CONTACTLESS_REMOVE_CARD_MESSAGE_DELAY_MS = 750;

    public static final byte[] PAX_DEFAULT_CERT_SN = new byte[]{0x00, 0x07, 0x11};
}