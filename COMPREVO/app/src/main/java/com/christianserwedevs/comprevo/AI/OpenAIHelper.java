package com.christianserwedevs.comprevo.AI;

import android.telecom.Call;
import android.util.Log;

import androidx.annotation.NonNull;

import com.christianserwedevs.comprevo.BuildConfig;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class OpenAIHelper {
    private static final String API_KEY = BuildConfig.OPENAI_API_KEY;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final OkHttpClient client = new OkHttpClient();
    public interface OpenAIResponseCallback {
        void onSuccess(String definition, String usage, String example, String similar, String pronunciation);
        void onFailure(String error);
    }
    public static void fetchWordDetails(String word, OpenAIResponseCallback callback) {
        JSONObject jsonBody = new JSONObject();
        try {
//            String prompt = "Provide the following details that can be easily understand by 12-13 years old student for the word '" + word + "':\n" +
//                    "- Definition\n" +
//                    "- Usage in a sentence\n" +
//                    "- An example sentence\n" +
//                    "- Similar words (synonyms)\n" +
//                    "- Pronunciation (IPA format, like /ˈhindər/)\n" +
//                    "Format the response as JSON with these keys:\n" +
//                    "{\n" +
//                    "  \"definition\": \"<definition>\",\n" +
//                    "  \"usage\": \"<usage>\",\n" +
//                    "  \"example\": \"<example>\",\n" +
//                    "  \"similar\": \"<similar words>\",\n" +
//                    "  \"pronunciation\": \"<pronunciation>\"\n" +
//                    "}";
//
//            jsonBody.put("model", "gpt-3.5-turbo");
//            jsonBody.put("messages", new org.json.JSONArray()
//                    .put(new JSONObject().put("role", "system").put("content", "You are a dictionary assistant."))
//                    .put(new JSONObject().put("role", "user").put("content", prompt)));
//            jsonBody.put("max_tokens", 200);

            String prompt = "Explain the word '" + word + "' in simple words for a 9-13 year old with:\n" +
                    "- Definition\n" +
                    "- Usage\n" +
                    "- Example\n" +
                    "- Synonyms\n" +
                    "- Pronunciation (IPA format)\n" +
                    "Respond in JSON format:\n" +
                    "{\n" +
                    "  \"definition\": \"<definition>\",\n" +
                    "  \"usage\": \"<usage>\",\n" +
                    "  \"example\": \"<example>\",\n" +
                    "  \"similar\": \"<synonyms>\",\n" +
                    "  \"pronunciation\": \"<pronunciation>\"\n" +
                    "}";

            jsonBody.put("model", "gpt-3.5-turbo");
            jsonBody.put("messages", new org.json.JSONArray()
                    .put(new JSONObject().put("role", "system").put("content", "You are a helpful dictionary assistant."))
                    .put(new JSONObject().put("role", "user").put("content", prompt)));
            jsonBody.put("max_tokens", 150);
        } catch (Exception e) {
            callback.onFailure("Error creating JSON body: " + e.getMessage());
            return;
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure("Error: " + response.message());
                    return;
                }
                String responseData = response.body().string();
                try {
                    JSONObject jsonResponse = new JSONObject(responseData);

                    // Extract the 'content' from 'choices' array
                    String aiResponse = jsonResponse
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");

                    // Debugging: Print the full AI response
                    System.out.println("Raw OpenAI Response: " + aiResponse);

                    // Clean up JSON formatting
                    aiResponse = aiResponse.trim()
                            .replace("```json", "")
                            .replace("```", "")
                            .replace("\n", "")
                            .replace("\r", "")
                            .replace("\\", "");
                    System.out.println("Raw OpenAI Response: " + aiResponse);



                    if (aiResponse.startsWith("{") && aiResponse.endsWith("}")) {
                        JSONObject responseJson = new JSONObject(aiResponse);

                        String definition = responseJson.optString("definition", "No definition found.");
                        String usage = responseJson.optString("usage", "No usage found.");
                        String example = responseJson.optString("example", "No example found.");
                        String similar = responseJson.optString("similar", "No synonyms found.");
                        String pronunciation = responseJson.optString("pronunciation", "No pronunciation found.");

                        callback.onSuccess(definition, usage, example, similar, pronunciation);
                    } else {
                        callback.onFailure("Malformed JSON: " + aiResponse);
                    }

                } catch (Exception e) {
                    callback.onFailure("Parsing error: " + e.getMessage() + "\nResponse: " + responseData);
                }
            }



            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                callback.onFailure("Request failed: " + e.getMessage());
            }
        });
    }

}