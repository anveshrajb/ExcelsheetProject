package shilpakarita.indobytes.com.excelsheetproject.Helper;

import java.io.IOException;

/**
 * Created by inforlinx on 12/4/15.
 */
public interface WebServiceCallBack {

    void onJSONResponse(String jsonResponse, String type) throws IOException;

    void onFailure();
}

