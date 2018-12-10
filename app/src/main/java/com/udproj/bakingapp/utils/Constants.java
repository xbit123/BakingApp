package com.udproj.bakingapp.utils;

import com.udproj.bakingapp.R;

import java.util.HashMap;

public final class Constants {
    public static final String INTENT_CAKE = "CAKE";
    public static final String INTENT_STEP_ID = "STEP_ID";
    public static final int STEP_ID_DEFAULT_VALUE = 0;
    public static final int DEFAULT_ASPECT_RATIO_WIDTH = 16;
    public static final int DEFAULT_ASPECT_RATIO_HEIGHT = 9;
    public static HashMap<String, Integer> INGREDIENT_MAP = new HashMap<String, Integer>() {{
        put("CUP", R.string.measure_cup);
        put("TSP", R.string.measure_tsp);
        put("TBLSP", R.string.measure_tblsp);
        put("K", R.string.measure_k);
        put("G", R.string.measure_g);
        put("OZ", R.string.measure_oz);
        put("UNIT", R.string.measure_unit);
    }};
}
