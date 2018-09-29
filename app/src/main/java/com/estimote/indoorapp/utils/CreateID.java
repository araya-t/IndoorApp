package com.estimote.indoorapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateID {

    Date now = new Date();
    int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));


}
