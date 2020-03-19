package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.util.Log;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ReplForm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.DataChannel;
import org.webrtc.DataChannel.Buffer;
import org.webrtc.DataChannel.Init;
import org.webrtc.DataChannel.Observer;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnection.ContinualGatheringPolicy;
import org.webrtc.PeerConnection.IceConnectionState;
import org.webrtc.PeerConnection.IceGatheringState;
import org.webrtc.PeerConnection.IceServer;
import org.webrtc.PeerConnection.IceServer.Builder;
import org.webrtc.PeerConnection.RTCConfiguration;
import org.webrtc.PeerConnection.SignalingState;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.PeerConnectionFactory.Options;
import org.webrtc.RtpReceiver;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;
import org.webrtc.SessionDescription.Type;

public class WebRTCNativeMgr {
    private static final boolean DEBUG = true;
    private static final String LOG_TAG = "AppInvWebRTC";
    /* access modifiers changed from: private */
    public static final CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
    /* access modifiers changed from: private */
    public DataChannel dataChannel = null;
    Observer dataObserver = new Observer() {
        public void onBufferedAmountChange(long j) {
        }

        public void onMessage(Buffer buffer) {
            try {
                String input = WebRTCNativeMgr.utf8Decoder.decode(buffer.data).toString();
                Log.d(WebRTCNativeMgr.LOG_TAG, "onMessage: received: " + input);
                WebRTCNativeMgr.this.form.evalScheme(input);
            } catch (CharacterCodingException e) {
                Log.e(WebRTCNativeMgr.LOG_TAG, "onMessage decoder error", e);
            }
        }

        public void onStateChange() {
        }
    };
    /* access modifiers changed from: private */
    public boolean first = true;
    /* access modifiers changed from: private */
    public ReplForm form;
    /* access modifiers changed from: private */
    public volatile boolean haveLocalDescription = false;
    private boolean haveOffer = false;
    private List<IceServer> iceServers = new ArrayList();
    /* access modifiers changed from: private */
    public volatile boolean keepPolling = true;
    PeerConnection.Observer observer = new PeerConnection.Observer() {
        public void onAddStream(MediaStream mediaStream) {
        }

        public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreamArr) {
        }

        public void onDataChannel(DataChannel dataChannel) {
            Log.d(WebRTCNativeMgr.LOG_TAG, "Have Data Channel!");
            Log.d(WebRTCNativeMgr.LOG_TAG, "v5");
            WebRTCNativeMgr.this.dataChannel = dataChannel;
            dataChannel.registerObserver(WebRTCNativeMgr.this.dataObserver);
            WebRTCNativeMgr.this.keepPolling = false;
            WebRTCNativeMgr.this.timer.cancel();
            Log.d(WebRTCNativeMgr.LOG_TAG, "Poller() Canceled");
            WebRTCNativeMgr.this.seenNonces.clear();
        }

        public void onIceCandidate(IceCandidate iceCandidate) {
            try {
                Log.d(WebRTCNativeMgr.LOG_TAG, "IceCandidate = " + iceCandidate.toString());
                if (iceCandidate.sdp == null) {
                    Log.d(WebRTCNativeMgr.LOG_TAG, "IceCandidate is null");
                } else {
                    Log.d(WebRTCNativeMgr.LOG_TAG, "IceCandidateSDP = " + iceCandidate.sdp);
                }
                JSONObject response = new JSONObject();
                response.put("nonce", WebRTCNativeMgr.this.random.nextInt(Form.MAX_PERMISSION_NONCE));
                JSONObject jsonCandidate = new JSONObject();
                jsonCandidate.put("candidate", iceCandidate.sdp);
                jsonCandidate.put("sdpMLineIndex", iceCandidate.sdpMLineIndex);
                jsonCandidate.put("sdpMid", iceCandidate.sdpMid);
                response.put("candidate", jsonCandidate);
                WebRTCNativeMgr.this.sendRendezvous(response);
            } catch (Exception e) {
                Log.e(WebRTCNativeMgr.LOG_TAG, "Exception during onIceCandidate", e);
            }
        }

        public void onIceCandidatesRemoved(IceCandidate[] iceCandidateArr) {
        }

        public void onIceConnectionChange(IceConnectionState iceConnectionState) {
        }

        public void onIceConnectionReceivingChange(boolean z) {
        }

        public void onIceGatheringChange(IceGatheringState iceGatheringState) {
            Log.d(WebRTCNativeMgr.LOG_TAG, "onIceGatheringChange: iceGatheringState = " + iceGatheringState);
        }

        public void onRemoveStream(MediaStream mediaStream) {
        }

        public void onRenegotiationNeeded() {
        }

