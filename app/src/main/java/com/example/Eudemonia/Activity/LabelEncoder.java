package com.example.Eudemonia.Activity;

import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LabelEncoder {
    private Map<String, Integer> labelToIndexMap;
    private Map<Integer, String> indexToLabelMap;

    public LabelEncoder() {
        labelToIndexMap = new HashMap<>();
        indexToLabelMap = new HashMap<>();
    }

    public void fit(String[] labels) {
        labelToIndexMap.clear();
        indexToLabelMap.clear();
        for (String label : labels) {
            if (!labelToIndexMap.containsKey(label)) {
                int index = labelToIndexMap.size();
                labelToIndexMap.put(label, index);
                indexToLabelMap.put(index, label);
            }
        }
    }

    public int[] transform(String labels) {
        int[] encodedLabels = new int[labels.length()];
        for (int i = 0; i < labels.length(); i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                encodedLabels[i] = labelToIndexMap.getOrDefault(labels, -1);
            }
        }
        return encodedLabels;
    }

    public String[] inverseTransform(int[] encodedLabels) {
        String[] labels = new String[encodedLabels.length];
        for (int i = 0; i < encodedLabels.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                labels[i] = indexToLabelMap.getOrDefault(encodedLabels[i], null);
            }
        }
        return labels;
    }

    public JSONArray toJson() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, Integer> entry : labelToIndexMap.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("label", entry.getKey());
            jsonObject.put("index", entry.getValue());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public static LabelEncoder fromJson(JSONArray jsonArray) throws JSONException {
        LabelEncoder labelEncoder = new LabelEncoder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String label = jsonObject.getString("label");
            int index = jsonObject.getInt("index");
            labelEncoder.labelToIndexMap.put(label, index);
            labelEncoder.indexToLabelMap.put(index, label);
        }
        return labelEncoder;
    }
}
