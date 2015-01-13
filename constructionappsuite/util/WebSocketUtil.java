package com.example.codetribe1.constructionappsuite.util;

import android.content.Context;
import android.util.Log;

import com.example.codetribe1.constructionappsuite.R;
import com.example.codetribe1.constructionappsuite.dto.transfer.RequestDTO;
import com.example.codetribe1.constructionappsuite.dto.transfer.ResponseDTO;
import com.example.codetribe1.constructionappsuite.toolbox.BaseVolley;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

/**
 * Utility class to manage web socket communications for the application
 * Created by aubreyM on 2014/08/10.
 */
public class WebSocketUtil {
    static final String LOG = WebSocketUtil.class.getSimpleName();
    static final Gson gson = new Gson();
    static WebSocketListener webSocketListener;
    static RequestDTO request;
    static Context ctx;
    static long start, end;
    static WebSocketClient mWebSocketClient;

    public static void disconnectSession() {
        if (mWebSocketClient != null) {
            mWebSocketClient.close();
            Log.e(LOG, "@@ webSocket session disconnected");
        }
    }

    public static void sendRequest(Context c, final String suffix, RequestDTO req, WebSocketListener listener) {
        if (!BaseVolley.checkNetworkOnDevice(c)) {
            listener.onError("Network connections unavailable");
            return;
        }

        start = System.currentTimeMillis();
        webSocketListener = listener;
        request = req;
        ctx = c;
        TimerUtil.startTimer(new TimerUtil.TimerListener() {
            @Override
            public void onSessionDisconnected() {
                try {
                    connectWebSocket(suffix);
                    return;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            if (mWebSocketClient == null) {
                connectWebSocket(suffix);
            } else {
                String json = gson.toJson(req);
                mWebSocketClient.send(json);
                Log.d(LOG, "## web socket message sent\n" + json);
            }


        } catch (WebsocketNotConnectedException e) {
            try {
                Log.e(LOG, "WebsocketNotConnectedException. Problems with web socket", e);
                connectWebSocket(suffix);
            } catch (URISyntaxException e1) {
                Log.e(LOG, "Problems with web socket", e);
                webSocketListener.onError("Problem starting server socket communications\n" + e1.getMessage());
            }
        } catch (URISyntaxException e) {
            Log.e(LOG, "Problems with web socket", e);
            webSocketListener.onError("Problem starting server socket communications");
        }
    }

    private static void connectWebSocket(String socketSuffix) throws URISyntaxException {
        URI uri = new URI(Statics.WEBSOCKET_URL + socketSuffix);

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.w(LOG, "########## WEBSOCKET Opened: " + serverHandshake.getHttpStatusMessage());
                String json = gson.toJson(request);
                mWebSocketClient.send(json);
                Log.d(LOG, "### web socket request sent after onOpen\n" + json);
            }

            @Override
            public void onMessage(String response) {
                TimerUtil.killTimer();
                end = System.currentTimeMillis();
                Log.i(LOG, "### onMessage, length: " + response.length() + " elapsed: " + getElapsed()
                        + "\n" + response);
                try {
                    ResponseDTO r = gson.fromJson(response, ResponseDTO.class);
                    if (r.getStatusCode() == 0) {
                        if (r.getSessionID() != null) {
                            SharedUtil.saveSessionID(ctx, r.getSessionID());
                        }
                    } else {
                        webSocketListener.onError(r.getMessage());
                    }
                } catch (Exception e) {
                    Log.e(LOG, "Failed to parse response from server", e);
                    webSocketListener.onError("Failed to parse response from server");
                }

            }

            @Override
            public void onMessage(ByteBuffer bb) {
                TimerUtil.killTimer();
                end = System.currentTimeMillis();
                parseData(bb);
            }


            @Override
            public void onClose(final int i, String s, boolean b) {
                Log.e(LOG, "########## WEBSOCKET onClose, status code:  " + i);
                webSocketListener.onClose();
            }

            @Override
            public void onError(final Exception e) {
                Log.e(LOG, "onError ", e);
                webSocketListener.onError(ctx.getString(R.string.server_comms_failed));


            }
        };

        Log.d(LOG, "### #### -------------> starting mWebSocketClient.connect ...");

        mWebSocketClient.connect();
    }

    private static void parseData(ByteBuffer bb) {
        Log.i(LOG, "### parseData ByteBuffer capacity: " + ZipUtil.getKilobytes(bb.capacity()));
        String content = null;
        try {
            //check if dd is not compressed
            try {
                content = new String(bb.array());
                ResponseDTO response = gson.fromJson(content, ResponseDTO.class);
                if (response.getStatusCode() == 0) {
                    webSocketListener.onMessage(response);
                } else {
                    webSocketListener.onError(response.getMessage());
                }
                return;

            } catch (Exception e) {
                content = ZipUtil.uncompressGZip(bb);
            }

            if (content != null) {
                Log.i(LOG, "### parseData, resonse unpacked OK - elapsed: " + getElapsed());
                ResponseDTO response = gson.fromJson(content, ResponseDTO.class);
                if (response.getStatusCode() == 0) {
                    Log.w(LOG, "### response status code is 0 - OK");
                    webSocketListener.onMessage(response);
                } else {
                    Log.e(LOG, "## response status code is > 0 - server found ERROR");
                    webSocketListener.onError(response.getMessage());
                }
            } else {
                Log.e(LOG, "-- Content from server failed. Response is null");
                webSocketListener.onError("Content from server failed. Response is null");
            }
        } catch (Exception e) {
            Log.e(LOG, "parseData Failed", e);
            webSocketListener.onError("Failed to unpack server response");
        }
    }

    public static String getElapsed() {
        BigDecimal m = new BigDecimal(end - start).divide(new BigDecimal(1000));

        return "" + m.doubleValue() + " seconds";
    }

    public interface WebSocketListener {
        public void onMessage(ResponseDTO response);

        public void onClose();

        public void onError(String message);

    }
}
