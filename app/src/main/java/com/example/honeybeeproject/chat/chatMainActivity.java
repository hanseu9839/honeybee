package com.example.honeybeeproject.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.honeybeeproject.MainActivity;
import com.example.honeybeeproject.R;
import com.example.honeybeeproject.calendar.calendarMainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class chatMainActivity extends AppCompatActivity {
    private WebSocket webSocket;
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ListView messageList = findViewById(R.id.messageList);
        EditText messageBox = findViewById(R.id.messageBox);
        TextView send = findViewById(R.id.send);

        instantiateWebSocket();

        adapter = new MessageAdapter();
        messageList.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageBox.getText().toString();
                if (!message.isEmpty()) {
                    webSocket.send(message);
                    messageBox.setText("");

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("message", message);
                        jsonObject.put("byServer", false);

                        adapter.addItem(jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void instantiateWebSocket() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://112.175.184.78:21").build();
        SocketListener socketListener = new SocketListener(this);
        webSocket = client.newWebSocket(request, socketListener);
    }

    public class SocketListener extends WebSocketListener {

        public chatMainActivity activity;

        public SocketListener(chatMainActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(activity, "연결완료!!", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("message", text);
                        jsonObject.put("bySever", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            super.onMessage(webSocket, text);
        }


        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
        }
    }


    public class MessageAdapter extends BaseAdapter {

        List<JSONObject> messageList = new ArrayList<>();
        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public Object getItem(int i) {
            return messageList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view == null)
                view = getLayoutInflater().inflate(R.layout.message_list_item, viewGroup ,false);

            TextView sentMessage = view.findViewById(R.id.sentMessage);
            TextView receivedMessage = view.findViewById(R.id.receivedMessage);

            JSONObject item = messageList.get(i);
            try {
                if(item.getBoolean("byServer")) {
                    receivedMessage.setVisibility(View.VISIBLE);
                    receivedMessage.setText(item.getString("message"));
                    sentMessage.setVisibility(View.INVISIBLE);
                } else{
                    sentMessage.setVisibility(View.VISIBLE);
                    sentMessage.setText(item.getString("message"));
                    receivedMessage.setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }
        void addItem(JSONObject item){
            messageList.add(item);
            notifyDataSetChanged();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(chatMainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}