        public void onSignalingChange(SignalingState signalingState) {
            Log.d(WebRTCNativeMgr.LOG_TAG, "onSignalingChange: signalingState = " + signalingState);
        }
    };
    /* access modifiers changed from: private */
    public PeerConnection peerConnection;
    /* access modifiers changed from: private */
    public String rCode;
    /* access modifiers changed from: private */
    public Random random = new Random();
    private String rendezvousServer = null;
    /* access modifiers changed from: private */
    public String rendezvousServer2 = null;
    SdpObserver sdpObserver = new SdpObserver() {
        public void onCreateFailure(String str) {
            Log.d(WebRTCNativeMgr.LOG_TAG, "onCreateFailure: " + str);
        }

        public void onCreateSuccess(SessionDescription sessionDescription) {
            try {
                Log.d(WebRTCNativeMgr.LOG_TAG, "sdp.type = " + sessionDescription.type.canonicalForm());
                Log.d(WebRTCNativeMgr.LOG_TAG, "sdp.description = " + sessionDescription.description);
                new Init();
                if (sessionDescription.type == Type.OFFER) {
                    Log.d(WebRTCNativeMgr.LOG_TAG, "Got offer, about to set remote description (again?)");
                    WebRTCNativeMgr.this.peerConnection.setRemoteDescription(WebRTCNativeMgr.this.sdpObserver, sessionDescription);
                } else if (sessionDescription.type == Type.ANSWER) {
                    Log.d(WebRTCNativeMgr.LOG_TAG, "onCreateSuccess: type = ANSWER");
                    WebRTCNativeMgr.this.peerConnection.setLocalDescription(WebRTCNativeMgr.this.sdpObserver, sessionDescription);
                    WebRTCNativeMgr.this.haveLocalDescription = true;
                    JSONObject offer = new JSONObject();
                    offer.put("type", "answer");
                    offer.put("sdp", sessionDescription.description);
                    JSONObject response = new JSONObject();
                    response.put("offer", offer);
                    WebRTCNativeMgr.this.sendRendezvous(response);
                }
            } catch (Exception e) {
                Log.e(WebRTCNativeMgr.LOG_TAG, "Exception during onCreateSuccess", e);
            }
        }

        public void onSetFailure(String str) {
        }

        public void onSetSuccess() {
        }
    };
    /* access modifiers changed from: private */
    public TreeSet<String> seenNonces = new TreeSet<>();
    Timer timer = new Timer();

    public WebRTCNativeMgr(String rendezvousServer3, String rendezvousResult) {
        this.rendezvousServer = rendezvousServer3;
        if (rendezvousResult.isEmpty() || rendezvousResult.startsWith("OK")) {
            rendezvousResult = "{\"rendezvous2\" : \"rendezvous.appinventor.mit.edu\",\"iceservers\" : [{ \"server\" : \"turn:turn.appinventor.mit.edu:3478\",\"username\" : \"oh\",\"password\" : \"boy\"}]}";
        }
        try {
            JSONObject resultJson = new JSONObject(rendezvousResult);
            this.rendezvousServer2 = resultJson.getString("rendezvous2");
            JSONArray iceServerArray = resultJson.getJSONArray("iceservers");
            this.iceServers = new ArrayList(iceServerArray.length());
            for (int i = 0; i < iceServerArray.length(); i++) {
                JSONObject jsonServer = iceServerArray.getJSONObject(i);
                Builder builder = IceServer.builder(jsonServer.getString("server"));
                Log.d(LOG_TAG, "Adding iceServer = " + jsonServer.getString("server"));
                if (jsonServer.has("username")) {
                    builder.setUsername(jsonServer.getString("username"));
                }
                if (jsonServer.has("password")) {
                    builder.setPassword(jsonServer.getString("password"));
                }
                this.iceServers.add(builder.createIceServer());
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "parsing iceServers:", e);
        }
    }

    public void initiate(ReplForm form2, Context context, String code) {
        this.form = form2;
        this.rCode = code;
        PeerConnectionFactory.initializeAndroidGlobals(context, false);
        PeerConnectionFactory factory = new PeerConnectionFactory(new Options());
        RTCConfiguration rtcConfig = new RTCConfiguration(this.iceServers);
        rtcConfig.continualGatheringPolicy = ContinualGatheringPolicy.GATHER_CONTINUALLY;
        this.peerConnection = factory.createPeerConnection(rtcConfig, new MediaConstraints(), this.observer);
        this.timer.schedule(new TimerTask() {
            public void run() {
                WebRTCNativeMgr.this.Poller();
            }
        }, 0, 1000);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0099 A[SYNTHETIC, Splitter:B:16:0x0099] */
    public void Poller() {
        try {
            if (this.keepPolling) {
                Log.d(LOG_TAG, "Poller() Called");
                Log.d(LOG_TAG, "Poller: rendezvousServer2 = " + this.rendezvousServer2);
                HttpResponse response = new DefaultHttpClient().execute(new HttpGet("http://" + this.rendezvousServer2 + "/rendezvous2/" + this.rCode + "-s"));
                StringBuilder sb = new StringBuilder();
                BufferedReader rd = null;
                try {
                    BufferedReader rd2 = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String str = "";
                    while (true) {
                        try {
                            String line = rd2.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                        } catch (Throwable th) {
                            th = th;
                            rd = rd2;
                            if (rd != null) {
                                rd.close();
                            }
                            throw th;
                        }
                    }
                    if (rd2 != null) {
                        rd2.close();
                    }
                    if (!this.keepPolling) {
                        Log.d(LOG_TAG, "keepPolling is false, we're done!");
                        return;
                    }
                    String responseText = sb.toString();
                    Log.d(LOG_TAG, "response = " + responseText);
                    if (responseText.equals("")) {
                        Log.d(LOG_TAG, "Received an empty response");
                        return;
                    }
                    JSONArray jsonArray = new JSONArray(responseText);
                    Log.d(LOG_TAG, "jsonArray.length() = " + jsonArray.length());
                    int i = 0;
                    while (i < jsonArray.length()) {
                        Log.d(LOG_TAG, "i = " + i);
                        Log.d(LOG_TAG, "element = " + jsonArray.optString(i));
                        JSONObject element = (JSONObject) jsonArray.get(i);
                        if (!this.haveOffer) {
                            if (!element.has("offer")) {
                                i++;
                            } else {
                                JSONObject offer = (JSONObject) element.get("offer");
                                String sdp = offer.optString("sdp");
                                String type = offer.optString("type");
                                this.haveOffer = true;
                                Log.d(LOG_TAG, "sdb = " + sdp);
                                Log.d(LOG_TAG, "type = " + type);
                                Log.d(LOG_TAG, "About to set remote offer");
                                Log.d(LOG_TAG, "Got offer, about to set remote description (maincode)");
                                PeerConnection peerConnection2 = this.peerConnection;
                                SdpObserver sdpObserver2 = this.sdpObserver;
                                SessionDescription sessionDescription = new SessionDescription(Type.OFFER, sdp);
                                peerConnection2.setRemoteDescription(sdpObserver2, sessionDescription);
                                this.peerConnection.createAnswer(this.sdpObserver, new MediaConstraints());
                                Log.d(LOG_TAG, "createAnswer returned");
                                i = -1;
                            }
                        } else if (element.has("nonce")) {
                            if (!this.haveLocalDescription) {
                                Log.d(LOG_TAG, "Incoming candidate before local description set, punting");
                                return;
                            } else if (element.has("offer")) {
                                i++;
                                Log.d(LOG_TAG, "skipping offer, already processed");
                            } else if (element.isNull("candidate")) {
                                i++;
                            } else {
                                String nonce = element.optString("nonce");
                                JSONObject candidate = (JSONObject) element.get("candidate");
                                String sdpcandidate = candidate.optString("candidate");
                                String sdpMid = candidate.optString("sdpMid");
                                int sdpMLineIndex = candidate.optInt("sdpMLineIndex");
                                if (!this.seenNonces.contains(nonce)) {
                                    this.seenNonces.add(nonce);
                                    Log.d(LOG_TAG, "new nonce, about to add candidate!");
                                    Log.d(LOG_TAG, "candidate = " + sdpcandidate);
                                    this.peerConnection.addIceCandidate(new IceCandidate(sdpMid, sdpMLineIndex, sdpcandidate));
                                    Log.d(LOG_TAG, "addIceCandidate returned");
                                }
                            }
                        }
                        i++;
                    }
                    Log.d(LOG_TAG, "exited loop");
                } catch (Throwable th2) {
                    th = th2;
                    if (rd != null) {
                    }
                    throw th;
                }
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Caught IOException: " + e.toString(), e);
        } catch (JSONException e2) {
            Log.e(LOG_TAG, "Caught JSONException: " + e2.toString(), e2);
        } catch (Exception e3) {
            Log.e(LOG_TAG, "Caught Exception: " + e3.toString(), e3);
        }
    }

    /* access modifiers changed from: private */
    public void sendRendezvous(final JSONObject data) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                try {
                    data.put("first", WebRTCNativeMgr.this.first);
                    data.put("webrtc", true);
                    data.put("key", WebRTCNativeMgr.this.rCode + "-r");
                    if (WebRTCNativeMgr.this.first) {
                        WebRTCNativeMgr.this.first = false;
                        data.put("apiversion", SdkLevel.getLevel());
                    }
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://" + WebRTCNativeMgr.this.rendezvousServer2 + "/rendezvous2/");
                    try {
                        Log.d(WebRTCNativeMgr.LOG_TAG, "About to send = " + data.toString());
                        post.setEntity(new StringEntity(data.toString()));
                        client.execute(post);
                    } catch (IOException e) {
                        Log.e(WebRTCNativeMgr.LOG_TAG, "sendRedezvous IOException", e);
                    }
                } catch (Exception e2) {
                    Log.e(WebRTCNativeMgr.LOG_TAG, "Exception in sendRendezvous", e2);
                }
            }
        });
    }

    public void send(String output) {
        try {
            if (this.dataChannel == null) {
                Log.w(LOG_TAG, "No Data Channel in Send");
                return;
            }
            this.dataChannel.send(new Buffer(ByteBuffer.wrap(output.getBytes("UTF-8")), false));
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "While encoding data to send to companion", e);
        }
    }
}
